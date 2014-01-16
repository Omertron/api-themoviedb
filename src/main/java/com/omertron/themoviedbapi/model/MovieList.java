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
 * @author Stuart
 */
public class MovieList extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;

    @JsonProperty("description")
    private String description;
    @JsonProperty("favorite_count")
    private int favoriteCount;
    @JsonProperty("id")
    private String id;
    @JsonProperty("item_count")
    private int itemCount;
    @JsonProperty("iso_639_1")
    private String language;
    @JsonProperty("name")
    private String name;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("list_type")
    private String listType;

    public String getDescription() {
        return description;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public String getId() {
        return id;
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

    public String getListType() {
        return listType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setListType(String listType) {
        this.listType = listType;
    }
}
