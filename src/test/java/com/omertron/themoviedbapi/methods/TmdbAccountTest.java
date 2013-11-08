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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        instance = new TmdbAccount(API_KEY, null);
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
//    @Test
    public void testGetAccount() throws MovieDbException {
        LOG.info("getAccount");
        Account result = instance.getAccount(SESSION_ID_APITESTS);
        assertEquals("Wrong account returned", ACCOUNT_ID_APITESTS, result);
    }

    /**
     * Test of getUserLists method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetUserLists() throws MovieDbException {
        LOG.info("getUserLists");
        List<MovieDbList> expResult = null;
        List<MovieDbList> result = instance.getUserLists(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS);
        LOG.info("{}", result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFavoriteMovies method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetFavoriteMovies() throws MovieDbException {
        LOG.info("getFavoriteMovies");
        List<MovieDb> expResult = null;
        List<MovieDb> result = instance.getFavoriteMovies(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS);
        LOG.info("{}", result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeFavoriteStatus method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testChangeFavoriteStatus() throws MovieDbException {
        LOG.info("changeFavoriteStatus");
        Integer movieId = null;
        boolean isFavorite = false;
        StatusCode expResult = null;
        StatusCode result = instance.changeFavoriteStatus(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS, movieId, isFavorite);
        LOG.info("{}", result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRatedMovies method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetRatedMovies() throws MovieDbException {
        LOG.info("getRatedMovies");
        List<MovieDb> expResult = null;
        List<MovieDb> result = instance.getRatedMovies(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS);
        LOG.info("{}", result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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

        LOG.info("Remove movie from list");
        // clean up again
        status = instance.modifyWatchList(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS, 550, false);
        LOG.info("Remove status: {}", status);

        assertTrue(instance.getWatchList(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS).isEmpty());
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
        boolean add = false;
        StatusCode expResult = null;
        StatusCode result = instance.modifyWatchList(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS, movieId, add);
        LOG.info("{}", result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
