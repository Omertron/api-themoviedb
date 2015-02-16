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

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.TBD_ExternalSource;
import com.omertron.themoviedbapi.model.TBD_FindResults;
import com.omertron.themoviedbapi.tools.HttpTools;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the Find Methods
 *
 * @author stuart.boston
 */
public class TmdbFind extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbFind(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * You con use this method to find movies, tv series or persons using
     * external ids.
     *
     * Supported query ids are
     * <ul>
     * <li>Movies: imdb_id</li>
     * <li>People: imdb_id, freebase_mid, freebase_id, tvrage_id</li>
     * <li>TV Series: imdb_id, freebase_mid, freebase_id, tvdb_id,
     * tvrage_id</li>
     * <li>TV Seasons: freebase_mid, freebase_id, tvdb_id, tvrage_id</li>
     * <li>TV Episodes: imdb_id, freebase_mid, freebase_id, tvdb_id,
     * tvrage_idimdb_id, freebase_mid, freebase_id, tvrage_id, tvdb_id.
     * </ul>
     *
     * For details see http://docs.themoviedb.apiary.io/#find
     *
     * @param id the external id
     * @param externalSource one of {@link ExternalSource}.
     * @param language the language
     * @return
     * @throws MovieDbException
     */
    public TBD_FindResults find(String id, TBD_ExternalSource externalSource, String language) throws MovieDbException {
        throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "not implemented yet");
    }
}
