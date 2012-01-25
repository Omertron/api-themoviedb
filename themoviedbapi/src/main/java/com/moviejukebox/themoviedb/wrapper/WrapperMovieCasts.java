/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.themoviedb.wrapper;

import com.moviejukebox.themoviedb.model.PersonCast;
import com.moviejukebox.themoviedb.model.PersonCrew;
import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Stuart
 */
public class WrapperMovieCasts {
    /*
     * Logger
     */

    private static final Logger LOGGER = Logger.getLogger(WrapperMovieCasts.class);
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
        LOGGER.warn(sb.toString());
    }
}
