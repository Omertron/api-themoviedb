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
package com.omertron.themoviedbapi;

import com.omertron.themoviedbapi.model.list.UserList;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.assertTrue;

public class TestSuite {

    private TestSuite() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void test(MovieBasic test) {
        assertTrue("No title", isNotBlank(test.getTitle()));
        assertTrue("No poster", isNotBlank(test.getPosterPath()));
        assertTrue("No release date", isNotBlank(test.getReleaseDate()));
    }

    public static void test(UserList test) {
        assertTrue("No ID", isNotBlank(test.getId()));
        assertTrue("No Description", isNotBlank(test.getDescription()));
    }

    public static void test(TVBasic test) {
        assertTrue("No name", isNotBlank(test.getName()));
        assertTrue("No poster", isNotBlank(test.getPosterPath()));
        assertTrue("No first air date", isNotBlank(test.getFirstAirDate()));
    }

    public static void test(int test) {
    }

    public static void test(float test) {
    }

    public static void test(boolean test) {
    }
}
