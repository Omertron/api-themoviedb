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
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.artwork.ArtworkMedia;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.change.ChangeListItem;
import com.omertron.themoviedbapi.model.person.CreditBasic;
import com.omertron.themoviedbapi.model.person.CreditMovieBasic;
import com.omertron.themoviedbapi.model.person.CreditTVBasic;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.person.Person;
import com.omertron.themoviedbapi.model.person.PersonCredits;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.wrapper.WrapperChanges;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
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
public class TmdbPeopleTest extends AbstractTests {

    private static TmdbPeople instance;
    private static final List<TestID> testIDs = new ArrayList<TestID>();

    public TmdbPeopleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbPeople(getApiKey(), getHttpTools());
        testIDs.add(new TestID("Bruce Willis", "nm0000246", 62));
//        testIDs.add(new TestID("Will Smith", "nm0000226", 2888));
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
    @Test
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

            // Check that we have the movie specific fields
            assertTrue("No title", StringUtils.isNotBlank(result.getCast().get(0).getTitle()));
            assertTrue("No title", StringUtils.isNotBlank(result.getCrew().get(0).getTitle()));
        }
    }

    /**
     * Test of getPersonTVCredits method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
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

            // Check that we have the TV specific fields
            assertTrue("No title", StringUtils.isNotBlank(result.getCast().get(0).getName()));
            assertTrue("No title", StringUtils.isNotBlank(result.getCrew().get(0).getName()));
        }
    }

    /**
     * Test of getPersonCombinedCredits method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetPersonCombinedCredits() throws MovieDbException {
        LOG.info("getPersonCombinedCredits");
        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : testIDs) {
            PersonCredits<CreditBasic> result = instance.getPersonCombinedCredits(test.getTmdb(), language, appendToResponse);
            LOG.info("ID: {}, # Cast: {}, # Crew: {}", result.getId(), result.getCast().size(), result.getCrew().size());
            assertEquals("Incorrect ID", test.getTmdb(), result.getId());
            assertFalse("No cast", result.getCast().isEmpty());
            assertFalse("No crew", result.getCrew().isEmpty());

            boolean checkedMovie = false;
            boolean checkedTV = false;

            for (CreditBasic p : result.getCast()) {
                if (!checkedMovie && p.getMediaType() == MediaType.MOVIE) {
                    CreditMovieBasic c = (CreditMovieBasic) p;
                    assertTrue("No title", StringUtils.isNotBlank(c.getTitle()));
                    checkedMovie = true;
                }

                if (!checkedTV && p.getMediaType() == MediaType.TV) {
                    CreditTVBasic c = (CreditTVBasic) p;
                    assertTrue("No name", StringUtils.isNotBlank(c.getName()));
                    checkedTV = true;
                }

                if (checkedMovie && checkedTV) {
                    break;
                }
            }

        }
    }

    /**
     * Test of getPersonExternalIds method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
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
    @Test
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
    @Test
    public void testGetPersonTaggedImages() throws MovieDbException {
        LOG.info("getPersonTaggedImages");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : testIDs) {
            TmdbResultsList<ArtworkMedia> result = instance.getPersonTaggedImages(test.getTmdb(), page, language);
            assertFalse("No images", result.isEmpty());
            for (ArtworkMedia am : result.getResults()) {
                assertTrue("No ID", StringUtils.isNotBlank(am.getId()));
                assertTrue("No file path", StringUtils.isNotBlank(am.getFilePath()));
            }
        }
    }

    /**
     * Test of getPersonChanges method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetPersonChanges() throws MovieDbException {
        LOG.info("getPersonChanges");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(DateUtils.addDays(new Date(), -14));
        String endDate = "";
        int maxCheck = 5;

        TmdbChanges chgs = new TmdbChanges(getApiKey(), getHttpTools());
        TmdbResultsList<ChangeListItem> changeList = chgs.getChangeList(MethodBase.PERSON, null, null, null);
        LOG.info("Found {} person changes to check", changeList.getResults().size());

        int count = 1;
        WrapperChanges result;
        for (ChangeListItem item : changeList.getResults()) {
            result = instance.getPersonChanges(item.getId(), startDate, endDate);
            for (ChangeKeyItem ci : result.getChangedItems()) {
                assertNotNull("Null changes", ci);
            }

            if (count++ > maxCheck) {
                break;
            }
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
        Integer page = null;
        TmdbResultsList<PersonFind> result = instance.getPersonPopular(page);
        assertFalse("No results", result.isEmpty());
        assertTrue("No results", result.getResults().size() > 0);
        for (PersonFind p : result.getResults()) {
            assertFalse("No known for entries", p.getKnownFor().isEmpty());
            LOG.info("{} ({}) = {}", p.getName(), p.getId(), p.getKnownFor().size());
        }
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
        assertTrue("No ID", result.getId() > 0);
        assertTrue("No name!", StringUtils.isNotBlank(result.getName()));
    }

}
