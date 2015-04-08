/*
 *      Copyright (c) 2004-2015 Stuart Boston
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
package com.omertron.themoviedbapi.model.artwork;

import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The artwork type information
 *
 * @author Stuart
 */
public class Artwork extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 100L;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public ArtworkType getArtworkType() {
        return artworkType;
    }

    public void setArtworkType(ArtworkType artworkType) {
        this.artworkType = artworkType;
    }

    @JsonSetter("image_type")
    public void setArtworkType(String artworkType) {
        this.artworkType = ArtworkType.fromString(artworkType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Artwork) {
            final Artwork other = (Artwork) obj;
            return new EqualsBuilder()
                    .append(aspectRatio, other.aspectRatio)
                    .append(filePath, other.filePath)
                    .append(language, other.language)
                    .append(height, other.height)
                    .append(width, other.width)
                    .append(artworkType, other.artworkType)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(aspectRatio)
                .append(filePath)
                .append(height)
                .append(width)
                .append(language)
                .append(artworkType)
                .toHashCode();
    }
}
