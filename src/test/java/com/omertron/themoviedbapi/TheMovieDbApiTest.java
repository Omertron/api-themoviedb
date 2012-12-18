/*
 *      Copyright (c) 2004-2012 Stuart Boston
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
package com.omertron.themoviedbapi;

import com.omertron.themoviedbapi.model.AlternativeTitle;
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.CollectionInfo;
import com.omertron.themoviedbapi.model.Company;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.Keyword;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.model.MovieList;
import com.omertron.themoviedbapi.model.Person;
import com.omertron.themoviedbapi.model.PersonCredit;
import com.omertron.themoviedbapi.model.ReleaseInfo;
import com.omertron.themoviedbapi.model.TmdbConfiguration;
import com.omertron.themoviedbapi.model.TokenAuthorisation;
import com.omertron.themoviedbapi.model.TokenSession;
import com.omertron.themoviedbapi.model.Trailer;
import com.omertron.themoviedbapi.model.Translation;
import com.omertron.themoviedbapi.tools.FilteringLayout;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test cases for TheMovieDbApi API
 *
 * @author stuart.boston
 */
public class TheMovieDbApiTest {

    // Logger
    private static final Logger logger = Logger.getLogger(TheMovieDbApiTest.class);
    // API Key
    private static final String API_KEY = "5a1a77e2eba8984804586122754f969f";
    private static TheMovieDbApi tmdb;
    // Test data
    private static final int ID_MOVIE_BLADE_RUNNER = 78;
    private static final int ID_MOVIE_STAR_WARS_COLLECTION = 10;
    private static final int ID_PERSON_BRUCE_WILLIS = 62;
    private static final int ID_COMPANY_LUCASFILM = 1;
    private static final String COMPANY_NAME = "Marvel Studios";
    private static final int ID_GENRE_ACTION = 28;

    public TheMovieDbApiTest() throws MovieDbException {
        tmdb = new TheMovieDbApi(API_KEY);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Set the logger level to TRACE
        Logger.getRootLogger().setLevel(Level.TRACE);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        // Make sure the filter isn't applied to the test output
        FilteringLayout.addReplacementString("DO_NOT_MATCH");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getConfiguration method, of class TheMovieDbApi.
     */
    //@Test
    public void testConfiguration() throws IOException {
        logger.info("Test Configuration");

        TmdbConfiguration tmdbConfig = tmdb.getConfiguration();
        assertNotNull("Configuration failed", tmdbConfig);
        assertTrue("No base URL", StringUtils.isNotBlank(tmdbConfig.getBaseUrl()));
        assertTrue("No backdrop sizes", tmdbConfig.getBackdropSizes().size() > 0);
        assertTrue("No poster sizes", tmdbConfig.getPosterSizes().size() > 0);
        assertTrue("No profile sizes", tmdbConfig.getProfileSizes().size() > 0);
        logger.info(tmdbConfig.toString());
    }

    /**
     * Test of searchMovie method, of class TheMovieDbApi.
     */
    //@Test
    public void testSearchMovie() throws MovieDbException {
        logger.info("searchMovie");

        // Try a movie with less than 1 page of results
        List<MovieDb> movieList = tmdb.searchMovie("Blade Runner", 0, "", true, 0);
//        List<MovieDb> movieList = tmdb.searchMovie("Blade Runner", "", true);
        assertTrue("No movies found, should be at least 1", movieList.size() > 0);

        // Try a russian langugage movie
        movieList = tmdb.searchMovie("О чём говорят мужчины", 0, "ru", true, 0);
        assertTrue("No movies found, should be at least 1", movieList.size() > 0);

        // Try a movie with more than 20 results
        movieList = tmdb.searchMovie("Star Wars", 0, "en", false, 0);
        assertTrue("Not enough movies found, should be over 15, found " + movieList.size(), movieList.size() >= 15);
    }

    /**
     * Test of getMovieInfo method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetMovieInfo() throws MovieDbException {
        logger.info("getMovieInfo");
        String language = "en";
        MovieDb result = tmdb.getMovieInfo(ID_MOVIE_BLADE_RUNNER, language);
        assertEquals("Incorrect movie information", "Blade Runner", result.getOriginalTitle());
    }

    /**
     * Test of getMovieAlternativeTitles method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetMovieAlternativeTitles() throws MovieDbException {
        logger.info("getMovieAlternativeTitles");
        String country = "";
        List<AlternativeTitle> results = tmdb.getMovieAlternativeTitles(ID_MOVIE_BLADE_RUNNER, country);
        assertTrue("No alternative titles found", results.size() > 0);

        country = "US";
        results = tmdb.getMovieAlternativeTitles(ID_MOVIE_BLADE_RUNNER, country);
        assertTrue("No alternative titles found", results.size() > 0);

    }

    /**
     * Test of getMovieCasts method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetMovieCasts() throws MovieDbException {
        logger.info("getMovieCasts");
        List<Person> people = tmdb.getMovieCasts(ID_MOVIE_BLADE_RUNNER);
        assertTrue("No cast information", people.size() > 0);

        String name1 = "Harrison Ford";
        String name2 = "Charles Knode";
        boolean foundName1 = Boolean.FALSE;
        boolean foundName2 = Boolean.FALSE;

        for (Person person : people) {
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
     */
    //@Test
    public void testGetMovieImages() throws MovieDbException {
        logger.info("getMovieImages");
        String language = "";
        List<Artwork> result = tmdb.getMovieImages(ID_MOVIE_BLADE_RUNNER, language);
        assertFalse("No artwork found", result.isEmpty());
    }

    /**
     * Test of getMovieKeywords method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetMovieKeywords() throws MovieDbException {
        logger.info("getMovieKeywords");
        List<Keyword> result = tmdb.getMovieKeywords(ID_MOVIE_BLADE_RUNNER);
        assertFalse("No keywords found", result.isEmpty());
    }

    /**
     * Test of getMovieReleaseInfo method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetMovieReleaseInfo() throws MovieDbException {
        logger.info("getMovieReleaseInfo");
        List<ReleaseInfo> result = tmdb.getMovieReleaseInfo(ID_MOVIE_BLADE_RUNNER, "");
        assertFalse("Release information missing", result.isEmpty());
    }

    /**
     * Test of getMovieTrailers method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetMovieTrailers() throws MovieDbException {
        logger.info("getMovieTrailers");
        List<Trailer> result = tmdb.getMovieTrailers(ID_MOVIE_BLADE_RUNNER, "");
        assertFalse("Movie trailers missing", result.isEmpty());
    }

    /**
     * Test of getMovieTranslations method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetMovieTranslations() throws MovieDbException {
        logger.info("getMovieTranslations");
        List<Translation> result = tmdb.getMovieTranslations(ID_MOVIE_BLADE_RUNNER);
        assertFalse("No translations found", result.isEmpty());
    }

    /**
     * Test of getCollectionInfo method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetCollectionInfo() throws MovieDbException {
        logger.info("getCollectionInfo");
        String language = "";
        CollectionInfo result = tmdb.getCollectionInfo(ID_MOVIE_STAR_WARS_COLLECTION, language);
        assertFalse("No collection information", result.getParts().isEmpty());
    }

    /**
     * Test of createImageUrl method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    //@Test
    public void testCreateImageUrl() throws MovieDbException {
        logger.info("createImageUrl");
        MovieDb movie = tmdb.getMovieInfo(ID_MOVIE_BLADE_RUNNER, "");
        String result = tmdb.createImageUrl(movie.getPosterPath(), "original").toString();
        assertTrue("Error compiling image URL", !result.isEmpty());
    }

    /**
     * Test of getMovieInfoImdb method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetMovieInfoImdb() throws MovieDbException {
        logger.info("getMovieInfoImdb");
        MovieDb result = tmdb.getMovieInfoImdb("tt0076759", "en-US");
        assertTrue("Error getting the movie from IMDB ID", result.getId() == 11);
    }

    /**
     * Test of getApiKey method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetApiKey() {
        // Not required
    }

    /**
     * Test of getApiBase method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetApiBase() {
        // Not required
    }

    /**
     * Test of getConfiguration method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetConfiguration() {
        // Not required
    }

    /**
     * Test of searchPeople method, of class TheMovieDbApi.
     */
    //@Test
    public void testSearchPeople() throws MovieDbException {
        logger.info("searchPeople");
        String personName = "Bruce Willis";
        boolean allResults = false;
        List<Person> result = tmdb.searchPeople(personName, allResults);
        assertTrue("Couldn't find the person", result.size() > 0);
    }

    /**
     * Test of getPersonInfo method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetPersonInfo() throws MovieDbException {
        logger.info("getPersonInfo");
        Person result = tmdb.getPersonInfo(ID_PERSON_BRUCE_WILLIS);
        assertTrue("Wrong actor returned", result.getId() == ID_PERSON_BRUCE_WILLIS);
    }

    /**
     * Test of getPersonCredits method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetPersonCredits() throws MovieDbException {
        logger.info("getPersonCredits");

        List<PersonCredit> people = tmdb.getPersonCredits(ID_PERSON_BRUCE_WILLIS);
        assertTrue("No cast information", people.size() > 0);
    }

    /**
     * Test of getPersonImages method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetPersonImages() throws MovieDbException {
        logger.info("getPersonImages");

        List<Artwork> artwork = tmdb.getPersonImages(ID_PERSON_BRUCE_WILLIS);
        assertTrue("No cast information", artwork.size() > 0);
    }

    /**
     * Test of getLatestMovie method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetLatestMovie() throws MovieDbException {
        logger.info("getLatestMovie");
        MovieDb result = tmdb.getLatestMovie();
        assertTrue("No latest movie found", result != null);
        assertTrue("No latest movie found", result.getId() > 0);
    }

    /**
     * Test of compareMovies method, of class TheMovieDbApi.
     */
    //@Test
    public void testCompareMovies() {
        // Not required
    }

    /**
     * Test of setProxy method, of class TheMovieDbApi.
     */
    //@Test
    public void testSetProxy() {
        // Not required
    }

    /**
     * Test of setTimeout method, of class TheMovieDbApi.
     */
    //@Test
    public void testSetTimeout() {
        // Not required
    }

    /**
     * Test of getNowPlayingMovies method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetNowPlayingMovies() throws MovieDbException {
        logger.info("getNowPlayingMovies");
        List<MovieDb> results = tmdb.getNowPlayingMovies("", true);
        assertTrue("No now playing movies found", !results.isEmpty());
    }

    /**
     * Test of getPopularMovieList method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetPopularMovieList() throws MovieDbException {
        logger.info("getPopularMovieList");
        List<MovieDb> results = tmdb.getPopularMovieList("", true);
        assertTrue("No popular movies found", !results.isEmpty());
    }

    /**
     * Test of getTopRatedMovies method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetTopRatedMovies() throws MovieDbException {
        logger.info("getTopRatedMovies");
        List<MovieDb> results = tmdb.getTopRatedMovies("", true);
        assertTrue("No top rated movies found", !results.isEmpty());
    }

    /**
     * Test of getCompanyInfo method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetCompanyInfo() throws MovieDbException {
        logger.info("getCompanyInfo");
        Company company = tmdb.getCompanyInfo(ID_COMPANY_LUCASFILM);
        assertTrue("No company information found", company.getCompanyId() > 0);
    }

    /**
     * Test of getCompanyMovies method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetCompanyMovies() throws MovieDbException {
        logger.info("getCompanyMovies");
        List<MovieDb> results = tmdb.getCompanyMovies(ID_COMPANY_LUCASFILM, "", true);
        assertTrue("No company movies found", !results.isEmpty());
    }

    /**
     * Test of searchCompanies method, of class TheMovieDbApi.
     */
    //@Test
    public void testSearchCompanies() throws MovieDbException {
        logger.info("searchCompanies");
        List<Company> results = tmdb.searchCompanies(COMPANY_NAME, "", true);
        assertTrue("No company information found", !results.isEmpty());
    }

    /**
     * Test of getSimilarMovies method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetSimilarMovies() throws MovieDbException {
        logger.info("getSimilarMovies");
        List<MovieDb> results = tmdb.getSimilarMovies(ID_MOVIE_BLADE_RUNNER, "", true);
        assertTrue("No similar movies found", !results.isEmpty());
    }

    /**
     * Test of getGenreList method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetGenreList() throws MovieDbException {
        logger.info("getGenreList");
        List<Genre> results = tmdb.getGenreList("");
        assertTrue("No genres found", !results.isEmpty());
    }

    /**
     * Test of getGenreMovies method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetGenreMovies() throws MovieDbException {
        logger.info("getGenreMovies");
        List<MovieDb> results = tmdb.getGenreMovies(ID_GENRE_ACTION, "", true);
        assertTrue("No genre movies found", !results.isEmpty());
    }

    /**
     * Test of getUpcoming method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetUpcoming() throws Exception {
        logger.info("getUpcoming");
        List<MovieDb> results = tmdb.getUpcoming("");
        assertTrue("No upcoming movies found", !results.isEmpty());
    }

    /**
     * Test of getCollectionImages method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetCollectionImages() throws Exception {
        logger.info("getCollectionImages");
        String language = "";
        List<Artwork> result = tmdb.getCollectionImages(ID_MOVIE_STAR_WARS_COLLECTION, language);
        assertFalse("No artwork found", result.isEmpty());
    }

    /**
     * Test of getAuthorisationToken method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetAuthorisationToken() throws Exception {
        logger.info("getAuthorisationToken");
        TokenAuthorisation result = tmdb.getAuthorisationToken();
        assertFalse("Token is null", result == null);
        assertTrue("Token is not valid", result.getSuccess());
        logger.info(result.toString());
    }

    /**
     * Test of getSessionToken method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetSessionToken() throws Exception {
        logger.info("getSessionToken");
        TokenAuthorisation token = tmdb.getAuthorisationToken();
        assertFalse("Token is null", token == null);
        assertTrue("Token is not valid", token.getSuccess());
        logger.info(token.toString());

        TokenSession result = tmdb.getSessionToken(token);
        assertFalse("Session token is null", result == null);
        assertTrue("Session token is not valid", result.getSuccess());
        logger.info(result.toString());
    }

    /**
     * Test of getGuestSessionToken method, of class TheMovieDbApi.
     */
    //@Test
    public void testGetGuestSessionToken() throws Exception {
        logger.info("getGuestSessionToken");
        TokenSession result = tmdb.getGuestSessionToken();

        assertTrue("Failed to get guest session", result.getSuccess());
    }

    @Test
    public void testGetMovieLists() throws Exception {
        logger.info("getMovieLists");
        String language = "en";
        List<MovieList> results = tmdb.getMovieLists(ID_MOVIE_BLADE_RUNNER, language, 0);
        assertNotNull("No results found", results);
        assertTrue("No results found", results.size() > 0);
    }
}
