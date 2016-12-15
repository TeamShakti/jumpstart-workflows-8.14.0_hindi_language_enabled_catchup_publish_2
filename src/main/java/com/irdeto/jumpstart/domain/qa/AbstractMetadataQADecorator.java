package com.irdeto.jumpstart.domain.qa;

import org.apache.commons.lang3.BooleanUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.BaseMetadata;

/**
 * File Name: AbstractMetadataQADecorator.java
 * 
 * Description: The QA Decorator abstract class Entities with Content
 * 
 * Developed by Tata Elxsi for Irdeto B.V.
 * 
 * Creation Date: 18-Nov-2014
 *
 */

public abstract class AbstractMetadataQADecorator<T extends BaseMetadata> extends AbstractQADecorator<T>{

	@Override
	@JsonIgnore
	public boolean isQARequired() {
		return BooleanUtils.isNotTrue(getEntity().getMetadataApproved());
	}
}
