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
import com.omertron.themoviedbapi.model.PersonCast;
import com.omertron.themoviedbapi.model.PersonCrew;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Stuart
 */
public class WrapperMovieCasts {
    /*
     * Logger
     */

    private static final Logger logger = Logger.getLogger(WrapperMovieCasts.class);
    /*
     * Properties
     */
    @JsonProperty("id")
    private int id;
    @JsonProperty("cast")
    private List<PersonCast> cast;
    @JsonProperty("crew")
    private List<PersonCrew> crew;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public List<PersonCast> getCast() {
        return cast;
    }

    public List<PersonCrew> getCrew() {
        return crew;
    }

    public int getId() {
        return id;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setCast(List<PersonCast> cast) {
        this.cast = cast;
    }

    public void setCrew(List<PersonCrew> crew) {
        this.crew = crew;
    }

    public void setId(int id) {
        this.id = id;
    }
    //</editor-fold>

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
        logger.trace(sb.toString());
    }
}
