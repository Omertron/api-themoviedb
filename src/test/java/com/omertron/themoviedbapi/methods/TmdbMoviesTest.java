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
import com.omertron.themoviedbapi.enumeration.MovieMethod;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.change.ChangeListItem;
import com.omertron.themoviedbapi.model.credits.MediaCreditCast;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.list.UserList;
import com.omertron.themoviedbapi.model.media.AlternativeTitle;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.media.Translation;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.movie.ReleaseInfo;
import com.omertron.themoviedbapi.model.review.Review;
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
 *
 * @author stuart.boston
 */
public class TmdbMoviesTest extends AbstractTests {

    private static TmdbMovies instance;
    private static final List<TestID> FILM_IDS = new ArrayList<>();

    public TmdbMoviesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbMovies(getApiKey(), getHttpTools());

        FILM_IDS.add(new TestID("Blade Runner", "tt0083658", 78, "Harrison Ford"));
        FILM_IDS.add(new TestID("Jupiter Ascending", "tt1617661", 76757, "Mila Kunis"));
        FILM_IDS.add(new TestID("Lucy", "tt2872732", 240832, "Morgan Freeman"));

    }

    /**
     * Test of Append_To_Response method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testAppendToResponse() throws MovieDbException {
        LOG.info("appendToResponse");

        String language = LANGUAGE_DEFAULT;
        String appendToResponse = appendToResponseBuilder(MovieMethod.class);

        for (TestID test : FILM_IDS) {
            // Just test Blade Runner
            if (test.getTmdb() != 78) {
                continue;
            }

            MovieInfo result = instance.getMovieInfo(test.getTmdb(), language, appendToResponse);
            assertEquals("Wrong IMDB ID", test.getImdb(), result.getImdbID());
            assertEquals("Wrong title", test.getName(), result.getTitle());
            TestSuite.test(result);
            TestSuite.testATR(result, MovieMethod.class, MovieMethod.CHANGES);
            TestSuite.test(result.getAlternativeTitles(), "Alt titles");
            TestSuite.test(result.getCast(), "Cast");
            TestSuite.test(result.getCrew(), "Crew");
            TestSuite.test(result.getImages(), "Images");
            TestSuite.test(result.getKeywords(), "Keywords");
            TestSuite.test(result.getReleases(), "Releases");
            TestSuite.test(result.getVideos(), "Videos");
            TestSuite.test(result.getTranslations(), "Translations");
            TestSuite.test(result.getSimilarMovies(), "Similar");
            TestSuite.test(result.getLists(), "Lists");
            TestSuite.test(result.getReviews(), "Reviews");
            // There are rarely any changes, so skip this test
        }
    }

    /**
     * Test of getMovieInfo method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieInfo() throws MovieDbException {
        LOG.info("getMovieInfo");

        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            MovieInfo result = instance.getMovieInfo(test.getTmdb(), language, appendToResponse);
            assertEquals("Wrong IMDB ID", test.getImdb(), result.getImdbID());
            assertEquals("Wrong title", test.getName(), result.getTitle());
            TestSuite.test(result);
        }
    }

    /**
     * Test of getMovieInfoImdb method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieInfoImdb() throws MovieDbException {
        LOG.info("getMovieInfoImdb");
        String language = LANGUAGE_DEFAULT;
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            MovieInfo result = instance.getMovieInfoImdb(test.getImdb(), language, appendToResponse);
            assertEquals("Wrong TMDB ID", test.getTmdb(), result.getId());
            assertEquals("Wrong title", test.getName(), result.getTitle());
            TestSuite.test(result);
        }
    }

    /**
     * Test of getMovieAccountState method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieAccountState() throws MovieDbException {
        LOG.info("getMovieAccountState");

        for (TestID test : FILM_IDS) {
            MediaState result = instance.getMovieAccountState(test.getTmdb(), getSessionId());
            assertNotNull("Null result", result);
            assertTrue("Invalid rating", result.getRated() > -2f);
        }
    }

    /**
     * Test of getMovieAlternativeTitles method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieAlternativeTitles() throws MovieDbException {
        LOG.info("getMovieAlternativeTitles");

        String country = "";

        for (TestID test : FILM_IDS) {
            ResultList<AlternativeTitle> result = instance.getMovieAlternativeTitles(test.getTmdb(), country);
            TestSuite.test(result, "Alt Titles");
        }
    }

    /**
     * Test of getMovieCredits method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieCredits() throws MovieDbException {
        LOG.info("getMovieCredits");

        for (TestID test : FILM_IDS) {
            MediaCreditList result = instance.getMovieCredits(test.getTmdb());
            assertNotNull(result);
            assertFalse(result.getCast().isEmpty());
            TestSuite.test(result.getCast(), "Cast");
            TestSuite.test(result.getCrew(), "Crew");

            boolean found = false;
            for (MediaCreditCast p : result.getCast()) {
                if (test.getOther().equals(p.getName())) {
                    found = true;
                    break;
                }
            }
            assertTrue(test.getOther() + " not found in cast!", found);

            assertFalse(result.getCrew().isEmpty());
        }
    }

    /**
     * Test of getMovieImages method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieImages() throws MovieDbException {
        LOG.info("getMovieImages");

        String language = LANGUAGE_DEFAULT;

        ArtworkResults results = new ArtworkResults();

        for (TestID test : FILM_IDS) {
            ResultList<Artwork> result = instance.getMovieImages(test.getTmdb(), language);
            assertFalse("No artwork", result.isEmpty());
            for (Artwork artwork : result.getResults()) {
                results.found(artwork.getArtworkType());
            }

            // We should only have posters & backdrops
            results.validateResults(ArtworkType.POSTER, ArtworkType.BACKDROP);
        }
    }

    /**
     * Test of getMovieKeywords method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieKeywords() throws MovieDbException {
        LOG.info("getMovieKeywords");

        for (TestID test : FILM_IDS) {
            ResultList<Keyword> result = instance.getMovieKeywords(test.getTmdb());
            TestSuite.test(result, "Keywords");
        }
    }

    /**
     * Test of getMovieReleaseInfo method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieReleaseInfo() throws MovieDbException {
        LOG.info("getMovieReleaseInfo");

        String language = LANGUAGE_DEFAULT;

        for (TestID test : FILM_IDS) {
            ResultList<ReleaseInfo> result = instance.getMovieReleaseInfo(test.getTmdb(), language);
            TestSuite.test(result, "Rel Info");
        }
    }

    /**
     * Test of getMovieVideos method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieVideos() throws MovieDbException {
        LOG.info("getMovieVideos");

        String language = LANGUAGE_DEFAULT;
        boolean found = false;

        for (TestID test : FILM_IDS) {
            ResultList<Video> result = instance.getMovieVideos(test.getTmdb(), language);
            found = found || !result.isEmpty();
        }
        assertTrue("No videos", found);
    }

    /**
     * Test of getMovieTranslations method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieTranslations() throws MovieDbException {
        LOG.info("getMovieTranslations");

        for (TestID test : FILM_IDS) {
            ResultList<Translation> result = instance.getMovieTranslations(test.getTmdb());
            TestSuite.test(result, "Translations");
        }
    }

    /**
     * Test of getSimilarMovies method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSimilarMovies() throws MovieDbException {
        LOG.info("getSimilarMovies");

        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : FILM_IDS) {
            ResultList<MovieInfo> result = instance.getSimilarMovies(test.getTmdb(), page, language);
            TestSuite.test(result, "Similar");
        }
    }

    /**
     * Test of getMovieReviews method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieReviews() throws MovieDbException {
        LOG.info("getMovieReviews");

        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : FILM_IDS) {
            if (test.getTmdb() == 76757) {
                // Has no reviews
                continue;
            }
            ResultList<Review> result = instance.getMovieReviews(test.getTmdb(), page, language);
            TestSuite.test(result, "Reviews");
        }
    }

    /**
     * Test of getMovieLists method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieLists() throws MovieDbException {
        LOG.info("getMovieLists");

        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        for (TestID test : FILM_IDS) {
            ResultList<UserList> result = instance.getMovieLists(test.getTmdb(), page, language);
            TestSuite.test(result, "Lists");
        }
    }

    /**
     * Test of getMovieChanges method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieChanges() throws MovieDbException {
        LOG.info("getMovieChanges");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(DateUtils.addDays(new Date(), -14));
        String endDate = "";
        int maxCheck = 5;

        TmdbChanges chgs = new TmdbChanges(getApiKey(), getHttpTools());
        ResultList<ChangeListItem> changeList = chgs.getChangeList(MethodBase.MOVIE, null, null, null);
        LOG.info("Found {} changes to check, will check maximum of {}", changeList.getResults().size(), maxCheck);

        int count = 1;
        ResultList<ChangeKeyItem> result;
        for (ChangeListItem item : changeList.getResults()) {
            result = instance.getMovieChanges(item.getId(), startDate, endDate);
            for (ChangeKeyItem ci : result.getResults()) {
                assertNotNull("Null changes", ci);
            }

            if (count++ > maxCheck) {
                break;
            }
        }
    }

    /**
     * Test of postMovieRating method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testPostMovieRating() throws MovieDbException {
        LOG.info("postMovieRating");
        int rating = TestSuite.randomRating();

        for (TestID test : FILM_IDS) {
            StatusCode result = instance.postMovieRating(test.getTmdb(), rating, getSessionId(), null);
            assertEquals("failed to post rating", 12, result.getCode());
        }
    }

    /**
     * Test of getLatestMovie method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetLatestMovie() throws MovieDbException {
        LOG.info("getLatestMovie");

        MovieInfo result = instance.getLatestMovie();
        assertNotNull("Null movie returned", result);
        assertTrue("No ID", result.getId() > 0);
        assertTrue("No title", StringUtils.isNotBlank(result.getTitle()));
    }

    /**
     * Test of getUpcoming method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetUpcoming() throws MovieDbException {
        LOG.info("getUpcoming");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        ResultList<MovieInfo> result = instance.getUpcoming(page, language);
        assertFalse("No results found", result.isEmpty());
    }

    /**
     * Test of getNowPlayingMovies method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetNowPlayingMovies() throws MovieDbException {
        LOG.info("getNowPlayingMovies");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        ResultList<MovieInfo> result = instance.getNowPlayingMovies(page, language);
        TestSuite.test(result, "Now Playing");
    }

    /**
     * Test of getPopularMovieList method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetPopularMovieList() throws MovieDbException {
        LOG.info("getPopularMovieList");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        ResultList<MovieInfo> result = instance.getPopularMovieList(page, language);
        TestSuite.test(result, "Popular");
    }

    /**
     * Test of getTopRatedMovies method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTopRatedMovies() throws MovieDbException {
        LOG.info("getTopRatedMovies");
        Integer page = null;
        String language = LANGUAGE_DEFAULT;

        ResultList<MovieInfo> result = instance.getTopRatedMovies(page, language);
        TestSuite.test(result, "Top Rated");
    }

}
