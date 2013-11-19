/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.person.PersonCredits;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author stuart.boston
 */
public class TmdbCreditsTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbCreditsTest.class);
    // API
    private static TmdbCredits instance;

    public TmdbCreditsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbCredits(API_KEY, null);
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
     * Test of getCreditInfo method, of class TmdbCredits.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetCreditInfo() throws MovieDbException {
        LOG.info("getCreditInfo");
        String creditId = "525346f619c29579400d4145";
        String language = "";
        PersonCredits result = instance.getCreditInfo(creditId, language);
        assertEquals("Wrong name", "Sean Bean", result.getPerson().getName());
        assertEquals("Wrong job", "Actor", result.getJob());
        assertFalse("No seasons", result.getMedia().getSeasons().isEmpty());
    }

}
