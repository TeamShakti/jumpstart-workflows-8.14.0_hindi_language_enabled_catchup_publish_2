package com.irdeto.jumpstart.workflow.publish.control;

import static com.irdeto.jumpstart.factory.FactoryHelper.classesFor;

import java.util.Map;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.factory.FactoryHelper;

public class ControlMapperFactory {
	@SuppressWarnings("unchecked")
	private static final Map<Class<Base>, Class<?>> controlMapperMap = classesFor(ControlMapper.class);
	private ControlMapperFactory() {}

	public static <T extends Base> ControlMapper<T> getInstance(PublishWrapper<T> publishWrapper) {
		if (publishWrapper == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		ControlMapper<T> controlMapper = (ControlMapper<T>) FactoryHelper.getInstance(controlMapperMap,
				publishWrapper.getApprovedEntity().getClass());
		if (controlMapper != null) {
			controlMapper.setPublishWrapper(publishWrapper);
		}
		return controlMapper;
	}
}
