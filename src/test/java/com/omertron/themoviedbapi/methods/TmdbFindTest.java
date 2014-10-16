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
import com.omertron.themoviedbapi.model.FindResults;
import org.junit.After;
import org.junit.AfterClass;
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
 * @author Luca Tagliani
  */
public class TmdbFindTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbFindTest.class);
    // API
    private static TmdbFind instance;

    public TmdbFindTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        TestLogger.Configure();
        instance = new TmdbFind(API_KEY, new DefaultPoolingHttpClient());
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
        FindResults result = instance.find("tt0196229", TmdbFind.ExternalSource.imdb_id, "it");
        assertFalse("No movie for id.", result.getMovieResults().isEmpty());
    }
    
    @Test
    public void testFindTvSeriesImdbID() throws MovieDbException {
        LOG.info("findTvSeriesImdbID");
        FindResults result = instance.find("tt1219024", TmdbFind.ExternalSource.imdb_id, "it");
        assertFalse("No tv for id.", result.getTvResults().isEmpty());
    }
    
    @Test
    public void testFindPersonImdbID() throws MovieDbException {
        LOG.info("findPersonImdbID");
        FindResults result = instance.find("nm0001774", TmdbFind.ExternalSource.imdb_id, "it");
        assertFalse("No person for id.", result.getPersonResults().isEmpty());
    }

    
}
