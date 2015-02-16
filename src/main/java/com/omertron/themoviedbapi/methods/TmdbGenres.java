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
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperGenres;
import com.omertron.themoviedbapi.wrapper.WrapperMovie;
import java.io.IOException;
import java.net.URL;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the Genre Methods
 *
 * @author stuart.boston
 */
public class TmdbGenres extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbGenres(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * You can use this method to retrieve the list of genres used on TMDb.
     *
     * These IDs will correspond to those found in movie calls.
     *
     * @param language
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Genre> getGenreList(String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.GENRE).setSubMethod(MethodSub.LIST).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperGenres wrapper = MAPPER.readValue(webpage, WrapperGenres.class);
            TmdbResultsList<Genre> results = new TmdbResultsList<Genre>(wrapper.getGenres());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get genre list", url, ex);
        }
    }

    /**
     * Get a list of movies per genre.
     *
     * It is important to understand that only movies with more than 10 votes
     * get listed.
     *
     * This prevents movies from 1 10/10 rating from being listed first and for
     * the first 5 pages.
     *
     * @param genreId
     * @param language
     * @param page
     * @param includeAllMovies
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getGenreMovies(int genreId, String language, int page, boolean includeAllMovies) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, genreId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.INCLUDE_ALL_MOVIES, includeAllMovies);

        URL url = new ApiUrl(apiKey, MethodBase.GENRE).setSubMethod(MethodSub.MOVIES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get genre movie list", url, ex);
        }
    }
}
