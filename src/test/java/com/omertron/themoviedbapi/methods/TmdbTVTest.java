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
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;

/**
 * Test for the TV Method
 *
 * @author stuart.boston
 */
public class TmdbTVTest extends AbstractTests {

    private static TmdbTV instance;
    // IDs
    private static final int ID_BIG_BANG_THEORY = 1418;

    public TmdbTVTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbTV(getApiKey(), getHttpTools());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    //@Test
    public void testGetTv() throws MovieDbException {
        fail("The test case is a prototype.");
    }

}
