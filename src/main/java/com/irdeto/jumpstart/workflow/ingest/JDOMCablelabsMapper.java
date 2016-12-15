package com.irdeto.jumpstart.workflow.ingest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.helper.xmlmodel.JDOMDocumentHelper;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseMetadataWithTitle;
import com.irdeto.jumpstart.domain.BaseMetadataWithTitle.ShowType;
import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Locale;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Person;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.SubtitleContent;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.VideoContent.ScreenFormat;
import com.irdeto.jumpstart.domain.ingest.BrandIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.ChannelIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EventIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.OfferIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.ProgramIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SeriesIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SourceInformation;
import com.irdeto.jumpstart.domain.ingest.SubscriptionPackageIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.TermIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.TvodCollectionIngestWrapper;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.workflow.DateHelper;
import com.irdeto.jumpstart.workflow.LocaleHelper;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public class JDOMCablelabsMapper extends AbstractVodIngestMapper implements VodIngestMapper {

	private static final String CABLELABS_SHOW_TYPE_MOVIE = "Movie";
	private static final String CABLELABS_SHOW_TYPE_MINISERIES = "Miniseries";

	private static final String ELEMENT_ACTOR = "Actor";
	private static final String ELEMENT_ASSET = "Asset";
	private static final String ELEMENT_BARKER = "Barker";
	private static final String ELEMENT_BILLING_ID = "BillingId";
	private static final String ELEMENT_OFFER_TYPE = "OfferType";
	private static final String ELEMENT_OFFER_PACKAGE_ID = "OfferPackageId";
	private static final String ELEMENT_BOX_COVER = "BoxCover";
	private static final String ELEMENT_CHANNEL_ID = "ChannelId";
	private static final String ELEMENT_IS_ENABLED = "isEnabled";
	private static final String ELEMENT_DISPLAY_ORDER = "DisplayOrder";
	private static final String ELEMENT_CHANNEL_PACKAGER = "ChannelPackager";
	private static final String ELEMENT_ALLOW_ON_BROWSER = "isAllowedonBrowsers";
	private static final String ELEMENT_FREE_CHANNEL = "isFreeChannel";
	private static final String ELEMENT_NUMBER_OF_AUDIO = "NumberOfAudio";
	private static final String ELEMENT_BROADCAST_SERVICE_ID = "BroadcastServiceId";
	private static final String ELEMENT_HD_CHANNEL = "isHDChannel";
	private static final String ELEMENT_CATCHUP_CHANNEL = "isCatchUpChannel";
	private static final String ELEMENT_UDP_MULTICAST_IP = "UDPMulticastIP";
	private static final String ELEMENT_SMS_PACKAGE_ID = "SMSPackageId";
	private static final String ELEMENT_ALACARTE = "Alacarte";
	private static final String ELEMENT_CONTENT_CHECKSUM = "ContentCheckSum";
	private static final String ELEMENT_CONTENT_FILESIZE = "ContentFileSize";
	private static final String ELEMENT_CONTENT_GROUP = "ContentGroup";
	private static final String ELEMENT_CONTRACT_NAME = "ContractName";
	private static final String ELEMENT_COUNTRY_OF_ORIGIN = "CountryOfOrigin";
	private static final String ELEMENT_KEYWORDS = "Keywords";
	
	private static final String ELEMENT_DISPLAY_RUN_TIME = "DisplayRunTime";
	private static final String ELEMENT_DIRECTOR = "Director";
	private static final String ELEMENT_DURATION = "Duration";
	private static final String ELEMENT_ENCODE_PROFILE = "EncodeProfile";
	private static final String ELEMENT_EPISODE_ID = "EpisodeID";
	private static final String ELEMENT_EPISODE_NAME = "EpisodeName";
	private static final String ELEMENT_EVENT_BROADCAST_DATE = "EventBroadcastDate";
	private static final String ELEMENT_EVENT_BROADCAST_END_DATE = "EventBroadcastEndDate";
	private static final String ELEMENT_GENRE = "Genre";
	private static final String ELEMENT_IS_CLOSED_CAPTIONING = "IsClosedCaptioning";
	private static final String ELEMENT_IS_SEASON_FINALE = "IsSeasonFinale";
	private static final String ELEMENT_IS_SEASON_PREMIER = "IsSeasonPremier";
	// code by nitin
	private static final String ELEMENT_NDS_OFFER_ID = "NDSOfferID";
	private static final String ELEMENT_NDS_PRICE = "NDSPrice";
			
	private static final String ELEMENT_MAX_CONCURRENT_STREAM = "MaxConcurrentStreams";
	private static final String ELEMENT_IS_DOWNLOADABLE = "isDownloadable";
	private static final String ELEMENT_DOWNLOAD_EXPIRY = "DownloadExpiry";
	private static final String ELEMENT_MSO_RATING = "MSORating";
	private static final String ELEMENT_LIVE_WINDOW_DURATION = "LiveWindowDuration";
	private static final String ELEMENT_LOCALIZABLE_TITLE = "LocalizableTitle";
	private static final String ELEMENT_MOVIE = "Movie";
	private static final String ELEMENT_OFFER = "Offer";
	private static final String ELEMENT_POSTER = "Poster";
	private static final String ELEMENT_PREVIEW = "Preview";
	private static final String ELEMENT_PRODUCER = "Producer";
	private static final String ELEMENT_PROGRAM_COUNT = "ProgramCount";
	private static final String ELEMENT_PROVIDER = "Provider";
	private static final String ELEMENT_RATING = "Rating";
	private static final String ELEMENT_SCHEDULE_SLOT_ID = "ScheduleSlotId";
	private static final String ELEMENT_SCREEN_FORMAT = "ScreenFormat";
	private static final String ELEMENT_SEASON = "Season";
	private static final String ELEMENT_SERIES_COUNT = "SeriesCount";
	private static final String ELEMENT_SHOW_TYPE = "ShowType";
	private static final String ELEMENT_SOURCE_URL = "SourceUrl";
	private static final String ELEMENT_SUGGESTED_PRICE = "SuggestedPrice";
	private static final String ELEMENT_SUMMARY_SHORT = "SummaryShort";
	private static final String ELEMENT_SUMMARY_MEDIUM = "SummaryMedium";
	private static final String ELEMENT_SUMMARY_LONG = "SummaryLong";
	private static final String ELEMENT_TERMS = "Terms";
	private static final String ELEMENT_THUMBNAIL = "Thumbnail";
	private static final String ELEMENT_TITLE = "Title";
	private static final String ELEMENT_TITLE_BRIEF = "TitleBrief";
	private static final String ELEMENT_TITLE_LONG = "TitleLong";
	private static final String ELEMENT_TITLE_MEDIUM = "TitleMedium";
	private static final String ELEMENT_TITLE_SORT_NAME = "TitleSortName";
	private static final String ELEMENT_WRITER = "Writer";
	private static final String ELEMENT_X_RESOLUTION = "X_Resolution";
	private static final String ELEMENT_Y_RESOLUTION = "Y_Resolution";
	private static final String ELEMENT_YEAR = "Year";
	private static final String ELEMENT_SUBTITLE_LANGUAGE = "SubtitleLanguage";
	private static final String ELEMENT_CONTENT_LANGUAGE = "Language";

	private static final String ATTRIBUTE_CURRENCY = "currency";
	private static final String ATTRIBUTE_END_DATE_TIME = "endDateTime";
	private static final String ATTRIBUTE_FIRST_NAME = "firstName";
	private static final String ATTRIBUTE_FULL_NAME = "fullName";
	private static final String ATTRIBUTE_LANG = "lang";
	private static final String ATTRIBUTE_LAST_NAME = "lastName";
	private static final String ATTRIBUTE_RATING_SYSTEM = "ratingSystem";
	private static final String ATTRIBUTE_SORTABLE_NAME = "sortableName";
	private static final String ATTRIBUTE_START_DATE_TIME = "startDateTime";
	private static final String ATTRIBUTE_URI_ID = "uriId";

	private static final String CONTENT_PREVIEW_TYPE = "content:PreviewType";
	private static final String CONTENT_MOVIE_TYPE = "content:MovieType";
	private static final String CONTENT_BARKER_TYPE = "content:BarkerType";
	private static final String CONTENT_THUMBNAIL_TYPE = "content:ThumbnailType";
	private static final String CONTENT_POSTER_TYPE = "content:PosterType";
	private static final String CONTENT_BOX_COVER_TYPE = "content:BoxCoverType";
	private static final String CONTENT_SUBTITLE = "Subtitle";
	private static final String IRDETO_BRAND_TYPE = "irdeto:BrandType";
	private static final String IRDETO_CHANNEL_TYPE = "irdeto:ChannelType";
	private static final String IRDETO_EVENT_TYPE = "irdeto:EventType";
	private static final String IRDETO_SERIES_TYPE = "irdeto:SeriesType";
	private static final String IRDETO_SUBSCRIPTION_PACKAGE_TYPE = "irdeto:SubscriptionPackageType";
	private static final String IRDETO_TVOD_COLLECTION_TYPE = "irdeto:TVODCollectionType";
	private static final String OFFER_CONTENT_GROUP_TYPE = "offer:ContentGroupType";
	private static final String OFFER_OFFER_TYPE = "offer:OfferType";
	private static final String TITLE_TITLE_TYPE = "title:TitleType";
	private static final String TERMS_TERMS_TYPE = "terms:TermsType";

	private static final String CONTENT_THUMBNAIL_REF = "ThumbnailRef";
	private static final String CONTENT_POSTER_REF = "PosterRef";
	private static final String CONTENT_BOX_COVER_REF = "BoxCoverRef";
	private static final String CONTENT_GROUP_REF = "ContentGroupRef";
	private static final String CONTENT_PREVIEW_REF = "PreviewRef";
	private static final String CONTENT_MOVIE_REF = "MovieRef";
	private static final String CONTENT_BARKER_REF = "BarkerRef";
	private static final String TITLE_REF = "TitleRef";
	private static final String SERIES_REF = "SeriesRef";
	private static final String BRAND_REF = "BrandRef";
	private static final String EVENT_REF = "EventRef";
	private static final String CHANNEL_REF = "ChannelRef";
	private static final String TERMS_REF = "TermsRef";

	private static final String CORE = "core";
	private static Namespace NS_CORE = Namespace.getNamespace(CORE, "urn:cablelabs:md:xsd:core:3.0");
	private static final String TITLE = "title";
	private static Namespace NS_TITLE = Namespace.getNamespace(TITLE, "urn:cablelabs:md:xsd:title:3.0");
	private static final String TERMS = "terms";
	private static Namespace NS_TERMS = Namespace.getNamespace(TERMS, "urn:cablelabs:md:xsd:terms:3.0");
	private static final String OFFER = "offer";
	private static Namespace NS_OFFER = Namespace.getNamespace(OFFER, "urn:cablelabs:md:xsd:offer:3.0");
	private static final String CONTENT = "content";
	private static Namespace NS_CONTENT = Namespace.getNamespace(CONTENT, "urn:cablelabs:md:xsd:content:3.0");
	private static final String IRDETO = "irdeto";
	private static Namespace NS_IRDETO = Namespace.getNamespace(IRDETO, "http://www.irdeto.com/schemas/metadata/1.0");
	private static final String IRDETO_TYPES = "irdetotypes";
	private static Namespace NS_IRDETO_TYPES = Namespace.getNamespace(IRDETO_TYPES, "http://www.irdeto.com/schemas/types/1.0");
	private static Namespace NS_XSI = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	private static Namespace NS_XML = Namespace.getNamespace("xml", "http://www.w3.org/XML/1998/namespace");

	private static final Logger logger = LoggerFactory.getLogger(JDOMCablelabsMapper.class);

	@Override
	@JsonIgnore
	public boolean isApplicableMapper() {
		return StringUtils.contains(getInputString(), "<ADI3 ");
	}

	@Override
	@JsonIgnore
	public String[] getSchemaFileNames() {
		try {
			String schemaFileNames = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.INGEST_VOD_VALIDATION_SCHEMA_KEY);
			return schemaFileNames.split(",");
		} catch (PropertyException e) {
		}
		return new String[0];
	}

	@Override
	@JsonIgnore
	public boolean isSchemaValidationEnabled() {
		try {
			return BooleanUtils.toBoolean(BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.INGEST_VOD_VALIDATION_ENABLE_KEY));
		} catch (PropertyException e) {
		}
		return false;
	}

	@Override
	public List<EntityIngestWrapper<?>> findEntities() throws Exception {
		List<EntityIngestWrapper<?>> ingestWrapperList = new ArrayList<>();
		ingestWrapperList.addAll(findTerms(getInputString()));
		ingestWrapperList.addAll(findPrograms(getInputString()));
		ingestWrapperList.addAll(findSeries(getInputString()));
		ingestWrapperList.addAll(findBrand(getInputString()));
		ingestWrapperList.addAll(findChannels(getInputString()));
		ingestWrapperList.addAll(findEvents(getInputString()));
		ingestWrapperList.addAll(findSubscriptionPackages(getInputString()));
		ingestWrapperList.addAll(findTvodCollections(getInputString()));
		ingestWrapperList.addAll(findOffers(getInputString()));
		return ingestWrapperList;
	}

	protected List<ProgramIngestWrapper> findPrograms(String xmlInput) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);

		List<ProgramIngestWrapper> ingestWrapperList = new ArrayList<>();
		List<Element> contentGroupElementList = findElements(document, OFFER_CONTENT_GROUP_TYPE, OFFER, ELEMENT_CONTENT_GROUP);
		for (Element contentGroupElement: contentGroupElementList) {
			ProgramIngestWrapper ingestWrapper = new ProgramIngestWrapper();
			Program program = new Program();
			ingestWrapper.setEntity(program);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
			processValuesFromBase(program, contentGroupElement);

			List<String> genreList = new ArrayList<>();
			Map<String, String> ratingMap = new HashMap<>();

			for (Element contentRefElement: contentGroupElement.getChildren()) {
				String refUriId = contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE);
				switch (contentRefElement.getName()) {
				case TITLE_REF:
					processTitle(document, program, refUriId, genreList, ratingMap);
					break;
				case CONTENT_MOVIE_REF:
					processVideo(document, program.getVideoContent(), refUriId, CONTENT_MOVIE_TYPE, CONTENT, ELEMENT_MOVIE, VideoContent.ContentType.MOVIE);
					break;
				case CONTENT_PREVIEW_REF:
					processVideo(document, program.getVideoContent(), refUriId, CONTENT_PREVIEW_TYPE, CONTENT, ELEMENT_PREVIEW, VideoContent.ContentType.PREVIEW);
					break;
				case CONTENT_BARKER_REF:
					processVideo(document, program.getVideoContent(), refUriId, CONTENT_BARKER_TYPE, CONTENT, ELEMENT_BARKER, VideoContent.ContentType.BARKER);
					break;
				case CONTENT_BOX_COVER_REF:
					processImage(document, program.getImageContent(), refUriId, CONTENT_BOX_COVER_TYPE, CONTENT, ELEMENT_BOX_COVER, ImageContent.ContentType.BOX_COVER);
					break;
				case CONTENT_POSTER_REF:
					processImage(document, program.getImageContent(), refUriId, CONTENT_POSTER_TYPE, CONTENT, ELEMENT_POSTER, ImageContent.ContentType.POSTER);
					break;
				case CONTENT_THUMBNAIL_REF:
					processImage(document, program.getImageContent(), refUriId, CONTENT_THUMBNAIL_TYPE, CONTENT, ELEMENT_THUMBNAIL, ImageContent.ContentType.THUMBNAIL);
					break;
				default:
				}
			}

			//ingestWrapper.addGenreList(genreList);
			//ingestWrapper.setRatingMap(ratingMap);

			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	protected void processTitle(Document document, BaseMetadataWithTitle entity, String titleUriId) throws JDOMException {
		processTitle(document, entity, titleUriId, null, null);
	}

	protected void processTitle(Document document, BaseMetadataWithTitle entity, String titleUriId, List<String> genreList) throws JDOMException {
		processTitle(document, entity, titleUriId, genreList, null);
	}

	protected void processTitle(Document document, BaseMetadataWithTitle entity, String titleUriId, List<String> genreList, Map<String, String> ratingMap) throws JDOMException {
		Element titleElement = findElementByURIId(document, TITLE_TITLE_TYPE, TITLE, ELEMENT_TITLE, titleUriId);
		if (titleElement == null) {
			return;
		}
		processTitleToBase(entity, titleElement);

		if (genreList != null) {
			List<Element> genres = titleElement.getChildren(ELEMENT_GENRE, NS_TITLE);
			if (genres != null && !genres.isEmpty()){
				for (Element genre : genres) {
					genreList.add(genre.getValue());
				}
			}
		}

		if (ratingMap != null) {
			List<Element> ratingElementList = titleElement.getChildren(ELEMENT_RATING, NS_TITLE);
			for (Element rating : ratingElementList) {
				ratingMap.put(rating.getAttributeValue(ATTRIBUTE_RATING_SYSTEM, Namespace.NO_NAMESPACE), rating.getText());
			}
		}

		if (entity instanceof Program) {
			Program program = (Program)entity;
			List<Element> localizableTitleElementList = titleElement.getChildren(ELEMENT_LOCALIZABLE_TITLE, NS_TITLE);
			Element defaultLocalizableTitleElement = getDefaultLocalizableTitleElement(localizableTitleElementList);
			if (defaultLocalizableTitleElement != null) {
				program.setEpisodeName(defaultLocalizableTitleElement.getChildText(ELEMENT_EPISODE_NAME, NS_TITLE));
				program.setEpisodeId(defaultLocalizableTitleElement.getChildText(ELEMENT_EPISODE_ID, NS_TITLE));
			}
			// code by nitin
			List<Element> GenreElementList = titleElement.getChildren(ELEMENT_GENRE);
			program.setGenre(getLocaleValues(GenreElementList));
			program.setRating(titleElement.getChildText(ELEMENT_RATING, NS_TITLE));
			// Misc elements
			program.setIsClosedCaptioning(getBooleanFromElement(titleElement, ELEMENT_IS_CLOSED_CAPTIONING, NS_TITLE));
			program.setDisplayRunTime(titleElement.getChildText(ELEMENT_DISPLAY_RUN_TIME, NS_TITLE));
			program.setYearOfRelease(getIntegerFromElement(titleElement, ELEMENT_YEAR, NS_TITLE));
			program.setIsSeasonPremier(getBooleanFromElement(titleElement, ELEMENT_IS_SEASON_PREMIER, NS_TITLE));
			// code by nitin
			program.setNDSOfferID(titleElement.getChildText(ELEMENT_NDS_OFFER_ID, NS_TITLE));
			program.setIsDownloadable(getBooleanFromElement(titleElement, ELEMENT_IS_DOWNLOADABLE, NS_TITLE));
			program.setDownloadExpiry(getIntegerFromElement(titleElement, ELEMENT_DOWNLOAD_EXPIRY, NS_TITLE));
			program.setNdsPrice(getIntegerFromElement(titleElement, ELEMENT_NDS_PRICE, NS_TITLE));
			program.setMaxconcurrentstream(getIntegerFromElement(titleElement, ELEMENT_MAX_CONCURRENT_STREAM, NS_TITLE));
			program.setMSORating(getIntegerFromElement(titleElement, ELEMENT_MSO_RATING, NS_TITLE));
			program.setIsSeasonFinale(getBooleanFromElement(titleElement, ELEMENT_IS_SEASON_FINALE, NS_TITLE));
		}
	}
	
	@JsonIgnore
	private Locale getLocaleValues(List<Element> elementList) {
		return getLocaleValues(elementList, -1);
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
	
	private Element getDefaultLocalizableTitleElement(List<Element> localizableTitleElementList) {
		// No elements, no default locale.
		if (localizableTitleElementList == null || localizableTitleElementList.isEmpty()) {
			return null;
		}
		// One element, use this.
		if (localizableTitleElementList.size() == 1) {
			return localizableTitleElementList.get(0);
		}

		// Look for language matching default.
		for (Element localizableTitleElement: localizableTitleElementList) {
			String localeKey = localizableTitleElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML);
			if (LocaleHelper.INGEST_DEFAULT_LOCALE.equals(localeKey)) {
				return localizableTitleElement;
			}
		}

		// Look for language not specified.
		for (Element localizableTitleElement: localizableTitleElementList) {
			String localeKey = localizableTitleElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML);
			if (localeKey == null) {
				return localizableTitleElement;
			}
		}

		// Use the first one in the list.
		return localizableTitleElementList.get(0);
	}

	private void processPeople(List<Person> personList, List<Element> personElementList, Person.Contribution contribution,String localeKey) {

		for (Element personElement : personElementList) {
			Locale personSortName = new Locale();
			Locale personFullName = new Locale();
			Locale personFirstName = new Locale();
			Locale personLastName = new Locale();
			Person person = new Person();
			person.setContribution(contribution);
			person.setSortableName(personSortName);
			person.setFullName(personFullName);
			person.setFirstName(personFirstName);
			person.setLastName(personLastName);
			LocaleHelper.setStringValueForLocale(personSortName, localeKey, personElement.getAttributeValue(ATTRIBUTE_SORTABLE_NAME));
			LocaleHelper.setStringValueForLocale(personFullName, localeKey, personElement.getAttributeValue(ATTRIBUTE_FULL_NAME));
			LocaleHelper.setStringValueForLocale(personFirstName, localeKey, personElement.getAttributeValue(ATTRIBUTE_FIRST_NAME));
			LocaleHelper.setStringValueForLocale(personLastName, localeKey, personElement.getAttributeValue(ATTRIBUTE_LAST_NAME));
			personList.add(person);
		}
	}

	private void processTitleToBase(BaseMetadataWithTitle base, Element title) {
		List<Element> localizableTitleElementList = title.getChildren(ELEMENT_LOCALIZABLE_TITLE, NS_TITLE);

		Locale titleSortName = new Locale();
		base.setTitleSortName(titleSortName);
		Locale titleBrief = new Locale();
		base.setTitleBrief(titleBrief);
		Locale titleMedium = new Locale();
		base.setTitleMedium(titleMedium);
		Locale titleLong = new Locale();
		base.setTitleLong(titleLong);
		Locale summaryShort = new Locale();
		base.setSummaryShort(summaryShort);
		Locale summaryMedium = new Locale();
		base.setSummaryMedium(summaryMedium);
		Locale summaryLong = new Locale();
		base.setSummaryLong(summaryLong);

		for (Element localizableTitleElement: localizableTitleElementList) {
			String localeKey = localizableTitleElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML, LocaleHelper.INGEST_DEFAULT_LOCALE);
			LocaleHelper.setStringValueForLocale(titleSortName, localeKey, localizableTitleElement.getChildText(ELEMENT_TITLE_SORT_NAME, NS_TITLE));
			LocaleHelper.setStringValueForLocale(titleBrief, localeKey, StringUtils.trim(localizableTitleElement.getChildText(ELEMENT_TITLE_BRIEF, NS_TITLE)));
			LocaleHelper.setStringValueForLocale(titleMedium, localeKey, StringUtils.trim(localizableTitleElement.getChildText(ELEMENT_TITLE_MEDIUM, NS_TITLE)));
			LocaleHelper.setStringValueForLocale(titleLong, localeKey, StringUtils.trim(localizableTitleElement.getChildText(ELEMENT_TITLE_LONG, NS_TITLE)));
			LocaleHelper.setStringValueForLocale(summaryShort, localeKey, StringUtils.trim(localizableTitleElement.getChildText(ELEMENT_SUMMARY_SHORT, NS_TITLE)));
			LocaleHelper.setStringValueForLocale(summaryMedium, localeKey, StringUtils.trim(localizableTitleElement.getChildText(ELEMENT_SUMMARY_MEDIUM, NS_TITLE)));
			LocaleHelper.setStringValueForLocale(summaryLong, localeKey, StringUtils.trim(localizableTitleElement.getChildText(ELEMENT_SUMMARY_LONG, NS_TITLE)));
			// Contributors
			if (base instanceof Program) {
				Program program = (Program)base;
					processPeople(program.getContributors(), localizableTitleElement.getChildren(ELEMENT_DIRECTOR, NS_TITLE), Person.Contribution.DIRECTOR,localeKey);
					processPeople(program.getContributors(), localizableTitleElement.getChildren(ELEMENT_ACTOR, NS_TITLE), Person.Contribution.ACTOR,localeKey);
					processPeople(program.getContributors(), localizableTitleElement.getChildren(ELEMENT_WRITER, NS_TITLE), Person.Contribution.WRITER,localeKey);
					processPeople(program.getContributors(), localizableTitleElement.getChildren(ELEMENT_PRODUCER, NS_TITLE), Person.Contribution.PRODUCER,localeKey);
			}
		}


		List<Element> countryOfOriginElementList = title.getChildren(ELEMENT_COUNTRY_OF_ORIGIN, NS_TITLE);
		base.getCountryOfOrigin().clear();
		for (Element countryOfOriginElement: countryOfOriginElementList) {
			String countryOfOrigin = countryOfOriginElement.getValue();
			if (StringUtils.isNotBlank(countryOfOrigin)) {
				if (BeanUtil.localeManager.isIso3166CountryValid(countryOfOrigin)) {
					String country2Id = BeanUtil.localeManager.getCountry2Code(countryOfOrigin);
					base.getCountryOfOrigin().add(country2Id);
				} else if (BeanUtil.localeManager.isIso3166Country2CodeValid(countryOfOrigin)) {
					base.getCountryOfOrigin().add(countryOfOrigin);
				}
			}
		}
		// code by nitin
		base.setKeywords(title.getChildText(ELEMENT_KEYWORDS, NS_TITLE));
		String showTypeString = title.getChildText(ELEMENT_SHOW_TYPE, NS_TITLE);
		if (CABLELABS_SHOW_TYPE_MOVIE.equals(showTypeString)) {
			showTypeString = ShowType.MOVIES.toString();
		} else if (CABLELABS_SHOW_TYPE_MINISERIES.equals(showTypeString)) {
			showTypeString = ShowType.SERIES.toString();
		} else if (showTypeString == null) {
			showTypeString = ShowType.OTHERS.toString();
		}

		ShowType showType = null;
		try {
			showType = ShowType.fromValue(showTypeString);
		} catch (IllegalArgumentException e) {
		}
		if (showType == null) {
			showType = ShowType.OTHERS;
		}
		base.setShowType(showType);
		base.setProvider(title.getChildText(ELEMENT_PROVIDER, NS_CORE));
	}

	private void processVideo(Document document, List<VideoContent> contentList, String uriId, String xmlContentType, String namespace, String element, VideoContent.ContentType contentType) throws JDOMException {
		VideoContent content = new VideoContent();
		content.setContentType(contentType);
		Element contentElement = findElementByURIId(document, xmlContentType, namespace, element, uriId);
		if (contentElement == null) {
			return;
		}
		processValuesFromBase(content, contentElement);
		if(!StringUtils.isEmpty(contentElement.getChildText(ELEMENT_SOURCE_URL, NS_CONTENT)))
			content.setSourceUrl(contentElement.getChildText(ELEMENT_SOURCE_URL, NS_CONTENT));
		if(!StringUtils.isEmpty(contentElement.getChildText(ELEMENT_CONTENT_FILESIZE, NS_CONTENT)))
			content.setFileSize(contentElement.getChildText(ELEMENT_CONTENT_FILESIZE, NS_CONTENT));
		if(!StringUtils.isEmpty(contentElement.getChildText(ELEMENT_CONTENT_CHECKSUM, NS_CONTENT)))
			content.setCheckSum(contentElement.getChildText(ELEMENT_CONTENT_CHECKSUM, NS_CONTENT));
		if(!StringUtils.isEmpty(contentElement.getChildText(ELEMENT_DURATION, NS_CONTENT)))
			content.setDuration(contentElement.getChildText(ELEMENT_DURATION, NS_CONTENT));

		List<Object> languageList = new ArrayList<>();
		List<Element> languages = contentElement.getChildren(ELEMENT_CONTENT_LANGUAGE, NS_CONTENT);
		if (languages != null && !languages.isEmpty()){
			for (Element language : languages) {
				languageList.add(language.getValue());
			}
			content.setLanguage(languageList);
		}

		if(!StringUtils.isEmpty(contentElement.getChildText(ELEMENT_SCREEN_FORMAT, NS_CONTENT))){
			String screenFormat = contentElement.getChildText(ELEMENT_SCREEN_FORMAT, NS_CONTENT);
			try{
				content.setScreenFormat(ScreenFormat.fromValue(screenFormat));
			} catch (IllegalArgumentException e){
			}
		}
		processSubTitle(content,contentElement);
		contentList.add(content);
	}
	/**
	 * This method processes the list of subtitles added to a video content of a program
	 *
	 * @param content
	 * @param contentElement
	 */
	private void processSubTitle(VideoContent content, Element contentElement) {
		List<SubtitleContent> subtitleContentListValues = new ArrayList<>();
		for (Element contentRefElement: contentElement.getChildren()) {
			String name = contentRefElement.getName();
			if(name.equals(CONTENT_SUBTITLE)){
			  SubtitleContent subtitleContent = new SubtitleContent();
			  subtitleContent.setUriId(contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE));
			  subtitleContent.setLanguage(contentRefElement.getChildText(ELEMENT_SUBTITLE_LANGUAGE, NS_IRDETO_TYPES));
			  subtitleContent.setCheckSum(contentRefElement.getChildText(ELEMENT_CONTENT_CHECKSUM, NS_IRDETO_TYPES));
			  subtitleContent.setFileSize(contentRefElement.getChildText(ELEMENT_CONTENT_FILESIZE, NS_IRDETO_TYPES));
			  subtitleContent.setSourceUrl(contentRefElement.getChildText(ELEMENT_SOURCE_URL, NS_IRDETO_TYPES));
			  subtitleContentListValues.add(subtitleContent);
			}
		}
		content.setSubtitleContent(subtitleContentListValues);
	}

	private void processImage(Document document, List<ImageContent> contentList, String uriId, String xmlContentType, String namespace, String element, ImageContent.ContentType contentType) throws JDOMException {
		ImageContent content = new ImageContent();
		content.setContentType(contentType);
		Element contentElement = findElementByURIId(document, xmlContentType, namespace, element, uriId);
		if (contentElement == null) {
			return;
		}
		processValuesFromBase(content, contentElement);
		if(!StringUtils.isEmpty(contentElement.getChildText(ELEMENT_SOURCE_URL, NS_CONTENT)))
			content.setSourceUrl(contentElement.getChildText(ELEMENT_SOURCE_URL, NS_CONTENT));
		if(!StringUtils.isEmpty(contentElement.getChildText(ELEMENT_CONTENT_FILESIZE, NS_CONTENT)))
			content.setFileSize(contentElement.getChildText(ELEMENT_CONTENT_FILESIZE, NS_CONTENT));
		if(!StringUtils.isEmpty(contentElement.getChildText(ELEMENT_CONTENT_CHECKSUM, NS_CONTENT)))
			content.setCheckSum(contentElement.getChildText(ELEMENT_CONTENT_CHECKSUM, NS_CONTENT));
		if(!StringUtils.isEmpty(contentElement.getChildText(ELEMENT_X_RESOLUTION, NS_CONTENT)))
			content.setXResolution(contentElement.getChildText(ELEMENT_X_RESOLUTION, NS_CONTENT));
		if(!StringUtils.isEmpty(contentElement.getChildText(ELEMENT_Y_RESOLUTION, NS_CONTENT)))
			content.setYResolution(contentElement.getChildText(ELEMENT_Y_RESOLUTION, NS_CONTENT));

		contentList.add(content);
	}

	protected List<SeriesIngestWrapper> findSeries(String xmlInput) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);

		List<SeriesIngestWrapper> ingestWrapperList = new ArrayList<>();
		List<Element> seriesAssetElementList = findElements(document, IRDETO_SERIES_TYPE);

		for (Element seriesAssetElement : seriesAssetElementList) {
			SeriesIngestWrapper ingestWrapper = new SeriesIngestWrapper();
			Series series = new Series();
			ingestWrapper.setEntity(series);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
			processValuesFromBase(series, seriesAssetElement);
			String season = seriesAssetElement.getChildText(ELEMENT_SEASON, NS_IRDETO);
			if (StringUtils.isNumeric(season)) {
				series.setSeason(Integer.valueOf(season));
			}
			String programCount = seriesAssetElement.getChildText(ELEMENT_PROGRAM_COUNT, NS_IRDETO);
			if (StringUtils.isNumeric(programCount)) {
				series.setProgramCount(Integer.valueOf(programCount));
			}
			List<Element> elementList = seriesAssetElement.getChildren();
			List<String> programUriIdList = new ArrayList<>();
			List<String> genreList = new ArrayList<>();
			for (Element contentRefElement : elementList) {
				try {
					String refUriId = contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE);
					switch (contentRefElement.getName()) {
					case TITLE_REF:
						processTitle(document, series, refUriId, genreList);
						break;
					case CONTENT_BOX_COVER_REF:
						processImage(document, series.getImageContent(), refUriId, CONTENT_BOX_COVER_TYPE, CONTENT, ELEMENT_BOX_COVER, ImageContent.ContentType.BOX_COVER);
						break;
					case CONTENT_POSTER_REF:
						processImage(document, series.getImageContent(), refUriId, CONTENT_POSTER_TYPE, CONTENT, ELEMENT_POSTER, ImageContent.ContentType.POSTER);
						break;
					case CONTENT_THUMBNAIL_REF:
						processImage(document, series.getImageContent(), refUriId, CONTENT_THUMBNAIL_TYPE, CONTENT, ELEMENT_THUMBNAIL, ImageContent.ContentType.THUMBNAIL);
						break;
					case CONTENT_GROUP_REF:
						programUriIdList.add(refUriId);
						break;
					default:
					}
				} catch (JDOMException e) {
					logger.debug("Error in process Element of Series: " + series.getUriId(), e);
				}
			}
			if(programUriIdList.size()!= 0){
			ingestWrapper.addProgramUriIdList(programUriIdList);
			}
			ingestWrapper.addGenreList(genreList);
			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	protected List<BrandIngestWrapper> findBrand(String xmlInput) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);

		List<BrandIngestWrapper> ingestWrapperList = new ArrayList<>();
		List<Element> brandAssetElementList = findElements(document, IRDETO_BRAND_TYPE);
		for (Element brandAssetElement : brandAssetElementList) {
			BrandIngestWrapper ingestWrapper = new BrandIngestWrapper();
			Brand brand = new Brand();
			ingestWrapper.setEntity(brand);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
			processValuesFromBase(brand, brandAssetElement);
			String seriesCount = brandAssetElement.getChildText(ELEMENT_SERIES_COUNT, NS_IRDETO);
			if (StringUtils.isNumeric(seriesCount)) {
				brand.setSeriesCount(Integer.valueOf(seriesCount));
			}
			List<Element> elementList = brandAssetElement.getChildren();
			List<String> genreList = new ArrayList<>();
			List<String> seriesUriIdList = new ArrayList<>();
			for (Element contentRefElement : elementList) {
				try {
					String refUriId = contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE);
					switch (contentRefElement.getName()) {
					case TITLE_REF:
						processTitle(document, brand, refUriId, genreList);
						break;
					case CONTENT_BOX_COVER_REF:
						processImage(document, brand.getImageContent(), refUriId, CONTENT_BOX_COVER_TYPE, CONTENT, ELEMENT_BOX_COVER, ImageContent.ContentType.BOX_COVER);
						break;
					case CONTENT_POSTER_REF:
						processImage(document, brand.getImageContent(), refUriId, CONTENT_POSTER_TYPE, CONTENT, ELEMENT_POSTER, ImageContent.ContentType.POSTER);
						break;
					case CONTENT_THUMBNAIL_REF:
						processImage(document, brand.getImageContent(), refUriId, CONTENT_THUMBNAIL_TYPE, CONTENT, ELEMENT_THUMBNAIL, ImageContent.ContentType.THUMBNAIL);
						break;
					case SERIES_REF:
						seriesUriIdList.add(refUriId);
						break;
					default:
					}
				} catch (JDOMException e) {
					logger.debug("Error in process Element of Brand: " + brand.getUriId(), e);
				}
			}
			ingestWrapper.addGenreList(genreList);
			ingestWrapper.addSeriesUriIdList(seriesUriIdList);
			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	private List<Namespace> getNamespaceList() {
		List<Namespace> namespaceList = new ArrayList<>();
		namespaceList.add(NS_CORE);
		namespaceList.add(NS_TITLE);
		namespaceList.add(NS_TERMS);
		namespaceList.add(NS_XSI);
		namespaceList.add(NS_XML);
		namespaceList.add(NS_OFFER);
		namespaceList.add(NS_CONTENT);
		namespaceList.add(NS_IRDETO);
		return namespaceList;
	}

	private Boolean getBooleanFromElement(Element surroundingElement, String elementName, Namespace namespace) {
		if (namespace != null) {
			return BooleanUtils.toBooleanObject(surroundingElement.getChildText(elementName, namespace));
		} else {
			return BooleanUtils.toBooleanObject(surroundingElement.getChildText(elementName));
		}
	}

	private Integer getIntegerFromElement(Element surroundingElement, String elementName, Namespace namespace) {
		String value = null;
		if (namespace != null) {
			value = surroundingElement.getChildText(elementName, namespace);
		} else {
			value = surroundingElement.getChildText(elementName);
		}
		if (StringUtils.isNumeric(value)) {
			return Integer.valueOf(value);
		} else {
			return null;
		}
	}

	protected List<SubscriptionPackageIngestWrapper> findSubscriptionPackages(String xmlInput) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);

		List<SubscriptionPackageIngestWrapper> ingestWrapperList = new ArrayList<>();
		List<Element> subscriptionPackageAssetElementList = findElements(document, IRDETO_SUBSCRIPTION_PACKAGE_TYPE);

		for (Element subscriptionPackageAssetElement : subscriptionPackageAssetElementList) {
			SubscriptionPackageIngestWrapper ingestWrapper = new SubscriptionPackageIngestWrapper();
			SubscriptionPackage subscriptionPackage = new SubscriptionPackage();
			subscriptionPackage.setAlacarte(BooleanUtils.toBoolean(subscriptionPackageAssetElement.getChildText(ELEMENT_ALACARTE, NS_IRDETO)));
			subscriptionPackage.setSmsPackageId(subscriptionPackageAssetElement.getChildText(ELEMENT_SMS_PACKAGE_ID, NS_IRDETO));
			ingestWrapper.setEntity(subscriptionPackage);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
			processValuesFromBase(subscriptionPackage, subscriptionPackageAssetElement);
			List<Element> elementList = subscriptionPackageAssetElement.getChildren();
			List<String> genreList = new ArrayList<>();
			List<String> programUriIdList = new ArrayList<>();
			List<String> seriesUriIdList = new ArrayList<>();
			List<String> brandUriIdList = new ArrayList<>();
			List<String> channelUriIdList = new ArrayList<>();
			List<String> eventUriIdList = new ArrayList<>();
			for (Element contentRefElement : elementList) {
				try {
					String refUriId = contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE);
					switch (contentRefElement.getName()) {
					case TITLE_REF:
						processTitle(document, subscriptionPackage, refUriId, genreList);
						break;
					case CONTENT_BOX_COVER_REF:
						processImage(document, subscriptionPackage.getImageContent(), refUriId, CONTENT_BOX_COVER_TYPE, CONTENT, ELEMENT_BOX_COVER, ImageContent.ContentType.BOX_COVER);
						break;
					case CONTENT_POSTER_REF:
						processImage(document, subscriptionPackage.getImageContent(), refUriId, CONTENT_POSTER_TYPE, CONTENT, ELEMENT_POSTER, ImageContent.ContentType.POSTER);
						break;
					case CONTENT_THUMBNAIL_REF:
						processImage(document, subscriptionPackage.getImageContent(), refUriId, CONTENT_THUMBNAIL_TYPE, CONTENT, ELEMENT_THUMBNAIL, ImageContent.ContentType.THUMBNAIL);
						break;
					case CONTENT_GROUP_REF:
						programUriIdList.add(refUriId);
						break;
					case SERIES_REF:
						seriesUriIdList.add(refUriId);
						break;
					case BRAND_REF:
						brandUriIdList.add(refUriId);
						break;
					case EVENT_REF:
						eventUriIdList.add(refUriId);
						break;
					case CHANNEL_REF:
						channelUriIdList.add(refUriId);
						break;
					default:
					}
				} catch (JDOMException e) {
					logger.error("Error in process Element of Subscription Package: " + subscriptionPackage.getUriId(), e);
				}
			}
			ingestWrapper.addGenreList(genreList);
			if(programUriIdList.size()!= 0){
			ingestWrapper.addProgramUriIdList(programUriIdList);
			}
			ingestWrapper.addSeriesUriIdList(seriesUriIdList);
			ingestWrapper.addBrandUriIdList(brandUriIdList);
			ingestWrapper.addEventUriIdList(eventUriIdList);
			ingestWrapper.addChannelUriIdList(channelUriIdList);
			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	public List<TvodCollectionIngestWrapper> findTvodCollections(String xmlInput) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);

		List<TvodCollectionIngestWrapper> ingestWrapperList = new ArrayList<>();
		List<Element> tvodCollectionAssetElementList = findElements(document, IRDETO_TVOD_COLLECTION_TYPE);
		for (Element tvodCollectionAssetElement : tvodCollectionAssetElementList) {
			TvodCollectionIngestWrapper ingestWrapper = new TvodCollectionIngestWrapper();
			TvodCollection tvodCollection = new TvodCollection();
			ingestWrapper.setEntity(tvodCollection);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
			processValuesFromBase(tvodCollection, tvodCollectionAssetElement);
			List<Element> elementList = tvodCollectionAssetElement.getChildren();
			List<String> programUriIdList = new ArrayList<>();
			List<String> seriesUriIdList = new ArrayList<>();
			List<String> brandUriIdList = new ArrayList<>();
			List<String> eventUriIdList = new ArrayList<>();
			List<String> genreList = new ArrayList<>();
			for (Element contentRefElement : elementList) {
				try {
					String refUriId = contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE);
					switch (contentRefElement.getName()) {
					case TITLE_REF:
						processTitle(document, tvodCollection, refUriId, genreList);
						break;
					case CONTENT_BOX_COVER_REF:
						processImage(document, tvodCollection.getImageContent(), refUriId, CONTENT_BOX_COVER_TYPE, CONTENT, ELEMENT_BOX_COVER, ImageContent.ContentType.BOX_COVER);
						break;
					case CONTENT_POSTER_REF:
						processImage(document, tvodCollection.getImageContent(), refUriId, CONTENT_POSTER_TYPE, CONTENT, ELEMENT_POSTER, ImageContent.ContentType.POSTER);
						break;
					case CONTENT_THUMBNAIL_REF:
						processImage(document, tvodCollection.getImageContent(), refUriId, CONTENT_THUMBNAIL_TYPE, CONTENT, ELEMENT_THUMBNAIL, ImageContent.ContentType.THUMBNAIL);
						break;
					case CONTENT_GROUP_REF:
						programUriIdList.add(refUriId);
						break;
					case SERIES_REF:
						seriesUriIdList.add(contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE));
						break;
					case BRAND_REF:
						brandUriIdList.add(contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE));
						break;
					case EVENT_REF:
						eventUriIdList.add(contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE));
						break;
					default:
					}
				} catch (JDOMException e) {
					logger.error("Error in process Element of Subscription Package: " + tvodCollection.getUriId(), e);
				}
			}
			ingestWrapper.addBrandUriIdList(brandUriIdList);
			ingestWrapper.addEventUriIdList(eventUriIdList);
			ingestWrapper.addGenreList(genreList);
			if(programUriIdList.size()!= 0){
			ingestWrapper.addProgramUriIdList(programUriIdList);
			}
			ingestWrapper.addSeriesUriIdList(seriesUriIdList);
			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	protected List<OfferIngestWrapper> findOffers(String xmlInput) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);

		List<OfferIngestWrapper> ingestWrapperList = new ArrayList<>();
		List<Element> offerAssetElementList = findElements(document, OFFER_OFFER_TYPE, OFFER, ELEMENT_OFFER);
		for (Element offerAssetElement : offerAssetElementList) {
			OfferIngestWrapper ingestWrapper = new OfferIngestWrapper();
			Offer offer = new Offer();
			ingestWrapper.setEntity(offer);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
			processValuesFromBase(offer, offerAssetElement);
			offer.setBillingId(offerAssetElement.getChildText(ELEMENT_BILLING_ID, NS_OFFER));
			offer.setOffertype(offerAssetElement.getChildText(ELEMENT_OFFER_TYPE, NS_OFFER));
			offer.setOfferpackageid(offerAssetElement.getChildText(ELEMENT_OFFER_PACKAGE_ID, NS_OFFER));
			List<Element> elementList = offerAssetElement.getChildren();
			List<String> brandUriIdList = new ArrayList<>();
			List<String> seriesUriIdList = new ArrayList<>();
			List<String> programUriIdList = new ArrayList<>();
			List<String> eventUriIdList = new ArrayList<>();
			List<String> tvodCollectionUriIdList = new ArrayList<>();
			List<String> subscriptionPackageUriIdList = new ArrayList<>();
			List<String> termUriIdList = new ArrayList<>();

			for (Element contentRefElement : elementList) {
				switch (contentRefElement.getName()) {
				case CONTENT_GROUP_REF:
					String uriId = contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE);
					Element contentGroup = findElementByURIId(document, OFFER_CONTENT_GROUP_TYPE, OFFER, ELEMENT_CONTENT_GROUP, uriId);
					if (contentGroup != null) {
						programUriIdList.add(uriId);
						break;
					}
					contentGroup = findElementByURIId(document, IRDETO_SERIES_TYPE, uriId);
					if (contentGroup != null) {
						seriesUriIdList.add(uriId);
						break;
					}
					contentGroup = findElementByURIId(document, IRDETO_BRAND_TYPE, uriId);
					if (contentGroup != null) {
						brandUriIdList.add(uriId);
						break;
					}
					contentGroup = findElementByURIId(document, IRDETO_TVOD_COLLECTION_TYPE, uriId);
					if (contentGroup != null) {
						tvodCollectionUriIdList.add(uriId);
						break;
					}
					contentGroup = findElementByURIId(document, IRDETO_SUBSCRIPTION_PACKAGE_TYPE, uriId);
					if (contentGroup != null) {
						subscriptionPackageUriIdList.add(uriId);
						break;
					}
					contentGroup = findElementByURIId(document, IRDETO_EVENT_TYPE, uriId);
					if (contentGroup != null) {
						eventUriIdList.add(uriId);
						break;
					}
					break;
				case TERMS_REF:
					termUriIdList.add(contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE));
					break;
				default:

				}
			}

			ingestWrapper.addBrandUriIdList(brandUriIdList);
			ingestWrapper.addEventUriIdList(eventUriIdList);
			if(programUriIdList.size()!= 0){
			ingestWrapper.addProgramUriIdList(programUriIdList);
			}
			ingestWrapper.addSeriesUriIdList(seriesUriIdList);
			ingestWrapper.addSubscriptionPackageUriIdList(subscriptionPackageUriIdList);
			ingestWrapper.addTvodCollectionUriIdList(tvodCollectionUriIdList);
			ingestWrapper.addTermUriIdList(termUriIdList);
			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	protected List<TermIngestWrapper> findTerms(String xmlInput) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);

		List<TermIngestWrapper> termWrapperList = new ArrayList<>();
		List<Element> termAssetElementList = findElements(document, TERMS_TERMS_TYPE, TERMS, ELEMENT_TERMS);
		for (Element termAssetElement : termAssetElementList) {
			TermIngestWrapper termWrapper = new TermIngestWrapper();
			Term term = new Term();
			termWrapper.setEntity(term);
			processValuesFromBase(term, termAssetElement);
			Map<String, Map<String, String>> priceMap = getTermPrice(termAssetElement);
			term.setSuggestedPrice(priceMap);
			String contractName = termAssetElement.getChildText(ELEMENT_CONTRACT_NAME, NS_TERMS);
			if (StringUtils.isNotBlank(contractName)) {
				term.setContractName(contractName);
				List<String> contractNameList = new ArrayList<>();
				contractNameList.add(contractName);
				termWrapper.addTermMappingContractNameList(contractNameList);
			}
			termWrapperList.add(termWrapper);
		}
		return termWrapperList;
	}

	private void processValuesFromBase(Base base, Element assetElement) {
		base.setUriId(assetElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE));
		// Availability dates
		String startDateString = assetElement.getAttributeValue(ATTRIBUTE_START_DATE_TIME);
		if (StringUtils.isNotBlank(startDateString)) {
			base.setStartDateTime(DateHelper.convertXMLDateToDateTime(startDateString));
		}
		String endDateString = assetElement.getAttributeValue(ATTRIBUTE_END_DATE_TIME);
		if (StringUtils.isNotBlank(endDateString)) {
			base.setEndDateTime(DateHelper.convertXMLDateToDateTime(endDateString));
		}
	}

	private Map<String, Map<String, String>> getTermPrice(
			Element termAssetElement) {
		List<Element> suggestedPriceElementList = termAssetElement.getChildren(ELEMENT_SUGGESTED_PRICE, NS_TERMS);
		Map<String, Map<String, String>> priceMap = new HashMap<>();
		if (suggestedPriceElementList != null) {
			for (Element suggestedPriceElement: suggestedPriceElementList) {
				String localeString = suggestedPriceElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML);
				if (localeString == null) {
					localeString = LocaleHelper.INGEST_DEFAULT_LOCALE;
				}
				String countryCode;
                if (!"01".equals(localeString)) {
                       java.util.Locale locale = LocaleUtils.toLocale(localeString);
                       countryCode = BeanUtil.localeManager.getCountry2Code(locale.getDisplayCountry());
                } else {
                       countryCode = "01";
                }
				String currency = suggestedPriceElement.getAttributeValue(ATTRIBUTE_CURRENCY, NS_CORE);
				if (currency == null) {
					currency = LocaleHelper.DEFAULT_CURRENCY;
				}
				String price = suggestedPriceElement.getText();
				if (countryCode != null && price != null) {
					Map<String, String> currencyMap = priceMap.get(countryCode);
					if (currencyMap == null) {
						currencyMap = new HashMap<>();
						priceMap.put(countryCode, currencyMap);
					}
					currencyMap.put(currency, price);
				}
			}
		}
		return priceMap;
	}

	protected List<ChannelIngestWrapper> findChannels(String xmlInput) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);

		List<ChannelIngestWrapper> ingestWrapperList = new ArrayList<>();
		List<Element> channelAssetElementList = findElements(document, IRDETO_CHANNEL_TYPE);
		for (Element channelAssetElement : channelAssetElementList) {
			ChannelIngestWrapper ingestWrapper = new ChannelIngestWrapper();
			Channel channel = new Channel();
			ingestWrapper.setEntity(channel);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
			processValuesFromBase(channel, channelAssetElement);
			String channelId = channelAssetElement.getChildText(ELEMENT_CHANNEL_ID, NS_IRDETO);
			// code by nitin
			channel.setEnabled(BooleanUtils.toBoolean(channelAssetElement.getChildText(ELEMENT_IS_ENABLED, NS_IRDETO)));
			channel.setAllowedOnBrowsers(BooleanUtils.toBoolean(channelAssetElement.getChildText(ELEMENT_ALLOW_ON_BROWSER, NS_IRDETO)));
			channel.setFreeChannel(BooleanUtils.toBoolean(channelAssetElement.getChildText(ELEMENT_FREE_CHANNEL, NS_IRDETO)));
			channel.setHDChannel(BooleanUtils.toBoolean(channelAssetElement.getChildText(ELEMENT_HD_CHANNEL, NS_IRDETO)));
			channel.setCatchupChannel(BooleanUtils.toBoolean(channelAssetElement.getChildText(ELEMENT_CATCHUP_CHANNEL, NS_IRDETO)));
			channel.setNumberOfAudio(getIntegerFromElement(channelAssetElement, ELEMENT_NUMBER_OF_AUDIO, NS_IRDETO));
			channel.setBroadcastServiceId(channelAssetElement.getChildText(ELEMENT_BROADCAST_SERVICE_ID, NS_IRDETO));
			channel.setUdpMulticastIP(channelAssetElement.getChildText(ELEMENT_UDP_MULTICAST_IP, NS_IRDETO));
			channel.setChannelPackager(channelAssetElement.getChildText(ELEMENT_CHANNEL_PACKAGER, NS_IRDETO));
			boolean isIdBlank = StringUtils.isBlank(channelId);
			if(isIdBlank){
				logger.warn("Channel with URI ID {} as no channel ID and so will not be ingested.",channel.getUriId());
				break;
			}
			channel.setChannelId(channelId);
			String displayOrder = channelAssetElement.getChildText(ELEMENT_DISPLAY_ORDER, NS_IRDETO);
			if (StringUtils.isNumeric(displayOrder)) {
				channel.setDisplayOrder(Integer.valueOf(displayOrder));
			}
			String liveWindowDuration = channelAssetElement.getChildText(ELEMENT_LIVE_WINDOW_DURATION, NS_IRDETO);
			if (StringUtils.isNumeric(liveWindowDuration)) {
				channel.setLiveWindowDuration(Integer.valueOf(liveWindowDuration));
			}

			List<Element> encodeProfileElementList = channelAssetElement.getChildren(ELEMENT_ENCODE_PROFILE, NS_IRDETO);
			List<String> encodeProfileNameList = new ArrayList<>();
			for (Element encodeProfileElement: encodeProfileElementList) {
				encodeProfileNameList.add(encodeProfileElement.getText());
			}
			ingestWrapper.addEncodeProfileNameList(encodeProfileNameList);

			List<Element> elementList = channelAssetElement.getChildren();
			for (Element contentRefElement : elementList) {
				try {
					String refUriId = contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE);
					switch (contentRefElement.getName()) {
					case TITLE_REF:
						processTitle(document, channel, refUriId);
						break;
					case CONTENT_BOX_COVER_REF:
						processImage(document, channel.getImageContent(), refUriId, CONTENT_BOX_COVER_TYPE, CONTENT, ELEMENT_BOX_COVER, ImageContent.ContentType.BOX_COVER);
						break;
					case CONTENT_POSTER_REF:
						processImage(document, channel.getImageContent(), refUriId, CONTENT_POSTER_TYPE, CONTENT, ELEMENT_POSTER, ImageContent.ContentType.POSTER);
						break;
					case CONTENT_THUMBNAIL_REF:
						processImage(document, channel.getImageContent(), refUriId, CONTENT_THUMBNAIL_TYPE, CONTENT, ELEMENT_THUMBNAIL, ImageContent.ContentType.THUMBNAIL);
						break;
					default:
					}
				} catch (JDOMException e) {
					logger.debug("Error in process Element of Channel: " + channel.getUriId(), e);
				}
			}
			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	protected List<EventIngestWrapper> findEvents(String xmlInput) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);

		List<EventIngestWrapper> ingestWrapperList = new ArrayList<>();
		List<Element> eventAssetElementList = findElements(document, IRDETO_EVENT_TYPE);
		for (Element eventAssetElement : eventAssetElementList) {
			EventIngestWrapper ingestWrapper = new EventIngestWrapper();
			Event event = new Event();
			ingestWrapper.setEntity(event);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
			processValuesFromBase(event, eventAssetElement);

			String eventBroadcastDate = eventAssetElement.getChildText(ELEMENT_EVENT_BROADCAST_DATE, NS_IRDETO);
			if (StringUtils.isNotBlank(eventBroadcastDate)) {
				event.setEventBroadcastDate(DateHelper.convertXMLDateToDateTime(eventBroadcastDate));
			}

			String eventBroadcastEndDate = eventAssetElement.getChildText(ELEMENT_EVENT_BROADCAST_END_DATE, NS_IRDETO);
			if (StringUtils.isNotBlank(eventBroadcastEndDate)) {
				event.setEventBroadcastEndDate(DateHelper.convertXMLDateToDateTime(eventBroadcastEndDate));
			}

			String screenFormat = eventAssetElement.getChildText(ELEMENT_SCREEN_FORMAT, NS_IRDETO);
			if (StringUtils.isNotBlank(screenFormat)) {
				event.setScreenFormat(Event.ScreenFormat.fromValue(screenFormat));
			}

			List<Element> encodeProfileElementList = eventAssetElement.getChildren(ELEMENT_ENCODE_PROFILE, NS_IRDETO);
			List<String> encodeProfileNameList = new ArrayList<>();
			for (Element encodeProfileElement: encodeProfileElementList) {
				encodeProfileNameList.add(encodeProfileElement.getText());
			}
			ingestWrapper.addEncodeProfileNameList(encodeProfileNameList);

			List<Element> scheduleSlotIdElementList = eventAssetElement.getChildren(ELEMENT_SCHEDULE_SLOT_ID, NS_IRDETO);
			List<String> scheduleSlotIdList = new ArrayList<>();
			for (Element scheduleSlotIdElement: scheduleSlotIdElementList) {
				scheduleSlotIdList.add(scheduleSlotIdElement.getText());
			}
			ingestWrapper.addScheduleSlotIdList(scheduleSlotIdList);

			List<String> programUriIdList = new ArrayList<>();
			List<String> genreList = new ArrayList<>();
			Map<String, String> ratingMap = new HashMap<>();

			List<Element> elementList = eventAssetElement.getChildren();
			for (Element contentRefElement : elementList) {
				try {
					String refUriId = contentRefElement.getAttributeValue(ATTRIBUTE_URI_ID, Namespace.NO_NAMESPACE);
					switch (contentRefElement.getName()) {
					case TITLE_REF:
						processTitle(document, event, refUriId, genreList, ratingMap);
						break;
					case CONTENT_BOX_COVER_REF:
						processImage(document, event.getImageContent(), refUriId, CONTENT_BOX_COVER_TYPE, CONTENT, ELEMENT_BOX_COVER, ImageContent.ContentType.BOX_COVER);
						break;
					case CONTENT_POSTER_REF:
						processImage(document, event.getImageContent(), refUriId, CONTENT_POSTER_TYPE, CONTENT, ELEMENT_POSTER, ImageContent.ContentType.POSTER);
						break;
					case CONTENT_THUMBNAIL_REF:
						processImage(document, event.getImageContent(), refUriId, CONTENT_THUMBNAIL_TYPE, CONTENT, ELEMENT_THUMBNAIL, ImageContent.ContentType.THUMBNAIL);
						break;
					case CONTENT_GROUP_REF:
						programUriIdList.add(refUriId);
						break;
					default:
					}
				} catch (JDOMException e) {
					logger.debug("Error in process Element of Event: " + event.getUriId(), e);
				}
			}
			if(programUriIdList.size()!= 0){
			ingestWrapper.addProgramUriIdList(programUriIdList);
			}
			ingestWrapper.setRatingMap(ratingMap);
			ingestWrapper.addGenreList(genreList);
			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	private Element findElementByURIId(Document document, String type, String uriId) {
		return findElementByURIId(document, type, null, null, uriId);
	}

	private Element findElementByURIId(Document document, String type, String namespace, String element, String uriId) {
		List<Namespace> namespaces = getNamespaceList();
		XPathFactory xPathFactory = XPathFactory.instance();
		if (StringUtils.isNotBlank(type)) {
			XPathExpression<Element> path = xPathFactory.compile("//" + CORE + ":" + ELEMENT_ASSET + "[@xsi:type='" + type + "' and @uriId='" + uriId + "']", Filters.element(), null, namespaces);
			List<Element> nodes = path.evaluate(document);
			if (nodes.size() >= 1) {
				return nodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(namespace) && StringUtils.isNotBlank(element)) {
			XPathExpression<Element> path = xPathFactory.compile("//" + namespace + ":" + element + "[@uriId='" + uriId + "']", Filters.element(), null, namespaces);
			List<Element> nodes = path.evaluate(document);
			if (nodes.size() >= 1) {
				return nodes.get(0);
			}
		}
		if (StringUtils.isNotBlank(element)) {
			XPathExpression<Element> path = xPathFactory.compile("//" + CORE+ ":" + element + "[@uriId='" + uriId + "']", Filters.element(), null, namespaces);
			List<Element> nodes = path.evaluate(document);
			if (nodes.size() >= 1) {
				return nodes.get(0);
			}
		}
		return null;
	}

	private List<Element> findElements(Document document, String type) {
		return findElements(document, type, null, null);
	}

	private List<Element> findElements(Document document, String type, String namespace, String element) {
		List<Element> elementList = new ArrayList<>();
		List<Namespace> namespaces = getNamespaceList();
		if (StringUtils.isNotBlank(type)) {
			XPathExpression<Element> path = XPathFactory.instance().compile("//" + CORE + ":" + ELEMENT_ASSET + "[@xsi:type='" + type + "']", Filters.element(), null, namespaces);
			List<Element> nodes = path.evaluate(document);
			elementList.addAll(nodes);
		}
		if (StringUtils.isNotBlank(namespace) && StringUtils.isNotBlank(element)) {
			XPathExpression<Element> path = XPathFactory.instance().compile("//" + namespace + ":" + element, Filters.element(), null, namespaces);
			List<Element> nodes = path.evaluate(document);
			elementList.addAll(nodes);
		}
        if (StringUtils.isNotBlank(element)) {
			XPathExpression<Element> path = XPathFactory.instance().compile("//" + CORE + ":" + element, Filters.element(), null, namespaces);
			List<Element> nodes = path.evaluate(document);
			elementList.addAll(nodes);
        }
		return elementList;
	}
}
