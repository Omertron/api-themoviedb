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
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.person.ExternalID;
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

    public static void test(ResultList<?> result, String message) {
        assertNotNull(message + ": Null result list", result);
        assertFalse(message + ": Empty result list", result.isEmpty());
        test(result.getResults(), message);
    }

    public static void test(List<?> result, String message) {
        assertNotNull(message + ": Null results", result);
        assertFalse(message + ": Empty results", result.isEmpty());
    }

    public static void test(MovieBasic test) {
        String message = test.getClass().getSimpleName();
        assertTrue(message + ": Missing title", isNotBlank(test.getTitle()));
        assertTrue(message + ": Missing poster", isNotBlank(test.getPosterPath()));
        assertTrue(message + ": Missing release date", isNotBlank(test.getReleaseDate()));
    }

    public static void test(MovieInfo test) {
        String message = test.getClass().getSimpleName();
        assertTrue(message + ": Missing title", isNotBlank(test.getTitle()));
        assertTrue(message + ": Missing original title", isNotBlank(test.getOriginalTitle()));
        assertTrue(message + ": Missing poster", isNotBlank(test.getPosterPath()));
        assertTrue(message + ": Missing release date", isNotBlank(test.getReleaseDate()));
        assertTrue(message + ": Missing backdrop", isNotBlank(test.getBackdropPath()));
        assertTrue(message + ": Missing poster path", isNotBlank(test.getPosterPath()));
        assertTrue(message + ": Missing overview", isNotBlank(test.getOverview()));
        assertTrue(message + ": Missing Language", isNotBlank(test.getOriginalLanguage()));
        test(test.getGenres(), "Genres");
        test(test.getProductionCompanies(), "ProductionCompany");
        test(test.getProductionCountries(), "ProductionCountries");
    }

    public static void test(UserList test) {
        String message = test.getClass().getSimpleName();
        assertTrue(message + ": Missing ID", isNotBlank(test.getId()));
        assertTrue(message + ": Missing Description", isNotBlank(test.getDescription()));
    }

    public static void test(TVBasic test) {
        String message = test.getClass().getSimpleName();
        assertTrue(message + ": Missing name", isNotBlank(test.getName()));
        assertTrue(message + ": Missing poster", isNotBlank(test.getPosterPath()));
        assertTrue(message + ": Missing first air date", isNotBlank(test.getFirstAirDate()));
    }

    public static void test(TVInfo test) {
        String message = test.getClass().getSimpleName();
        assertTrue(message + ": Missing ID", test.getId() > 0);
        assertFalse(message + ": Missing runtime", test.getEpisodeRunTime().isEmpty());
        assertFalse(message + ": Missing genres", test.getGenres().isEmpty());
        assertTrue(message + ": Missing season count", test.getNumberOfSeasons() > 0);
        assertTrue(message + ": Missing episode count", test.getNumberOfEpisodes() > 0);
    }

    public static void test(TVEpisodeInfo test) {
        String message = test.getClass().getSimpleName();
        assertTrue(message + ": Missing ID", test.getId() > 0);
        assertTrue(message + ": Missing name", StringUtils.isNotBlank(test.getName()));
        assertTrue(message + ": Missing crew", test.getCrew().size() > 0);
        assertTrue(message + ": Missing guest stars", test.getGuestStars().size() > 0);
    }

    public static void test(Person test) {
        String message = test.getClass().getSimpleName();
        assertTrue(message + ": Missing bio", StringUtils.isNotBlank(test.getBiography()));
        assertTrue(message + ": Missing birthday", StringUtils.isNotBlank(test.getBirthday()));
        assertTrue(message + ": Missing homepage", StringUtils.isNotBlank(test.getHomepage()));
        assertTrue(message + ": Missing name", StringUtils.isNotBlank(test.getName()));
        assertTrue(message + ": Missing birth place", StringUtils.isNotBlank(test.getPlaceOfBirth()));
        assertTrue(message + ": Missing artwork", StringUtils.isNotBlank(test.getProfilePath()));
        assertTrue(message + ": Missing bio", test.getPopularity() > 0F);
    }

    public static void test(TVSeasonInfo test) {
        String message = test.getClass().getSimpleName();
        assertTrue(message + ": Missing ID", test.getId() > 0);
        assertTrue(message + ": Missing name", StringUtils.isNotBlank(test.getName()));
        assertTrue(message + ": Missing overview", StringUtils.isNotBlank(test.getOverview()));
        assertTrue(message + ": Missing episodes", test.getEpisodes().size() > 0);
    }

    public static void test(ExternalID test) {
        String message = test.getClass().getSimpleName();
        assertTrue(message + ": Missing IMDB ID", StringUtils.isNotBlank(test.getImdbId()));
        boolean found = false;
        found |= StringUtils.isNotBlank(test.getFreebaseId());
        found |= StringUtils.isNotBlank(test.getFreebaseMid());
        found |= StringUtils.isNotBlank(test.getTvdbId());
        found |= StringUtils.isNotBlank(test.getTvrageId());
        assertTrue(message + ": Missing one of the other IDs", found);
    }

    public static void test(MediaCreditList test) {
        String message = test.getClass().getSimpleName();
        boolean found = false;
        found |= test.getCast().isEmpty();
        found |= test.getCrew().isEmpty();
        found |= test.getGuestStars().isEmpty();
        assertTrue(message + ": Missing cast/crew/guest stars information", found);
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
