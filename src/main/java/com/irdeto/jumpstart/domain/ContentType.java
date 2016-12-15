
package com.irdeto.jumpstart.domain;

import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public enum ContentType {

    MOVIE("movie"),
    PREVIEW("preview"),
    BARKER("barker"),
    EVENT("event"),
    CHANNEL("channel");
    private final String value;
    private final static Map<String, ContentType> CONSTANTS = new HashMap<String, ContentType>();

    static {
        for (ContentType c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private ContentType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static ContentType fromValue(String value) {
        ContentType constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
