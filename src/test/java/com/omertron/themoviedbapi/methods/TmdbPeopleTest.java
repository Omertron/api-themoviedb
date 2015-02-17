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
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.getApiKey();
import static com.omertron.themoviedbapi.TheMovieDbApiTest.LANGUAGE_DEFAULT;
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.person.NewCast;
import com.omertron.themoviedbapi.model.person.NewPersonCredits;
import com.omertron.themoviedbapi.model.person.Person;
import com.omertron.themoviedbapi.model.person.PersonBasic;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

/**
 *
 * @author stuart.boston
 */
public class TmdbPeopleTest extends AbstractTests{

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbGenresTest.class);
    // API
    private static TmdbPeople instance;
    private static final int ID_BRUCE_WILLIS = 62;
    private static final int ID_SEAN_BEAN = 48;
    private static final int ID_DICK_WOLF = 117443;

    public TmdbPeopleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        TestLogger.Configure();
        instance = new TmdbPeople(getApiKey(),getHttpTools());
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
     * Test of getPersonChanges method, of class TmdbPeople.
     *
     * @throws MovieDbException
     */
    @Ignore("Not ready yet")
    public void testGetPersonChanges() throws MovieDbException {
        LOG.info("getPersonChanges");
        String startDate = "";
        String endDate = "";
        instance.getPersonChanges(ID_BRUCE_WILLIS, startDate, endDate);
    }

    /**
     * Test of getPersonPopular method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetPersonPopular() throws MovieDbException {
        LOG.info("getPersonPopular");
        int page = 0;
        TmdbResultsList<PersonBasic> result = instance.getPersonPopular(page);
        assertFalse("No popular people", result.getResults().isEmpty());
        for (PersonBasic person : result.getResults()) {
            assertTrue("Missing name", StringUtils.isNotBlank(person.getName()));
        }
    }

    /**
     * Test of getPersonLatest method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetPersonLatest() throws MovieDbException {
        LOG.info("getPersonLatest");

        Person result = instance.getPersonLatest();

        assertNotNull("No results found", result);
        assertTrue("No results found", StringUtils.isNotBlank(result.getName()));
    }

    /**
     * Test of getPersonMovieOldCredits method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetPersonMovieCredits() throws MovieDbException {
        LOG.info("getPersonMovieCredits");

        NewPersonCredits result = instance.getPersonMovieCredits(ID_BRUCE_WILLIS, LANGUAGE_DEFAULT);
        assertEquals("Invalid ID", ID_BRUCE_WILLIS, result.getId());
        assertFalse("No cast information returned", result.getCast().isEmpty());
        for(NewCast person:result.getCast()) {
            LOG.info(person.toString());
        }

        assertFalse("No crew information returned", result.getCrew().isEmpty());
    }

    /**
     * Test of getPersonTvCredits method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetPersonTvCredits() throws MovieDbException {
        LOG.info("getPersonTvCredits");

        NewPersonCredits result = instance.getPersonTvCredits(ID_DICK_WOLF, LANGUAGE_DEFAULT);
        assertEquals("Invalid ID", ID_DICK_WOLF, result.getId());
        assertFalse("No cast information returned", result.getCast().isEmpty());
        assertFalse("No crew information returned", result.getCrew().isEmpty());
    }

    /**
     * Test of getPersonCombinedCredits method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetPersonCombinedCredits() throws MovieDbException {
        LOG.info("getPersonCombinedCredits");

        NewPersonCredits result = instance.getPersonCombinedCredits(ID_SEAN_BEAN, LANGUAGE_DEFAULT);
        assertEquals("Invalid ID", ID_SEAN_BEAN, result.getId());
        assertFalse("No cast information returned", result.getCast().isEmpty());
        assertFalse("No crew information returned", result.getCrew().isEmpty());

        LOG.info("{}", result.toString());
    }

}
