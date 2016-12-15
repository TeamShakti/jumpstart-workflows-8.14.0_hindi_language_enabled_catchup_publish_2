package com.irdeto.jumpstart.workflow.publish.reference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.factory.AbstractInstance;
import com.irdeto.jumpstart.factory.WrapperWithDestinations;

public interface ReferenceDocumentMapper<T extends Base> extends AbstractInstance<T> {
    void setPublishWrapper(PublishWrapper<T> publishWrapper);

    @JsonIgnore
    ReferenceDocument getReferenceDocument() throws Exception;
}
