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
import static com.omertron.themoviedbapi.methods.AbstractMethod.mapper;
import com.omertron.themoviedbapi.model.person.PersonCredits;
import com.omertron.themoviedbapi.model.person.PersonMovieCredits;
import com.omertron.themoviedbapi.tools.ApiUrl;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_ID;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_LANGUAGE;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

public class TmdbCredits extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbCredits.class);
    // API URL Parameters
    private static final String BASE_CREDIT = "/credit";

    public TmdbCredits(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * Get the detailed information about a particular credit record.
     * <p>
     * This is currently only supported with the new credit model found in TV. <br/>
     * These IDs can be found from any TV credit response as well as the TV_credits and combined_credits methods for people. <br/>
     * The episodes object returns a list of episodes and are generally going to be guest stars. <br/>
     * The season array will return a list of season numbers. <br/>
     * Season credits are credits that were marked with the "add to every season" option in the editing interface and are assumed to
     * be "season regulars".
     *
     * @param creditId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public PersonCredits getCreditInfo(String creditId, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_CREDIT, "/movie_credits");

        apiUrl.addArgument(PARAM_ID, creditId);
        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            PersonCredits pc = mapper.readValue(webpage, PersonCredits.class);
            return pc;
        } catch (IOException ex) {
            LOG.warn("Failed to get credit info: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
}
