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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
    // Properties
    private final String apiKey;
    private final MethodBase method;
    private MethodSub submethod = MethodSub.NONE;
    private static final List<Param> IGNORE_PARAMS = new ArrayList<>();

    static {
        IGNORE_PARAMS.add(Param.ID);
        IGNORE_PARAMS.add(Param.QUERY);
        IGNORE_PARAMS.add(Param.SEASON_NUMBER);
        IGNORE_PARAMS.add(Param.EPISODE_NUMBER);
    }

    /**
     * Constructor for the simple API URL method without a sub-method
     *
     * @param apiKey
     * @param method
     */
    public ApiUrl(String apiKey, MethodBase method) {
        this.apiKey = apiKey;
        this.method = method;
    }

    /**
     * Add a sub-methods
     *
     * @param submethod
     * @return
     */
    public ApiUrl subMethod(MethodSub submethod) {
        if (submethod != MethodSub.NONE) {
            this.submethod = submethod;
        }
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
    public URL buildUrl(final TmdbParameters params) {
        StringBuilder urlString = new StringBuilder(TMDB_API_BASE);

        LOG.trace("Method: '{}', Sub-method: '{}', Params: {}", method.getValue(), submethod.getValue(),
                ToStringBuilder.reflectionToString(params, ToStringStyle.SHORT_PREFIX_STYLE));

        // Get the start of the URL, substituting TV for the season or episode methods
        if (method == MethodBase.SEASON || method == MethodBase.EPISODE) {
            urlString.append(MethodBase.TV.getValue());
        } else {
            urlString.append(method.getValue());
        }

        // We have either a queury, or a ID request
        if (params.has(Param.QUERY)) {
            urlString.append(queryProcessing(params));
        } else {
            urlString.append(idProcessing(params));
        }

        urlString.append(otherProcessing(params));

        try {
            LOG.trace("URL: {}", urlString.toString());
            return new URL(urlString.toString());
        } catch (MalformedURLException ex) {
            LOG.warn("Failed to create URL {} - {}", urlString.toString(), ex.getMessage());
            return null;
        }
    }

    /**
     * Create the query based URL portion
     *
     * @param params
     * @return
     */
    private StringBuilder queryProcessing(TmdbParameters params) {
        StringBuilder urlString = new StringBuilder();

        // Append the suffix of the API URL
        if (submethod != MethodSub.NONE) {
            urlString.append("/").append(submethod.getValue());
        }

        // Append the key information
        urlString.append(DELIMITER_FIRST)
                .append(Param.API_KEY.getValue())
                .append(apiKey);

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

        return urlString;
    }

    /**
     * Create the ID based URL portion
     *
     * @param params
     * @return
     */
    private StringBuilder idProcessing(final TmdbParameters params) {
        StringBuilder urlString = new StringBuilder();

        // Append the ID
        if (params.has(Param.ID)) {
            urlString.append("/").append(params.get(Param.ID));
        }

        if (params.has(Param.SEASON_NUMBER)) {
            urlString.append("/season/").append(params.get(Param.SEASON_NUMBER));
        }

        if (params.has(Param.EPISODE_NUMBER)) {
            urlString.append("/episode/").append(params.get(Param.EPISODE_NUMBER));
        }

        if (submethod != MethodSub.NONE) {
            urlString.append("/").append(submethod.getValue());
        }

        // Append the key information
        urlString.append(DELIMITER_FIRST)
                .append(Param.API_KEY.getValue())
                .append(apiKey);

        return urlString;
    }

    /**
     * Create a string of the remaining parameters
     *
     * @param params
     * @return
     */
    private StringBuilder otherProcessing(final TmdbParameters params) {
        StringBuilder urlString = new StringBuilder();

        for (Map.Entry<Param, String> argEntry : params.getEntries()) {
            // Skip the ID an QUERY params
            if (IGNORE_PARAMS.contains(argEntry.getKey())) {
                continue;
            }

            urlString.append(DELIMITER_SUBSEQUENT)
                    .append(argEntry.getKey().getValue())
                    .append(argEntry.getValue());
        }
        return urlString;
    }
}
