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
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 * @author Luca Tagliani
 */
public class TmdbFindTest extends AbstractTests {

    private static TmdbFind instance;

    public TmdbFindTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbFind(getApiKey(), getHttpTools());
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
     * Test of getMovieChangesList method, of class TmdbFindTest.
     *
     * @throws MovieDbException
     */
    @Test
    public void testFindMoviesImdbID() throws MovieDbException {
        LOG.info("findMoviesImdbID");
//        TBD_FindResults result = instance.find("tt0196229", TBD_ExternalSource.imdb_id, "it");
//        assertFalse("No movie for id.", result.getMovieResults().isEmpty());
        fail("The test case is a prototype.");
    }

    @Test
    public void testFindTvSeriesImdbID() throws MovieDbException {
        LOG.info("findTvSeriesImdbID");
//        TBD_FindResults result = instance.find("tt1219024", TBD_ExternalSource.imdb_id, "it");
//        assertFalse("No tv for id.", result.getTvResults().isEmpty());
        fail("The test case is a prototype.");
    }

    @Test
    public void testFindPersonImdbID() throws MovieDbException {
        LOG.info("findPersonImdbID");
//        TBD_FindResults result = instance.find("nm0001774", TBD_ExternalSource.imdb_id, "it");
//        assertFalse("No person for id.", result.getPersonResults().isEmpty());
        fail("The test case is a prototype.");
    }

}
