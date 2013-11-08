/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.model.tv.TVSeriesBasic;
import com.omertron.themoviedbapi.results.TmdbResultsList;
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
public class TmdbDiscoverTest {
    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbDiscoverTest.class);
    // API
    private static TmdbDiscover instance;

    public TmdbDiscoverTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbDiscover(API_KEY, null);
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
     * Test of getDiscoverMovie method, of class TmdbDiscover.
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetDiscoverMovie() throws MovieDbException {
        LOG.info("getDiscoverMovie");
        Discover discover = null;
        TmdbResultsList<MovieDb> expResult = null;
        TmdbResultsList<MovieDb> result = instance.getDiscoverMovie(discover);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDiscoverTv method, of class TmdbDiscover.
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetDiscoverTv() throws MovieDbException {
        LOG.info("getDiscoverTv");
        Discover discover = null;
        TmdbResultsList<TVSeriesBasic> expResult = null;
        TmdbResultsList<TVSeriesBasic> result = instance.getDiscoverTv(discover);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
