package com.irdeto.jumpstart.workflow.ingest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import com.irdeto.helper.xmlmodel.JDOMDocumentHelper;
import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.Locale;
import com.irdeto.jumpstart.domain.PolicyGroupProfile;
import com.irdeto.jumpstart.domain.PolicyProfile;
import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.TermMapping;
import com.irdeto.jumpstart.domain.TranscodeProfile;
import com.irdeto.jumpstart.domain.DeviceProfile.PackagingType;
import com.irdeto.jumpstart.domain.ingest.DeviceProfileIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EncodeProfileIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.PolicyGroupProfileIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.PolicyProfileIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.ProtectProfileIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SourceInformation;
import com.irdeto.jumpstart.domain.ingest.TermMappingIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.TranscodeProfileIngestWrapper;
import com.irdeto.jumpstart.workflow.DateHelper;
import com.irdeto.jumpstart.workflow.LocaleHelper;

/**
 * File Name: JDOMIrdetoDeviceProfileMapper.java
 *
 * Description: The Mapper class used for deviceProfiles.xml
 *
 * Developed by Tata Elxsi for Irdeto B.V.
 *
 * Creation Date: 15-Oct-2014
 *
 */
public class JDOMIrdetoDeviceProfileMapper extends AbstractVodIngestMapper implements VodIngestMapper {

	// Device Profile
	private static final String ATTRIBUTE_DEVICE_CLASS = "deviceClass";
	private static final String ATTRIBUTE_ENABLED = "enabled";
	private static final String ATTRIBUTE_DEVICE_NAME = "name";
	private static final String ATTRIBUTE_PACKAGING_TYPE = "packagingType";
	private static final String ELEMENT_ENCODE_PROFILE_NAME = "EncodeProfileName";
	private static final String ELEMENT_TRANSCODE_PROFILE_NAME = "TranscodeProfileName";
	private static final String ELEMENT_PROTECT_PROFILE_NAME = "ProtectProfileName";
	// Encode Profile
	private static final String ATTRIBUTE_LIVE_URI = "liveUri";
	private static final String ATTRIBUTE_ENCODE_NAME = "name";
	// Transcode Profile
	private static final String ATTRIBUTE_TRANSCODE_NAME= "name";
	private static final String ATTRIBUTE_TRANSCODE_FILE_PATTERN= "transcodedFilePattern";
	private static final String ATTRIBUTE_TRANSCODER_PROFILE= "transcoderProfile";
	private static final String ATTRIBUTE_TRANSCODER_URI= "transcoderUri";
	private static final String ATTRIBUTE_TRANSCODER_WF = "transcoderWorkflow";
	private static final String ATTRIBUTE_TRANSCODED_FILE_COUNT= "transcodedFileCount";
	private static final String ELEMENT_PROTECT_NAME = "ProtectProfileName";
	// Protect Profile
	private static final String ATTRIBUTE_PROTECTION_TYPE = "protectionType";
	private static final String ATTRIBUTE_PROTECT_NAME = "name";
	private static final String ATTRIBUTE_REQUIRED = "required";
	// Term Mapping
	private static final String ATTRIBUTE_CONTRACT_NAME = "contractName";
	private static final String ATTRIBUTE_POLICY_GROUP_ID = "policyGroupId";
	private static final String ATTRIBUTE_POLICY_ID = "policyId";
	private static final String ATTRIBUTE_POLICY_TYPE = "policyType";
	private static final String ATTRIBUTE_DURATION = "duration";
	private static final String ELEMENT_CONTENT_TYPE = "ContentType";
	private static final String ELEMENT_DEVICE_PROFILE = "DeviceProfile";
	// Policy Group
	 private static final String ATTRIBUTE_POLICY_GROUP_NAME = "policyGroupName";
	 private static final String ATTRIBUTE_POLICY_GROUP_DESCRIPTION = "policyGroupDescription";
	 private static final String ATTRIBUTE_LICENSE_PROFILE_REFERENCE = "licenseProfileReference";
	 private static final String ELEMENT_POLICY_GROUP = "PolicyGroup";
	 private static final String ELEMENT_POLICIES = "Policy";
	 // Policy
	 private static final String ATTRIBUTE_POLICY_NAME = "policyName";
	 private static final String ATTRIBUTE_POLICY_DESCRIPTION = "policyDescription";
	 private static final String ATTRIBUTE_ENTITLEMENT_START_MODE = "entitlementStartMode";
	 private static final String ATTRIBUTE_ENTITLEMENT_DURATION = "entitlementDuration";
	 private static final String ATTRIBUTE_REQUIRE_SUBSCRIPTION =  "requireSubscription";
	 private static final String ATTRIBUTE_SUBSCRIPTION_REFERENCE = "subscriptionReference";
	 private static final String ATTRIBUTE_REQUIRE_AUTHENTICATION = "requireAuthentication";
	 private static final String ATTRIBUTE_SUBSCRIPTION_BILLING_INTERVAL = "subscriptionBillingInterval";
	 private static final String ATTRIBUTE_SUBSCRIPTION_GROUP_ID = "subscriptionGroupId";
	 private static final String ATTRIBUTE_SUBSCRIPTION_ID = "subscriptionId";
	 private static final String ATTRIBUTE_SUBSCRIPTION_RELEASE_DATE = "subscriptionReleaseDate";
	 private static final String ATTRIBUTE_SUBSCRIPTION_RELEASE_END = "subscriptionReleaseEnd";
	 private static final String ATTRIBUTE_SUBSCRIPTION_MINIMUM_BILLING_INTERVAL = "subscriptionMinimumBillingInterval";
	 private static final String ATTRIBUTE_SUBSCRIPTION_BILLING_END_DATE = "subscriptionBillingEndDate";
	 private static final String ATTRIBUTE_NUMBER_OF_DEVICES = "numberOfDevices";
	 private static final String ATTRIBUTE_PRODUCT_TAX_TYPE = "productTaxType";
	 private static final String ATTRIBUTE_BILL_IMMEDIATELY = "billImmediately";
	 private static final String ATTRIBUTE_SYNDICATE_ACCOUNT_ID = "syndicateAccountId";
	 private static final String ELEMENT_PRICE_TABLE = "CurrencyMap";
	 private static final String ELEMENT_POLICY = "Policy";
	 private static final String ATTRIBUTE_LANG = "lang";
	 private static final String ATTRIBUTE_COUNTRY = "country";
	 private static final String ATTRIBUTE_CURRENCY = "currency";
	 private static final String ATTRIBUTE_AMOUNT = "amount";


	@Override
	@JsonIgnore
	public boolean isApplicableMapper() {
		return StringUtils.contains(getInputString(), "<Profile>");
	}

	@Override
	@JsonIgnore
	public String[] getSchemaFileNames() {
		return new String[] {"deviceProfiles.xsd"};
	}

	@Override
	@JsonIgnore
	public boolean isSchemaValidationEnabled() {
		return true;
	}

	/**
	 * This method read Device Profile, Term Mappings , Encode Profile , Transcode Profile , Protect Profile and their relationships
	 * from the xml and set to the respective domain entities which forms the wrapper object for ingestion
	 *
	 */
	@Override
	public List<EntityIngestWrapper<?>> findEntities() throws Exception {
		List<EntityIngestWrapper<?>> ingestWrapperList = new ArrayList<>();
		ingestWrapperList.addAll(findDeviceProfile(getInputString()));
		ingestWrapperList.addAll(findEncodeProfile(getInputString()));
		ingestWrapperList.addAll(findProtectProfile(getInputString()));
		ingestWrapperList.addAll(findTermMapping(getInputString()));
		ingestWrapperList.addAll(findTranscodeProfile(getInputString()));
		ingestWrapperList.addAll(findPolicyGroup(getInputString()));
		ingestWrapperList.addAll(findPolicy(getInputString()));
		return ingestWrapperList;
	}

	protected List<DeviceProfileIngestWrapper> findDeviceProfile(String xmlInput)
			throws Exception {
		List<DeviceProfileIngestWrapper> ingestWrapperList = new ArrayList<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> contentGroupPath = xPathFactory.compile("/Profile/DeviceProfile", Filters.element(), null);
		List<Element> deviceProfileElementList = contentGroupPath.evaluate(document);
		for (Element deviceProfileElement: deviceProfileElementList) {
			DeviceProfileIngestWrapper ingestWrapper = new DeviceProfileIngestWrapper();
			DeviceProfile deviceProfile = new DeviceProfile();
			ingestWrapper.setEntity(deviceProfile);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

			String deviceClass  = deviceProfileElement.getAttributeValue(ATTRIBUTE_DEVICE_CLASS);
			String isEnabled = deviceProfileElement.getAttributeValue(ATTRIBUTE_ENABLED);
			String deviceName = deviceProfileElement.getAttributeValue(ATTRIBUTE_DEVICE_NAME);
			String packagingType = deviceProfileElement.getAttributeValue(ATTRIBUTE_PACKAGING_TYPE);
			if (StringUtils.isBlank(packagingType)) {
				packagingType = DeviceProfile.PackagingType.NA.toString();
			}

			deviceProfile.setDeviceClass(deviceClass);
			deviceProfile.setEnabled(BooleanUtils.toBoolean(isEnabled));
			deviceProfile.setName(deviceName);
			deviceProfile.setPackagingType(PackagingType.fromValue(packagingType));

			List<Element> encodeProfileElementList = deviceProfileElement.getChildren(ELEMENT_ENCODE_PROFILE_NAME);
			List<String> encodeProfileElementListValues = getProfileElementListValues(encodeProfileElementList);
			ingestWrapper.addEncodeProfileNameList(encodeProfileElementListValues);

			List<Element> transcodeProfileElementList = deviceProfileElement.getChildren(ELEMENT_TRANSCODE_PROFILE_NAME);
			List<String> transcodeProfileElementListValues = getProfileElementListValues(transcodeProfileElementList);
			ingestWrapper.addTranscodeProfileNameList(transcodeProfileElementListValues);

			List<Element> protectProfileElementList = deviceProfileElement.getChildren(ELEMENT_PROTECT_PROFILE_NAME);
			List<String> protectProfileElementListValues = getProfileElementListValues(protectProfileElementList);
			ingestWrapper.addProtectProfileNameList(protectProfileElementListValues);

			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	@JsonIgnore
	 private List<String> getProfileElementListValues(List<Element> elementList) {
	  List<String> profileElementListValues = new ArrayList<>();
	  for (Element element: elementList) {
	   String value = element.getValue();
	   profileElementListValues.add(value);
	  }
	  return profileElementListValues;
	 }

	protected List<EncodeProfileIngestWrapper> findEncodeProfile(String xmlInput)
			throws Exception {
		List<EncodeProfileIngestWrapper> ingestWrapperList = new ArrayList<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> contentGroupPath = xPathFactory.compile("/Profile/EncodeProfile", Filters.element(), null);
		List<Element> encodeProfileElementList = contentGroupPath.evaluate(document);
		for (Element encodeProfileElement: encodeProfileElementList) {
			EncodeProfileIngestWrapper ingestWrapper = new EncodeProfileIngestWrapper();
			EncodeProfile encodeProfile = new EncodeProfile();
			ingestWrapper.setEntity(encodeProfile);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

			encodeProfile.setLiveUri(encodeProfileElement.getAttributeValue(ATTRIBUTE_LIVE_URI));
			encodeProfile.setName(encodeProfileElement.getAttributeValue(ATTRIBUTE_ENCODE_NAME));
			encodeProfile.setProtectionType(EncodeProfile.ProtectionType.fromValue(encodeProfileElement.getAttributeValue(ATTRIBUTE_PROTECTION_TYPE)));
			ingestWrapperList.add(ingestWrapper);
		}

		return ingestWrapperList;
	}

	protected List<ProtectProfileIngestWrapper> findProtectProfile(String xmlInput)
			throws Exception {
		List<ProtectProfileIngestWrapper> ingestWrapperList = new ArrayList<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> contentGroupPath = xPathFactory.compile("/Profile/ProtectProfile", Filters.element(), null);
		List<Element> protectProfileElementList = contentGroupPath.evaluate(document);
		for (Element protectProfileElement: protectProfileElementList) {
			ProtectProfileIngestWrapper ingestWrapper = new ProtectProfileIngestWrapper();
			ProtectProfile protectProfile = new ProtectProfile();
			ingestWrapper.setEntity(protectProfile);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

			String protectionType = protectProfileElement.getAttributeValue(ATTRIBUTE_PROTECTION_TYPE);
			protectProfile.setProtectionType(ProtectProfile.ProtectionType.fromValue(protectionType));

			protectProfile.setName(protectProfileElement.getAttributeValue(ATTRIBUTE_PROTECT_NAME));

			Attribute requiredAttribute = protectProfileElement.getAttribute(ATTRIBUTE_REQUIRED);
			protectProfile.setRequired(requiredAttribute.getBooleanValue());

			ingestWrapperList.add(ingestWrapper);
		}

		return ingestWrapperList;
	}

	protected List<TermMappingIngestWrapper> findTermMapping(String xmlInput)
			throws Exception {
		List<TermMappingIngestWrapper> ingestWrapperList = new ArrayList<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> contentGroupPath = xPathFactory.compile("/Profile/TermMapping", Filters.element(), null);
		List<Element> termMappingElementList = contentGroupPath.evaluate(document);

		for (Element termMappingElement: termMappingElementList) {
			TermMappingIngestWrapper ingestWrapper = new TermMappingIngestWrapper();
			TermMapping termMapping = new TermMapping();
			ingestWrapper.setEntity(termMapping);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

			termMapping.setContractName(termMappingElement.getAttributeValue(ATTRIBUTE_CONTRACT_NAME));
			termMapping.setPolicyGroupId(Integer.valueOf(termMappingElement.getAttributeValue(ATTRIBUTE_POLICY_GROUP_ID)));
			termMapping.setPolicyId(Integer.valueOf(termMappingElement.getAttributeValue(ATTRIBUTE_POLICY_ID)));
			if(null != termMappingElement.getAttributeValue(ATTRIBUTE_DURATION)){
			termMapping.setDuration(Integer.valueOf(termMappingElement.getAttributeValue(ATTRIBUTE_DURATION)));
			}

			String policyType = termMappingElement.getAttributeValue(ATTRIBUTE_POLICY_TYPE);
			termMapping.setPolicyType(TermMapping.PolicyType.fromValue(policyType));

			  List<Element> termMappingContentTypeList = termMappingElement.getChildren(ELEMENT_CONTENT_TYPE);
			  List<ContentType> contentTypeList = new ArrayList<>();
			  for (Element element: termMappingContentTypeList) {
			   String value = element.getValue();
			   contentTypeList.add(ContentType.fromValue(value));
			  }
			  termMapping.setContentType(contentTypeList);

			  List<Element> termMappingDeviceProfileList = termMappingElement.getChildren(ELEMENT_DEVICE_PROFILE);
			  List<String> termMappingDevicetListValues = getProfileElementListValues(termMappingDeviceProfileList);
			  ingestWrapper.addDeviceProfileNameList(termMappingDevicetListValues);

			  	// PolicyGroup
			     List<Element> policyGroupList = termMappingElement.getChildren(ELEMENT_POLICY_GROUP);
			     List<String> policyGroupNameList = getProfileElementListValues(policyGroupList);
			     ingestWrapper.addPolicyGroupNameList(policyGroupNameList);

			     //Policy
			     List<Element> policyList = termMappingElement.getChildren(ELEMENT_POLICY);
			     List<String> policyNameList = getProfileElementListValues(policyList);
			     ingestWrapper.addPolicyNameList(policyNameList);

			ingestWrapperList.add(ingestWrapper);
		}

		return ingestWrapperList;
	}

	protected List<PolicyGroupProfileIngestWrapper> findPolicyGroup(String xmlInput) throws Exception{
		  List<PolicyGroupProfileIngestWrapper> ingestWrapperList = new ArrayList<>();
		  Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		  XPathFactory xPathFactory = XPathFactory.instance();
		  XPathExpression<Element> contentGroupPath = xPathFactory.compile("/Profile/PolicyGroup", Filters.element(), null);
		  List<Element> policyGroupElementList = contentGroupPath.evaluate(document);

		  for(Element policyGroupElement : policyGroupElementList){
		   PolicyGroupProfileIngestWrapper ingestWrapper = new PolicyGroupProfileIngestWrapper();
		   PolicyGroupProfile policyGroup = new PolicyGroupProfile();
		   ingestWrapper.setEntity(policyGroup);
		   ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

		   policyGroup.setPolicyGroupName(policyGroupElement.getAttributeValue(ATTRIBUTE_POLICY_GROUP_NAME));
		   policyGroup.setPolicyGroupDescription(policyGroupElement.getAttributeValue(ATTRIBUTE_POLICY_GROUP_DESCRIPTION));
		   policyGroup.setLicenseProfileReference(Integer.valueOf(policyGroupElement.getAttributeValue(ATTRIBUTE_LICENSE_PROFILE_REFERENCE)));

		   List<Element> policyElementList = policyGroupElement.getChildren(ELEMENT_POLICIES);
		   List<String> policyNameList = getProfileElementListValues(policyElementList);
		   ingestWrapper.addPoliciesNameList(policyNameList);

		   ingestWrapperList.add(ingestWrapper);
		  }

		  return ingestWrapperList;
		 }

	 protected List<PolicyProfileIngestWrapper> findPolicy(String xmlInput) throws Exception{
		  List<PolicyProfileIngestWrapper> ingestWrapperList = new ArrayList<>();
		  Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		  XPathFactory xPathFactory = XPathFactory.instance();
		  XPathExpression<Element> contentGroupPath = xPathFactory.compile("/Profile/Policy", Filters.element(), null);
		  List<Element> policyElementList = contentGroupPath.evaluate(document);

		  for(Element policyElement : policyElementList){
		   PolicyProfileIngestWrapper ingestWrapper = new PolicyProfileIngestWrapper();
		   PolicyProfile policy = new PolicyProfile();
		   ingestWrapper.setEntity(policy);
		   ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

		   policy.setPolicyName(policyElement.getAttributeValue(ATTRIBUTE_POLICY_NAME));
		   policy.setPolicyDescription(policyElement.getAttributeValue(ATTRIBUTE_POLICY_DESCRIPTION));
		   policy.setPolicyType(PolicyProfile.PolicyType.fromValue(policyElement.getAttributeValue(ATTRIBUTE_POLICY_TYPE)));
		   policy.setEntitlementStartMode(policyElement.getAttributeValue(ATTRIBUTE_ENTITLEMENT_START_MODE));
		   policy.setEntitlementDuration(policyElement.getAttributeValue(ATTRIBUTE_ENTITLEMENT_DURATION));
		   policy.setRequireSubscription(Boolean.valueOf(policyElement.getAttributeValue(ATTRIBUTE_REQUIRE_SUBSCRIPTION)));
		   policy.setSubscriptionReference(Integer.valueOf(policyElement.getAttributeValue(ATTRIBUTE_SUBSCRIPTION_REFERENCE)));
		   policy.setLicenseProfileReference(Integer.valueOf(policyElement.getAttributeValue(ATTRIBUTE_LICENSE_PROFILE_REFERENCE)));
		   policy.setRequireAuthentication(Boolean.valueOf(policyElement.getAttributeValue(ATTRIBUTE_REQUIRE_AUTHENTICATION)));
		   policy.setSubscriptionBillingInterval(Integer.valueOf(policyElement.getAttributeValue(ATTRIBUTE_SUBSCRIPTION_BILLING_INTERVAL)));
		   if(null != policyElement.getAttributeValue(ATTRIBUTE_SUBSCRIPTION_GROUP_ID))
		   {
		   policy.setSubscriptionGroupId(Integer.valueOf(policyElement.getAttributeValue(ATTRIBUTE_SUBSCRIPTION_GROUP_ID)));
		   }
		   policy.setSubscriptionId(Integer.valueOf(policyElement.getAttributeValue(ATTRIBUTE_SUBSCRIPTION_ID)));
		   policy.setSubscriptionReleaseDate(DateHelper.convertXMLTVDate(policyElement.getAttributeValue(ATTRIBUTE_SUBSCRIPTION_RELEASE_DATE)));
		   policy.setSubscriptionReleaseEnd(DateHelper.convertXMLTVDate(policyElement.getAttributeValue(ATTRIBUTE_SUBSCRIPTION_RELEASE_END)));
		   policy.setSubscriptionMinimumBillingInterval(Integer.valueOf(policyElement.getAttributeValue(ATTRIBUTE_SUBSCRIPTION_MINIMUM_BILLING_INTERVAL)));
		   policy.setSubscriptionBillingEndDate(DateHelper.convertXMLTVDate(policyElement.getAttributeValue(ATTRIBUTE_SUBSCRIPTION_BILLING_END_DATE)));
		   policy.setNumberOfDevices(Integer.valueOf(policyElement.getAttributeValue(ATTRIBUTE_NUMBER_OF_DEVICES)));
		   policy.setProductTaxType(policyElement.getAttributeValue(ATTRIBUTE_PRODUCT_TAX_TYPE));
		   policy.setBillImmediately(Boolean.valueOf(policyElement.getAttributeValue(ATTRIBUTE_BILL_IMMEDIATELY)));
		   policy.setSyndicateAccountId(policyElement.getAttributeValue(ATTRIBUTE_SYNDICATE_ACCOUNT_ID));

		   // Price Table
		   List<Element> priceElementList = policyElement.getChildren(ELEMENT_PRICE_TABLE);
		   Map<String, Map<String, String>> countryCurrencyMap = new HashMap<>();
			for (Element priceElement: priceElementList) {
				String country = priceElement.getAttributeValue(ATTRIBUTE_COUNTRY);
				String currency = priceElement.getAttributeValue(ATTRIBUTE_CURRENCY);
				String amount = priceElement.getAttributeValue(ATTRIBUTE_AMOUNT);
				Map<String,String> currencyMap = countryCurrencyMap.get(country);
				if (currencyMap == null) {
					currencyMap = new HashMap<>();
					countryCurrencyMap.put(country, currencyMap);
				}
				currencyMap.put(currency, amount);
			}

		   policy.setPriceTable(countryCurrencyMap);

		   ingestWrapperList.add(ingestWrapper);
		  }

		  return ingestWrapperList;
		 }

	protected List<TranscodeProfileIngestWrapper> findTranscodeProfile(String xmlInput)
			throws Exception {
		List<TranscodeProfileIngestWrapper> ingestWrapperList = new ArrayList<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> contentGroupPath = xPathFactory.compile("/Profile/TranscodeProfile", Filters.element(), null);
		List<Element> transcodeProfileElementList = contentGroupPath.evaluate(document);
		for (Element transcodeProfileElement: transcodeProfileElementList) {
			TranscodeProfileIngestWrapper ingestWrapper = new TranscodeProfileIngestWrapper();
			TranscodeProfile transcodeProfile = new TranscodeProfile();
			ingestWrapper.setEntity(transcodeProfile);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

			transcodeProfile.setName(transcodeProfileElement.getAttributeValue(ATTRIBUTE_TRANSCODE_NAME));
			transcodeProfile.setTranscodedFilePattern(transcodeProfileElement.getAttributeValue(ATTRIBUTE_TRANSCODE_FILE_PATTERN));
			transcodeProfile.setTranscoderUri(transcodeProfileElement.getAttributeValue(ATTRIBUTE_TRANSCODER_URI));
			transcodeProfile.setTranscoderProfile(transcodeProfileElement.getAttributeValue(ATTRIBUTE_TRANSCODER_PROFILE));

			if(transcodeProfileElement.getAttributeValue(ATTRIBUTE_TRANSCODER_WF).equals("TranscodeElementalCloud"))
			transcodeProfile.setTranscoderWorkflow(TranscodeProfile.TranscoderWorkflow.TRANSCODE_ELEMENTAL_CLOUD);

			transcodeProfile.setTranscodedFileCount(Integer.valueOf(transcodeProfileElement.getAttributeValue(ATTRIBUTE_TRANSCODED_FILE_COUNT)));

			List<Element> protectNameElementList = transcodeProfileElement.getChildren(ELEMENT_PROTECT_NAME);
			List<String> protectNameElementListValues = getProfileElementListValues(protectNameElementList);
			ingestWrapper.addProtectProfileNameList(protectNameElementListValues);

			ingestWrapperList.add(ingestWrapper);
		}

		return ingestWrapperList;
	}


	@JsonIgnore
	private Locale getLocaleValues(List<Element> elementList) {
		Locale locale = new Locale();
		for (Element element: elementList) {
			String language = element.getAttributeValue(ATTRIBUTE_LANG, LocaleHelper.DEFAULT_LANGUAGE_NAME);
			String value = element.getValue();
			LocaleHelper.setStringValueForLanguage(locale, language, value);
		}
		return locale;
	}
}

// End JDOMIrdetoDeviceProfileMapper
