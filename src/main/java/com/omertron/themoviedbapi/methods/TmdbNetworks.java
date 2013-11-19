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
import static com.omertron.themoviedbapi.methods.AbstractMethod.MAPPER;
import com.omertron.themoviedbapi.model.tv.Network;
import com.omertron.themoviedbapi.tools.ApiUrl;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_ID;
import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the Networks methods
 *
 * @author stuart.boston
 */
public class TmdbNetworks extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbNetworks.class);
    // API URL Parameters
    private static final String BASE_NETWORK = "network/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbNetworks(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * This method is used to retrieve the basic information about a TV network.
     * <p>
     * You can use this ID to search for TV shows with the discover method.
     *
     * @param networkId
     * @return
     * @throws MovieDbException
     */
    public Network getNetworkInfo(int networkId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_NETWORK);

        apiUrl.addArgument(PARAM_ID, networkId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return MAPPER.readValue(webpage, Network.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get network information: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
}
