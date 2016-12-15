package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.domain.qts.response.QTSFileInfo;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.Locale;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.ProtectVideoSub;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.TransVideoSub;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.VideoSubcontent;

public class WorkflowHelperTest {
	public static Program getDummyProgram(String type) {
		Program program = new Program();
		program.setYearOfRelease(2010);
		program.setStartDateTime(WorkflowHelper.START_OF_TIME);
		program.setEndDateTime(WorkflowHelper.END_OF_TIME);
		Locale titleBrief = new Locale();
		LocaleHelper.setStringValueForDefaultLanguage(titleBrief, "Mad Max");
		program.setTitleBrief(titleBrief);
		Locale summaryLong = new Locale();
		LocaleHelper.setStringValueForDefaultLanguage(summaryLong, "A guy does stuff in Australia");
		program.setSummaryLong(summaryLong);
		program.setId("123");
		program.setCreatedDate(WorkflowHelper.START_OF_TIME);
		VideoContent videoContent = new VideoContent();
		videoContent.setContentType(VideoContent.ContentType.MOVIE);
		videoContent.setId("123");
		videoContent.setType("videoContent");
		videoContent.setSourceVersion(1);
		videoContent.setCreatedDate(WorkflowHelper.START_OF_TIME);
		program.getVideoContent().add(videoContent);
		
		if (WorkflowHelper.SOURCE_TYPE.equals(type)) {
			videoContent.getSubcontent().add(getSourceVideoSubcontent("1"));
			videoContent.getSubcontent().add(getSourceVideoSubcontent("2"));
		}
		if (WorkflowHelper.TRANSCODED_TYPE.equals(type) || WorkflowHelper.PROTECTED_TYPE.equals(type)) {
			SourceVideoSub sourceVideoSubcontent = getSourceVideoSubcontent();
			videoContent.getSubcontent().add(getSourceVideoSubcontent());
			sourceVideoSubcontent.getTransSubs().add(getTranscodedVideoSubcontent());
		}
		if (WorkflowHelper.PROTECTED_TYPE.equals(type)) {
			SourceVideoSub sourceVideoSubcontent = getSourceVideoSubcontent();
			videoContent.getSubcontent().add(getSourceVideoSubcontent());
			TransVideoSub transcodedVideoSubcontent = getTranscodedVideoSubcontent();
			sourceVideoSubcontent.getTransSubs().add(transcodedVideoSubcontent);
			sourceVideoSubcontent.getProtectSubs().add(getProtectedVideoSubcontent());
		}
		return program;
	}
	
	private static SourceVideoSub getSourceVideoSubcontent() {
		return getSourceVideoSubcontent("123");
	}

	private static SourceVideoSub getSourceVideoSubcontent(String id) {
		SourceVideoSub movieSubcontent = new SourceVideoSub();
		movieSubcontent.setId(id);
		movieSubcontent.setType("movieSubcontent");
		movieSubcontent.setSourcePath("https://s3-us-west-2.amazonaws.com/jumpstart-mezzanine/movie-1-m1.mov");
		movieSubcontent.setCreatedDate(WorkflowHelper.START_OF_TIME.plusDays(Integer.valueOf(id)));

		return movieSubcontent;
	}
	
	private static TransVideoSub getTranscodedVideoSubcontent() {
		TransVideoSub movieSubcontent = new TransVideoSub();
		movieSubcontent.setId("123");
		movieSubcontent.setType("movieSubcontent");
		movieSubcontent.setSourcePath("https://s3-us-west-2.amazonaws.com/jumpstart-transcoded/movie-1-m1-256.mp4");
		movieSubcontent.setBitRate(1500);
		movieSubcontent.setCreatedDate(WorkflowHelper.START_OF_TIME);
		return movieSubcontent;
	}

	private static ProtectVideoSub getProtectedVideoSubcontent() {
		ProtectVideoSub movieSubcontent = new ProtectVideoSub();
		movieSubcontent.setId("123");
		movieSubcontent.setType("movieSubcontent");
		movieSubcontent.setSourcePath("test.mp4");
		movieSubcontent.setCreatedDate(WorkflowHelper.START_OF_TIME);
		return movieSubcontent;
	}

	protected List<QTSFileInfo> getDummyFileInfoList() {
		List<QTSFileInfo> fileInfoList = new ArrayList<>();
		QTSFileInfo fileInfo = new QTSFileInfo() {
			@Override
			public String getFileSize() {
				return "1024";
			}

			@Override
			public String getCreationDate() {
				return "2001-01-01T00:00:00+0100";
			}

			@Override
			public String getFps() {
				return "10";
			}

			@Override
			public String getName() {
				return "\\\\AMAZONA-JVV6AJG\\jumpstart\\CPSPROTECTED\\IRDE2012000000004291-1-en_US\\program-5-en_US\\AC_PR_HLS\\IRDE2012000000004291-1-en_US.m3u8";
			}

			@Override
			public String getVideoBitRate() {
				return "1200";
			}

			@Override
			public String getDuration() {
				return "1";
			}
		};
		fileInfoList.add(fileInfo);
		return fileInfoList;
	}
		
	@Test
	public void testIsSuperclass() {
		Assert.assertTrue(WorkflowHelper.isSuperclass(Content.class, VideoContent.class));
		Assert.assertTrue(WorkflowHelper.isSuperclass(VideoSubcontent.class, SourceVideoSub.class));
		Assert.assertTrue(WorkflowHelper.isSuperclass(Object.class, SourceVideoSub.class));
	}
	
	@Test
	public void testParentCategoryId() {
		Channel channel = new Channel();
		channel.setId("123");
		Assert.assertEquals(WorkflowHelper.getParentCategoryId(channel).intValue(), 4);
		Assert.assertEquals(WorkflowHelper.getCategoryId(channel).intValue(), 400000123);
	}
	
	@Test
	public void testGetLatestSourceVideoSubcontent() {
		Program program = getDummyProgram(WorkflowHelper.SOURCE_TYPE);
		SourceVideoSub sourceVideoSub = WorkflowHelper.getLatestSourceVideoSubcontent(program.getVideoContent().get(0));
		Assert.assertEquals(sourceVideoSub.getId(), "2");
	}
	
}
