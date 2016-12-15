package com.irdeto.jumpstart.workflow.purge;

import org.kie.api.runtime.process.ProcessContext;

import com.irdeto.jumpstart.domain.purge.PurgeWrapper;
import com.irdeto.jumpstart.workflow.EternalWorkflowDecorator;
import com.irdeto.jumpstart.workflow.EternalWorkflowDecoratorFactory;

public class CancelTasksHelper {
	public static final String PURGE_WRAPPER = "purgeWrapper";

	public static final String CONTENT_QA_DECORATOR = "contentQADecorator";
	public static final String METADATA_QA_DECORATOR = "metadataQADecorator";

	public static void setup(ProcessContext kcontext) {
		PurgeWrapper purgeWrapper = (PurgeWrapper) kcontext.getVariable(PURGE_WRAPPER);

		EternalWorkflowDecorator eternalWorkflowDecorator = EternalWorkflowDecoratorFactory.getInstance(
				purgeWrapper.getEntity()
		);

		kcontext.setVariable(CONTENT_QA_DECORATOR, eternalWorkflowDecorator.getContentQADecorator());
		kcontext.setVariable(METADATA_QA_DECORATOR, eternalWorkflowDecorator.getMetadataQADecorator());
	}
}
