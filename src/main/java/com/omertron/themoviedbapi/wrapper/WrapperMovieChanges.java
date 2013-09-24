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
package com.omertron.themoviedbapi.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.ChangedMovie;

public class WrapperMovieChanges extends AbstractWrapperAll {

    @JsonProperty("results")
    private List<ChangedMovie> results;

    public WrapperMovieChanges() {
        super(WrapperMovieChanges.class);
    }

    public List<ChangedMovie> getResults() {
        return results;
    }

    public void setResults(List<ChangedMovie> results) {
        this.results = results;
    }
}
