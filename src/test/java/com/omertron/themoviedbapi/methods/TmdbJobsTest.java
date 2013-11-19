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
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.JobDepartment;
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
public class TmdbJobsTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbGenresTest.class);
    // API
    private static TmdbJobs tmdb;

    public TmdbJobsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        tmdb = new TmdbJobs(API_KEY, null);
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
     * Test of getJobs method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetJobs() throws MovieDbException {
        LOG.info("getJobs");
        TmdbResultsList<JobDepartment> result = tmdb.getJobs();
        assertFalse("No jobs found", result.getResults().isEmpty());
    }

}
