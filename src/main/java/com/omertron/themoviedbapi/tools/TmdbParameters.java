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
package com.omertron.themoviedbapi.tools;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Parameters for the TMDB API
 *
 * @author Stuart
 */
public class TmdbParameters {

    private final Map<Param, String> parameters = new EnumMap<>(Param.class);

    /**
     * Construct an empty set of parameters
     */
    public TmdbParameters() {
        // Create an empty set of parameters
    }

    /**
     * Get the entry set of the parameters
     *
     * @return map of parameters
     */
    public Set<Map.Entry<Param, String>> getEntries() {
        return parameters.entrySet();
    }

    /**
     * Add an array parameter to the collection
     *
     * @param key Parameter to add
     * @param value The array value to use (will be converted into a comma separated list)
     */
    public void add(final Param key, final String[] value) {
        if (value != null && value.length > 0) {
            parameters.put(key, toList(value));
        }
    }

    /**
     * Add a string parameter to the collection
     *
     * @param key Parameter to add
     * @param value The value to add (will be checked to ensure it's valid)
     */
    public void add(final Param key, final String value) {
        if (StringUtils.isNotBlank(value)) {
            parameters.put(key, value);
        }
    }

    /**
     * Add an integer parameter to the collection
     *
     * @param key Parameter to add
     * @param value The value to add (will be checked to ensure greater than zero)
     */
    public void add(final Param key, final Integer value) {
        if (value != null && value > 0) {
            parameters.put(key, String.valueOf(value));
        }
    }

    /**
     * Add a float parameter to the collection
     *
     * @param key Parameter to add
     * @param value The value to add (will be checked to ensure greater than zero)
     */
    public void add(final Param key, final Float value) {
        if (value != null && value > 0f) {
            parameters.put(key, String.valueOf(value));
        }
    }

    /**
     * Add a boolean parameter to the collection
     *
     * @param key Parameter to add
     * @param value The value to add (will be checked to ensure greater than zero)
     */
    public void add(final Param key, final Boolean value) {
        if (value != null) {
            parameters.put(key, String.valueOf(value));
        }
    }

    /**
     * Check to see if the collection has a certain parameter
     *
     * @param key The Parameter to check
     * @return true if parameter exists
     */
    public boolean has(final Param key) {
        return parameters.containsKey(key);
    }

    /**
     * Get a parameter from the collection
     *
     * @param key The parameter to get
     * @return the parameter
     */
    public Object get(final Param key) {
        return parameters.get(key);
    }

    /**
     * Remove a parameter from the collection
     *
     * @param key parameter to remove
     */
    public void remove(final Param key) {
        parameters.remove(key);
    }

    /**
     * Check to see if the collection has no items
     *
     * @return true if the parameters are empty
     */
    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    /**
     * Check to see if the collection has items
     *
     * @return true if there are parameters
     */
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * Append any optional parameters to the URL
     *
     * @param appendToResponse String array to convert to a comma list
     * @return comma separated list
     */
    public String toList(final String[] appendToResponse) {
        StringBuilder sb = new StringBuilder();
        boolean first = Boolean.TRUE;
        for (String append : appendToResponse) {
            if (first) {
                first = Boolean.FALSE;
            } else {
                sb.append(",");
            }
            sb.append(append);
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(parameters, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
