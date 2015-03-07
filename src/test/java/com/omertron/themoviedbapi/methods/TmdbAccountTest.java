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

import com.omertron.themoviedbapi.AbstractTests;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestSuite;
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.enumeration.SortBy;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.account.Account;
import com.omertron.themoviedbapi.model.list.UserList;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.results.ResultList;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbAccountTest extends AbstractTests {

    private static TmdbAccount instance;
    // Constants
    private static final int ID_MOVIE_FIGHT_CLUB = 550;
    private static final int ID_TV_WALKING_DEAD = 1402;

    public TmdbAccountTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbAccount(getApiKey(), getHttpTools());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws MovieDbException {
    }

    @After
    public void tearDown() throws MovieDbException {
    }

    /**
     * Test of getAccountId method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetAccount() throws MovieDbException {
        LOG.info("getAccount");
        Account result = instance.getAccount(getSessionId());
        assertNotNull("No account returned", result);
        // Make sure properties are extracted correctly
        assertEquals("Wrong username!", getUsername(), result.getUserName());
    }

    /**
     * Test of getUserLists method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetUserLists() throws MovieDbException {
        LOG.info("getUserLists");
        ResultList<UserList> results = instance.getUserLists(getSessionId(), getAccountId());
        TestSuite.test(results,"UserLists");

        for (UserList result : results.getResults()) {
            TestSuite.test(result);
        }
    }

    /**
     * Test of getFavoriteMovies method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetFavoriteMovies() throws MovieDbException {
        LOG.info("getFavoriteMovies");
        ResultList<MovieBasic> results = instance.getFavoriteMovies(getSessionId(), getAccountId());
        TestSuite.test(results,"Fav Movies");

        for (MovieBasic result : results.getResults()) {
            TestSuite.test(result);
        }
    }

    /**
     * Test of getFavoriteTv method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetFavoriteTv() throws MovieDbException {
        LOG.info("getFavoriteTv");
        ResultList<TVBasic> results = instance.getFavoriteTv(getSessionId(), getAccountId());
        TestSuite.test(results,"Fav TV");

        for (TVBasic result : results.getResults()) {
            TestSuite.test(result);
        }
    }

    /**
     * Test of modifyFavoriteStatus method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testModifyFavoriteStatus() throws MovieDbException {
        LOG.info("modifyFavoriteStatus");

        // Add a movie as a favourite
        StatusCode result = instance.modifyFavoriteStatus(getSessionId(), getAccountId(), MediaType.MOVIE, ID_MOVIE_FIGHT_CLUB, true);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getCode() == 1 || result.getCode() == 12);

        // Remove a movie as a favourite
        result = instance.modifyFavoriteStatus(getSessionId(), getAccountId(), MediaType.MOVIE, ID_MOVIE_FIGHT_CLUB, false);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getCode() == 13);

        // Add a TV Show as a favourite
        result = instance.modifyFavoriteStatus(getSessionId(), getAccountId(), MediaType.TV, ID_TV_WALKING_DEAD, true);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getCode() == 1 || result.getCode() == 12);

        // Remove a TV Show as a favourite
        result = instance.modifyFavoriteStatus(getSessionId(), getAccountId(), MediaType.TV, ID_TV_WALKING_DEAD, false);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getCode() == 13);
    }

    /**
     * Test of getRatedMovies method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetRatedMovies() throws MovieDbException {
        LOG.info("getRatedMovies");
        ResultList<MovieBasic> results = instance.getRatedMovies(getSessionId(), getAccountId(), null, null, null);
        TestSuite.test(results,"Rated Movies");
    }

    /**
     * Test of getRatedTV method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetRatedTV() throws MovieDbException {
        LOG.info("getRatedTV");
        ResultList<TVBasic> results = instance.getRatedTV(getSessionId(), getAccountId(), null, null, null);
        TestSuite.test(results,"Rated TV");
        for (TVBasic result : results.getResults()) {
            TestSuite.test(result);
        }
    }

    /**
     * Test of getWatchListMovie method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetWatchListMovie() throws MovieDbException {
        LOG.info("getWatchListMovie");
        ResultList<MovieBasic> results = instance.getWatchListMovie(getSessionId(), getAccountId(), null, null, null);
        TestSuite.test(results,"Watch List Movie");
        for (MovieBasic result : results.getResults()) {
            TestSuite.test(result);
        }
    }

    /**
     * Test of getWatchListTV method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetWatchListTV() throws MovieDbException {
        LOG.info("getWatchListTV");
        ResultList<TVBasic> results = instance.getWatchListTV(getSessionId(), getAccountId(), null, null, null);
        TestSuite.test(results,"Watch List TV");
        for (TVBasic result : results.getResults()) {
            TestSuite.test(result);
        }
    }

    /**
     * Test of modifyWatchList method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testModifyWatchList() throws MovieDbException {
        LOG.info("modifyWatchList");

        // Add a movie to the watch list
        StatusCode result = instance.modifyWatchList(getSessionId(), getAccountId(), MediaType.MOVIE, ID_MOVIE_FIGHT_CLUB, true);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getCode() == 1 || result.getCode() == 12);

        // Remove a movie from the watch list
        result = instance.modifyWatchList(getSessionId(), getAccountId(), MediaType.MOVIE, ID_MOVIE_FIGHT_CLUB, false);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getCode() == 13);

        // Add a TV Show to the watch list
        result = instance.modifyWatchList(getSessionId(), getAccountId(), MediaType.TV, ID_TV_WALKING_DEAD, true);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getCode() == 1 || result.getCode() == 12);

        // Remove a TV Show from the watch list
        result = instance.modifyWatchList(getSessionId(), getAccountId(), MediaType.TV, ID_TV_WALKING_DEAD, false);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getCode() == 13);
    }

    /**
     * Test of getGuestRatedMovies method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetGuestRatedMovies() throws MovieDbException {
        LOG.info("getGuestRatedMovies");

        // Get the guest token
        String guestSession = getGuestSession();

        String language = LANGUAGE_DEFAULT;
        Integer page = null;
        SortBy sortBy = SortBy.CREATED_AT_ASC;
        ResultList<MovieBasic> result = instance.getGuestRatedMovies(guestSession, language, page, sortBy);

        // Check and post some ratings if required
        if (result.isEmpty()) {
            postGuestRating(guestSession, ID_MOVIE_FIGHT_CLUB);
            postGuestRating(guestSession, 78);
            postGuestRating(guestSession, 76757);
            postGuestRating(guestSession, 240832);
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            } catch (InterruptedException ex) {
                LOG.trace("Interrupted");
            }

            // Get the movie list again
            result = instance.getGuestRatedMovies(guestSession, language, page, sortBy);
        }

        TestSuite.test(result,"Guest Reated Movies");
    }

    private void postGuestRating(String guestSessionId, int movieId) throws MovieDbException {
        TmdbMovies tmdbMovies = new TmdbMovies(getApiKey(), getHttpTools());
        int rating = TestSuite.randomRating();

        LOG.info("Posting rating of '{}' to ID {} for guest session '{}'", rating, movieId, guestSessionId);
        StatusCode sc = tmdbMovies.postMovieRating(movieId, rating, null, guestSessionId);
        LOG.info("{}", sc);
        assertTrue("Failed to post rating", sc.getCode() == 1 || sc.getCode() == 12);
    }
}
