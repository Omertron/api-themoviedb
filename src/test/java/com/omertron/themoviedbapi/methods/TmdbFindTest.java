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
import com.omertron.themoviedbapi.enumeration.ExternalSource;
import com.omertron.themoviedbapi.model2.FindResults;
import com.omertron.themoviedbapi.model2.movie.MovieBasic;
import com.omertron.themoviedbapi.model2.person.PersonFind;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 * @author Luca Tagliani
 */
public class TmdbFindTest extends AbstractTests {

    private static TmdbFind instance;
    private static final List<TestID> PERSON_IDS = new ArrayList<TestID>();
    private static final List<TestID> FILM_IDS = new ArrayList<TestID>();
    private static final List<TestID> TV_IDS = new ArrayList<TestID>();

    public TmdbFindTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbFind(getApiKey(), getHttpTools());

        PERSON_IDS.add(new TestID("Mila Kunis", "nm0005109", 18973));
        PERSON_IDS.add(new TestID("Andrew Lincoln", "nm0511088", 7062));

        FILM_IDS.add(new TestID("Jupiter Ascending", "tt1617661", 76757));
        FILM_IDS.add(new TestID("Lucy", "tt2872732", 240832));

        TV_IDS.add(new TestID("The Walking Dead", "tt1520211", 1402));
        TV_IDS.add(new TestID("Supernatural", "tt0460681", 1622));
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
     * Test of Find Movie
     *
     * @throws MovieDbException
     */
//    @Test
    public void testFindMoviesImdbID() throws MovieDbException {
        LOG.info("findMoviesImdbID");
        FindResults result;

        for (TestID test : FILM_IDS) {
            result = instance.find(test.getImdb(), ExternalSource.IMDB_ID, LANGUAGE_DEFAULT);
            assertFalse("No movie for ID " + test.getName(), result.getMovieResults().isEmpty());
            boolean found = false;
            for (MovieBasic m : result.getMovieResults()) {
                if (m.getId() == test.getTmdb()) {
                    found = true;
                    break;
                }
            }
            assertTrue("No movie found: " + test.getName(), found);
        }
    }

    /**
     * Test of Find Person
     *
     * @throws MovieDbException
     * @throws IOException
     */
//    @Test
    public void testFindPersonImdbID() throws MovieDbException, IOException {
        LOG.info("findPersonImdbID");
        FindResults result;

        for (TestID test : PERSON_IDS) {
            result = instance.find(test.getImdb(), ExternalSource.IMDB_ID, LANGUAGE_DEFAULT);
            assertFalse("No person for ID: " + test.getName(), result.getPersonResults().isEmpty());
            boolean found = false;
            for (PersonFind p : result.getPersonResults()) {
                for (Object x : p.getKnownFor()) {
                    LOG.info("  {}", x.toString());
                }
                if (p.getId() == test.getTmdb()) {
                    found = true;
                    break;
                }
            }
            assertTrue("No person found for ID: " + test.getName(), found);
        }
    }

    /**
     * Test of Find TV
     *
     * @throws MovieDbException
     */
    @Test
    public void testFindTvSeriesImdbID() throws MovieDbException {
        LOG.info("findTvSeriesImdbID");
        FindResults result;

        for (TestID test : TV_IDS) {
            result = instance.find(test.getImdb(), ExternalSource.IMDB_ID, LANGUAGE_DEFAULT);
            assertFalse("No TV Show for ID: " + test.getName(), result.getPersonResults().isEmpty());
            boolean found = false;
            for (PersonFind p : result.getPersonResults()) {
                if (p.getId() == test.getTmdb()) {
                    found = true;
                    break;
                }
            }
            assertTrue("No TV Show found for ID: " + test.getName(), found);
        }
    }

}
