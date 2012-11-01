/*
 *      Copyright (c) 2004-2012 Stuart Boston
 *
 *      This software is licensed under a Creative Commons License
 *      See the LICENCE.txt file included in this package
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.themoviedb.wrapper;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviejukebox.themoviedb.model.Genre;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Wrapper class for the Genres searches
 *
 * @author Stuart
 */
public class WrapperGenres {
    /*
     * Logger
     */

    private static final Logger LOGGER = Logger.getLogger(WrapperGenres.class);
    /*
     * Properties
     */
    @JsonProperty("genres")
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
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
        LOGGER.trace(sb.toString());
    }
}
