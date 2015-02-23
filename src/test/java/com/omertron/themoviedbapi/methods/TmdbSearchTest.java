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
import com.omertron.themoviedbapi.model2.collection.Collection;
import com.omertron.themoviedbapi.model2.company.Company;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.model.MovieList;
import com.omertron.themoviedbapi.model.person.Person;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbSearchTest extends AbstractTests {

    // API
    private static TmdbSearch instance;
    private static final String COMPANY_NAME = "Marvel Studios";

    public TmdbSearchTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbSearch(getApiKey(), getHttpTools());
    }

    @AfterClass
    public static void tearDownClass() {
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
        TmdbResultsList<Person> result = instance.searchPeople(personName, includeAdult, 0);
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
