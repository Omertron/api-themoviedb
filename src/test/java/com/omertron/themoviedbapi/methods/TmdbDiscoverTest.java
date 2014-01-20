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
import static com.omertron.themoviedbapi.TheMovieDbApiTest.LANGUAGE_ENGLISH;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.results.TmdbResultsList;
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
        TestLogger.Configure();
        instance = new TmdbDiscover(API_KEY, new DefaultPoolingHttpClient());
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
