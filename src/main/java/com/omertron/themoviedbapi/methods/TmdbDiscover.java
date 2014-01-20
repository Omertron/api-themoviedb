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
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.model.tv.TVSeriesBasic;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.wrapper.movie.WrapperMovie;
import com.omertron.themoviedbapi.wrapper.tv.WrapperTVSeries;
import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the Discover methods
 *
 * @author stuart.boston
 */
public class TmdbDiscover extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbDiscover.class);
    // API URL Parameters
    private static final String BASE_DISCOVER = "discover/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbDiscover(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * Discover movies by different types of data like average rating, number of votes, genres and certifications.
     *
     * @param discover A discover object containing the search criteria required
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getDiscoverMovie(Discover discover) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_DISCOVER, "/movie");

        apiUrl.setArguments(discover.getParams());

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());

            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get discover list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Discover TV shows by different types of data like average rating, number of votes, genres, the network they aired on and air
     * dates.
     *
     * @param discover A discover object containing the search criteria required
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<TVSeriesBasic> getDiscoverTv(Discover discover) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_DISCOVER, "/tv");

        apiUrl.setArguments(discover.getParams());

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperTVSeries wrapper = MAPPER.readValue(webpage, WrapperTVSeries.class);
            TmdbResultsList<TVSeriesBasic> results = new TmdbResultsList<TVSeriesBasic>(wrapper.getSeries());

            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get discover list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
}
