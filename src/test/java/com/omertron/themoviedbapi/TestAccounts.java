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
package com.omertron.themoviedbapi;

import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.model.Account;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.model.MovieDbList;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.TokenAuthorisation;
import com.omertron.themoviedbapi.model.TokenSession;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestAccounts extends AbstractTests {

    private static TheMovieDbApi tmdb;
    private static TokenSession tokenSession = null;
    private static Account account = null;

    public TestAccounts() throws MovieDbException {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        tmdb = new TheMovieDbApi(getApiKey());
    }

    @Before
    public void setUp() throws MovieDbException {
        if (tokenSession == null) {
            testSessionCreation();
        }

        if (account == null) {
            testAccount();
        }
    }

    /**
     * Test and create a session token for the rest of the tests
     *
     * @throws MovieDbException
     */
    @Test
    public void testSessionCreation() throws MovieDbException {
        LOG.info("Test and create a session token for the rest of the tests");

        // 1: Create a request token
        TokenAuthorisation token = tmdb.getAuthorisationToken();
        assertFalse("Token (auth) is null", token == null);
        assertTrue("Token (auth) is not valid", token.getSuccess());
        LOG.info("Token (auth): {}", token.toString());

        // 2b; Get user permission
        token = tmdb.getSessionTokenLogin(token, getUsername(), getPassword());
        assertFalse("Token (login) is null", token == null);
        assertTrue("Token (login) is not valid", token.getSuccess());
        LOG.info("Token (login): {}", token.toString());

        // 3: Create the sessions ID
        tokenSession = tmdb.getSessionToken(token);
        assertFalse("Session token is null", tokenSession == null);
        assertTrue("Session token is not valid", tokenSession.getSuccess());
        LOG.info("Session token: {}", tokenSession.toString());
    }

    /**
     * Test the account information
     *
     * @throws MovieDbException
     */
    @Test
    public void testAccount() throws MovieDbException {
        LOG.info("Using Session ID '{}' for test", tokenSession.getSessionId());
        account = tmdb.getAccount(tokenSession.getSessionId());

        LOG.info("Account: {}", account);

        // Make sure properties are extracted correctly
        assertEquals("Wrong username!", getUsername(), account.getUserName());
    }

    @Test
    public void testWatchList() throws MovieDbException {
        // If the static account is null, get it
        if (account == null) {
            account = tmdb.getAccount(tokenSession.getSessionId());
        }

        // make sure it's empty (because it's just a test account
        assertTrue(tmdb.getWatchListMovie(tokenSession.getSessionId(), account.getId()).isEmpty());

        // add a movie
        tmdb.addToWatchList(tokenSession.getSessionId(), account.getId(), 550, MediaType.MOVIE);

        List<MovieDb> watchList = tmdb.getWatchListMovie(tokenSession.getSessionId(), account.getId());
        assertNotNull("Empty watch list returned", watchList);
        assertEquals("Watchlist wrong size", 1, watchList.size());

        // clean up again
        tmdb.removeFromWatchList(tokenSession.getSessionId(), account.getId(), 550, MediaType.MOVIE);

        assertTrue(tmdb.getWatchListMovie(tokenSession.getSessionId(), account.getId()).isEmpty());
    }

    @Test
    public void testFavorites() throws MovieDbException {
        // If the static account is null, get it
        if (account == null) {
            account = tmdb.getAccount(tokenSession.getSessionId());
        }

        // make sure it's empty (because it's just a test account
        assertTrue(tmdb.getFavoriteMovies(tokenSession.getSessionId(), account.getId()).isEmpty());

        // add a movie
        tmdb.changeFavoriteStatus(tokenSession.getSessionId(), account.getId(), 550, MediaType.MOVIE, true);

        List<MovieDb> watchList = tmdb.getFavoriteMovies(tokenSession.getSessionId(), account.getId());
        assertNotNull("Empty watch list returned", watchList);
        assertEquals("Watchlist wrong size", 1, watchList.size());

        // clean up again
        tmdb.changeFavoriteStatus(tokenSession.getSessionId(), account.getId(), 550, MediaType.MOVIE, false);

        assertTrue(tmdb.getFavoriteMovies(tokenSession.getSessionId(), account.getId()).isEmpty());
    }

    /**
     * Test of getSessionToken method, of class TheMovieDbApi.
     *
     * TODO: Cannot be tested without a HTTP authorisation: http://help.themoviedb.org/kb/api/user-authentication
     *
     * @throws MovieDbException
     */
    @Ignore("Tested in setup")
    public void testGetSessionToken() throws MovieDbException {
    }

    /**
     * Test of getGuestSessionToken method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetGuestSessionToken() throws MovieDbException {
        LOG.info("getGuestSessionToken");
        TokenSession result = tmdb.getGuestSessionToken();

        assertTrue("Failed to get guest session", result.getSuccess());
    }

    /**
     * Test of postMovieRating method, of class TheMovieDbApi.
     *
     * TODO: Cannot be tested without a HTTP authorisation: http://help.themoviedb.org/kb/api/user-authentication
     *
     * @throws MovieDbException
     */
    @Test
    public void testMovieRating() throws MovieDbException {
        LOG.info("postMovieRating");

        Integer movieID = 68724;
        Integer rating = new Random().nextInt(10) + 1;

        boolean wasPosted = tmdb.postMovieRating(tokenSession.getSessionId(), movieID, rating);
        assertTrue("Rating was not added", wasPosted);

        // get all rated movies
        List<MovieDb> ratedMovies = tmdb.getRatedMovies(tokenSession.getSessionId(), account.getId());
        assertTrue("No rated movies", ratedMovies.size() > 0);

        // We should check that the movie was correctly rated, but the CDN does not update fast enough.
    }

    @Test
    public void testMovieLists() throws MovieDbException {
        Integer movieID = 68724;

        // use a random name to avoid that we clash we leftovers of incomplete test runs
        String name = "test list " + new Random().nextInt(100);

        // create the list
        String listId = tmdb.createList(tokenSession.getSessionId(), name, "api testing only");

        // add a movie, and test that it is on the list now
        tmdb.addMovieToList(tokenSession.getSessionId(), listId, movieID);
        MovieDbList list = tmdb.getList(listId);
        assertNotNull("Movie list returned was null", list);
        assertEquals("Unexpected number of items returned", 1, list.getItemCount());
        assertEquals((int) movieID, list.getItems().get(0).getId());

        // now remove the movie
        tmdb.removeMovieFromList(tokenSession.getSessionId(), listId, movieID);
        assertEquals(tmdb.getList(listId).getItemCount(), 0);

        // delete the test list
        StatusCode statusCode = tmdb.deleteMovieList(tokenSession.getSessionId(), listId);
        assertEquals(statusCode.getStatusCode(), 13);
    }
}
