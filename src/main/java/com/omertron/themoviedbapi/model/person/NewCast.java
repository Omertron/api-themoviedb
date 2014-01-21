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

public class NewCast extends AbstractJsonMapping {

    @JsonProperty("id")
    private int id;
    @JsonProperty("character")
    private String character;
    private String title;
    private String titleOriginal;
    @JsonProperty("poster_path")
    private String posterPath;
    private String date;
    private MediaType mediaType;
    // TV specific properties
    @JsonProperty("credit_id")
    private String creditId = "";
    @JsonProperty("episode_count")
    private int episodeCount = -1;
    // Movie specific properties
    @JsonProperty("adult")
    private boolean adult = Boolean.FALSE;

    public void setId(int id) {
        this.id = id;
    }

    public void setCharacter(String character) {
        this.character = character;
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

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @JsonSetter("release_date")
    public void setReleaseDate(String date) {
        this.date = date;
    }

    @JsonSetter("first_air_date")
    public void setFirstAiredDate(String date) {
        this.date = date;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @JsonSetter("media_type")
    public void setMediaType(String mediaType) {
        this.mediaType = MediaType.valueOf(mediaType.toUpperCase());
    }

    public int getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleOriginal() {
        return titleOriginal;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getDate() {
        return date;
    }

    public String getCreditId() {
        return creditId;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public boolean isAdult() {
        return adult;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

}
