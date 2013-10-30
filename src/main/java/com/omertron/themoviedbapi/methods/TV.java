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
import com.omertron.themoviedbapi.MovieDbException.MovieDbExceptionType;
import com.omertron.themoviedbapi.model.person.Person;
import com.omertron.themoviedbapi.model.tv.TVSeries;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.omertron.themoviedbapi.tools.ApiUrl.*;
import com.omertron.themoviedbapi.wrapper.person.WrapperCasts;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the TV methods
 *
 * @author stuart.boston
 */
public class TV extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TV.class);
    // API URL Parameters
    private static final String BASE_TV = "tv/";

    public TV(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * Get the primary information about a TV series by id.
     *
     * @param id
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TVSeries getTv(int id, String language, String[] appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV);

        apiUrl.addArgument(PARAM_ID, id);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();

        String webpage = requestWebPage(url);
        try {
            TVSeries series = mapper.readValue(webpage, TVSeries.class);
            return series;
        } catch (IOException ex) {
            LOG.warn("Failed to find series: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }

    public TmdbResultsList<Person> getTvCredits(int id, String language, String[] appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV, "/credits");

        apiUrl.addArgument(PARAM_ID, id);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperCasts wrapper = mapper.readValue(webpage, WrapperCasts.class);
            TmdbResultsList<Person> results = new TmdbResultsList<Person>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get TV Credis: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }
}
