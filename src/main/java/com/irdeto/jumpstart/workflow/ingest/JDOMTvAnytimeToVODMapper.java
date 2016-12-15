package com.irdeto.jumpstart.workflow.ingest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.helper.xmlmodel.JDOMDocumentHelper;
import com.irdeto.jumpstart.domain.Locale;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Person;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.ingest.BrandIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.OfferIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.ProgramIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SeriesIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SourceInformation;
import com.irdeto.jumpstart.domain.ingest.TermIngestWrapper;
import com.irdeto.jumpstart.workflow.LocaleHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.task.BeanUtil;

public class JDOMTvAnytimeToVODMapper extends AbstractVodIngestMapper implements VodIngestMapper {
	private static final String TERM_URIID = "irdeto.com/Terms/0001";
	private static final String TERM_PRICE = "4.99";
	private static final String TERM_CURRENCY = "USD";
	private static final String TERM_CONTRACT_NAME = "AS Contract";

	private static final String OFFER_BILLING_ID = "12345";

	private static final String VARIABLE_CR_ID = "crId";
	private static final String VARIABLE_ROLE = "role";

	private static final Namespace NS_TVA = Namespace.getNamespace("tva", "urn:tva:metadata:2008");
	private static final Namespace NS_XSI = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	private static final Namespace NS_XML = Namespace.getNamespace("xml", "http://www.w3.org/XML/1998/namespace");
	private static final Namespace NS_MPEG7 = Namespace.getNamespace("mpeg7", "urn:tva:mpeg7:2008");

	private static final String ATTRIBUTE_GROUP_ID = "groupId";
	private static final String ATTRIBUTE_INDEX = "index";
	private static final String ATTRIBUTE_LANG = "lang";
	private static final String ATTRIBUTE_PROGRAM_ID = "programId";

	private static final String ELEMENT_SALUTATION = "Salutation";
	private static final String ELEMENT_GIVEN_NAME = "GivenName";
	private static final String ELEMENT_FAMILY_NAME = "FamilyName";

	private static final List<Namespace> NAMESPACE_LIST = new ArrayList<>();
	private static final Map<String, String> GENRE_MAP = new HashMap<>();

	static {
		NAMESPACE_LIST.add(NS_TVA);
		NAMESPACE_LIST.add(NS_XSI);
		NAMESPACE_LIST.add(NS_XML);
		NAMESPACE_LIST.add(NS_MPEG7);
		GENRE_MAP.put("Spielfilm", "Drama");
	}

	private static final Logger logger = LoggerFactory.getLogger(JDOMTvAnytimeToVODMapper.class);

	@Override
	@JsonIgnore
	public boolean isApplicableMapper() {
		return StringUtils.contains(getInputString(), "<TVAMain ");
	}

	@Override
	@JsonIgnore
	public String[] getSchemaFileNames() {
		return new String[0];
	}

	@Override
	@JsonIgnore
	public boolean isSchemaValidationEnabled() {
		return false;
	}

	@Override
	public List<EntityIngestWrapper<?>> findEntities() throws Exception {
		List<EntityIngestWrapper<?>> ingestWrapperList = new ArrayList<>();
		ingestWrapperList.addAll(findTerms(getInputString()));
		ingestWrapperList.addAll(findPrograms(getInputString()));
		ingestWrapperList.addAll(findSeries(getInputString()));
		ingestWrapperList.addAll(findBrand(getInputString()));
		ingestWrapperList.addAll(findOffers(getInputString()));
		return ingestWrapperList;
	}

	protected List<ProgramIngestWrapper> findPrograms(String xmlInput) throws Exception {
		XPathExpression<Element> programInformationPath = XPathFactory.instance().compile("/tva:TVAMain/tva:ProgramDescription/tva:ProgramInformationTable/tva:ProgramInformation", Filters.element(), null, NAMESPACE_LIST);
		XPathExpression<Element> programTitlePath = XPathFactory.instance().compile("tva:BasicDescription/tva:Title[@type='main']", Filters.element(), null, NAMESPACE_LIST);
		XPathExpression<Element> programSynopsisPath = XPathFactory.instance().compile("tva:BasicDescription/tva:Synopsis[@length='long']", Filters.element(), null, NAMESPACE_LIST);
		XPathExpression<Element> productionLocationPath = XPathFactory.instance().compile("tva:BasicDescription/tva:ProductionLocation", Filters.element(), null, NAMESPACE_LIST);
		XPathExpression<Element> memberOfTypeEpisodeOfTypePath = XPathFactory.instance().compile("tva:MemberOf[@type='EpisodeOfType']", Filters.element(), null, NAMESPACE_LIST);
		XPathExpression<Element> durationPath = XPathFactory.instance().compile("tva:BasicDescription/tva:Duration", Filters.element(), null, NAMESPACE_LIST);
		XPathExpression<Element> productionDateTimePointPath = XPathFactory.instance().compile("tva:BasicDescription/tva:ProductionDate/tva:TimePoint", Filters.element(), null, NAMESPACE_LIST);

		List<ProgramIngestWrapper> ingestWrapperList = new ArrayList<>();

		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		List<Element> programInformationElementList = programInformationPath.evaluate(document);
		for (Element programInformationElement: programInformationElementList) {
			ProgramIngestWrapper ingestWrapper = new ProgramIngestWrapper();
			Program program = new Program();
			program.setUriId(programInformationElement.getAttributeValue(ATTRIBUTE_PROGRAM_ID));

			//Title
			List<Element> titleElementList = programTitlePath.evaluate(programInformationElement);
			Locale titleBriefLocale = new Locale();
			program.setTitleBrief(titleBriefLocale);
			Locale titleMediumLocale = new Locale();
			program.setTitleMedium(titleMediumLocale);
			Locale titleLongLocale = new Locale();
			program.setTitleLong(titleLongLocale);
			Locale titleSortNameLocale = new Locale();
			program.setTitleSortName(titleSortNameLocale);
			for (Element titleElement: titleElementList) {
				String language = titleElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML, LocaleHelper.DEFAULT_LANGUAGE_NAME);
				String title = titleElement.getValue();
				LocaleHelper.setStringValueForLanguage(titleBriefLocale, language, title);
				LocaleHelper.setStringValueForLanguage(titleMediumLocale, language, title);
				LocaleHelper.setStringValueForLanguage(titleLongLocale, language, title);
				LocaleHelper.setStringValueForLanguage(titleSortNameLocale, language, title);
				//TODO this should be set only if the default language is matched.
				//if (StringUtils.isEmpty(language) || LocaleHelper.DEFAULT_LANGUAGE_NAME.equals(language)) {
					program.setEpisodeName(title);
				//}
			}

			//Summary
			List<Element> synopsisElementList = programSynopsisPath.evaluate(programInformationElement);
			Locale summaryLongLocale = new Locale();
			program.setSummaryLong(summaryLongLocale);
			for (Element synopsisElement: synopsisElementList) {
				String language = synopsisElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML, LocaleHelper.DEFAULT_LANGUAGE_NAME);
				String synopsis = synopsisElement.getValue();
				LocaleHelper.setStringValueForLanguage(summaryLongLocale, language, synopsis);
			}

			//Country of Origin
			List<Element> productionLocationElementList = productionLocationPath.evaluate(programInformationElement);
			for (Element productionLocationElement: productionLocationElementList) {
				String productionLocation = productionLocationElement.getValue();
				if (StringUtils.isNotBlank(productionLocation)) {
					if (BeanUtil.localeManager.isIso3166CountryValid(productionLocation)) {
						String country2Id = BeanUtil.localeManager.getCountry2Code(productionLocation);
						program.getCountryOfOrigin().add(country2Id);
					} else if (BeanUtil.localeManager.isIso3166Country2CodeValid(productionLocation)) {
						program.getCountryOfOrigin().add(productionLocation);
					}
				}
			}

			//Show type
			//TODO Need to fix this against a dynamic mapping from Genre
			program.setShowType(Program.ShowType.MOVIES);

			//EpisodeId
			List<Element> memberOfTypeElementList = memberOfTypeEpisodeOfTypePath.evaluate(programInformationElement);
			for (Element memberOfTypeElement: memberOfTypeElementList) {
				String index = memberOfTypeElement.getAttributeValue(ATTRIBUTE_INDEX);
				if (StringUtils.isNotBlank(index)) {
					program.setEpisodeId(index);
				}
			}

			//People
			program.getContributors().addAll(getPersonList(programInformationElement, "urn:mpeg:mpeg7:cs:RoleCS:2001:ACTOR", Person.Contribution.ACTOR));
			program.getContributors().addAll(getPersonList(programInformationElement, "urn:mpeg:mpeg7:cs:RoleCS:2001:DIRECTOR", Person.Contribution.DIRECTOR));
			program.getContributors().addAll(getPersonList(programInformationElement, "urn:mpeg:mpeg7:cs:RoleCS:2001:PRODUCER", Person.Contribution.PRODUCER));
			program.getContributors().addAll(getPersonList(programInformationElement, "urn:mpeg:mpeg7:cs:RoleCS:2001:SCRIPTWRITER", Person.Contribution.WRITER));

			//Start and End Date
			//TODO Currently hard coded
			program.setStartDateTime(WorkflowHelper.START_OF_TIME);
			program.setEndDateTime(WorkflowHelper.END_OF_TIME);

			//Closed Captioning
			//TODO Currently hard coded
			program.setIsClosedCaptioning(false);

			//Runtime
			List<Element> durationElementList = durationPath.evaluate(programInformationElement);
			for (Element durationElement: durationElementList) {
				String duration = durationElement.getValue();
				if (StringUtils.isNotBlank(duration)) {
					program.setDisplayRunTime(duration);
				}
			}

			//Year of release
			List<Element> productionDateElementList = productionDateTimePointPath.evaluate(programInformationElement);
			for (Element productionDateElement: productionDateElementList) {
				String productionDate = productionDateElement.getValue();
				if (StringUtils.isNumeric(productionDate)) {
					program.setYearOfRelease(Integer.valueOf(productionDate));
				}
			}

			//Premier flag
			//TODO Currently hard coded
			program.setIsSeasonPremier(false);

			//Season finale flag
			//TODO Currently hard coded
			program.setIsSeasonFinale(false);

			ingestWrapper.setEntity(program);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
			List<String> genreList = getProgramGenreList(xmlInput, program);
			if(genreList != null && !genreList.isEmpty()) {
				ingestWrapper.addGenreList(genreList);
			}
			Map<String, String> ratingMap = getProgramRatingMap(xmlInput, program);
			if(ratingMap != null && !ratingMap.isEmpty()) {
				ingestWrapper.setRatingMap(ratingMap);
			}

			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	private Map<String, String> getProgramRatingMap(String xmlInput, Program program) throws Exception {
		XPathExpression<Element> parentalGuidancePath = XPathFactory.instance().compile("tva:BasicDescription/tva:ParentalGuidance/mpeg7:MinimumAge", Filters.element(), null, NAMESPACE_LIST);

		Map<String, String> programRatingMap = new HashMap<>();

		Element programInformationElement = getProgramInformationElement(xmlInput, program.getUriId());
		List<Element> parentalGuidanceElementList = parentalGuidancePath.evaluate(programInformationElement);
		// TODO Generalize for different rating schemes
		for (Element parentalGuidanceElement: parentalGuidanceElementList) {
			programRatingMap.put("FSK", "FSK " + parentalGuidanceElement.getValue());
		}
		return programRatingMap;
	}

	private List<String> getProgramGenreList(String xmlInput, Program program) throws Exception {
		XPathExpression<Element> genrePath = XPathFactory.instance().compile("tva:BasicDescription/tva:Genre/tva:Name", Filters.element(), null, NAMESPACE_LIST);

		List<String> programGenreList = new ArrayList<>();

		Element programInformationElement = getProgramInformationElement(xmlInput, program.getUriId());
		List<Element> genreNameElementList = genrePath.evaluate(programInformationElement);
		for (Element genreNameElement: genreNameElementList) {
			String genreName = genreNameElement.getValue();
			String mappedGenre = GENRE_MAP.get(genreName);
			if (mappedGenre != null) {
				if (!programGenreList.contains(mappedGenre)) {
					programGenreList.add(mappedGenre);
				}
			} else {
				logger.debug("Unmapped genre: {}", genreName);
			}
		}
		return programGenreList;
	}

	protected List<SeriesIngestWrapper> findSeries(String xmlInput) throws Exception {
		XPathExpression<Element> seriesGroupInformationPath = XPathFactory.instance().compile("/tva:TVAMain/tva:ProgramDescription/tva:GroupInformationTable/tva:GroupInformation[tva:GroupType/@xsi:type='ProgramGroupTypeType' and tva:GroupType/@value='series']", Filters.element(), null, NAMESPACE_LIST);
		XPathExpression<Element> seriesTitlePath = XPathFactory.instance().compile("tva:BasicDescription/tva:Title", Filters.element(), null, NAMESPACE_LIST);
		XPathExpression<Element> seriesSynopsisPath = XPathFactory.instance().compile("tva:BasicDescription/tva:Synopsis", Filters.element(), null, NAMESPACE_LIST);

		List<SeriesIngestWrapper> ingestWrapperList = new ArrayList<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		List<Element> groupInformationElementList = seriesGroupInformationPath.evaluate(document);
		for (Element groupInformationElement: groupInformationElementList) {
			SeriesIngestWrapper ingestWrapper = new SeriesIngestWrapper();
			ingestWrapperList.add(ingestWrapper);
			String crId = groupInformationElement.getAttributeValue(ATTRIBUTE_GROUP_ID);
			ingestWrapper.getEntity().setUriId(crId);

			//Title
			List<Element> titleElementList = seriesTitlePath.evaluate(groupInformationElement);
			Locale titleBriefLocale = new Locale();
			ingestWrapper.getEntity().setTitleBrief(titleBriefLocale);
			Locale titleMediumLocale = new Locale();
			ingestWrapper.getEntity().setTitleMedium(titleMediumLocale);
			Locale titleLongLocale = new Locale();
			ingestWrapper.getEntity().setTitleLong(titleLongLocale);
			Locale titleSortNameLocale = new Locale();
			ingestWrapper.getEntity().setTitleSortName(titleSortNameLocale);
			for (Element titleElement: titleElementList) {
				String language = titleElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML, LocaleHelper.DEFAULT_LANGUAGE_NAME);
				String title = titleElement.getValue();
				LocaleHelper.setStringValueForLanguage(titleBriefLocale, language, title);
				LocaleHelper.setStringValueForLanguage(titleMediumLocale, language, title);
				LocaleHelper.setStringValueForLanguage(titleLongLocale, language, title);
				LocaleHelper.setStringValueForLanguage(titleSortNameLocale, language, title);
			}

			//Summary
			List<Element> synopsisElementList = seriesSynopsisPath.evaluate(groupInformationElement);
			Locale summaryLongLocale = new Locale();
			ingestWrapper.getEntity().setSummaryLong(summaryLongLocale);
			for (Element synopsisElement: synopsisElementList) {
				String language = synopsisElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML, LocaleHelper.DEFAULT_LANGUAGE_NAME);
				String synopsis = synopsisElement.getValue();
				LocaleHelper.setStringValueForLanguage(summaryLongLocale, language, synopsis);
			}

			//Program List
			Map<String, Object> variableMap = new HashMap<>();
			variableMap.put(VARIABLE_CR_ID, crId);
			XPathExpression<Element> programInformationPath = XPathFactory.instance().compile("/tva:TVAMain/tva:ProgramDescription/tva:ProgramInformationTable/tva:ProgramInformation[tva:MemberOf/@xsi:type='EpisodeOfType' and tva:MemberOf/@crid='$crId']", Filters.element(), variableMap, NAMESPACE_LIST);
			List<Element> programInformationElementList = programInformationPath.evaluate(document);
			List<String> programIdList = new ArrayList<>();
			for (Element programInformationElement: programInformationElementList) {
				String programId = programInformationElement.getAttributeValue(ATTRIBUTE_PROGRAM_ID);
				programIdList.add(programId);
			}
			ingestWrapper.addProgramUriIdList(programIdList);

		}
		//Programs requiring dummy series
		XPathExpression<Element> brandGroupInformationPath = XPathFactory.instance().compile("/tva:TVAMain/tva:ProgramDescription/tva:GroupInformationTable/tva:GroupInformation[tva:GroupType/@xsi:type='ProgramGroupTypeType' and tva:GroupType/@value='show']", Filters.element(), null, NAMESPACE_LIST);
		List<Element> brandGroupInformationElementList = brandGroupInformationPath.evaluate(document);
		for (Element brandGroupInformationElement: brandGroupInformationElementList) {
			Map<String, Object> programInformationPathVariableMap = new HashMap<>();
			programInformationPathVariableMap.put(VARIABLE_CR_ID, brandGroupInformationElement.getAttribute(ATTRIBUTE_GROUP_ID));
			XPathExpression<Element> programInformationInBrandPath = XPathFactory.instance().compile("/tva:TVAMain/tva:ProgramDescription/tva:ProgramInformationTable/tva:ProgramInformation[tva:MemberOf/@xsi:type='EpisodeOfType' and tva:MemberOf/@crid='$crId']", Filters.element(), programInformationPathVariableMap, NAMESPACE_LIST);
			XPathExpression<Element> programTitlePath = XPathFactory.instance().compile("tva:BasicDescription/tva:Title[@type='main']", Filters.element(), null, NAMESPACE_LIST);
			XPathExpression<Element> programSynopsisPath = XPathFactory.instance().compile("tva:BasicDescription/tva:Synopsis[@length='long']", Filters.element(), null, NAMESPACE_LIST);

			List<Element> programInformationInBrandElementList = programInformationInBrandPath.evaluate(document);
			for (Element programInformationInBrandElement: programInformationInBrandElementList) {
				SeriesIngestWrapper ingestWrapper = new SeriesIngestWrapper();
				ingestWrapperList.add(ingestWrapper);
				List<String> programIdList = new ArrayList<>();
				String programId = programInformationInBrandElement.getAttributeValue(ATTRIBUTE_PROGRAM_ID);
				programIdList.add(programId);
				ingestWrapper.addProgramUriIdList(programIdList);
				ingestWrapper.getEntity().setUriId(programId + "/SERIES");

				//Title
				List<Element> titleElementList = programTitlePath.evaluate(programInformationInBrandElement);
				Locale titleBriefLocale = new Locale();
				ingestWrapper.getEntity().setTitleBrief(titleBriefLocale);
				Locale titleMediumLocale = new Locale();
				ingestWrapper.getEntity().setTitleBrief(titleMediumLocale);
				Locale titleLongLocale = new Locale();
				ingestWrapper.getEntity().setTitleLong(titleLongLocale);
				Locale titleSortNameLocale = new Locale();
				ingestWrapper.getEntity().setTitleSortName(titleSortNameLocale);
				for (Element titleElement: titleElementList) {
					String language = titleElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML, LocaleHelper.DEFAULT_LANGUAGE_NAME);
					String title = titleElement.getValue();
					LocaleHelper.setStringValueForLanguage(titleBriefLocale, language, title);
					LocaleHelper.setStringValueForLanguage(titleMediumLocale, language, title);
					LocaleHelper.setStringValueForLanguage(titleLongLocale, language, title);
					LocaleHelper.setStringValueForLanguage(titleSortNameLocale, language, title);
				}

				//Summary
				List<Element> synopsisElementList = programSynopsisPath.evaluate(programInformationInBrandElement);
				Locale summaryLongLocale = new Locale();
				ingestWrapper.getEntity().setSummaryLong(summaryLongLocale);
				for (Element synopsisElement: synopsisElementList) {
					String language = synopsisElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML, LocaleHelper.DEFAULT_LANGUAGE_NAME);
					String synopsis = synopsisElement.getValue();
					LocaleHelper.setStringValueForLanguage(summaryLongLocale, language, synopsis);
				}
			}
		}
		return ingestWrapperList;
	}

	protected List<BrandIngestWrapper> findBrand(String xmlInput) throws Exception {
		XPathExpression<Element> brandGroupInformationPath = XPathFactory.instance().compile("/tva:TVAMain/tva:ProgramDescription/tva:GroupInformationTable/tva:GroupInformation[tva:GroupType/@xsi:type='ProgramGroupTypeType' and tva:GroupType/@value='show']", Filters.element(), null, NAMESPACE_LIST);
		XPathExpression<Element> brandTitlePath = XPathFactory.instance().compile("tva:BasicDescription/tva:Title", Filters.element(), null, NAMESPACE_LIST);
		XPathExpression<Element> brandSynopsisPath = XPathFactory.instance().compile("tva:BasicDescription/tva:Synopsis", Filters.element(), null, NAMESPACE_LIST);

		List<BrandIngestWrapper> ingestWrapperList = new ArrayList<>();

		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		List<Element> groupInformationElementList = brandGroupInformationPath.evaluate(document);
		for (Element groupInformationElement: groupInformationElementList) {
			BrandIngestWrapper ingestWrapper = new BrandIngestWrapper();
			ingestWrapperList.add(ingestWrapper);
			List<String> seriesUriIdList = new ArrayList<>();
			String crId = groupInformationElement.getAttributeValue(ATTRIBUTE_GROUP_ID);
			ingestWrapper.getEntity().setUriId(crId);

			//Title
			List<Element> titleElementList = brandTitlePath.evaluate(groupInformationElement);
			Locale titleBriefLocale = new Locale();
			ingestWrapper.getEntity().setTitleBrief(titleBriefLocale);
			Locale titleMediumLocale = new Locale();
			ingestWrapper.getEntity().setTitleBrief(titleMediumLocale);
			Locale titleLongLocale = new Locale();
			ingestWrapper.getEntity().setTitleLong(titleLongLocale);
			Locale titleSortNameLocale = new Locale();
			ingestWrapper.getEntity().setTitleSortName(titleSortNameLocale);
			for (Element titleElement: titleElementList) {
				String language = titleElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML, LocaleHelper.DEFAULT_LANGUAGE_NAME);
				String title = titleElement.getValue();
				LocaleHelper.setStringValueForLanguage(titleBriefLocale, language, title);
				LocaleHelper.setStringValueForLanguage(titleMediumLocale, language, title);
				LocaleHelper.setStringValueForLanguage(titleLongLocale, language, title);
				LocaleHelper.setStringValueForLanguage(titleSortNameLocale, language, title);
			}

			//Summary
			List<Element> synopsisElementList = brandSynopsisPath.evaluate(groupInformationElement);
			Locale summaryLongLocale = new Locale();
			ingestWrapper.getEntity().setSummaryLong(summaryLongLocale);
			for (Element synopsisElement: synopsisElementList) {
				String language = synopsisElement.getAttributeValue(ATTRIBUTE_LANG, NS_XML, LocaleHelper.DEFAULT_LANGUAGE_NAME);
				String synopsis = synopsisElement.getValue();
				LocaleHelper.setStringValueForLanguage(summaryLongLocale, language, synopsis);
			}

			//Program List
			Map<String, Object> programInformationPathVariableMap = new HashMap<>();
			programInformationPathVariableMap.put(VARIABLE_CR_ID, crId);
			XPathExpression<Element> programInformationPath = XPathFactory.instance().compile("/tva:TVAMain/tva:ProgramDescription/tva:ProgramInformationTable/tva:ProgramInformation[tva:MemberOf/@xsi:type='EpisodeOfType' and tva:MemberOf/@crid='$crId']", Filters.element(), programInformationPathVariableMap, NAMESPACE_LIST);

			List<Element> programInformationElementList = programInformationPath.evaluate(document);
			for (Element programInformationElement: programInformationElementList) {
				String programId = programInformationElement.getAttributeValue(ATTRIBUTE_PROGRAM_ID);
				// We will construct a dummy series to point at.
				seriesUriIdList.add(programId + "/SERIES");
			}

			//Series List
			Map<String, Object> seriesGroupInformationVariableMap = new HashMap<>();
			seriesGroupInformationVariableMap.put(VARIABLE_CR_ID, crId);
			XPathExpression<Element> seriesGroupInformationPath = XPathFactory.instance().compile("/tva:TVAMain/tva:ProgramDescription/tva:GroupInformationTable/tva:GroupInformation[tva:MemberOf/@xsi:type='MemberOfType' and tva:MemberOf/@crid='$crId']", Filters.element(), seriesGroupInformationVariableMap, NAMESPACE_LIST);

			List<Element> seriesGroupInformationElementList = seriesGroupInformationPath.evaluate(document);
			for (Element seriesGroupInformationElement: seriesGroupInformationElementList) {
				String seriesGroupId = seriesGroupInformationElement.getAttributeValue(ATTRIBUTE_GROUP_ID);
				seriesUriIdList.add(seriesGroupId);
			}
			ingestWrapper.addSeriesUriIdList(seriesUriIdList);
		}

		return ingestWrapperList;
	}

	protected List<OfferIngestWrapper> findOffers(String xmlInput) throws Exception {
		//TODO Map offers from TVAnytime.
		XPathExpression<Element> programInformationPath = XPathFactory.instance().compile("/tva:TVAMain/tva:ProgramDescription/tva:ProgramInformationTable/tva:ProgramInformation", Filters.element(), null, NAMESPACE_LIST);

		List<OfferIngestWrapper> ingestWrapperList = new ArrayList<>();

		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		List<Element> programInformationElementList = programInformationPath.evaluate(document);

		for (Element programInformationElement: programInformationElementList) {
			OfferIngestWrapper ingestWrapper = new OfferIngestWrapper();
			List<String> termUriIdList = new ArrayList<>();
			termUriIdList.add(TERM_URIID);
			ingestWrapper.addTermUriIdList(termUriIdList);

			List<String> programUriIdList = new ArrayList<>();
			String programId = programInformationElement.getAttributeValue(ATTRIBUTE_PROGRAM_ID);
			programUriIdList.add(programId);
			ingestWrapper.addProgramUriIdList(programUriIdList);

			Offer offer = new Offer();
			offer.setUriId(programId + "/OFFER");
			offer.setMetadataApproved(true);
			offer.setStartDateTime(WorkflowHelper.START_OF_TIME);
			offer.setEndDateTime(WorkflowHelper.END_OF_TIME);
			offer.setBillingId(OFFER_BILLING_ID);
			ingestWrapper.setEntity(offer);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

			ingestWrapperList.add(ingestWrapper);
		}
		return ingestWrapperList;
	}

	protected List<TermIngestWrapper> findTerms(String xmlInput) throws Exception {
		//TODO Map terms from TVAnytime.
		List<TermIngestWrapper> termWrapperList = new ArrayList<>();
		TermIngestWrapper ingestWrapper = new TermIngestWrapper();
		Term term = new Term();
		ingestWrapper.setEntity(term);
		ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
		termWrapperList.add(ingestWrapper);
		term.setUriId(TERM_URIID);
		term.setMetadataApproved(true);
		term.setContractName(TERM_CONTRACT_NAME);
		term.setStartDateTime(WorkflowHelper.START_OF_TIME);
		term.setEndDateTime(WorkflowHelper.END_OF_TIME);
		Map<String, Map<String, String>> suggestedPriceMap = new HashMap<>();
		Map<String, String> usPriceMap = new HashMap<>();
		usPriceMap.put(TERM_CURRENCY, TERM_PRICE);
		//suggestedPriceMap.put(TERM_LOCALE, usPriceMap);
		term.setSuggestedPrice(suggestedPriceMap);
		return termWrapperList;
	}

	private Element getProgramInformationElement(String xmlInput, String crId) throws Exception {
		Map<String, Object> variableMap = new HashMap<>();
		variableMap.put(VARIABLE_CR_ID, crId);
		XPathExpression<Element> programInformationPath = XPathFactory.instance().compile("/tva:TVAMain/tva:ProgramDescription/tva:ProgramInformationTable/tva:ProgramInformation[@programId='$crId']", Filters.element(), variableMap, NAMESPACE_LIST);

		// Note that URI ID is actually CRID in TV Anytime
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		List<Element> programInformationElementList = programInformationPath.evaluate(document);
		for (Element programInformationElement: programInformationElementList) {
			return programInformationElement;
		}
		throw new Exception("XML does not contain CRID " + crId + ".");
	}

	private List<Person> getPersonList(Object context, String role, Person.Contribution contribution) {
		List<Person> personList = new ArrayList<>();
		Map<String, Object> variableMap = new HashMap<>();
		variableMap.put(VARIABLE_ROLE, role);
		XPathExpression<Element> peoplePath = XPathFactory.instance().compile("tva:BasicDescription/tva:CreditsItem[@role='$role']/tva:PersonName", Filters.element(), variableMap, NAMESPACE_LIST);
		List<Element> peopleElementList = peoplePath.evaluate(context);
		Locale personSortableName = new Locale();
		Locale personFullName = new Locale();
		Locale personFirstName = new Locale();
		Locale personLastName = new Locale();

		for (Element peopleElement: peopleElementList) {
			String salutation = peopleElement.getChildText(ELEMENT_SALUTATION, NS_MPEG7);
			String givenName = peopleElement.getChildText(ELEMENT_GIVEN_NAME, NS_MPEG7);
			String familyName = peopleElement.getChildText(ELEMENT_FAMILY_NAME, NS_MPEG7);
			String fullName = salutation + " " + givenName + " " + familyName;
			String sortableName = familyName + "," + givenName;
			Person person = new Person();
			person.setContribution(contribution);
			LocaleHelper.setStringValueForDefaultLanguage(personFirstName, givenName);
			person.setFirstName(personFirstName);
			LocaleHelper.setStringValueForDefaultLanguage(personFullName, fullName);
			person.setFullName(personFullName);
			LocaleHelper.setStringValueForDefaultLanguage(personLastName, familyName);
			person.setLastName(personLastName);
			LocaleHelper.setStringValueForDefaultLanguage(personSortableName, sortableName);
			person.setSortableName(personSortableName);
			personList.add(person);
		}
		return personList;
	}
}
