package com.irdeto.jumpstart.domain.reference;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceImageContent extends ReferenceContent {
    private Integer xResolution;
    private Integer yResolution;

    @JsonProperty("xResolution")
    public Integer getXResolution() {
        return xResolution;
    }
    public void setXResolution(Integer xResolution) {
        this.xResolution = xResolution;
    }

    @JsonProperty("yResolution")
    public Integer getYResolution() {
        return yResolution;
    }
    public void setYResolution(Integer yResolution) {
        this.yResolution = yResolution;
    }
}
