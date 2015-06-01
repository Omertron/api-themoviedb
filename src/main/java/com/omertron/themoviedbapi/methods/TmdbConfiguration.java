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
package com.omertron.themoviedbapi.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import com.omertron.themoviedbapi.MovieDbException;
import static com.omertron.themoviedbapi.methods.AbstractMethod.MAPPER;
import com.omertron.themoviedbapi.model.config.Configuration;
import com.omertron.themoviedbapi.model.config.JobDepartment;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.results.ResultsMap;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.results.WrapperConfig;
import com.omertron.themoviedbapi.results.WrapperJobList;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the Configuration Methods
 *
 * @author stuart.boston
 */
public class TmdbConfiguration extends AbstractMethod {

    /**
     * Cache the configuration in memory<br/>
     * It rarely changes, so this should be safe.
     */
    private static Configuration config = null;

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbConfiguration(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Get the configuration<br/>
     * If the configuration has been previously retrieved, use that instead
     *
     * @return
     * @throws MovieDbException
     */
    public Configuration getConfig() throws MovieDbException {
        if (config == null) {
            URL configUrl = new ApiUrl(apiKey, MethodBase.CONFIGURATION).buildUrl();
            String webpage = httpTools.getRequest(configUrl);

            try {
                WrapperConfig wc = MAPPER.readValue(webpage, WrapperConfig.class);
                config = wc.getTmdbConfiguration();
            } catch (IOException ex) {
                throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to read configuration", configUrl, ex);
            }
        }
        return config;
    }

    /**
     * Get a list of valid jobs
     *
     * @return
     * @throws MovieDbException
     */
    public ResultList<JobDepartment> getJobs() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.JOB).subMethod(MethodSub.LIST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            WrapperJobList wrapper = MAPPER.readValue(webpage, WrapperJobList.class);
            ResultList<JobDepartment> results = new ResultList<>(wrapper.getJobs());
            wrapper.setResultProperties(results);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get job list", url, ex);
        }
    }

    /**
     * Get the list of supported timezones for the API methods that support them
     *
     * @return
     * @throws MovieDbException
     */
    public ResultsMap<String, List<String>> getTimezones() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.TIMEZONES).subMethod(MethodSub.LIST).buildUrl();
        String webpage = httpTools.getRequest(url);

        List<Map<String, List<String>>> tzList;
        try {
            tzList = MAPPER.readValue(webpage, new TypeReference<List<Map<String, List<String>>>>() {
            });
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get timezone list", url, ex);
        }

        ResultsMap<String, List<String>> timezones = new ResultsMap<>();

        for (Map<String, List<String>> tzMap : tzList) {
            for (Map.Entry<String, List<String>> x : tzMap.entrySet()) {
                timezones.put(x.getKey(), x.getValue());
            }
        }

        return timezones;
    }

}
