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

import com.moviejukebox.themoviedb.model.Trailer;
import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Stuart
 */
public class WrapperTrailers {
    /*
     * Logger
     */

    private static final Logger LOGGER = Logger.getLogger(WrapperTrailers.class);
    /*
     * Properties
     */
    @JsonProperty("id")
    private int id;
    @JsonProperty("quicktime")
    private List<Trailer> quicktime;
    @JsonProperty("youtube")
    private List<Trailer> youtube;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public int getId() {
        return id;
    }

    public List<Trailer> getQuicktime() {
        return quicktime;
    }

    public List<Trailer> getYoutube() {
        return youtube;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setId(int id) {
        this.id = id;
    }

    public void setQuicktime(List<Trailer> quicktime) {
        this.quicktime = quicktime;
    }

    public void setYoutube(List<Trailer> youtube) {
        this.youtube = youtube;
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
