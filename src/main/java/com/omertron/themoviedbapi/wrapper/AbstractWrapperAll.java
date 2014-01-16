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
package com.omertron.themoviedbapi.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Base class for the wrappers
 *
 * @author Stuart
 */
public class AbstractWrapperAll extends AbstractWrapperId implements IWrapperId, IWrapperPages, IWrapperDates {

    @JsonProperty("page")
    private int page;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;
    @JsonProperty("dates")
    private ResultDates dates = new ResultDates();

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
    public ResultDates getDates() {
        return dates;
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

    @Override
    public void setDates(ResultDates dates) {
        this.dates = dates;
    }
}
