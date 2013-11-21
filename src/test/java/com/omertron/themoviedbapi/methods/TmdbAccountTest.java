/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.ACCOUNT_ID_APITESTS;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.SESSION_ID_APITESTS;
import com.omertron.themoviedbapi.model.Account;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.model.movie.MovieDbList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

/**
 *
 * @author stuart.boston
 */
public class TmdbAccountTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbAccountTest.class);
    // API
    private static TmdbAccount instance;

    public TmdbAccountTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbAccount(API_KEY, new DefaultPoolingHttpClient());
        TestLogger.Configure();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAccount method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetAccount() throws MovieDbException {
        LOG.info("getAccount");
        Account result = instance.getAccount(SESSION_ID_APITESTS);
        assertEquals("Wrong account returned", ACCOUNT_ID_APITESTS, result.getId());
    }

    /**
     * Test of getUserLists method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetUserLists() throws MovieDbException {
        LOG.info("getUserLists");
        List<MovieDbList> result = instance.getUserLists(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS);
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
        List<MovieDb> favList = instance.getFavoriteMovies(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS);
        assertTrue("Favorite list was not empty!", favList.isEmpty());

        LOG.info("Add movie to list");
        // add a movie
        StatusCode status = instance.changeFavoriteStatus(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS, 550, true);
        LOG.info("Add Status: {}", status);

        LOG.info("Get favorite list");
        favList = instance.getFavoriteMovies(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS);
        assertNotNull("Empty favorite list returned", favList);
        assertFalse("Favorite list empty", favList.isEmpty());

        // clean up again
        LOG.info("Remove movie(s) from list");
        for (MovieDb movie : favList) {
            LOG.info("Removing movie {}-'{}'", movie.getId(), movie.getTitle());
            status = instance.changeFavoriteStatus(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS, movie.getId(), false);
            LOG.info("Remove status: {}", status);
        }
        assertTrue("Favorite list was not empty", instance.getFavoriteMovies(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS).isEmpty());
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
        List<MovieDb> result = instance.getRatedMovies(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS);
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
        List<MovieDb> watchList = instance.getWatchList(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS);
        assertTrue("Watch list was not empty!", watchList.isEmpty());

        LOG.info("Add movie to list");
        // add a movie
        StatusCode status = instance.modifyWatchList(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS, 550, true);
        LOG.info("Add Status: {}", status);

        LOG.info("Get watch list");
        watchList = instance.getWatchList(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS);
        assertNotNull("Empty watch list returned", watchList);
        assertEquals("Watchlist wrong size", 1, watchList.size());

        LOG.info("Removing movie(s) from list");
        // clean up again
        for (MovieDb movie : watchList) {
            LOG.info("Removing movie {}-'{}'", movie.getId(), movie.getTitle());
            status = instance.modifyWatchList(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS, movie.getId(), false);
            LOG.info("Remove status: {}", status);
        }

        assertTrue(instance.getWatchList(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS).isEmpty());
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
