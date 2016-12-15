package com.irdeto.jumpstart.workflow.publish.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.domain.control.cws.objectfactory.UpsertP7CategoryObjectFactory;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.TermMapping.PolicyType;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.control.ControlAvailabilityOverride;
import com.irdeto.jumpstart.domain.control.ControlBatch;
import com.irdeto.jumpstart.domain.control.ControlContent;
import com.irdeto.jumpstart.domain.control.ControlMedia;
import com.irdeto.jumpstart.domain.control.ControlPriceOverride;
import com.irdeto.jumpstart.domain.control.qsa.ControlQSAAuthorizationInfo;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.workflow.DateHelper;
import com.irdeto.jumpstart.workflow.publish.PublishHelper;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public abstract class AbstractControlMapper<T extends Base> implements ControlMapper<T> {
	protected static final String TYPE = "default";
	private static final Boolean BLOCKED = false;
	private static final Logger logger = LoggerFactory.getLogger(AbstractControlMapper.class);
	
	private PublishWrapper<T> publishWrapper;
	
	public PublishWrapper<T> getPublishWrapper() {
		return publishWrapper;
	}

	@Override
	public void setPublishWrapper(PublishWrapper<T> publishWrapper) {
		this.publishWrapper = publishWrapper;
	}

	// No video content but with availability => publish as an offered category, i.e. as its own content item and as in the Control category hierarchy 
	// No video content and no availability => publish as a category only, i.e. in the Control category hierarchy only 
	// Video content and availability => publish as a content item
	// Channel and events are published with the live flag
	@JsonIgnore
	@Override
	public ControlBatch getControlBatch() {
		List<ControlMedia> mediaList = getMediaList();

		if (mediaList.isEmpty()) {
			return null;
		}
		ControlBatch controlBatch = new ControlBatch();
		controlBatch.setMediaList(mediaList);

		return controlBatch;
	}
	
	@JsonIgnore
	protected abstract List<ControlMedia> getMediaList();
	
	@JsonIgnore
	protected List<ControlMedia> getDirectlyOfferedMediaList() {
		List<ControlMedia> mediaList = new ArrayList<>();
		
		// Only include if content is directly offered.
		List<TermWrapper> directlyOfferedTermWrapperList = new ArrayList<>();
		for (TermWrapper termWrapper: getPublishWrapper().getTermWrapperList()) {
			if (termWrapper.getOfferedEntity().getId().equals(getPublishWrapper().getApprovedEntity().getId())
					&& termWrapper.getOfferedEntity().getType().equals(getPublishWrapper().getApprovedEntity().getType())) {
				directlyOfferedTermWrapperList.add(termWrapper);
			}
		}
		if (!directlyOfferedTermWrapperList.isEmpty()) {
			mediaList.add(getMedia(directlyOfferedTermWrapperList));
		}
		return mediaList;
	}

	@JsonIgnore
	protected List<ControlMedia> getIndirectlyOfferedMediaList() {
		List<TermWrapper> termWrapperList = getPublishWrapper().getTermWrapperList();
		List<ControlMedia> mediaList = new ArrayList<>();
		if (!termWrapperList.isEmpty()) {
			mediaList.add(getMedia(termWrapperList));
		}
		return mediaList;
	}

	private ControlMedia getMedia(List<TermWrapper> termWrapperList) {
		Base entity = getPublishWrapper().getApprovedEntity();
		
		ControlMedia media = new ControlMedia();
		media.setAccountId(getAccountId());
		media.setMediaId(PublishHelper.getControlAssetId(entity));
		media.setName(getPublishWrapper().getApprovedEntity().getUriId());
		List<ControlContent> contentList = new ArrayList<>();
		ControlContent controlContent = getContent(entity, termWrapperList);
		if (controlContent != null) {
			contentList.add(controlContent);
		}
		media.setContentList(contentList);
		return media;
	}

	private ControlContent getContent(Base entity, List<TermWrapper> termWrapperList) {
		Integer policyGroupId = null;
		
		if (termWrapperList != null) {
			logger.debug("term wrapper list size is ..."+termWrapperList.size());
			for (TermWrapper termWrapper: termWrapperList) {
				if (termWrapper.getTermMapList() != null) {
					for (TermMap termMap: termWrapper.getTermMapList()) {
						if (termMap.getPolicyGroupId() != null && isMainContentType(termMap)) {
							if (policyGroupId != null && !policyGroupId.equals(termMap.getPolicyGroupId())) {
								logger.error("Policy group ID is both {} and {}.  Only one policy group ID can be used.", policyGroupId, termMap.getPolicyGroupId());
							}
							policyGroupId = termMap.getPolicyGroupId();
						}
					}
				}
			}
		}
		if (policyGroupId == null) {
			logger.warn("Policy group id is null");
			return null;
		}
		
		ControlContent controlContent = new ControlContent();
		controlContent.setAccountId(getAccountId());
		controlContent.setContentId(PublishHelper.getControlAssetId(entity));
		controlContent.setMediaId(PublishHelper.getControlAssetId(entity));
		controlContent.setPolicyId(policyGroupId.toString());
		controlContent.setType(TYPE);
		if (entity != null && (
				entity instanceof Channel
				|| entity instanceof Event
				)) {
			controlContent.setLive(true);
		}
		controlContent.setAvailabilityOverrideList(getAvailabilityOverrideList(termWrapperList, true));
		controlContent.setPriceOverrideList(getPriceOverrideList(termWrapperList));
		return controlContent;
	}

	
	protected List<ControlPriceOverride> getPriceOverrideList(List<TermWrapper> termWrapperList) {
		List<ControlPriceOverride> priceOverrideList = new ArrayList<>();
		for (TermWrapper termWrapper: termWrapperList) {
			for (Entry<String, Map<String, String>> countryPriceEntry: termWrapper.getMergedCurrencyMap().entrySet()) {
				handleCountryPriceEntry(priceOverrideList, termWrapper, countryPriceEntry);
			}
		}
		return priceOverrideList;
	}

	private void handleCountryPriceEntry(List<ControlPriceOverride> priceOverrideList, TermWrapper termWrapper,
			Entry<String, Map<String, String>> countryPriceEntry) {
		for (TermMap termMap: termWrapper.getTermMapList()) {
			if (isMainContentType(termMap) && !isFreePolicyType(termMap)) {
				ControlPriceOverride priceOverride = new ControlPriceOverride();
				priceOverride.setCountry(countryPriceEntry.getKey());
				Iterator<String> priceEntryIter = countryPriceEntry.getValue().values().iterator();
				if (priceEntryIter.hasNext()) {
					priceOverride.setAmount(Float.valueOf(priceEntryIter.next()));
				}
				priceOverride.setOptionId(termMap.getPolicyId().toString());
				priceOverrideList.add(priceOverride);
			}
		}
	}

	protected List<ControlAvailabilityOverride> getAvailabilityOverrideList(List<TermWrapper> termWrapperList,
			boolean onlyIncludeMainContentType) {
		List<ControlAvailabilityOverride> availabilityOverrideList = new ArrayList<>();
		for (TermWrapper termWrapper: termWrapperList) {
			if (onlyIncludeMainContentType && !isAllMainContentType(termWrapper.getTermMapList())) {
				continue;
			}

			for (String country: termWrapper.getMergedCurrencyMap().keySet()) {
				ControlAvailabilityOverride availabilityOverride = new ControlAvailabilityOverride();
				availabilityOverride.setBlocked(BLOCKED);
				availabilityOverride.setCountry(country);
				//1900-01-01T00:00:00+01:00
				availabilityOverride.setReleaseDate(DateHelper.convertDateTimeToXMLDate(termWrapper.getStartDateTime()));
				//2099-12-31T23:00:00+01:00
				availabilityOverride.setReleaseEnd(DateHelper.convertDateTimeToXMLDate(termWrapper.getEndDateTime()));
				availabilityOverrideList.add(availabilityOverride);
			}
		}
		return availabilityOverrideList;
	}
	
	@Override
	@JsonIgnore
	public List<SoapEnvelope> getCategorySoapEnvelopeList(List<ControlQSAAuthorizationInfo> authorizationInfoList) {
		String categoryId = PublishHelper.getControlAssetId(getPublishWrapper().getApprovedEntity());
		String name = getPublishWrapper().getApprovedEntity().getUriId();
		Set<Integer> policyGroupIdSet = new HashSet<>();
		for (TermWrapper termWrapper: getPublishWrapper().getTermWrapperList()) {
			for (TermMap termMap: termWrapper.getTermMapList()) {
				if (isSameAsApprovedEntity(termWrapper.getOfferedEntity())) {
					policyGroupIdSet.add(termMap.getPolicyGroupId());
				}
			}
		}
		List<SoapEnvelope> soapEnvelopeList = new ArrayList<>();
		for (Integer policyGroupId: policyGroupIdSet) {
			soapEnvelopeList.add(UpsertP7CategoryObjectFactory.createSoapEnvelope("Insert", categoryId, null, name,
					String.valueOf(policyGroupId), name));
		}
		return soapEnvelopeList;
	}
	
	private String getAccountId() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_ACCOUNT_ID_KEY);
		} catch (PropertyException e) {
		}
		return null;
	}

	@JsonIgnore
	private boolean isAllMainContentType(List<TermMap> termMapList) {
		if (termMapList != null) {
			for (TermMap termMap: termMapList) {
				if (!isMainContentType(termMap)) {
					return false;
				}
			}
		}
		return true;
	}

	@JsonIgnore
	protected abstract boolean isMainContentType(TermMap termMap);

	@JsonIgnore
	protected boolean isFreePolicyType(TermMap termMap) {
		return PolicyType.FREE.toString().equals(termMap.getPolicyType());
	}


	@JsonIgnore
	protected boolean isSameAsApprovedEntity(Base entity) {
		return getPublishWrapper().getApprovedEntity().getType().equals(entity.getType())
				&& getPublishWrapper().getApprovedEntity().getId().equals(entity.getId());
	}
}
