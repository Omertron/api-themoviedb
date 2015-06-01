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
import com.omertron.themoviedbapi.TestID;
import com.omertron.themoviedbapi.model.network.Network;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbNetworksTest extends AbstractTests {

    private static TmdbNetworks instance;
    private static final List<TestID> testIDs = new ArrayList<>();

    public TmdbNetworksTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbNetworks(getApiKey(), getHttpTools());
        testIDs.add(new TestID("Fuji Television", "", 1));
        testIDs.add(new TestID("Sonshine Media Network International", "", 200));
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getNetworkInfo method, of class TmdbNetworks.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetNetworkInfo() throws MovieDbException {
        LOG.info("getNetworkInfo");

        Network result;
        for (TestID t : testIDs) {
            result = instance.getNetworkInfo(t.getTmdb());
            assertEquals("Wrong network returned", t.getName(), result.getName());
        }
    }

}
