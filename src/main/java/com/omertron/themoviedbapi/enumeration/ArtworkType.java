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
 * ArtworkType enum List of the artwork types that are available
 */
public enum ArtworkType {

    /**
     * Poster artwork
     */
    POSTER,
    /**
     * Fanart/backdrop
     */
    BACKDROP,
    /**
     * Person image
     */
    PROFILE,
    /**
     * Still (Video image)
     */
    STILL;

    /**
     * Convert a string into an Enum type
     *
     * @param artworkType
     * @return
     * @throws IllegalArgumentException If type is not recognised
     *
     */
    public static ArtworkType fromString(String artworkType) {
        if (StringUtils.isNotBlank(artworkType)) {
            try {
                return ArtworkType.valueOf(artworkType.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("ArtworkType " + artworkType + " does not exist.", ex);
            }
        }
        throw new IllegalArgumentException("ArtworkType must not be null");
    }
}
