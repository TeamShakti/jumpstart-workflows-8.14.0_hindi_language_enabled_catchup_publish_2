package com.irdeto.jumpstart.domain.publish;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.ProtectVideoSub;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.SubtitleSubcontent;
import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.factory.WrapperWithDestinations;

public interface PublishWrapper<T extends Base> extends WrapperWithDestinations<T> {
	/**
	 * @deprecated use {@link #getEntity()}.
	 */
	default T getApprovedEntity() {
		return getEntity();
	}
	/**
	 * @deprecated use {@link #setEntity}.
	 */
	default void setApprovedEntity(T entity){
		setEntity(entity);
	}
	public List<VideoContentWrapper> getVideoContentWrapperList();
	public List<ImageContentWrapper> getImageContentWrapperList();
	public List<SubtitleContentWrapper> getSubtitleContentWrapperList();
	public List<Device> getDeviceList();
	public List<TermMap> getTermMapList();
	public List<TermWrapper> getTermWrapperList();
	@JsonIgnore
	public Map<EncodeProfile, List<DeviceProfile>> getEncodeProfileDeviceProfileListMap();
	public void setCompoundRelationshipMap(Map<String, Object> compoundRelationshipMap);
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList();

	@JsonIgnore
	public boolean publishRequired();
	@JsonIgnore
	public <U extends Content> U getContentById(AbstractContentWrapper<U> contentWrapper);
	@JsonIgnore
	public <U extends Content> U getContentById(Base entity, AbstractContentWrapper<U> contentWrapper);
	@JsonIgnore
	public SourceVideoSub getSourceVideoSubcontentById(String sourceVideoSubcontentId);
	@JsonIgnore
	public ProtectVideoSub getProtectedVideoSubcontentById(String protectedVideoSubcontentId);
	@JsonIgnore
	public ImageSubcontent getImageSubcontentById(String imageSubcontentId);
	@JsonIgnore
	public SubtitleSubcontent getSubtitleSubcontentById(String subtitleSubcontentId);
	@JsonIgnore
	public boolean hasVideoContent();
	@JsonIgnore
	public int getDurationSeconds();
	@JsonIgnore
	public boolean isValidTermMap(TermMap termMap);
	@JsonIgnore
	public List<Relationship<?>> getTermRelationshipList();
	@JsonIgnore
	public boolean isTermsRequired();
	@JsonIgnore
	public List<Base> getPrerequisites();
	@JsonIgnore
	public boolean hasMissingPrerequisites();
}
