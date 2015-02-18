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
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.Person;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbPeopleTest extends AbstractTests {

    private static TmdbPeople instance;
    private static final int ID_BRUCE_WILLIS = 62;
    private static final int ID_SEAN_BEAN = 48;
    private static final int ID_DICK_WOLF = 117443;

    public TmdbPeopleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbPeople(getApiKey(), getHttpTools());
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
//    @Test
    public void testGetPersonInfo() throws MovieDbException {
        LOG.info("getPersonInfo");
        Person result = instance.getPersonInfo(ID_BRUCE_WILLIS);
        assertTrue("Wrong actor returned", result.getId() == ID_BRUCE_WILLIS);
        assertTrue("Missing bio", StringUtils.isNotBlank(result.getBiography()));
        assertTrue("Missing birthday", StringUtils.isNotBlank(result.getBirthday()));
        assertTrue("Missing homepage", StringUtils.isNotBlank(result.getHomepage()));
        assertTrue("Missing IMDB", StringUtils.isNotBlank(result.getImdbId()));
        assertTrue("Missing name", StringUtils.isNotBlank(result.getName()));
        assertTrue("Missing birth place", StringUtils.isNotBlank(result.getBirthplace()));
        assertTrue("Missing artwork", StringUtils.isNotBlank(result.getProfilePath()));
        assertTrue("Missing bio", result.getPopularity() > 0F);
    }

    /**
     * Test of getPersonMovieOldImages method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
//    @Test
    public void testGetPersonImages() throws MovieDbException {
        LOG.info("getPersonImages");

        TmdbResultsList<Artwork> result = instance.getPersonImages(ID_BRUCE_WILLIS);
        assertTrue("No cast information", result.getResults().size() > 0);
        for (Artwork artwork : result.getResults()) {
            assertTrue("Artwork is blank", StringUtils.isNotBlank(artwork.getFilePath()));
        }

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
        TmdbResultsList<Person> result = instance.getPersonPopular(page);
        assertFalse("No popular people", result.getResults().isEmpty());
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonLatest method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetPersonLatest() throws MovieDbException {
        LOG.info("getPersonLatest");

        Person result = instance.getPersonLatest();

        assertNotNull("No results found", result);
        assertTrue("No results found", StringUtils.isNotBlank(result.getName()));
        fail("The test case is a prototype.");
    }

}
