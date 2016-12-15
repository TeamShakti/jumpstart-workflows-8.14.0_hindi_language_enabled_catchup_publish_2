package com.irdeto.jumpstart.workflow.purge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.ProtectVideoSub;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.Subcontent;
import com.irdeto.jumpstart.domain.TransVideoSub;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class EntityPurgeHelper extends WorkflowHelper {

	private static final String PURGE_ENTITIES_KEY = "PurgeEntities";
	private static final String DELETE_SUBCONTENT_LIST_KEY = "deleteSubcontentList";
	private static final String PURGE_ACTION_KEY = "PurgeAction";
	private static final String PURGE_ON_EXPIRY_KEY = "PurgeOnExpiry";
	private static final Logger logger = LoggerFactory.getLogger(EntityPurgeHelper.class);

	public static boolean isValidProgramToPurge(ProcessContext kcontext){
		Program program = (Program) kcontext.getVariable(PROGRAM_KEY);
		String purgeOnExpiry = (String) kcontext.getVariable(PURGE_ON_EXPIRY_KEY);
		if(purgeOnExpiry.equalsIgnoreCase("false")){
			logger.debug("PurgeOnExpiry is false.");
			return true;
		} else {
			DateTime endDateTime = program.getEndDateTime();
			logger.debug("Read Program ID:{}, EndDateTime:{}, Now:{}", program.getId(), endDateTime.toString(), System.currentTimeMillis());
			if (endDateTime != null) {
				if (endDateTime.isBeforeNow()){
					logger.debug("PurgeOnExpiry is true and Program is Expired.", program.getId(), endDateTime.toString(), System.currentTimeMillis());
					return true;
				} else {
					logger.debug("PurgeOnExpiry is true but Program is not expired. Purge is bypass.");
				}
			}
		}
		return false;
	}

	public static void buildUpDeleteFileList(ProcessContext kcontext){
		Program program = (Program) kcontext.getVariable(PROGRAM_KEY);
		String purgeAction = (String) kcontext.getVariable(PURGE_ACTION_KEY);
		List<Subcontent> deleteSubcontentList = new ArrayList<Subcontent>();
		if(purgeAction.toLowerCase().contains("image")){
			for(ImageContent imageContent : program.getImageContent()){
				for(ImageSubcontent imageSubcontent :imageContent.getSubcontent()){
					deleteSubcontentList.add(imageSubcontent);
				}
				imageContent.getSubcontent().clear();
			}
		}
		if(purgeAction.toLowerCase().contains("protect")){
			for(VideoContent videoContent : program.getVideoContent()){
				for(SourceVideoSub sourceVideoSub : videoContent.getSubcontent()){
					for(ProtectVideoSub protectVideoSub : sourceVideoSub.getProtectSubs()){
						deleteSubcontentList.add(protectVideoSub);
					}
					sourceVideoSub.getProtectSubs().clear();
				}
			}
		}
		if(purgeAction.toLowerCase().contains("transcoded")){
			for(VideoContent videoContent : program.getVideoContent()){
				for(SourceVideoSub sourceVideoSub : videoContent.getSubcontent()){
					if(sourceVideoSub.getProtectSubs()==null || sourceVideoSub.getProtectSubs().isEmpty()){
						for(TransVideoSub transVideoSub : sourceVideoSub.getTransSubs()){
							deleteSubcontentList.add(transVideoSub);
						}
					}
					sourceVideoSub.getTransSubs().clear();
				}
			}
		}
		if(purgeAction.toLowerCase().contains("source")){
			for(VideoContent videoContent : program.getVideoContent()){
				Iterator<SourceVideoSub> iter = videoContent.getSubcontent().iterator();
				while(iter.hasNext()){
					SourceVideoSub sourceVideoSub = iter.next();
					if((sourceVideoSub.getProtectSubs()==null || sourceVideoSub.getProtectSubs().isEmpty())
							&& (sourceVideoSub.getTransSubs()==null || sourceVideoSub.getTransSubs().isEmpty())){
						deleteSubcontentList.add(sourceVideoSub);
						iter.remove();
					}
				}
			}
		}
		logger.debug("Number of subcontent to be purged is : {}", deleteSubcontentList.size());
		kcontext.setVariable(DELETE_SUBCONTENT_LIST_KEY, deleteSubcontentList);
	}
	public static boolean isEntityPurgeRequired(ProcessContext kcontext){
		String purgeEntities = (String) kcontext.getVariable(PURGE_ENTITIES_KEY);
		if(purgeEntities.equalsIgnoreCase("false")){
			return false;
		} else {
			Program program = (Program) kcontext.getVariable(PROGRAM_KEY);
			boolean emptyAllSub = true;
			for(VideoContent videoContent : program.getVideoContent()){
				if(videoContent.getSubcontent().isEmpty()){
					emptyAllSub &= true;
				} else {
					emptyAllSub &= false;
				}
			}
			for(ImageContent imageContent : program.getImageContent()){
				if(imageContent.getSubcontent().isEmpty()){
					emptyAllSub &= true;
				} else {
					emptyAllSub &= false;
				}
			}
			if(emptyAllSub == true){
				logger.debug("All subcontent are empty. Entity can be purged.");
				return true;
			} else{
				logger.debug("Subcontent are not empty. Entity can NOT be purged.");
				return false;
			}
		}
	}
}
