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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.interfaces.WrapperPages;

/**
 * Abstract class to return the results and the id/page info
 *
 * @author Stuart
 */
public abstract class AbstractWrapperIdPages extends AbstractWrapperId implements WrapperPages {

    @JsonProperty("page")
    private int page = 0;
    @JsonProperty("total_pages")
    private int totalPages = 0;
    @JsonProperty("total_results")
    private int totalResults = 0;

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public int getTotalResults() {
        return totalResults;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * Copy the wrapper values to the results
     *
     * @param results
     */
    @Override
    public void setResultProperties(AbstractWrapperIdPages results) {
        super.setResultProperties(results);
        results.setPage(page);
        results.setTotalPages(totalPages);
        results.setTotalResults(totalResults);
    }
}
