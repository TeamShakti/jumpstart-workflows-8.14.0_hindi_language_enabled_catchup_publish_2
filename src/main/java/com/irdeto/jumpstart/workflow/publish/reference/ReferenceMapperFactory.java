package com.irdeto.jumpstart.workflow.publish.reference;

import static com.irdeto.jumpstart.factory.FactoryHelper.classesFor;

import java.util.Map;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.factory.FactoryHelper;

@SuppressWarnings("unchecked")
public class ReferenceMapperFactory {
    private static final Map<Class<Base>, Class<?>> referenceMapperMap =
            classesFor(ReferenceDocumentMapper.class);

    private ReferenceMapperFactory() {
    }

    public static <T extends Base> ReferenceDocumentMapper<T> getInstance(PublishWrapper<T> publishWrapper) {
        if (publishWrapper == null) {
            return null;
        }
        ReferenceDocumentMapper<T> referenceMapper = (ReferenceDocumentMapper<T>) FactoryHelper.getInstance(
                referenceMapperMap,
                publishWrapper.getApprovedEntity().getClass()
        );
        if (referenceMapper != null) {
            referenceMapper.setPublishWrapper(publishWrapper);
        }
        return referenceMapper;
    }
}
