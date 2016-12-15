package com.irdeto.jumpstart.domain.publish;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.ProtectVideoSub;
import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.RatingScheme;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.SubtitleContent;
import com.irdeto.jumpstart.domain.SubtitleSubcontent;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public abstract class AbstractPublishWrapper<T extends Base> implements PublishWrapper<T> {
	private static final String GET_VIDEO_CONTENT_METHOD_NAME = "getVideoContent";
	private static final String GET_IMAGE_CONTENT_METHOD_NAME = "getImageContent";
	private T approvedEntity;
	private List<VideoContentWrapper> videoContentWrapperList = new ArrayList<>();
	private List<ImageContentWrapper> imageContentWrapperList = new ArrayList<>();
	private List<SubtitleContentWrapper> subtitleContentWrapperList = new ArrayList<>();
	private List<Device> deviceList = new ArrayList<>();
	private List<TermMap> termMapList = new ArrayList<>();
	private List<TermWrapper> termWrapperList = new ArrayList<>();
	private Map<String, Object> chainedRelationshipMap = new HashMap<>();
	private Map<String, BaseEntity> directRelationshipMap = new HashMap<>();
	
	@Override
	public T getEntity() {
		return approvedEntity;
	}

	public void setEntity(T approvedEntity) {
		this.approvedEntity = approvedEntity;
	}

	@Override
	public List<VideoContentWrapper> getVideoContentWrapperList() {
		return videoContentWrapperList;
	}
	public void setVideoContentWrapperList(List<VideoContentWrapper> videoContentWrapperList) {
		this.videoContentWrapperList = videoContentWrapperList;
	}

	public Map<String, Object> getChainedRelationshipMap() {
		return chainedRelationshipMap;
	}

	public void setChainedRelationshipMap(Map<String, Object> chainedRelationshipMap) {
		this.chainedRelationshipMap = chainedRelationshipMap;
	}
	
	@Override
	public void setCompoundRelationshipMap(Map<String, Object> compoundRelationshipMap) {
		List<BaseEntity> entityList = getEntityListFromMap(compoundRelationshipMap);
		for (BaseEntity baseEntity: entityList) {
			getDirectRelationshipMap().put(getKey(baseEntity), baseEntity);
		}
		setChainedRelationshipMap(compoundRelationshipMap);
	}
	
	@SuppressWarnings("unchecked")
	@JsonIgnore
	private List<BaseEntity> getEntityListFromMap(Object relatedEntities) {
		List<BaseEntity> entityList = new ArrayList<>();
		if (relatedEntities instanceof BaseEntity) {
			entityList.add((BaseEntity)relatedEntities);
		} else if (relatedEntities instanceof List) {
			List<Object> list = (List<Object>)relatedEntities;
			for (Object entity: list) {
				entityList.addAll(getEntityListFromMap(entity));
			}
			list.clear();
			for (BaseEntity baseEntity: entityList) {
				list.add(getKey(baseEntity));
			}
		} else if (relatedEntities instanceof Map) {
			Map<Object, Object> relatedEntityMap = (Map<Object, Object>)relatedEntities;
			Map<String, Object> tempMap = new HashMap<>();
			for (Entry<Object, Object> entry: relatedEntityMap.entrySet()) {
				entityList.addAll(getEntityListFromMap(entry.getValue()));
				if (entry.getKey() instanceof BaseEntity) {
					entityList.addAll(getEntityListFromMap(entry.getKey()));
					tempMap.put(getKey((BaseEntity)entry.getKey()), entry.getValue());
				} else if (entry.getKey() instanceof String) {
					tempMap.put((String)entry.getKey(), entry.getValue());
				}
			}
			((Map<Object, Object>)relatedEntities).clear();
			((Map<Object, Object>)relatedEntities).putAll(tempMap);
		}
		return entityList;
	}

	@SuppressWarnings("unchecked")
	@JsonIgnore
	// E.g. program->genre relationship.  Use this to get the list of genres from the relationship name list {"genres"}
	protected <U extends BaseEntity> List<U> getEntityListFromRelationshipMap(Class<U> type, String... relationshipNames) {
		List<U> entityList = new ArrayList<>();
		String relationshipKey = StringUtils.join(relationshipNames, ",");
		Object relatedEntities = getChainedRelationshipMap().get(relationshipKey);
		if (relatedEntities != null) {
			List<String> idList = getTerminalIdsFromChainedRelationshipMap(relatedEntities);
			for (String id: idList) {
				BaseEntity entity = getDirectRelationshipMap().get(id);
				if (type.equals(entity.getClass())) {
					entityList.add((U)entity);
				}
			}
		}
		return entityList;
	}
	
	@JsonIgnore
	private List<String> getTerminalIdsFromChainedRelationshipMap(Object relatedEntities) {
		List<String> idList = new ArrayList<>();
		if (relatedEntities instanceof String) {
			idList.add((String)relatedEntities);
		} else if (relatedEntities instanceof List) {
			List<?> list = (List<?>) relatedEntities;
			for (Object item: list) {
				idList.addAll(getTerminalIdsFromChainedRelationshipMap(item));
			}
		} else if (relatedEntities instanceof Map) {
			Map<?, ?> map = (Map<?, ?>)relatedEntities;
			for (Entry<?, ?> entry: map.entrySet()) {
				idList.addAll(getTerminalIdsFromChainedRelationshipMap(entry.getValue()));
			}
		}
		return idList;
	}

	@JsonIgnore
	// E.g. program->series->brand relationship.  Use this to get the brand from the relationship name list {"seriess","brands"}
	protected <U extends BaseEntity> U getEntityFromRelationshipMap(Class<U> type, String... relationshipNames) {
		List<U> entityList = getEntityListFromRelationshipMap(type, relationshipNames);
		if (!entityList.isEmpty()) {
			return entityList.get(0);
		} else {
			return null;
		}
	}
	
	@JsonIgnore
	private static String getKey(BaseEntity baseEntity) {
		return baseEntity.getType() + ":" + baseEntity.getId();
	}

	@Override
	public List<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}

	@Override
	public List<TermMap> getTermMapList() {
		return termMapList;
	}

	public void setTermMapList(List<TermMap> termMapList) {
		this.termMapList = termMapList;
	}

	@Override
	public List<TermWrapper> getTermWrapperList() {
		return termWrapperList;
	}

	public void setTermWrapperList(List<TermWrapper> termWrapperList) {
		this.termWrapperList = termWrapperList;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((approvedEntity == null) ? 0 : approvedEntity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractPublishWrapper<?> other = (AbstractPublishWrapper<?>) obj;
		if (approvedEntity == null) {
			if (other.approvedEntity != null)
				return false;
		} else if (!approvedEntity.equals(other.approvedEntity))
			return false;
		return true;
	}
	
	@Override
	public List<ImageContentWrapper> getImageContentWrapperList() {
		return imageContentWrapperList;
	}
	public void setImageContentWrapperList(List<ImageContentWrapper> imageContentWrapperList) {
		this.imageContentWrapperList = imageContentWrapperList;
	}
	
	@Override
	public List<SubtitleContentWrapper> getSubtitleContentWrapperList() {
		return subtitleContentWrapperList;
	}
	public void setSubtitlecontentWrapperList(
			List<SubtitleContentWrapper> subtitleContentWrapperList) {
		this.subtitleContentWrapperList = subtitleContentWrapperList;
	}
	
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}

	public Map<String, BaseEntity> getDirectRelationshipMap() {
		return directRelationshipMap;
	}

	public void setDirectRelationshipMap(Map<String, BaseEntity> directRelationshipMap) {
		this.directRelationshipMap = directRelationshipMap;
	}
	
	@JsonIgnore
	protected Map<Rating, List<RatingScheme>> getRatingRatingSchemeListMap(String... relationshipNames) {
		String relationshipKey = StringUtils.join(relationshipNames, ",");
		Object relationshipObject = getChainedRelationshipMap().get(relationshipKey);
		if (relationshipObject instanceof Map) {
			return getRatingRatingSchemeListMap((Map<?, ?>)relationshipObject);
		} else {
			return new HashMap<>();
		}
	}
	
	@SuppressWarnings("unchecked")
	@JsonIgnore
	private Map<Rating, List<RatingScheme>> getRatingRatingSchemeListMap(Map<?, ?> relationshipMap) {
		Map<Rating, List<RatingScheme>> ratingRatingSchemeListMap = new HashMap<>();
		for (Entry<?, ?> entry: relationshipMap.entrySet()) {
			if (entry.getKey() instanceof String && entry.getValue() instanceof List) {
				String ratingId = (String)entry.getKey();
				Object ratingObject = getDirectRelationshipMap().get(ratingId);
				List<RatingScheme> ratingSchemeList = new ArrayList<>();
				if (ratingObject != null && ratingObject instanceof Rating) {
					for (String ratingSchemeId: (List<String>)entry.getValue()) {
						Object ratingSchemeObject = getDirectRelationshipMap().get(ratingSchemeId);
						if (ratingSchemeObject != null && ratingSchemeObject instanceof RatingScheme) {
							ratingSchemeList.add((RatingScheme)ratingSchemeObject);
						}
					}
					maintainRatingRatingSchemeListMap(ratingRatingSchemeListMap, (Rating)ratingObject, ratingSchemeList);
				}
			} else if (entry.getValue() instanceof Map) {
				Map<Rating, List<RatingScheme>> leafRatingRatingSchemeListMap = getRatingRatingSchemeListMap((Map<?, ?>)entry.getValue());
				for (Entry<Rating, List<RatingScheme>> leafEntry: leafRatingRatingSchemeListMap.entrySet()) {
					maintainRatingRatingSchemeListMap(ratingRatingSchemeListMap, leafEntry.getKey(), leafEntry.getValue());
				}
			}
		}
		return ratingRatingSchemeListMap;
	}
	
	private void maintainRatingRatingSchemeListMap(Map<Rating, List<RatingScheme>> ratingRatingSchemeListMap, Rating rating, List<RatingScheme> ratingSchemeList) {
		List<RatingScheme> existingRatingSchemeList = ratingRatingSchemeListMap.get(rating);
		if (existingRatingSchemeList == null) {
			existingRatingSchemeList = new ArrayList<>();
			ratingRatingSchemeListMap.put(rating, existingRatingSchemeList);
		}
		for (RatingScheme ratingScheme: ratingSchemeList) {
			if (!existingRatingSchemeList.contains(ratingScheme)) {
				existingRatingSchemeList.add(ratingScheme);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@JsonIgnore
	@Override
	public Map<EncodeProfile, List<DeviceProfile>> getEncodeProfileDeviceProfileListMap() {
		Map<EncodeProfile, List<DeviceProfile>> encodeProfileDeviceProfileListMap = new HashMap<>();
		Object encodeProfileDeviceProfileIdListObject = getChainedRelationshipMap().get(WorkflowHelper.ENCODE_PROFILE_RELATIONSHIP_NAME + "," + WorkflowHelper.DEVICE_PROFILE_RELATIONSHIP_NAME);
		if (encodeProfileDeviceProfileIdListObject != null && encodeProfileDeviceProfileIdListObject instanceof Map) {
			Map<?, ?> encodeProfileDeviceProfileIdListMap = (Map<?, ?>) encodeProfileDeviceProfileIdListObject;
			for (Entry<?, ?> encodeProfileDeviceProfileIdListEntry: encodeProfileDeviceProfileIdListMap.entrySet()) {
				if (encodeProfileDeviceProfileIdListEntry.getKey() instanceof String && encodeProfileDeviceProfileIdListEntry.getValue() instanceof List) {
					String encodeProfileId = (String)encodeProfileDeviceProfileIdListEntry.getKey();
					Object encodeProfileObject = getDirectRelationshipMap().get(encodeProfileId);
					for (String deviceProfileId: (List<String>)encodeProfileDeviceProfileIdListEntry.getValue()) {
						Object deviceProfileObject = getDirectRelationshipMap().get(deviceProfileId);
						if (encodeProfileObject != null && encodeProfileObject instanceof EncodeProfile && deviceProfileObject != null && deviceProfileObject instanceof DeviceProfile) {
							List<DeviceProfile> deviceProfileList = encodeProfileDeviceProfileListMap.get((EncodeProfile)encodeProfileObject);
							if (deviceProfileList == null) {
								deviceProfileList = new ArrayList<>();
								encodeProfileDeviceProfileListMap.put((EncodeProfile)encodeProfileObject, deviceProfileList);
							}
							if (!deviceProfileList.contains(deviceProfileObject)) {
								deviceProfileList.add((DeviceProfile)deviceProfileObject);
							}
						}
					}
				}
			}
		}
		return encodeProfileDeviceProfileListMap;
	}
	
	@Override
	@JsonIgnore
	public boolean publishRequired() {
		return true;
	}

	private List<VideoContent> getVideoContentList() {
		return getVideoContentList(getApprovedEntity());
	}

	@SuppressWarnings("unchecked")
	private List<VideoContent> getVideoContentList(Base entity) {
		try {
			Method method = entity.getClass().getMethod(GET_VIDEO_CONTENT_METHOD_NAME);
			List<VideoContent> videoContentList = (List<VideoContent>)method.invoke(getApprovedEntity());
			return videoContentList;
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
		}
		return new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	private List<SubtitleContent> getSubtitleContentList(Base entity) {
		try {
			Method method = entity.getClass().getMethod(GET_VIDEO_CONTENT_METHOD_NAME);
			List<VideoContent> videoContentList = (List<VideoContent>)method.invoke(getApprovedEntity());			
			List<SubtitleContent> subtitleContentList = new ArrayList<>();			
			for(VideoContent videoContent : videoContentList){
			 subtitleContentList = videoContent.getSubtitleContent();
			}
			return subtitleContentList;
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
		}
		return new ArrayList<>();
	}
	
	@Override
	@JsonIgnore
	public <U extends Content> U getContentById(AbstractContentWrapper<U> contentWrapper) {
		return getContentById(getApprovedEntity(), contentWrapper);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@JsonIgnore
	public <U extends Content> U getContentById(Base entity, AbstractContentWrapper<U> contentWrapper) {
		if (contentWrapper.getContentId() == null) {
			return null;
		}
		List<U> contentList = new ArrayList<>();
		if (contentWrapper instanceof ImageContentWrapper) {
			contentList = (List<U>)getImageContentList(entity);
		} else if (contentWrapper instanceof VideoContentWrapper) {
			contentList = (List<U>)getVideoContentList(entity);
		} else if (contentWrapper instanceof SubtitleContentWrapper) {
			contentList = (List<U>)getSubtitleContentList(entity);
		}
		for (U content: contentList) {
			if (contentWrapper.getContentId().equals(content.getId())) {
				return content;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<ImageContent> getImageContentList(Base entity) {
		try {
			Method method = entity.getClass().getMethod(GET_IMAGE_CONTENT_METHOD_NAME);
			List<ImageContent> imageContentList = (List<ImageContent>)method.invoke(getApprovedEntity());
			return imageContentList;
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
		}
		return new ArrayList<>();
	}

	private List<ImageContent> getImageContentList() {
		return getImageContentList(getApprovedEntity());
	}
	
	private List<SubtitleContent> getSubtitleContentList() {
		return getSubtitleContentList(getApprovedEntity());
	}
	
	@Override
	@JsonIgnore
	public SourceVideoSub getSourceVideoSubcontentById(String sourceVideoSubcontentId) {
		if (sourceVideoSubcontentId == null) {
			return null;
		}
		for (VideoContent videoContent: getVideoContentList()) {
			for (SourceVideoSub sourceVideoSubcontent: videoContent.getSubcontent()) {
				if (sourceVideoSubcontentId.equals(sourceVideoSubcontent.getId())) {
					return sourceVideoSubcontent;
				}
			}
		}
		return null;
	}

	@Override
	@JsonIgnore
	public ProtectVideoSub getProtectedVideoSubcontentById(String protectedVideoSubcontentId) {
		if (protectedVideoSubcontentId == null) {
			return null;
		}
		for (VideoContent videoContent: getVideoContentList()) {
			for (SourceVideoSub sourceVideoSubcontent: videoContent.getSubcontent()) {
				for (ProtectVideoSub protectedVideoSubcontent: sourceVideoSubcontent.getProtectSubs()) {
					if (protectedVideoSubcontentId.equals(protectedVideoSubcontent.getId())) {
						return protectedVideoSubcontent;
					}
				}
			}
		}
		return null;
	}
	
	@Override
	@JsonIgnore
	public ImageSubcontent getImageSubcontentById(String imageSubcontentId) {
		if (imageSubcontentId == null) {
			return null;
		}
		for (ImageContent imageContent: getImageContentList()) {
			for (ImageSubcontent imageSubcontent: imageContent.getSubcontent()) {
				if (imageSubcontentId.equals(imageSubcontent.getId())) {
					return imageSubcontent;
				}
			}
		}
		return null;
	}
	
	@Override
	@JsonIgnore
	public SubtitleSubcontent getSubtitleSubcontentById(String subtitleSubcontentId) {
		if (subtitleSubcontentId == null) {
			return null;
		}
		for (SubtitleContent subtitleContent: getSubtitleContentList()) {
			for (SubtitleSubcontent subtitleSubcontent: subtitleContent.getSubcontent()) {
				if (subtitleSubcontentId.equals(subtitleSubcontent.getId())) {
					return subtitleSubcontent;
				}
			}
		}
		return null;
	}
	
	@Override
	@JsonIgnore
	public boolean hasVideoContent() {
		return false;
	}
	
	@Override
	@JsonIgnore
	public int getDurationSeconds() {
		return 0;
	}
	
	@Override
	@JsonIgnore
	public boolean isTermsRequired() {
		return true;
	}
	
	@Override
	@JsonIgnore
	public boolean hasMissingPrerequisites(){
		
		List<Base> prerequisites = getPrerequisites();
		for(Base prerequisite : prerequisites ){
			if(prerequisite.getLastPublishDateTime() == null){
				return true;
			}
			if(prerequisite.getLastModifiedDateTime() == null){
				continue;
			}
			if(prerequisite.getLastPublishDateTime().isBefore(prerequisite.getLastModifiedDateTime())){
				return true;
			}
		}
		
		return false;
	}
}
