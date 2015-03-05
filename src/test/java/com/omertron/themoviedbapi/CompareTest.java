/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.themoviedbapi;

import com.omertron.themoviedbapi.model.movie.MovieInfo;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stuart.Boston
 */
public class CompareTest {

    private static final Logger LOG = LoggerFactory.getLogger(CompareTest.class);

    private static MovieInfo moviedb;
    private static final String TITLE_MAIN = "Blade Runner";
    private static final String TITLE_OTHER = "Blade Runner Directors Cut";
    private static final String YEAR_FULL = "1982-01-01";
    private static final String YEAR_SHORT = "1982";

    private static final boolean CASE_SENSITIVE = true;
    private static final boolean NOT_CASE_SENSITIVE = false;

    public CompareTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        TestLogger.Configure();

        // Set the default comparison movie
        moviedb = new MovieInfo();
        moviedb.setTitle(TITLE_MAIN);
        moviedb.setOriginalTitle(TITLE_OTHER);
        moviedb.setReleaseDate(YEAR_FULL);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Exact match
     */
    @Test
    public void testExactMatch() {
        int maxDistance = 0;
        boolean result;

        result = Compare.movies(moviedb, TITLE_MAIN, YEAR_SHORT, maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_OTHER, YEAR_SHORT, maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_MAIN, YEAR_SHORT, maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_OTHER, YEAR_SHORT, maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);

        result = Compare.movies(moviedb, TITLE_MAIN, "", maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_OTHER, "", maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_MAIN, "", maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_OTHER, "", maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
    }

    /**
     * Close match
     */
    @Test
    public void testCloseMatch() {
        int maxDistance = 6;
        boolean result;

        String closeMain = "bloderannar";
        String closeOther = "Blade Runner Dir Cut";

        // Make sure they are close enough
        int currentDistance;

        currentDistance = StringUtils.getLevenshteinDistance(TITLE_MAIN, closeMain);
        LOG.info("Distance between '{}' and '{}' is {}", TITLE_MAIN, closeMain, currentDistance);
        assertTrue(currentDistance <= maxDistance);

        currentDistance = StringUtils.getLevenshteinDistance(TITLE_OTHER, closeOther);
        LOG.info("Distance between '{}' and '{}' is {}", TITLE_OTHER, closeOther, currentDistance);
        assertTrue(currentDistance <= maxDistance);

        result = Compare.movies(moviedb, closeMain, YEAR_SHORT, maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeOther, YEAR_SHORT, maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeMain, YEAR_SHORT, maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeOther, YEAR_SHORT, maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);

        result = Compare.movies(moviedb, closeMain, "", maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeOther, "", maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeMain, "", maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeOther, "", maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
    }

}
