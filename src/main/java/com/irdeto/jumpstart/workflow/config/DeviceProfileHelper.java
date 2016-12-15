package com.irdeto.jumpstart.workflow.config;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.BooleanUtils;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.BaseProtection.ProtectionType;
import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.PolicyGroupProfile;
import com.irdeto.jumpstart.domain.PolicyProfile;
import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.TermMapping;
import com.irdeto.jumpstart.domain.TermMapping.PolicyType;
import com.irdeto.jumpstart.domain.TranscodeProfile;
import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.config.Encode;
import com.irdeto.jumpstart.domain.config.Policy;
import com.irdeto.jumpstart.domain.config.PolicyGroup;
import com.irdeto.jumpstart.domain.config.Profile;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.config.Transcode;
import com.irdeto.jumpstart.domain.ingest.MaintainEntities;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.jbpm.knowledge.ClassManagerImpl;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public class DeviceProfileHelper extends WorkflowHelper {
	private static final String ATTRIBUTE_NAME_CONTRACT_NAME = "contractName";
	private static final String ATTRIBUTE_NAME_NAME = "name";
	private static final String MAINTAIN_PROTECT_PROFILE_ENTITIES_KEY = "maintainProtectProfileEntities";
	private static final String MAINTAIN_ENCODE_PROFILE_ENTITIES_KEY = "maintainEncodeProfileEntities";
	private static final String MAINTAIN_TRANSCODE_PROFILE_ENTITIES_KEY = "maintainTranscodeProfileEntities";
	private static final String MAINTAIN_DEVICE_PROFILE_ENTITIES_KEY = "maintainDeviceProfileEntities";
	private static final String MAINTAIN_TERM_MAPPING_ENTITIES_KEY = "maintainTermMappingEntities";
	private static final String TERM_MAPPING_LIST_KEY = "termMappingList";
	private static final String DEVICE_PROFILE_LIST_KEY = "deviceProfileList";
	private static final String TRANSCODE_PROFILE_LIST_KEY = "transcodeProfileList";
	private static final String DEVICE_PROFILE_NAME_LIST_KEY = "deviceProfileNameList";
	private static final String PROTECT_PROFILE_NAME_LIST_KEY = "protectProfileNameList";
	private static final String ENCODE_PROFILE_NAME_LIST_KEY = "encodeProfileNameList";
	private static final String TRANSCODE_PROFILE_NAME_LIST_KEY = "transcodeProfileNameList";
	private static final String PROFILE_KEY = "profile";
	private static final String DEVICE_PROFILE_KEY = "deviceProfile";
	private static final String XML_KEY = "xml";

	private static final Logger logger = LoggerFactory.getLogger(DeviceProfileHelper.class);

	public static void addDeviceToList(ProcessContext kcontext) {
		Profile deviceList = (Profile) kcontext.getVariable(PROFILE_KEY);
		DeviceProfile deviceProfile = (DeviceProfile) kcontext.getVariable(DEVICE_PROFILE_KEY);
		@SuppressWarnings("unchecked")
		List<String> transcodeProfileNameList = (List<String>) kcontext.getVariable(TRANSCODE_PROFILE_NAME_LIST_KEY);
		@SuppressWarnings("unchecked")
		List<String> protectProfileNameList = (List<String>) kcontext.getVariable(PROTECT_PROFILE_NAME_LIST_KEY);
		@SuppressWarnings("unchecked")
		List<String> encodeProfileNameList = (List<String>) kcontext.getVariable(ENCODE_PROFILE_NAME_LIST_KEY);
		Device device = convertDeviceProfileToDevice(deviceProfile);
		device.setTranscodeProfileNameList(transcodeProfileNameList);
		device.setProtectProfileNameList(protectProfileNameList);
		device.setEncodeProfileNameList(encodeProfileNameList);
		deviceList.getDeviceList().add(device);
	}

	public static Device convertDeviceProfileToDevice(
			DeviceProfile deviceProfile) {
		Device device = new Device();
		device.setDeviceProfileId(deviceProfile.getId());
		device.setDeviceClass(deviceProfile.getDeviceClass());
		device.setName(deviceProfile.getName());
		device.setEnabled(BooleanUtils.isTrue(deviceProfile.getEnabled()));
		device.setPackagingType(deviceProfile.getPackagingType().toString());
		return device;
	}

	public static TermMap convertTermMappingToTermMap(TermMapping termMapping) {
		TermMap termMap = new TermMap();
		termMap.setTermMappingId(termMapping.getId());
		termMap.setContractName(termMapping.getContractName());
		termMap.setPolicyGroupId(termMapping.getPolicyGroupId());
		termMap.setPolicyId(termMapping.getPolicyId());
		termMap.setDuration(termMapping.getDuration());
		if (termMapping.getPolicyType() != null) {
			termMap.setPolicyType(termMapping.getPolicyType().toString());
		}
		for (ContentType contentType : termMapping.getContentType()) {
			termMap.getContentTypeList().add(contentType.toString());
		}
		return termMap;
	}

	public static PolicyGroup convertPolicyGroupProfileToPolicyGroup(PolicyGroupProfile policyGroupProfile) {
		PolicyGroup policyGroup = new PolicyGroup();
		policyGroup.setPolicyId(policyGroupProfile.getId());
		policyGroup.setPolicyGroupName(policyGroupProfile.getPolicyGroupName());
		policyGroup.setPolicyGroupDescription(policyGroupProfile.getPolicyGroupDescription());
		policyGroup.setLicenseProfileReference(policyGroupProfile.getLicenseProfileReference());
		return policyGroup;
	}

	public static Policy convertPolicyProfileToPolicy(PolicyProfile policyProfile) {
		Policy policy = new Policy();
		policy.setPolicyId(policyProfile.getId());
		policy.setPolicyName(policyProfile.getPolicyName());
		policy.setPolicyDescription(policyProfile.getPolicyDescription());
		policy.setPolicyType(policyProfile.getType());
		policy.setEntitlementDuration(policyProfile.getEntitlementDuration());
		policy.setEntitlementStartMode(policyProfile.getEntitlementStartMode());
		policy.setRequireSubscription(policyProfile.getRequireSubscription());
		policy.setSubscriptionReference(policyProfile.getSubscriptionReference());
		policy.setLicenseProfileReference(policyProfile.getLicenseProfileReference());
		policy.setRequireAuthentication(policyProfile.getRequireAuthentication());
		policy.setPriceTable(policyProfile.getPriceTable());
		policy.setSubscriptionBillingInterval(policyProfile.getSubscriptionBillingInterval());
		policy.setSubscriptionGroupId(policyProfile.getSubscriptionGroupId());
		policy.setSubscriptionId(policyProfile.getSubscriptionId());
		policy.setSubscriptionReleaseDate(policyProfile.getSubscriptionReleaseDate());
		policy.setSubscriptionReleaseEnd(policyProfile.getSubscriptionReleaseEnd());
		policy.setSubscriptionMinimumBillingInterval(policyProfile.getSubscriptionMinimumBillingInterval());
		policy.setSubscriptionBillingEndDate(policyProfile.getSubscriptionBillingEndDate());
		policy.setNumberOfDevices(policyProfile.getNumberOfDevices());
		policy.setProductTaxType(policyProfile.getProductTaxType());
		policy.setBillImmediately(policyProfile.getBillImmediately());
		policy.setSyndicateAccountId(policyProfile.getSyndicateAccountId());

		return policy;
	}

	public static Encode convertEncodeProfileToEncode(
			EncodeProfile encodeProfile) {
		Encode encode = new Encode();
		encode.setEncodeProfileId(encodeProfile.getId());
		encode.setName(encodeProfile.getName());
		encode.setLiveUri(encodeProfile.getLiveUri());
		encode.setProtectionType(encodeProfile.getProtectionType().toString());
		return encode;
	}

	public static Transcode convertTranscodeProfileToTranscode(TranscodeProfile transcodeProfile) {
		Transcode transcode = new Transcode();
		transcode.setTranscodeProfileId(transcodeProfile.getId());
		transcode.setName(transcodeProfile.getName());
		transcode.setTranscodedFilePattern(transcodeProfile.getTranscodedFilePattern());
		transcode.setTranscoderProfile(transcodeProfile.getTranscoderProfile());
		transcode.setTranscoderUri(transcodeProfile.getTranscoderUri());
		transcode.setTranscoderWorkflow(transcodeProfile.getTranscoderWorkflow().toString());
		transcode.setTranscodedFileCount(transcodeProfile.getTranscodedFileCount());
		return transcode;
	}

	public static Protect convertProtectProfileToProtect(
			ProtectProfile protectProfile) {
		Protect protect = new Protect();
		protect.setProtectProfileId(protectProfile.getId());
		protect.setName(protectProfile.getName());
		protect.setProtectionType(protectProfile.getProtectionType().toString());
		protect.setRequired(BooleanUtils.isTrue(protectProfile.getRequired()));
		return protect;
	}

	public static List<DeviceProfile> getDeviceProfileList(MM8Response response) {
		return getEntityList(DeviceProfile.class, response);
	}

	public static DeviceProfile getDeviceProfileEntity(MM8Response response) {
		return getEntity(DeviceProfile.class, response);
	}

	public static String getImageCdnPrefix() {
		return getCdnUrlPrefixHLS();
	}

	public static String getCdnUrlPrefixHLS() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CDN_HLS_PREFIX);
		} catch (PropertyException e) {
		}
		return null;
	}

	public static String getLicenseServer() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_LICENSE_SERVER_KEY);
		} catch (PropertyException e) {
		}
		return null;
	}

	public static String getCrmId() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CONTROL_CRM_ID_KEY);
		} catch (PropertyException e) {
		}
		return null;
	}

	public static String getCdnUrlPrefixIIS() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CDN_IIS_PREFIX);
		} catch (PropertyException e) {
		}
		return null;
	}

	public static String getCdnUrlPrefixUS() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CDN_US_PREFIX);
		} catch (PropertyException e) {
		}
		return null;
	}

	@SuppressWarnings("resource")
	public static void readDefaultDeviceProfile(ProcessContext kcontext) {
		logger.debug("Fail to read deviceProfile.xml in folder config.data.dir. Instead, read from default profile from jar");
		ClassManager classManager = BeanUtil.getBean(ClassManagerImpl.class);
		InputStream inputStream = classManager.getClassLoader().getResourceAsStream("data/deviceProfiles.xml");
		String schemaString = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
		kcontext.setVariable(XML_KEY, schemaString);
	}

	public static void getEncodeProfileNameList(ProcessContext kcontext) {
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<EncodeProfile> encodeProfileList = getEntityList(EncodeProfile.class, response);
		List<String> encodeProfileNameList = new ArrayList<>();
		for (EncodeProfile encodeProfile : encodeProfileList) {
			encodeProfileNameList.add(encodeProfile.getName());
		}
		kcontext.setVariable(ENCODE_PROFILE_NAME_LIST_KEY, encodeProfileNameList);
	}

	public static void getTranscodeProfileNameList(ProcessContext kcontext) {
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<TranscodeProfile> transcodeProfileList = getEntityList(TranscodeProfile.class, response);
		List<String> transcodeProfileNameList = new ArrayList<>();
		for (TranscodeProfile transcodeProfile : transcodeProfileList) {
			transcodeProfileNameList.add(transcodeProfile.getName());
		}
		kcontext.setVariable(TRANSCODE_PROFILE_NAME_LIST_KEY, transcodeProfileNameList);
	}

	public static void getDeviceProfileNameList(ProcessContext kcontext) {
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<DeviceProfile> deviceProfileList = getDeviceProfileList(response);
		List<String> deviceProfileNameList = new ArrayList<>();
		for (DeviceProfile deviceProfile : deviceProfileList) {
			deviceProfileNameList.add(deviceProfile.getName());
		}
		kcontext.setVariable(DEVICE_PROFILE_NAME_LIST_KEY, deviceProfileNameList);
	}

	public static void getProtectProfileNameList(ProcessContext kcontext) {
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<ProtectProfile> protectProfileList = getEntityList(ProtectProfile.class, response);
		List<String> protectProfileNameList = new ArrayList<>();
		for (ProtectProfile protectProfile : protectProfileList) {
			protectProfileNameList.add(protectProfile.getName());
		}
		kcontext.setVariable(PROTECT_PROFILE_NAME_LIST_KEY, protectProfileNameList);
	}

	public static void setupMaintain(ProcessContext kcontext) {
		Profile profile = (Profile) kcontext.getVariable(PROFILE_KEY);
		List<TermMapping> termMappingList = convertTermMapListToTermMappingList(profile.getTermMapList());
		kcontext.setVariable(MAINTAIN_TERM_MAPPING_ENTITIES_KEY, new MaintainEntities(TermMapping.class, ATTRIBUTE_NAME_CONTRACT_NAME, termMappingList));
		List<DeviceProfile> deviceProfileList = convertDeviceToDeviceProfileList(profile.getDeviceList());
		kcontext.setVariable(MAINTAIN_DEVICE_PROFILE_ENTITIES_KEY, new MaintainEntities(DeviceProfile.class, ATTRIBUTE_NAME_NAME, deviceProfileList));
		List<EncodeProfile> encodeProfileList = convertEncodeListToEncodeProfileList(profile.getEncodeList());
		kcontext.setVariable(MAINTAIN_ENCODE_PROFILE_ENTITIES_KEY, new MaintainEntities(EncodeProfile.class, ATTRIBUTE_NAME_NAME, encodeProfileList));
		List<TranscodeProfile> transcodeProfileList = convertTranscodeListToTranscodeProfileList(profile.getTranscodeList());
		kcontext.setVariable(MAINTAIN_TRANSCODE_PROFILE_ENTITIES_KEY, new MaintainEntities(TranscodeProfile.class, ATTRIBUTE_NAME_NAME, transcodeProfileList));
		List<ProtectProfile> protectProfileList = convertProtectListToProtectProfileList(profile.getProtectList());
		kcontext.setVariable(MAINTAIN_PROTECT_PROFILE_ENTITIES_KEY, new MaintainEntities(ProtectProfile.class, ATTRIBUTE_NAME_NAME, protectProfileList));
	}

	private static List<ProtectProfile> convertProtectListToProtectProfileList(
			List<Protect> protectList) {
		List<ProtectProfile> protectProfileList = new ArrayList<>();
		for (Protect profile : protectList) {
			ProtectProfile protectProfile = new ProtectProfile();
			protectProfile.setProtectionType(ProtectionType.fromValue(profile.getProtectionType()));
			protectProfile.setName(profile.getName());
			protectProfile.setRequired(BooleanUtils.isTrue(profile.getRequired()));
			protectProfileList.add(protectProfile);
		}
		return protectProfileList;
	}

	private static List<EncodeProfile> convertEncodeListToEncodeProfileList(
			List<Encode> encodeList) {
		List<EncodeProfile> encodeProfileList = new ArrayList<>();
		for (Encode encode : encodeList) {
			EncodeProfile encodeProfile = new EncodeProfile();
			encodeProfile.setName(encode.getName());
			encodeProfile.setLiveUri(encode.getLiveUri());
			encodeProfile.setProtectionType(EncodeProfile.ProtectionType.fromValue(encode.getProtectionType()));
			encodeProfileList.add(encodeProfile);
		}
		return encodeProfileList;
	}

	private static List<TranscodeProfile> convertTranscodeListToTranscodeProfileList(
			List<Transcode> transcodeList) {
		List<TranscodeProfile> transcodeProfileList = new ArrayList<>();
		for (Transcode transcode : transcodeList) {
			TranscodeProfile transcodeProfile = new TranscodeProfile();
			transcodeProfile.setName(transcode.getName());
			transcodeProfile.setTranscodedFilePattern(transcode.getTranscodedFilePattern());
			transcodeProfile.setTranscoderProfile(transcode.getTranscoderProfile());
			transcodeProfile.setTranscoderUri(transcode.getTranscoderUri());
			transcodeProfile.setTranscoderWorkflow(TranscodeProfile.TranscoderWorkflow.fromValue(transcode.getTranscoderWorkflow()));
			transcodeProfile.setTranscodedFileCount(transcode.getTranscodedFileCount());
			transcodeProfileList.add(transcodeProfile);
		}
		return transcodeProfileList;
	}

	private static List<DeviceProfile> convertDeviceToDeviceProfileList(
			List<Device> deviceList) {
		List<DeviceProfile> deviceProfileList = new ArrayList<>();
		for (Device device : deviceList) {
			DeviceProfile deviceProfile = new DeviceProfile();
			deviceProfile.setDeviceClass(device.getDeviceClass());
			deviceProfile.setEnabled(BooleanUtils.isTrue(device.getEnabled()));
			deviceProfile.setName(device.getName());
			deviceProfile.setPackagingType(DeviceProfile.PackagingType.fromValue(device.getPackagingType()));
			deviceProfileList.add(deviceProfile);
		}
		return deviceProfileList;
	}

	private static List<TermMapping> convertTermMapListToTermMappingList(
			List<TermMap> termMapList) {
		List<TermMapping> termMappingList = new ArrayList<>();
		for (TermMap termMap : termMapList) {
			TermMapping termMapping = new TermMapping();
			termMapping.setContractName(termMap.getContractName());
			termMapping.setPolicyId(termMap.getPolicyId());
			termMapping.setPolicyGroupId(termMap.getPolicyGroupId());
			termMapping.setDuration(termMap.getDuration());
			if (termMap.getPolicyType() != null) {
				termMapping.setPolicyType(PolicyType.fromValue(termMap.getPolicyType()));
			}
			for (String contentType : termMap.getContentTypeList()) {
				termMapping.getContentType().add(ContentType.fromValue(contentType));
			}
			termMappingList.add(termMapping);
		}
		return termMappingList;
	}

	public static void setupTermMappingListIterator(ProcessContext kcontext) {
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<TermMapping> termMappingList = getEntityList(TermMapping.class, response);
		kcontext.setVariable(TERM_MAPPING_LIST_KEY, termMappingList);
	}

	public static void setupDeviceProfileListIterator(ProcessContext kcontext) {
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<DeviceProfile> deviceProfileList = getDeviceProfileList(response);
		kcontext.setVariable(DEVICE_PROFILE_LIST_KEY, deviceProfileList);
	}

	public static void setupTranscodeProfileListIterator(ProcessContext kcontext) {
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<TranscodeProfile> transcodeProfileList = getEntityList(TranscodeProfile.class, response);
		kcontext.setVariable(TRANSCODE_PROFILE_LIST_KEY, transcodeProfileList);
	}
}
