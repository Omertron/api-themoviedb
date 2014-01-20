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
package com.omertron.themoviedbapi.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.omertron.themoviedbapi.model.type.PersonType;
import org.apache.commons.lang3.StringUtils;

/**
 * @author stuart.boston
 */
public abstract class PersonMovie extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;
    /*
     * Properties
     */
    // Movie information
    @JsonProperty("id")
    private int movieId = 0;
    @JsonProperty("original_title")
    private String movieOriginalTitle;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("title")
    private String movieTitle;
    @JsonProperty("adult")
    private String adult;
    private PersonType personType;

    public PersonMovie() {
        this.personType = PersonType.PERSON;
    }

    public PersonMovie(PersonType personType) {
        this.personType = personType;
    }

    public PersonType getPersonType() {
        return personType;
    }

    protected void setPersonType(PersonType personType) {
        this.personType = personType;
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

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getAdult() {
        return adult;
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

    public void setPosterPath(String posterPath) {
        this.posterPath = StringUtils.trimToEmpty(posterPath);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = StringUtils.trimToEmpty(releaseDate);
    }

    public void setAdult(String adult) {
        this.adult = StringUtils.trimToEmpty(adult);
    }
}
