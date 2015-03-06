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
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.interfaces.IIdentification;
import com.omertron.themoviedbapi.model.person.Person;
import com.omertron.themoviedbapi.model.tv.TVEpisodeInfo;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.model.tv.TVSeasonInfo;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestSuite {

    private TestSuite() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static int randomRating() {
        return new Random().nextInt(10) + 1;
    }

    public static void test(ResultList<?> result) {
        assertNotNull("Null result list", result);
        assertFalse("Empty result list", result.isEmpty());
        test(result.getResults());
    }

    public static void test(List<?> result) {
        assertNotNull("Null results", result);
        assertFalse("Empty results", result.isEmpty());
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

    public static void test(TVInfo test) {
        assertTrue("No ID", test.getId() > 0);
        assertFalse("No runtime", test.getEpisodeRunTime().isEmpty());
        assertFalse("No genres", test.getGenres().isEmpty());
        assertTrue("No season count", test.getNumberOfSeasons() > 0);
        assertTrue("No episode count", test.getNumberOfEpisodes() > 0);
    }

    public static void test(TVEpisodeInfo test) {
        assertTrue("No ID", test.getId() > 0);
        assertTrue("No name", StringUtils.isNotBlank(test.getName()));
        assertTrue("No crew", test.getCrew().size() > 0);
        assertTrue("No guest stars", test.getGuestStars().size() > 0);
    }

    public static void test(Person test) {
        assertTrue("Missing bio", StringUtils.isNotBlank(test.getBiography()));
        assertTrue("Missing birthday", StringUtils.isNotBlank(test.getBirthday()));
        assertTrue("Missing homepage", StringUtils.isNotBlank(test.getHomepage()));
        assertTrue("Missing name", StringUtils.isNotBlank(test.getName()));
        assertTrue("Missing birth place", StringUtils.isNotBlank(test.getPlaceOfBirth()));
        assertTrue("Missing artwork", StringUtils.isNotBlank(test.getProfilePath()));
        assertTrue("Missing bio", test.getPopularity() > 0F);
    }

    public static void test(TVSeasonInfo test) {
        assertTrue("No ID", test.getId() > 0);
        assertTrue("No name", StringUtils.isNotBlank(test.getName()));
        assertTrue("No overview", StringUtils.isNotBlank(test.getOverview()));
        assertTrue("No episodes", test.getEpisodes().size() > 0);
    }

    public static void testId(ResultList<? extends IIdentification> result, int id, String message) {
        testId(result.getResults(), id, message);
    }

    public static void testId(List<? extends IIdentification> result, int id, String message) {
        boolean found = false;
        for (IIdentification item : result) {
            if (item.getId() == id) {
                found = true;
                break;
            }
        }
        assertTrue(message + " ID " + id + " not found in list", found);
    }
}
