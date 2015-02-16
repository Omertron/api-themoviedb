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

public enum MethodSub {

    ALT_TITLES("alternative_titles"),
    CASTS("casts"),
    CHANGES("changes"),
    COLLECTION("collection"),
    COMPANY("company"),
    CREDITS("credits"),
    FAVORITE("favorite"),
    FAVORITE_MOVIES("favorite_movies"),
    GUEST_SESSION("guest_session/new"),
    IMAGES("images"),
    ITEM_STATUS("item_status"),
    KEYWORD("keyword"),
    KEYWORDS("keywords"),
    LATEST("latest"),
    LIST("list"),
    LISTS("lists"),
    MOVIE("movie"),
    MOVIES("movies"),
    WATCHLIST("watchlist"),
    WATCHLIST_MOVIES("watchlist/movies"),
    WATCHLIST_TV("watchlist/tv"),
    NOW_PLAYING("now-playing"),
    PERSON("person"),
    POPULAR("popular"),
    RATED_MOVIES("rated/movies"),
    RATING("rating"),
    RELEASES("releases"),
    REVIEWS("reviews"),
    SESSION_NEW("session/new"),
    SIMILAR_MOVIES("similar_movies"),
    TOKEN_NEW("token/new"),
    TOKEN_VALIDATE("token/validate_with_login"),
    TOP_RATED("top-rated"),
    TRANSLATIONS("translations"),
    UPCOMING("upcoming"),
    VIDEOS("videos"),
    ADD_ITEM("add_item"),
    REMOVE_ITEM("remove_item"),
    MOVIE_LIST("movie/list"),
    TV_LIST("tv/list");

    private final String value;

    private MethodSub(String value) {
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
    public static MethodSub fromString(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (final MethodSub method : EnumSet.allOf(MethodSub.class)) {
                if (value.equalsIgnoreCase(method.value)) {
                    return method;
                }
            }
        }

        // We've not found the type!
        throw new IllegalArgumentException("Method '" + value + "' not recognised");
    }
}
