/*
 *      Copyright (c) 2004-2013 Stuart Boston
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
import com.omertron.themoviedbapi.TheMovieDbApi;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.*;
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.ExternalIds;
import com.omertron.themoviedbapi.model.person.PersonCredits;
import com.omertron.themoviedbapi.model.tv.TVEpisode;
import com.omertron.themoviedbapi.model.tv.TVSeason;
import com.omertron.themoviedbapi.model.tv.TVSeries;
import com.omertron.themoviedbapi.model.tv.TVSeriesBasic;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for the TV Method
 *
 * @author stuart.boston
 */
public class TVTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TVTest.class);
    // API Key
    private static final String API_KEY = "5a1a77e2eba8984804586122754f969f";
    // API
    private static TheMovieDbApi tmdb;
    // IDs
    private static final int ID_BIG_BANG_THEORY = 1418;

    public TVTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        tmdb = new TheMovieDbApi(API_KEY);
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
     * Test of searchTv method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testSearchTv() throws MovieDbException {
        LOG.info("searchTv");
        TmdbResultsList<TVSeriesBasic> results = tmdb.searchTv("Big Bang Theory", 0, LANGUAGE_DEFAULT);
        assertFalse("No shows found, should be at least 1", results.getResults().isEmpty());
    }

    /**
     * Test of getTv method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTv() throws MovieDbException {
        LOG.info("getTv");
        TVSeries result = tmdb.getTv(ID_BIG_BANG_THEORY, LANGUAGE_DEFAULT);
        assertEquals("Wrong title returned", "The Big Bang Theory", result.getName());
        assertTrue("No seasons returned", result.getNumberSeasons() >= 5);
        assertTrue("No episodes returned", result.getNumberEpisodes() > 100);
        assertFalse("No genres returned", result.getGenres().isEmpty());
        assertFalse("No created by", result.getCreatedBy().isEmpty());
    }

    /**
     * Test of getTvCredits method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTvCredits() throws MovieDbException {
        LOG.info("getTvCredits");

        PersonCredits result = tmdb.getTvCredits(ID_BIG_BANG_THEORY, LANGUAGE_DEFAULT);
        assertFalse("No cast", result.getCast().isEmpty());
        assertFalse("No crew", result.getCrew().isEmpty());
        assertTrue("Guest stars returned", result.getGuestStar().isEmpty());
    }

    /**
     * Test of getTvExternalIds method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTvExternalIds() throws MovieDbException {
        LOG.info("getTvExternalIds");
        ExternalIds result = tmdb.getTvExternalIds(ID_BIG_BANG_THEORY, LANGUAGE_DEFAULT);
        assertFalse("No ids found", result.getIds().isEmpty());
        assertTrue("TMDB Id not found", result.hasId("id"));
        assertEquals("Wrong id returned", Integer.toString(ID_BIG_BANG_THEORY), result.getId("id"));
        assertEquals("Wrong TVDB Id returned", "80379", result.getId("tvdb_id"));
    }

    /**
     * Test of getTvImages method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTvImages() throws MovieDbException {
        LOG.info("getTvImages");
        TmdbResultsList<Artwork> result = tmdb.getTvImages(ID_BIG_BANG_THEORY, LANGUAGE_DEFAULT);
        assertFalse("No results found", result.getResults().isEmpty());
    }

    /**
     * Test of getTvSeason method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTvSeason() throws MovieDbException {
        LOG.info("getTvSeason");

        TVSeason result = tmdb.getTvSeason(ID_BIG_BANG_THEORY, 1, LANGUAGE_DEFAULT);
        assertFalse("No episodes found for season", result.getEpisodes().isEmpty());
        assertEquals("Wrong air date", "2007-09-24", result.getAirDate());
        assertEquals("Wrong season name", "Season 1", result.getName());
    }

    /**
     * Test of getTvSeasonExternalIds method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTvSeasonExternalIds() throws MovieDbException {
        LOG.info("getTvSeasonExternalIds");

        ExternalIds result = tmdb.getTvSeasonExternalIds(ID_BIG_BANG_THEORY, 1, LANGUAGE_DEFAULT);
        assertFalse("No ids found", result.getIds().isEmpty());
        assertTrue("TMDB Id not found", result.hasId("id"));
        assertEquals("Wrong season id returned", "3738", result.getId("id"));
        assertEquals("Wrong TVDB Id returned", "28047", result.getId("tvdb_id"));
    }

    /**
     * Test of getTvSeasonImages method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTvSeasonImages() throws MovieDbException {
        LOG.info("getTvSeasonImages");
        TmdbResultsList<Artwork> result = tmdb.getTvSeasonImages(ID_BIG_BANG_THEORY, 1, LANGUAGE_DEFAULT);
        assertFalse("No results found", result.getResults().isEmpty());
    }

    /**
     * Test of getTvEpisode method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTvEpisode() throws MovieDbException {
        LOG.info("getTvEpisode");
        TVEpisode result = tmdb.getTvEpisode(ID_BIG_BANG_THEORY, 1, 1, LANGUAGE_DEFAULT);
        assertEquals("Wrong ID", 64766, result.getId());
        assertEquals("Wrong date", "2007-09-24", result.getAirDate());
        assertTrue("No overview", StringUtils.isNotBlank(result.getOverview()));
    }

    /**
     * Test of getTvEpisodeCredits method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetTvEpisodeCredits() throws MovieDbException {
        LOG.info("getTvEpisodeCredits");
        PersonCredits result = tmdb.getTvEpisodeCredits(ID_BIG_BANG_THEORY, 1, 1, LANGUAGE_DEFAULT);
        assertFalse("No cast", result.getCast().isEmpty());
        assertFalse("No crew", result.getCrew().isEmpty());
        assertFalse("No Guest stars returned", result.getGuestStar().isEmpty());
    }

    /**
     * Test of getTvEpisodeExternalIds method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Ignore("Find an episode with ids to test this on")
    public void testGetTvEpisodeExternalIds() throws MovieDbException {
        LOG.info("getTvEpisodeExternalIds");
        ExternalIds result = tmdb.getTvEpisodeExternalIds(ID_BIG_BANG_THEORY, 1, 1, LANGUAGE_DEFAULT);
        LOG.info("{}", result);
        assertFalse("No ids found", result.getIds().isEmpty());
        assertTrue("TMDB Id not found", result.hasId("id"));
        assertEquals("Wrong season id returned", "3738", result.getId("id"));
        assertEquals("Wrong TVDB Id returned", "28047", result.getId("tvdb_id"));
    }

    /**
     * Test of getTvEpisodeImages method, of class TV.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    //@Test
    public void testGetTvEpisodeImages() throws MovieDbException {
        LOG.info("getTvEpisodeImages");
        String result = tmdb.getTvEpisodeImages(ID_BIG_BANG_THEORY, 1, 1, LANGUAGE_DEFAULT);
        LOG.info("{}", result);
        fail("Unfinished");
    }

}
