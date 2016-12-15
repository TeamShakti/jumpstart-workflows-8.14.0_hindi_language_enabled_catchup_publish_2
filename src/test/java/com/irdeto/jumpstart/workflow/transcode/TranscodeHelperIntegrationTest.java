package com.irdeto.jumpstart.workflow.transcode;

import com.irdeto.domain.mediamanager.command.MaintainRelationshipsCommand;
import com.irdeto.domain.qts.response.QTSFileInfo;
import com.irdeto.domain.qts.response.elementalcloud.FileFileInfo;
import com.irdeto.jumpstart.domain.config.Transcode;
import com.irdeto.jumpstart.domain.transcode.TranscodeWrapper;
import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.properties.PropertiesManagerImpl;
import com.irdeto.manager.task.BeanUtil;
import com.irdeto.test.BaseSpringTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class TranscodeHelperIntegrationTest extends BaseSpringTest {
	public void testGetTranscodeProfileRelationshipsList() {
		PropertiesManagerImpl propertiesManager = (PropertiesManagerImpl)context.getBean("propertiesManager");
		ClassManager classManager = (ClassManager)context.getBean("classManager");
		propertiesManager.secondStageSetup(classManager);
		BeanUtil.setContext(context.getDependencyInjectionContext());

		List<QTSFileInfo> transcodedFileInfoList = new ArrayList<>();
		FileFileInfo qtsFileInfo = new FileFileInfo();
		qtsFileInfo.setName("jumpstart-transcoded/videoContent-9-hz2dgzaw-m1-1500.mp4");
		transcodedFileInfoList.add(qtsFileInfo);
		TranscodeWrapper transcodeWrapper = new TranscodeWrapper();
		transcodeWrapper.getTranscodeGroup().add(getSSTranscode());
		transcodeWrapper.getTranscodeGroup().add(getHLSTranscode());
		transcodeWrapper.getTranscodeGroup().add(getD2GTranscode());
		List<MaintainRelationshipsCommand> relationshipsList =
				TranscodeHelper.getTranscodeProfileRelationshipsList(transcodedFileInfoList, transcodeWrapper);
		Assert.assertEquals(relationshipsList.size(), 2);
	}

	private Transcode getSSTranscode() {
		Transcode transcode = new Transcode();
		transcode.setTranscodedFilePattern("(.)*\\.ism[cva]{0,1}$");
		return transcode;
	}

	private Transcode getHLSTranscode() {
		Transcode transcode = new Transcode();
		transcode.setTranscodedFilePattern("(.)*\\.mp4$");
		return transcode;
	}

	private Transcode getD2GTranscode() {
		Transcode transcode = new Transcode();
		transcode.setTranscodedFilePattern("(.)*-1500\\.mp4$");
		return transcode;
	}
}
