/*
 *      Copyright (c) 2004-2014 Stuart Boston
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

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The API URL that is used to construct the API call
 *
 * @author Stuart
 */
public class ApiUrl {

    /*
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(ApiUrl.class);
    /*
     * TheMovieDbApi API Base URL
     */
    private static final String TMDB_API_BASE = "http://api.themoviedb.org/3/";
    /*
     * Parameter configuration
     */
    private static final String DELIMITER_FIRST = "?";
    private static final String DELIMITER_SUBSEQUENT = "&";
    private static final String DEFAULT_STRING = "";
    /*
     * Properties
     */
    private final String apiKey;
    private final String method;
    private final String submethod;
    private final Map<String, String> arguments = new HashMap<String, String>();
    /*
     * API Parameters
     */
    public static final String PARAM_ADULT = "include_adult=";
    public static final String PARAM_API_KEY = "api_key=";
    public static final String PARAM_COUNTRY = "country=";
    public static final String PARAM_FAVORITE = "favorite=";
    public static final String PARAM_ID = "id=";
    public static final String PARAM_LANGUAGE = "language=";
    public static final String PARAM_INCLUDE_ALL_MOVIES = "include_all_movies=";
    public static final String PARAM_MOVIE_WATCHLIST = "movie_watchlist=";
    public static final String PARAM_PAGE = "page=";
    public static final String PARAM_QUERY = "query=";
    public static final String PARAM_SESSION = "session_id=";
    public static final String PARAM_TOKEN = "request_token=";
    public static final String PARAM_VALUE = "value=";
    public static final String PARAM_YEAR = "year=";
    public static final String PARAM_START_DATE = "start_date=";
    public static final String PARAM_END_DATE = "end_date=";
    private static final String APPEND_TO_RESPONSE = "append_to_response=";

    //<editor-fold defaultstate="collapsed" desc="Constructor Methods">
    /**
     * Constructor for the simple API URL method without a sub-method
     *
     * @param apiKey
     * @param method
     */
    public ApiUrl(String apiKey, String method) {
        this.apiKey = apiKey;
        this.method = method;
        this.submethod = DEFAULT_STRING;
    }

    /**
     * Constructor for the API URL with a sub-method
     *
     * @param apiKey
     * @param method
     * @param submethod
     */
    public ApiUrl(String apiKey, String method, String submethod) {
        this.apiKey = apiKey;
        this.method = method;
        this.submethod = submethod;
    }
    //</editor-fold>

    /**
     * Build the URL from the pre-created arguments.
     *
     * @return
     */
    public URL buildUrl() {
        StringBuilder urlString = new StringBuilder(TMDB_API_BASE);

        // Get the start of the URL
        urlString.append(method);

        // We have either a queury, or a direct request
        if (arguments.containsKey(PARAM_QUERY)) {
            // Append the suffix of the API URL
            if (StringUtils.endsWith(urlString, "/") && submethod.startsWith("/")) {
                urlString.deleteCharAt(urlString.length() - 1);
            }
            urlString.append(submethod);

            // Append the key information
            urlString.append(DELIMITER_FIRST).append(PARAM_API_KEY);
            urlString.append(apiKey);

            // Append the search term
            urlString.append(DELIMITER_SUBSEQUENT);
            urlString.append(PARAM_QUERY);

            String query = arguments.get(PARAM_QUERY);

            try {
                urlString.append(URLEncoder.encode(query, "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                LOG.trace("Unable to encode query: '{}' trying raw.", query);
                // If we can't encode it, try it raw
                urlString.append(query);
            }

            // Remove the query from the arguments so it is not added later
            arguments.remove(PARAM_QUERY);
        } else {
            // Append the ID if provided
            if (arguments.containsKey(PARAM_ID)) {
                urlString.append(arguments.get(PARAM_ID));
                arguments.remove(PARAM_ID);
            }

            // Append the suffix of the API URL
            if (StringUtils.endsWith(urlString, "/") && submethod.startsWith("/")) {
                urlString.deleteCharAt(urlString.length() - 1);
            }
            urlString.append(submethod);

            // Append the key information
            urlString.append(DELIMITER_FIRST).append(PARAM_API_KEY);
            urlString.append(apiKey);
        }

        for (Map.Entry<String, String> argEntry : arguments.entrySet()) {
            urlString.append(DELIMITER_SUBSEQUENT).append(argEntry.getKey());
            urlString.append(argEntry.getValue());
        }

        try {
            LOG.trace("URL: {}", urlString.toString());
            return new URL(urlString.toString());
        } catch (MalformedURLException ex) {
            LOG.warn("Failed to create URL {} - {}", urlString.toString(), ex.toString());
            return null;
        } finally {
            arguments.clear();
        }
    }

    /**
     * Add arguments individually
     *
     * @param key
     * @param value
     */
    public void addArgument(String key, String value) {
        arguments.put(key, value);
    }

    /**
     * Add arguments individually
     *
     * @param key
     * @param value
     */
    public void addArgument(String key, int value) {
        arguments.put(key, Integer.toString(value));
    }

    /**
     * Add arguments individually
     *
     * @param key
     * @param value
     */
    public void addArgument(String key, boolean value) {
        arguments.put(key, Boolean.toString(value));
    }

    /**
     * Add arguments individually
     *
     * @param key
     * @param value
     */
    public void addArgument(String key, float value) {
        arguments.put(key, Float.toString(value));
    }

    /**
     * Clear the arguments
     */
    public void clearArguments() {
        arguments.clear();
    }

    /**
     * Set the arguments directly
     *
     * @param args
     */
    public void setArguments(Map<String, String> args) {
        arguments.putAll(args);
    }

    /**
     * Append any optional parameters to the URL
     *
     * @param appendToResponse
     */
    public void appendToResponse(String[] appendToResponse) {
        if (appendToResponse.length > 0) {
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
            addArgument(APPEND_TO_RESPONSE, sb.toString());
        }
    }
}
