package com.irdeto.jumpstart.workflow.publish.reference;

import static java.util.stream.Collectors.toMap;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.publish.EntityWithEncodeProfilesPublishWrapper;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceRendition;
import com.irdeto.jumpstart.domain.reference.ReferenceVideoContent;
import com.irdeto.jumpstart.workflow.protect.DRMProfile;
import com.irdeto.jumpstart.workflow.protect.ProtectHelper;
import com.irdeto.jumpstart.workflow.protect.USDRMProfile;

public abstract class AbstractReferenceDocumentMapper<T extends Base> extends AbstractReferenceMapper implements ReferenceDocumentMapper<T> {
	private PublishWrapper<T> publishWrapper;

	public PublishWrapper<T> getPublishWrapper() {
		return publishWrapper;
	}

	@Override
	public void setPublishWrapper(PublishWrapper<T> publishWrapper) {
		this.publishWrapper = publishWrapper;
	}

	protected List<ReferenceVideoContent> mapEncodeProfileRenditions() {
		List<ReferenceVideoContent> videoContentList = new ArrayList<>();
		if (getPublishWrapper() instanceof EntityWithEncodeProfilesPublishWrapper) {
			EntityWithEncodeProfilesPublishWrapper publishWrapper = (EntityWithEncodeProfilesPublishWrapper) getPublishWrapper();

			ReferenceVideoContent videoContent = new ReferenceVideoContent();
			videoContentList.add(videoContent);

			videoContent.getRenditionMap().putAll(
					publishWrapper.getEncodeProfiles().entrySet().stream().map(
							deviceEncodeProfile -> {
								DeviceProfile deviceProfile = deviceEncodeProfile.getKey();
								EncodeProfile encodeProfile = deviceEncodeProfile.getValue();

								Optional<Device> device = getPublishWrapper().getDeviceList().stream().filter(
										d -> d.getDeviceClass().equals(deviceProfile.getDeviceClass())
								).findFirst();

								return device.isPresent()
										? new SimpleEntry<>(
												device.get(),
												mapEncodeProfileToRendition(device.get(), encodeProfile)
										)
										: null;
							}
					).filter(Objects::nonNull).collect(toMap(SimpleEntry::getKey, SimpleEntry::getValue))
			);
		}
		return videoContentList;
	}

	private ReferenceRendition mapEncodeProfileToRendition(Device device, EncodeProfile encodeProfile) {
		BaseEntity entity = getPublishWrapper().getApprovedEntity();
		ReferenceRendition rendition = new ReferenceRendition();
		rendition.setConsumerUrl(appendConsumerUrl(device, encodeProfile.getLiveUri()));
		rendition.setLicenseAcquisitionUrl(buildLicenseAcquisitionUrlMap(device, entity.getType(), entity.getId()));
		return rendition;
	}

	protected static String appendConsumerUrl(Device device, String consumerUrl) {
		if (device != null && device.getPackagingType() != null) {
			switch (device.getPackagingType()) {
			case "DASH":
				// Append the last segment stripped of extension + extension .mpd added
				return consumerUrl + "/.mpd";
			case "HLS":
				// Append the last segment stripped of extension + extension .m3u8 added
				return consumerUrl + "/.m3u8";
			case "Smooth Stream":
				// Append /manifest
				return consumerUrl + "/Manifest";
			case "NA":
			default:
				//do nothing
				return consumerUrl;
			}
		}
		return consumerUrl;
	}

	protected static Map<String, String> buildLicenseAcquisitionUrlMap(Device device, String entityType, String entityId) {
		Map <String, String> urlMap = new HashMap<>();

		if (device != null && device.getProtectList() != null) {
			for (Protect protect : device.getProtectList()) {
				DRMProfile drmProfile = ProtectHelper.getProfile(protect);
				if (drmProfile instanceof USDRMProfile && drmProfile.publishLicenseAcquisitionUrl()) {
					USDRMProfile usDrmProfile = (USDRMProfile) drmProfile;
					for (Entry<String, String> entry : usDrmProfile.getLicenseUrlMap().entrySet()) {
						urlMap.put(
								entry.getKey(),
								usDrmProfile.getLicenseAcquisitionUrl(entityId, entityType, entry.getKey()));
					}
				}
			}
		}

		return urlMap;
	}
}
