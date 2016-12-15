package com.irdeto.jumpstart.workflow.publish.control;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.control.ControlMedia;


public abstract class CategoryControlMapper<T extends Base> extends AbstractControlMapper<T> {
	@JsonIgnore
	@Override
	protected List<ControlMedia> getMediaList() {
		return getDirectlyOfferedMediaList();
	}
	
	@JsonIgnore
	@Override
	public List<String> getMediaIdList() {
		return new ArrayList<>();
	}
}
