package com.irdeto.jumpstart.workflow.publish.control;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.control.ControlBatch;
import com.irdeto.jumpstart.domain.control.qsa.ControlQSAAuthorizationInfo;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.factory.AbstractInstance;

public interface ControlMapper<T extends Base> extends AbstractInstance<T> {
	public void setPublishWrapper(PublishWrapper<T> publishWrapper);
	
	@JsonIgnore
	public ControlBatch getControlBatch();
	
	@JsonIgnore
	public List<SoapEnvelope> getCategorySoapEnvelopeList(List<ControlQSAAuthorizationInfo> authorizationInfoList);

	@JsonIgnore
	public List<String> getMediaIdList();
}
