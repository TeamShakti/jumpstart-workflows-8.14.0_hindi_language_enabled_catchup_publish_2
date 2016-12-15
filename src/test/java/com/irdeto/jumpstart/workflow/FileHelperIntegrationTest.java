package com.irdeto.jumpstart.workflow;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.BaseProtection.ProtectionType;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.ProtectVideoSub;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.workflow.FileHelper.FileIngestHelper;
import com.irdeto.jumpstart.workflow.FileHelper.FileProtectHelper;
import com.irdeto.jumpstart.workflow.FileHelper.FilePublishHelper;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.secondstagebean.SecondStageBeanManager;
import com.irdeto.manager.task.BeanUtil;

@Test
public class FileHelperIntegrationTest extends WorkflowHelperTest {
	private ApplicationContext context = null;
	
	@BeforeClass
	public void setup(){
		context = new ClassPathXmlApplicationContext("/testApplicationContext.xml");
		BeanUtil.propertiesManager = context.getBean(PropertiesManager.class);
		context.getBean(SecondStageBeanManager.class);
	}

	public void testGeneralFileMethods() {
		String filename = "/baseurl/folder/irdetocom2FMovie2F201300014002-Movie-en_US-1.mp4";
		String windowsFilename = "\\\\baseurl\\folder1\\folder2\\irdetocom2FMovie2F201300014002-Movie-en_US-1.mp4";

		Assert.assertEquals(FileHelper.getExtension(filename),"mp4");
		Assert.assertEquals(FileHelper.getFilenameWithoutPathAndExt(filename),"irdetocom2FMovie2F201300014002-Movie-en_US-1");
		Assert.assertEquals(FileHelper.getFilenameRoot(filename, 1),"irdetocom2FMovie2F201300014002-Movie-en_US");
		Assert.assertEquals(FileHelper.getLastFoldername(filename),	"folder");
		Assert.assertEquals(FileHelper.getExtension(windowsFilename),"mp4");
		Assert.assertEquals(FileHelper.getFilenameWithoutPathAndExt(windowsFilename),"irdetocom2FMovie2F201300014002-Movie-en_US-1");
		Assert.assertEquals(FileHelper.getFilenameRoot(windowsFilename, 1),"irdetocom2FMovie2F201300014002-Movie-en_US");
		Assert.assertEquals(FileHelper.getLastFoldername(windowsFilename),	"folder2");
		Assert.assertEquals(FileHelper.getLastFoldername("\\\\AMAZONA-JVV6AJG\\jumpstart\\CPSSOURCE\\movie-1-m2-ActiveCloak2GoDRM\\"),	"movie-1-m2-ActiveCloak2GoDRM");
	}
	
	// **************************************************
	// Ingest
	// **************************************************
	public void testIngest() {
		String filename = "/path/irdeto%2Ecom%2FMovie%2F201300014002-videoContent-en_US.mp4";
		Integer version = 1;
				
		String filenameWithoutPath = FileHelper.getFilenameWithoutPath(filename);
				
		Assert.assertEquals(
				filenameWithoutPath,
				"irdeto%2Ecom%2FMovie%2F201300014002-videoContent-en_US.mp4");
		Assert.assertEquals(
				FileIngestHelper.getIngestFilenameCharactersStripped(filename),
				"irdeto2Ecom2FMovie2F201300014002-videoContent-en_US.mp4");
		Assert.assertEquals(
				FileIngestHelper.getIngestFilenameWithVersion(filenameWithoutPath, version),
				"irdeto%2Ecom%2FMovie%2F201300014002-videoContent-en_US-1.mp4");
		Assert.assertEquals(
				FileIngestHelper.getIngestEntityTypeFromFilename(filenameWithoutPath),
				"videoContent");
		
		VideoContent videoContent = new VideoContent();
		videoContent.setType("videoContent");
		videoContent.setId("1");
		videoContent.setCreatedDate(WorkflowHelper.START_OF_TIME);
		videoContent.setSourceVersion(1);
		videoContent.setSourceUrl("test.mp4");
		String mezzanineFilename = FileIngestHelper.getSourceFilename(videoContent);
		Assert.assertTrue(mezzanineFilename.startsWith("videoContent-1-"));
		Assert.assertTrue(mezzanineFilename.endsWith("-m1.mp4"));
		String mezzanineFileUrl = FileIngestHelper.getSourceFileUrl(videoContent);
		Assert.assertTrue(mezzanineFileUrl.startsWith("https://s3-us-west-2.amazonaws.com/jumpstart-mezzanine/videoContent-1-"));
		Assert.assertTrue(mezzanineFileUrl.endsWith("-m1.mp4"));
	}

	// **************************************************
	// Transcode
	// **************************************************
/*	public void testTranscode() {
		String s3UrlPrefix = "https://s3-us-west-2.amazonaws.com";
		Program program = getDummyProgram(WorkflowHelper.MEZZANINE_TYPE);
		Movie movie = program.getMovies().get(0);
		Subcontent sourceSubcontent = TranscodeHelper.getUntranscodedSourceSubcontent(movie);
		String sourceBucket = "mezzanine";
		String targetBucket = "transcode";
		Assert.assertEquals(
				FileTranscodeHelper.internalGetTranscodeSourceFilename(sourceSubcontent, sourceBucket),
				"s3://mezzanine/test.mp4");

		String basename = "test";
		Assert.assertEquals(
				FileTranscodeHelper.internalGetTranscodeTargetPath(basename),
				"s3://test");
		
		String filename = "irdetocom2FMovie2F201300014002-Movie-en_US-1-2048.mp4";
		Assert.assertEquals(
				FileTranscodeHelper.internalGetTranscodeSourceUrl(filename, s3UrlPrefix, targetBucket),
				"https://s3-us-west-2.amazonaws.com/transcode/irdetocom2FMovie2F201300014002-Movie-en_US-1-2048.mp4");
	}
	*/

	// **************************************************
	// Protect
	// **************************************************
	public void testProtectD2GValues() {
		String sourceFileD2G = "movie-1-hvgm1yd3-m1-2048.mp4";
		String mastersourceFile = "movie-1-hvgm1yd3-m1.mp4";
		String cpsPathRoot = "\\\\WIN-27B0HEBOR2B\\Multiscreen\\CPSSOURCE\\";
		String protectType = "ActiveCloak2GoDRM";
		Integer policyGroupId = 4;
		Assert.assertEquals(
				FileProtectHelper.internalGetCpsSourceFolder(mastersourceFile, cpsPathRoot, protectType,policyGroupId),
				"\\\\WIN-27B0HEBOR2B\\Multiscreen\\CPSSOURCE\\movie-1-hvgm1yd3-m1-4-ActiveCloak2GoDRM\\");
		Assert.assertEquals(
				FileProtectHelper.internalGetD2gRenamedFilename(sourceFileD2G),
				"movie-1-hvgm1yd3-m1-4-2048-AC_D2G.mp4");
	}
	
	// **************************************************
	// Publish
	// **************************************************
	public void testPublish() {
		
		// Video
		VideoContent videoContent = new VideoContent();
		videoContent.setContentType(VideoContent.ContentType.MOVIE);
		videoContent.setPublishVersion(10);

		SourceVideoSub sourceVideoSubcontent = new SourceVideoSub();
		sourceVideoSubcontent.setId("1");
		sourceVideoSubcontent.setType("movieSubcontent");
		sourceVideoSubcontent.setSourcePath("https://s3-us-west-2.amazonaws.com/jumpstart-mezzanine/movie-1-hvgm1yd3-m5.mp4");
		ProtectVideoSub protectedVideoSubcontent = new ProtectVideoSub();
		protectedVideoSubcontent.setId("1");
		protectedVideoSubcontent.setType("movieSubcontent");
		protectedVideoSubcontent.setSourcePath("https://s3-us-west-2.amazonaws.com/jumpstart-protected/HLS/movie-1-hvgm1yd3-m5/movie-1-hvgm1yd3-m5.m3u8");
		
		Protect protect = new Protect();
		protect.setProtectionType(ProtectionType.ACTIVE_CLOAK.toString());
		
		Assert.assertEquals(
				FilePublishHelper.getConsumerUrl(10, sourceVideoSubcontent, protectedVideoSubcontent, protect),
				"http://cloudfronthttp.jumpstart.irdeto.com/HLS/movie-1-hvgm1yd3-v10/movie-1-hvgm1yd3-m5/movie-1-hvgm1yd3-m5.m3u8");

		// Image
		ImageContent imageContent = new ImageContent();
		imageContent.setContentType(ImageContent.ContentType.BOX_COVER);
		imageContent.setPublishVersion(10);

		ImageSubcontent imageSubcontent = new ImageSubcontent();
		imageSubcontent.setId("1");
		imageSubcontent.setType("boxCoverSubcontent");
		imageSubcontent.setSourcePath("https://s3-us-west-2.amazonaws.com/jumpstart-mezzanine/boxCover-1-abcdef-m4.png");

		Assert.assertEquals(
				FilePublishHelper.getConsumerUrl(10, imageSubcontent),
				"http://cloudfronthttp.jumpstart.irdeto.com/HLS/boxCover-1-abcdef-v10/boxCover-1-abcdef-m4.png");
	}
}
