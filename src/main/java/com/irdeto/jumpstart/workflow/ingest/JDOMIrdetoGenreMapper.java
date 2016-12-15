package com.irdeto.jumpstart.workflow.ingest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import com.irdeto.helper.xmlmodel.JDOMDocumentHelper;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.Locale;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.GenreIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SourceInformation;
import com.irdeto.jumpstart.workflow.LocaleHelper;

public class JDOMIrdetoGenreMapper extends AbstractVodIngestMapper implements VodIngestMapper {
	private static final String ATTRIBUTE_IS_DISPLAY = "isDisplay";
	private static final String ATTRIBUTE_IS_ENABLED = "isEnabled";
	private static final String ATTRIBUTE_ID = "id";
	private static final String ATTRIBUTE_LANG = "lang";

	private static final String ELEMENT_GENRE = "Genre";
	private static final String ELEMENT_NAME = "Name";
	private static final String ELEMENT_INGEST_GENRE = "IngestGenre";

	@Override
	@JsonIgnore
	public boolean isApplicableMapper() {
		return StringUtils.contains(getInputString(), "<Genres>");
	}

	@Override
	@JsonIgnore
	public String[] getSchemaFileNames() {
		return new String[] {"genre.xsd"};
	}

	@Override
	@JsonIgnore
	public boolean isSchemaValidationEnabled() {
		return true;
	}

	@Override
	public List<EntityIngestWrapper<?>> findEntities() throws Exception {
		List<EntityIngestWrapper<?>> ingestWrapperList = new ArrayList<>();
		ingestWrapperList.addAll(findGenres(getInputString()));
		return ingestWrapperList;
	}

	@JsonIgnore
	private Locale getLocaleValues(List<Element> elementList) {
		Locale locale = new Locale();
		for (Element element: elementList) {
			String language = element.getAttributeValue(ATTRIBUTE_LANG, LocaleHelper.DEFAULT_LANGUAGE_NAME);
			String value = element.getValue();
			LocaleHelper.setStringValueForLanguage(locale, language, value);
		}
		return locale;
	}

	protected List<GenreIngestWrapper> findGenres(String xmlInput)
			throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> contentGroupPath = xPathFactory.compile("/Genres/Genre", Filters.element(), null);
		List<Element> genreElementList = contentGroupPath.evaluate(document);
		return getGenreIngestWrapperList(genreElementList, null);
	}

	private List<GenreIngestWrapper> getGenreIngestWrapperList(List<Element> genreElementList, Genre parentGenre) {
		List<GenreIngestWrapper> genreIngestWrapperList = new ArrayList<>();
		for (Element genreElement: genreElementList) {
			Genre genre = getMappedGenre(genreElement);
			GenreIngestWrapper ingestWrapper = new GenreIngestWrapper();
			ingestWrapper.setEntity(genre);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
			if (parentGenre != null) {
				List<String> parentGenreUriIdList = new ArrayList<>();
				parentGenreUriIdList.add(parentGenre.getUriId());
				ingestWrapper.addParentGenreUriIdList(parentGenreUriIdList);
			}
			genreIngestWrapperList.add(ingestWrapper);
			List<Element> subgenreElementList = genreElement.getChildren(ELEMENT_GENRE);
			genreIngestWrapperList.addAll(getGenreIngestWrapperList(subgenreElementList, genre));
		}
		return genreIngestWrapperList;
	}

	private Genre getMappedGenre(Element genreElement) {
		Genre genre = new Genre();
		String isDisplay = genreElement.getAttributeValue(ATTRIBUTE_IS_DISPLAY);
		genre.setIsDisplayGenre(BooleanUtils.toBooleanObject(isDisplay));
		// code by nitin
		String isEnabled = genreElement.getAttributeValue(ATTRIBUTE_IS_ENABLED);
		genre.setIsEnabled(BooleanUtils.toBooleanObject(isEnabled));
		String ID = genreElement.getAttributeValue(ATTRIBUTE_ID);
		genre.setUriId(ID);
		List<Element> nameElementList = genreElement.getChildren(ELEMENT_NAME);
		Locale titleLocale = getLocaleValues(nameElementList);
		String ingestGenre = genreElement.getChildText(ELEMENT_INGEST_GENRE);
		genre.setTitle(titleLocale);
		genre.setUriId(LocaleHelper.getStringValueForDefaultLanguage(titleLocale));
		if(ingestGenre == null || ingestGenre.isEmpty()){
			genre.setIngestGenre(LocaleHelper.getStringValueForDefaultLanguage(titleLocale));
		} else {
			genre.setIngestGenre(ingestGenre);
		}
		return genre;
	}
}
