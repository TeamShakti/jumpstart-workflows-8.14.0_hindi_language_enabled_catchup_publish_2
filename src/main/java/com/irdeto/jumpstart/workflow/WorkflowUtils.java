package com.irdeto.jumpstart.workflow;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_EMPTY_JSON_ARRAYS;
import static com.irdeto.domain.constants.TaskHandlerConstants.EXIT_MESSAGE_KEY;
import static com.irdeto.domain.constants.TaskHandlerConstants.EXIT_STATUS_KEY;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.ProcessContext;
import org.kie.api.runtime.process.ProcessInstance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.irdeto.manager.task.BeanUtil;
import com.irdeto.manager.workflow.ProcessExecutionManager;

public class WorkflowUtils {
    public static final ObjectMapper JSON_MAPPER =
            new ObjectMapper()
                   .registerModule(new JodaModule())
                   .configure(WRITE_DATES_AS_TIMESTAMPS, false)
                   .configure(WRITE_EMPTY_JSON_ARRAYS, false);

    private WorkflowUtils() {
    }

    public static void reportError(ProcessContext kcontext, String message) {
        Map<String, String> data = new HashMap<>();
        data.put(EXIT_STATUS_KEY, "Error");
        data.put(EXIT_MESSAGE_KEY, message);

        ProcessInstance instance = kcontext.getProcessInstance();
        BeanUtil.getBean(ProcessExecutionManager.class)
            .signalProcessInstance(instance == null ? -1 : instance.getId(),
                                   "Error",
                                   data,
                                   true);
    }
}
