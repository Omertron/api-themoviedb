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

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The artwork type information
 *
 * @author Stuart
 */
public class Artwork {

    /*
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(Artwork.class);
    /*
     * Properties
     */
    @JsonProperty("aspect_ratio")
    private float aspectRatio;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("height")
    private int height;
    @JsonProperty("iso_639_1")
    private String language;
    @JsonProperty("width")
    private String width;
    @JsonProperty("vote_average")
    private float voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    private ArtworkType artworkType = ArtworkType.POSTER;

    // <editor-fold defaultstate="collapsed" desc="Getter methods">
    public ArtworkType getArtworkType() {
        return artworkType;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getHeight() {
        return height;
    }

    public String getLanguage() {
        return language;
    }

    public String getWidth() {
        return width;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setArtworkType(ArtworkType artworkType) {
        this.artworkType = artworkType;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
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
        LOGGER.warn(sb.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Artwork other = (Artwork) obj;
        if (Float.floatToIntBits(this.aspectRatio) != Float.floatToIntBits(other.aspectRatio)) {
            return false;
        }
        if ((this.filePath == null) ? (other.filePath != null) : !this.filePath.equals(other.filePath)) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if ((this.language == null) ? (other.language != null) : !this.language.equals(other.language)) {
            return false;
        }
        if ((this.width == null) ? (other.width != null) : !this.width.equals(other.width)) {
            return false;
        }
        if (this.artworkType != other.artworkType) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Float.floatToIntBits(this.aspectRatio);
        hash = 71 * hash + (this.filePath != null ? this.filePath.hashCode() : 0);
        hash = 71 * hash + this.height;
        hash = 71 * hash + (this.language != null ? this.language.hashCode() : 0);
        hash = 71 * hash + (this.width != null ? this.width.hashCode() : 0);
        hash = 71 * hash + (this.artworkType != null ? this.artworkType.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Artwork=");
        sb.append("[aspectRatio=").append(aspectRatio);
        sb.append("],[filePath=").append(filePath);
        sb.append("],[height=").append(height);
        sb.append("],[language=").append(language);
        sb.append("],[width=").append(width);
        sb.append("],[artworkType=").append(artworkType);
        sb.append("]]");
        return sb.toString();
    }
}
