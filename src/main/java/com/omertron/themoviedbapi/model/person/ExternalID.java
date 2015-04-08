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
import com.omertron.themoviedbapi.interfaces.Identification;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.io.Serializable;

/**
 *
 * @author Stuart
 */
public class ExternalID extends AbstractJsonMapping implements Serializable, Identification {

    private static final long serialVersionUID = 100L;

    @JsonProperty("id")
    private int id;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("freebase_mid")
    private String freebaseMid;
    @JsonProperty("freebase_id")
    private String freebaseId;
    @JsonProperty("tvdb_id")
    private String tvdbId;
    @JsonProperty("tvrage_id")
    private String tvrageId;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
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

    public String getTvdbId() {
        return tvdbId;
    }

    public void setTvdbId(String tvdbId) {
        this.tvdbId = tvdbId;
    }

}
