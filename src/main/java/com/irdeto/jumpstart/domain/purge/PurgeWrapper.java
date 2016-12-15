package com.irdeto.jumpstart.domain.purge;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.factory.WrapperWithDestinations;

public interface PurgeWrapper<U extends Base> extends WrapperWithDestinations<U> {
    String ACTION_DELETE = "Delete";
    String ACTION_DE_ORPHAN = "De-Orphan";
    String ACTION_RECURSE = "Recurse";

    String MODE_NOW = "Now";
    String MODE_DEFERRED = "Deferred";

    @JsonIgnore
    DateTime getEndDate();
    @JsonIgnore
    DateTime getModifiedDate();
    @JsonIgnore
    Integer getGracePeriod();

    U getEntity();
    void setEntity(U entity);

    String getAction();
    void setAction(String action);

    String getMode();
    void setMode(String mode);

    boolean purgeDue();
    @JsonIgnore
    String getPurgeDelay();

    @JsonIgnore
    List<Relationship<? extends BaseEntity>> getRelationshipList();

    @JsonIgnore
    List<Relationship<? extends BaseEntity>> getPurgeRelationshipList();
}
