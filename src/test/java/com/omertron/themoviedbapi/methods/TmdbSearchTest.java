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
import com.omertron.themoviedbapi.TestSuite;
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.enumeration.SearchType;
import com.omertron.themoviedbapi.model.collection.Collection;
import com.omertron.themoviedbapi.model.company.Company;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.list.UserList;
import com.omertron.themoviedbapi.model.media.MediaBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.results.ResultList;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbSearchTest extends AbstractTests {

    private static TmdbSearch instance;

    public TmdbSearchTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbSearch(getApiKey(), getHttpTools());
    }

    /**
     * Test of searchCompanies method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testSearchCompanies() throws MovieDbException {
        LOG.info("searchCompanies");
        ResultList<Company> result = instance.searchCompanies("Marvel Studios", 0);
        TestSuite.test(result, "Companies");
        TestSuite.testId(result, 420, "Company");
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
        ResultList<Collection> result = instance.searchCollection(query, page, LANGUAGE_DEFAULT);
        TestSuite.test(result, "Collection");
        TestSuite.testId(result, 263, "Collection");
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
        ResultList<Keyword> result = instance.searchKeyword(query, page);
        TestSuite.test(result, "Keyword");
        TestSuite.testId(result, 207600, "Keyword");
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
        ResultList<UserList> result = instance.searchList(query, page, null);
        TestSuite.test(result, "List");
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
        ResultList<MovieInfo> movieList = instance.searchMovie("Blade Runner", 0, "", null, 0, 0, SearchType.PHRASE);
        TestSuite.test(movieList, "Movie 1");
        assertTrue("No movies found, should be at least 1", movieList.getResults().size() > 0);

        // Try a russian langugage movie
        movieList = instance.searchMovie("О чём говорят мужчины", 0, LANGUAGE_RUSSIAN, null, 0, 0, SearchType.PHRASE);
        TestSuite.test(movieList, "Movie 2");
        assertTrue("No 'RU' movies found, should be at least 1", movieList.getResults().size() > 0);

        // Try a movie with more than 20 results
        movieList = instance.searchMovie("Star Wars", 0, LANGUAGE_ENGLISH, null, 0, 0, SearchType.PHRASE);
        TestSuite.test(movieList, "Movie 3");
        assertTrue("Not enough movies found, should be over 15, found " + movieList.getResults().size(), movieList.getResults().size() >= 15);
    }

    /**
     * Test of searchMulti method, of class TmdbSearch.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testSearchMulti() throws MovieDbException {
        LOG.info("searchMulti");
        String query = "babylon";
        Integer page = null;
        String language = "";
        Boolean includeAdult = null;
        ResultList<MediaBasic> result = instance.searchMulti(query, page, language, includeAdult);
        TestSuite.test(result, "Multi");

        boolean foundTV = false;
        boolean foundMovie = false;
        for (MediaBasic item : result.getResults()) {
            if (foundMovie && foundTV) {
                break;
            }

            if (item.getMediaType() == MediaType.MOVIE) {
                foundMovie = true;
            }

            if (item.getMediaType() == MediaType.TV) {
                foundTV = true;
            }
        }
        assertTrue("Movies not found", foundMovie);
        assertTrue("TV not found", foundTV);
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
        ResultList<PersonFind> result = instance.searchPeople(personName, null, null, SearchType.PHRASE);
        TestSuite.test(result, "People");
        TestSuite.testId(result.getResults(), 62, "People");
    }

    /**
     * Test of searchTV method, of class TmdbSearch.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testSearchTV() throws MovieDbException {
        LOG.info("searchTV");
        String query = "The Walking Dead";
        Integer page = null;
        String language = LANGUAGE_ENGLISH;
        Integer firstAirDateYear = null;
        SearchType searchType = SearchType.PHRASE;
        ResultList<TVBasic> result = instance.searchTV(query, page, language, firstAirDateYear, searchType);
        TestSuite.test(result, "TV");
        TestSuite.testId(result, 1402, "TV Show");
    }

}
