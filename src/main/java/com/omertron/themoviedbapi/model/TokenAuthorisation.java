/*
 *      Copyright (c) 2004-2012 Stuart Boston
 *
 *      This software is licensed under a Creative Commons License
 *      See the LICENCE.txt file included in this package
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.Logger;

public class TokenAuthorisation {
    /*
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(TokenAuthorisation.class);
    /*
     * Properties
     */
    @JsonProperty("expires_at")
    private String expires;
    @JsonProperty("request_token")
    private String requestToken;
    @JsonProperty("success")
    private Boolean success;

    // <editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getExpires() {
        return expires;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public Boolean getSuccess() {
        return success;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setExpires(String expires) {
        this.expires = expires;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
    // </editor-fold>

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
        LOGGER.trace(sb.toString());
    }

    @Override
    public String toString() {
        return "TokenAuthorisation{" + "expires=" + expires + ", requestToken=" + requestToken + ", success=" + success + '}';
    }

}
