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
import com.omertron.themoviedbapi.TestSuite;
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.enumeration.PeopleMethod;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.artwork.ArtworkMedia;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.change.ChangeListItem;
import com.omertron.themoviedbapi.model.credits.CreditBasic;
import com.omertron.themoviedbapi.model.credits.CreditMovieBasic;
import com.omertron.themoviedbapi.model.credits.CreditTVBasic;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.person.PersonCreditList;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.model.person.PersonInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.MethodBase;
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
    private static final List<TestID> TEST_IDS = new ArrayList<>();

    public TmdbPeopleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbPeople(getApiKey(), getHttpTools());
        TEST_IDS.add(new TestID("Bruce Willis", "nm0000246", 62));
        TEST_IDS.add(new TestID("Will Smith", "nm0000226", 2888));
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
     * Test of Append_To_Response method, of class TmdbPeople.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testAppendToResponse() throws MovieDbException {
        LOG.info("appendToResponse");

        String appendToResponse = appendToResponseBuilder(PeopleMethod.class);

        for (TestID test : TEST_IDS) {
            PersonInfo result = instance.getPersonInfo(test.getTmdb(), appendToResponse);
            TestSuite.test(result);
            //TODO: Remove the combined credits skip when working
            TestSuite.testATR(result, PeopleMethod.class, PeopleMethod.COMBINED_CREDITS);
            TestSuite.test(result.getExternalIDs());
            TestSuite.test(result.getImages(), "Images");
            TestSuite.test(result.getMovieCredits(), "Movie Credits");
            TestSuite.test(result.getTvCredits(), "TV Credits");
            TestSuite.test(result.getTaggedImages(), "Tagged Images");
            // There are rarely any changes, so skip this test
        }
    }

    /**
     * Test of getPersonMovieOldInfo method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetPersonInfo() throws MovieDbException {
        LOG.info("getPersonInfo");
        PersonInfo result;

        for (TestID test : TEST_IDS) {
            result = instance.getPersonInfo(test.getTmdb());
            assertEquals("Wrong actor returned", test.getTmdb(), result.getId());
            assertEquals("Missing IMDB", test.getImdb(), result.getImdbId());
            TestSuite.test(result);
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

        for (TestID test : TEST_IDS) {
            PersonCreditList<CreditMovieBasic> result = instance.getPersonMovieCredits(test.getTmdb(), language);
            LOG.info("ID: {}, # Cast: {}, # Crew: {}", result.getId(), result.getCast().size(), result.getCrew().size());
            assertEquals("Incorrect ID", test.getTmdb(), result.getId());
            TestSuite.test(result.getCast(), "Cast");
            TestSuite.test(result.getCrew(), "Crew");

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

        for (TestID test : TEST_IDS) {
            PersonCreditList<CreditTVBasic> result = instance.getPersonTVCredits(test.getTmdb(), language);
            LOG.info("ID: {}, # Cast: {}, # Crew: {}", result.getId(), result.getCast().size(), result.getCrew().size());
            assertEquals("Incorrect ID", test.getTmdb(), result.getId());
            TestSuite.test(result.getCast(), "Cast");
            TestSuite.test(result.getCrew(), "Crew");

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

        for (TestID test : TEST_IDS) {
            PersonCreditList<CreditBasic> result = instance.getPersonCombinedCredits(test.getTmdb(), language);
            LOG.info("ID: {}, # Cast: {}, # Crew: {}", result.getId(), result.getCast().size(), result.getCrew().size());
            assertEquals("Incorrect ID", test.getTmdb(), result.getId());
            TestSuite.test(result.getCast(), "Cast");
            TestSuite.test(result.getCrew(), "Crew");

            boolean checkedMovie = false;
            boolean checkedTV = false;

            for (CreditBasic p : result.getCast()) {
                if (!checkedMovie && p.getMediaType() == MediaType.MOVIE) {
                    CreditMovieBasic c = (CreditMovieBasic) p;
                    assertTrue("No Movie title", StringUtils.isNotBlank(c.getTitle()));
                    checkedMovie = true;
                }

                if (!checkedTV && p.getMediaType() == MediaType.TV) {
                    CreditTVBasic c = (CreditTVBasic) p;
                    assertTrue("No TV name", StringUtils.isNotBlank(c.getName()));
                    checkedTV = true;
                }

                if (checkedMovie && checkedTV) {
                    break;
                }
            }

            checkedMovie = false;
            checkedTV = false;
            for (CreditBasic p : result.getCrew()) {
                if (!checkedMovie && p.getMediaType() == MediaType.MOVIE) {
                    CreditMovieBasic c = (CreditMovieBasic) p;
                    assertTrue("No title", StringUtils.isNotBlank(c.getTitle()));
                    assertTrue("No department", StringUtils.isNotBlank(c.getDepartment()));
                    checkedMovie = true;
                }

                if (!checkedTV && p.getMediaType() == MediaType.TV) {
                    CreditTVBasic c = (CreditTVBasic) p;
                    assertTrue("No name", StringUtils.isNotBlank(c.getName()));
                    assertTrue("No department", StringUtils.isNotBlank(c.getDepartment()));
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

        for (TestID test : TEST_IDS) {
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

        for (TestID test : TEST_IDS) {
            ResultList<Artwork> result = instance.getPersonImages(test.getTmdb());
            TestSuite.test(result, "Images");
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

        for (TestID test : TEST_IDS) {
            ResultList<ArtworkMedia> result = instance.getPersonTaggedImages(test.getTmdb(), page, language);
            TestSuite.test(result, "Tagged");
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
        ResultList<ChangeListItem> changeList = chgs.getChangeList(MethodBase.PERSON, null, null, null);
        LOG.info("Found {} person changes to check", changeList.getResults().size());

        int count = 1;
        ResultList<ChangeKeyItem> result;
        for (ChangeListItem item : changeList.getResults()) {
            result = instance.getPersonChanges(item.getId(), startDate, endDate);
            for (ChangeKeyItem ci : result.getResults()) {
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
        ResultList<PersonFind> result = instance.getPersonPopular(page);
        TestSuite.test(result, "Popular");
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
        PersonInfo result = instance.getPersonLatest();
        // All we get on the latest person is usually id and name
        assertTrue("No id", result.getId() > 0);
        assertTrue("No name", StringUtils.isNotBlank(result.getName()));
    }

}
