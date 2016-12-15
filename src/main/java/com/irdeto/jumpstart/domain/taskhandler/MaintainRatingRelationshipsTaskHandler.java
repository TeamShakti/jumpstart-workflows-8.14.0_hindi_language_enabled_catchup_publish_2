package com.irdeto.jumpstart.domain.taskhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.constants.TaskResult;
import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.domain.mmentityapi.HttpQueryFilterParameter;
import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.RatingScheme;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.mediamanager.MediaManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("MaintainRatingRelationships")
public class MaintainRatingRelationshipsTaskHandler extends AbstractTaskHandler {
	private static final Logger logger = LoggerFactory.getLogger(MaintainRatingRelationshipsTaskHandler.class);
	
	@TaskProperty(type=Map.class)
	public static final String RATING_MAP_MAP_PROPERTY = "RatingMapMap";

	@TaskResult
	public static final String MAINTAINED_ENTITY_LIST_PROPERTY = "MaintainedEntityList";
	@Resource(name="mediaManager8Rest")
	private MediaManager mediaManager;

	@SuppressWarnings("unchecked")
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		// Map from scheme to value.
		Map<String, Map<String, String>> ratingMapMap = (Map<String, Map<String, String>>)verifiedParameters.get(RATING_MAP_MAP_PROPERTY);
		// We still need to remove ratings if there are none supplied over metadata.
		if (ratingMapMap == null) {
			ratingMapMap = new HashMap<>();
		}
		List<String> maintainedEntityList = new ArrayList<>();
		for (Entry<String, Map<String, String>> entry: ratingMapMap.entrySet()) {
			String[] splitKey = entry.getKey().split(":");
			if (splitKey.length == 2 && StringUtils.isNumeric(splitKey[1])) {
				String entityType = splitKey[0];
				Integer entityId = Integer.valueOf(splitKey[1]);
				Map<String, String> ratingMap = entry.getValue();
				if (maintainRatings(entityType, entityId, ratingMap)) {
					maintainedEntityList.add(entry.getKey());
				}
			}
		}
		results.put(MAINTAINED_ENTITY_LIST_PROPERTY, maintainedEntityList);
	}

	private boolean maintainRatings(String entityType, Integer entityId, Map<String, String> ratingMap) throws Exception {
		boolean added = false;
		// Load existing rating relationships
		MM8Response existingRatingsResponse = mediaManager.readRelationships(entityType, entityId, WorkflowHelper.RATING_RELATIONSHIP_NAME, WorkflowHelper.ENTITY_TYPE_RATING, new HttpQueryFilterParameter(), null, null);
		
		List<Rating> existingRatingList = WorkflowHelper.getEntityList(Rating.class, existingRatingsResponse);
		List<String> existingRatingIdList = new ArrayList<>();
		List<String> deleteRatingIdList = new ArrayList<>();
		for (Rating rating: existingRatingList) {
			existingRatingIdList.add(rating.getId());
			deleteRatingIdList.add(rating.getId());
		}

		for (Entry<String, String> ratingEntry: ratingMap.entrySet()) {
			
			// Check valid values.
			String classification = ratingEntry.getKey();
			String ratingValue = ratingEntry.getValue();
			if (StringUtils.isBlank(classification) || StringUtils.isBlank(ratingValue)) {
				logger.warn("Not able to load rating {}: {}.  Blank values.", classification, ratingValue);
				continue;
			}
			
			// Read rating scheme
			QueryFilterParameter queryFilterParameter = WorkflowHelper.getQueryFilterParameter(WorkflowHelper.ATTRIBUTE_NAME_CLASSIFICATION, classification, false);
			MM8Response ratingSchemeResponse = mediaManager.readEntity(WorkflowHelper.ENTITY_TYPE_RATING_SCHEME, queryFilterParameter, null, null);
			
			RatingScheme ratingScheme = WorkflowHelper.getEntity(RatingScheme.class, ratingSchemeResponse);
			if (ratingScheme == null || !StringUtils.isNumeric(ratingScheme.getId())) {
				logger.warn("Not able to load rating {}: {}.  Rating scheme not found in Media Manager.", classification, ratingValue);
				continue;
			}
			
			// Read all ratings attached to rating scheme
			MM8Response ratingResponse = mediaManager.readRelationships(WorkflowHelper.ENTITY_TYPE_RATING_SCHEME, Integer.valueOf(ratingScheme.getId()), WorkflowHelper.RATING_RELATIONSHIP_NAME, WorkflowHelper.ENTITY_TYPE_RATING, new HttpQueryFilterParameter(), null, null);
			List<Rating> ratingList = WorkflowHelper.getEntityList(Rating.class, ratingResponse);
			
			// Determine the rating from MM that matches the rating from metadata
			String ratingId = null;
			for (Rating rating: ratingList) {
				if (ratingValue.equals(rating.getRatingLabel())) {
					ratingId = rating.getId();
					break;
				}
			}
			if (ratingId == null || !StringUtils.isNumeric(ratingId)) {
				logger.warn("Not able to load rating {}: {}.  Rating value not found in Media Manager.", classification, ratingValue);
				continue;
			}
			
			// Create the entry if it does not already exist.  Maintain the deletes list.
			if (!existingRatingIdList.contains(ratingId)) {
				mediaManager.createRelationship(entityType, entityId, WorkflowHelper.RATING_RELATIONSHIP_NAME, Integer.valueOf(ratingId), WorkflowHelper.ENTITY_TYPE_RATING);
				added = true;
				existingRatingIdList.add(ratingId);
			} else {
				deleteRatingIdList.remove(ratingId);
			}
		}
		boolean deleted = deleteUnneededRatings(entityType, entityId, deleteRatingIdList);
		return deleted || added;
	}

	private boolean deleteUnneededRatings(String entityType, Integer entityId, List<String> deleteRatingIdList)
			throws Exception {
		// Delete ratings that should not be attached.
		boolean deleted = false;
		for (String ratingId: deleteRatingIdList) {
			mediaManager.deleteRelationship(entityType, entityId, WorkflowHelper.RATING_RELATIONSHIP_NAME, Integer.valueOf(ratingId));
			deleted = true;
		}
		return deleted;
	}
}
