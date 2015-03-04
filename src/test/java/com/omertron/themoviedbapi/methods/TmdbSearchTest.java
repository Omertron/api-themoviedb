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
import com.omertron.themoviedbapi.enumeration.SearchType;
import com.omertron.themoviedbapi.model.collection.Collection;
import com.omertron.themoviedbapi.model.company.Company;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.list.UserList;
import com.omertron.themoviedbapi.model.media.MediaBasic;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbSearchTest extends AbstractTests {

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

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
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
        assertNotNull("Null results", result);
        assertNotNull("Null company", result.getResults());
        assertFalse("Empty company", result.isEmpty());
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
        TmdbResultsList<Collection> result = instance.searchCollection(query, page, LANGUAGE_DEFAULT);
        assertNotNull("Null results", result);
        assertNotNull("Null collection", result.getResults());
        assertFalse("Empty collection", result.isEmpty());
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
        assertNotNull("Null results", result);
        assertNotNull("Null keyword", result.getResults());
        assertFalse("Empty keyword", result.isEmpty());
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
        TmdbResultsList<UserList> result = instance.searchList(query, page, null);
        assertNotNull("Null results", result);
        assertNotNull("Null list", result.getResults());
        assertFalse("Empty list", result.isEmpty());
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
        TmdbResultsList<MovieDb> movieList = instance.searchMovie("Blade Runner", 0, "", null, 0, 0, SearchType.PHRASE);
        assertTrue("No movies found, should be at least 1", movieList.getResults().size() > 0);

        // Try a russian langugage movie
        movieList = instance.searchMovie("О чём говорят мужчины", 0, LANGUAGE_RUSSIAN, null, 0, 0, SearchType.PHRASE);
        assertTrue("No 'RU' movies found, should be at least 1", movieList.getResults().size() > 0);

        // Try a movie with more than 20 results
        movieList = instance.searchMovie("Star Wars", 0, LANGUAGE_ENGLISH, null, 0, 0, SearchType.PHRASE);
        assertTrue("Not enough movies found, should be over 15, found " + movieList.getResults().size(), movieList.getResults().size() >= 15);
    }

    /**
     * Test of searchMulti method, of class TmdbSearch.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testSearchMulti() throws MovieDbException {
        System.out.println("searchMulti");
        String query = "j";
        Integer page = null;
        String language = "";
        Boolean includeAdult = null;
        TmdbResultsList<MediaBasic> result = instance.searchMulti(query, page, language, includeAdult);
        for (MediaBasic item : result.getResults()) {
            LOG.info("{}", item);
        }
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
        TmdbResultsList<PersonFind> result = instance.searchPeople(personName, null, null, SearchType.PHRASE);
        assertNotNull("Null results", result);
        assertNotNull("Null people", result.getResults());
        assertFalse("Empty people", result.isEmpty());
    }

    /**
     * Test of searchTV method, of class TmdbSearch.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testSearchTV() throws MovieDbException {
        System.out.println("searchTV");
        String query = "The Walking Dead";
        Integer page = null;
        String language = LANGUAGE_ENGLISH;
        Integer firstAirDateYear = null;
        SearchType searchType = SearchType.PHRASE;
        TmdbResultsList<TVBasic> result = instance.searchTV(query, page, language, firstAirDateYear, searchType);
        assertNotNull("Null results", result);
        assertNotNull("Null TV", result.getResults());
        assertFalse("Empty TV", result.isEmpty());
    }

}
