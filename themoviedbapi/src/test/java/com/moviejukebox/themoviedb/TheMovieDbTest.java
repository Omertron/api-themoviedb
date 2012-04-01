/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.themoviedb;

import com.moviejukebox.themoviedb.model.*;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * Test cases for TheMovieDb API
 *
 * @author stuart.boston
 */
public class TheMovieDbTest {

    private static final Logger LOGGER = Logger.getLogger(TheMovieDbTest.class);
    private static final String API_KEY = "5a1a77e2eba8984804586122754f969f";
    private static TheMovieDb tmdb;
    /*
     * Test data
     */
    private static final int ID_BLADE_RUNNER = 78;
    private static final int ID_STAR_WARS_COLLECTION = 10;
    private static final int ID_BRUCE_WILLIS = 62;

    public TheMovieDbTest() throws MovieDbException {
        tmdb = new TheMovieDb(API_KEY);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getConfiguration method, of class TheMovieDb.
     */
    @Test
    public void testConfiguration() throws IOException {
        LOGGER.info("Test Configuration");

        TmdbConfiguration tmdbConfig = tmdb.getConfiguration();
        assertNotNull("Configuration failed", tmdbConfig);
        assertTrue("No base URL", StringUtils.isNotBlank(tmdbConfig.getBaseUrl()));
        assertTrue("No backdrop sizes", tmdbConfig.getBackdropSizes().size() > 0);
        assertTrue("No poster sizes", tmdbConfig.getPosterSizes().size() > 0);
        assertTrue("No profile sizes", tmdbConfig.getProfileSizes().size() > 0);
        LOGGER.info(tmdbConfig.toString());
    }

    /**
     * Test of searchMovie method, of class TheMovieDb.
     */
    @Test
    public void testSearchMovie() throws MovieDbException {
        LOGGER.info("searchMovie");

        // Try a movie with less than 1 page of results
        List<MovieDb> movieList = tmdb.searchMovie("Blade Runner", "", true);
        assertTrue("No movies found, should be at least 1", movieList.size() > 0);

        // Try a russian langugage movie
        movieList = tmdb.searchMovie("О чём говорят мужчины", "ru", true);
        assertTrue("No movies found, should be at least 1", movieList.size() > 0);

        // Try a movie with more than 20 results
        movieList = tmdb.searchMovie("Star Wars", "en", false);
        assertTrue("Not enough movies found, should be 20", movieList.size() == 20);
    }

    /**
     * Test of getMovieInfo method, of class TheMovieDb.
     */
    @Test
    public void testGetMovieInfo() throws MovieDbException {
        LOGGER.info("getMovieInfo");
        String language = "en";
        MovieDb result = tmdb.getMovieInfo(ID_BLADE_RUNNER, language);
        assertEquals("Incorrect movie information", "Blade Runner", result.getOriginalTitle());
    }

    /**
     * Test of getMovieAlternativeTitles method, of class TheMovieDb.
     */
    @Test
    public void testGetMovieAlternativeTitles() throws MovieDbException {
        LOGGER.info("getMovieAlternativeTitles");
        String country = "";
        List<AlternativeTitle> results = tmdb.getMovieAlternativeTitles(ID_BLADE_RUNNER, country);
        assertTrue("No alternative titles found", results.size() > 0);

        country = "US";
        results = tmdb.getMovieAlternativeTitles(ID_BLADE_RUNNER, country);
        assertTrue("No alternative titles found", results.size() > 0);

    }

    /**
     * Test of getMovieCasts method, of class TheMovieDb.
     */
    @Test
    public void testGetMovieCasts() throws MovieDbException {
        LOGGER.info("getMovieCasts");
        List<Person> people = tmdb.getMovieCasts(ID_BLADE_RUNNER);
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
     * Test of getMovieImages method, of class TheMovieDb.
     */
    @Test
    public void testGetMovieImages() throws MovieDbException {
        LOGGER.info("getMovieImages");
        String language = "";
        List<Artwork> result = tmdb.getMovieImages(ID_BLADE_RUNNER, language);
        assertFalse("No artwork found", result.isEmpty());
    }

    /**
     * Test of getMovieKeywords method, of class TheMovieDb.
     */
    @Test
    public void testGetMovieKeywords() throws MovieDbException {
        LOGGER.info("getMovieKeywords");
        List<Keyword> result = tmdb.getMovieKeywords(ID_BLADE_RUNNER);
        assertFalse("No keywords found", result.isEmpty());
    }

    /**
     * Test of getMovieReleaseInfo method, of class TheMovieDb.
     */
    @Test
    public void testGetMovieReleaseInfo() throws MovieDbException {
        LOGGER.info("getMovieReleaseInfo");
        List<ReleaseInfo> result = tmdb.getMovieReleaseInfo(ID_BLADE_RUNNER, "");
        assertFalse("Release information missing", result.isEmpty());
    }

    /**
     * Test of getMovieTrailers method, of class TheMovieDb.
     */
    @Test
    public void testGetMovieTrailers() throws MovieDbException {
        LOGGER.info("getMovieTrailers");
        List<Trailer> result = tmdb.getMovieTrailers(ID_BLADE_RUNNER, "");
        assertFalse("Movie trailers missing", result.isEmpty());
    }

    /**
     * Test of getMovieTranslations method, of class TheMovieDb.
     */
    @Test
    public void testGetMovieTranslations() throws MovieDbException {
        LOGGER.info("getMovieTranslations");
        List<Translation> result = tmdb.getMovieTranslations(ID_BLADE_RUNNER);
        assertFalse("No translations found", result.isEmpty());
    }

    /**
     * Test of getCollectionInfo method, of class TheMovieDb.
     */
    @Test
    public void testGetCollectionInfo() throws MovieDbException {
        LOGGER.info("getCollectionInfo");
        String language = "";
        CollectionInfo result = tmdb.getCollectionInfo(ID_STAR_WARS_COLLECTION, language);
        assertFalse("No collection information", result.getParts().isEmpty());
    }

    @Test
    public void testCreateImageUrl() throws MovieDbException {
        LOGGER.info("createImageUrl");
        MovieDb movie = tmdb.getMovieInfo(ID_BLADE_RUNNER, "");
        String result = tmdb.createImageUrl(movie.getPosterPath(), "original").toString();
        assertTrue("Error compiling image URL", !result.isEmpty());
    }

    /**
     * Test of getMovieInfoImdb method, of class TheMovieDb.
     */
    @Test
    public void testGetMovieInfoImdb() throws MovieDbException {
        LOGGER.info("getMovieInfoImdb");
        MovieDb result = tmdb.getMovieInfoImdb("tt0076759", "en-US");
        assertTrue("Error getting the movie from IMDB ID", result.getId() == 11);
    }

    /**
     * Test of getApiKey method, of class TheMovieDb.
     */
    @Test
    public void testGetApiKey() {
        // Not required
    }

    /**
     * Test of getApiBase method, of class TheMovieDb.
     */
    @Test
    public void testGetApiBase() {
        // Not required
    }

    /**
     * Test of getConfiguration method, of class TheMovieDb.
     */
    @Test
    public void testGetConfiguration() {
        // Not required
    }

    /**
     * Test of searchPeople method, of class TheMovieDb.
     */
    @Test
    public void testSearchPeople() throws MovieDbException {
        LOGGER.info("searchPeople");
        String personName = "Bruce Willis";
        boolean allResults = false;
        List<Person> result = tmdb.searchPeople(personName, allResults);
        assertTrue("Couldn't find the person", result.size() > 0);
    }

    /**
     * Test of getPersonInfo method, of class TheMovieDb.
     */
    @Test
    public void testGetPersonInfo() throws MovieDbException {
        LOGGER.info("getPersonInfo");
        Person result = tmdb.getPersonInfo(ID_BRUCE_WILLIS);
        assertTrue("Wrong actor returned", result.getId() == ID_BRUCE_WILLIS);
    }

    /**
     * Test of getPersonCredits method, of class TheMovieDb.
     */
    @Test
    public void testGetPersonCredits() throws MovieDbException {
        LOGGER.info("getPersonCredits");

        List<PersonCredit> people = tmdb.getPersonCredits(ID_BRUCE_WILLIS);
        assertTrue("No cast information", people.size() > 0);
    }

    /**
     * Test of getPersonImages method, of class TheMovieDb.
     */
    @Test
    public void testGetPersonImages() throws MovieDbException {
        LOGGER.info("getPersonImages");

        List<Artwork> artwork = tmdb.getPersonImages(ID_BRUCE_WILLIS);
        assertTrue("No cast information", artwork.size() > 0);
    }

    /**
     * Test of getLatestMovie method, of class TheMovieDb.
     */
    @Test
    public void testGetLatestMovie() throws MovieDbException {
        LOGGER.info("getLatestMovie");
        MovieDb result = tmdb.getLatestMovie();
        assertTrue("No latest movie found", result != null);
        assertTrue("No latest movie found", result.getId() > 0);
    }

    /**
     * Test of compareMovies method, of class TheMovieDb.
     */
    @Test
    public void testCompareMovies() {
        // Not required
    }

    /**
     * Test of setProxy method, of class TheMovieDb.
     */
    @Test
    public void testSetProxy() {
        // Not required
    }

    /**
     * Test of setTimeout method, of class TheMovieDb.
     */
    @Test
    public void testSetTimeout() {
        // Not required
    }

    /**
     * Test of getNowPlayingMovies method, of class TheMovieDb.
     */
    @Test
    public void testGetNowPlayingMovies() throws MovieDbException {
        LOGGER.info("getNowPlayingMovies");
        List<MovieDb> results = tmdb.getNowPlayingMovies("");
        assertTrue("No now playing movies found", !results.isEmpty());
    }

    /**
     * Test of getPopularMovieList method, of class TheMovieDb.
     */
    @Test
    public void testGetPopularMovieList() throws MovieDbException {
        LOGGER.info("getPopularMovieList");
        List<MovieDb> results = tmdb.getPopularMovieList("");
        assertTrue("No popular movies found", !results.isEmpty());
    }
}
