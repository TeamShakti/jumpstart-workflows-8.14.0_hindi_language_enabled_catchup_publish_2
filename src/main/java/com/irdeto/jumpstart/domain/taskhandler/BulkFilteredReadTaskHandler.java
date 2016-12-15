package com.irdeto.jumpstart.domain.taskhandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.constants.TaskResult;
import com.irdeto.domain.operation.MM8Response;
import com.irdeto.domain.operation.ResponseType;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.mediamanager.MediaManager;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.workflow.WorkItemManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("BulkFilteredReadTask")
public class BulkFilteredReadTaskHandler extends AbstractTaskHandler {
	@TaskProperty(type=List.class)
	public static final String QUERY_FILTER_WRAPPER_LIST_PROPERTY = "QueryFilterWrapperList";

	@TaskResult
	public static final String RESULT_MAP_PROPERTY = "ResultMap";

	@Resource(name="propertiesManager")
	private PropertiesManager propertiesManager;

	@Resource(name="mediaManager8Rest")
	private MediaManager mediaManager;

	@Resource(name="workItemManager")
	private WorkItemManager workItemManager;

	@SuppressWarnings("unchecked")
	@Override
	public void processTask(long workItemId, String workItemName,
			Map<String, Object> verifiedParameters, Map<String, Object> results)
			throws Exception {
		List<QueryFilterWrapper> queryFilterWrapperList = (List<QueryFilterWrapper>)verifiedParameters.get(QUERY_FILTER_WRAPPER_LIST_PROPERTY);
		ConcurrentHashMap<Integer, Object> resultMap = new ConcurrentHashMap<>();
		ThreadPoolFactory.executeLoop(queryFilterWrapperList, this, ReadEntity.class, resultMap);
		results.put(RESULT_MAP_PROPERTY, resultMap);
	}

	class ReadEntity extends ThreadPoolFactory.ItemRunnable<QueryFilterWrapper> {
		@Override
		public void run() throws Throwable {
			QueryFilterWrapper queryFilterWrapper = getItem();
			MM8Response response = null;
			try {
				response = mediaManager.readEntity(queryFilterWrapper.getEntityType(), queryFilterWrapper.getQueryFilterParameter(), null, null);
			} catch (Throwable e) {
				throw new Exception("Bulk Filtered Read failure for filter " + queryFilterWrapper.toString(), e);
			}
			if (response == null) {
				throw new Exception("Bulk Filtered Read failure, null response for filter " + queryFilterWrapper.toString());
			}
			if (!ResponseType.SUCCESS.equals(response.getResponse())) {
				throw new Exception("Bulk Filtered Read failure, " + response.getResponseCode() + " - " + response.getResponseData() + " for filter " + queryFilterWrapper.toString());
			}
			getResultMap().put(getPosition(), WorkflowHelper.getEntityList(BaseEntity.class, response));
		}
	}
}
