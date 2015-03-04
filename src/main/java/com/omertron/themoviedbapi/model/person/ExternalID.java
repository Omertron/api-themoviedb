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
package com.omertron.themoviedbapi.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;

/**
 *
 * @author Stuart
 */
public class ExternalID extends AbstractJsonMapping {

    @JsonProperty("id")
    private String id;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("freebase_mid")
    private String freebaseMid;
    @JsonProperty("freebase_id")
    private String freebaseId;
    @JsonProperty("tvrage_id")
    private String tvrageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getFreebaseMid() {
        return freebaseMid;
    }

    public void setFreebaseMid(String freebaseMid) {
        this.freebaseMid = freebaseMid;
    }

    public String getFreebaseId() {
        return freebaseId;
    }

    public void setFreebaseId(String freebaseId) {
        this.freebaseId = freebaseId;
    }

    public String getTvrageId() {
        return tvrageId;
    }

    public void setTvrageId(String tvrageId) {
        this.tvrageId = tvrageId;
    }

}
