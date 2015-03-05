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
import static com.omertron.themoviedbapi.AbstractTests.doConfiguration;
import static com.omertron.themoviedbapi.AbstractTests.getApiKey;
import static com.omertron.themoviedbapi.AbstractTests.getHttpTools;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestID;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Stuart.Boston
 */
public class TmdbTVSeasonsTest extends AbstractTests {

    private static TmdbTVSeasons instance;
    private static final List<TestID> TV_IDS = new ArrayList<TestID>();

    public TmdbTVSeasonsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbTVSeasons(getApiKey(), getHttpTools());

        TV_IDS.add(new TestID("The Walking Dead", "tt1520211", 1402, "Andrew Lincoln"));
//        TV_IDS.add(new TestID("Supernatural", "tt0460681", 1622,"Misha Collins"));
//        TV_IDS.add(new TestID("The Big Bang Theory", "tt0898266", 1418,"Kaley Cuoco"));
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
     * Test of getSeasonInfo method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonInfo() throws MovieDbException {
        System.out.println("getSeasonInfo");
        int tvID = 0;
        int seasonNumber = 0;
        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : TV_IDS) {
            String result = instance.getSeasonInfo(tvID, seasonNumber, language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeasonChanges method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonChanges() throws MovieDbException {
        System.out.println("getSeasonChanges");
        int tvID = 0;
        String startDate = "";
        String endDate = "";

        for (TestID test : TV_IDS) {
            String result = instance.getSeasonChanges(tvID, startDate, endDate);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeasonAccountState method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonAccountState() throws MovieDbException {
        System.out.println("getSeasonAccountState");
        int tvID = 0;
        String sessionID = "";

        for (TestID test : TV_IDS) {
            String result = instance.getSeasonAccountState(tvID, sessionID);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeasonCredits method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonCredits() throws MovieDbException {
        System.out.println("getSeasonCredits");
        int tvID = 0;
        int seasonNumber = 0;

        for (TestID test : TV_IDS) {
            String result = instance.getSeasonCredits(tvID, seasonNumber);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeasonExternalID method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonExternalID() throws MovieDbException {
        System.out.println("getSeasonExternalID");
        int tvID = 0;
        int seasonNumber = 0;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            String result = instance.getSeasonExternalID(tvID, seasonNumber, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeasonImages method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonImages() throws MovieDbException {
        System.out.println("getSeasonImages");
        int tvID = 0;
        int seasonNumber = 0;
        String language = LANGUAGE_DEFAULT;
        String[] includeImageLanguage = null;

        for (TestID test : TV_IDS) {
            String result = instance.getSeasonImages(tvID, seasonNumber, language, includeImageLanguage);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeasonVideos method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonVideos() throws MovieDbException {
        System.out.println("getSeasonVideos");
        int tvID = 0;
        int seasonNumber = 0;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            String result = instance.getSeasonVideos(tvID, seasonNumber, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
