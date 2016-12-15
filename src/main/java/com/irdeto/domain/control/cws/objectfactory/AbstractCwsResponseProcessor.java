package com.irdeto.domain.control.cws.objectfactory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filter;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;


public abstract class AbstractCwsResponseProcessor implements CwsResponseProcessor {
	protected static final Namespace NAMESPACE_SOAP_ENVELOPE = Namespace.getNamespace("s", "http://schemas.xmlsoap.org/soap/envelope/");
	protected static final Namespace NAMESPACE_CONSOLE_SERVICE = Namespace.getNamespace("a", "http://schemas.datacontract.org/2004/07/ConsoleService");
	protected static final Namespace NAMESPACE_BUSSINESS_OBJECT = Namespace.getNamespace("b","http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject");
	protected static final Namespace NAMESPACE_XSD = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
	protected static final Namespace NAMESPACE_SCHEMA_INSTANCE = Namespace.getNamespace("i", "http://www.w3.org/2001/XMLSchema-instance");
	protected static final List<Namespace> NAMESPACE_LIST = new ArrayList<>();
	static {
		NAMESPACE_LIST.add(NAMESPACE_SOAP_ENVELOPE);
		NAMESPACE_LIST.add(NAMESPACE_CONSOLE_SERVICE);
		NAMESPACE_LIST.add(NAMESPACE_BUSSINESS_OBJECT);
		NAMESPACE_LIST.add(NAMESPACE_SCHEMA_INSTANCE);
		NAMESPACE_LIST.add(NAMESPACE_XSD);
	}
	
	private String allowSerialization;

	@JsonIgnore
	protected <T> String getValue(Document document, String query, Class<T> xmlClass) throws Exception {
		return getValueList(document, query, xmlClass).get(0);
	}

	@SuppressWarnings("unchecked")
	@JsonIgnore
	protected <T> List<String> getValueList(Document document, String query, Class<T> xmlClass) throws Exception {
		List<String> valueList = new ArrayList<>();
		
		XPathFactory xPathFactory = XPathFactory.instance();
		Filter<T> filter = null;
		if (Element.class.equals(xmlClass)) {
			filter = (Filter<T>)Filters.element();
		} else if (Attribute.class.equals(xmlClass)) {
			filter = (Filter<T>)Filters.attribute();
		} else {
			throw new Exception("Unsupported filter type: " + xmlClass.getSimpleName());
		}
		XPathExpression<T> expression = xPathFactory.compile(query, filter, null, NAMESPACE_LIST);
		List<T> list = expression.evaluate(document);
		if (list == null || list.isEmpty()) {
			throw new Exception("No " + xmlClass.getSimpleName() + " found for query: " + query);
		}
		for (T item: list) {
			String value = null;
			if (item instanceof Element) {
				value = ((Element)item).getValue();
			} else if (item instanceof Attribute) {
				value = ((Attribute)item).getValue();
			} else {
				throw new Exception("Unsupported xml artifact: " + xmlClass.getSimpleName());
			}
			if (StringUtils.isNotBlank(value)) {
				valueList.add(value);
			}
		}
		
		if (valueList.isEmpty()) {
			throw new Exception("No values found.");
		}
		return valueList;
	}

	public String getAllowSerialization() {
		return allowSerialization;
	}

	public void setAllowSerialization(String allowSerialization) {
		this.allowSerialization = allowSerialization;
	}

}
