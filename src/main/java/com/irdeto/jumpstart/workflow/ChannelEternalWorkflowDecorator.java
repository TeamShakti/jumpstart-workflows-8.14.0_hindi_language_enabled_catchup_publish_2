package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.qa.ChannelContentQA;
import com.irdeto.jumpstart.domain.qa.ChannelMetadataQA;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.relationship.Relationship;


public class ChannelEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<Channel> {

	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return new ChannelContentQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return new ChannelMetadataQA(getEntityId(), getEntityType());
	}

	@Override
	public List<Relationship<?>> getRelationshipList() {
		return new ArrayList<>();
	}

	@Override
	@JsonIgnore
	public Class<Channel> getEntityClass() {
		return Channel.class;
	}
}
