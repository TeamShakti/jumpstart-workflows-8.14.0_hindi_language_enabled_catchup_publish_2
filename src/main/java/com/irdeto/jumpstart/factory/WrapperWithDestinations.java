package com.irdeto.jumpstart.factory;

import static java.util.Collections.emptyList;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;

public interface WrapperWithDestinations<T extends Base> extends AbstractInstance<T> {
    String ACTION_PUBLISH = "publish";
    String ACTION_PUBLISH_CREATE = "create";
    String ACTION_PUBLISH_UPDATE = "update";
    String ACTION_PURGE = "purge";

    T getEntity();
    void setEntity(T entity);

    @JsonIgnore
    default List<String> getDestinationProcessIdList() {
        return emptyList();
    }
}
