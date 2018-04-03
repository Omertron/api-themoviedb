/*
 *      Copyright (c) 2004-2016 Stuart Boston
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

/**
 * Release Type for the video
 *
 * @author stuar
 */
public enum ReleaseType {
    PREMIERE(1),
    THEATRICAL_LIMITED(2),
    THEATRICAL(3),
    DIGITAL(4),
    PHYSICAL(5),
    TV(6),
    UNKNOWN(0);

    private final int type;

    private ReleaseType(int type) {
        this.type = type;
    }

    /**
     * Get the Release Type from an integer type
     *
     * @param type Integer to convert to enum
     * @return enum version of param
     */
    public static ReleaseType fromInteger(int type) {
        for (ReleaseType rt : ReleaseType.values()) {
            if (rt.type == type) {
                return rt;
            }
        }
        return UNKNOWN;
    }

}
