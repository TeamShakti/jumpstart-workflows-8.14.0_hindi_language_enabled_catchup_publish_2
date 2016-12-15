package com.irdeto.jumpstart.workflow.ingest;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.workflow.ingest.MediaIngestHelper;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.ingest.MediaIngest")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/ingest/MediaIngest.bpmn"})
public class MediaIngestIntegrationTest extends BaseProcessUnitTest {

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<>();
		
		data.put(MediaIngestHelper.ENTITY_TYPE_KEY, VideoContent.ContentType.MOVIE.toString());
		data.put(MediaIngestHelper.PODSERVER_KEY, "AMAZONA-JVV6AJG");
		data.put(MediaIngestHelper.FILENAME_KEY, "irdeto%2Ecom%2FBoxCover%2F401300017004-BoxCover-320-en_US.png");
		data.put(MediaIngestHelper.FILEPATH_KEY, "\\\\AMAZONA-JVV6AJG\\Jumpstart\\Namjun\\MOVIE\\processed\\");
		data.put("mdIdentifier", "movie");
		
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
