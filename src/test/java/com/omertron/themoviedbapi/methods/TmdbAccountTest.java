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
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.enumeration.SortBy;
import com.omertron.themoviedbapi.model2.StatusCode;
import com.omertron.themoviedbapi.model2.account.Account;
import com.omertron.themoviedbapi.model2.authentication.TokenSession;
import com.omertron.themoviedbapi.model2.list.UserList;
import com.omertron.themoviedbapi.model2.movie.MovieBasic;
import com.omertron.themoviedbapi.model2.tv.TVBasic;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
        List<UserList> results = instance.getUserLists(getSessionId(), getAccountId());
        assertNotNull("No list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (UserList r : results) {
            LOG.info("    {}", r.toString());
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
        List<MovieBasic> results = instance.getFavoriteMovies(getSessionId(), getAccountId());
        assertNotNull("No list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (MovieBasic r : results) {
            LOG.info("    {}", r.toString());
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
        List<TVBasic> results = instance.getFavoriteTv(getSessionId(), getAccountId());
        assertNotNull("No list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (TVBasic r : results) {
            LOG.info("    {}", r.toString());
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
        List<MovieBasic> results = instance.getRatedMovies(getSessionId(), getAccountId(), null, null, null);
        assertNotNull("No rated list found", results);
        assertFalse("No entries found", results.isEmpty());
    }

    /**
     * Test of getRatedTV method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetRatedTV() throws MovieDbException {
        LOG.info("getRatedTV");
        List<TVBasic> results = instance.getRatedTV(getSessionId(), getAccountId(), null, null, null);
        assertNotNull("No rated list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (TVBasic r : results) {
            LOG.info("    {}", r.toString());
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
        List<MovieBasic> results = instance.getWatchListMovie(getSessionId(), getAccountId(), null, null, null);
        assertNotNull("No rated list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (MovieBasic r : results) {
            LOG.info("    {}", r.toString());
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
        List<TVBasic> results = instance.getWatchListTV(getSessionId(), getAccountId(), null, null, null);
        assertNotNull("No rated list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (TVBasic r : results) {
            LOG.info("    {}", r.toString());
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

        //TODO: Need to use rated movies with the guest session to add some movies to get
        TmdbAuthentication auth = new TmdbAuthentication(getApiKey(), getHttpTools());
        TokenSession guestToken = auth.getGuestSessionToken();
        TmdbMovies tmdbM = new TmdbMovies(getApiKey(), getHttpTools());

        String language = LANGUAGE_DEFAULT;
        Integer page = null;
        SortBy sortBy = null;
        List<MovieBasic> result = instance.getGuestRatedMovies(guestToken.getGuestSessionId(), language, page, sortBy);
        LOG.info("{}", result);
        fail("Need rated movies");
    }
}
