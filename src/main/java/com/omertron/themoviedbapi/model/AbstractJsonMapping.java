package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author Holger Brandl
 */
public abstract class AbstractJsonMapping implements Serializable {

    private static Logger getLogger(Class<?> aClass) {
        return LoggerFactory.getLogger(aClass);
    }

    /**
     * Handle unknown properties and print a message
     *
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown property: '").append(key);
        sb.append("' value: '").append(value).append("'");

        getLogger(this.getClass()).warn(sb.toString());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
