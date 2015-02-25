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
package com.omertron.themoviedbapi.model2.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model2.MediaBasic;
import java.util.List;

/**
 * @author stuart.boston
 */
public class PersonFind extends PersonBasic {

    private static final long serialVersionUID = 1L;
    @JsonProperty("adult")
    private Boolean adult;
    @JsonProperty("popularity")
    private Float popularity;
    @JsonProperty("profile_path")
    private String profilePath;
    @JsonProperty("known_for")
    private List<? extends MediaBasic> knownFor;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public List<? extends MediaBasic> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(List<? extends MediaBasic> knownFor) {
        this.knownFor = knownFor;
    }

}
