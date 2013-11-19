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
import com.omertron.themoviedbapi.model.Account;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.model.movie.MovieDbList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_ID;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_SESSION;
import com.omertron.themoviedbapi.wrapper.movie.WrapperMovie;
import com.omertron.themoviedbapi.wrapper.movie.WrapperMovieDbList;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the Account Methods
 *
 * @author stuart.boston
 */
public class TmdbAccount extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbAccount.class);
    // API URL Parameters
    private static final String BASE_ACCOUNT = "account/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbAccount(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * Get the basic information for an account. You will need to have a valid session id.
     *
     * @param sessionId
     * @return
     * @throws MovieDbException
     */
    public Account getAccount(String sessionId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT.replace("/", ""));

        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, Account.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get Session Token: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get all lists of a given user
     *
     * @param sessionId
     * @param accountId
     * @return The lists
     * @throws MovieDbException
     */
    public List<MovieDbList> getUserLists(String sessionId, int accountId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, "/lists");
        apiUrl.addArgument(PARAM_ID, accountId);
        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, WrapperMovieDbList.class).getLists();
        } catch (IOException ex) {
            LOG.warn("Failed to get lists: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, accountId + "/favorite_movies");
        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, WrapperMovie.class).getMovies();
        } catch (IOException ex) {
            LOG.warn("Failed to get favorite movies: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Add or remove a movie to an accounts favorite list.
     *
     * @param sessionId
     * @param accountId
     * @param movieId
     * @param isFavorite
     * @return
     * @throws MovieDbException
     */
    public StatusCode changeFavoriteStatus(String sessionId, int accountId, Integer movieId, boolean isFavorite) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, accountId + "/favorite");

        apiUrl.addArgument(PARAM_SESSION, sessionId);

        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("movie_id", movieId);
        body.put("favorite", isFavorite);
        String jsonBody = convertToJson(body);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url, jsonBody);

        try {
            return mapper.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            LOG.warn("Failed to change favorite movies: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, accountId + "/rated_movies");
        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, WrapperMovie.class).getMovies();
        } catch (IOException ex) {
            LOG.warn("Failed to get rated movies: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
    public List<MovieDb> getWatchList(String sessionId, int accountId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, accountId + "/movie_watchlist");
        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, WrapperMovie.class).getMovies();
        } catch (IOException ex) {
            LOG.warn("Failed to get watch list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Add or remove a movie to an accounts watch list.
     *
     * @param sessionId
     * @param accountId
     * @param movieId
     * @param addToWatchlist
     * @return
     * @throws MovieDbException
     */
    public StatusCode modifyWatchList(String sessionId, int accountId, Integer movieId, boolean addToWatchlist) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, "/movie_watchlist");

        apiUrl.addArgument(PARAM_ID, accountId);
        apiUrl.addArgument(PARAM_SESSION, sessionId);

        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("movie_id", movieId);
        body.put("movie_watchlist", addToWatchlist);
        String jsonBody = convertToJson(body);
        LOG.info("Json Body: '{}'", jsonBody);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url, jsonBody);

        try {
            return mapper.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            LOG.warn("Failed to modify watch list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

}
