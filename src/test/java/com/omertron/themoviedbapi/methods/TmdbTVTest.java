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
import static com.omertron.themoviedbapi.AbstractTests.getApiKey;
import static com.omertron.themoviedbapi.AbstractTests.getHttpTools;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestID;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.change.ChangeListItem;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.movie.AlternativeTitle;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.wrapper.WrapperChanges;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for the TV Method
 *
 * @author stuart.boston
 */
public class TmdbTVTest extends AbstractTests {

    private static TmdbTV instance;
    private static final List<TestID> TV_IDS = new ArrayList<TestID>();

    public TmdbTVTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbTV(getApiKey(), getHttpTools());

        TV_IDS.add(new TestID("The Walking Dead", "tt1520211", 1402));
//        TV_IDS.add(new TestID("Supernatural", "tt0460681", 1622));
//        TV_IDS.add(new TestID("The Big Bang Theory", "tt0898266", 1418));
    }

    /**
     * Test of getTVInfo method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetTVInfo() throws MovieDbException {
        LOG.info("getTVInfo");

        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : TV_IDS) {
            TVInfo result = instance.getTVInfo(test.getTmdb(), language, appendToResponse);
            assertTrue("No ID", result.getId() > 0);
            assertFalse("No runtime", result.getEpisodeRunTime().isEmpty());
            assertFalse("No genres", result.getGenres().isEmpty());
            assertTrue("No season count", result.getNumberOfSeasons() > 0);
            assertTrue("No episode count", result.getNumberOfEpisodes() > 0);
        }
    }

    /**
     * Test of getTVAccountState method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetTVAccountState() throws MovieDbException {
        LOG.info("getTVAccountState");

        for (TestID test : TV_IDS) {
            MediaState result = instance.getTVAccountState(test.getTmdb(), getSessionId());
            assertNotNull("Null result", result);
            assertTrue("Invalid rating", result.getRated() > -2f);
        }
    }

    /**
     * Test of getTVAlternativeTitles method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetTVAlternativeTitles() throws MovieDbException {
        LOG.info("getTVAlternativeTitles");

        for (TestID test : TV_IDS) {
            TmdbResultsList<AlternativeTitle> result = instance.getTVAlternativeTitles(test.getTmdb());
            assertFalse("No alt titles", result.isEmpty());
        }
    }

    /**
     * Test of getTVChanges method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
//    @Test
    public void testGetTVChanges() throws MovieDbException {
        LOG.info("getTVChanges");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(DateUtils.addDays(new Date(), -14));
        String endDate = "";
        int maxCheck = 5;

        TmdbChanges chgs = new TmdbChanges(getApiKey(), getHttpTools());
        List<ChangeListItem> changeList = chgs.getChangeList(MethodBase.TV, null, null, null);
        LOG.info("Found {} changes to check, will check maximum of {}", changeList.size(), maxCheck);

        int count = 1;
        WrapperChanges result;
        for (ChangeListItem item : changeList) {
            result = instance.getTVChanges(item.getId(), startDate, endDate);
            for (ChangeKeyItem ci : result.getChangedItems()) {
                assertNotNull("Null changes", ci);
            }

            if (count++ > maxCheck) {
                break;
            }
        }
    }

    /**
     * Test of getTVContentRatings method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVContentRatings() throws MovieDbException {
        LOG.info("getTVContentRatings");

        for (TestID test : TV_IDS) {
            String result = instance.getTVContentRatings(test.getTmdb());
        }
    }

    /**
     * Test of getTVCredits method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVCredits() throws MovieDbException {
        LOG.info("getTVCredits");

        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : TV_IDS) {
            String result = instance.getTVCredits(test.getTmdb(), language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVExternalIDs method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVExternalIDs() throws MovieDbException {
        LOG.info("getTVExternalIDs");

        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            String result = instance.getTVExternalIDs(test.getTmdb(), language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVImages method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVImages() throws MovieDbException {
        LOG.info("getTVImages");

        String language = LANGUAGE_DEFAULT;
        String[] includeImageLanguage = null;

        for (TestID test : TV_IDS) {
            String result = instance.getTVImages(test.getTmdb(), language, includeImageLanguage);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVKeywords method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVKeywords() throws MovieDbException {
        LOG.info("getTVKeywords");

        String[] appendToResponse = null;

        for (TestID test : TV_IDS) {
            String result = instance.getTVKeywords(test.getTmdb(), appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of postTVRating method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testPostTVRating() throws MovieDbException {
        LOG.info("postTVRating");

        int rating = 0;

        for (TestID test : TV_IDS) {
            String result = instance.postTVRating(test.getTmdb(), rating, getSessionId(), null);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVSimilar method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVSimilar() throws MovieDbException {
        LOG.info("getTVSimilar");

        Integer page = null;
        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : TV_IDS) {
            String result = instance.getTVSimilar(test.getTmdb(), page, language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVTranslations method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVTranslations() throws MovieDbException {
        LOG.info("getTVTranslations");

        for (TestID test : TV_IDS) {
            String result = instance.getTVTranslations(test.getTmdb());
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVVideos method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVVideos() throws MovieDbException {
        LOG.info("getTVVideos");

        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            String result = instance.getTVVideos(test.getTmdb(), language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVLatest method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVLatest() throws MovieDbException {
        LOG.info("getTVLatest");

        for (TestID test : TV_IDS) {
            String result = instance.getTVLatest();
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVOnTheAir method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVOnTheAir() throws MovieDbException {
        LOG.info("getTVOnTheAir");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            String result = instance.getTVOnTheAir(page, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVAiringToday method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVAiringToday() throws MovieDbException {
        LOG.info("getTVAiringToday");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;
        String timezone = "";

        for (TestID test : TV_IDS) {
            String result = instance.getTVAiringToday(page, language, timezone);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVTopRated method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVTopRated() throws MovieDbException {
        LOG.info("getTVTopRated");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            String result = instance.getTVTopRated(page, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTVPopular method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTVPopular() throws MovieDbException {
        LOG.info("getTVPopular");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            String result = instance.getTVPopular(page, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
