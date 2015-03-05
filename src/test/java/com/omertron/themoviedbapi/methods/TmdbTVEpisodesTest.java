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
public class TmdbTVEpisodesTest extends AbstractTests {

    private static TmdbTVEpisodes instance;
    private static final List<TestID> TV_IDS = new ArrayList<TestID>();

    public TmdbTVEpisodesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbTVEpisodes(getApiKey(), getHttpTools());

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
     * Test of getEpisodeInfo method, of class TmdbTVEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeInfo() throws MovieDbException {
        System.out.println("getEpisodeInfo");
        int tvID = 0;
        int seasonNumber = 0;
        int episodeNumber = 0;
        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : TV_IDS) {
            String result = instance.getEpisodeInfo(tvID, seasonNumber, episodeNumber, language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEpisodeChanges method, of class TmdbTVEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeChanges() throws MovieDbException {
        System.out.println("getEpisodeChanges");
        int episodeID = 0;
        String startDate = "";
        String endDate = "";

        for (TestID test : TV_IDS) {
            String result = instance.getEpisodeChanges(episodeID, startDate, endDate);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEpisodeAccountState method, of class TmdbTVEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeAccountState() throws MovieDbException {
        System.out.println("getEpisodeAccountState");
        int tvID = 0;
        int seasonNumber = 0;
        int episodeNumber = 0;
        String sessionID = "";

        for (TestID test : TV_IDS) {
            String result = instance.getEpisodeAccountState(tvID, seasonNumber, episodeNumber, sessionID);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEpisodeCredits method, of class TmdbTVEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeCredits() throws MovieDbException {
        System.out.println("getEpisodeCredits");
        int tvID = 0;
        int seasonNumber = 0;
        int episodeNumber = 0;

        for (TestID test : TV_IDS) {
            String result = instance.getEpisodeCredits(tvID, seasonNumber, episodeNumber);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEpisodeExternalID method, of class TmdbTVEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeExternalID() throws MovieDbException {
        System.out.println("getEpisodeExternalID");
        int tvID = 0;
        int seasonNumber = 0;
        int episodeNumber = 0;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            String result = instance.getEpisodeExternalID(tvID, seasonNumber, episodeNumber, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEpisodeImages method, of class TmdbTVEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeImages() throws MovieDbException {
        System.out.println("getEpisodeImages");
        int tvID = 0;
        int seasonNumber = 0;
        int episodeNumber = 0;

        for (TestID test : TV_IDS) {
            String result = instance.getEpisodeImages(tvID, seasonNumber, episodeNumber);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of postEpisodeRating method, of class TmdbTVEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testPostEpisodeRating() throws MovieDbException {
        System.out.println("postEpisodeRating");
        int tvID = 0;
        int seasonNumber = 0;
        int episodeNumber = 0;
        int rating = 0;
        String sessionID = "";
        String guestSessionID = "";

        for (TestID test : TV_IDS) {
            String result = instance.postEpisodeRating(tvID, seasonNumber, episodeNumber, rating, sessionID, guestSessionID);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEpisodeVideos method, of class TmdbTVEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeVideos() throws MovieDbException {
        System.out.println("getEpisodeVideos");
        int tvID = 0;
        int seasonNumber = 0;
        int episodeNumber = 0;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            String result = instance.getEpisodeVideos(tvID, seasonNumber, episodeNumber, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
