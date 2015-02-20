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
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.model.Account;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.model.MovieDbList;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.PostBody;
import com.omertron.themoviedbapi.tools.PostTools;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperMovie;
import com.omertron.themoviedbapi.wrapper.WrapperMovieDbList;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the Account Methods
 *
 * @author stuart.boston
 */
public class TmdbAccount extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbAccount(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Get the basic information for an account. You will need to have a valid
     * session id.
     *
     * @param sessionId
     * @return
     * @throws MovieDbException
     */
    public Account getAccount(String sessionId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);

        URL url = new ApiUrl(apiKey, MethodBase.ACCOUNT).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, Account.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get Account", url, ex);
        }
    }

    /*
     /account/{id}/lists Get the lists that you have created and marked as a favorite.
     /account/{id}/favorite/movies Get the list of favorite movies for an account.
     /account/{id}/favorite/tv Get the list of favorite TV series for an account
     /account/{id}/favorite Add or remove a movie to an accounts favorite list
     /account/{id}/rated/movies Get the list of rated movies (and associated rating) for an account
     /account/{id}/rated/tv Get the list of rated TV shows (and associated rating) for an account.
     /account/{id}/watchlist/movies Get the list of movies on an accounts watchlist
     /account/{id}/watchlist/tv Get the list of TV series on an accounts watchlist
     /account/{id}/watchlist Add or remove a movie to an accounts watch list
     */
    /**
     * Get all lists of a given user
     *
     * @param sessionId
     * @param accountId
     * @return The lists
     * @throws MovieDbException
     */
    public List<MovieDbList> getUserLists(String sessionId, int accountId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);
        parameters.add(Param.ID, accountId);

        URL url = new ApiUrl(apiKey, MethodBase.ACCOUNT).setSubMethod(MethodSub.LISTS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, WrapperMovieDbList.class).getLists();
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get user list", url, ex);
        }
    }

    /**
     * Get the list of favorite movies for an account.
     *
     * @param sessionId
     * @param accountId
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> getFavoriteMovies(String sessionId, int accountId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);
        parameters.add(Param.ID, accountId);

        URL url = new ApiUrl(apiKey, MethodBase.ACCOUNT).setSubMethod(MethodSub.FAVORITE_MOVIES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, WrapperMovie.class).getMovies();
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get favorite movies", url, ex);
        }
    }

    /**
     * Get the list of favorite TV series for an account.
     *
     * @param sessionId
     * @param accountId
     * @return
     * @throws MovieDbException
     */
    public List getFavoriteTv(String sessionId, int accountId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);
        parameters.add(Param.ID, accountId);

        URL url = new ApiUrl(apiKey, MethodBase.ACCOUNT).setSubMethod(MethodSub.FAVORITE_TV).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }

    /**
     * Add or remove a movie to an accounts favorite list.
     *
     * @param sessionId
     * @param accountId
     * @param mediaType
     * @param mediaId
     * @param isFavorite
     * @return
     * @throws MovieDbException
     */
    public StatusCode changeFavoriteStatus(String sessionId, int accountId, Integer mediaId, MediaType mediaType, boolean isFavorite) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);
        parameters.add(Param.ID, accountId);

        String jsonBody = new PostTools()
                .add(PostBody.MEDIA_TYPE, mediaType.toString().toLowerCase())
                .add(PostBody.MEDIA_ID, mediaId)
                .add(PostBody.FAVORITE, isFavorite)
                .build();

        URL url = new ApiUrl(apiKey, MethodBase.ACCOUNT).setSubMethod(MethodSub.FAVORITE).buildUrl(parameters);
        String webpage = httpTools.postRequest(url, jsonBody);

        try {
            return MAPPER.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get favorite status", url, ex);
        }
    }

    /**
     * Get the list of rated movies (and associated rating) for an account.
     *
     * @param sessionId
     * @param accountId
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> getRatedMovies(String sessionId, int accountId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);
        parameters.add(Param.ID, accountId);

        URL url = new ApiUrl(apiKey, MethodBase.ACCOUNT).setSubMethod(MethodSub.RATED_MOVIES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, WrapperMovie.class).getMovies();
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get rated movies", url, ex);
        }
    }

    /**
     * Get the list of rated TV shows (and associated rating) for an account.
     *
     * @param sessionId
     * @param accountId
     * @return
     * @throws MovieDbException
     */
    public List getRatedTV(String sessionId, int accountId) throws MovieDbException {
        throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }

    /**
     * Get the list of movies on an accounts watch list.
     *
     * @param sessionId
     * @param accountId
     * @return The watch list of the user
     * @throws MovieDbException
     */
    public List<MovieDb> getWatchListMovie(String sessionId, int accountId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);
        parameters.add(Param.ID, accountId);

        URL url = new ApiUrl(apiKey, MethodBase.ACCOUNT).setSubMethod(MethodSub.WATCHLIST_MOVIES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, WrapperMovie.class).getMovies();
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get Movie watch list", url, ex);
        }
    }

    /**
     * Get the list of movies on an accounts watch list.
     *
     * @param sessionId
     * @param accountId
     * @return The watch list of the user
     * @throws MovieDbException
     */
    public List getWatchListTV(String sessionId, int accountId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);
        parameters.add(Param.ID, accountId);

        URL url = new ApiUrl(apiKey, MethodBase.ACCOUNT).setSubMethod(MethodSub.WATCHLIST_TV).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, WrapperMovie.class).getMovies();
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get TV watch list", url, ex);
        }
    }

    /**
     * Add or remove a movie to an accounts watch list.
     *
     * @param sessionId
     * @param accountId
     * @param movieId
     * @param mediaType
     * @param addToWatchlist
     * @return
     * @throws MovieDbException
     */
    public StatusCode modifyWatchList(String sessionId, int accountId, Integer movieId, MediaType mediaType, boolean addToWatchlist) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);
        parameters.add(Param.ID, accountId);

        String jsonBody = new PostTools()
                .add(PostBody.MEDIA_TYPE, mediaType.toString().toLowerCase())
                .add(PostBody.MEDIA_ID, movieId)
                .add(PostBody.WATCHLIST, addToWatchlist)
                .build();

        URL url = new ApiUrl(apiKey, MethodBase.ACCOUNT).setSubMethod(MethodSub.WATCHLIST).buildUrl(parameters);
        String webpage = httpTools.postRequest(url, jsonBody);

        try {
            return MAPPER.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to modify watch list", url, ex);
        }
    }
}
