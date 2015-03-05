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
import com.omertron.themoviedbapi.ArtworkResults;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestID;
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.media.MediaCreditCast;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.tv.TVSeasonInfo;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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
        TV_IDS.add(new TestID("Supernatural", "tt0460681", 1622, "Misha Collins"));
        TV_IDS.add(new TestID("The Big Bang Theory", "tt0898266", 1418, "Kaley Cuoco"));
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
        LOG.info("getSeasonInfo");

        int seasonNumber = 1;
        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : TV_IDS) {
            TVSeasonInfo result = instance.getSeasonInfo(test.getTmdb(), seasonNumber, language, appendToResponse);
            assertTrue("No ID", result.getId() > 0);
            assertTrue("No name", StringUtils.isNotBlank(result.getName()));
            assertTrue("No overview", StringUtils.isNotBlank(result.getOverview()));
            assertTrue("No episodes", result.getEpisodes().size() > 0);
        }
    }

    /**
     * Test of getSeasonChanges method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonChanges() throws MovieDbException {
        LOG.info("getSeasonChanges");
        // This is too empherial to test
    }

    /**
     * Test of getSeasonAccountState method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonAccountState() throws MovieDbException {
        LOG.info("getSeasonAccountState");

        for (TestID test : TV_IDS) {
            MediaState result = instance.getSeasonAccountState(test.getTmdb(), getSessionId());
            assertNotNull("Null result", result);
            assertTrue("Invalid rating", result.getRated() > -2f);
        }
    }

    /**
     * Test of getSeasonCredits method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonCredits() throws MovieDbException {
        LOG.info("getSeasonCredits");

        int seasonNumber = 0;

        for (TestID test : TV_IDS) {
            MediaCreditList result = instance.getSeasonCredits(test.getTmdb(), seasonNumber);
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
     * Test of getSeasonExternalID method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonExternalID() throws MovieDbException {
        LOG.info("getSeasonExternalID");

        int seasonNumber = 0;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            ExternalID result = instance.getSeasonExternalID(test.getTmdb(), seasonNumber, language);
            assertEquals("Wrong IMDB ID", test.getImdb(), result.getImdbId());
        }
    }

    /**
     * Test of getSeasonImages method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonImages() throws MovieDbException {
        LOG.info("getSeasonImages");

        int seasonNumber = 1;
        String language = LANGUAGE_DEFAULT;
        String[] includeImageLanguage = null;

        ArtworkResults results = new ArtworkResults();

        for (TestID test : TV_IDS) {
            TmdbResultsList<Artwork> result = instance.getSeasonImages(test.getTmdb(), seasonNumber, language, includeImageLanguage);
            assertFalse("No artwork", result.isEmpty());
            for (Artwork artwork : result.getResults()) {
                results.found(artwork.getArtworkType());
            }

            // We should only have posters
            results.validateResults(ArtworkType.POSTER);
        }
    }

    /**
     * Test of getSeasonVideos method, of class TmdbTVSeasons.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSeasonVideos() throws MovieDbException {
        LOG.info("getSeasonVideos");

        int seasonNumber = 0;
        String language = LANGUAGE_DEFAULT;
        boolean found = false;

        for (TestID test : TV_IDS) {
            TmdbResultsList<Video> result = instance.getSeasonVideos(test.getTmdb(), seasonNumber, language);
            found = found || !result.isEmpty();
        }

        assertTrue("No videos", found);
    }

}
