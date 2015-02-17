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
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.getApiKey();
import static com.omertron.themoviedbapi.TheMovieDbApiTest.SESSION_ID_APITESTS;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.StatusCodeList;
import com.omertron.themoviedbapi.model.movie.MovieDbList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TmdbListsTest extends AbstractTests{

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbGenresTest.class);
    // API
    private static TmdbLists instance;

    public TmdbListsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        TestLogger.Configure();
        instance = new TmdbLists(getApiKey(),getHttpTools());
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
     * Test of createList method, of class TmdbList.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Ignore("Not working")
    public void test1CreateList() throws MovieDbException {
        LOG.info("createList");
        String name = "My Totally Awesome List";
        String description = "This list was created to share all of the totally awesome movies I've seen.";
        StatusCodeList result = instance.createList(SESSION_ID_APITESTS, name, description);
        LOG.info(result.toString());
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getList method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void test2GetList() throws MovieDbException {
        LOG.info("getList");
        String listId = "509ec17b19c2950a0600050d";
        MovieDbList result = instance.getList(listId);
        assertFalse("List not found", result.getItems().isEmpty());
    }

    /**
     * Test of addMovieToList method, of class TmdbList.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Ignore("Not working")
    public void test3AddMovieToList() throws MovieDbException {
        LOG.info("addMovieToList");
        String listId = "";
        Integer movieId = null;
        StatusCode expResult = null;
        StatusCode result = instance.addMovieToList(SESSION_ID_APITESTS, listId, movieId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isMovieOnList method, of class TmdbList.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Ignore("Not working")
    public void test4IsMovieOnList() throws MovieDbException {
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
     * Test of removeMovieFromList method, of class TmdbList.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Ignore("Not working")
    public void test5RemoveMovieFromList() throws MovieDbException {
        LOG.info("removeMovieFromList");
        String listId = "";
        Integer movieId = null;
        StatusCode expResult = null;
        StatusCode result = instance.removeMovieFromList(SESSION_ID_APITESTS, listId, movieId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteMovieList method, of class TmdbList.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Ignore("Not working")
    public void test6DeleteMovieList() throws MovieDbException {
        LOG.info("deleteMovieList");
        String listId = "";
        StatusCode expResult = null;
        StatusCode result = instance.deleteMovieList(SESSION_ID_APITESTS, listId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
