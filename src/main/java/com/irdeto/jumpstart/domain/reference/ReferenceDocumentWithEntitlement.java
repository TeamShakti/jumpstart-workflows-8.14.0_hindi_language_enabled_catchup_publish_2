package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public abstract class ReferenceDocumentWithEntitlement extends ReferenceDocument {
    private String entitlementId;
    private List<ReferencePurchaseOption> purchaseOptions = new ArrayList<>();

    @JsonProperty("entitlementId")
    public String getEntitlementId() {
        return entitlementId;
    }
    public void setEntitlementId(String entitlementId) {
        this.entitlementId = entitlementId;
    }

    @JsonProperty("purchaseOptions")
    public List<ReferencePurchaseOption> getPurchaseOptions() {
        return purchaseOptions;
    }
    public void setPurchaseOptions(List<ReferencePurchaseOption> purchaseOptions) {
        this.purchaseOptions = purchaseOptions;
    }
}
