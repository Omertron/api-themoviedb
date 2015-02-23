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
import com.omertron.themoviedbapi.model.change.ChangedMedia;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.MethodBase;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
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
        doConfiguration();
        instance = new TmdbChanges(getApiKey(), getHttpTools());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getChangeList(MOVIE) method, of class TmdbChanges.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieChangesList() throws MovieDbException {
        LOG.info("getMovieChangesList");
        TmdbResultsList<ChangedMedia> result = instance.getChangeList(MethodBase.MOVIE, null, null, null);
        assertFalse("No movie changes.", result.getResults().isEmpty());
    }

    /**
     * Test of getChangeList(PERSON) method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Ignore("Not ready yet")
    public void testGetPersonChangesList() throws MovieDbException {
        LOG.info("getPersonChangesList");
        TmdbResultsList<ChangedMedia> result = instance.getChangeList(MethodBase.PERSON, null, null, null);
        assertFalse("No Person changes.", result.getResults().isEmpty());
    }

    /**
     * Test of getChangeList(TV) method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Ignore("Not ready yet")
    public void testGetTVChangesList() throws MovieDbException {
        LOG.info("getPersonChangesList");
        TmdbResultsList<ChangedMedia> result = instance.getChangeList(MethodBase.PERSON, null, null, null);
        assertFalse("No TV changes.", result.getResults().isEmpty());
    }
}
