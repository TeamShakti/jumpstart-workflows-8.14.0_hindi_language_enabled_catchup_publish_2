package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class SoapBody {
	private Object bodyElement;

	public SoapBody() {
	}

	public SoapBody(Object bodyElement) {
		super();
		this.bodyElement = bodyElement;
	}

	@XmlElements({
        @XmlElement(name = "UpsertP7Category", type = UpsertP7Category.class, namespace = "http://ws.entriq.net/services/console"),
        @XmlElement(name = "UpsertP7MediaCategory", type = UpsertP7MediaCategory.class, namespace = "http://ws.entriq.net/services/console"),
        @XmlElement(name = "DeleteMedia", type = DeleteMedia.class, namespace = "http://ws.entriq.net/services/console"),
        @XmlElement(name = "GetPolicyGroupList", type = GetPolicyGroupList.class, namespace = "http://ws.entriq.net/services/console"),
        @XmlElement(name = "GetPolicyGroupByID", type = GetPolicyGroupByID.class, namespace = "http://ws.entriq.net/services/console"),
        @XmlElement(name = "AddToPolicyGroup", type = AddToPolicyGroup.class, namespace = "http://ws.entriq.net/services/console"),
        @XmlElement(name = "CreatePolicy", type = CreatePolicy.class, namespace = "http://ws.entriq.net/services/console")
    })
    public Object getBodyElement() {
		return bodyElement;
	}

	public void setBodyElement(Object bodyElement) {
		this.bodyElement = bodyElement;
	}

}
