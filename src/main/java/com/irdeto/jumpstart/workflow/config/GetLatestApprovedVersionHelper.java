package com.irdeto.jumpstart.workflow.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.ingest.DataIngestHelper;

public class GetLatestApprovedVersionHelper extends WorkflowHelper{

	private static final CharSequence APPROVED_KEY = "approved";

	private static final Logger logger = LoggerFactory.getLogger(DataIngestHelper.class);

	private static final String ENTITY_KEY = "entity";
	private static final String VERSIONS_KEY = "versions";
	private static final String LAST_APPROVED_VERSION = "lastApprovedVersion";

	public static void getVersions(ProcessContext kcontext){
		Object entity = kcontext.getVariable(ENTITY_KEY);
		if (entity instanceof Base){
			List<Object> versions = ((Base)entity).getVersions();
			String lastApprovedVersion = versions.remove(versions.size()-1).toString();
			kcontext.setVariable(VERSIONS_KEY, versions);
			kcontext.setVariable(LAST_APPROVED_VERSION, lastApprovedVersion);
		}
	}

	public static boolean hasApprovedField(Object entity){
		Method[] methods = entity.getClass().getMethods();
		for ( Method method : methods ){
			if(method.getName().toLowerCase().contains(APPROVED_KEY)){
				return true;
			}
		}
		return false;
	}

	public static boolean isApproved(Object entity) {
		boolean isAllApproved = true;
		boolean found = false;
		Method[] methods = entity.getClass().getMethods();
		for ( Method method : methods ){
			if(method.getName().toLowerCase().contains(APPROVED_KEY) && method.getName().startsWith("get")){
				found = true;
				try {
					Object value = method.invoke(entity);
					if (value instanceof Boolean && BooleanUtils.isTrue((Boolean)value)){
						isAllApproved &= true;
					} else{
						isAllApproved &= false;
					}
				} catch (IllegalAccessException e) {
					logger.error("Metadata copy error with IllegalAccessException: ", e);
				} catch (IllegalArgumentException e) {
					logger.error("Metadata copy error with IllegalArgumentException: ", e);
				} catch (InvocationTargetException e) {
					logger.error("Metadata copy error with InvocationTargetException: ", e);
				}
			}
		}
		if (found==true && isAllApproved==true)
			return true;
		else
			return false;
	}

	public static void getOldVersion(ProcessContext kcontext) {
		@SuppressWarnings("unchecked")
		List<Object> versions = (List<Object>) kcontext.getVariable(VERSIONS_KEY);

		String lastApprovedVersion = versions.remove(versions.size()-1).toString();
		kcontext.setVariable(LAST_APPROVED_VERSION, lastApprovedVersion);
	}

}
