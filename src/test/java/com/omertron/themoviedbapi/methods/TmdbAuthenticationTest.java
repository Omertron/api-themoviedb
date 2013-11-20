/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.TokenAuthorisation;
import com.omertron.themoviedbapi.model.TokenSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

/**
 *
 * @author stuart.boston
 */
public class TmdbAuthenticationTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbAuthenticationTest.class);
    // API
    private static TmdbAuthentication instance;

    public TmdbAuthenticationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbAuthentication(API_KEY, new DefaultPoolingHttpClient());
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
     * Test of getAuthorisationToken method, of class TmdbAuthentication.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetAuthorisationToken() throws MovieDbException {
        LOG.info("getAuthorisationToken");
        TokenAuthorisation token = instance.getAuthorisationToken();
        assertFalse("Token is null", token == null);
        assertTrue("Token is not valid", token.getSuccess());
    }

    /**
     * Test of getSessionToken method, of class TmdbAuthentication.
     *
     * TODO: Cannot be tested without a HTTP authorisation: <br/>
     * http://help.themoviedb.org/kb/api/user-authentication
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Ignore("Session needs to be authorised")
    public void testGetSessionToken() throws MovieDbException {
        LOG.info("getSessionToken");
        TokenAuthorisation token = instance.getAuthorisationToken();
        // Need to authorise the token here.
        TokenSession result = instance.getSessionToken(token);
        assertFalse("Session token is null", result == null);
        assertTrue("Session token is not valid", result.getSuccess());
    }

    /**
     * Test of getGuestSessionToken method, of class TmdbAuthentication.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetGuestSessionToken() throws MovieDbException {
        LOG.info("getGuestSessionToken");
        TokenSession result = instance.getGuestSessionToken();
        assertTrue("Failed to get guest session", result.getSuccess());
    }

}
