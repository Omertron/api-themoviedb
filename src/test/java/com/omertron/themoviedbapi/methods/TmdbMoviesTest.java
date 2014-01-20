/*
 *      Copyright (c) 2004-2014 Stuart Boston
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

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.ACCOUNT_ID_APITESTS;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.LANGUAGE_DEFAULT;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.LANGUAGE_ENGLISH;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.SESSION_ID_APITESTS;
import com.omertron.themoviedbapi.model.AlternativeTitle;
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.ChangedItem;
import com.omertron.themoviedbapi.model.Keyword;
import com.omertron.themoviedbapi.model.ReleaseInfo;
import com.omertron.themoviedbapi.model.Review;
import com.omertron.themoviedbapi.model.Trailer;
import com.omertron.themoviedbapi.model.Translation;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.model.movie.MovieList;
import com.omertron.themoviedbapi.model.movie.MovieState;
import com.omertron.themoviedbapi.model.person.PersonMovieOld;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.results.TmdbResultsMap;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

/**
 *
 * @author stuart.boston
 */
public class TmdbMoviesTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbGenresTest.class);
    // API
    private static TmdbMovies instance;
    private static final int ID_MOVIE_BLADE_RUNNER = 78;
    private static final int ID_MOVIE_THE_AVENGERS = 24428;

    public TmdbMoviesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbMovies(API_KEY, new DefaultPoolingHttpClient());
        TestLogger.Configure();
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
     * Test of getMovieInfo method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieInfo() throws MovieDbException {
        LOG.info("getMovieInfo");
        MovieDb result = instance.getMovieInfo(ID_MOVIE_BLADE_RUNNER, LANGUAGE_ENGLISH, "alternative_titles,casts,images,keywords,releases,trailers,translations,similar_movies,reviews,lists");
        assertEquals("Incorrect movie information", "Blade Runner", result.getOriginalTitle());
    }

    /**
     * Test of getMovieInfoImdb method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieInfoImdb() throws MovieDbException {
        LOG.info("getMovieInfoImdb");
        MovieDb result = instance.getMovieInfoImdb("tt0076759", "en-US");
        assertTrue("Error getting the movie from IMDB ID", result.getId() == 11);
    }

    /**
     * Test of getMovieAlternativeTitles method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieAlternativeTitles() throws MovieDbException {
        LOG.info("getMovieAlternativeTitles");
        String country = "";
        TmdbResultsList<AlternativeTitle> result = instance.getMovieAlternativeTitles(ID_MOVIE_BLADE_RUNNER, country, "casts,images,keywords,releases,trailers,translations,similar_movies,reviews,lists");
        assertTrue("No alternative titles found", result.getResults().size() > 0);

        country = "US";
        result = instance.getMovieAlternativeTitles(ID_MOVIE_BLADE_RUNNER, country);
        assertTrue("No alternative titles found", result.getResults().size() > 0);

    }

    /**
     * Test of getMovieCredits method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieCasts() throws MovieDbException {
        LOG.info("getMovieCasts");
        TmdbResultsList<PersonMovieOld> people = instance.getMovieCredits(ID_MOVIE_BLADE_RUNNER, "alternative_titles,casts,images,keywords,releases,trailers,translations,similar_movies,reviews,lists");
        assertTrue("No cast information", people.getResults().size() > 0);

        String name1 = "Harrison Ford";
        String name2 = "Charles Knode";
        boolean foundName1 = Boolean.FALSE;
        boolean foundName2 = Boolean.FALSE;

        for (PersonMovieOld person : people.getResults()) {
            if (!foundName1 && person.getName().equalsIgnoreCase(name1)) {
                foundName1 = Boolean.TRUE;
            }

            if (!foundName2 && person.getName().equalsIgnoreCase(name2)) {
                foundName2 = Boolean.TRUE;
            }
        }
        assertTrue("Couldn't find " + name1, foundName1);
        assertTrue("Couldn't find " + name2, foundName2);
    }

    /**
     * Test of getMovieImages method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieImages() throws MovieDbException {
        LOG.info("getMovieImages");
        String language = "";
        TmdbResultsList<Artwork> result = instance.getMovieImages(ID_MOVIE_BLADE_RUNNER, language);
        assertFalse("No artwork found", result.getResults().isEmpty());
    }

    /**
     * Test of getMovieKeywords method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieKeywords() throws MovieDbException {
        LOG.info("getMovieKeywords");
        TmdbResultsList<Keyword> result = instance.getMovieKeywords(ID_MOVIE_BLADE_RUNNER);
        assertFalse("No keywords found", result.getResults().isEmpty());
    }

    /**
     * Test of getMovieReleaseInfo method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieReleaseInfo() throws MovieDbException {
        LOG.info("getMovieReleaseInfo");
        TmdbResultsList<ReleaseInfo> result = instance.getMovieReleaseInfo(ID_MOVIE_BLADE_RUNNER, "");
        assertFalse("Release information missing", result.getResults().isEmpty());
    }

    /**
     * Test of getMovieTrailers method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieTrailers() throws MovieDbException {
        LOG.info("getMovieTrailers");
        TmdbResultsList<Trailer> result = instance.getMovieTrailers(ID_MOVIE_BLADE_RUNNER, "");
        assertFalse("Movie trailers missing", result.getResults().isEmpty());
    }

    /**
     * Test of getMovieTranslations method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieTranslations() throws MovieDbException {
        LOG.info("getMovieTranslations");
        TmdbResultsList<Translation> result = instance.getMovieTranslations(ID_MOVIE_BLADE_RUNNER);
        assertFalse("No translations found", result.getResults().isEmpty());
    }

    /**
     * Test of getSimilarMovies method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetSimilarMovies() throws MovieDbException {
        LOG.info("getSimilarMovies");
        TmdbResultsList<MovieDb> result = instance.getSimilarMovies(ID_MOVIE_BLADE_RUNNER, LANGUAGE_DEFAULT, 0);
        assertTrue("No similar movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of getReview method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetReviews() throws MovieDbException {
        LOG.info("getReviews");
        int page = 0;
        TmdbResultsList<Review> result = instance.getReviews(ID_MOVIE_THE_AVENGERS, LANGUAGE_DEFAULT, page);
        assertFalse("No reviews found", result.getResults().isEmpty());
    }

    /**
     * Test of getMovieLists method, of class TmdbMovie.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieLists() throws MovieDbException {
        LOG.info("getMovieLists");
        TmdbResultsList<MovieList> result = instance.getMovieLists(ID_MOVIE_BLADE_RUNNER, LANGUAGE_ENGLISH, 0);
        assertNotNull("No results found", result);
        assertTrue("No results found", result.getResults().size() > 0);
    }

    /**
     * Test of getMovieChanges method,of class TheMovieDbApi
     *
     * @throws MovieDbException
     */
    @Ignore("Do not test this until it is fixed")
    public void testGetMovieChanges() throws MovieDbException {
        LOG.info("getMovieChanges");

        String startDate = "";
        String endDate = null;

        // Get some popular movies
        TmdbResultsList<MovieDb> movieList = instance.getPopularMovieList(LANGUAGE_DEFAULT, 0);
        for (MovieDb movie : movieList.getResults()) {
            TmdbResultsMap<String, List<ChangedItem>> result = instance.getMovieChanges(movie.getId(), startDate, endDate);
            LOG.info("{} has {} changes.", movie.getTitle(), result.getResults().size());
            assertTrue("No changes found", result.getResults().size() > 0);
            break;
        }
    }

    /**
     * Test of getLatestMovie method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetLatestMovie() throws MovieDbException {
        LOG.info("getLatestMovie");
        MovieDb result = instance.getLatestMovie();
        assertTrue("No latest movie found", result != null);
        assertTrue("No latest movie found", result.getId() > 0);
    }

    /**
     * Test of getUpcoming method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetUpcoming() throws MovieDbException {
        LOG.info("getUpcoming");
        TmdbResultsList<MovieDb> result = instance.getUpcoming(LANGUAGE_DEFAULT, 0);
        assertTrue("No upcoming movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of getNowPlayingMovies method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetNowPlayingMovies() throws MovieDbException {
        LOG.info("getNowPlayingMovies");
        TmdbResultsList<MovieDb> result = instance.getNowPlayingMovies(LANGUAGE_DEFAULT, 0);
        assertTrue("No now playing movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of getPopularMovieList method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetPopularMovieList() throws MovieDbException {
        LOG.info("getPopularMovieList");
        TmdbResultsList<MovieDb> result = instance.getPopularMovieList(LANGUAGE_DEFAULT, 0);
        assertTrue("No popular movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of getTopRatedMovies method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetTopRatedMovies() throws MovieDbException {
        LOG.info("getTopRatedMovies");
        TmdbResultsList<MovieDb> result = instance.getTopRatedMovies(LANGUAGE_DEFAULT, 0);
        assertTrue("No top rated movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of postMovieRating method, of class TheMovieDbApi.
     *
     * TODO: Cannot be tested without a HTTP authorisation: http://help.themoviedb.org/kb/api/user-authentication /**
     *
     * @throws MovieDbException
     */
    @Test
    public void testMovieRating() throws MovieDbException {
        LOG.info("postMovieRating");
        Integer movieID = 68724;
        Integer rating = new Random().nextInt(10) + 1;

        boolean wasPosted = instance.postMovieRating(SESSION_ID_APITESTS, movieID, rating);
        assertTrue("Failed to post rating", wasPosted);

        try {
            // Make sure that we pause long enough for the record to by updated
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            // Ignore the exception and move on
        }

        // get all rated movies
        TmdbAccount account = new TmdbAccount(API_KEY, new DefaultPoolingHttpClient());
        List<MovieDb> ratedMovies = account.getRatedMovies(SESSION_ID_APITESTS, ACCOUNT_ID_APITESTS);
        assertTrue(ratedMovies.size() > 0);

        // make sure that we find the movie and it is rated correctly
        boolean foundMovie = false;
        for (MovieDb movie : ratedMovies) {
            if (movie.getId() == movieID) {
                assertEquals("Incorrect rating", movie.getUserRating(), (float) rating, 0);
                foundMovie = true;
                break;
            }
        }
        assertTrue("Movie not found!", foundMovie);
    }

    /**
     * Test of getMovieCredits method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieCredits() throws MovieDbException {
        LOG.info("getMovieCredits");
        TmdbResultsList<PersonMovieOld> result = instance.getMovieCredits(ID_MOVIE_BLADE_RUNNER, "");
        assertFalse("No credits returned", result.getResults().isEmpty());
    }

    /**
     * Test of getMovieStatus method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieStatus() throws MovieDbException {
        LOG.info("getMovieStatus");
        // This movie should be rated
        MovieState result = instance.getMovieStatus(SESSION_ID_APITESTS, ID_MOVIE_BLADE_RUNNER);
        assertNotNull("No state returned", result);
        assertFalse("Movie is favourited", result.isFavorite());
        assertFalse("Movie is not rated", result.getRating() < 0);
        assertFalse("Movie is watch listed", result.isWatchlist());

        // This movie should not be rated
        result = instance.getMovieStatus(SESSION_ID_APITESTS, ID_MOVIE_THE_AVENGERS);
        assertNotNull("No state returned", result);
        assertFalse("Movie is favourited", result.isFavorite());
        assertFalse("Movie is rated", result.getRating() > -1);
        assertFalse("Movie is watch listed", result.isWatchlist());
    }

    /**
     * Test of postMovieRating method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testPostMovieRating() throws MovieDbException {
        LOG.info("postMovieRating");
        Integer rating = 10;
        boolean result = instance.postMovieRating(SESSION_ID_APITESTS, ID_MOVIE_BLADE_RUNNER, rating);
        assertEquals("Failed to set rating", true, result);
    }

}
