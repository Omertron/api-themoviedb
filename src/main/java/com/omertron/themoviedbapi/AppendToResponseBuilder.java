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
package com.omertron.themoviedbapi;

import com.omertron.themoviedbapi.interfaces.AppendToResponseMethod;

/**
 * Simple string builder for the Append To Response methods
 *
 * @author Stuart.Boston
 */
public class AppendToResponseBuilder {

    private final StringBuilder response;

    /**
     * Construct the builder with the first method
     *
     * @param method
     */
    public AppendToResponseBuilder(AppendToResponseMethod method) {
        response = new StringBuilder(method.getPropertyString());
    }

    /**
     * Generate the string
     *
     * @return
     */
    public String build() {
        return response.toString();
    }

    /**
     * Add a method to the list
     *
     * @param method
     * @return
     */
    public AppendToResponseBuilder add(AppendToResponseMethod method) {
        response.append(",").append(method.getPropertyString());
        return this;
    }
}
