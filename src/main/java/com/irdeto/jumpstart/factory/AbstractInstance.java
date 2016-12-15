package com.irdeto.jumpstart.factory;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.BaseEntity;

public interface AbstractInstance<T extends BaseEntity> {
    @JsonIgnore
    Class<T> getEntityClass();
}
