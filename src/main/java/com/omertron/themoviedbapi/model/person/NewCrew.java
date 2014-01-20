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
package com.omertron.themoviedbapi.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.omertron.themoviedbapi.model.type.MediaType;

public class NewCrew extends AbstractJsonMapping {

    // NewCrew ID for TV, or ID for Movie
    @JsonProperty("id")
    private int id;
    private String title;
    private String titleOriginal;
    @JsonProperty("department")
    private String department;
    @JsonProperty("job")
    private String job;
    @JsonProperty("poster_path")
    private String posterPath;
    private MediaType mediaType;
    // TV specific properties
    @JsonProperty("credit_id")
    private String creditId = "";
    @JsonProperty("episode_count")
    private int episodeCount = -1;
    // Movie specific properties
    @JsonProperty("release_date")
    private String releaseDate = "";
    @JsonProperty("adult")
    private boolean adult = Boolean.FALSE;

    public void setId(int id) {
        this.id = id;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    @JsonSetter("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.title = name;
    }

    @JsonSetter("original_title")
    public void setTitleOriginal(String titleOriginal) {
        this.titleOriginal = titleOriginal;
    }

    @JsonSetter("original_name")
    public void setNameOriginal(String nameOriginal) {
        this.titleOriginal = nameOriginal;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @JsonSetter("media_type")
    public void setMediaType(String mediaType) {
        this.mediaType = MediaType.valueOf(mediaType.toUpperCase());
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleOriginal() {
        return titleOriginal;
    }

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getCreditId() {
        return creditId;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isAdult() {
        return adult;
    }

}
