package com.irdeto.jumpstart.domain.livedrm;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;


public abstract class AbstractLiveDrmSoapBody {
    @JsonIgnore
    @XmlTransient
    public abstract String getCommand();
}
