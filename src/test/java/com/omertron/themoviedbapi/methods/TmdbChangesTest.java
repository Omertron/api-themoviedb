/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.ChangedMovie;
import com.omertron.themoviedbapi.results.TmdbResultsList;
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
public class TmdbChangesTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbChangesTest.class);
    // API
    private static TmdbChanges instance;

    public TmdbChangesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbChanges(API_KEY, new DefaultPoolingHttpClient());
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
     * Test of getMovieChangesList method, of class TmdbChanges.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieChangesList() throws MovieDbException {
        LOG.info("getMovieChangesList");
        int page = 0;
        String startDate = "";
        String endDate = "";
        TmdbResultsList<ChangedMovie> result = instance.getMovieChangesList(page, startDate, endDate);
        assertFalse("No movie changes.", result.getResults().isEmpty());
    }

    /**
     * Test of getPersonMovieOldChangesList method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Ignore("Not ready yet")
    public void testGetPersonChangesList() throws MovieDbException {
        LOG.info("getPersonChangesList");
        int page = 0;
        String startDate = "";
        String endDate = "";
        instance.getPersonChangesList(page, startDate, endDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
