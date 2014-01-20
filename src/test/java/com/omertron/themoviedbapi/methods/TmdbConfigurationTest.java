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
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.Configuration;
import com.omertron.themoviedbapi.model.type.ArtworkType;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

/**
 *
 * @author stuart.boston
 */
public class TmdbConfigurationTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbConfigurationTest.class);

    // API
    private static TmdbConfiguration instance;

    public TmdbConfigurationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        TestLogger.Configure();
        instance = new TmdbConfiguration(API_KEY, new DefaultPoolingHttpClient());
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
     * Test of getConfig method, of class TmdbConfiguration.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetConfig() throws MovieDbException {
        LOG.info("getConfig");
        Configuration result = instance.getConfig();
        LOG.info(result.toString());
        assertFalse("No backdrop sizes", result.getBackdropSizes().isEmpty());
        assertFalse("No logo sizes", result.getLogoSizes().isEmpty());
        assertFalse("No poster sizes", result.getPosterSizes().isEmpty());
        assertFalse("No profile sizes", result.getProfileSizes().isEmpty());
        assertTrue("No base url", StringUtils.isNotBlank(result.getBaseUrl()));
        assertTrue("No secure base url", StringUtils.isNotBlank(result.getSecureBaseUrl()));
    }

    /**
     * Test of createImageUrl method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testCreateImageUrl() throws MovieDbException {
        LOG.info("createImageUrl");
        Configuration config = instance.getConfig();

        String result = config.createImageUrl("http://mediaplayersite.com/image.jpg", ArtworkType.POSTER, "original").toString();
        assertTrue("Error compiling image URL", !result.isEmpty());
    }

}
