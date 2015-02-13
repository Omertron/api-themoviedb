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

    private static final Logger LOG = LoggerFactory.getLogger(ApiUrl.class);
    // TheMovieDbApi API Base URL
    private static final String TMDB_API_BASE = "http://api.themoviedb.org/3/";
    // Parameter configuration
    private static final String DELIMITER_FIRST = "?";
    private static final String DELIMITER_SUBSEQUENT = "&";
    private static final String DEFAULT_STRING = "";
    // Properties
    private final String apiKey;
    private final String method;
    private String submethod;
    private final Map<String, String> arguments = new HashMap<String, String>();

    /**
     * Constructor for the simple API URL method without a sub-method
     *
     * @param apiKey
     * @param method
     */
    public ApiUrl(String apiKey, String method) {
        this.apiKey = apiKey;
        this.method = method;
    }

    /**
     * Add a sub-methods
     *
     * @param submethod
     * @return
     */
    public ApiUrl setSubMethod(String submethod) {
        this.submethod = submethod;
        return this;
    }

    /**
     * Add a sub-method that has an ID at the start
     *
     * @param someId
     * @param submethod
     * @return
     */
    public ApiUrl setSubMethod(int someId, String submethod) {
        this.submethod = someId + submethod;
        return this;
    }

    /**
     * Add a sub-method that has an ID at the start
     *
     * @param someId
     * @param submethod
     * @return
     */
    public ApiUrl setSubMethod(String someId, String submethod) {
        this.submethod = someId + submethod;
        return this;
    }

    /**
     * Build the URL with the default parameters
     *
     * @return
     */
    public URL buildUrl() {
        return buildUrl(new TmdbParameters());
    }

    /**
     * Build the URL from the pre-created parameters.
     *
     * @param params
     * @return
     */
    public URL buildUrl(TmdbParameters params) {
        StringBuilder urlString = new StringBuilder(TMDB_API_BASE);

        // Get the start of the URL
        urlString.append(method);

        // We have either a queury, or a direct request
        if (params.has(Param.QUERY)) {
            // Append the suffix of the API URL
            if (StringUtils.endsWith(urlString, "/") && submethod.startsWith("/")) {
                urlString.deleteCharAt(urlString.length() - 1);
            }
            urlString.append(submethod);

            // Append the key information
            urlString.append(DELIMITER_FIRST).append(Param.API_KEY.getValue());
            urlString.append(apiKey);

            // Append the search term
            urlString.append(DELIMITER_SUBSEQUENT);
            urlString.append(Param.QUERY.getValue());

            String query = (String) params.get(Param.QUERY);

            try {
                urlString.append(URLEncoder.encode(query, "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                LOG.trace("Unable to encode query: '{}' trying raw.", query, ex);
                // If we can't encode it, try it raw
                urlString.append(query);
            }

            // Remove the query from the arguments so it is not added later
            params.remove(Param.QUERY);
        } else {
            // Append the ID if provided
            if (params.has(Param.ID)) {
                urlString.append(params.get(Param.ID));
                params.remove(Param.ID);
            }

            // Append the suffix of the API URL
            if (StringUtils.endsWith(urlString, "/") && submethod.startsWith("/")) {
                urlString.deleteCharAt(urlString.length() - 1);
            }
            urlString.append(submethod);

            // Append the key information
            urlString.append(DELIMITER_FIRST).append(Param.API_KEY);
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
}
