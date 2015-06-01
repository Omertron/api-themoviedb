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
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.results.WrapperGenericList;
import com.omertron.themoviedbapi.results.WrapperGenres;
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
     * Get the list of movie genres.
     *
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<Genre> getGenreMovieList(String language) throws MovieDbException {
        return getGenreList(language, MethodSub.MOVIE_LIST);
    }

    /**
     * Get the list of TV genres.
     *
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<Genre> getGenreTVList(String language) throws MovieDbException {
        return getGenreList(language, MethodSub.TV_LIST);
    }

    /**
     * Get the list of genres for movies or TV
     *
     * @param language
     * @param sub
     * @return
     * @throws MovieDbException
     */
    private ResultList<Genre> getGenreList(String language, MethodSub sub) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.GENRE).subMethod(sub).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperGenres wrapper = MAPPER.readValue(webpage, WrapperGenres.class);
            ResultList<Genre> results = new ResultList<>(wrapper.getGenres());
            wrapper.setResultProperties(results);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get genre " + sub.toString(), url, ex);
        }
    }

    /**
     * Get the list of movies for a particular genre by id.
     *
     * By default, only movies with 10 or more votes are included.
     *
     * @param genreId
     * @param language
     * @param page
     * @param includeAllMovies
     * @param includeAdult
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieBasic> getGenreMovies(int genreId, String language, Integer page, Boolean includeAllMovies, Boolean includeAdult) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, genreId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.INCLUDE_ALL_MOVIES, includeAllMovies);
        parameters.add(Param.INCLUDE_ADULT, includeAdult);

        URL url = new ApiUrl(apiKey, MethodBase.GENRE).subMethod(MethodSub.MOVIES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        WrapperGenericList<MovieBasic> wrapper = processWrapper(getTypeReference(MovieBasic.class), url, webpage);
        return wrapper.getResultsList();
    }
}
