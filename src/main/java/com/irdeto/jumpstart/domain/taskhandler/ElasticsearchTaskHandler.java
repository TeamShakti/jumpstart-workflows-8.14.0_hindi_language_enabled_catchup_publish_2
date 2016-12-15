package com.irdeto.jumpstart.domain.taskhandler;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.constants.TaskResult;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.web.WebManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("Elasticsearch")
public class ElasticsearchTaskHandler extends AbstractTaskHandler {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchTaskHandler.class);

    public static final String COMMAND_CREATE = "create";
    public static final String COMMAND_INDEX = "index";
    public static final String COMMAND_UPDATE = "update";
    public static final String COMMAND_DELETE = "delete";

    @TaskProperty(required = false)
    public static final String ELASTICSEARCH_URL = "URL";
    @TaskProperty(required = false)
    public static final String ELASTICSEARCH_INDEX = "Index";
    @TaskProperty(required = false)
    public static final String ELASTICSEARCH_TYPE = "Type";

    @TaskProperty
    public static final String COMMAND_PROPERTY = "Command";
    @TaskProperty(type = ReferenceDocument.class)
    public static final String ENTITY_PROPERTY = "Entity";

    @TaskResult
    public static final String RESPONSE_PROPERTY = "Response";

    @Resource(name = "propertiesManager")
    private PropertiesManager propertiesManager;

    @Resource(name = "webManager")
    private WebManager webManager;

    private ObjectMapper jsonMapper;

    public ElasticsearchTaskHandler() {
        // TODO: shall we replace it with WorkflowUtils.JSON_MAPPER ?
        this.jsonMapper = new ObjectMapper();

        this.jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.jsonMapper.registerModule(new JodaModule());

        this.jsonMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
    }

    @Override
    public void processTask(
            long workItemId, String workItemName,
            Map<String, Object> verifiedParameters, Map<String, Object> results)
            throws Exception {
        String url = (String) verifiedParameters.get(ELASTICSEARCH_URL);
        String index = (String) verifiedParameters.get(ELASTICSEARCH_INDEX);
        String type = (String) verifiedParameters.get(ELASTICSEARCH_TYPE);
        String command = (String) verifiedParameters.get(COMMAND_PROPERTY);

        ReferenceDocument entity = (ReferenceDocument) verifiedParameters.get(ENTITY_PROPERTY);
        if (StringUtils.isEmpty(entity.getIndexId())) {
            throw new Exception("Entity cannot be published to Elasticsearch because the IndexId is empty or null.");
        }

        url = isBlank(url) ? propertiesManager.getProperty(JumpstartPropertyKey.ELASTICSEARCH_URL) : url;
        index = isBlank(index) ? propertiesManager.getProperty(JumpstartPropertyKey.ELASTICSEARCH_INDEX_NAME) : index;
        type = isBlank(type) ? entity.getType() : type;
        String documentId = entity.getIndexId();

        String apiUrl = String.format("%s/%s/%s/%s", url, index, type, documentId);
        logger.debug("Elasticsearch Entity URL: " + apiUrl);

        switch (command.toLowerCase()) {
            case COMMAND_INDEX:
            case COMMAND_UPDATE:
            case COMMAND_CREATE:
                String serializedEntity = jsonMapper.writeValueAsString(entity);
                webManager.callWebService(apiUrl, WebManager.REQUEST_METHOD_PUT, serializedEntity, WebManager.JSON_CONTENT_TYPE, new HashMap<>(), new Integer[]{200, 201});
                break;

            case COMMAND_DELETE:
                webManager.callWebService(apiUrl, WebManager.REQUEST_METHOD_DELETE, null, WebManager.JSON_CONTENT_TYPE, new HashMap<>(), new Integer[]{200, 201, 404});
                break;

            default:
                throw new Exception("Command " + command + " not known.");
        }
    }

    protected ObjectMapper getJsonMapper() {
        return jsonMapper;
    }
}
