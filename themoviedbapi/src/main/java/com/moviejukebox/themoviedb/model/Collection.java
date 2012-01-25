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
package com.moviejukebox.themoviedb.model;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 *
 * @author stuart.boston
 */
@JsonRootName("collection")
public class Collection {

    /*
     * Logger
     */
    private static final Logger logger = Logger.getLogger(Collection.class);
    /*
     * Properties
     */
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("name")
    private String name;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("release_date")
    private String releaseDate;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getBackdropPath() {
        return backdropPath;
    }

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        if (StringUtils.isBlank(title)) {
            return name;
        }
        return title;
    }

    public String getName() {
        if (StringUtils.isBlank(name)) {
            return title;
        }
        return name;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Collection other = (Collection) obj;
        if ((this.backdropPath == null) ? (other.backdropPath != null) : !this.backdropPath.equals(other.backdropPath)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.posterPath == null) ? (other.posterPath != null) : !this.posterPath.equals(other.posterPath)) {
            return false;
        }
        if ((this.releaseDate == null) ? (other.releaseDate != null) : !this.releaseDate.equals(other.releaseDate)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.backdropPath != null ? this.backdropPath.hashCode() : 0);
        hash = 19 * hash + this.id;
        hash = 19 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 19 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 19 * hash + (this.posterPath != null ? this.posterPath.hashCode() : 0);
        hash = 19 * hash + (this.releaseDate != null ? this.releaseDate.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Collection=");
        sb.append("[id=").append(id);
        sb.append("],[title=").append(title);
        sb.append("],[name=").append(name);
        sb.append("],[posterPath=").append(posterPath);
        sb.append("],[backdropPath=").append(backdropPath);
        sb.append("],[releaseDate=").append(releaseDate);
        sb.append("]]");
        return sb.toString();
    }
}
