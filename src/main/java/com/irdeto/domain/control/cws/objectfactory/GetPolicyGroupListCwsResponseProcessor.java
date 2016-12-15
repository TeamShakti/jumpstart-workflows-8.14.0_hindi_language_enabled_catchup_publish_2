package com.irdeto.domain.control.cws.objectfactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filter;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import com.irdeto.domain.control.cws.PolicyGroupLite;
import com.irdeto.helper.xmlmodel.JDOMDocumentHelper;

public class GetPolicyGroupListCwsResponseProcessor extends AbstractCwsResponseProcessor {
	private static final String ELEMENT_ID = "Id";
	private static final String ELEMENT_NAME = "Name";
	private static final String ELEMENT_DESCRIPTION = "Description";
	public static final String POLICY_GROUP_LITE_LIST_KEY = "PolicyGroupLite";
	
	@Override
	public Map<String, Object> processResponse(String xmlResponse)
			throws Exception {
		Map<String, Object> results = new HashMap<>();
		List<PolicyGroupLite> policyGroupLiteList = new ArrayList<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlResponse);
		XPathFactory xPathFactory = XPathFactory.instance();
		Filter<Element> filter = (Filter<Element>)Filters.element();
		XPathExpression<Element> expression = xPathFactory.compile("//a:PolicyGroupLite", filter, null, NAMESPACE_LIST);
		List<Element> policyGroupLiteElementList = expression.evaluate(document);
		for (Element policyGroupLiteElement: policyGroupLiteElementList) {
			PolicyGroupLite policyGroupLite = new PolicyGroupLite();
			String id = policyGroupLiteElement.getChildText(ELEMENT_ID, NAMESPACE_CONSOLE_SERVICE);
			String name = policyGroupLiteElement.getChildText(ELEMENT_NAME, NAMESPACE_CONSOLE_SERVICE);
			String description = policyGroupLiteElement.getChildText(ELEMENT_DESCRIPTION, NAMESPACE_CONSOLE_SERVICE);
			policyGroupLite.setId(id);
			policyGroupLite.setName(name);
			policyGroupLite.setDescription(description);
			policyGroupLiteList.add(policyGroupLite);
		}
		results.put(POLICY_GROUP_LITE_LIST_KEY, policyGroupLiteList);
		return results;
	}

}
