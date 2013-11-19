/*
 *      Copyright (c) 2004-2013 Stuart Boston
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
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.Configuration;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.wrapper.WrapperConfig;
import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the Account Methods
 *
 * @author stuart.boston
 */
public class TmdbConfiguration extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbConfiguration.class);
    // API URL Parameters
    private static final String BASE_CONFIG = "configuration/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbConfiguration(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * Get the configuration
     *
     * @return
     * @throws MovieDbException
     */
    public Configuration getConfig() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_CONFIG);
        URL configUrl = apiUrl.buildUrl();
        String webpage = requestWebPage(configUrl);

        Configuration tmdbConfig = null;
        try {
            WrapperConfig wc = mapper.readValue(webpage, WrapperConfig.class);
            tmdbConfig = wc.getTmdbConfiguration();
        } catch (IOException ex) {
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, "Failed to read configuration", ex);
        }

        return tmdbConfig;
    }

}
