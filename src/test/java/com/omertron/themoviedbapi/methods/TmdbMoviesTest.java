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
import com.omertron.themoviedbapi.model.AlternativeTitle;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.model.MovieList;
import com.omertron.themoviedbapi.model.ReleaseInfo;
import com.omertron.themoviedbapi.model.Translation;
import com.omertron.themoviedbapi.model.Video;
import com.omertron.themoviedbapi.model.person.Person;
import com.omertron.themoviedbapi.model2.artwork.Artwork;
import com.omertron.themoviedbapi.model2.keyword.Keyword;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbMoviesTest extends AbstractTests {

    private static TmdbMovies instance;
    private static final List<TestID> FILM_IDS = new ArrayList<TestID>();

    public TmdbMoviesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbMovies(getApiKey(), getHttpTools());

        FILM_IDS.add(new TestID("Blade Runner", "", 78));
        FILM_IDS.add(new TestID("Jupiter Ascending", "tt1617661", 76757));
        FILM_IDS.add(new TestID("Lucy", "tt2872732", 240832));

    }

    /**
     * Test of getMovieInfo method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieInfo() throws MovieDbException {
        LOG.info("getMovieInfo");

        String language = "";
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            MovieDb result = instance.getMovieInfo(test.getTmdb(), language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieInfoImdb method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieInfoImdb() throws MovieDbException {
        LOG.info("getMovieInfoImdb");
        String language = "";
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            MovieDb result = instance.getMovieInfoImdb(test.getImdb(), language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
            String result = instance.getMovieAccountState(test.getTmdb(), getSessionId());
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            TmdbResultsList<AlternativeTitle> result = instance.getMovieAlternativeTitles(test.getTmdb(), country, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieCredits method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieCredits() throws MovieDbException {
        LOG.info("getMovieCredits");

        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            String result = instance.getMovieCredits(test.getTmdb(), appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieCasts method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieCasts() throws MovieDbException {
        LOG.info("getMovieCasts");

        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            TmdbResultsList<Person> result = instance.getMovieCasts(test.getTmdb(), appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieImages method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieImages() throws MovieDbException {
        LOG.info("getMovieImages");

        String language = "";
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            TmdbResultsList<Artwork> result = instance.getMovieImages(test.getTmdb(), language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieKeywords method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieKeywords() throws MovieDbException {
        LOG.info("getMovieKeywords");

        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            TmdbResultsList<Keyword> result = instance.getMovieKeywords(test.getTmdb(), appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieReleaseInfo method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieReleaseInfo() throws MovieDbException {
        LOG.info("getMovieReleaseInfo");

        String language = "";
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            TmdbResultsList<ReleaseInfo> result = instance.getMovieReleaseInfo(test.getTmdb(), language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieVideos method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieVideos() throws MovieDbException {
        LOG.info("getMovieVideos");

        String language = "";
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            TmdbResultsList<Video> result = instance.getMovieVideos(test.getTmdb(), language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieTranslations method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieTranslations() throws MovieDbException {
        LOG.info("getMovieTranslations");

        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            TmdbResultsList<Translation> result = instance.getMovieTranslations(test.getTmdb(), appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        String language = "";
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            TmdbResultsList<MovieDb> result = instance.getSimilarMovies(test.getTmdb(), page, language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        String language = "";
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            String result = instance.getMovieReviews(test.getTmdb(), page, language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        String language = "";
        String[] appendToResponse = null;

        for (TestID test : FILM_IDS) {
            TmdbResultsList<MovieList> result = instance.getMovieLists(test.getTmdb(), page, language, appendToResponse);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovieChanges method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetMovieChanges() throws MovieDbException {
        LOG.info("getMovieChanges");

        String startDate = "";
        String endDate = "";

        for (TestID test : FILM_IDS) {
            String result = instance.getMovieChanges(test.getTmdb(), startDate, endDate);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of postMovieRating method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testPostMovieRating() throws MovieDbException {
        LOG.info("postMovieRating");
        String sessionId = "";
        Integer rating = null;

        for (TestID test : FILM_IDS) {
            boolean result = instance.postMovieRating(sessionId, test.getTmdb(), rating);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLatestMovie method, of class TmdbMovies.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetLatestMovie() throws MovieDbException {
        LOG.info("getLatestMovie");

        for (TestID test : FILM_IDS) {
            MovieDb result = instance.getLatestMovie();
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        String language = "";

        for (TestID test : FILM_IDS) {
            TmdbResultsList<MovieDb> result = instance.getUpcoming(page, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        String language = "";

        for (TestID test : FILM_IDS) {
            TmdbResultsList<MovieDb> result = instance.getNowPlayingMovies(page, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        String language = "";

        for (TestID test : FILM_IDS) {
            TmdbResultsList<MovieDb> result = instance.getPopularMovieList(page, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        String language = "";

        for (TestID test : FILM_IDS) {
            TmdbResultsList<MovieDb> result = instance.getTopRatedMovies(page, language);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
