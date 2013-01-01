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

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.MovieDb;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author stuart.boston
 */
public class WrapperCompanyMovies {
    // Loggers
    private static final Logger logger = Logger.getLogger(WrapperCompanyMovies.class);
    /*
     * Properties
     */
    @JsonProperty("id")
    private int companyId;
    @JsonProperty("page")
    private int page;
    @JsonProperty("results")
    private List<MovieDb> results;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public int getCompanyId() {
        return companyId;
    }

    public int getPage() {
        return page;
    }

    public List<MovieDb> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setResults(List<MovieDb> results) {
        this.results = results;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
    //</editor-fold>

    /**
     * Handle unknown properties and print a message
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown property: '").append(key);
        sb.append("' value: '").append(value).append("'");
        logger.trace(sb.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ResultList=[");
        sb.append("[companyId=").append(companyId);
        sb.append("],[page=").append(page);
        sb.append("],[pageResults=").append(results.size());
        sb.append("],[totalPages=").append(totalPages);
        sb.append("],[totalResults=").append(totalResults);
        sb.append("]]");
        return sb.toString();
    }
}
