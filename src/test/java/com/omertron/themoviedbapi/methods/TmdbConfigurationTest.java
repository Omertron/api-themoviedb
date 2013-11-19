/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.Configuration;
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
        instance = new TmdbConfiguration(API_KEY, null);
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

}
