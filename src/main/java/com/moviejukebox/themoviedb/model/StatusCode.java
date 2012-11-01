/*
 *      Copyright (c) 2004-2012 Stuart Boston
 *
 *      This software is licensed under a Creative Commons License
 *      See the LICENCE.txt file included in this package
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.themoviedb.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import org.apache.log4j.Logger;

/**
 *
 * @author Stuart
 */
public class StatusCode implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(StatusCode.class);
    /*
     * Properties
     */
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("status_message")
    private String statusMessage;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    //</editor-fold>

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
        StringBuilder sb = new StringBuilder();
        sb.append("Status Code: ").append(statusCode);
        sb.append(", Message: ").append(statusMessage);
        return sb.toString();
    }
}
