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
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.change.ChangeListItem;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.media.MediaCreditCast;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.movie.AlternativeTitle;
import com.omertron.themoviedbapi.model.movie.Translation;
import com.omertron.themoviedbapi.model.movie.Video;
import com.omertron.themoviedbapi.model.person.ContentRating;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.wrapper.WrapperChanges;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
    private static final List<TestID> TV_IDS = new ArrayList<TestID>();

    public TmdbTVTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbTV(getApiKey(), getHttpTools());

        TV_IDS.add(new TestID("The Walking Dead", "tt1520211", 1402, "Andrew Lincoln"));
//        TV_IDS.add(new TestID("Supernatural", "tt0460681", 1622,"Misha Collins"));
//        TV_IDS.add(new TestID("The Big Bang Theory", "tt0898266", 1418,"Kaley Cuoco"));
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
            TmdbResultsList<AlternativeTitle> result = instance.getTVAlternativeTitles(test.getTmdb());
            assertFalse("No alt titles", result.isEmpty());
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
            TmdbResultsList<ContentRating> result = instance.getTVContentRatings(test.getTmdb());
            assertFalse("No ratings", result.isEmpty());
            assertTrue("No language", StringUtils.isNotBlank(result.getResults().get(0).getLanguage()));
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

        boolean foundBackdrop = false;
        boolean foundPoster = false;
        boolean foundOther = false;

        for (TestID test : TV_IDS) {
            TmdbResultsList<Artwork> result = instance.getTVImages(test.getTmdb(), language, includeImageLanguage);
            assertFalse("No artwork", result.isEmpty());
            for (Artwork artwork : result.getResults()) {
                if (artwork.getArtworkType() == ArtworkType.BACKDROP) {
                    foundBackdrop = true;
                    continue;
                } else if (artwork.getArtworkType() == ArtworkType.POSTER) {
                    foundPoster = true;
                    continue;
                }
                foundOther = true;
            }
            assertTrue("No backdrops", foundBackdrop);
            assertTrue("No posters", foundPoster);
            assertFalse("Something else found!", foundOther);
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

        String[] appendToResponse = null;

        for (TestID test : TV_IDS) {
            TmdbResultsList<Keyword> result = instance.getTVKeywords(test.getTmdb(), appendToResponse);
            assertFalse("No keywords", result.isEmpty());
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
            Integer rating = new Random().nextInt(10) + 1;
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
        String[] appendToResponse = null;

        for (TestID test : TV_IDS) {
            TmdbResultsList<TVInfo> result = instance.getTVSimilar(test.getTmdb(), page, language, appendToResponse);
            assertFalse("No similar TV shows", result.isEmpty());
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
            TmdbResultsList<Translation> result = instance.getTVTranslations(test.getTmdb());
            assertFalse("No translations", result.isEmpty());
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

        for (TestID test : TV_IDS) {
            TmdbResultsList<Video> result = instance.getTVVideos(test.getTmdb(), language);
            assertFalse("No videos", result.isEmpty());
        }
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
        assertNotNull("Null TV returned", result);
        assertTrue("No ID", result.getId() > 0);
        assertTrue("No title", StringUtils.isNotBlank(result.getName()));
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

        TmdbResultsList<TVBasic> result = instance.getTVOnTheAir(page, language);
        assertFalse("No results", result.isEmpty());
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

        TmdbResultsList<TVBasic> result = instance.getTVAiringToday(page, language, timezone);
        assertFalse("No results", result.isEmpty());
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

        TmdbResultsList<TVBasic> result = instance.getTVTopRated(page, language);
        assertFalse("No results", result.isEmpty());
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

        TmdbResultsList<TVBasic> result = instance.getTVPopular(page, language);
        assertFalse("No results", result.isEmpty());
    }

}
