/*
 *      Copyright (c) 2004-2013 Stuart Boston
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
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.person.PersonCrew;
import java.util.List;

/**
 * TV Series information<br/>
 * Represents all the information returned.
 *
 * @author Stuart
 */
public class TVSeries extends TVSeriesBasic {

    @JsonProperty("overview")
    private String overview;
    @JsonProperty("last_air_date")
    private String lastAirDate;
    @JsonProperty("created_by")
    private List<PersonCrew> createdBy;
    @JsonProperty("episode_run_time")
    private List<Integer> episodeRuntime;
    @JsonProperty("genres")
    private List<Genre> genres;
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("in_production")
    private boolean inProduction;
    @JsonProperty("languages")
    private List<String> languages;
    @JsonProperty("networks")
    private List<Network> networks;
    @JsonProperty("number_of_episodes")
    private int numberEpisodes;
    @JsonProperty("number_of_seasons")
    private int numberSeasons;
    @JsonProperty("origin_country")
    private List<String> originCountry;
    @JsonProperty("status")
    private String status;
    @JsonProperty("seasons")
    private List<TVSeasonBasic> seasons;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public List<PersonCrew> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<PersonCrew> createdBy) {
        this.createdBy = createdBy;
    }

    public List<Integer> getEpisodeRuntime() {
        return episodeRuntime;
    }

    public void setEpisodeRuntime(List<Integer> episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public int getNumberEpisodes() {
        return numberEpisodes;
    }

    public void setNumberEpisodes(int numberEpisodes) {
        this.numberEpisodes = numberEpisodes;
    }

    public int getNumberSeasons() {
        return numberSeasons;
    }

    public void setNumberSeasons(int numberSeasons) {
        this.numberSeasons = numberSeasons;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TVSeasonBasic> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<TVSeasonBasic> seasons) {
        this.seasons = seasons;
    }

}
