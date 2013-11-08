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
import com.omertron.themoviedbapi.model.TokenAuthorisation;
import com.omertron.themoviedbapi.model.TokenSession;
import com.omertron.themoviedbapi.tools.ApiUrl;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_TOKEN;
import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

public class TmdbAuthentication extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbAuthentication.class);
    // API URL Parameters
    private static final String BASE_AUTH = "authentication/";

    public TmdbAuthentication(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_AUTH, "token/new");

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, TokenAuthorisation.class
            );
        } catch (IOException ex) {
            LOG.warn("Failed to get Authorisation Token: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.AUTHORISATION_FAILURE, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_AUTH, "session/new");

        if (!token.getSuccess()) {
            LOG.warn("Authorisation token was not successful!");
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.AUTHORISATION_FAILURE, "Authorisation token was not successful!");
        }

        apiUrl.addArgument(PARAM_TOKEN, token.getRequestToken());
        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, TokenSession.class
            );
        } catch (IOException ex) {
            LOG.warn("Failed to get Session Token: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_AUTH, "guest_session/new");

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, TokenSession.class
            );
        } catch (IOException ex) {
            LOG.warn("Failed to get Session Token: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
}
