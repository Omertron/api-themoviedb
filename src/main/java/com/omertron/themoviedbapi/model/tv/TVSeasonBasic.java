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
package com.omertron.themoviedbapi.model.tv;

import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.interfaces.Identification;
import java.io.Serializable;

/**
 * TV Favorite information
 *
 * @author stuart.boston
 */
public class TVSeasonBasic extends AbstractJsonMapping implements Serializable, Identification {

    private static final long serialVersionUID = 100L;

    @JsonProperty("id")
    private int id = -1;
    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("season_number")
    private int seasonNumber;
    @JsonProperty("episode_count")
    private int episodeCount = -1;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

}
