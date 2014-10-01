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
import org.apache.commons.lang3.StringUtils;

/**
 * @author stuart.boston
 */
public class PersonCredit extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_STRING = "";
    /*
     * Properties
     */
    @JsonProperty("id")
    private int movieId = 0;
    @JsonProperty("character")
    private String character = DEFAULT_STRING;
    @JsonProperty("original_title")
    private String movieOriginalTitle = DEFAULT_STRING;
    @JsonProperty("poster_path")
    private String posterPath = DEFAULT_STRING;
    @JsonProperty("backdrop_path")
    private String backdropPath = DEFAULT_STRING;
    @JsonProperty("release_date")
    private String releaseDate = DEFAULT_STRING;
    @JsonProperty("title")
    private String movieTitle = DEFAULT_STRING;
    @JsonProperty("department")
    private String department = DEFAULT_STRING;
    @JsonProperty("job")
    private String job = DEFAULT_STRING;
    @JsonProperty("adult")
    private String adult = DEFAULT_STRING;
    @JsonProperty("credit_id")
    private String creditId = DEFAULT_STRING;
    private PersonType personType = PersonType.PERSON;
    @JsonProperty("popularity")
    private float popularity;
    @JsonProperty("vote_average")
    private float voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    @JsonProperty("media_type")
    private String mediaType;

    public String getCharacter() {
        return character;
    }

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getAdult() {
        return adult;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCharacter(String character) {
        this.character = StringUtils.trimToEmpty(character);
    }

    public void setDepartment(String department) {
        this.department = StringUtils.trimToEmpty(department);
    }

    public void setJob(String job) {
        this.job = StringUtils.trimToEmpty(job);
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setMovieOriginalTitle(String movieOriginalTitle) {
        this.movieOriginalTitle = StringUtils.trimToEmpty(movieOriginalTitle);
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = StringUtils.trimToEmpty(movieTitle);
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = StringUtils.trimToEmpty(posterPath);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = StringUtils.trimToEmpty(releaseDate);
    }

    public void setAdult(String adult) {
        this.adult = StringUtils.trimToEmpty(adult);
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
