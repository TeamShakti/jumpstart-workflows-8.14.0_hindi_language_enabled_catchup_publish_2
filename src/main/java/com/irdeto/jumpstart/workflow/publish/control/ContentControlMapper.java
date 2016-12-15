package com.irdeto.jumpstart.workflow.publish.control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.domain.control.cws.objectfactory.UpsertP7MediaCategoryObjectFactory;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.ProtectVideoSub;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.control.ControlContent;
import com.irdeto.jumpstart.domain.control.ControlMedia;
import com.irdeto.jumpstart.domain.control.ControlSubContent;
import com.irdeto.jumpstart.domain.control.qsa.ControlQSAAuthorizationInfo;
import com.irdeto.jumpstart.domain.control.qsa.ControlQSACategory;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.publish.VideoContentWrapper;
import com.irdeto.jumpstart.domain.publish.VideoSubcontentWrapper;
import com.irdeto.jumpstart.workflow.protect.ProtectHelper;
import com.irdeto.jumpstart.workflow.publish.PublishHelper;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;


public abstract class ContentControlMapper<T extends Base> extends AbstractControlMapper<T> {
	@JsonIgnore
	@Override
	protected List<ControlMedia> getMediaList() {
		List<ControlMedia> mediaList = new ArrayList<>();
		
		if (!getPublishWrapper().getVideoContentWrapperList().isEmpty()) {
			//Video Assets, e.g. program
			for (VideoContentWrapper videoContentWrapper: getPublishWrapper().getVideoContentWrapperList()) {
				for (VideoSubcontentWrapper videoSubcontentWrapper: videoContentWrapper.getVideoSubcontentWrapperList()) {
					if (!videoSubcontentWrapper.getTermMapList().isEmpty()
							&& !videoSubcontentWrapper.getTermWrapperList().isEmpty()) {
						try {
							mediaList.add(getMedia(videoContentWrapper, videoSubcontentWrapper));
						} catch (PropertyException e) {
						}
					}
				}
			}
		}
		mediaList.addAll(getIndirectlyOfferedMediaList());
		return mediaList;
	}

	private ControlMedia getMedia(VideoContentWrapper videoContentWrapper, VideoSubcontentWrapper videoSubcontentWrapper) throws PropertyException {
		Base entity = getPublishWrapper().getApprovedEntity();
		VideoContent videoContent = getPublishWrapper().getContentById(videoContentWrapper);
		
		ControlMedia media = new ControlMedia();
		String accountId = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_ACCOUNT_ID_KEY);
		media.setAccountId(accountId);
		media.setMediaId(PublishHelper.getControlAssetId(entity, videoContent));
		media.setName(getPublishWrapper().getApprovedEntity().getUriId());
		List<ControlContent> contentList = new ArrayList<>();
		contentList.add(getContent(entity, videoContent, videoSubcontentWrapper));
		media.setContentList(contentList);
		return media;
	}

	private ControlContent getContent(Base entity, VideoContent videoContent, VideoSubcontentWrapper videoSubcontentWrapper) throws PropertyException {
		ProtectVideoSub protectedVideoSubcontent = getPublishWrapper().getProtectedVideoSubcontentById(videoSubcontentWrapper.getProtectedVideoSubcontentId());
		
		ControlContent controlContent = new ControlContent();
		String accountId = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_ACCOUNT_ID_KEY);
		controlContent.setAccountId(accountId);
		controlContent.setContentId(PublishHelper.getControlAssetId(entity, videoContent));
		controlContent.setMediaId(PublishHelper.getControlAssetId(entity, videoContent));
		controlContent.setPolicyId(protectedVideoSubcontent.getProtectPolicyGroupId());
		controlContent.setType(TYPE);
		controlContent.setAvailabilityOverrideList(getAvailabilityOverrideList(videoSubcontentWrapper.getTermWrapperList(), false));
		controlContent.setPriceOverrideList(getPriceOverrideList(videoSubcontentWrapper.getTermWrapperList()));

		
		if (videoContent != null) {
			if (videoSubcontentWrapper.getProtectList() != null && !videoSubcontentWrapper.getProtectList().isEmpty()) {
				Protect representativeProtect = videoSubcontentWrapper.getProtectList().get(0);
				List<ControlSubContent> subContentList = new ArrayList<>();
				subContentList.add(getSubContent(entity, videoContent, protectedVideoSubcontent, representativeProtect));
				controlContent.setSubContentList(subContentList);
			}
		}
		return controlContent;
	}
	
	private ControlSubContent getSubContent(Base entity, VideoContent videoContent, ProtectVideoSub protectedVideoSubcontent, Protect protect) throws PropertyException {
		ControlSubContent subContent = new ControlSubContent();
		String accountId = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_ACCOUNT_ID_KEY);
		subContent.setAccountId(accountId);
		subContent.setContentId(PublishHelper.getControlAssetId(entity, videoContent));
		subContent.setMediaId(PublishHelper.getControlAssetId(entity, videoContent));
		subContent.setType(ProtectHelper.getProfile(protect).getSubcontentType());
		return subContent;
	}

	@Override
	@JsonIgnore
	public List<SoapEnvelope> getCategorySoapEnvelopeList(List<ControlQSAAuthorizationInfo> authorizationInfoList) {
		// Categories
		List<SoapEnvelope> soapEnvelopeList = super.getCategorySoapEnvelopeList(authorizationInfoList);
		if (soapEnvelopeList == null) {
			soapEnvelopeList = new ArrayList<>();
		}
		// Relationships
		
		// Get unique set of offered entities
		Set<String> offeredEntityCategoryIdSet = new HashSet<>();
		for (TermWrapper termWrapper: getPublishWrapper().getTermWrapperList()) {
			offeredEntityCategoryIdSet.add(PublishHelper.getControlAssetId(termWrapper.getOfferedEntity()));
		}
		// Find videos/channels/events/programs to link
		List<String> mediaIdList = getMediaIdList();
		
		if (mediaIdList != null && !mediaIdList.isEmpty() && !offeredEntityCategoryIdSet.isEmpty()) {
			soapEnvelopeList.add(UpsertP7MediaCategoryObjectFactory.createSoapEnvelope("Insert", new ArrayList<>(offeredEntityCategoryIdSet), mediaIdList));
		}

		// Existing relationships that need to be removed
		for (ControlQSAAuthorizationInfo authorizationInfo: authorizationInfoList) {
			Set<String> deleteCategoryIdSet = new HashSet<>();
			for (ControlQSACategory category: authorizationInfo.getCategoryList()) {
				String categoryId = category.getCategoryId();
				if (!offeredEntityCategoryIdSet.contains(categoryId)) {
					deleteCategoryIdSet.add(categoryId);
				}
			}
			if (mediaIdList != null && !mediaIdList.isEmpty() && !deleteCategoryIdSet.isEmpty()) {
				soapEnvelopeList.add(UpsertP7MediaCategoryObjectFactory.createSoapEnvelope("Delete", new ArrayList<>(deleteCategoryIdSet), mediaIdList));
			}
		}
		
		return soapEnvelopeList;
	}
	
	@JsonIgnore
	public abstract List<String> getMediaIdList();

}
