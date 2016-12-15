package com.irdeto.jumpstart.domain.publish;

import java.util.Map;

import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.EncodeProfile;

public interface EntityWithEncodeProfilesPublishWrapper extends EntityWithTermWrapperListPublishWrapper{
	public Map<DeviceProfile, EncodeProfile> getEncodeProfiles();
}
