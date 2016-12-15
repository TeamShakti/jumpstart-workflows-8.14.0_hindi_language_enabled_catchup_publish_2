package com.irdeto.jumpstart.domain.taskhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.constants.TaskResult;
import com.irdeto.jumpstart.domain.control.qsa.ControlQSAAuthorizationInfo;
import com.irdeto.jumpstart.domain.control.qsa.ControlQSACategory;
import com.irdeto.jumpstart.manager.ControlQuerySessionAuthorizationManager;
import com.irdeto.jumpstart.manager.ControlQuerySessionAuthorizationManagerImpl;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("ControlQuerySessionAuthorization")
public class ControlQuerySessionAuthorizationTaskHandler extends AbstractTaskHandler {
	@Resource(name="propertiesManager")
	private PropertiesManager propertiesManager;

	@Resource(name="webManager")
	private WebManager webManager;

	@Resource(name="xmlManager")
	private XmlManager xmlManager;

	private ControlQuerySessionAuthorizationManager controlQueryAuthorizationManager;
	
	@TaskProperty
	private static final String CONTENT_ID_KEY = "ContentId";
	@TaskProperty
	private static final String CONTENT_ID_LIST_KEY = "ContentIdList";
	
	@TaskResult
	private static final String AUTHORIZATION_INFO_KEY = "AuthorizationInfo";
	@TaskResult
	private static final String AUTHORIZATION_INFO_LIST_KEY = "AuthorizationInfoList";
	@TaskResult
	private static final String CATEGORY_LIST_KEY = "CategoryList";
	
	@PostConstruct
	protected void setupControlQuerySessionAuthorizationManager() {
		controlQueryAuthorizationManager = new ControlQuerySessionAuthorizationManagerImpl(propertiesManager, webManager, xmlManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processTask(long workItemId, String workItemName,
			Map<String, Object> params, Map<String, Object> results)
			throws Exception {
		List<String> contentIdList = (List<String>)params.get(CONTENT_ID_LIST_KEY);
		if (contentIdList == null) {
			contentIdList = new ArrayList<>();
		}
		String contentId = (String)params.get(CONTENT_ID_KEY);
		if (StringUtils.isNotBlank(contentId)) {
			contentIdList.add(contentId);
		}
		
		List<ControlQSAAuthorizationInfo> controlAuthorizationInfoList = controlQueryAuthorizationManager.getAuthorizationInfoList(contentIdList);
		if (controlAuthorizationInfoList != null && !controlAuthorizationInfoList.isEmpty()) {
			results.put(AUTHORIZATION_INFO_KEY, controlAuthorizationInfoList.get(0));
		}
		results.put(AUTHORIZATION_INFO_LIST_KEY, controlAuthorizationInfoList);
		List<ControlQSACategory> categoryList = new ArrayList<>();
		for (ControlQSAAuthorizationInfo controlAuthorizationInfo: controlAuthorizationInfoList) {
			categoryList.addAll(controlAuthorizationInfo.getCategoryList());
		}
		results.put(CATEGORY_LIST_KEY, categoryList);
	}

}
