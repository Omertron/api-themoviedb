/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.themoviedb.wrapper;

import com.moviejukebox.themoviedb.model.MovieDb;
import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author stuart.boston
 */
public class WrapperMovie {
    /*
     * Logger
     */

    private static final Logger LOGGER = Logger.getLogger(WrapperMovie.class);
    /*
     * Properties
     */
    @JsonProperty("page")
    private int page;
    @JsonProperty("results")
    private List<MovieDb> movies;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;
    @JsonProperty("id")
    private int id;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public int getPage() {
        return page;
    }

    public List<MovieDb> getMovies() {
        return movies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getId() {
        return id;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setPage(int page) {
        this.page = page;
    }

    public void setMovies(List<MovieDb> results) {
        this.movies = results;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setId(int id) {
        this.id = id;
    }
    //</editor-fold>

    /**
     * Handle unknown properties and print a message
     *
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown property: '").append(key);
        sb.append("' value: '").append(value).append("'");
        LOGGER.warn(sb.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ResultList=[");
        sb.append("[page=").append(page);
        sb.append("],[pageResults=").append(movies.size());
        sb.append("],[totalPages=").append(totalPages);
        sb.append("],[totalResults=").append(totalResults);
        sb.append("],[id=").append(id);
        sb.append("]]");
        return sb.toString();
    }
}
