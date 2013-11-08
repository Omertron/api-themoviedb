/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.LANGUAGE_ENGLISH;
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
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetDiscoverMovie() throws MovieDbException {
        LOG.info("getDiscoverMovie");
        Discover discover = new Discover();
        discover.year(2013).language(LANGUAGE_ENGLISH);

        TmdbResultsList<MovieDb> result = instance.getDiscoverMovie(discover);
        assertFalse("No movies discovered", result.getResults().isEmpty());
    }

    /**
     * Test of getDiscoverTv method, of class TmdbDiscover.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetDiscoverTv() throws MovieDbException {
        LOG.info("getDiscoverTv");
        Discover discover = new Discover();
        discover.year(2013).language(LANGUAGE_ENGLISH);

        TmdbResultsList<MovieDb> result = instance.getDiscoverMovie(discover);
        assertFalse("No TV shows discovered", result.getResults().isEmpty());
    }

}
