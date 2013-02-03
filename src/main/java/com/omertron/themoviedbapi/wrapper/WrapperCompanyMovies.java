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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.MovieDb;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author stuart.boston
 */
public class WrapperCompanyMovies extends WrapperBase {
    /*
     * Properties
     */

    @JsonProperty("results")
    private List<MovieDb> results;

    public WrapperCompanyMovies() {
        super(Logger.getLogger(WrapperCompanyMovies.class));
    }

    public List<MovieDb> getResults() {
        return results;
    }

    public void setResults(List<MovieDb> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ResultList=[");
        sb.append("[companyId=").append(getId());
        sb.append("],[page=").append(getPage());
        sb.append("],[pageResults=").append(getResults().size());
        sb.append("],[totalPages=").append(getTotalPages());
        sb.append("],[totalResults=").append(getTotalResults());
        sb.append("]]");
        return sb.toString();
    }
}
