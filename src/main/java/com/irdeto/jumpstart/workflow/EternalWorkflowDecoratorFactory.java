package com.irdeto.jumpstart.workflow;

import static com.irdeto.jumpstart.factory.FactoryHelper.classesFor;

import java.util.Map;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.factory.FactoryHelper;

public class EternalWorkflowDecoratorFactory {
	
	@SuppressWarnings("unchecked")
	private static final Map<Class<Base>, Class<?>> eternalWorkflowDecoratorMap =
			classesFor(EternalWorkflowDecorator.class);
	private EternalWorkflowDecoratorFactory() {}
	
	public static <T extends Base> EternalWorkflowDecorator<T> getInstance(T entity) {
		if (entity == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		EternalWorkflowDecorator<T> eternalWorkflowDecorator =
				(EternalWorkflowDecorator<T>) FactoryHelper.getInstance(eternalWorkflowDecoratorMap, entity.getClass());
		if (eternalWorkflowDecorator != null) {
			eternalWorkflowDecorator.setEntityId(entity.getId());
			eternalWorkflowDecorator.setUriId(entity.getUriId());
		}
		return eternalWorkflowDecorator;
	}
}
