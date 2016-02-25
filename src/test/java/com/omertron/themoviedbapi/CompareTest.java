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
package com.omertron.themoviedbapi;

import com.omertron.themoviedbapi.model.movie.MovieInfo;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Stuart.Boston
 */
public class CompareTest {

    private static final Logger LOG = LoggerFactory.getLogger(CompareTest.class);

    private static MovieInfo moviedb;
    private static final String TITLE_MAIN = "Blade Runner";
    private static final String TITLE_OTHER = "Blade Runner Directors Cut";
    private static final String YEAR_FULL = "1982-01-01";
    private static final String YEAR_SHORT = "1982";

    private static final boolean CASE_SENSITIVE = true;
    private static final boolean NOT_CASE_SENSITIVE = false;

    @BeforeClass
    public static void setUpClass() {
        TestLogger.configure();

        // Set the default comparison movie
        moviedb = new MovieInfo();
        moviedb.setTitle(TITLE_MAIN);
        moviedb.setOriginalTitle(TITLE_OTHER);
        moviedb.setReleaseDate(YEAR_FULL);
    }

    /**
     * Exact match
     */
    @Test
    public void testExactMatch() {
        int maxDistance = 0;
        boolean result;

        result = Compare.movies(moviedb, TITLE_MAIN, YEAR_SHORT, maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_OTHER, YEAR_SHORT, maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_MAIN, YEAR_SHORT, maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_OTHER, YEAR_SHORT, maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);

        result = Compare.movies(moviedb, TITLE_MAIN, "", maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_OTHER, "", maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_MAIN, "", maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, TITLE_OTHER, "", maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
    }

    /**
     * Close match
     */
    @Test
    public void testCloseMatch() {
        int maxDistance = 6;
        boolean result;

        String closeMain = "bloderannar";
        String closeOther = "Blade Runner Dir Cut";

        // Make sure they are close enough
        int currentDistance;

        currentDistance = StringUtils.getLevenshteinDistance(TITLE_MAIN, closeMain);
        LOG.info("Distance between '{}' and '{}' is {}", TITLE_MAIN, closeMain, currentDistance);
        assertTrue(currentDistance <= maxDistance);

        currentDistance = StringUtils.getLevenshteinDistance(TITLE_OTHER, closeOther);
        LOG.info("Distance between '{}' and '{}' is {}", TITLE_OTHER, closeOther, currentDistance);
        assertTrue(currentDistance <= maxDistance);

        result = Compare.movies(moviedb, closeMain, YEAR_SHORT, maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeOther, YEAR_SHORT, maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeMain, YEAR_SHORT, maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeOther, YEAR_SHORT, maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);

        result = Compare.movies(moviedb, closeMain, "", maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeOther, "", maxDistance, CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeMain, "", maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
        result = Compare.movies(moviedb, closeOther, "", maxDistance, NOT_CASE_SENSITIVE);
        assertTrue(result);
    }

}
