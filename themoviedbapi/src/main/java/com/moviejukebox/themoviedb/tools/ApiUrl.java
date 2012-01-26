/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.themoviedb.tools;

import com.moviejukebox.themoviedb.TheMovieDB;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * The API URL that is used to construct the API call
 *
 * @author Stuart
 */
public class ApiUrl {

    /*
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(ApiUrl.class);
    /*
     * Parameter configuration
     */
    private static final String DELIMITER_FIRST = "?";
    private static final String DELIMITER_SUBSEQUENT = "&";
    private static final String PARAMETER_API_KEY = "api_key="; // The API Key is always needed and always first
    private static final String PARAMETER_QUERY = "query=";
    private static final String PARAMETER_LANGUAGE = DELIMITER_SUBSEQUENT + "language=";
    private static final String PARAMETER_COUNTRY = DELIMITER_SUBSEQUENT + "country=";
    private static final String PARAMETER_PAGE = DELIMITER_SUBSEQUENT + "page=";
    private static final String DEFAULT_QUERY = "";
    private static final int DEFAULT_ID = -1;
    private static final int IMDB_ID_TRIGGER = 0;   // Use to determine its an IMDB search
    private static final String DEFAULT_LANGUAGE = "";
    private static final String DEFAULT_COUNTRY = "";
    private static final int DEFAULT_PAGE = -1;
    /*
     * Properties
     */
    private String method;
    private String submethod;

    //<editor-fold defaultstate="collapsed" desc="Constructor Methods">
    public ApiUrl(String method) {
        this.method = method;
        this.submethod = DEFAULT_QUERY;
    }

    public ApiUrl(String method, String submethod) {
        this.method = method;
        this.submethod = submethod;
    }
    //</editor-fold>

    /**
     * Create the full URL with the API.
     *
     * @param query
     * @param tmdbId
     * @param language
     * @param country
     * @param page
     * @return
     */
    private URL getFullUrl(String query, int tmdbId, String imdbId, String language, String country, int page) {
        StringBuilder urlString = new StringBuilder(TheMovieDB.getApiBase());

        // Get the start of the URL
        urlString.append(method);

        // Append the search term if required
        if (StringUtils.isNotBlank(query)) {
            urlString.append(DELIMITER_FIRST);
            urlString.append(PARAMETER_QUERY);

            try {
                urlString.append(URLEncoder.encode(query, "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                // If we can't encode it, try it raw
                urlString.append(query);
            }
        }

        // Append the ID if provided
        if (tmdbId > DEFAULT_ID) {
            urlString.append(tmdbId);
        }
        
        // Append the IMDB ID if provided
        if (StringUtils.isNotBlank(imdbId)) {
            urlString.append(imdbId);
        }

        // Append the suffix of the API URL
        urlString.append(submethod);

        // Append the key information
        if (StringUtils.isBlank(query)) {
            // This is the first parameter
            urlString.append(DELIMITER_FIRST);
        } else {
            // The first parameter was the query
            urlString.append(DELIMITER_SUBSEQUENT);
        }
        urlString.append(PARAMETER_API_KEY);
        urlString.append(TheMovieDB.getApiKey());

        // Append the language to the URL
        if (StringUtils.isNotBlank(language)) {
            urlString.append(PARAMETER_LANGUAGE);
            urlString.append(language);
        }

        // Append the country to the URL
        if (StringUtils.isNotBlank(country)) {
            urlString.append(PARAMETER_COUNTRY);
            urlString.append(country);
        }

        // Append the page to the URL
        if (page > DEFAULT_PAGE) {
            urlString.append(PARAMETER_PAGE);
            urlString.append(page);
        }

        try {
            LOGGER.trace("URL: " + urlString.toString());
            return new URL(urlString.toString());
        } catch (MalformedURLException ex) {
            LOGGER.warn("Failed to create URL " + urlString.toString());
            return null;
        }
    }

    /**
     * Create an URL using a query (string) and optional language and page
     * @param query
     * @param language
     * @param page
     * @return
     */
    public URL getQueryUrl(String query, String language, int page) {
        return getFullUrl(query, DEFAULT_ID, DEFAULT_QUERY, language, null, page);
    }

    public URL getQueryUrl(String query) {
        return getQueryUrl(query, DEFAULT_LANGUAGE, DEFAULT_PAGE);
    }

    public URL getQueryUrl(String query, String language) {
        return getQueryUrl(query, language, DEFAULT_PAGE);
    }

    /**
     * Create an URL using the TheMovieDB ID and optional language an country codes
     * @param tmdbId
     * @param language
     * @param country
     * @return
     */
    public URL getIdUrl(int tmdbId, String language, String country) {
        return getFullUrl(DEFAULT_QUERY, tmdbId, DEFAULT_QUERY, language, country, DEFAULT_PAGE);
    }

    public URL getIdUrl(int tmdbId) {
        return getIdUrl(tmdbId, DEFAULT_LANGUAGE, DEFAULT_COUNTRY);
    }

    public URL getIdUrl(int tmdbId, String language) {
        return getIdUrl(tmdbId, language, DEFAULT_COUNTRY);
    }
    
    /**
     * Get the movie info for an IMDB ID.
     * Note, this is a special case
     * @param imdbId
     * @param language
     * @return 
     */
    public URL getIdUrl(String imdbId, String language) {
        return getFullUrl(DEFAULT_QUERY, DEFAULT_ID, imdbId, language, DEFAULT_COUNTRY, DEFAULT_PAGE);
    }

}
