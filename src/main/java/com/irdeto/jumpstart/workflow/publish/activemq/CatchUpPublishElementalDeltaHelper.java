package com.irdeto.jumpstart.workflow.publish.activemq;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.kie.api.runtime.process.ProcessContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.publish.EntityWithChannelListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.workflow.CatchUpEndpoint;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.publish.reference.ScheduleSlotReferenceDocumentMapper;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.w3c.dom.Element;

import java.io.IOException;
import java.io.StringReader;

public class CatchUpPublishElementalDeltaHelper extends WorkflowHelper {
	private static final Logger logger = LoggerFactory.getLogger(CatchUpPublishElementalDeltaHelper.class);
	private static final String URL="url";
	private static final String L2V_BODY="l2v_body";
	private static final String L2V_RESPONSE="l2v_response";
	private static final String DASH_PACKAGING_BODY="dash_pack_body";
	private static final String DASH_PACKAGING_RESPONSE="dash_pack_response";
	private static final String HLS_PACKAGING_BODY="hls_pack_body";
	private static final String HLS_PACKAGING_RESPONSE="hls_pack_response";
	private static final String WIDEVINE_PACKAGING_BODY="widevine_pack_body";
	private static final String WIDEVINE_PACKAGING_RESPONSE="widevine_pack_response";
	private static final String DASH_ENCRYPTION_BODY="dash_enc_body";
	private static final String HLS_ENCRYPTION_BODY="hls_enc_body";
	private static final String WIDEVINE_ENCRYPTION_BODY="widevine_enc_body";

	public static boolean needVersionRead(PublishWrapper<ScheduleSlot> publishWrapper) {
		List<Object> versions = getEntity(publishWrapper).getVersions();
		return versions.isEmpty() || versions.size() > 1;
	}

	public static Integer getPreviousVersion(PublishWrapper<ScheduleSlot> publishWrapper) {
		List<Object> versions = getEntity(publishWrapper).getVersions();
		try {
			return versions.size() <= 1
					? 1
					: Integer.valueOf((versions.get(versions.size() - 2).toString()));
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			logger.warn("Failed to get previous version number of {}", getEntity(publishWrapper).toString());
			return 0;
		}
	}

	public static boolean needsCatchUpPublish(
			ScheduleSlot currentVersion ,
			ScheduleSlot previousVersion
	) {
		if (currentVersion == null) {
			return false;
		}
		if (currentVersion.equals(previousVersion)) {
			return false;
		}
		//changes by nitin
		if (previousVersion == null) {
			return (currentVersion.getCatchUp()&&currentVersion.getSTBEnabled());
		}

		// change by nitin
		boolean stbenabledChanged
		= currentVersion.getSTBEnabled() != previousVersion.getSTBEnabled()
		&& currentVersion.getLinearBroadcastDate() != null
		&& currentVersion.getLinearBroadcastEndDate() != null;
		
		boolean catchupChanged
				= currentVersion.getCatchUp() != previousVersion.getCatchUp()
				&& currentVersion.getLinearBroadcastDate() != null
				&& currentVersion.getLinearBroadcastEndDate() != null;
		boolean startChanged
				= currentVersion.getLinearBroadcastDate() != null
				&& previousVersion.getLinearBroadcastDate() != null
				&& currentVersion.getLinearBroadcastDate().getMillis()
				!= previousVersion.getLinearBroadcastDate().getMillis();
		boolean endChanged
				= currentVersion.getLinearBroadcastEndDate() != null
				&& previousVersion.getLinearBroadcastEndDate() != null
				&& currentVersion.getLinearBroadcastEndDate().getMillis()
				!= previousVersion.getLinearBroadcastEndDate().getMillis();

		return stbenabledChanged || (currentVersion.getSTBEnabled() && (startChanged || endChanged));
	}

	public static String prepareDocument(PublishWrapper<ScheduleSlot> publishWrapper) throws JsonProcessingException {
		ScheduleSlot scheduleSlot = getEntity(publishWrapper);
		Channel channel = null;
		if (publishWrapper instanceof EntityWithChannelListPublishWrapper) {
			channel = ((EntityWithChannelListPublishWrapper) publishWrapper).getChannel();
		}
// changes by nitin
		return CatchUpEndpoint.getDocument(
				channel != null ? channel.getChannelId() : null,
				scheduleSlot.getUriId(),

				scheduleSlot.getLinearBroadcastDate(),
				scheduleSlot.getLinearBroadcastEndDate(),

				scheduleSlot.getTitle(),
				scheduleSlot.getSummary(),
				scheduleSlot.getGenre(),
				scheduleSlot.getEpisodeName(),
				scheduleSlot.getEpisodeId(),
				scheduleSlot.getCatchUp(),
				scheduleSlot.getDownloadable(),
				scheduleSlot.getSTBEnabled(),
				scheduleSlot.getRating()
				
		);
		
		
	}

	public static void setUpLivetoVODFilter(PublishWrapper<ScheduleSlot> publishWrapper,ProcessContext kcontext){
		Channel channel = null;
		if (publishWrapper instanceof EntityWithChannelListPublishWrapper) {
			channel = ((EntityWithChannelListPublishWrapper) publishWrapper).getChannel();
		}
		ScheduleSlot scheduleSlot = getEntity(publishWrapper);
		kcontext.setVariable(URL, "http://172.28.1.102:8080/api/contents/11/filters");
		kcontext.setVariable(L2V_BODY, "<filter><name>live_to_vod</name><parent_filter/><endpoint>true</endpoint><output_url/><filter_type>live_to_vod</filter_type><filter_settings><start_time>"+scheduleSlot.getLinearBroadcastDate()+"</start_time><end_time>"+scheduleSlot.getLinearBroadcastEndDate()+"</end_time><start_over>false</start_over></filter_settings></filter>");
		
	}
	public static void setUpPackagingFilters(ProcessContext kcontext) throws SAXException, IOException, ParserConfigurationException{
		String l2v_response = (String)kcontext.getVariable(L2V_RESPONSE);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(l2v_response)));
		Element rootElement = doc.getDocumentElement();
		String l2v_filter_id = (String)rootElement.getElementsByTagName("id").item(0).getTextContent();
		kcontext.setVariable(DASH_PACKAGING_BODY, "<filter><name>dash_iso_package</name><<parent_id>"+l2v_filter_id+"</parent_id><endpoint>true</endpoint><output_url/><filter_type>dash_iso_package</filter_type><filter_settings><fragment_duration>12</fragment_duration><index_duration>60</index_duration><hbbtv>1.5</hbbtv><min_update_period>1</min_update_period><min_buffer_time>1</min_buffer_time><suggested_presentation_delay>1</suggested_presentation_delay></filter_settings></filter>");
		kcontext.setVariable(HLS_PACKAGING_BODY, "<filter><name>hls_package</name><parent_id>"+l2v_filter_id+"</parent_id><endpoint>true</endpoint><output_url/><filter_type>hls_package</filter_type><filter_settings><segment_duration>3</segment_duration><index_duration>120</index_duration><avail_trigger>all</avail_trigger><ad_markers>none</ad_markers><broadcast_time>false</broadcast_time><ignore_web_delivery_allowed>false</ignore_web_delivery_allowed><ignore_no_regional_blackout>false</ignore_no_regional_blackout><enable_blackout>false</enable_blackout><enable_network_end_blackout>false</enable_network_end_blackout><network_id/></filter_settings></filter>");
		kcontext.setVariable(WIDEVINE_PACKAGING_BODY, "<filter><name>mss_package</name><parent_id>"+l2v_filter_id+"</parent_id><endpoint>true</endpoint><output_url/><filter_type>mss_package</filter_type><filter_settings><fragment_duration>12</fragment_duration><index_duration>60</index_duration><enable_events>true</enable_events></filter_settings></filter>");		
	}
	public static void setHLSencryption (PublishWrapper<ScheduleSlot> publishWrapper,ProcessContext kcontext) throws SAXException, IOException, ParserConfigurationException{
		ScheduleSlot scheduleSlot = getEntity(publishWrapper);
		String hls_pack_response = (String)kcontext.getVariable(HLS_PACKAGING_RESPONSE);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(hls_pack_response)));
		Element rootElement = doc.getDocumentElement();
		String hls_packaging_filter_id = (String)rootElement.getElementsByTagName("id").item(0).getTextContent();
		kcontext.setVariable(HLS_ENCRYPTION_BODY, "<filter><name>HLS Encryption</name><parent_id>"+hls_packaging_filter_id+"</parent_id><endpoint>true</endpoint><output_url>"+scheduleSlot.getUriId()+"</output_url><filter_type>hls_encryption</filter_type><filter_settings><encryption_type>AES-128</encryption_type><key_rotation_count>1</key_rotation_count><iv_follows_segment_number>true</iv_follows_segment_number><constant_iv/><key_format/><key_format_versions/><key_id/><keyprovider_type>self_generated</keyprovider_type><server_url/><service_id/><content_key_base64/><content_key_hex/><license_url/><ui_license_url/><custom_attributes/><keyprovider_settings><common_key>false</common_key><key_prefix>http://10.4.140.51/out/</key_prefix></keyprovider_settings></filter_settings></filter>");
	}
	
	public static void setDASHencryption (PublishWrapper<ScheduleSlot> publishWrapper,ProcessContext kcontext) throws SAXException, IOException, ParserConfigurationException{
		ScheduleSlot scheduleSlot = getEntity(publishWrapper);
		String dash_pack_response = (String)kcontext.getVariable(DASH_PACKAGING_RESPONSE);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(dash_pack_response)));
		Element rootElement = doc.getDocumentElement();
		String dash_packaging_filter_id = (String)rootElement.getElementsByTagName("id").item(0).getTextContent();
		kcontext.setVariable(DASH_ENCRYPTION_BODY, "<filter><name>COMMON Encryption</name><<parent_id>"+dash_packaging_filter_id+"</parent_id><endpoint>true</endpoint><output_url>"+scheduleSlot.getUriId()+"</output_url><filter_type>common_encryption</filter_type><filter_settings><keyprovider_type>generic_cenc</keyprovider_type><keyprovider_settings><protection_uuid>irdeto</protection_uuid><key_value>F1E46AF7DA2D4B2CB0739F640ECD0124</key_value><kid>F1E46AF7DA2D4B2CB0739F640ECD0124</kid><pssh>F1E46AF7DA2D4B2CB0739F640ECD0124</pssh></keyprovider_settings></filter_settings></filter>");
	}
	public static void setWidevineencryption (PublishWrapper<ScheduleSlot> publishWrapper,ProcessContext kcontext) throws SAXException, IOException, ParserConfigurationException{
		ScheduleSlot scheduleSlot = getEntity(publishWrapper);
		String widevine_pack_response = (String)kcontext.getVariable(WIDEVINE_PACKAGING_RESPONSE);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(widevine_pack_response)));
		Element rootElement = doc.getDocumentElement();
		String widevine_packaging_filter_id = (String)rootElement.getElementsByTagName("id").item(0).getTextContent();
		kcontext.setVariable(WIDEVINE_ENCRYPTION_BODY, "<filter><name>COMMON Encryption</name><parent_id>"+widevine_packaging_filter_id+"</parent_id><endpoint>true</endpoint><output_url>"+scheduleSlot.getUriId()+"</output_url><filter_type>common_encryption</filter_type><filter_settings><keyprovider_type>widevine</keyprovider_type><content_id>1</content_id><key_rotation_count>0</key_rotation_count><license_url>http://172.28.1.111</license_url><provider_id>irdeto</provider_id><provider_iv>F1E46AF7DA2D4B2CB0739F640ECD0124</provider_iv><provider_key>F1E46AF7DA2D4B2CB0739F640ECD0124</provider_key><request_playready_key>true</request_playready_key><request_widevine_key>false</request_widevine_key><reuse_last_key>false</reuse_last_key></filter_settings></filter>");
	}
	private static ScheduleSlot getEntity(PublishWrapper<ScheduleSlot> publishWrapper) {
		return publishWrapper.getEntity();
	}
}
