package com.irdeto.jumpstart.domain.property;

import com.irdeto.domain.constants.Constraints;

public class JumpstartPropertyKey {

	/**
	 * <B>Storage properties</B>
	 */
	@Constraints(allowSpaces=false, required=true, defaultValue="s3")
	public static final String STORAGE_TYPE_KEY = "storage.type";
	@Constraints(allowSpaces=false, required=false)
	public static final String S3_SECRET_KEY_KEY = "s3.secret.key";
	@Constraints(allowSpaces=false, required=false)
	public static final String S3_ACCESS_KEY_KEY = "s3.access.key";
	@Constraints(allowSpaces=false, required=false)
	public static final String S3_SOURCE_URL_KEY="s3.source.url";
	@Constraints(allowSpaces=false, required=false)
	public static final String SOURCE_URL_ROOT="source.url.root";
	@Constraints(allowSpaces=false, required=false)
	public static final String VM_BASE_URL_KEY = "vm.base.url";

	@Constraints(allowSpaces=false, required=true, defaultValue="/opt/data")
	public static final String CONFIG_DATA_DIR = "config.data.dir";
	@Constraints(allowSpaces=false, required=false, defaultValue="false")
	public static final String HUMAN_TASK_DISABLE_KEY="jumpstart.humantask.bypass";

	/**
	 * <B>Ingest properties</B>
	 */
	@Constraints(allowSpaces=false, required=false)
	public static final String S3_BUCKET_SOURCE_KEY = "s3.bucket.source";
	@Constraints(allowSpaces=false, required=false)
	public static final String VM_MEZZANINE_FOLDER_KEY = "vm.mezzanine.folder";
	//Cable labs validation related
	@Constraints(allowSpaces=false, required=true, defaultValue="true")
	public static final String INGEST_VOD_VALIDATION_ENABLE_KEY="ingest.vod.validation.enable";
	@Constraints(allowSpaces=false, required=false)
	public static final String INGEST_VOD_VALIDATION_SCHEMA_KEY = "ingest.vod.validation.schema";
	@Constraints(allowSpaces=false, required=true, defaultValue="CableLabs")
	public static final String INGEST_VOD_METADATA_MECHANISM_KEY = "ingest.vod.metadata.mechanism";
	/**
	 * <B>Transcode properties</B>
	 */
	@Constraints(allowSpaces=false, required=false)
	public static final String S3_BUCKET_TRANSCODED_KEY = "s3.bucket.transcoded";
	@Constraints(allowSpaces=false, required=false)
	public static final String VM_TRANSCODED_FOLDER_KEY = "vm.transcoded.folder";
	@Constraints(allowSpaces=false, required=true)
	public static final String EC2_SOURCE_PWD_KEY = "ec2.source.pwd";
	@Constraints(allowSpaces=false, required=true)
	public static final String EC2_SOURCE_UID_KEY = "ec2.source.uid";
	@Constraints(allowSpaces=false, required=true)
	public static final String EC2_TARGET_PWD_KEY = "ec2.target.pwd";
	@Constraints(allowSpaces=false, required=true)
	public static final String EC2_TARGET_UID_KEY = "ec2.target.uid";
	@Constraints(allowSpaces=false, required=true)
	public static final String ELEMENTAL_API_KEY = "elemental.api.key";
	@Constraints(allowSpaces=false, required=true, defaultValue="irdeto.services")
	public static final String ELEMENTAL_USERID = "elemental.userid";
	@Constraints(allowSpaces=false, required=false)
	public static final String TRANSCODE_URL_ROOT = "transcode.url.root";

	/**
	 * <B>Protect properties</B>
	 */
	@Constraints(allowSpaces=false, required=false)
	public static final String S3_BUCKET_PROTECTED_KEY = "s3.bucket.protected";
	@Constraints(allowSpaces=false, required=false)
	public static final String VM_PROTECTED_FOLDER_KEY = "vm.protected.folder";
	@Constraints(allowSpaces=false, required=true)
	public static final String CPS_PATH_ROOT_KEY = "cps.path.root";
	/**
	 * <B>us.path.root</B>
	 * <P>
	 * The path where the Unified Streaming manifest will be pushed to using QTS
	 * File Create prior to being pushed on to the protected bucket/storage.
	 * </P>
	 */
	@Constraints(allowSpaces=false, required=true)
	public static final String US_PATH_ROOT_KEY = "us.path.root";
	@Constraints(allowSpaces=false, required=true, defaultValue="localhost")
	public static final String CPS_HOST_KEY = "cps.host";
	@Constraints(allowSpaces=false, required=true)
	public static final String CPS_FTP_URL_KEY = "cps.ftp.url";
	@Constraints(allowSpaces=false, required=true)
	public static final String CPS_FTP_USER_KEY = "cps.ftp.user";
	@Constraints(allowSpaces=false, required=true)
	public static final String CPS_FTP_PASSWORD_KEY = "cps.ftp.password";
	@Constraints(allowSpaces=false, required=true)
	public static final String CPS_FTP_PATH_KEY = "cps.ftp.path";
	@Constraints(allowSpaces=false, required=true)
	public static final String CPS_ACCOUNT_ID_KEY = "cps.account.id";
	@Constraints(allowSpaces=false, required=true)
	public static final String CPS_LICENSE_SERVER_KEY = "cps.license.server";
	@Constraints(allowSpaces=false, required=true)
	public static final String CPS_MAN_PASSWORD_KEY = "cps.man.password";
	@Constraints(allowSpaces=false, required=true)
	public static final String CPS_MAN_SERVICE_URL_KEY = "cps.man.service.url";
	@Constraints(allowSpaces=false, required=true)
	public static final String CPS_MAN_USER_KEY = "cps.man.user";
	@Constraints(allowSpaces=false, required=true)
	public static final String CONTROL_CRM_ID_KEY = "control.crm.id";
	@Constraints(allowSpaces=false, required=true)
	public static final String CONTROL_ACCOUNT_ID_KEY = "control.account.id";
	@Constraints(allowSpaces=false, required=false, defaultValue="/opt/jbpm")
	public static final String US_ROOT_DIRECTORY_KEY = "us.root.directory";
	@Constraints(allowSpaces=true, required=false, defaultValue="root@localhost")
	public static final String US_SERVER_KEY = "us.server";
	//constants for publish to CDN
	@Constraints(allowSpaces=false, required=false)
	public static final String S3_BUCKET_CDN_KEY = "s3.bucket.cdn";
	@Constraints(allowSpaces=false, required=false)
	public static final String VM_CDN_FOLDER_KEY = "vm.cdn.folder";
	@Constraints(allowSpaces=false, required=true)
	public static final String CDN_HLS_PREFIX = "cdn.hls.prefix";
	@Constraints(allowSpaces=false, required=true)
	public static final String CDN_IIS_PREFIX = "cdn.iis.prefix";
	@Constraints(allowSpaces=false, required=false)
	public static final String CDN_US_PREFIX = "cdn.us.prefix";
	@Constraints(allowSpaces=false, required=true)
	public static final String CONTROL_CWS_URL_KEY = "control.cws.url";
	@Constraints(allowSpaces=false, required=true)
	public static final String CONTROL_CWS_USER_NAME_KEY = "control.cws.username";
	@Constraints(allowSpaces=false, required=true)
	public static final String CONTROL_CWS_PASSWORD_KEY = "control.cws.password";
	@Constraints(allowSpaces=false, required=true)
	public static final String CONTROL_LIVE_DRM_URL_KEY = "control.livedrm.url";

	@Constraints(allowSpaces=false, required=false)
	public static final String ELASTICSEARCH_URL = "elasticsearch.url";
	@Constraints(allowSpaces=false, required=false, defaultValue="metadata")
	public static final String ELASTICSEARCH_INDEX_NAME = "elasticsearch.indexname";

	@Constraints(allowSpaces=false, required=false)
	public static final String ACTIVEMQ_PUBLISH_URL = "activemq.publish.url";
	@Constraints(allowSpaces=false, required=false, defaultValue="MultiscreenPublish")
	public static final String ACTIVEMQ_PUBLISH_QUEUE = "activemq.publish.queue";

	@Constraints(allowSpaces=false, required=false)
	public static final String ACTIVEMQ_PUBLISH_CATCHUP_URL = "activemq.publish.catchup.url";
	@Constraints(allowSpaces=false, required=false, defaultValue="CatchupPublish")
	public static final String ACTIVEMQ_CATCHUP_PUBLISH_QUEUE = "activemq.publish.catchup.queue";
	/**
	 * <B>EPG properties</B>
	 */
	@Constraints(allowSpaces=false, required=true)
	public static final String DEFAULT_LIVE_IMAGE_KEY = "default.live.image";
	@Constraints(allowSpaces=false, required=true, defaultValue="true")
	public static final String INGEST_EPG_VALIDATION_ENABLE_KEY="ingest.epg.validation.enable";
	@Constraints(allowSpaces=false, required=false)
	public static final String INGEST_EPG_VALIDATION_SCHEMA_KEY = "ingest.epg.validation.schema";

	/**
	 * <B>General Workflow</B>
	 */
	@Constraints(allowSpaces=false, required=true)
	public static final String DEFAULT_VIEWNAME_KEY = "default.viewname";
	@Constraints(allowSpaces=false, required=true)
	public static final String HTS_DUE_PERIOD_KEY = "hts.dueperiod";
	@Constraints(allowSpaces=false, required=true, defaultValue="QA")
	public static final String HTS_QA_USER_KEY = "hts.qa.user";

	@Constraints(allowSpaces=false, required=false, defaultValue="commandLine")
	public static final String ACTIVEMQ_QUEUE_COMMAND_LINE_KEY = "activemq.queue.commandline";
	@Constraints(allowSpaces=false, required=false, defaultValue="60000")
	public static final String PROFILE_READ_CACHE_DURATION_PROPERTY = "profile.read.cache.duration";

	@Constraints(allowSpaces=false, required=false, defaultValue="315360000")
	public static final String S3_SIGNATURE_EXPIRY_KEY = "s3.signature.expiry";
	@Constraints(allowSpaces=false, required=false, defaultValue="http://localhost:8161/demo/message/commandLine?type=queue")
	public static final String COMMAND_LINE_CALLBACK_URL_KEY = "command.line.callback.url";
	@Constraints(allowSpaces=false, required=false, defaultValue="com.irdeto.jumpstart")
	public static final String FACTORY_BASE_PACKAGE_KEY = "factory.base.package";
	@Constraints(allowSpaces=false, required=false, defaultValue="10")
	public static final String TASK_HANDLER_THREAD_POOL_SIZE_KEY = "taskhandler.threadpool.size";
	@Constraints(allowSpaces=false, required=false, defaultValue="10")
	public static final String TASK_HANDLER_THREAD_POOL_MAX_SIZE_KEY = "taskhandler.threadpool.maxsize";
	@Constraints(allowSpaces=false, required=false, defaultValue="300")
	public static final String TASK_HANDLER_THREAD_POOL_KEEP_ALIVE_TIME_KEY = "taskhandler.threadpool.keepalivetime";
	@Constraints(allowSpaces=false, required=false, defaultValue="/opt/data/mp4split-remote.sh")
	public static final String MP4SPLIT_REMOTE_SCRIPT_LOCATION_KEY = "mp4splitremote.script.location";
	@Constraints(allowSpaces=false, required=false, defaultValue="/opt/data/mp4split-remote_subtitles.sh")
	public static final String MP4SPLIT_REMOTE_SUBTITLES_SCRIPT_LOCATION_KEY = "mp4splitremote.subtitles.script.location";
	@Constraints(allowSpaces=false, required=false, defaultValue="28")
	public static final String PURGE_DURATION_KEY = "purge.duration";
	@Constraints(allowSpaces=false, required=false, defaultValue="7")
	public static final String PURGE_DURATION_SCHEDULE_SLOT_KEY = "purge.duration.scheduleslot";
	@Constraints(allowSpaces=false, required=false, defaultValue="1")
	public static final String PURGE_ORPHAN_TEST_PERIOD_KEY = "purge.orphan.test.period";
	@Constraints(allowSpaces=false, required=false, defaultValue="7")
	public static final String PURGE_ORPHAN_DURATION_KEY = "purge.orphan.duration";
	@Constraints(allowSpaces=false, required=false, defaultValue="false")
	public static final String REMOVE_FROM_PROCESSED_FOLDER_KEY = "ingest.remove.from.processed";
	@Constraints(allowSpaces=false, required=false, defaultValue="20")
	public static final String COMMAND_LINE_MONITOR_POOL_SIZE_PROPERTY = "command.line.monitor.threadpool.size";
	@Constraints(allowSpaces=false, required=false, defaultValue="enabled")
	public static final String PROCESS_ID_CDN_PUBLISH = "com.irdeto.jumpstart.workflow.publish.cdn.CdnPublish";
	@Constraints(allowSpaces=false, required=false, defaultValue="enabled")
	public static final String PROCESS_ID_CONTROL_PUBLISH = "com.irdeto.jumpstart.workflow.publish.control.ControlPublish";
	@Constraints(allowSpaces=false, required=false, defaultValue="disabled")
	public static final String PROCESS_ID_ELASTICSEARCH_PUBLISH = "com.irdeto.jumpstart.workflow.publish.elasticsearch.ElasticsearchPublish";
	@Constraints(allowSpaces=false, required=false, defaultValue="disabled")
	public static final String PROCESS_ID_ACTIVEMQ_PUBLISH = "com.irdeto.jumpstart.workflow.publish.activemq.ActiveMQPublish";
	@Constraints(allowSpaces=false, required=false, defaultValue="disabled")
	public static final String PROCESS_ID_ACTIVEMQ_CATCH_UP_PUBLISH = "com.irdeto.jumpstart.workflow.publish.activemq.CatchUpPublish";
	/**
	 * <B>Code by nitin to add CatchUpPublishElementalDelta</B>
	 */
	@Constraints(allowSpaces=false, required=false, defaultValue="disabled")
	public static final String PROCESS_ID_ACTIVEMQ_CATCH_UP_PUBLISH_ELEMENTAL_DELTA = "com.irdeto.jumpstart.workflow.publish.activemq.CatchUpPublishElementalDelta";
	@Constraints(allowSpaces=false, required=false, defaultValue="disabled")
	public static final String PROCESS_ID_ACTIVEMQ_CATCH_UP_PURGE = "com.irdeto.jumpstart.workflow.purge.activemq.CatchUpPurge";
}
