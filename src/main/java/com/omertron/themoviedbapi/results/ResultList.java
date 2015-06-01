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
package com.omertron.themoviedbapi.results;

import java.util.ArrayList;
import java.util.List;

/**
 * List of the results from TheMovieDb
 *
 * If the original request contains, or could contain, a page of results, this wrapper is returned to indicate what page was
 * returned and how many are available
 *
 * @author Stuart
 * @param <T>
 */
public final class ResultList<T> extends AbstractWrapperIdPages {

    private List<T> results;

    public ResultList() {
        this(null);
    }

    public ResultList(List<T> resultList) {
        if (resultList == null) {
            results = new ArrayList<>();
        } else {
            results = new ArrayList<>(resultList);
        }
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

    @Override
    public int getTotalResults() {
        if (super.getTotalResults() == 0) {
            return results.size();
        } else {
            return super.getTotalResults();
        }
    }
}
