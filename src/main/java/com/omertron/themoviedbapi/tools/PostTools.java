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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omertron.themoviedbapi.MovieDbException;
import java.util.HashMap;
import java.util.Map;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 *
 * @author Stuart.Boston
 */
public class PostTools {

    // Jackson JSON configuration
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    private final Map<String, Object> values = new HashMap<>();

    public PostTools() {
    }

    public PostTools add(PostBody key, Object value) {
        values.put(key.getValue(), value);
        return this;
    }

    public PostTools add(String key, Object value) {
        values.put(key, value);
        return this;
    }

    public String build() throws MovieDbException {
        return convertToJson(values);
    }

    /**
     * Use Jackson to convert Map to JSON string.
     *
     * @param map
     * @return
     * @throws MovieDbException
     */
    private String convertToJson(Map<String, ?> map) throws MovieDbException {
        try {
            return MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "JSON conversion failed", "", ex);
        }
    }
}
