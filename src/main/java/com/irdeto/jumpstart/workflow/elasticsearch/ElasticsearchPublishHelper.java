package com.irdeto.jumpstart.workflow.elasticsearch;

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
import com.irdeto.jumpstart.domain.taskhandler.ElasticsearchTaskHandler;
import com.irdeto.jumpstart.factory.WrapperWithDestinations;
import com.irdeto.jumpstart.workflow.WorkflowArgumentException;
import com.irdeto.jumpstart.workflow.publish.reference.ReferenceDocumentMapper;
import com.irdeto.jumpstart.workflow.publish.reference.ReferenceMapperFactory;

public class ElasticsearchPublishHelper {
	private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchPublishHelper.class);

	public static final String DOCUMENT = "document";
	public static final String COMMAND = "command";

	public static void prepareVariables(ProcessContext kcontext, Object wrapper) {
		reportError(kcontext, "Unsupported wrapper object " + wrapper.getClass());
	}

	public static void prepareDocument(ProcessContext kcontext, WrapperWithDestinations<?> wrapper)
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

		Map<String, Object> map = new HashMap<>();
		map.put(COMMAND, ElasticsearchTaskHandler.COMMAND_INDEX);
		map.put(DOCUMENT, referenceDocument);
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
		ReferenceDocumentMapper<?> mapper =
				ReferenceMapperFactory.getInstance(wrapper);
		if (mapper == null) {
			LOG.error("Could not get reference document mapper for {}.", wrapper.getClass());
			throw new WorkflowArgumentException("Could not get reference document mapper.");
		}

		return mapper.getReferenceDocument();
	}

	private static Map<String, Object> buildVariablesMap(PurgeWrapper<?> wrapper) throws Exception {
		ReferenceDocument referenceDocument = getReferenceDocument(wrapper);

		Map<String, Object> map = new HashMap<>();
		map.put(COMMAND, ElasticsearchTaskHandler.COMMAND_DELETE);
		map.put(DOCUMENT, referenceDocument);
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

		ReferenceDocumentMapper<?> mapper = ReferenceMapperFactory.getInstance(publishWrapper);
		if (mapper == null) {
			LOG.error("Could not get reference document mapper for {}.", wrapper.getClass());
			throw new WorkflowArgumentException("Could not get reference document mapper.");
		}

		return mapper.getReferenceDocument();
	}
}
