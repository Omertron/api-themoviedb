/*
 *      Copyright (c) 2004-2014 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The artwork type information
 *
 * @author Stuart
 */
public class Artwork extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;
    @JsonProperty("aspect_ratio")
    private float aspectRatio;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("height")
    private int height;
    @JsonProperty("iso_639_1")
    private String language;
    @JsonProperty("width")
    private int width;
    @JsonProperty("vote_average")
    private float voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    @JsonProperty("flag")
    private String flag;
    private ArtworkType artworkType = ArtworkType.POSTER;

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

    public int getWidth() {
        return width;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getFlag() {
        return flag;
    }

    public String getId() {
        return id;
    }

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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setId(String id) {
        this.id = id;
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
        if (this.width != other.width) {
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
        hash = 71 * hash + this.width;
        hash = 71 * hash + (this.artworkType != null ? this.artworkType.hashCode() : 0);
        return hash;
    }
}
