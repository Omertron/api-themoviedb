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
package com.omertron.themoviedbapi.tools;

import com.omertron.themoviedbapi.TestLogger;
import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test case for ApiUrl
 *
 * @author Stuart
 */
public class ApiUrlTest {

    private static final Logger LOG = LoggerFactory.getLogger(ApiUrlTest.class);
    private static final String APIKEY = "APIKEY";

    public ApiUrlTest() {
    }

    @BeforeClass
    public static void setUpClass() {
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

    @Test
    public void testConfiguration() {
        LOG.info("Configuration Test");

        URL result = new ApiUrl(APIKEY, MethodBase.CONFIGURATION).buildUrl();
        String expResult = "http://api.themoviedb.org/3/configuration?api_key=APIKEY";
        assertEquals("Wrong config URL", expResult, result.toString());
    }

    @Test
    public void testQuery() {
        LOG.info("Query Test");
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, "query");

        URL result = new ApiUrl(APIKEY, MethodBase.MOVIE).buildUrl(parameters);
        String expResult = "http://api.themoviedb.org/3/movie?api_key=APIKEY&query=query";
        assertEquals("Wrong Query URL", expResult, result.toString());
    }

    @Test
    public void testQuerySub() {
        LOG.info("Query-Sub Test");
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, "query");

        URL result = new ApiUrl(APIKEY, MethodBase.MOVIE).subMethod(MethodSub.LATEST).buildUrl(parameters);
        String expResult = "http://api.themoviedb.org/3/movie/latest?api_key=APIKEY&query=query";
        assertEquals("Wrong Query-Sub URL", expResult, result.toString());
    }

    @Test
    public void testQueryExtra() {
        LOG.info("Query Extra Test");
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, "query");
        parameters.add(Param.PAGE, 1);
        parameters.add(Param.LANGUAGE, "lang");

        URL result = new ApiUrl(APIKEY, MethodBase.MOVIE).subMethod(MethodSub.LATEST).buildUrl(parameters);
        String expResult = "http://api.themoviedb.org/3/movie/latest?api_key=APIKEY&query=query&language=lang&page=1";
        assertEquals("Wrong Query Extra URL", expResult, result.toString());
    }

    @Test
    public void testId() {
        LOG.info("ID Test");
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, "ID");

        URL result = new ApiUrl(APIKEY, MethodBase.MOVIE).buildUrl(parameters);
        String expResult = "http://api.themoviedb.org/3/movie/ID?api_key=APIKEY";
        assertEquals("Wrong ID URL", expResult, result.toString());
    }

    @Test
    public void testIdSub() {
        LOG.info("ID-Sub Test");
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, "ID");

        URL result = new ApiUrl(APIKEY, MethodBase.MOVIE).subMethod(MethodSub.LATEST).buildUrl(parameters);
        String expResult = "http://api.themoviedb.org/3/movie/ID/latest?api_key=APIKEY";
        assertEquals("Wrong ID-Sub URL", expResult, result.toString());
    }

    @Test
    public void testIdExtra() {
        LOG.info("ID Extra Test");
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, "ID");
        parameters.add(Param.PAGE, 1);
        parameters.add(Param.LANGUAGE, "lang");

        URL result = new ApiUrl(APIKEY, MethodBase.MOVIE).subMethod(MethodSub.LATEST).buildUrl(parameters);
        String expResult = "http://api.themoviedb.org/3/movie/ID/latest?api_key=APIKEY&language=lang&page=1";
        assertEquals("Wrong Query Extra URL", expResult, result.toString());
    }

    @Test
    public void testTV() {
        LOG.info("TV test");
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, "ID");

        URL result = new ApiUrl(APIKEY, MethodBase.TV).buildUrl(parameters);
        String expResult = "http://api.themoviedb.org/3/tv/ID?api_key=APIKEY";
        assertEquals("Wrong TV URL", expResult, result.toString());
    }

    @Test
    public void testTVSeason() {
        LOG.info("TV Season test");
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, "ID");
        parameters.add(Param.SEASON_NUMBER, "SEASON");

        URL result = new ApiUrl(APIKEY, MethodBase.SEASON).buildUrl(parameters);
        String expResult = "http://api.themoviedb.org/3/tv/ID/season/SEASON?api_key=APIKEY";
        assertEquals("Wrong TV Season URL", expResult, result.toString());
    }

    @Test
    public void testTVEpsiode() {
        LOG.info("TV Episode test");
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, "ID");
        parameters.add(Param.SEASON_NUMBER, "SEASON");
        parameters.add(Param.EPISODE_NUMBER, "EPISODE");

        URL result = new ApiUrl(APIKEY, MethodBase.EPISODE).buildUrl(parameters);
        String expResult = "http://api.themoviedb.org/3/tv/ID/season/SEASON/episode/EPISODE?api_key=APIKEY";
        assertEquals("Wrong TV Episode URL", expResult, result.toString());
    }

}
