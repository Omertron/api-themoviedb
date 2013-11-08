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
import com.omertron.themoviedbapi.TheMovieDbApi;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.person.PersonMovieCredits;
import com.omertron.themoviedbapi.model.person.PersonMovieOld;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author stuart.boston
 */
public class TmdbPeopleTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbGenreTest.class);
    // API
    private static TheMovieDbApi tmdb;
    private static final int ID_PERSON_BRUCE_WILLIS = 62;

    public TmdbPeopleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        tmdb = new TheMovieDbApi(API_KEY);
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
     * Test of getPersonMovieOldInfo method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetPersonInfo() throws MovieDbException {
        LOG.info("getPersonInfo");
        PersonMovieOld result = tmdb.getPersonInfo(ID_PERSON_BRUCE_WILLIS);
        assertTrue("Wrong actor returned", result.getId() == ID_PERSON_BRUCE_WILLIS);
    }

    /**
     * Test of getPersonMovieOldCredits method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetPersonMovieCredits() throws MovieDbException {
        LOG.info("getPersonCredits");

        PersonMovieCredits result = tmdb.getPersonMovieCredits(ID_PERSON_BRUCE_WILLIS, "");
        assertFalse("No cast information", result.getCast().isEmpty());
    }

    /**
     * Test of getPersonMovieOldImages method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetPersonImages() throws MovieDbException {
        LOG.info("getPersonImages");

        TmdbResultsList<Artwork> result = tmdb.getPersonImages(ID_PERSON_BRUCE_WILLIS);
        assertTrue("No cast information", result.getResults().size() > 0);
    }

    /**
     * Test of getPersonChanges method, of class TmdbPeople.
     *
     * @throws MovieDbException
     */
    @Ignore("Not ready yet")
    public void testGetPersonChanges() throws MovieDbException {
        LOG.info("getPersonChanges");
        String startDate = "";
        String endDate = "";
        tmdb.getPersonChanges(ID_PERSON_BRUCE_WILLIS, startDate, endDate);
    }

    /**
     * Test of getPersonPopular method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetPersonPopular() throws MovieDbException {
        LOG.info("getPersonPopular");
        int page = 0;
        TmdbResultsList<PersonMovieOld> result = tmdb.getPersonPopular(page);
        assertFalse("No popular people", result.getResults().isEmpty());
    }

    /**
     * Test of getPersonLatest method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetPersonLatest() throws MovieDbException {
        LOG.info("getPersonLatest");

        PersonMovieOld result = tmdb.getPersonLatest();

        assertNotNull("No results found", result);
        assertTrue("No results found", StringUtils.isNotBlank(result.getName()));
    }

}
