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
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.model.MovieDbList;
import com.omertron.themoviedbapi.model.StatusCode;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbAccountTest extends AbstractTests {

    private static TmdbAccount instance;
    // Constants
    private static final int ID_MOVIE_FIGHT_CLUB = 550;

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
        List<MovieDbList> result = instance.getUserLists(getSessionId(), getAccountId());
        assertNotNull("Null list returned", result);
    }

    /**
     * Test of getFavoriteMovies method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetFavoriteMovies() throws MovieDbException {
        LOG.info("getFavoriteMovies");

        LOG.info("Check favorite list is empty");
        // make sure it's empty (because it's just a test account
        List<MovieDb> favList = instance.getFavoriteMovies(getSessionId(), getAccountId());
        assertTrue("Favorite list was not empty!", favList.isEmpty());

        LOG.info("Add movie to list");
        // add a movie
        StatusCode status = instance.changeFavoriteStatus(getSessionId(), getAccountId(), ID_MOVIE_FIGHT_CLUB, MediaType.MOVIE, true);
        LOG.info("Add Status: {}", status);

        LOG.info("Get favorite list");
        favList = instance.getFavoriteMovies(getSessionId(), getAccountId());
        assertNotNull("Empty favorite list returned", favList);
        assertFalse("Favorite list empty", favList.isEmpty());

        // clean up again
        LOG.info("Remove movie(s) from list");
        for (MovieDb movie : favList) {
            LOG.info("Removing movie {}-'{}'", movie.getId(), movie.getTitle());
            status = instance.changeFavoriteStatus(getSessionId(), getAccountId(), movie.getId(), MediaType.MOVIE, false);
            LOG.info("Remove status: {}", status);
        }
        assertTrue("Favorite list was not empty", instance.getFavoriteMovies(getSessionId(), getAccountId()).isEmpty());
    }

    /**
     * Test of changeFavoriteStatus method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Ignore("Tested as part of getFavoriteMovies")
    public void testChangeFavoriteStatus() throws MovieDbException {
    }

    /**
     * Test of getRatedMovies method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetRatedMovies() throws MovieDbException {
        LOG.info("getRatedMovies");
        List<MovieDb> result = instance.getRatedMovies(getSessionId(), getAccountId());
        assertFalse("No rated movies", result.isEmpty());
    }

    /**
     * Test of getWatchList method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetWatchList() throws MovieDbException {
        LOG.info("getWatchList");

        LOG.info("Check list is empty");
        // make sure it's empty (because it's just a test account
        List<MovieDb> watchList = instance.getWatchListMovie(getSessionId(), getAccountId());
        assertTrue("Watch list was not empty!", watchList.isEmpty());

        LOG.info("Add movie to list");
        // add a movie
        StatusCode status = instance.modifyWatchList(getSessionId(), getAccountId(), ID_MOVIE_FIGHT_CLUB, MediaType.MOVIE, true);
        LOG.info("Add Status: {}", status);

        LOG.info("Get watch list");
        watchList = instance.getWatchListMovie(getSessionId(), getAccountId());
        assertNotNull("Empty watch list returned", watchList);
        assertFalse("Watchlist list empty", watchList.isEmpty());

        LOG.info("Removing movie(s) from list");
        // clean up again
        for (MovieDb movie : watchList) {
            LOG.info("Removing movie {}-'{}'", movie.getId(), movie.getTitle());
            status = instance.modifyWatchList(getSessionId(), getAccountId(), movie.getId(), MediaType.MOVIE, false);
            LOG.info("Remove status: {}", status);
        }

        assertTrue(instance.getWatchListMovie(getSessionId(), getAccountId()).isEmpty());
    }

    /**
     * Test of modifyWatchList method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Ignore("Tested as part of testGetWatchList")
    public void testModifyWatchList() throws MovieDbException {
    }
}
