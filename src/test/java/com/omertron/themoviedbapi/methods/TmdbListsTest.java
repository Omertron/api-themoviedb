/*
 *      Copyright (c) 2004-2016 Stuart Boston
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
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.list.ListItem;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TmdbListsTest extends AbstractTests {

    private static TmdbLists instance;
    private static final String RESULT = "Result: {}";
    private static final String INVALID_RESPONSE = "Invalid response: ";
    private static final int ID_JUPITER_ASCENDING = 76757;
    private static final int ID_BIG_HERO_6 = 177572;
    // Status codes
    private static final int SC_SUCCESS_UPD = 12;
    private static final int SC_SUCCESS_DEL = 13;
    // Expected exception
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbLists(getApiKey(), getHttpTools());
    }

    @Test
    public void testSuite() throws MovieDbException {
        // Test the list creation
        String listId = testCreateList();

        // Add two items to the list
        testAddItem(listId, ID_JUPITER_ASCENDING);
        testAddItem(listId, ID_BIG_HERO_6);

        // Get information on the list
        testGetList(listId);

        // Check item status on the list
        testCheckItemStatus(listId, ID_JUPITER_ASCENDING);

        // Delete an item from the list
        testRemoveItem(listId, ID_BIG_HERO_6);

        // Clear the list
        testClear(listId);

        // Delete the list
        // API v4 ONLY
//        testDeleteList(listId);

    }

    /**
     * Test of getList method, of class TmdbLists.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    private void testGetList(String listId) throws MovieDbException {
        LOG.info("getList - List: {}", listId);

        ListItem result = instance.getList(listId);
        LOG.info("Found {} movies on list", result.getItems().size());
        assertFalse("No movies in list", result.getItems().isEmpty());
    }

    /**
     * Test of createList method, of class TmdbLists.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    private String testCreateList() throws MovieDbException {
        LOG.info("createList");
        int r = new Random().nextInt();

        String name = "Random list name #" + r;
        String description = "This is random list number " + r + " used for testing purposes";
        String result = instance.createList(getSessionId(), name, description);
        assertTrue("No list ID returned", StringUtils.isNotBlank(result));

        LOG.info("List created: {}", result);
        return result;
    }

    /**
     * Test of checkItemStatus method, of class TmdbLists.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    private void testCheckItemStatus(String listId, int mediaId) throws MovieDbException {
        LOG.info("checkItemStatus - List: {}, Media: {}", listId, mediaId);
        boolean expResult = true;
        boolean result = instance.checkItemStatus(listId, mediaId);
        assertEquals("Item is not on list!", expResult, result);
    }

    /**
     * Test of deleteList method, of class TmdbLists.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    private void testDeleteList(String listId) throws MovieDbException {
        LOG.info("deleteList - List: {}", listId);
        StatusCode result = instance.deleteList(getSessionId(), listId);
        LOG.info(RESULT, result);

        // We expect there to be an exception thrown here
        exception.expect(MovieDbException.class);
        ListItem result2 = instance.getList(listId);
        LOG.info(RESULT, result2);
    }

    /**
     * Test of addItem method, of class TmdbLists.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    private void testAddItem(String listId, int mediaId) throws MovieDbException {
        LOG.info("addItem - List: {}, Media: {}", listId, mediaId);
        StatusCode result = instance.addItem(getSessionId(), listId, mediaId);
        assertEquals(INVALID_RESPONSE + result.toString(), SC_SUCCESS_UPD, result.getCode());
    }

    /**
     * Test of removeItem method, of class TmdbLists.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    private void testRemoveItem(String listId, int mediaId) throws MovieDbException {
        LOG.info("removeItem - List: {}, Media: {}", listId, mediaId);
        StatusCode result = instance.removeItem(getSessionId(), listId, mediaId);
        assertEquals(INVALID_RESPONSE + result.toString(), SC_SUCCESS_DEL, result.getCode());
    }

    /**
     * Test of clear method, of class TmdbLists.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    private void testClear(String listId) throws MovieDbException {
        LOG.info("clear - List: {}", listId);
        StatusCode result = instance.clear(getSessionId(), listId, true);
        assertEquals(INVALID_RESPONSE + result.toString(), SC_SUCCESS_UPD, result.getCode());
    }

}
