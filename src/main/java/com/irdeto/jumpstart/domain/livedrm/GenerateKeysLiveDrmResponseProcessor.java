package com.irdeto.jumpstart.domain.livedrm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import com.irdeto.helper.xmlmodel.JDOMDocumentHelper;

public class GenerateKeysLiveDrmResponseProcessor extends AbstractLiveDrmResponseProcessor {
	private static final String DRM_VALUES_KEY = "DrmValues";

	@Override
	public Map<String, Object> processResponse(String xmlResponse) throws Exception {
		Map<String, Object> results = new HashMap<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlResponse);
		String generatePlayreadyKeysResult = getValue(document, "//a:GenerateKeysResult", Element.class);
		StringEscapeUtils.unescapeXml(generatePlayreadyKeysResult);
		Document keyResultDocument = JDOMDocumentHelper.buildDocument(generatePlayreadyKeysResult);
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> expression = xPathFactory.compile("//Key", Filters.element(), null, NAMESPACE_LIST);
		List<Element> keyElementList = expression.evaluate(keyResultDocument);
		List<Map<String, String>> drmValues = new ArrayList<>();
		for (Element keyElement: keyElementList) {
			Map<String, String> keyValues = new HashMap<>();
			drmValues.add(keyValues);
			for (Attribute keyAttribute: keyElement.getAttributes()) {
				keyValues.put(keyAttribute.getName(), keyAttribute.getValue());
			}
		}
		results.put(DRM_VALUES_KEY, drmValues);
		return results;
	}
}
