package com.irdeto.jumpstart.workflow.activemq;

import static com.irdeto.jumpstart.domain.publish.PublishWrapper.ACTION_PUBLISH;
import static com.irdeto.jumpstart.factory.WrapperWithDestinations.ACTION_PUBLISH_CREATE;
import static com.irdeto.jumpstart.factory.WrapperWithDestinations.ACTION_PUBLISH_UPDATE;
import static com.irdeto.jumpstart.factory.WrapperWithDestinations.ACTION_PURGE;
import static com.irdeto.jumpstart.workflow.WorkflowUtils.JSON_MAPPER;
import static com.irdeto.jumpstart.workflow.WorkflowUtils.reportError;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.domain.publish.PublishWrapperFactory;
import com.irdeto.jumpstart.domain.purge.PurgeWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.factory.WrapperWithDestinations;
import com.irdeto.jumpstart.workflow.WorkflowArgumentException;
import com.irdeto.jumpstart.workflow.publish.reference.ReferenceDocumentMapper;
import com.irdeto.jumpstart.workflow.publish.reference.ReferenceMapperFactory;

@SuppressWarnings("unused")
public class ActiveMQPublishHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQPublishHelper.class);

    public static final String MESSAGE_PROPERTIES = "messageProperties";
    public static final String DOCUMENT = "document";

    private static final String ENTITY_TYPE = "EntityType";
    private static final String MODE = "Mode";
    private static final String GRACE_PERIOD = "GracePeriod";
    private static final String ACTION = "Action";
    private static final String PURGE_DELAY = "PurgeDelay";


    public static void prepareMessage(ProcessContext kcontext, Object wrapper) {
        reportError(kcontext, "Unsupported wrapper object " + wrapper.getClass());
    }

    public static void prepareMessage(ProcessContext kcontext, WrapperWithDestinations<?> wrapper)
            throws Exception {
        Map<String, Object> variables;
        try {
            if (wrapper instanceof PublishWrapper) {
                variables = buildVariablesMap((PublishWrapper<?>) wrapper);
            } else if (wrapper instanceof PurgeWrapper) {
                variables = buildVariablesMap((PurgeWrapper<?>) wrapper);
            } else {
                throw new WorkflowArgumentException("Wrapper '" + wrapper.getClass() + "' is not supported.");
            }
        } catch (Throwable e) {
            reportError(kcontext, e.getMessage());
            return;
        }
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            kcontext.setVariable(entry.getKey(), entry.getValue());
        }
    }

    private static Map<String, Object> buildVariablesMap(PublishWrapper<?> wrapper)
            throws Exception {
        ReferenceDocument referenceDocument = getReferenceDocument(wrapper);

        Map<String, Object> props = new HashMap<>();
        props.put(ENTITY_TYPE, referenceDocument.getType());
        props.put(ACTION, referenceDocument.getLastPublishDateTime() == null ? ACTION_PUBLISH_CREATE : ACTION_PUBLISH_UPDATE);

        Map<String, Object> map = new HashMap<>();
        map.put(MESSAGE_PROPERTIES, props);
        map.put(DOCUMENT, JSON_MAPPER.writeValueAsString(referenceDocument));
        return map;
    }

    /**
     * Returns the reference document for {@link PublishWrapper}s.
     * @param wrapper wrapper to get document for.
     * @return reference document, if available.
     * @throws Exception in case of errors.
     * @throws WorkflowArgumentException in case, when workflow has unsupported parameters.
     */
    private static ReferenceDocument getReferenceDocument(PublishWrapper<?> wrapper)
            throws Exception {
        ReferenceDocumentMapper<?> instance =
            ReferenceMapperFactory.getInstance(wrapper);
        if (instance == null) {
            LOG.error("Could not get reference document mapper for {}.", wrapper.getClass());
            throw new WorkflowArgumentException("Could not get reference document mapper.");
        }
        return instance.getReferenceDocument();
    }

    private static Map<String, Object> buildVariablesMap(PurgeWrapper<?> wrapper) throws Exception {
        ReferenceDocument referenceDocument = getReferenceDocument(wrapper);

        Map<String, Object> props = new HashMap<>();
        props.put(ENTITY_TYPE, referenceDocument.getType());
        props.put(ACTION, ACTION_PURGE);

        Map<String, Object> map = new HashMap<>();
        map.put(MESSAGE_PROPERTIES, props);
        map.put(DOCUMENT, JSON_MAPPER.writeValueAsString(referenceDocument));
        return map;
    }

    /**
     * The ugly way to obtain reference document for {@link PurgeWrapper}s.
     * @param wrapper wrapper to get document for.
     * @return reference document, if available.
     * @throws Exception in case of errors.
     * @throws WorkflowArgumentException in case, when workflow has unsupported parameters.
     */
    private static ReferenceDocument getReferenceDocument(final PurgeWrapper<?> wrapper)
            throws WorkflowArgumentException, Exception {
        PublishWrapper<?> publishWrapper = PublishWrapperFactory.getInstance(wrapper.getEntity());

        ReferenceDocumentMapper<?> instance = ReferenceMapperFactory.getInstance(publishWrapper);
        if (instance == null) {
            LOG.error("Could not get reference document mapper for {}.", wrapper.getClass());
            throw new WorkflowArgumentException("Could not get reference document mapper.");
        }
        return instance.getReferenceDocument();
    }
}
