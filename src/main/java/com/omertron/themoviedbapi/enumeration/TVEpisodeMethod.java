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
 * List of TV methods
 *
 * @author Stuart
 */
public enum TVEpisodeMethod implements AppendToResponseMethod {

    CREDITS,
    EXTERNAL_IDS,
    IMAGES,
    VIDEOS;

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
    public static TVEpisodeMethod fromString(String method) {
        if (StringUtils.isNotBlank(method)) {
            try {
                return TVEpisodeMethod.valueOf(method.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Method " + method + " does not exist.", ex);
            }
        }
        throw new IllegalArgumentException("Method must not be null");
    }
}
