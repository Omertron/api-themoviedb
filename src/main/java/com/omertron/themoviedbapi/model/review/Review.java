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
package com.omertron.themoviedbapi.model.review;

import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * @author Stuart
 */
public class Review extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("id")
    private String id;
    @JsonProperty("author")
    private String author;
    @JsonProperty("content")
    private String content;
    @JsonProperty("iso_639_1")
    private String langauage;
    @JsonProperty("media_id")
    private String mediaId;
    @JsonProperty("media_title")
    private String mediaTitle;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLangauage() {
        return langauage;
    }

    public void setLangauage(String langauage) {
        this.langauage = langauage;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
