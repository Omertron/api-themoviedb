/*
 *      Copyright (c) 2004-2014 Stuart Boston
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

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.person.PersonCredits;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

/**
 *
 * @author stuart.boston
 */
public class TmdbCreditsTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbCreditsTest.class);
    // API
    private static TmdbCredits instance;

    public TmdbCreditsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbCredits(API_KEY, new DefaultPoolingHttpClient());
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
     * Test of getCreditInfo method, of class TmdbCredits.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetCreditInfo() throws MovieDbException {
        LOG.info("getCreditInfo");
        String creditId = "525346f619c29579400d4145";
        String language = "";
        PersonCredits result = instance.getCreditInfo(creditId, language);
        assertEquals("Wrong name", "Sean Bean", result.getPerson().getName());
        assertEquals("Wrong job", "Actor", result.getJob());
        assertFalse("No seasons", result.getMedia().getSeasons().isEmpty());
    }

}
