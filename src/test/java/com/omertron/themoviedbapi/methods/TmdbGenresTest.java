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
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbGenresTest extends AbstractTests {

    private static TmdbGenres instance;
    private static final int ID_GENRE_ACTION = 28;

    public TmdbGenresTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbGenres(getApiKey(), getHttpTools());
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
     * Test of getGenreList method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetGenreList() throws MovieDbException {
        LOG.info("getGenreList");
        TmdbResultsList<Genre> result = instance.getGenreList(LANGUAGE_DEFAULT);
        assertTrue("No genres found", !result.getResults().isEmpty());
    }

    /**
     * Test of getGenreMovies method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetGenreMovies() throws MovieDbException {
        LOG.info("getGenreMovies");
        TmdbResultsList<MovieDb> result = instance.getGenreMovies(ID_GENRE_ACTION, LANGUAGE_DEFAULT, 0, Boolean.TRUE);
        assertTrue("No genre movies found", !result.isEmpty());
    }

}
