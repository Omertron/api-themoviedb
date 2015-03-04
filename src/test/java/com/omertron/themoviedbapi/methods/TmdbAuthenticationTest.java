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
import com.omertron.themoviedbapi.model.authentication.TokenAuthorisation;
import com.omertron.themoviedbapi.model.authentication.TokenSession;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbAuthenticationTest extends AbstractTests {

    private static TmdbAuthentication instance;

    public TmdbAuthenticationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbAuthentication(getApiKey(), getHttpTools());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
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

    /**
     * Test of getSessionTokenLogin method, of class TmdbAuthentication.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetSessionTokenLogin() throws MovieDbException {
        System.out.println("getSessionTokenLogin");
        TokenAuthorisation token = instance.getAuthorisationToken();
        TokenAuthorisation result = instance.getSessionTokenLogin(token, getUsername(), getPassword());
        assertNotNull("Null token returned", result);
        assertTrue("Empty token", StringUtils.isNotBlank(result.getRequestToken()));
    }

}
