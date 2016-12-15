package com.irdeto.jumpstart.workflow.purge;

import org.kie.api.runtime.process.ProcessContext;

import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.publish.PublishHelper;

/**
 * @deprecated use {@link com.irdeto.jumpstart.workflow.publish.PublishHelper} instead.
 */
public class PurgeHelper extends WorkflowHelper {
    private static final String PURGE_WRAPPER_KEY = "purgeWrapper";

    /**
     * @deprecated use {@link PublishHelper#populateEnabledEndpoints(ProcessContext, String)}.
     */
    @Deprecated
    public static void configureEndpoints(ProcessContext kcontext) {
        PublishHelper.populateEnabledEndpoints(kcontext, PURGE_WRAPPER_KEY);
    }
}
