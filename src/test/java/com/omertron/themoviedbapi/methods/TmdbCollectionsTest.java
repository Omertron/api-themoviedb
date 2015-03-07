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
import com.omertron.themoviedbapi.TestSuite;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.collection.CollectionInfo;
import com.omertron.themoviedbapi.results.ResultList;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbCollectionsTest extends AbstractTests {

    private static TmdbCollections instance;
    private static final int ID_COLLECTION_STAR_WARS = 10;

    public TmdbCollectionsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbCollections(getApiKey(), getHttpTools());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getCollectionInfo method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetCollectionInfo() throws MovieDbException {
        LOG.info("getCollectionInfo");
        String language = "";
        CollectionInfo result = instance.getCollectionInfo(ID_COLLECTION_STAR_WARS, language);
        assertFalse("No collection information", result.getParts().isEmpty());
    }

    /**
     * Test of getCollectionImages method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetCollectionImages() throws MovieDbException {
        LOG.info("getCollectionImages");
        ResultList<Artwork> result = instance.getCollectionImages(ID_COLLECTION_STAR_WARS, LANGUAGE_DEFAULT);
        TestSuite.test(result,"Collection Images");
    }

}
