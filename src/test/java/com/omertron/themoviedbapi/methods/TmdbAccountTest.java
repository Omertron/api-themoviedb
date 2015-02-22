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
import com.omertron.themoviedbapi.model.Account;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.list.MovieFavorite;
import com.omertron.themoviedbapi.model.list.TVFavorite;
import com.omertron.themoviedbapi.model.list.UserList;
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
//    @Test
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
//    @Test
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
//    @Test
    public void testGetFavoriteMovies() throws MovieDbException {
        LOG.info("getFavoriteMovies");
        List<MovieFavorite> results = instance.getFavoriteMovies(getSessionId(), getAccountId());
        assertNotNull("No list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (MovieFavorite r : results) {
            LOG.info("    {}", r.toString());
        }
    }

    /**
     * Test of getFavoriteTv method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetFavoriteTv() throws MovieDbException {
        LOG.info("getFavoriteTv");
        List<TVFavorite> results = instance.getFavoriteTv(getSessionId(), getAccountId());
        assertNotNull("No list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (TVFavorite r : results) {
            LOG.info("    {}", r.toString());
        }
    }

    /**
     * Test of modifyFavoriteStatus method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testModifyFavoriteStatus() throws MovieDbException {
        LOG.info("modifyFavoriteStatus");

        // Add a movie as a favourite
        StatusCode result = instance.modifyFavoriteStatus(getSessionId(), getAccountId(), MediaType.MOVIE, ID_MOVIE_FIGHT_CLUB, true);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getStatusCode() == 1 || result.getStatusCode() == 12);

        // Remove a movie as a favourite
        result = instance.modifyFavoriteStatus(getSessionId(), getAccountId(), MediaType.MOVIE, ID_MOVIE_FIGHT_CLUB, false);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getStatusCode() == 13);

        // Add a TV Show as a favourite
        result = instance.modifyFavoriteStatus(getSessionId(), getAccountId(), MediaType.TV, ID_TV_WALKING_DEAD, true);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getStatusCode() == 1 || result.getStatusCode() == 12);

        // Remove a TV Show as a favourite
        result = instance.modifyFavoriteStatus(getSessionId(), getAccountId(), MediaType.TV, ID_TV_WALKING_DEAD, false);
        LOG.info("Result: {}", result);
        assertTrue("Incorrect status code", result.getStatusCode() == 13);

    }

    /**
     * Test of getRatedMovies method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetRatedMovies() throws MovieDbException {
        LOG.info("getRatedMovies");
        List<MovieFavorite> results = instance.getRatedMovies(getSessionId(), getAccountId(), null, null, null);
        assertNotNull("No rated list found", results);
        assertFalse("No entries found", results.isEmpty());

        for(MovieFavorite r: results){
            LOG.info("    {}-{}", r.getClass(),r.toString());
        }

    }

    /**
     * Test of getRatedTV method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetRatedTV() throws MovieDbException {
        LOG.info("getRatedTV");
        List<TVFavorite> results = instance.getRatedTV(getSessionId(), getAccountId(), null, null, null);
        assertNotNull("No rated list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (TVFavorite r : results) {
            LOG.info("    {}", r.toString());
        }
    }

    /**
     * Test of getWatchListMovie method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetWatchListMovie() throws MovieDbException {
        LOG.info("getWatchListMovie");
        List<MovieFavorite> results = instance.getWatchListMovie(getSessionId(), getAccountId(), null, null, null);
        assertNotNull("No rated list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (MovieFavorite r : results) {
            LOG.info("    {}", r.toString());
        }
    }

    /**
     * Test of getWatchListTV method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetWatchListTV() throws MovieDbException {
        LOG.info("getWatchListTV");
        List<TVFavorite> results = instance.getWatchListTV(getSessionId(), getAccountId(), null, null, null);
        assertNotNull("No rated list found", results);
        assertFalse("No entries found", results.isEmpty());
        for (TVFavorite r : results) {
            LOG.info("    {}", r.toString());
        }
    }

    /**
     * Test of modifyWatchList method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testModifyWatchList() throws MovieDbException {
        LOG.info("modifyWatchList");
        Integer movieId = null;
        MediaType mediaType = null;
        boolean addToWatchlist = false;
        StatusCode expResult = null;
        StatusCode result = instance.modifyWatchList(getSessionId(), getAccountId(), movieId, mediaType, addToWatchlist);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
