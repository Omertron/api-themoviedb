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
package com.omertron.themoviedbapi.tools;

import java.util.EnumSet;
import org.apache.commons.lang3.StringUtils;

public enum MethodBase {

    ACCOUNT("account"),
    AUTH("authentication"),
    CERTIFICATION("certification"),
    COLLECTION("collection"),
    COMPANY("company"),
    CONFIGURATION("configuration"),
    CREDIT("credit"),
    DISCOVER("discover"),
    EPISODE("episode"),
    FIND("find"),
    GENRE("genre"),
    GUEST_SESSION("guest_session"),
    JOB("job"),
    KEYWORD("keyword"),
    LIST("list"),
    MOVIE("movie"),
    NETWORK("network"),
    PERSON("person"),
    REVIEW("review"),
    SEARCH("search"),
    SEASON("season"),
    TIMEZONES("timezones"),
    TV("tv");

    private final String value;

    private MethodBase(String value) {
        this.value = value;
    }

    /**
     * Get the URL parameter to use
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Convert a string into an Enum type
     *
     * @param value
     * @return
     */
    public static MethodBase fromString(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (final MethodBase method : EnumSet.allOf(MethodBase.class)) {
                if (value.equalsIgnoreCase(method.value)) {
                    return method;
                }
            }
        }

        // We've not found the type!
        throw new IllegalArgumentException("Method '" + value + "' not recognised");
    }
}
