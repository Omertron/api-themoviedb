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
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * Test cases for TheMovieDB API
 *
 * @author stuart.boston
 */
public class TheMovieDBTest {

    private static final Logger LOGGER = Logger.getLogger(TheMovieDBTest.class);
    private static final String API_KEY = "5a1a77e2eba8984804586122754f969f";
    private static TheMovieDB tmdb;
    /*
     * Test data
     */
    private static final int ID_BLADE_RUNNER = 78;
    private static final int ID_STAR_WARS_COLLECTION = 10;

    public TheMovieDBTest() throws IOException {
        tmdb = new TheMovieDB(API_KEY);
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
     * Test of getConfiguration method, of class TheMovieDB.
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
     * Test of searchMovie method, of class TheMovieDB.
     */
    @Test
    public void testSearchMovie() throws UnsupportedEncodingException {
        LOGGER.info("searchMovie");

        // Try a movie with less than 1 page of results
        List<MovieDB> movieList = tmdb.searchMovie("Blade Runner", "", true);
        assertTrue("No movies found, should be at least 1", movieList.size() > 0);

        // Try a russian langugage movie
        movieList = tmdb.searchMovie("О чём говорят мужчины", "ru", true);
        assertTrue("No movies found, should be at least 1", movieList.size() > 0);

        // Try a movie with more than 20 results
        movieList = tmdb.searchMovie("Star Wars", "en", false);
        assertTrue("Not enough movies found, should be 20", movieList.size() == 20);
    }

    /**
     * Test of getMovieInfo method, of class TheMovieDB.
     */
    @Test
    public void testGetMovieInfo() {
        LOGGER.info("getMovieInfo");
        String language = "en";
        MovieDB result = tmdb.getMovieInfo(ID_BLADE_RUNNER, language);
        assertEquals("Incorrect movie information", "Blade Runner", result.getOriginalTitle());
    }

    /**
     * Test of getMovieAlternativeTitles method, of class TheMovieDB.
     */
    @Test
    public void testGetMovieAlternativeTitles() {
        LOGGER.info("getMovieAlternativeTitles");
        String country = "";
        List<AlternativeTitle> results = tmdb.getMovieAlternativeTitles(ID_BLADE_RUNNER, country);
        assertTrue("No alternative titles found", results.size() > 0);

        country = "US";
        results = tmdb.getMovieAlternativeTitles(ID_BLADE_RUNNER, country);
        assertTrue("No alternative titles found", results.size() > 0);

    }

    /**
     * Test of getMovieCasts method, of class TheMovieDB.
     */
    @Test
    public void testGetMovieCasts() {
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
     * Test of getMovieImages method, of class TheMovieDB.
     */
    @Test
    public void testGetMovieImages() {
        LOGGER.info("getMovieImages");
        String language = "";
        List<Artwork> result = tmdb.getMovieImages(ID_BLADE_RUNNER, language);
        assertFalse("No artwork found", result.isEmpty());
    }

    /**
     * Test of getMovieKeywords method, of class TheMovieDB.
     */
    @Test
    public void testGetMovieKeywords() {
        LOGGER.info("getMovieKeywords");
        List<Keyword> result = tmdb.getMovieKeywords(ID_BLADE_RUNNER);
        assertFalse("No keywords found", result.isEmpty());
    }

    /**
     * Test of getMovieReleaseInfo method, of class TheMovieDB.
     */
    @Test
    public void testGetMovieReleaseInfo() {
        LOGGER.info("getMovieReleaseInfo");
        List<ReleaseInfo> result = tmdb.getMovieReleaseInfo(ID_BLADE_RUNNER, "");
        assertFalse("Release information missing", result.isEmpty());
    }

    /**
     * Test of getMovieTrailers method, of class TheMovieDB.
     */
    @Test
    public void testGetMovieTrailers() {
        LOGGER.info("getMovieTrailers");
        List<Trailer> result = tmdb.getMovieTrailers(ID_BLADE_RUNNER, "");
        assertFalse("Movie trailers missing", result.isEmpty());
    }

    /**
     * Test of getMovieTranslations method, of class TheMovieDB.
     */
    @Test
    public void testGetMovieTranslations() {
        LOGGER.info("getMovieTranslations");
        List<Translation> result = tmdb.getMovieTranslations(ID_BLADE_RUNNER);
        assertFalse("No translations found", result.isEmpty());
    }

    /**
     * Test of getCollectionInfo method, of class TheMovieDB.
     */
    @Test
    public void testGetCollectionInfo() {
        LOGGER.info("getCollectionInfo");
        String language = "";
        CollectionInfo result = tmdb.getCollectionInfo(ID_STAR_WARS_COLLECTION, language);
        assertFalse("No collection information", result.getParts().isEmpty());
    }

    @Test
    public void testCreateImageUrl() {
        LOGGER.info("createImageUrl");
        MovieDB movie = tmdb.getMovieInfo(ID_BLADE_RUNNER, "");
        String result = tmdb.createImageUrl(movie.getPosterPath(), "original").toString();
        assertTrue("Error compiling image URL", !result.isEmpty());
    }
}
