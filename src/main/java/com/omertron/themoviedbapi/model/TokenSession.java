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

public class TokenSession {
    /*
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(TokenSession.class);
    /*
     * Properties
     */
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status_code")
    private String statusCode;
    @JsonProperty("status_message")
    private String statusMessage;

    // <editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getSessionId() {
        return sessionId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
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
        return "TokenSession{" + "sessionId=" + sessionId + ", success=" + success + ", statusCode=" + statusCode + ", statusMessage=" + statusMessage + '}';
    }

}
