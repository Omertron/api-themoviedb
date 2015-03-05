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
package com.omertron.themoviedbapi.tools;

import java.util.EnumSet;
import org.apache.commons.lang3.StringUtils;

/**
 * Parameters for use in the URL
 *
 * @author Stuart
 */
public enum Param {

    ADULT("include_adult="),
    API_KEY("api_key="),
    APPEND("append_to_response="),
    CERTIFICATION("certification="),
    CERTIFICATION_COUNTRY("certification_country="),
    CERTIFICATION_LTE("certification.lte="),
    CONFIRM("confirm="),
    COUNTRY("country="),
    END_DATE("end_date="),
    EPISODE_NUMBER("episode_number="),
    EXTERNAL_SOURCE("external_source="),
    FAVORITE("favorite="),
    FIRST_AIR_DATE_GTE("first_air_date.gte"),
    FIRST_AIR_DATE_LTE("first_air_date.lte"),
    FIRST_AIR_DATE_YEAR("first_air_date_year="),
    GUEST_SESSION_ID("guest_session_id="),
    ID("id="),
    INCLUDE_ADULT("include_adult="),
    INCLUDE_ALL_MOVIES("include_all_movies="),
    INCLUDE_IMAGE_LANGUAGE("include_image_language="),
    INCLUDE_VIDEO("include_video="),
    LANGUAGE("language="),
    MOVIE_ID("movie_id="),
    MOVIE_WATCHLIST("movie_watchlist="),
    PAGE("page="),
    PASSWORD("password="),
    PRIMARY_RELEASE_YEAR("primary_release_year="),
    QUERY("query="),
    RELEASE_DATE_GTE("release_date.gte="),
    RELEASE_DATE_LTE("release_date.lte="),
    SEARCH_TYPE("search_type="),
    SEASON_NUMBER("season_number="),
    SESSION_ID("session_id="),
    SORT_BY("sort_by="),
    SORT_ORDER("sort_order="),
    START_DATE("start_date="),
    TIMEZONE("timezone="),
    TOKEN("request_token="),
    USERNAME("username="),
    VALUE("value="),
    VOTE_AVERAGE_GTE("vote_average.gte="),
    VOTE_AVERAGE_LTE("vote_average.lte="),
    VOTE_COUNT_GTE("vote_count.gte="),
    VOTE_COUNT_LTE("vote_count.lte="),
    WITH_CAST("with_cast="),
    WITH_COMPANIES("with_companies="),
    WITH_CREW("with_crew="),
    WITH_GENRES("with_genres="),
    WITH_KEYWORDS("with_keywords="),
    WITH_NETWORKS("with_networks="),
    WITH_PEOPLE("with_people="),
    YEAR("year=");

    private final String value;

    private Param(String value) {
        this.value = value;
    }

    /**
     * Get the URL parameter to use
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Convert a string into an Enum type
     *
     * @param value
     * @return
     */
    public static Param fromString(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (final Param param : EnumSet.allOf(Param.class)) {
                if (value.equalsIgnoreCase(param.value)) {
                    return param;
                }
            }
        }

        // We've not found the type!
        throw new IllegalArgumentException("Value '" + value + "' not recognised");
    }

}
