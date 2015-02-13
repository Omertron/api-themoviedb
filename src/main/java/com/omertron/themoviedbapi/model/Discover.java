/*
 *      Copyright (c) 2004-2015 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation;private either version 3 of the License;private or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful;private
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not;private see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.themoviedbapi.model;

import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;

/**
 * Generate a discover object for use in the MovieDbApi
 * <p/>
 * This allows you to just add the search components you are concerned with
 *
 * @author stuart.boston
 */
public class Discover {

    private final TmdbParameters params = new TmdbParameters();

    private static final int YEAR_MIN = 1900;
    private static final int YEAR_MAX = 2100;

    /**
     * Get the parameters
     * <p/>
     * This will be used to construct the URL in the API
     *
     * @return
     */
    public TmdbParameters getParams() {
        return params;
    }

    /**
     * Minimum value is 1 if included.
     *
     * @param page
     * @return
     */
    public Discover page(int page) {
        params.add(Param.PAGE, page);
        return this;
    }

    /**
     * ISO 639-1 code
     *
     * @param language
     * @return
     */
    public Discover language(String language) {
        params.add(Param.LANGUAGE, language);
        return this;
    }

    /**
     * Available options are <br>
     * vote_average.desc<br>
     * vote_average.asc<br>
     * release_date.desc<br>
     * release_date.asc<br>
     * popularity.desc<br>
     * popularity.asc
     *
     * @param sortBy
     * @return
     */
    public Discover sortBy(String sortBy) {
        params.add(Param.SORT_BY, sortBy);
        return this;
    }

    /**
     * Toggle the inclusion of adult titles
     *
     * @param includeAdult
     * @return
     */
    public Discover includeAdult(boolean includeAdult) {
        params.add(Param.ADULT, includeAdult);
        return this;
    }

    /**
     * Filter the results release dates to matches that include this value.
     *
     * @param year
     * @return
     */
    public Discover year(int year) {
        if (checkYear(year)) {
            params.add(Param.YEAR, year);
        }
        return this;
    }

    /**
     * Filter the results so that only the primary release date year has this
     * value
     *
     * @param primaryReleaseYear
     * @return
     */
    public Discover primaryReleaseYear(int primaryReleaseYear) {
        if (checkYear(primaryReleaseYear)) {
            params.add(Param.PRIMARY_RELEASE_YEAR, primaryReleaseYear);
        }
        return this;
    }

    /**
     * Only include movies that are equal to, or have a vote count higher than
     * this value
     *
     * @param voteCountGte
     * @return
     */
    public Discover voteCountGte(int voteCountGte) {
        params.add(Param.VOTE_COUNT_GTE, voteCountGte);
        return this;
    }

    /**
     * Only include movies that are equal to, or have a higher average rating
     * than this value
     *
     * @param voteAverageGte
     * @return
     */
    public Discover voteAverageGte(float voteAverageGte) {
        params.add(Param.VOTE_AVERAGE_GTE, voteAverageGte);
        return this;
    }

    /**
     * Only include movies with the specified genres.
     * <p/>
     * Expected value is an integer (the id of a genre).
     * <p/>
     * Multiple values can be specified.
     * <p/>
     * Comma separated indicates an 'AND' query, while a pipe (|) separated
     * value indicates an 'OR'
     *
     * @param withGenres
     * @return
     */
    public Discover withGenres(String withGenres) {
        params.add(Param.WITH_GENRES, withGenres);
        return this;
    }

    /**
     * The minimum release to include.
     * <p/>
     * Expected format is YYYY-MM-DD.
     *
     * @param releaseDateGte
     * @return
     */
    public Discover releaseDateGte(String releaseDateGte) {
        params.add(Param.RELEASE_DATE_GTE, releaseDateGte);
        return this;
    }

    /**
     * The maximum release to include.
     * <p/>
     * Expected format is YYYY-MM-DD.
     *
     * @param releaseDateLte
     * @return
     */
    public Discover releaseDateLte(String releaseDateLte) {
        params.add(Param.RELEASE_DATE_LTE, releaseDateLte);
        return this;
    }

    /**
     * Only include movies with certifications for a specific country.
     * <p/>
     * When this value is specified, 'certificationLte' is required.
     * <p/>
     * A ISO 3166-1 is expected
     *
     * @param certificationCountry
     * @return
     */
    public Discover certificationCountry(String certificationCountry) {
        params.add(Param.CERTIFICATION_COUNTRY, certificationCountry);
        return this;
    }

    /**
     * Only include movies with this certification and lower.
     * <p/>
     * Expected value is a valid certification for the specified
     * 'certificationCountry'.
     *
     * @param certificationLte
     * @return
     */
    public Discover certificationLte(String certificationLte) {
        params.add(Param.CERTIFICATION_LTE, certificationLte);
        return this;
    }

    /**
     * Filter movies to include a specific company.
     * <p/>
     * Expected value is an integer (the id of a company).
     * <p/>
     * They can be comma separated to indicate an 'AND' query
     *
     * @param withCompanies
     * @return
     */
    public Discover withCompanies(String withCompanies) {
        params.add(Param.WITH_COMPANIES, withCompanies);
        return this;
    }

    /**
     * check the year is between the min and max
     *
     * @param year
     * @return
     */
    private boolean checkYear(int year) {
        return year >= YEAR_MIN && year <= YEAR_MAX;
    }
}
