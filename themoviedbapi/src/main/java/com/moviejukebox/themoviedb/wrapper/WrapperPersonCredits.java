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

import com.moviejukebox.themoviedb.model.PersonCredit;
import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author stuart.boston
 */
public class WrapperPersonCredits {
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
    private List<PersonCredit> cast;
    @JsonProperty("crew")
    private List<PersonCredit> crew;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public List<PersonCredit> getCast() {
        return cast;
    }

    public List<PersonCredit> getCrew() {
        return crew;
    }

    public int getId() {
        return id;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setCast(List<PersonCredit> cast) {
        this.cast = cast;
    }

    public void setCrew(List<PersonCredit> crew) {
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
