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
import com.omertron.themoviedbapi.TestID;
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import com.omertron.themoviedbapi.model2.MediaBasic;
import com.omertron.themoviedbapi.model2.artwork.Artwork;
import com.omertron.themoviedbapi.model2.artwork.ArtworkMedia;
import com.omertron.themoviedbapi.model2.person.CreditMovieBasic;
import com.omertron.themoviedbapi.model2.person.CreditTVBasic;
import com.omertron.themoviedbapi.model2.person.ExternalID;
import com.omertron.themoviedbapi.model2.person.Person;
import com.omertron.themoviedbapi.model2.person.PersonCredits;
import com.omertron.themoviedbapi.model2.person.PersonFind;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    private static final int ID_DICK_WOLF = 117443;
    private static final List<TestID> testIDs = new ArrayList<TestID>();

    public TmdbPeopleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbPeople(getApiKey(), getHttpTools());
        testIDs.add(new TestID("Bruce Willis", "nm0000246", 62));
        testIDs.add(new TestID("Will Smith", "nm0000226", 2888));
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
        Person result;

        for (TestID test : testIDs) {
            result = instance.getPersonInfo(test.getTmdb());
            assertEquals("Wrong actor returned", test.getTmdb(), result.getId());
            assertTrue("Missing bio", StringUtils.isNotBlank(result.getBiography()));
            assertTrue("Missing birthday", StringUtils.isNotBlank(result.getBirthday()));
            assertTrue("Missing homepage", StringUtils.isNotBlank(result.getHomepage()));
            assertEquals("Missing IMDB", test.getImdb(), result.getImdbId());
            assertTrue("Missing name", StringUtils.isNotBlank(result.getName()));
            assertTrue("Missing birth place", StringUtils.isNotBlank(result.getPlaceOfBirth()));
            assertTrue("Missing artwork", StringUtils.isNotBlank(result.getProfilePath()));
            assertTrue("Missing bio", result.getPopularity() > 0F);
        }
    }

    /**
     * Test of getPersonMovieCredits method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetPersonMovieCredits() throws MovieDbException {
        LOG.info("getPersonMovieCredits");
        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : testIDs) {
            PersonCredits<CreditMovieBasic> result = instance.getPersonMovieCredits(test.getTmdb(), language, appendToResponse);
            LOG.info("ID: {}, # Cast: {}, # Crew: {}", result.getId(), result.getCast().size(), result.getCrew().size());
            assertEquals("Incorrect ID", test.getTmdb(), result.getId());
            assertFalse("No cast", result.getCast().isEmpty());
            assertFalse("No crew", result.getCrew().isEmpty());
        }
    }

    /**
     * Test of getPersonTVCredits method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetPersonTVCredits() throws MovieDbException {
        LOG.info("getPersonTVCredits");
        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : testIDs) {
            PersonCredits<CreditTVBasic> result = instance.getPersonTVCredits(test.getTmdb(), language, appendToResponse);
            LOG.info("ID: {}, # Cast: {}, # Crew: {}", result.getId(), result.getCast().size(), result.getCrew().size());
            assertEquals("Incorrect ID", test.getTmdb(), result.getId());
            assertFalse("No cast", result.getCast().isEmpty());
            assertFalse("No crew", result.getCrew().isEmpty());
        }
    }

    /**
     * Test of getPersonCombinedCredits method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetPersonCombinedCredits() throws MovieDbException {
        LOG.info("getPersonCombinedCredits");
        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : testIDs) {
            PersonCredits<CreditTVBasic> result = instance.getPersonCombinedCredits(test.getTmdb(), language, appendToResponse);
            LOG.info("ID: {}, # Cast: {}, # Crew: {}", result.getId(), result.getCast().size(), result.getCrew().size());
            assertEquals("Incorrect ID", test.getTmdb(), result.getId());
            assertFalse("No cast", result.getCast().isEmpty());
            assertFalse("No crew", result.getCrew().isEmpty());
        }
        fail("Not working");
    }

    /**
     * Test of getPersonExternalIds method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetPersonExternalIds() throws MovieDbException {
        LOG.info("getPersonExternalIds");

        for (TestID test : testIDs) {
            ExternalID result = instance.getPersonExternalIds(test.getTmdb());
            assertEquals("Wrong IMDB ID", test.getImdb(), result.getImdbId());
        }
    }

    /**
     * Test of getPersonImages method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetPersonImages() throws MovieDbException {
        LOG.info("getPersonImages");

        for (TestID test : testIDs) {
            TmdbResultsList<Artwork> result = instance.getPersonImages(test.getTmdb());
            assertFalse("No artwork", result.isEmpty());
            assertEquals("Wrong artwork type", ArtworkType.PROFILE, result.getResults().get(0).getArtworkType());
        }
    }

    /**
     * Test of getPersonTaggedImages method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetPersonTaggedImages() throws MovieDbException {
        LOG.info("getPersonTaggedImages");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : testIDs) {
            TmdbResultsList<ArtworkMedia> result = instance.getPersonTaggedImages(test.getTmdb(), page, language);
            for (ArtworkMedia am : result.getResults()) {
                LOG.info("{}", ToStringBuilder.reflectionToString(am, ToStringStyle.DEFAULT_STYLE));
            }
        }
    }

    /**
     * Test of getPersonChanges method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetPersonChanges() throws MovieDbException {
        LOG.info("getPersonChanges");
        int persondId = 0;
        String startDate = "";
        String endDate = "";

        TmdbResultsList<Person> expResult = null;
        TmdbResultsList<Person> result = instance.getPersonChanges(persondId, startDate, endDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonPopular method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetPersonPopular() throws MovieDbException {
        LOG.info("getPersonPopular");
        Integer page = null;
        TmdbResultsList<PersonFind> result = instance.getPersonPopular(page);
        assertFalse("No results", result.isEmpty());
        assertTrue("No results", result.getResults().size() > 0);
        for (PersonFind p : result.getResults()) {
            LOG.info("{} ({}) = {}", p.getName(), p.getId(), p.getKnownFor().size());
            for (MediaBasic k : p.getKnownFor()) {
                LOG.info("    {}", k.toString());
            }
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

        Person expResult = null;
        Person result = instance.getPersonLatest();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
