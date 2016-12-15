package com.irdeto.jumpstart.workflow.ingest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.joda.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.helper.xmlmodel.JDOMDocumentHelper;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.ChannelDay;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.Locale;
import com.irdeto.jumpstart.domain.Person;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.ScheduleSlot.ScreenFormat;
import com.irdeto.jumpstart.domain.ingest.ChannelDayIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.ChannelIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.ScheduleSlotIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SourceInformation;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.workflow.DateHelper;
import com.irdeto.jumpstart.workflow.LocaleHelper;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public class JDOMXmlTvMapper extends AbstractVodIngestMapper implements EpgIngestMapper {
	private static final Integer CHANNEL_LOGO_X_RESOLUTION = 64;
	private static final Integer CHANNEL_LOGO_Y_RESOLUTION = 48;

	private static final String ATTRIBUTE_CATCHUP = "catchup";
	// code by nitin
	
		private static final String ATTRIBUTE_DOWNLOADABLE = "downloadable";
		private static final String ATTRIBUTE_STBENABLED = "stbenabled";
	private static final String ATTRIBUTE_BLACKOUT = "blackout";

	private static final String ATTRIBUTE_CHANNEL = "channel";
	private static final String ATTRIBUTE_ID = "id";
	private static final String ATTRIBUTE_LANG = "lang";
	private static final String ATTRIBUTE_SRC = "src";
	private static final String ATTRIBUTE_START = "start";
	private static final String ATTRIBUTE_STOP = "stop";
	private static final String ATTRIBUTE_SYSTEM = "system";
	private static final String ELEMENT_ACTOR = "actor";
	private static final String ELEMENT_ASPECT= "aspect";
	private static final String ELEMENT_CATEGORY = "category";
	private static final String ELEMENT_COUNTRY = "country";
	private static final String ELEMENT_CREDITS = "credits";
	private static final String ELEMENT_DESC = "desc";
	private static final String ELEMENT_DIRECTOR = "director";
	private static final String ELEMENT_DISPLAY_NAME = "display-name";
	private static final String ELEMENT_EPISODE_NUM = "episode-num";
	private static final String ELEMENT_ICON = "icon";
	private static final String ELEMENT_PRODUCER = "producer";
	private static final String ELEMENT_RATING = "rating";
	private static final String ELEMENT_SUB_TITLE = "sub-title";
	private static final String ELEMENT_TITLE = "title";
	private static final String ELEMENT_VALUE = "value";
	private static final String ELEMENT_VIDEO = "video";
	private static final String ELEMENT_WRITER = "writer";
	private static final String XMLTV_SERIE="serie";
	private static final String XMLTV_KIDS="kids";
	private static final String XMLTV_MOVIE="movie";
	private static final String XMLTV_SPORT="sport";
	private static final String XMLTV_MUSIC="music";
	private static final String XMLTV_ADULT="adult";
	private static final String XMLTV_COMMERCIAL="commercial";
	private static final String XMLTV_DOCUMENTARY="documentary";
	private static final String XMLTV_EDUCATIONAL="educational";
	private static final String XMLTV_ENTERTAINMENT="entertainment";
	private static final String XMLTV_NEWS="news";
	private static final String XMLTV_RELIGIOUS="religious";

	private static final Logger logger = LoggerFactory.getLogger(JDOMXmlTvMapper.class);

	@Override
	@JsonIgnore
	public boolean isApplicableMapper() {
		return StringUtils.contains(getInputString(), "<tv ");
	}

	@Override
	@JsonIgnore
	public String[] getSchemaFileNames() {
		try {
			String schemaFileNames = BeanUtil.propertiesManager.getProperty(
					JumpstartPropertyKey.INGEST_EPG_VALIDATION_SCHEMA_KEY);
			return schemaFileNames.split(",");
		} catch (PropertyException e) {
		}
		return new String[0];
	}

	@Override
	@JsonIgnore
	public boolean isSchemaValidationEnabled() {
		try {
			return BooleanUtils.toBoolean(BeanUtil.propertiesManager.getProperty(
					JumpstartPropertyKey.INGEST_EPG_VALIDATION_ENABLE_KEY));
		} catch (PropertyException e) {
		}
		return false;
	}

	@Override
	public List<EntityIngestWrapper<?>> findEntities() throws Exception {
		List<EntityIngestWrapper<?>> ingestWrapperList = new ArrayList<>();

		List<ScheduleSlotIngestWrapper> scheduleSlotIngestWrappers = findScheduleSlots(getInputString());
		ingestWrapperList.addAll(scheduleSlotIngestWrappers);

		Map<String, ChannelDayIngestWrapper> channelDayIngestWrappers = new HashMap<>();

		for (ScheduleSlotIngestWrapper scheduleSlotIngestWrapper : scheduleSlotIngestWrappers) {
			String channelId = scheduleSlotIngestWrapper.getChannelId();
			DateTime slotDate = scheduleSlotIngestWrapper.getEntity()
					.getLinearBroadcastDate().withTimeAtStartOfDay();
			String channelDayUriId = channelId + "/" + slotDate.toLocalDate().toString("yyyy-MM-dd");

			ChannelDayIngestWrapper channelDayIngestWrapper
					= channelDayIngestWrappers.get(channelDayUriId);
			if (channelDayIngestWrapper == null) {
				ChannelDay channelDay = new ChannelDay();
				channelDay.setUriId(channelDayUriId);
				channelDay.setDate(slotDate);

				channelDayIngestWrapper = new ChannelDayIngestWrapper();
				channelDayIngestWrappers.put(
						channelDayUriId,
						channelDayIngestWrapper
				);
				channelDayIngestWrapper.setEntity(channelDay);
				channelDayIngestWrapper.addChannelIdList(
						Collections.singletonList(channelId)
				);
			}

			channelDayIngestWrapper.addScheduleSlot(scheduleSlotIngestWrapper.getEntity());
		}
		ingestWrapperList.addAll(channelDayIngestWrappers.values());
		return ingestWrapperList;
	}

	protected List<ChannelIngestWrapper> findChannels(String xmlInput) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		List<ChannelIngestWrapper> ingestWrapperList = new ArrayList<>();
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> contentGroupPath = xPathFactory.compile("/tv/channel", Filters.element(), null);
		List<Element> channelElementList = contentGroupPath.evaluate(document);
		for (Element channelElement: channelElementList) {
			ChannelIngestWrapper ingestWrapper = new ChannelIngestWrapper();
			Channel channel = new Channel();
			ingestWrapper.setEntity(channel);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

			// Uri ID
			String channelId  = channelElement.getAttributeValue(ATTRIBUTE_ID);
			boolean isIdBlank = StringUtils.isBlank(channelId);

			// Display name

			List<Element> displayNameElementList = channelElement.getChildren(ELEMENT_DISPLAY_NAME);
			Locale channelDisplayBrief = getLocaleValues(displayNameElementList,19);
			Locale channelDisplayMedium = getLocaleValues(displayNameElementList,35);
			Locale channelDisplayLong = getLocaleValues(displayNameElementList,128);
			channel.setTitleBrief(channelDisplayBrief);
			channel.setTitleMedium(channelDisplayMedium);
			channel.setTitleLong(channelDisplayLong);


			if(isIdBlank){
				String displayName = LocaleHelper.getStringValueForDefaultLanguage(channelDisplayLong);
				logger.warn("Channel {} has a blank ID in the XML source file and so will not be ingested.",displayName);
				break;
			}
			channel.setUriId(channelId);
			channel.setChannelId(channelId);

			// Icon
			List<Element> iconElementList = channelElement.getChildren(ELEMENT_ICON);
			for (Element iconElement: iconElementList) {
				String icon = iconElement.getAttributeValue(ATTRIBUTE_SRC);
				ImageContent imageContent = new ImageContent();
				imageContent.setSourceUrl(icon);
				imageContent.setIsPublished(true);
				imageContent.setPublishedDate(new DateTime());
				imageContent.setPublishVersion(1);
				imageContent.setSourceVersion(1);
				imageContent.setUriId(icon);
				imageContent.setContentType(ImageContent.ContentType.THUMBNAIL);
				imageContent.setXResolution(CHANNEL_LOGO_X_RESOLUTION.toString());
				imageContent.setYResolution(CHANNEL_LOGO_Y_RESOLUTION.toString());
				channel.getImageContent().add(imageContent);

				ImageSubcontent imageSubcontent = new ImageSubcontent();
				imageSubcontent.setConsumerUrl(icon);
				imageSubcontent.setSourcePath(icon);
				imageSubcontent.setXResolution(CHANNEL_LOGO_X_RESOLUTION);
				imageSubcontent.setYResolution(CHANNEL_LOGO_Y_RESOLUTION);
				imageContent.getSubcontent().add(imageSubcontent);
			}

			ingestWrapperList.add(ingestWrapper);
		}

		return ingestWrapperList;
	}

	protected List<ScheduleSlotIngestWrapper> findScheduleSlots(String xmlInput)
			throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		List<ScheduleSlotIngestWrapper> ingestWrapperList = new ArrayList<>();
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> contentGroupPath = xPathFactory.compile("/tv/programme", Filters.element(), null);
		List<Element> programmeElementList = contentGroupPath.evaluate(document);
		for (Element programmeElement: programmeElementList) {
			ScheduleSlotIngestWrapper ingestWrapper = new ScheduleSlotIngestWrapper();
			ScheduleSlot scheduleSlot = new ScheduleSlot();
			ingestWrapper.setEntity(scheduleSlot);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

			// Channel mapping
			String channelId = programmeElement.getAttributeValue(ATTRIBUTE_CHANNEL);
			boolean isIdBlank = StringUtils.isBlank(channelId);

			// Title
			List<Element> titleElementList = programmeElement.getChildren(ELEMENT_TITLE);
			scheduleSlot.setTitle(getLocaleValues(titleElementList));

			Locale titleLocale = scheduleSlot.getTitle();
			String title = LocaleHelper.getStringValueForDefaultLanguage(titleLocale);
			if(isIdBlank){
				logger.warn("Schedule slot with title {} has no channel ID and so will not be ingested.",title);
				break;
			}

			ingestWrapper.setChannelId(channelId);

			scheduleSlot.setCatchUp(BooleanUtils.toBoolean(programmeElement.getAttributeValue(ATTRIBUTE_CATCHUP)));
			// code by nitin
						
						scheduleSlot.setDownloadable(BooleanUtils.toBoolean(programmeElement.getAttributeValue(ATTRIBUTE_DOWNLOADABLE)));
						scheduleSlot.setSTBEnabled(BooleanUtils.toBoolean(programmeElement.getAttributeValue(ATTRIBUTE_STBENABLED)));
			scheduleSlot.setBlackOut(BooleanUtils.toBoolean(programmeElement.getAttributeValue(ATTRIBUTE_BLACKOUT)));

			// Genre mapping and show type
			List<Element> categoryElementList = programmeElement.getChildren(ELEMENT_CATEGORY);
			for (Element categoryElement: categoryElementList) {
				if (LocaleHelper.DEFAULT_LANGUAGE_NAME.equals(categoryElement.getAttributeValue(ATTRIBUTE_LANG))
						|| (LocaleHelper.ALTERNATE_DEFAULT_LANG_NAME.equals(categoryElement.getAttributeValue(ATTRIBUTE_LANG)))) {
					String categoryValue = categoryElement.getValue();

					switch (categoryValue) {
						case XMLTV_SERIE:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.SERIES);
							break;
						case XMLTV_KIDS:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.KIDS);
							break;
						case XMLTV_MOVIE:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.MOVIES);
							break;
						case XMLTV_SPORT:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.SPORTS);
							break;
						case XMLTV_MUSIC:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.MUSIC);
							break;
						case XMLTV_ADULT:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.AD);
							break;
						case XMLTV_COMMERCIAL:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.COMMERCIAL);
							break;
						case XMLTV_DOCUMENTARY:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.DOCUMENTARY);
							break;
						case XMLTV_EDUCATIONAL:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.EDUCATIONAL);
							break;
						case XMLTV_ENTERTAINMENT:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.ENTERTAINMENT);
							break;
						case XMLTV_NEWS:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.NEWS);
							break;
						case XMLTV_RELIGIOUS:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.RELIGIOUS);
							break;
						default:
							scheduleSlot.setShowType(ScheduleSlot.ShowType.OTHERS);
							break;
					}

					ingestWrapper.setIngestGenre(categoryValue);

					// TODO: support multiple genres.
					// For now take into account only the first genre.
					break;
				}
			}

			// Ratings
			List<Element> ratingElementList = programmeElement.getChildren(ELEMENT_RATING);
			for (Element ratingElement: ratingElementList) {
				String ratingScheme = ratingElement.getAttributeValue(ATTRIBUTE_SYSTEM);
				Element valueElement = ratingElement.getChild(ELEMENT_VALUE);
				String rating = valueElement.getValue();
				if (StringUtils.isNotBlank(ratingScheme) && StringUtils.isNotBlank(rating)) {
					scheduleSlot.setRating(ratingScheme + " " + rating);

					// TODO: support multiple ratings.
					// For now take into account only the first rating.
					break;
				}
			}

			// Episode Name
			List<Element> subTitleElementList = programmeElement.getChildren(ELEMENT_SUB_TITLE);
			scheduleSlot.setEpisodeName(getLocaleValues(subTitleElementList));

			// Summary
			List<Element> descElementList = programmeElement.getChildren(ELEMENT_DESC);
			scheduleSlot.setSummary(getLocaleValues(descElementList));

			// Linear broadcast date
			scheduleSlot.setLinearBroadcastDate(DateHelper.convertXMLTVDateToDateTime(programmeElement.getAttributeValue(ATTRIBUTE_START)));

			// Linear broadcast end date
			scheduleSlot.setLinearBroadcastEndDate(DateHelper.convertXMLTVDateToDateTime(programmeElement.getAttributeValue(ATTRIBUTE_STOP)));

			if(scheduleSlot.getLinearBroadcastDate() == null) {
				String scheduleTitleLocale = LocaleHelper.getStringValueForDefaultLanguage(scheduleSlot.getTitle());
				logger.warn("The start time is null for the Schedule slot {}", scheduleTitleLocale);
				break;
			}

			if(scheduleSlot.getLinearBroadcastEndDate() != null
					&& scheduleSlot.getLinearBroadcastDate().isAfter(scheduleSlot.getLinearBroadcastEndDate())){
				String scheduleTitleLocale = LocaleHelper.getStringValueForDefaultLanguage(scheduleSlot.getTitle());
				logger.warn("The start time is greater than the end time for the Schedule slot {}", scheduleTitleLocale);
				break;
			}

			// Slot Date
			String slotDate = programmeElement.getAttributeValue(ATTRIBUTE_START);
			scheduleSlot.setStartDateTime(DateHelper.convertXMLTVDate(slotDate.substring(0,8)));

			// URI ID
			scheduleSlot.setUriId(channelId + "/" + scheduleSlot.getLinearBroadcastDate().getMillis());

			// Country
			List<Element> countryElementList = programmeElement.getChildren(ELEMENT_COUNTRY);
			scheduleSlot.getCountryOfOrigin().clear();
			for (Element countryElement: countryElementList) {
				String countryOfOrigin = countryElement.getValue();
				if (StringUtils.isNotBlank(countryOfOrigin)) {
					if (BeanUtil.localeManager.isIso3166CountryValid(countryOfOrigin)) {
						String country2Id = BeanUtil.localeManager.getCountry2Code(countryOfOrigin);
						scheduleSlot.getCountryOfOrigin().add(country2Id);
					} else if (BeanUtil.localeManager.isIso3166Country2CodeValid(countryOfOrigin)) {
						scheduleSlot.getCountryOfOrigin().add(countryOfOrigin);
					}
				}
			}

			// Video Aspect
			Element videoElement = programmeElement.getChild(ELEMENT_VIDEO);
			if (videoElement != null) {
				String aspect = videoElement.getChildText(ELEMENT_ASPECT);
				switch (aspect) {
				case "16:9":
					scheduleSlot.setScreenFormat(ScreenFormat.WIDESCREEN);
					break;
				case "4:3":
					scheduleSlot.setScreenFormat(ScreenFormat.STANDARD);
					break;
				default:
					scheduleSlot.setScreenFormat(ScreenFormat.STANDARD);
					break;
				}
			}

			// Availability
			String purgeDuration = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.PURGE_DURATION_SCHEDULE_SLOT_KEY);
			if (StringUtils.isNumeric(purgeDuration)) {
				scheduleSlot.setStartDateTime(scheduleSlot.getLinearBroadcastDate().minusDays(Integer.valueOf(purgeDuration)));
			}
			scheduleSlot.setEndDateTime(scheduleSlot.getLinearBroadcastEndDate());

			// Image
			List<Element> iconElementList = programmeElement.getChildren(ELEMENT_ICON);
			for (Element iconElement: iconElementList) {
				scheduleSlot.setImageUrl(iconElement.getAttributeValue(ATTRIBUTE_SRC));
				break;
			}

			// Episode ID Note this is a format comprising series + episode number e.g. "18.5. "
			scheduleSlot.setEpisodeId(programmeElement.getChildText(ELEMENT_EPISODE_NUM));

			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	@JsonIgnore
	private Locale getLocaleValues(List<Element> elementList,int maxLength) {
		Locale locale = new Locale();
		for (Element element: elementList) {
			String language = element.getAttributeValue(ATTRIBUTE_LANG, LocaleHelper.DEFAULT_LANGUAGE_NAME);
			String value = element.getValue();

			if(maxLength >= 0 && value.length() > maxLength){
				LocaleHelper.setStringValueForLanguage(locale, language,StringUtils.substring(value, 0,maxLength));
			} else {
				LocaleHelper.setStringValueForLanguage(locale, language,value);
			}

		}
		return locale;
	}

	@JsonIgnore
	private Locale getLocaleValues(List<Element> elementList) {
		return getLocaleValues(elementList, -1);
	}
}
