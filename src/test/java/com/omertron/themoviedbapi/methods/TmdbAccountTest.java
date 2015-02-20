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
import com.omertron.themoviedbapi.model.Account;
import com.omertron.themoviedbapi.model.MovieDbList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbAccountTest extends AbstractTests {

    private static TmdbAccount instance;
    // Constants
    private static final int ID_MOVIE_FIGHT_CLUB = 550;

    public TmdbAccountTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbAccount(getApiKey(), getHttpTools());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws MovieDbException {
    }

    @After
    public void tearDown() throws MovieDbException {
    }

    /**
     * Test of getAccountId method, of class TmdbAccount.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetAccount() throws MovieDbException {
        LOG.info("getAccount");
        Account result = instance.getAccount(getSessionId());
        assertNotNull("No account returned", result);
        // Make sure properties are extracted correctly
        assertEquals("Wrong username!", getUsername(), result.getUserName());
    }

    /**
     * Test of getUserLists method, of class TmdbAccount.
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetUserLists() throws MovieDbException {
        LOG.info("getUserLists");
        List<MovieDbList> result = instance.getUserLists(getSessionId(), getAccountId());
    }
}
