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

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.ChangedMedia;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperMediaChanges;
import java.io.IOException;
import java.net.URL;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the Change Methods
 *
 * @author stuart.boston
 */
public class TmdbChanges extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbChanges(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Get a list of Media IDs that have been edited.
     *
     * You can then use the movie/TV/person changes API to get the actual data
     * that has been changed.
     *
     * @param method The method base to get
     * @param page
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return List of changed movie
     * @throws MovieDbException
     */
    public TmdbResultsList<ChangedMedia> getChangeList(MethodBase method, int page, String startDate, String endDate) throws MovieDbException {
        TmdbParameters params = new TmdbParameters();
        params.add(Param.PAGE, page);
        params.add(Param.START_DATE, startDate);
        params.add(Param.END_DATE, endDate);

        URL url = new ApiUrl(apiKey, method).setSubMethod(MethodSub.CHANGES).buildUrl(params);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMediaChanges wrapper = MAPPER.readValue(webpage, WrapperMediaChanges.class);

            TmdbResultsList<ChangedMedia> results = new TmdbResultsList<ChangedMedia>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get changes", url, ex);
        }
    }

    /**
     * Get the changes for a specific movie id.
     *
     * @param id
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList getMovieChanges(int id, String startDate, String endDate) throws MovieDbException {
        LOG.trace("Movie Changes: id: {}, start: {}, end: {}", id, startDate, endDate);
        throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }

    /**
     * Get the changes for a specific TV Show
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList getTvChanges(int id, String startDate, String endDate) throws MovieDbException {
        LOG.trace("TV Changes: id: {}, start: {}, end: {}", id, startDate, endDate);
        throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }

    /**
     * Get the changes for a specific person id.
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList getPersonChanges(int id, String startDate, String endDate) throws MovieDbException {
        LOG.trace("Person Changes: id: {}, start: {}, end: {}", id, startDate, endDate);
        throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Not implemented yet", "");
    }
}
