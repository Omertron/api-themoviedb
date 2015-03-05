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

import org.apache.commons.lang3.StringUtils;

/**
 * Media type options
 *
 * @author Stuart
 */
public enum MediaType {

    /**
     * Movie media type
     */
    MOVIE,
    /**
     * TV Show media type
     */
    TV,
    /**
     * TV Episode media type
     */
    EPISODE;

    /**
     * Convert a string into an Enum type
     *
     * @param mediaType
     * @return
     * @throws IllegalArgumentException If type is not recognised
     *
     */
    public static MediaType fromString(String mediaType) {
        if (StringUtils.isNotBlank(mediaType)) {
            try {
                return MediaType.valueOf(mediaType.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("MediaType " + mediaType + " does not exist.", ex);
            }
        }
        throw new IllegalArgumentException("MediaType must not be null");
    }
}
