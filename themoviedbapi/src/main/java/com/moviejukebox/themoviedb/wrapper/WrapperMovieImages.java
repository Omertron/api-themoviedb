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

import com.moviejukebox.themoviedb.model.Artwork;
import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Stuart
 */
public class WrapperMovieImages {
    /*
     * Logger
     */

    private static final Logger logger = Logger.getLogger(WrapperMovieImages.class);
    /*
     * Properties
     */
    @JsonProperty("id")
    private int id;
    @JsonProperty("backdrops")
    private List<Artwork> backdrops;
    @JsonProperty("posters")
    private List<Artwork> posters;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public List<Artwork> getBackdrops() {
        return backdrops;
    }

    public int getId() {
        return id;
    }

    public List<Artwork> getPosters() {
        return posters;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setBackdrops(List<Artwork> backdrops) {
        this.backdrops = backdrops;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosters(List<Artwork> posters) {
        this.posters = posters;
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
        logger.warn(sb.toString());
    }
}
