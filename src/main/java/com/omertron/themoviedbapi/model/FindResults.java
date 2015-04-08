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
package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.model.tv.TVEpisodeBasic;
import com.omertron.themoviedbapi.model.tv.TVSeasonBasic;
import java.io.Serializable;
import java.util.List;

/**
 * @author stuart.boston
 */
public class FindResults extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("movie_results")
    private List<MovieBasic> movieResults;
    @JsonProperty("person_results")
    private List<PersonFind> personResults;
    @JsonProperty("tv_results")
    private List<TVBasic> tvResults;
    @JsonProperty("tv_season_results")
    private List<TVSeasonBasic> tvSeasonResults;
    @JsonProperty("tv_episode_results")
    private List<TVEpisodeBasic> tvEpisodeResults;

    public List<MovieBasic> getMovieResults() {
        return movieResults;
    }

    public void setMovieResults(List<MovieBasic> movieResults) {
        this.movieResults = movieResults;
    }

    public List<PersonFind> getPersonResults() {
        return personResults;
    }

    public void setPersonResults(List<PersonFind> personResults) {
        this.personResults = personResults;
    }

    public List<TVBasic> getTvResults() {
        return tvResults;
    }

    public void setTvResults(List<TVBasic> tvResults) {
        this.tvResults = tvResults;
    }

    public List<TVSeasonBasic> getTvSeasonResults() {
        return tvSeasonResults;
    }

    public void setTvSeasonResults(List<TVSeasonBasic> tvSeasonResults) {
        this.tvSeasonResults = tvSeasonResults;
    }

    public List<TVEpisodeBasic> getTvEpisodeResults() {
        return tvEpisodeResults;
    }

    public void setTvEpisodeResults(List<TVEpisodeBasic> tvEpisodeResults) {
        this.tvEpisodeResults = tvEpisodeResults;
    }

}
