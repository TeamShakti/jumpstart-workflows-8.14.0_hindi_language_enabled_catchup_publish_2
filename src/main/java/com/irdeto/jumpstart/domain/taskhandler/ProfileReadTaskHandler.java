package com.irdeto.jumpstart.domain.taskhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.constants.TaskResult;
import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.domain.mmentityapi.HttpQueryFilterParameter;
import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.PolicyGroupProfile;
import com.irdeto.jumpstart.domain.PolicyProfile;
import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.TermMapping;
import com.irdeto.jumpstart.domain.TranscodeProfile;
import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.config.Encode;
import com.irdeto.jumpstart.domain.config.Policy;
import com.irdeto.jumpstart.domain.config.PolicyGroup;
import com.irdeto.jumpstart.domain.config.Profile;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.config.Transcode;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.config.DeviceProfileHelper;
import com.irdeto.manager.mediamanager.MediaManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("ProfileRead")
public class ProfileReadTaskHandler extends AbstractTaskHandler {
	@TaskProperty(type=Term.class, required=false)
	public static final String TERM_WRAPPER_PROPERTY = "TermWrapper";
	@TaskProperty(type=List.class, required=false)
	public static final String TERM_WRAPPER_LIST_PROPERTY = "TermWrapperList";
	@TaskProperty(required=false)
	public static final String CONTENT_TYPE_PROPERTY = "ContentType";
	@TaskProperty(type=Boolean.class, required=false, defaultValue="true")
	public static final String DEVICE_PROFILE_ENABLED_ONLY_PROPERTY = "DeviceProfileEnabledOnly";
	@TaskResult
	public static final String PROFILE_PROPERTY = "Profile";

	protected static final String TERM_MAP_KEY = "termMap";
	protected static final String DEVICE_KEY = "device";

	@Resource(name="mediaManager8Rest")
	private MediaManager mediaManager;

	volatile private Profile cachedProfile;
	volatile private Long cacheTime;
		
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		String contentType = (String) verifiedParameters.get(CONTENT_TYPE_PROPERTY);
		boolean deviceProfileEnabledOnly = (Boolean) verifiedParameters.get(DEVICE_PROFILE_ENABLED_ONLY_PROPERTY);
		
		@SuppressWarnings("unchecked")
		List<TermWrapper> termWrapperList = (List<TermWrapper>) verifiedParameters.get(TERM_WRAPPER_LIST_PROPERTY);
		TermWrapper termWrapper = (TermWrapper) verifiedParameters.get(TERM_WRAPPER_PROPERTY);
		if (termWrapperList == null) {
			termWrapperList = new ArrayList<>();
		}
		if (termWrapper != null) {
			termWrapperList.add(termWrapper);
		}

		// If device profile enabled only is false, assume full results required.
		if (!deviceProfileEnabledOnly) {
			results.put(PROFILE_PROPERTY, getProfile(deviceProfileEnabledOnly));
			return;
		}

		String cacheDurationString = propertiesManager.getProperty(JumpstartPropertyKey.PROFILE_READ_CACHE_DURATION_PROPERTY);
		long cacheDuration = Long.parseLong(cacheDurationString);
		synchronized(this) {
			if (cachedProfile == null || cacheTime == null || cacheTime + cacheDuration < System.currentTimeMillis()) {
				cachedProfile = getProfile(deviceProfileEnabledOnly);
				cacheTime = System.currentTimeMillis();
			}
			if (termWrapperList.isEmpty() && StringUtils.isBlank(contentType)) {
				results.put(PROFILE_PROPERTY, cachedProfile);
			} else if (!termWrapperList.isEmpty()) {
				results.put(PROFILE_PROPERTY, getSpecificTermWrapperProfile(cachedProfile, contentType, termWrapperList));
			} else {
				results.put(PROFILE_PROPERTY, getSpecificContentTypeProfile(cachedProfile, contentType));
			}
		}
	}
	
	private Profile getSpecificTermWrapperProfile(Profile profile, String contentType, List<TermWrapper> termWrapperList) {
		Profile specificProfile = new Profile();
		for (TermWrapper termWrapper: termWrapperList) {
			if (termWrapper.getTerm() == null
						|| StringUtils.isBlank(termWrapper.getTerm().getContractName())) {
				continue;
			}
			for (TermMap termMap: profile.getTermMapList()) {
				if (StringUtils.isNotBlank(contentType) && !termMap.getContentTypeList().contains(contentType)) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(termMap.getContractName(), termWrapper.getTerm().getContractName())) {
					termWrapper.getTermMapList().add(termMap);
					specificProfile.getTermMapList().add(termMap);
					processDeviceToProfile(specificProfile, termMap);
				}
			}
		}
		return specificProfile;
	}

	private void processDeviceToProfile(Profile specificProfile, TermMap termMap) {
		for (Device device: termMap.getDeviceList()) {
			if (!specificProfile.getDeviceList().contains(device)) {
				specificProfile.getDeviceList().add(device);
			}
			for (Transcode transcode: device.getTranscodeList()) {
				if (!specificProfile.getTranscodeList().contains(transcode)) {
					specificProfile.getTranscodeList().add(transcode);
				}
			}
			for (Protect protect: device.getProtectList()) {
				if (!specificProfile.getProtectList().contains(protect)) {
					specificProfile.getProtectList().add(protect);
				}
			}
			for (Encode encode: device.getEncodeList()) {
				if (!specificProfile.getEncodeList().contains(encode)) {
					specificProfile.getEncodeList().add(encode);
				}
			}
		}
	}

	private Profile getSpecificContentTypeProfile(Profile profile, String contentType) {
		Profile specificProfile = new Profile();
		for (TermMap termMap: profile.getTermMapList()) {
			if (StringUtils.isNotBlank(contentType) && !termMap.getContentTypeList().contains(contentType)) {
				continue;
			}
			specificProfile.getTermMapList().add(termMap);
			processDeviceToProfile(specificProfile, termMap);
		}
		return specificProfile;
	}

	private Profile getProfile(boolean deviceProfileEnabledOnly)
			throws Exception {
		Profile profile = new Profile();
		profile.getTermMapList().addAll(getTermMapList());
		for (TermMap termMap: profile.getTermMapList()) {
			List<Device> deviceList = getDeviceList(termMap, deviceProfileEnabledOnly);
			List<Device> unknownDeviceList = new ArrayList<>();
			for (Device device: deviceList) {
				boolean found = false;
				for (Device knownDevice: profile.getDeviceList()) {
					if (device.getDeviceProfileId().equals(knownDevice.getDeviceProfileId())) {
						termMap.getDeviceList().add(knownDevice);
						termMap.getDeviceProfileNameList().add(knownDevice.getName());
						found = true;
						break;
					}
				}
				if (!found) {
					unknownDeviceList.add(device);
					termMap.getDeviceList().add(device);
					termMap.getDeviceProfileNameList().add(device.getName());
				}
			}
			
			ConcurrentHashMap<String, Object> deviceDataMap = new ConcurrentHashMap<>();
			deviceDataMap.put(TERM_MAP_KEY, termMap);
			deviceDataMap.put(PROFILE_PROPERTY, profile);
			ThreadPoolFactory.executeLoop(unknownDeviceList, this, DeviceProcessor.class, deviceDataMap, null);
			List<Policy> policyList = getPolicyList(termMap);
			termMap.setPolicyList(policyList);
			List<PolicyGroup> policyGroupList = getPolicyGroupList(termMap);
			termMap.setPolicyGroupList(policyGroupList);
			for (Device device: unknownDeviceList) {
				if (!profile.getDeviceList().contains(device)) {
					profile.getDeviceList().add(device);
					for (Encode encode: device.getEncodeList()) {
						if (!profile.getEncodeList().contains(encode)) {
							profile.getEncodeList().add(encode);
						}
					}
					for (Transcode transcode: device.getTranscodeList()) {
						if (!profile.getTranscodeList().contains(transcode)) {
							profile.getTranscodeList().add(transcode);
							for (Protect protect: transcode.getProtectList()) {
								if (!profile.getProtectList().contains(protect)) {
									profile.getProtectList().add(protect);
								}
							}
						}
					}
					for (Protect protect: device.getProtectList()) {
						if (!profile.getProtectList().contains(protect)) {
							profile.getProtectList().add(protect);
						}
					}
				}
			}
		}
		return profile;
	}
		
	class DeviceProcessor extends ThreadPoolFactory.ItemRunnable<Device> {
		@Override
		public void run() throws Throwable {
			Device device = getItem();
			TermMap termMap = (TermMap)getDataMap().get(TERM_MAP_KEY);
			Profile profile = (Profile)getDataMap().get(PROFILE_PROPERTY);
			termMap.getDeviceProfileNameList().add(device.getName());

			processEncodeList(profile, device);
			processTranscodeList(profile, device);
			processProtectList(profile, device);
		}
		
		private void processEncodeList(Profile profile, Device device) throws Exception {
			List<Encode> encodeList = getEncodeList(device);
			device.setEncodeList(encodeList);
			for (Encode encode: encodeList) {
				device.getEncodeProfileNameList().add(encode.getName());
			}
		}
		
		private void processTranscodeList(Profile profile, Device device) throws Exception {
			List<Transcode> transcodeList = getTranscodeList(device);
			device.setTranscodeList(transcodeList);
			ConcurrentHashMap<String, Object> transcodeDataMap = new ConcurrentHashMap<>();
			transcodeDataMap.put(PROFILE_PROPERTY, getDataMap().get(PROFILE_PROPERTY));
			transcodeDataMap.put(DEVICE_KEY, device);
			ThreadPoolFactory.executeLoop(transcodeList, this, TranscodeProcessor.class, transcodeDataMap, null);  
		}
	
		private void processProtectList(Profile profile, Device device) throws Exception {
			List<Protect> protectList = getProtectList(device);
			device.setProtectList(protectList);
			for (Protect protect: protectList) {
				device.getProtectProfileNameList().add(protect.getName());
			}
		}

		class TranscodeProcessor extends ThreadPoolFactory.ItemRunnable<Transcode> {
			@Override
			public void run() throws Throwable {
				Transcode transcode = getItem();
				Device device = (Device)getDataMap().get(DEVICE_KEY);
				device.getTranscodeProfileNameList().add(transcode.getName());
				List<Protect> protectList = getProtectList(transcode);
				transcode.setProtectList(protectList);
				for (Protect protect: protectList) {
					transcode.getProtectProfileNameList().add(protect.getName());
				}
			}
		}
	}
	
	private List<Encode> getEncodeList(Device device) throws Exception {
		List<Encode> encodeList = new ArrayList<>();
		List<EncodeProfile> encodeProfileList = readEntityRelationships(WorkflowHelper.ENTITY_TYPE_DEVICE_PROFILE, device.getDeviceProfileId(), WorkflowHelper.ENCODE_PROFILE_RELATIONSHIP_NAME, EncodeProfile.class);
		for (EncodeProfile encodeProfile: encodeProfileList) {
			Encode encode = DeviceProfileHelper.convertEncodeProfileToEncode(encodeProfile);
			if (!encodeList.contains(encode)) {
				encodeList.add(encode);
			}
		}
		return encodeList;
	}

	private List<Transcode> getTranscodeList(Device device) throws Exception {
		List<Transcode> transcodeList = new ArrayList<>();
		List<TranscodeProfile> transcodeProfileList = readEntityRelationships(WorkflowHelper.ENTITY_TYPE_DEVICE_PROFILE, device.getDeviceProfileId(), WorkflowHelper.TRANSCODE_PROFILE_RELATIONSHIP_NAME, TranscodeProfile.class);
		for (TranscodeProfile transcodeProfile: transcodeProfileList) {
			Transcode transcode = DeviceProfileHelper.convertTranscodeProfileToTranscode(transcodeProfile);
			if (!transcodeList.contains(transcode)) {
				transcodeList.add(transcode);
			}
		}
		return transcodeList;
	}

	private List<Protect> getProtectList(Device device) throws Exception {
		List<Protect> protectList = new ArrayList<>();
		List<ProtectProfile> protectProfileList = readEntityRelationships(WorkflowHelper.ENTITY_TYPE_DEVICE_PROFILE, device.getDeviceProfileId(), WorkflowHelper.PROTECT_PROFILE_RELATIONSHIP_NAME, ProtectProfile.class);
		for (ProtectProfile protectProfile: protectProfileList) {
			Protect protect = DeviceProfileHelper.convertProtectProfileToProtect(protectProfile);
			if (!protectList.contains(protect)) {
				protectList.add(protect);
			}
		}
		return protectList;
	}

	private List<Protect> getProtectList(Transcode transcode) throws Exception {
		List<Protect> protectList = new ArrayList<>();
		List<ProtectProfile> protectProfileList = readEntityRelationships(WorkflowHelper.ENTITY_TYPE_TRANSCODE_PROFILE, transcode.getTranscodeProfileId(), WorkflowHelper.PROTECT_PROFILE_RELATIONSHIP_NAME, ProtectProfile.class);
		for (ProtectProfile protectProfile: protectProfileList) {
			Protect protect = DeviceProfileHelper.convertProtectProfileToProtect(protectProfile);
			if (!protectList.contains(protect)) {
				protectList.add(protect);
			}
		}
		return protectList;
	}

	private List<Device> getDeviceList(TermMap termMap, boolean deviceProfileEnabledOnly) throws Exception {
		List<Device> deviceList = new ArrayList<>();
		List<DeviceProfile> deviceProfileList = readEntityRelationships(WorkflowHelper.ENTITY_TYPE_TERM_MAPPING, termMap.getTermMappingId(), WorkflowHelper.DEVICE_PROFILE_RELATIONSHIP_NAME, DeviceProfile.class);
		for (DeviceProfile deviceProfile: deviceProfileList) {
			Device device = DeviceProfileHelper.convertDeviceProfileToDevice(deviceProfile);
			if (!deviceList.contains(device)
					&& (BooleanUtils.isTrue(device.getEnabled()) || !deviceProfileEnabledOnly)) {
				deviceList.add(device);
			}
		}
		return deviceList;
	}

	private List<Policy> getPolicyList(TermMap termMap) throws Exception {
		List<Policy> policyList = new ArrayList<>();
		List<PolicyProfile> policyProfileList = readEntityRelationships(WorkflowHelper.ENTITY_TYPE_TERM_MAPPING, termMap.getTermMappingId(), WorkflowHelper.POLICY_PROFILE_RELATIONSHIP_NAME, PolicyProfile.class);
		for (PolicyProfile policyProfile: policyProfileList) {
			Policy policy = DeviceProfileHelper.convertPolicyProfileToPolicy(policyProfile);
			if (!policyList.contains(policy)) {
				policyList.add(policy);
			}
		}
		return policyList;
	}
	
	private List<PolicyGroup> getPolicyGroupList(TermMap termMap) throws Exception {
		List<PolicyGroup> policyGroupList = new ArrayList<>();
		List<PolicyGroupProfile> policyGroupProfileList = readEntityRelationships(WorkflowHelper.ENTITY_TYPE_TERM_MAPPING, termMap.getTermMappingId(), WorkflowHelper.POLICY_GROUP_PROFILE_RELATIONSHIP_NAME, PolicyGroupProfile.class);
		for (PolicyGroupProfile policyGroupProfile: policyGroupProfileList) {
			PolicyGroup policyGroup = DeviceProfileHelper.convertPolicyGroupProfileToPolicyGroup(policyGroupProfile);
			if (!policyGroupList.contains(policyGroup)) {
				policyGroupList.add(policyGroup);
			}
		}
		return policyGroupList;
	}
	
	private List<TermMap> getTermMapList() throws Exception {
		List<TermMapping> termMappingList = readEntityList(TermMapping.class);
		List<TermMap> termMapList = getTermMapListFromTermMappingList(termMappingList);
		return termMapList;
	}

	private List<TermMap> getTermMapListFromTermMappingList(List<TermMapping> termMappingList) {
		List<TermMap> termMapList = new ArrayList<>();
		for (TermMapping termMapping: termMappingList) {
			TermMap termMap = DeviceProfileHelper.convertTermMappingToTermMap(termMapping);

			if (!termMapList.contains(termMap)) {
				termMapList.add(termMap);
			}
		}
		return termMapList;
	}
	
	private <T extends BaseEntity> List<T> readEntityList(Class<T> entityClass) throws Exception {
		QueryFilterParameter queryFilterParameter = new HttpQueryFilterParameter();
		MM8Response response = mediaManager.readEntity(WorkflowHelper.getEntityType(entityClass), queryFilterParameter, null, null);
		return WorkflowHelper.getEntityList(entityClass, response);
	}
	
	private <T extends BaseEntity, U extends BaseEntity> List<U> readEntityRelationships(String entityType, String entityId, String relationship, Class<U> relationshipEntityClass) throws Exception {
		if (StringUtils.isNumeric(entityId)) {
			MM8Response response = mediaManager.readRelationships(entityType, Integer.valueOf(entityId), relationship, WorkflowHelper.getEntityType(relationshipEntityClass), new HttpQueryFilterParameter(), null, null);
			return WorkflowHelper.getEntityList(relationshipEntityClass, response);
		} else {
			return new ArrayList<U>();
		}
	}
}
