package com.irdeto.jumpstart.domain.publish;

import static com.irdeto.jumpstart.factory.FactoryHelper.classesFor;

import java.util.Map;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.factory.FactoryHelper;

public class PublishWrapperFactory {
	private PublishWrapperFactory() {}
	@SuppressWarnings("unchecked")
	private static final Map<Class<Base>, Class<?>> publishWrapperMap = classesFor(PublishWrapper.class);

	public static <T extends Base> PublishWrapper<T> getInstance(T entity) {
		if (entity == null) {
			return null;
		}

		@SuppressWarnings("unchecked")
		PublishWrapper<T> publishWrapper = (PublishWrapper<T>)
				FactoryHelper.getInstance(publishWrapperMap, entity.getClass());
		if (publishWrapper != null) {
			publishWrapper.setEntity(entity);
		}
		return publishWrapper;
    }

}
