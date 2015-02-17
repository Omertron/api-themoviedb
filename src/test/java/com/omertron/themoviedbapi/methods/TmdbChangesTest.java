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
import com.omertron.themoviedbapi.TestLogger;
import com.omertron.themoviedbapi.model.ChangedMovie;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbChangesTest extends AbstractTests {

    // API
    private static TmdbChanges instance;

    public TmdbChangesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        TestLogger.Configure();
        instance = new TmdbChanges(getApiKey(), getHttpTools());
    }

    @AfterClass
    public static void tearDownClass() {
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
