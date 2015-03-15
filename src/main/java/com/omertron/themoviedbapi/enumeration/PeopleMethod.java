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
package com.omertron.themoviedbapi.enumeration;

import com.omertron.themoviedbapi.interfaces.AppendToResponseMethod;
import org.apache.commons.lang3.StringUtils;

/**
 * List of People methods
 *
 * @author Stuart
 */
public enum PeopleMethod implements AppendToResponseMethod {

    CHANGES,
    COMBINED_CREDITS,
    EXTERNAL_IDS,
    IMAGES,
    MOVIE_CREDITS,
    TAGGED_IMAGES,
    TV_CREDITS;

    /**
     * Get the string to use in the URL
     *
     * @return
     */
    @Override
    public String getPropertyString() {
        return this.name().toLowerCase();
    }

    /**
     * Convert a string into an Enum type
     *
     * @param method
     * @return
     * @throws IllegalArgumentException If type is not recognised
     *
     */
    public static PeopleMethod fromString(String method) {
        if (StringUtils.isNotBlank(method)) {
            try {
                return PeopleMethod.valueOf(method.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Method " + method + " does not exist.", ex);
            }
        }
        throw new IllegalArgumentException("Method must not be null");
    }
}
