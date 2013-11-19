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
import static com.omertron.themoviedbapi.TheMovieDbApiTest.LANGUAGE_DEFAULT;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.LANGUAGE_ENGLISH;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.LANGUAGE_RUSSIAN;
import com.omertron.themoviedbapi.model.Collection;
import com.omertron.themoviedbapi.model.Company;
import com.omertron.themoviedbapi.model.Keyword;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.model.movie.MovieList;
import com.omertron.themoviedbapi.model.person.PersonMovieOld;
import com.omertron.themoviedbapi.model.tv.TVSeriesBasic;
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
public class TmdbSearchTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbGenresTest.class);
    // API
    private static TmdbSearch instance;
    private static final String COMPANY_NAME = "Marvel Studios";

    public TmdbSearchTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbSearch(API_KEY, null);
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
     * Test of searchMovie method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testSearchMovie() throws MovieDbException {
        LOG.info("searchMovie");

        // Try a movie with less than 1 page of results
        TmdbResultsList<MovieDb> movieList = instance.searchMovie("Blade Runner", 0, "", true, 0);
//        List<MovieDb> movieList = tmdb.searchMovie("Blade Runner", "", true);
        assertTrue("No movies found, should be at least 1", movieList.getResults().size() > 0);

        // Try a russian langugage movie
        movieList = instance.searchMovie("О чём говорят мужчины", 0, LANGUAGE_RUSSIAN, true, 0);
        assertTrue("No 'RU' movies found, should be at least 1", movieList.getResults().size() > 0);

        // Try a movie with more than 20 results
        movieList = instance.searchMovie("Star Wars", 0, LANGUAGE_ENGLISH, false, 0);
        assertTrue("Not enough movies found, should be over 15, found " + movieList.getResults().size(), movieList.getResults().size() >= 15);
    }

    /**
     * Test of searchTv method, of class TmdbSearch.
     *
     * @throws MovieDbException
     */
    @Test
    public void testSearchTv() throws MovieDbException {
        LOG.info("searchTv");
        TmdbResultsList<TVSeriesBasic> results = instance.searchTv("Big Bang Theory", 0, LANGUAGE_DEFAULT, null, 0);
        assertFalse("No shows found, should be at least 1", results.getResults().isEmpty());
    }

    /**
     * Test of searchCollection method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testSearchCollection() throws MovieDbException {
        LOG.info("searchCollection");
        String query = "batman";
        int page = 0;
        TmdbResultsList<Collection> result = instance.searchCollection(query, LANGUAGE_DEFAULT, page);
        assertFalse("No collections found", result == null);
        assertTrue("No collections found", result.getResults().size() > 0);
    }

    /**
     * Test of searchPeople method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testSearchPeople() throws MovieDbException {
        LOG.info("searchPeople");
        String personName = "Bruce Willis";
        boolean includeAdult = false;
        TmdbResultsList<PersonMovieOld> result = instance.searchPeople(personName, includeAdult, 0);
        assertTrue("Couldn't find the person", result.getResults().size() > 0);
    }

    /**
     * Test of searchList method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testSearchList() throws MovieDbException {
        LOG.info("searchList");
        String query = "watch";
        int page = 0;
        TmdbResultsList<MovieList> result = instance.searchList(query, LANGUAGE_DEFAULT, page);
        assertFalse("No lists found", result.getResults() == null);
        assertTrue("No lists found", result.getResults().size() > 0);
    }

    /**
     * Test of searchCompanies method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testSearchCompanies() throws MovieDbException {
        LOG.info("searchCompanies");
        TmdbResultsList<Company> result = instance.searchCompanies(COMPANY_NAME, 0);
        assertTrue("No company information found", !result.getResults().isEmpty());
    }

    /**
     * Test of searchKeyword method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testSearchKeyword() throws MovieDbException {
        LOG.info("searchKeyword");
        String query = "action";
        int page = 0;
        TmdbResultsList<Keyword> result = instance.searchKeyword(query, page);
        assertFalse("No keywords found", result.getResults() == null);
        assertTrue("No keywords found", result.getResults().size() > 0);
    }

}
