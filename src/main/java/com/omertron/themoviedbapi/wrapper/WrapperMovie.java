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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author stuart.boston
 */
public class WrapperMovie extends WrapperBase {
    /*
     * Properties
     */

    @JsonProperty("results")
    private List<MovieDb> movies;

    public WrapperMovie() {
        super(LoggerFactory.getLogger(WrapperMovie.class));
    }

    public List<MovieDb> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDb> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ResultList=[");
        sb.append("[page=").append(getPage());
        sb.append("],[pageResults=").append(getMovies().size());
        sb.append("],[totalPages=").append(getTotalPages());
        sb.append("],[totalResults=").append(getTotalResults());
        sb.append("],[id=").append(getId());
        sb.append("]]");
        return sb.toString();
    }
}
