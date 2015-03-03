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
package com.omertron.themoviedbapi.model2.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model2.AbstractJsonMapping;

/**
 *
 * @author Stuart.Boston
 */
public class MediaState extends AbstractJsonMapping {

    @JsonProperty("id")
    private int id;
    @JsonProperty("favorite")
    private boolean favorite;
    @JsonProperty("watchlist")
    private boolean watchlist;
    @JsonProperty("rated")
    private RatedValue rated;

    public int getId() {
        return id;
    }

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
        return rated.getValue();
    }

    public void setRated(RatedValue rated) {
        this.rated = rated;
    }

    public class RatedValue extends AbstractJsonMapping {

        @JsonProperty("value")
        private float value = -1f;

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }
}
