package com.irdeto.jumpstart.domain.qa;

import com.irdeto.jumpstart.domain.BaseMetadata;

public abstract class AbstractBaseMetadataQADecorator<T extends BaseMetadata> extends AbstractMetadataQADecorator<T> {
	protected String applyMetadataRules() {
		StringBuffer messages = new StringBuffer();
		if (getEntity().getStartDateTime() != null && getEntity().getEndDateTime() != null) {
			if (getEntity().getStartDateTime().isAfter(getEntity().getEndDateTime())) {
				messages.append("Entity must have start time before end time.").append("\n");
			}
		}
		return messages.toString();
	}
}
