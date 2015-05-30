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
import com.omertron.themoviedbapi.ArtworkResults;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestID;
import com.omertron.themoviedbapi.TestSuite;
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import com.omertron.themoviedbapi.enumeration.TVEpisodeMethod;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.credits.MediaCreditCast;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.tv.TVEpisodeInfo;
import com.omertron.themoviedbapi.results.ResultList;
import java.util.ArrayList;
import java.util.List;
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
 * @author Stuart.Boston
 */
public class TmdbEpisodesTest extends AbstractTests {

    private static TmdbEpisodes instance;
    private static final List<TestID> TV_IDS = new ArrayList<>();

    public TmdbEpisodesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbEpisodes(getApiKey(), getHttpTools());

        TV_IDS.add(new TestID("The Walking Dead", "tt1589921", 1402, "Andrew Lincoln"));
        TV_IDS.add(new TestID("Supernatural", "tt0713618", 1622, "Misha Collins"));
        TV_IDS.add(new TestID("The Big Bang Theory", "tt0775431", 1418, "Kaley Cuoco"));
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
     * Test of getEpisodeInfo method, of class TmdbEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeInfo() throws MovieDbException {
        LOG.info("getEpisodeInfo");

        int seasonNumber = 1;
        int episodeNumber = 1;
        String language = LANGUAGE_DEFAULT;
        String appendToResponse = appendToResponseBuilder(TVEpisodeMethod.class);

        for (TestID test : TV_IDS) {
            LOG.info("Testing: {}", test);
            TVEpisodeInfo result = instance.getEpisodeInfo(test.getTmdb(), seasonNumber, episodeNumber, language, appendToResponse);
            TestSuite.test(result);
            TestSuite.testATR(result, TVEpisodeMethod.class, null);
        }
    }

    /**
     * Test of getEpisodeChanges method, of class TmdbEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeChanges() throws MovieDbException {
        LOG.info("getEpisodeChanges");
        // This is too empherial to test
    }

    /**
     * Test of getEpisodeAccountState method, of class TmdbEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeAccountState() throws MovieDbException {
        LOG.info("getEpisodeAccountState");

        int seasonNumber = 1;
        int episodeNumber = 1;

        for (TestID test : TV_IDS) {
            LOG.info("Testing: {}", test);
            MediaState result = instance.getEpisodeAccountState(test.getTmdb(), seasonNumber, episodeNumber, getSessionId());
            assertNotNull("Null result", result);
            assertTrue("Invalid rating", result.getRated() > -2f);
        }
    }

    /**
     * Test of getEpisodeCredits method, of class TmdbEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeCredits() throws MovieDbException {
        LOG.info("getEpisodeCredits");

        int seasonNumber = 1;
        int episodeNumber = 1;

        for (TestID test : TV_IDS) {
            LOG.info("Testing: {}", test);
            MediaCreditList result = instance.getEpisodeCredits(test.getTmdb(), seasonNumber, episodeNumber);
            assertNotNull(result);
            assertFalse(result.getCast().isEmpty());

            boolean found = false;
            for (MediaCreditCast p : result.getCast()) {
                if (test.getOther().equals(p.getName())) {
                    found = true;
                    break;
                }
            }
            assertTrue(test.getOther() + " not found in cast!", found);

            assertFalse(result.getCrew().isEmpty());
            break;
        }
    }

    /**
     * Test of getEpisodeExternalID method, of class TmdbEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeExternalID() throws MovieDbException {
        LOG.info("getEpisodeExternalID");

        int seasonNumber = 1;
        int episodeNumber = 1;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            LOG.info("Testing: {}", test);
            ExternalID result = instance.getEpisodeExternalID(test.getTmdb(), seasonNumber, episodeNumber, language);
            assertEquals("Wrong IMDB ID", test.getImdb(), result.getImdbId());
        }
    }

    /**
     * Test of getEpisodeImages method, of class TmdbEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeImages() throws MovieDbException {
        LOG.info("getEpisodeImages");

        int seasonNumber = 1;
        int episodeNumber = 1;

        for (TestID test : TV_IDS) {
            LOG.info("Testing: {}", test);
            ArtworkResults results = new ArtworkResults();
            ResultList<Artwork> result = instance.getEpisodeImages(test.getTmdb(), seasonNumber, episodeNumber);
            assertFalse("No artwork", result.isEmpty());
            for (Artwork artwork : result.getResults()) {
                results.found(artwork.getArtworkType());
            }

            // We should only have posters & backdrops
            results.validateResults(ArtworkType.STILL);
        }

    }

    /**
     * Test of postEpisodeRating method, of class TmdbEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testPostEpisodeRating() throws MovieDbException {
        LOG.info("postEpisodeRating");

        int seasonNumber = 1;
        int episodeNumber = 1;
        String guestSessionID = null;

        for (TestID test : TV_IDS) {
            LOG.info("Testing: {}", test);
            int rating = TestSuite.randomRating();
            StatusCode result = instance.postEpisodeRating(test.getTmdb(), seasonNumber, episodeNumber, rating, getSessionId(), guestSessionID);
            assertEquals("failed to post rating", 12, result.getCode());
        }
    }

    /**
     * Test of getEpisodeVideos method, of class TmdbEpisodes.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetEpisodeVideos() throws MovieDbException {
        LOG.info("getEpisodeVideos");

        // Game of thrones S03E01 has videos
        int tvID = 1399;
        int seasonNumber = 3;
        int episodeNumber = 1;
        String language = LANGUAGE_DEFAULT;

        ResultList<Video> result = instance.getEpisodeVideos(tvID, seasonNumber, episodeNumber, language);
        TestSuite.test(result, "Videos");
    }

}
