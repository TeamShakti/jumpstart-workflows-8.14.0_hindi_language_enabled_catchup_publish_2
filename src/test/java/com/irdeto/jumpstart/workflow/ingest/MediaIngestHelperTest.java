package com.irdeto.jumpstart.workflow.ingest;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class MediaIngestHelperTest {

	@Test
	public void testCreateEntity() {
		Map<String, Object> ingestData = new HashMap<>();
		String uriid = "com.irdeto/1234";
		String sourceUrl = "something-320-somethingelse.png";

		ingestData.put(MediaIngestHelper.URI_ID_KEY, uriid);
		ingestData.put(MediaIngestHelper.FILENAME_KEY, sourceUrl);

		String entityType = WorkflowHelper.ENTITY_TYPE_VIDEO_CONTENT;
		Content content = MediaIngestHelper.createEntity(ingestData, entityType);
		Assert.assertNotNull(content);
		Assert.assertEquals(content.getClass(), VideoContent.class);
		Assert.assertEquals(content.getSourceUrl(), sourceUrl);

		entityType = WorkflowHelper.ENTITY_TYPE_IMAGE_CONTENT;
		content = MediaIngestHelper.createEntity(ingestData, entityType);
		Assert.assertNotNull(content);
		Assert.assertEquals(content.getClass(), ImageContent.class);
		Assert.assertEquals(content.getSourceUrl(), sourceUrl);
	}

	@Test
	public void failCreateEntity() {
		Map<String, Object> ingestData = new HashMap<>();
		String uriid = "com.irdeto/1234";
		String filename = "something-320-somethingelse.png";

		ingestData.put(MediaIngestHelper.URI_ID_KEY, uriid);
		ingestData.put(MediaIngestHelper.FILENAME_KEY, filename);

		String entityType = "bogus";
		Content content = MediaIngestHelper.createEntity(ingestData, entityType);
		Assert.assertNull(content);
	}
}