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
import com.irdeto.jumpstart.domain.PolicyProfile;
import com.irdeto.jumpstart.workflow.DateHelper;

/**
 * File Name: GetPolicyGroupByIDCwsResponseProcessor.java
 * 
 * Description: The response processor for  class for GetPolicyGroupByID
 * 
 * Developed by Tata Elxsi for Irdeto B.V.
 * 
 * Creation Date: 30-Jan-2015
 *
 */
public class GetPolicyGroupByIDCwsResponseProcessor extends AbstractCwsResponseProcessor {

	private static final String ELEMENT_ID = "Id";
	private static final String ELEMENT_NAME = "Name";
	private static final String ELEMENT_DESCRIPTION = "Description";
	private static final String ELEMENT_POLICY_ID = "Id";
	private static final String ELEMENT_POLICY_NAME = "Name";
	private static final String ELEMENT_POLICY_DESCRIPTION = "Description";
	private static final String ELEMENT_PRICE = "Price";
	private static final String ELEMENT_PRICE_TABLE = "PriceTable";
	private static final String ELEMENT_SUBSCRIPTION_DETAILS = "SubscriptionDetails";
	private static final String ATTRIBUTE_TYPE = "Type";
	private static final String ATTRIBUTE_ENTSTARTMODE = "EntStartMode";
	private static final String ATTRIBUTE_ENT_DURATION = "EntDuration";
	private static final String ATTRIBUTE_REQ_SUBSCRIPTION = "ReqSubscription";
	private static final String ATTRIBUTE_SUBSCRIPTION_REF = "SubscriptionRef";
	private static final String ATTRIBUTE_LICENSE_PROFILE_REF = "LicenseProfileRef";
	private static final String ATTRIBUTE_REQ_AUTHENTICATION = "ReqAuthentication";
	private static final String ATTRIBUTE_COUNTRY = "Country";
	private static final String ATTRIBUTE_CURRENCY = "Currency";
	private static final String ATTRIBUTE_AMOUNT = "Amount";
	private static final String ATTRIBUTE_NO_DEVICES = "NoDevices";
	private static final String ATTRIBUTE_PRODUCT_TAX_TYPE = "ProductTaxType";
	private static final String ATTRIBUTE_BILL_IMMEDIATELY = "BillImmediately";
	private static final String ATTRIBUTE_SYNDICATE_ACC_ID = "SyndicateAccountId";
	private static final String ATTRIBUTE_SUBSCRIPTION_BILLING_INTERVAL = "BillingInterval";
	private static final String ATTRIBUTE_SUBSCRIPTION_GROUP_ID = "SubscriptionGroupId";
	private static final String ATTRIBUTE_SUBSCRIPTION_ID = "Id";
	private static final String ATTRIBUTE_SUBSCRIPTION_MIN_BILLING_INTERVAL = "MinBillingInterval";
	private static final String ATTRIBUTE_SUBSCRIPTION_RELEASE_END = "ReleaseEnd";
	private static final String ATTRIBUTE_SUBSCRIPTION_RELEASE_DATE = "ReleaseDate";
	private static final String ATTRIBUTE_SUBSCRIPTION_BILLING_END_DATE = "BillingEndDate";
	public static final String POLICY_GROUP_LIST_KEY = "PolicyGroup";
	public static final String POLICY_LIST_KEY = "Policy";
	private static final String PRICE_LIST_KEY = "Price";
	
	@Override
	public Map<String, Object> processResponse(String xmlResponse)
			throws Exception {
		Map<String, Object> results = new HashMap<>();
		List<PolicyGroupProfile> policyGroupList = new ArrayList<>();
		List<PolicyProfile> policyList = new ArrayList<>();
		List<Map<String, Map<String, String>>> priceList = new ArrayList<>();

		Document document = JDOMDocumentHelper.buildDocument(xmlResponse);
		XPathFactory xPathFactory = XPathFactory.instance();
		Filter<Element> filter = Filters.element();
		XPathExpression<Element> expression = xPathFactory.compile("//a:PolicyGroup", filter, null, NAMESPACE_LIST);
		List<Element> policyGroupElementList = expression.evaluate(document);
		for (Element policyGroupElement: policyGroupElementList) {
			PolicyGroupProfile policyGroup = new PolicyGroupProfile();
			String id = policyGroupElement.getChildText(ELEMENT_ID, NAMESPACE_CONSOLE_SERVICE);
			String policyGroupName = policyGroupElement.getChildText(ELEMENT_NAME, NAMESPACE_CONSOLE_SERVICE);
			String policyGroupDesc = policyGroupElement.getChildText(ELEMENT_DESCRIPTION, NAMESPACE_CONSOLE_SERVICE);
			String licenseProfileReference = policyGroupElement.getChildText(ATTRIBUTE_LICENSE_PROFILE_REF, NAMESPACE_CONSOLE_SERVICE);
			
			policyGroup.setId(id);
			policyGroup.setPolicyGroupName(policyGroupName);
			policyGroup.setPolicyGroupDescription(policyGroupDesc);
			if(null != licenseProfileReference)
			{
				policyGroup.setLicenseProfileReference(Integer.parseInt(licenseProfileReference));
			}
		
			policyGroupList.add(policyGroup);
		
			XPathExpression<Element> expressionPolicy = xPathFactory.compile("//b:Policy", filter, null, NAMESPACE_LIST);
			List<Element> policyElementList = expressionPolicy.evaluate(document);
			
			for (Element policyElement: policyElementList) {
				PolicyProfile policy = new PolicyProfile();
				String policyId = policyElement.getChildText(ELEMENT_POLICY_ID, NAMESPACE_BUSSINESS_OBJECT);
				String policyName = policyElement.getChildText(ELEMENT_POLICY_NAME, NAMESPACE_BUSSINESS_OBJECT);
				String policyDesc = policyElement.getChildText(ELEMENT_POLICY_DESCRIPTION, NAMESPACE_BUSSINESS_OBJECT);
				String policyType = policyElement.getChildText(ATTRIBUTE_TYPE, NAMESPACE_BUSSINESS_OBJECT);
				String entStartMode = policyElement.getChildText(ATTRIBUTE_ENTSTARTMODE, NAMESPACE_BUSSINESS_OBJECT);
				String entDuration = policyElement.getChildText(ATTRIBUTE_ENT_DURATION, NAMESPACE_BUSSINESS_OBJECT);
				String reqSubscription = policyElement.getChildText(ATTRIBUTE_REQ_SUBSCRIPTION, NAMESPACE_BUSSINESS_OBJECT);
				String subscriptionRef = policyElement.getChildText(ATTRIBUTE_SUBSCRIPTION_REF, NAMESPACE_BUSSINESS_OBJECT);
				String licenseProfileRef = policyElement.getChildText(ATTRIBUTE_LICENSE_PROFILE_REF, NAMESPACE_BUSSINESS_OBJECT);
				String reqAunthentication = policyElement.getChildText(ATTRIBUTE_REQ_AUTHENTICATION, NAMESPACE_BUSSINESS_OBJECT);
				
				policy.setId(policyId);
				policy.setPolicyName(policyName);
				policy.setPolicyDescription(policyDesc);
				policy.setType(policyType);
				policy.setEntitlementStartMode(entStartMode);
				policy.setEntitlementDuration(entDuration);
				policy.setRequireSubscription(Boolean.valueOf(reqSubscription));
				policy.setSubscriptionReference(Integer.parseInt(subscriptionRef));
				policy.setLicenseProfileReference(Integer.parseInt(licenseProfileRef));
				policy.setRequireAuthentication(Boolean.valueOf(reqAunthentication));
				
				List<Element> priceTableElementList = policyElement.getChildren(ELEMENT_PRICE_TABLE, NAMESPACE_BUSSINESS_OBJECT); 
				for(Element priceTableElement: priceTableElementList){
				List<Element> priceElementList = priceTableElement.getChildren(ELEMENT_PRICE, NAMESPACE_BUSSINESS_OBJECT); 
				
				Map<String, Map<String, String>> countryCurrencyMap = new HashMap<>();
				for (Element priceElement: priceElementList) {
					String country = priceElement.getChildText(ATTRIBUTE_COUNTRY, NAMESPACE_BUSSINESS_OBJECT);
					String currency = priceElement.getChildText(ATTRIBUTE_CURRENCY, NAMESPACE_BUSSINESS_OBJECT);
					String amount = priceElement.getChildText(ATTRIBUTE_AMOUNT, NAMESPACE_BUSSINESS_OBJECT);
					Map<String,String> currencyMap = countryCurrencyMap.get(country);
					if(currencyMap == null ){
						currencyMap = new HashMap<>();
						countryCurrencyMap.put(country, currencyMap);
					}
					currencyMap.put(currency, amount);
					priceList.add(countryCurrencyMap);
					}
				}
					String noDevices = policyElement.getChildText(ATTRIBUTE_NO_DEVICES, NAMESPACE_BUSSINESS_OBJECT);
					String productTaxType = policyElement.getChildText(ATTRIBUTE_PRODUCT_TAX_TYPE, NAMESPACE_BUSSINESS_OBJECT);
					String billImmd = policyElement.getChildText(ATTRIBUTE_BILL_IMMEDIATELY, NAMESPACE_BUSSINESS_OBJECT);
					String syndicateAccId = policyElement.getChildText(ATTRIBUTE_SYNDICATE_ACC_ID, NAMESPACE_BUSSINESS_OBJECT);
					
					policy.setNumberOfDevices(Integer.valueOf(noDevices));
					policy.setProductTaxType(productTaxType);
					policy.setSyndicateAccountId(syndicateAccId);
					policy.setBillImmediately(Boolean.valueOf(billImmd));
					policyList.add(policy);
					for(Element subscriptionDetailsElement:
							policyElement.getChildren(ELEMENT_SUBSCRIPTION_DETAILS, NAMESPACE_BUSSINESS_OBJECT)) {
						String subBillingInterval = subscriptionDetailsElement.getChildText(ATTRIBUTE_SUBSCRIPTION_BILLING_INTERVAL, NAMESPACE_BUSSINESS_OBJECT);
						String subscriptionGroupId = subscriptionDetailsElement.getChildText(ATTRIBUTE_SUBSCRIPTION_GROUP_ID, NAMESPACE_BUSSINESS_OBJECT);
						String subscriptionId = subscriptionDetailsElement.getChildText(ATTRIBUTE_SUBSCRIPTION_ID, NAMESPACE_BUSSINESS_OBJECT);
						String subscriptionReleaseDate = subscriptionDetailsElement.getChildText(ATTRIBUTE_SUBSCRIPTION_RELEASE_DATE, NAMESPACE_BUSSINESS_OBJECT);
						String subscriptionReleaseEnd = subscriptionDetailsElement.getChildText(ATTRIBUTE_SUBSCRIPTION_RELEASE_END, NAMESPACE_BUSSINESS_OBJECT);
						String subMinBillingInterval = subscriptionDetailsElement.getChildText(ATTRIBUTE_SUBSCRIPTION_MIN_BILLING_INTERVAL, NAMESPACE_BUSSINESS_OBJECT);
						String subBillingEndDate = subscriptionDetailsElement.getChildText(ATTRIBUTE_SUBSCRIPTION_BILLING_END_DATE, NAMESPACE_BUSSINESS_OBJECT);
					
						policy.setSubscriptionBillingEndDate(DateHelper.convertXMLTVDate(subBillingEndDate));
						policy.setSubscriptionBillingInterval(Integer.parseInt(subBillingInterval));
						if(!subscriptionGroupId.isEmpty())
						{
						policy.setSubscriptionGroupId(Integer.parseInt(subscriptionGroupId));
						}
						policy.setSubscriptionId(Integer.parseInt(subscriptionId));
						policy.setSubscriptionMinimumBillingInterval(Integer.parseInt(subMinBillingInterval));
						policy.setSubscriptionReleaseDate(DateHelper.convertXMLTVDate(subscriptionReleaseDate));
						policy.setSubscriptionReleaseEnd(DateHelper.convertXMLTVDate(subscriptionReleaseEnd));
						
						policyList.add(policy);
					}
				
			}
		}
		
		results.put(POLICY_GROUP_LIST_KEY, policyGroupList);
		results.put(POLICY_LIST_KEY, policyList);
		results.put(PRICE_LIST_KEY, priceList);
		return results;
	}
}
