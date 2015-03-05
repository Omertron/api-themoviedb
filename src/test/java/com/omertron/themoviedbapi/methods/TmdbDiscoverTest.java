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
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbDiscoverTest extends AbstractTests {

    private static TmdbDiscover instance;

    public TmdbDiscoverTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbDiscover(getApiKey(), getHttpTools());
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

        List<MovieBasic> result = instance.getDiscoverMovies(discover);
        assertFalse("No movies discovered", result.isEmpty());
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

        List<TVBasic> result = instance.getDiscoverTV(discover);
        assertFalse("No TV shows discovered", result.isEmpty());
    }

}
