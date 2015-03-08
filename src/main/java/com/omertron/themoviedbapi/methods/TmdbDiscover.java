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
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.results.WrapperGenericList;
import java.net.URL;

/**
 * Class to hold the Discover Methods
 *
 * @author stuart.boston
 */
public class TmdbDiscover extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbDiscover(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Discover movies by different types of data like average rating, number of votes, genres and certifications.
     *
     * @param discover A discover object containing the search criteria required
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieBasic> getDiscoverMovies(Discover discover) throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.DISCOVER).subMethod(MethodSub.MOVIE).buildUrl(discover.getParams());
        String webpage = httpTools.getRequest(url);
        WrapperGenericList<MovieBasic> wrapper = processWrapper(getTypeReference(MovieBasic.class), url, webpage);
        return wrapper.getResultsList();
    }

    /**
     * Discover movies by different types of data like average rating, number of votes, genres and certifications.
     *
     * @param discover A discover object containing the search criteria required
     * @return
     * @throws MovieDbException
     */
    public ResultList<TVBasic> getDiscoverTV(Discover discover) throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.DISCOVER).subMethod(MethodSub.TV).buildUrl(discover.getParams());
        String webpage = httpTools.getRequest(url);
        WrapperGenericList<TVBasic> wrapper = processWrapper(getTypeReference(TVBasic.class), url, webpage);
        return wrapper.getResultsList();
    }
}
