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

import com.omertron.themoviedbapi.model.movie.MovieInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * Compare various objects to see if the are logically the same.
 *
 * Allows for some variance of the details
 *
 * @author Stuart.Boston
 */
public class Compare {

    // Constants
    private static final int YEAR_LENGTH = 4;

    private Compare() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Compare the MovieDB object with a title & year
     *
     * @param moviedb The moviedb object to compare too
     * @param title The title of the movie to compare
     * @param year The year of the movie to compare exact match
     * @return True if there is a match, False otherwise.
     */
    public static boolean movies(final MovieInfo moviedb, final String title, final String year) {
        return movies(moviedb, title, year, 0, true);
    }

    /**
     * Compare the MovieDB object with a title & year
     *
     * @param moviedb The moviedb object to compare too
     * @param title The title of the movie to compare
     * @param year The year of the movie to compare
     * @param maxDistance The Levenshtein Distance between the two titles. 0 =
     * exact match
     * @param caseSensitive true if the comparison is to be case sensitive
     * @return True if there is a match, False otherwise.
     */
    public static boolean movies(final MovieInfo moviedb, final String title, final String year, int maxDistance, boolean caseSensitive) {
        if ((moviedb == null) || (StringUtils.isBlank(title))) {
            return false;
        }

        String primaryTitle, firstCompareTitle, secondCompareTitle;
        if (caseSensitive) {
            primaryTitle = title;
            firstCompareTitle = moviedb.getOriginalTitle();
            secondCompareTitle = moviedb.getTitle();
        } else {
            primaryTitle = title.toLowerCase();
            firstCompareTitle = moviedb.getTitle().toLowerCase();
            secondCompareTitle = moviedb.getOriginalTitle().toLowerCase();
        }

        if (isValidYear(year) && isValidYear(moviedb.getReleaseDate())) {
            // Compare with year
            String movieYear = moviedb.getReleaseDate().substring(0, YEAR_LENGTH);
            return movieYear.equals(year) && compareTitles(primaryTitle, firstCompareTitle, secondCompareTitle, maxDistance);
        }

        // Compare without year
        return compareTitles(primaryTitle, firstCompareTitle, secondCompareTitle, maxDistance);
    }

    /**
     * Compare a title with two other titles.
     *
     * @param primaryTitle Primary title
     * @param firstCompareTitle First title to compare with
     * @param secondCompareTitle Second title to compare with
     * @param maxDistance Maximum difference between the titles
     * @return
     */
    private static boolean compareTitles(String primaryTitle, String firstCompareTitle, String secondCompareTitle, int maxDistance) {
        // Compare with the first title
        if (compareDistance(primaryTitle, firstCompareTitle, maxDistance)) {
            return true;
        }

        // Compare with the other title
        return compareDistance(primaryTitle, secondCompareTitle, maxDistance);
    }

    /**
     * Compare the MovieDB object with a title & year, case sensitive
     *
     * @param moviedb
     * @param title
     * @param year
     * @param maxDistance
     * @return
     */
    public static boolean movies(final MovieInfo moviedb, final String title, final String year, int maxDistance) {
        return Compare.movies(moviedb, title, year, maxDistance, true);
    }

    /**
     * Compare the Levenshtein Distance between the two strings
     *
     * @param title1
     * @param title2
     * @param distance
     */
    private static boolean compareDistance(final String title1, final String title2, int distance) {
        return StringUtils.getLevenshteinDistance(title1, title2) <= distance;
    }

    /**
     * Check the year is not blank or UNKNOWN
     *
     * @param year
     */
    private static boolean isValidYear(final String year) {
        return StringUtils.isNotBlank(year) && !"UNKNOWN".equals(year);
    }
}
