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
import com.omertron.themoviedbapi.model.JobDepartment;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.wrapper.WrapperJobList;
import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the Jobs methods
 *
 * @author stuart.boston
 */
public class TmdbJobs extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbJobs.class);
    // API URL Parameters
    private static final String BASE_JOB = "job/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbJobs(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * Get a list of valid jobs.
     *
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<JobDepartment> getJobs() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_JOB, "/list");

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperJobList wrapper = MAPPER.readValue(webpage, WrapperJobList.class);
            TmdbResultsList<JobDepartment> results = new TmdbResultsList<JobDepartment>(wrapper.getJobs());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get job list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

}
