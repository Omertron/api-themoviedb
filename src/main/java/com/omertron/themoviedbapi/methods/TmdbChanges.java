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
import com.omertron.themoviedbapi.model.change.ChangeListItem;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.results.WrapperGenericList;
import java.net.URL;

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
     * You can then use the movie/TV/person changes API to get the actual data that has been changed.
     *
     * @param method The method base to get
     * @param page
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return List of changed movie
     * @throws MovieDbException
     */
    public ResultList<ChangeListItem> getChangeList(MethodBase method, Integer page, String startDate, String endDate) throws MovieDbException {
        TmdbParameters params = new TmdbParameters();
        params.add(Param.PAGE, page);
        params.add(Param.START_DATE, startDate);
        params.add(Param.END_DATE, endDate);

        URL url = new ApiUrl(apiKey, method).subMethod(MethodSub.CHANGES).buildUrl(params);
        WrapperGenericList<ChangeListItem> wrapper = processWrapper(getTypeReference(ChangeListItem.class), url, "changes");
        return wrapper.getResultsList();
    }

}
