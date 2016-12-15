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

import com.irdeto.helper.xmlmodel.JDOMDocumentHelper;
import com.irdeto.jumpstart.domain.PolicyGroupProfile;

public class CreatePolicyGroupCwsResponseProcessor extends AbstractCwsResponseProcessor {

    private static final String ELEMENT_ID = "Id";
    private static final String ELEMENT_NAME = "Name";
    private static final String ELEMENT_DESCRIPTION = "Description";
    private static final String POLICY_GROUP_LIST_KEY = "PolicyGroup";

@Override
	public Map<String, Object> processResponse(String xmlResponse)
			throws Exception {
	
		Map<String, Object> results = new HashMap<>();
		List<PolicyGroupProfile> policyGroupList = new ArrayList<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlResponse);
		XPathFactory xPathFactory = XPathFactory.instance();
		Filter<Element> filter = (Filter<Element>)Filters.element();
		XPathExpression<Element> expression = xPathFactory.compile("//a:PolicyGroup", filter, null, NAMESPACE_LIST);
        List<Element> policyGroupElementList = expression.evaluate(document);
		for (Element policyGroupElement: policyGroupElementList) {
            PolicyGroupProfile policyGroup = new PolicyGroupProfile();
            String id = policyGroupElement.getChildText(ELEMENT_ID, NAMESPACE_CONSOLE_SERVICE);
            String name = policyGroupElement.getChildText(ELEMENT_NAME, NAMESPACE_CONSOLE_SERVICE);
            String description = policyGroupElement.getChildText(ELEMENT_DESCRIPTION, NAMESPACE_CONSOLE_SERVICE);
			
			policyGroup.setId(id);
            policyGroup.setPolicyGroupName(name);
            policyGroup.setPolicyGroupDescription(description);
            policyGroupList.add(policyGroup);
		}	
		
		results.put(POLICY_GROUP_LIST_KEY, policyGroupList);
		return results;
	}	

}