package com.irdeto.jumpstart.workflow.publish.reference;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseMetadataWithTitle;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.Locale;
import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.domain.reference.ReferenceDocumentWithEntitlement;
import com.irdeto.jumpstart.domain.reference.ReferenceDocumentWithEntitlementAndTitle;
import com.irdeto.jumpstart.domain.reference.ReferenceEntity;
import com.irdeto.jumpstart.domain.reference.ReferenceImageContent;
import com.irdeto.jumpstart.domain.reference.ReferenceOffer;
import com.irdeto.jumpstart.domain.reference.ReferencePurchaseOption;
import com.irdeto.jumpstart.domain.reference.ReferenceRendition;
import com.irdeto.jumpstart.domain.reference.ReferenceVideoContent;
import com.irdeto.jumpstart.workflow.LocaleHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public abstract class AbstractReferenceMapper {
	private static final Logger logger = LoggerFactory.getLogger(AbstractReferenceMapper.class);

	protected static List<String> stringifyList(List<Object> list) {
		List<String> stringList = new ArrayList<>();
		if (list != null) {
			for (Object object : list) {
				stringList.add(object.toString());
			}
		}
		return stringList;
	}

	protected static Map<String, String> mapLocalized(Locale localized) {
		return mapLocalized(localized, null);
	}

	protected static Map<String, String> mapLocalized(Locale localized, Integer length) {
		Map<String, String> languageMap = new HashMap<String, String>();

		if (localized != null) {
			for (String language : LocaleHelper.getLanguages()) {
				String value = LocaleHelper.getStringValueForLanguage(localized, language);
				if (length == null) {
					languageMap.put(language, value);
				} else {
					String abbreviatedValue = StringUtils.abbreviate(value, length);
					languageMap.put(language, abbreviatedValue);
				}
			}
		}

		return languageMap;
	}

	protected static String stringify(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	protected static void mapDocumentTitle(BaseMetadataWithTitle source, ReferenceDocumentWithEntitlementAndTitle referenceDocument) throws Exception {
		mapDocumentTitle(source, null, referenceDocument);
	}

	protected static void mapDocumentTitle(BaseMetadataWithTitle source, List<TermWrapper> termWrapperList, ReferenceDocumentWithEntitlementAndTitle referenceDocument) throws Exception {
		if (source != null && referenceDocument != null) {
			referenceDocument.setTitleSortName(mapLocalized(source.getTitleSortName(), 22));
			referenceDocument.setTitleBrief(mapLocalized(source.getTitleBrief(), 19));
			referenceDocument.setTitleMedium(mapLocalized(source.getTitleMedium(), 35));
			referenceDocument.setTitleLong(mapLocalized(source.getTitleLong(), 128));

			referenceDocument.setSummaryShort(mapLocalized(source.getSummaryShort(), 256));
			referenceDocument.setSummaryMedium(mapLocalized(source.getSummaryMedium(), 1024));
			referenceDocument.setSummaryLong(mapLocalized(source.getSummaryLong(), 4096));

			referenceDocument.setKeywords(source.getKeywords());
			referenceDocument.setCountryOfOrigin(stringifyList(source.getCountryOfOrigin()));
			referenceDocument.setShowType(stringify(source.getShowType()));
		} else {
			throw new Exception("Unable to map null entities.");
		}
	}

	protected static void mapDocumentEntitlement(Base source, ReferenceDocumentWithEntitlement referenceDocument) throws Exception {
		mapDocumentEntitlement(source, null, referenceDocument);
	}

	protected static void mapDocumentEntitlement(Base source, List<TermWrapper> termWrapperList, ReferenceDocumentWithEntitlement referenceDocument) throws Exception {
		mapDocumentEntitlement(source, termWrapperList, null, referenceDocument);
	}

	protected static void mapDocumentEntitlement(Base source, List<TermWrapper> termWrapperList, List<ReferenceVideoContent> videoContentList, ReferenceDocumentWithEntitlement referenceDocument) throws Exception {
		mapEntity(source, referenceDocument);
		referenceDocument.setEntitlementId(WorkflowHelper.getControlAssetId(source));
		List<ReferencePurchaseOption> referencePurchaseOptionList = new ArrayList<>();
		if (termWrapperList != null) {
			for (TermWrapper termWrapper: termWrapperList) {
				for (TermMap termMap: termWrapper.getTermMapList()) {
					for (Entry<String, Map<String, String>> currencyEntry: termWrapper.getMergedCurrencyMap().entrySet()) {
						ReferencePurchaseOption referencePurchaseOption = new ReferencePurchaseOption();
						referencePurchaseOption.setContractName(termWrapper.getTerm().getContractName());
						referencePurchaseOption.setContentTypeList(termMap.getContentTypeList());
						referencePurchaseOption.setCountry(currencyEntry.getKey());
						referencePurchaseOption.setVideoContents(getSelectedVideoContentList(videoContentList, termMap.getDeviceProfileNameList()));
						referencePurchaseOption.setEndDateTime(termWrapper.getEndDateTime());
						ReferenceOffer referenceOffer = new ReferenceOffer();
						referenceOffer.setId(termWrapper.getOffer().getUriId());
						// code by nitin
						referenceOffer.setOfferpackageid(termWrapper.getOffer().getOfferpackageid());
						referenceOffer.setOffertype(termWrapper.getOffer().getOffertype());
						referencePurchaseOption.setOffer(referenceOffer);
						referencePurchaseOption.setOfferedEntityId(WorkflowHelper.getControlAssetId(termWrapper.getOfferedEntity()));
						referencePurchaseOption.setOfferedEntityType(termWrapper.getOfferedEntity().getType());
						referencePurchaseOption.setPolicyGroupId(termMap.getPolicyGroupId());
						referencePurchaseOption.setPolicyId(termMap.getPolicyId());
						referencePurchaseOption.setPolicyType(termMap.getPolicyType());
						Map<String, Double> priceMap = new HashMap<>();
						for (Entry<String, String> priceEntry: currencyEntry.getValue().entrySet()) {
							if (priceEntry.getValue() != null) {
								try {
									Double price = Double.valueOf(priceEntry.getValue());
									priceMap.put(priceEntry.getKey(), price);
								} catch (NumberFormatException e) {
									logger.error("Unable to process price " + priceEntry.getValue(), e);
								}
							}
						}
						referencePurchaseOption.setPrice(priceMap);
						referencePurchaseOption.setStartDateTime(termWrapper.getStartDateTime());
						referencePurchaseOptionList.add(referencePurchaseOption);
					}
				}
			}
		}
		referenceDocument.setPurchaseOptions(referencePurchaseOptionList);
	}

	private static List<ReferenceVideoContent> getSelectedVideoContentList(List<ReferenceVideoContent> videoContentList, List<String> deviceProfileNameList) {
		List<ReferenceVideoContent> selectedVideoContentList = new ArrayList<>();
		if (videoContentList != null) {
			for (ReferenceVideoContent videoContent: videoContentList) {
				ReferenceVideoContent clonedVideoContent = videoContent.copy();
				Map<Device, ReferenceRendition> selectedRenditionMap = new HashMap<>();
				if (videoContent.getRenditionMap() != null) {
					for (Entry<Device, ReferenceRendition> renditionEntry: videoContent.getRenditionMap().entrySet()) {
						if (deviceProfileNameList.contains(renditionEntry.getKey().getName())) {
							selectedRenditionMap.put(renditionEntry.getKey(), renditionEntry.getValue());
						}
					}
				}
				if (!selectedRenditionMap.isEmpty()) {
					clonedVideoContent.setRenditionMap(selectedRenditionMap);
					selectedVideoContentList.add(clonedVideoContent);
				}
			}
		}
		return selectedVideoContentList;
	}

	protected static void mapDocument(Base source, ReferenceDocument referenceDocument) throws Exception {
		mapEntity(source, referenceDocument);
		referenceDocument.setIndexId(WorkflowHelper.getControlAssetId(source));
		referenceDocument.setStartDateTime(source.getStartDateTime());
		referenceDocument.setEndDateTime(source.getEndDateTime());
		referenceDocument.setLastPublishDateTime(source.getLastPublishDateTime());
	}

	static void mapEntity(Base source, ReferenceEntity referenceEntity) throws Exception {
		if (source != null && referenceEntity != null) {
			referenceEntity.setId(source.getUriId());
			referenceEntity.setProvider(source.getProvider());
		} else {
			throw new Exception("Unable to map null entities.");
		}
	}

	protected static List<ReferenceImageContent> mapImageContents(List<ImageContent> imageContentList) throws Exception {
		List<ReferenceImageContent> referenceImageContentList = new ArrayList<>();

		if (imageContentList != null) {
			for (ImageContent imageContent : imageContentList) {
				if (imageContent != null) {
					for (ImageSubcontent imageSubcontent : imageContent.getSubcontent()) {
						referenceImageContentList.add(mapImageContent(imageContent, imageSubcontent));
					}
				}
			}
		}

		return referenceImageContentList;
	}

	private static ReferenceImageContent mapImageContent(ImageContent imageContent, ImageSubcontent imageSubcontent) throws Exception {
		ReferenceImageContent referenceImageContent = new ReferenceImageContent();

		if (imageContent == null || imageSubcontent == null) {
			throw new Exception("Image content and image subcontent must not be null");
		}

		referenceImageContent.setContentType(imageContent.getContentType().toString());

		if (!isBlank(imageContent.getXResolution()) && !isBlank(imageContent.getYResolution())
				&& isNumeric(imageContent.getXResolution()) && isNumeric(imageContent.getXResolution())) {
			referenceImageContent.setXResolution(Integer.valueOf(imageContent.getXResolution()));
			referenceImageContent.setYResolution(Integer.valueOf(imageContent.getYResolution()));
		} else {
			referenceImageContent.setXResolution(imageSubcontent.getXResolution());
			referenceImageContent.setYResolution(imageSubcontent.getYResolution());
		}

		referenceImageContent.setLanguages(Arrays.asList(new String[]{imageSubcontent.getLanguage()}));
		ReferenceRendition rendition = new ReferenceRendition();
		rendition.setConsumerUrl(imageSubcontent.getConsumerUrl());
		Map<Device, ReferenceRendition> referenceRenditionMap = new HashMap<>();
		Device device = new Device();
		device.setDeviceClass("*");
		referenceRenditionMap.put(device, rendition);
		referenceImageContent.setRenditionMap(referenceRenditionMap);

		return referenceImageContent;
	}
}
