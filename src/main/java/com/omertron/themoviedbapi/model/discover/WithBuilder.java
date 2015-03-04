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
package com.omertron.themoviedbapi.model.discover;

/**
 * Class to create a String for the "with???" parameters of the Discover method
 *
 * @author Stuart
 */
public class WithBuilder {

    StringBuilder value = new StringBuilder();

    /**
     * Create the first ID in the string
     *
     * @param id
     */
    public WithBuilder(int id) {
        value.append(id);
    }

    /**
     * Create the first ID in the string
     *
     * @param id
     */
    public WithBuilder(String id) {
        value.append(id);
    }

    /**
     * Generate the string to pass to the method
     *
     * @return
     */
    public String build() {
        return value.toString();
    }

    /**
     * Generate the string to pass to the method
     *
     * @return
     */
    @Override
    public String toString() {
        return build();
    }

    /**
     * Add an "AND ID"
     *
     * @param id
     * @return
     */
    public WithBuilder and(int id) {
        value.append(",").append(id);
        return this;
    }

    /**
     * Add an "AND ID"
     *
     * @param id
     * @return
     */
    public WithBuilder and(String id) {
        value.append(",").append(id);
        return this;
    }

    /**
     * Add an "OR ID"
     *
     * @param id
     * @return
     */
    public WithBuilder or(int id) {
        value.append("|").append(id);
        return this;
    }

    /**
     * Add an "OR ID"
     *
     * @param id
     * @return
     */
    public WithBuilder or(String id) {
        value.append("|").append(id);
        return this;
    }

}
