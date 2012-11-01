/*
 *      Copyright (c) 2004-2012 Stuart Boston
 *
 *      This software is licensed under a Creative Commons License
 *      See the LICENCE.txt file included in this package
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.omertron.themoviedbapi.wrapper;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.TmdbConfiguration;
import org.apache.log4j.Logger;

/**
 *
 * @author Stuart
 */
public class WrapperConfig {
    /*
     * Logger
     */

    private static final Logger LOGGER = Logger.getLogger(WrapperConfig.class);
    /*
     * Properties
     */
    @JsonProperty("images")
    private TmdbConfiguration tmdbConfiguration;

    public TmdbConfiguration getTmdbConfiguration() {
        return tmdbConfiguration;
    }

    public void setTmdbConfiguration(TmdbConfiguration tmdbConfiguration) {
        this.tmdbConfiguration = tmdbConfiguration;
    }

    /**
     * Handle unknown properties and print a message
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

}
