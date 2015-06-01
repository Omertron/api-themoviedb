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
import com.omertron.themoviedbapi.enumeration.TVMethod;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.change.ChangeListItem;
import com.omertron.themoviedbapi.model.credits.MediaCreditCast;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.media.AlternativeTitle;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.media.Translation;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.person.ContentRating;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.MethodBase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for the TV Method
 *
 * @author stuart.boston
 */
public class TmdbTVTest extends AbstractTests {

    private static TmdbTV instance;
    private static final List<TestID> TV_IDS = new ArrayList<>();

    public TmdbTVTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbTV(getApiKey(), getHttpTools());

        TV_IDS.add(new TestID("The Walking Dead", "tt1520211", 1402, "Andrew Lincoln"));
        TV_IDS.add(new TestID("Supernatural", "tt0460681", 1622, "Misha Collins"));
        TV_IDS.add(new TestID("The Big Bang Theory", "tt0898266", 1418, "Kaley Cuoco"));
    }

    /**
     * Test of Append_To_Response method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testAppendToResponse() throws MovieDbException {
        LOG.info("appendToResponse");

        String language = LANGUAGE_DEFAULT;
        String appendToResponse = appendToResponseBuilder(TVMethod.class);

        for (TestID test : TV_IDS) {
            // Just test Waling Dead
            if (test.getTmdb() != 1402) {
                continue;
            }

            TVInfo result = instance.getTVInfo(test.getTmdb(), language, appendToResponse);
            TestSuite.test(result);
            TestSuite.testATR(result, TVMethod.class, null);
            TestSuite.test(result.getAlternativeTitles(), "Alt titles");
            TestSuite.test(result.getContentRatings(), "Content Ratings");
            TestSuite.test(result.getCredits());
            TestSuite.test(result.getExternalIDs());
            TestSuite.test(result.getImages(), "Images");
            TestSuite.test(result.getKeywords(), "Keywords");
            TestSuite.test(result.getVideos(), "Videos");
            TestSuite.test(result.getTranslations(), "Translations");
            TestSuite.test(result.getSimilarTV(), "Similar");
            // There are rarely any changes, so skip this test
        }
    }

    /**
     * Test of getTVInfo method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVInfo() throws MovieDbException {
        LOG.info("getTVInfo");

        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : TV_IDS) {
            TVInfo result = instance.getTVInfo(test.getTmdb(), language, appendToResponse);
            TestSuite.test(result);
        }
    }

    /**
     * Test of getTVAccountState method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
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
    @Test
    public void testGetTVAlternativeTitles() throws MovieDbException {
        LOG.info("getTVAlternativeTitles");

        for (TestID test : TV_IDS) {
            ResultList<AlternativeTitle> result = instance.getTVAlternativeTitles(test.getTmdb());
            TestSuite.test(result, "TV Alt Titles");
        }
    }

    /**
     * Test of getTVChanges method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVChanges() throws MovieDbException {
        LOG.info("getTVChanges");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(DateUtils.addDays(new Date(), -14));
        String endDate = "";
        int maxCheck = 5;

        TmdbChanges chgs = new TmdbChanges(getApiKey(), getHttpTools());
        ResultList<ChangeListItem> changeList = chgs.getChangeList(MethodBase.TV, null, null, null);
        LOG.info("Found {} changes to check, will check maximum of {}", changeList.getResults().size(), maxCheck);

        int count = 1;
        ResultList<ChangeKeyItem> result;
        for (ChangeListItem item : changeList.getResults()) {
            result = instance.getTVChanges(item.getId(), startDate, endDate);
            for (ChangeKeyItem ci : result.getResults()) {
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
            ResultList<ContentRating> result = instance.getTVContentRatings(test.getTmdb());
            TestSuite.test(result, "TV Content Rating");
            assertTrue("No language", StringUtils.isNotBlank(result.getResults().get(0).getCountry()));
            assertTrue("No rating", StringUtils.isNotBlank(result.getResults().get(0).getRating()));
        }
    }

    /**
     * Test of getTVCredits method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVCredits() throws MovieDbException {
        LOG.info("getTVCredits");

        for (TestID test : TV_IDS) {
            MediaCreditList result = instance.getTVCredits(test.getTmdb(), LANGUAGE_DEFAULT);
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
     * Test of getTVExternalIDs method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVExternalIDs() throws MovieDbException {
        LOG.info("getTVExternalIDs");

        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            ExternalID result = instance.getTVExternalIDs(test.getTmdb(), language);
            assertEquals("Wrong IMDB ID", test.getImdb(), result.getImdbId());
        }
    }

    /**
     * Test of getTVImages method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVImages() throws MovieDbException {
        LOG.info("getTVImages");

        String language = LANGUAGE_DEFAULT;
        String[] includeImageLanguage = null;

        ArtworkResults results = new ArtworkResults();

        for (TestID test : TV_IDS) {
            ResultList<Artwork> result = instance.getTVImages(test.getTmdb(), language, includeImageLanguage);
            TestSuite.test(result, "TV Images");
            for (Artwork artwork : result.getResults()) {
                results.found(artwork.getArtworkType());
            }

            // We should only have posters & backdrops
            results.validateResults(ArtworkType.POSTER, ArtworkType.BACKDROP);
        }

    }

    /**
     * Test of getTVKeywords method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVKeywords() throws MovieDbException {
        LOG.info("getTVKeywords");

        for (TestID test : TV_IDS) {
            ResultList<Keyword> result = instance.getTVKeywords(test.getTmdb());
            TestSuite.test(result, "TV Keyword");
        }
    }

    /**
     * Test of postTVRating method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testPostTVRating() throws MovieDbException {
        LOG.info("postTVRating");

        for (TestID test : TV_IDS) {
            int rating = TestSuite.randomRating();
            StatusCode result = instance.postTVRating(test.getTmdb(), rating, getSessionId(), null);
            assertEquals("failed to post rating", 12, result.getCode());
        }
    }

    /**
     * Test of getTVSimilar method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVSimilar() throws MovieDbException {
        LOG.info("getTVSimilar");

        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : TV_IDS) {
            ResultList<TVInfo> result = instance.getTVSimilar(test.getTmdb(), page, language);
            TestSuite.test(result, "TV Similar");
        }
    }

    /**
     * Test of getTVTranslations method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVTranslations() throws MovieDbException {
        LOG.info("getTVTranslations");

        for (TestID test : TV_IDS) {
            ResultList<Translation> result = instance.getTVTranslations(test.getTmdb());
            TestSuite.test(result, "TV Translations");
        }
    }

    /**
     * Test of getTVVideos method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVVideos() throws MovieDbException {
        LOG.info("getTVVideos");

        String language = LANGUAGE_DEFAULT;
        boolean found = false;

        for (TestID test : TV_IDS) {
            ResultList<Video> result = instance.getTVVideos(test.getTmdb(), language);
            found = found || !result.isEmpty();
        }
        assertTrue("No videos", found);
    }

    /**
     * Test of getLatestTV method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVLatest() throws MovieDbException {
        LOG.info("getTVLatest");

        TVInfo result = instance.getLatestTV();
        assertTrue("Missing ID", result.getId() > 0);
        assertTrue("Missing Name", StringUtils.isNotBlank(result.getName()));
    }

    /**
     * Test of getTVOnTheAir method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVOnTheAir() throws MovieDbException {
        LOG.info("getTVOnTheAir");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        ResultList<TVBasic> result = instance.getTVOnTheAir(page, language);
        TestSuite.test(result, "TV OTA");
    }

    /**
     * Test of getTVAiringToday method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVAiringToday() throws MovieDbException {
        LOG.info("getTVAiringToday");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;
        String timezone = "";

        ResultList<TVBasic> result = instance.getTVAiringToday(page, language, timezone);
        TestSuite.test(result, "TV Airing");
    }

    /**
     * Test of getTVTopRated method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVTopRated() throws MovieDbException {
        LOG.info("getTVTopRated");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        ResultList<TVBasic> result = instance.getTVTopRated(page, language);
        TestSuite.test(result, "TV Top");
    }

    /**
     * Test of getTVPopular method, of class TmdbTV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTVPopular() throws MovieDbException {
        LOG.info("getTVPopular");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        ResultList<TVBasic> result = instance.getTVPopular(page, language);
        TestSuite.test(result, "tv Popular");
    }

}
