/*
 *      Copyright (c) 2004-2013 Stuart Boston
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
package com.omertron.themoviedbapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.Configuration;
import com.omertron.themoviedbapi.model.movie.MovieDbList;
import org.junit.Test;

/**
 * Test cases for TheMovieDbApi API
 *
 * @author stuart.boston
 */
public class TheMovieDbApiTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TheMovieDbApiTest.class);
    // API Key
    public static final String API_KEY = "5a1a77e2eba8984804586122754f969f";
    // API
    private static TheMovieDbApi tmdb;
    // Languages
    public static final String LANGUAGE_DEFAULT = "";
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_RUSSIAN = "ru";
    // session and account id of test users named 'apitests'
    public static final String SESSION_ID_APITESTS = "63c85deb39337e29b69d78265eb28d639cbd6f72";
//    public static final String SESSION_ID_APITESTS = "4a2557ab55aaa4a51dc183f0aed08bea304deda0";
    public static final int ACCOUNT_ID_APITESTS = 6065849;

    public TheMovieDbApiTest() throws MovieDbException {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        tmdb = new TheMovieDbApi(API_KEY);
        TestLogger.Configure();
    }

    @AfterClass
    public static void tearDownClass() throws MovieDbException {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getConfiguration method, of class TheMovieDbApi.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testConfiguration() throws MovieDbException {
        LOG.info("Test Configuration");

        Configuration tmdbConfig = tmdb.getConfiguration();
        assertNotNull("Configuration failed", tmdbConfig);
        assertTrue("No base URL", StringUtils.isNotBlank(tmdbConfig.getBaseUrl()));
        assertTrue("No backdrop sizes", tmdbConfig.getBackdropSizes().size() > 0);
        assertTrue("No poster sizes", tmdbConfig.getPosterSizes().size() > 0);
        assertTrue("No profile sizes", tmdbConfig.getProfileSizes().size() > 0);
        LOG.info(tmdbConfig.toString());
    }

    @Ignore("Session required")
    public void testMovieLists() throws MovieDbException {
        Integer movieID = 68724;

        // use a random name to avoid that we clash we leftovers of incomplete test runs
        String name = "test list " + new Random().nextInt(100);

        // create the list
        String listId = tmdb.createList(SESSION_ID_APITESTS, name, "api testing only");

        // add a movie, and test that it is on the list now
        tmdb.addMovieToList(SESSION_ID_APITESTS, listId, movieID);
        MovieDbList list = tmdb.getList(listId);
        assertNotNull("Movie list returned was null", list);
        assertEquals("Unexpected number of items returned", 1, list.getItemCount());
        assertEquals((int) movieID, list.getItems().get(0).getId());

        // now remove the movie
        tmdb.removeMovieFromList(SESSION_ID_APITESTS, listId, movieID);
        assertEquals(tmdb.getList(listId).getItemCount(), 0);

        // delete the test list
        StatusCode statusCode = tmdb.deleteMovieList(SESSION_ID_APITESTS, listId);
        assertEquals(statusCode.getStatusCode(), 13);
    }
}
