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
import com.omertron.themoviedbapi.model.authentication.TokenAuthorisation;
import com.omertron.themoviedbapi.model.authentication.TokenSession;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import java.io.IOException;
import java.net.URL;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the Authentication Methods
 *
 * @author stuart.boston
 */
public class TmdbAuthentication extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbAuthentication(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * This method is used to generate a valid request token for user based authentication.
     *
     * A request token is required in order to request a session id.
     *
     * You can generate any number of request tokens but they will expire after 60 minutes.
     *
     * As soon as a valid session id has been created the token will be destroyed.
     *
     * @return
     * @throws MovieDbException
     */
    public TokenAuthorisation getAuthorisationToken() throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        URL url = new ApiUrl(apiKey, MethodBase.AUTH).subMethod(MethodSub.TOKEN_NEW).buildUrl(parameters);

        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, TokenAuthorisation.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.AUTH_FAILURE, "Failed to get Authorisation Token", url, ex);
        }
    }

    /**
     * This method is used to generate a session id for user based authentication.
     *
     * A session id is required in order to use any of the write methods.
     *
     * @param token
     * @return
     * @throws MovieDbException
     */
    public TokenSession getSessionToken(TokenAuthorisation token) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();

        if (!token.getSuccess()) {
            throw new MovieDbException(ApiExceptionType.AUTH_FAILURE, "Authorisation token was not successful!");
        }

        parameters.add(Param.TOKEN, token.getRequestToken());
        URL url = new ApiUrl(apiKey, MethodBase.AUTH).subMethod(MethodSub.SESSION_NEW).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, TokenSession.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get Session Token", url, ex);
        }
    }

    /**
     * This method is used to generate a session id for user based authentication. User must provide their username and password
     *
     * A session id is required in order to use any of the write methods.
     *
     * @param token Session token
     * @param username User's username
     * @param password User's password
     * @return
     * @throws MovieDbException
     */
    public TokenAuthorisation getSessionTokenLogin(TokenAuthorisation token, String username, String password) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();

        if (!token.getSuccess()) {
            throw new MovieDbException(ApiExceptionType.AUTH_FAILURE, "Authorisation token was not successful!");
        }

        parameters.add(Param.TOKEN, token.getRequestToken());
        parameters.add(Param.USERNAME, username);
        parameters.add(Param.PASSWORD, password);

        URL url = new ApiUrl(apiKey, MethodBase.AUTH).subMethod(MethodSub.TOKEN_VALIDATE).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, TokenAuthorisation.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get Session Token", url, ex);
        }
    }

    /**
     * This method is used to generate a guest session id.
     *
     * A guest session can be used to rate movies without having a registered TMDb user account.
     *
     * You should only generate a single guest session per user (or device) as you will be able to attach the ratings to a TMDb user
     * account in the future.
     *
     * There are also IP limits in place so you should always make sure it's the end user doing the guest session actions.
     *
     * If a guest session is not used for the first time within 24 hours, it will be automatically discarded.
     *
     * @return
     * @throws MovieDbException
     */
    public TokenSession getGuestSessionToken() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.AUTH).subMethod(MethodSub.GUEST_SESSION).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, TokenSession.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get Guest Session Token", url, ex);
        }
    }}
