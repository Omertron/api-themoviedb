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
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.movie.MovieDbList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TmdbListsTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbGenresTest.class);
    // API
    private static TmdbLists instance;

    public TmdbListsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbLists(API_KEY, null);
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
     * Test of getList method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetList() throws MovieDbException {
        LOG.info("getList");
        String listId = "509ec17b19c2950a0600050d";
        MovieDbList result = instance.getList(listId);
        assertFalse("List not found", result.getItems().isEmpty());
    }

    /**
     * Test of createList method, of class TmdbList.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testCreateList() throws MovieDbException {
        LOG.info("createList");
        String sessionId = "";
        String name = "";
        String description = "";
        String expResult = "";
        String result = instance.createList(sessionId, name, description);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isMovieOnList method, of class TmdbList.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testIsMovieOnList() throws MovieDbException {
        LOG.info("isMovieOnList");
        String listId = "";
        Integer movieId = null;
        boolean expResult = false;
        boolean result = instance.isMovieOnList(listId, movieId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMovieToList method, of class TmdbList.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testAddMovieToList() throws MovieDbException {
        LOG.info("addMovieToList");
        String sessionId = "";
        String listId = "";
        Integer movieId = null;
        StatusCode expResult = null;
        StatusCode result = instance.addMovieToList(sessionId, listId, movieId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeMovieFromList method, of class TmdbList.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testRemoveMovieFromList() throws MovieDbException {
        LOG.info("removeMovieFromList");
        String sessionId = "";
        String listId = "";
        Integer movieId = null;
        StatusCode expResult = null;
        StatusCode result = instance.removeMovieFromList(sessionId, listId, movieId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteMovieList method, of class TmdbList.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testDeleteMovieList() throws MovieDbException {
        LOG.info("deleteMovieList");
        String sessionId = "";
        String listId = "";
        StatusCode expResult = null;
        StatusCode result = instance.deleteMovieList(sessionId, listId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
