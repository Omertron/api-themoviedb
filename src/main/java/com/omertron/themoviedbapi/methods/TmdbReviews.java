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
import static com.omertron.themoviedbapi.methods.AbstractMethod.MAPPER;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_ID;
import com.omertron.themoviedbapi.model.Review;
import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the Reviews methods
 *
 * @author stuart.boston
 */
public class TmdbReviews extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbReviews.class);
    // API URL Parameters
    private static final String BASE_REVIEW = "review/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbReviews(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * Get the full details of a review by ID.
     *
     * @param reviewId
     * @return
     * @throws MovieDbException
     */
    public Review getReview(String reviewId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_REVIEW);
        apiUrl.addArgument(PARAM_ID, reviewId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return MAPPER.readValue(webpage, Review.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get reviews: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }
}
