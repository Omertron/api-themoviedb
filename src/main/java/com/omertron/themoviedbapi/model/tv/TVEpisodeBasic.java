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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.model.media.MediaBasic;
import java.io.Serializable;

/**
 * TV Favorite information
 *
 * @author stuart.boston
 */
public class TVEpisodeBasic extends MediaBasic implements Serializable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("season_number")
    private int seasonNumber;
    @JsonProperty("episode_number")
    private int episodeNumber;
    @JsonProperty("name")
    private String name;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("still_path")
    private String stillPath;
    @JsonProperty("show_id")
    private String showId;

    public TVEpisodeBasic() {
        super.setMediaType(MediaType.EPISODE);
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

}
