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
package com.omertron.themoviedbapi.results;

import com.omertron.themoviedbapi.wrapper.AbstractWrapper;
import com.omertron.themoviedbapi.wrapper.AbstractWrapperAll;
import com.omertron.themoviedbapi.wrapper.AbstractWrapperId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Abstract class to return the results and the id/page info
 *
 * @author Stuart
 */
public abstract class AbstractResults {

    private int id = 0;
    private int page = 0;
    private int totalPages = 0;
    private int totalResults = 0;

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    public void setId(int id) {
        this.id = id;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
    //</editor-fold>

    public void copyWrapper(AbstractWrapper wrapper) {
        // These results have nothing to copy, so this is just a placeholder
    }

    /**
     * Copy the Id from the wrapper
     *
     * @param wrapper
     */
    public void copyWrapper(AbstractWrapperId wrapper) {
        this.id = wrapper.getId();
    }

    /**
     * Copy the Id and pages from the wrapper
     *
     * @param wrapper
     */
    public void copyWrapper(AbstractWrapperAll wrapper) {
        this.id = wrapper.getId();
        this.page = wrapper.getPage();
        this.totalPages = wrapper.getTotalPages();
        this.totalResults = wrapper.getTotalResults();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
