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
package com.omertron.themoviedbapi.model.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.omertron.themoviedbapi.interfaces.Identification;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.io.Serializable;

/**
 *
 * @author Stuart.Boston
 */
public class MediaState extends AbstractJsonMapping implements Serializable, Identification {

    private static final long serialVersionUID = 100L;

    @JsonProperty("id")
    private int id;
    @JsonProperty("favorite")
    private boolean favorite;
    @JsonProperty("watchlist")
    private boolean watchlist;
    private float rated;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(boolean watchlist) {
        this.watchlist = watchlist;
    }

    public float getRated() {
        return rated;
    }

    @JsonSetter("rated")
    public void setRated(RatedValue rated) {
        this.rated = rated.getValue();
    }

}
