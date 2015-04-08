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
package com.omertron.themoviedbapi.model.list;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Wrapper for the ListItem function
 *
 * @author stuart.boston
 * @param <T> Type of list
 */
public class ListItem<T> extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("id")
    private String id;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("description")
    private String description;
    @JsonProperty("favorite_count")
    private int favoriteCount;
    @JsonProperty("items")
    private List<T> items = Collections.emptyList();
    @JsonProperty("item_count")
    private int itemCount;
    @JsonProperty("iso_639_1")
    private String language;
    @JsonProperty("name")
    private String name;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("status_code")
    private String statusCode;
    @JsonProperty("status_message")
    private String statusMessage;

    public String getId() {
        return id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDescription() {
        return description;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public List<T> getItems() {
        return items;
    }

    public int getItemCount() {
        return itemCount;
    }

    public String getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
