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
import com.omertron.themoviedbapi.TestSuite;
import com.omertron.themoviedbapi.enumeration.ExternalSource;
import com.omertron.themoviedbapi.model.FindResults;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
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
    private static final List<TestID> PERSON_IDS = new ArrayList<>();
    private static final List<TestID> FILM_IDS = new ArrayList<>();
    private static final List<TestID> TV_IDS = new ArrayList<>();

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
    @Test
    public void testFindMoviesImdbID() throws MovieDbException {
        LOG.info("findMoviesImdbID");
        FindResults result;

        for (TestID test : FILM_IDS) {
            LOG.info("Testing {}", test);
            result = instance.find(test.getImdb(), ExternalSource.IMDB_ID, LANGUAGE_DEFAULT);
            TestSuite.test(result.getMovieResults(), "Movies IMDB");
            TestSuite.testId(result.getMovieResults(), test.getTmdb(), "Movie");
        }
    }

    /**
     * Test of Find Person
     *
     * @throws MovieDbException
     * @throws IOException
     */
    @Test
    public void testFindPersonImdbID() throws MovieDbException, IOException {
        LOG.info("findPersonImdbID");
        FindResults result;

        for (TestID test : PERSON_IDS) {
            LOG.info("Testing {}", test);
            result = instance.find(test.getImdb(), ExternalSource.IMDB_ID, LANGUAGE_DEFAULT);
            TestSuite.test(result.getPersonResults(), "Person IMDB");
            TestSuite.testId(result.getPersonResults(), test.getTmdb(), "Person");
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
            LOG.info("Testing {}", test);
            result = instance.find(test.getImdb(), ExternalSource.IMDB_ID, LANGUAGE_DEFAULT);
            TestSuite.test(result.getTvResults(), "TV IMDB");
            TestSuite.testId(result.getTvResults(), test.getTmdb(), "TV");
        }
    }

}
