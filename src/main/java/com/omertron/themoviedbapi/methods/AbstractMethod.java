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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model2.movie.MovieFavorite;
import com.omertron.themoviedbapi.model2.tv.TVFavorite;
import com.omertron.themoviedbapi.model2.list.UserList;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.wrapper.WrapperGenericList;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Abstract methods
 *
 * @author Stuart
 */
public class AbstractMethod {

    // The API key to be used
    protected final String apiKey;
    // The HttpTools to use
    protected final HttpTools httpTools;
    // Jackson JSON configuration
    protected static final ObjectMapper MAPPER = new ObjectMapper();
    // Logger
    protected static final Logger LOG = LoggerFactory.getLogger(TmdbAccount.class);
    protected static final TypeReference<WrapperGenericList<MovieFavorite>> TR_MOVIE_FAV = new TypeReference<WrapperGenericList<MovieFavorite>>() {
    };
    protected static final TypeReference<WrapperGenericList<TVFavorite>> TR_TV_FAV = new TypeReference<WrapperGenericList<TVFavorite>>() {
    };
    protected static final TypeReference<WrapperGenericList<UserList>> TR_USER_LIST = new TypeReference<WrapperGenericList<UserList>>() {
    };

    /**
     * Default constructor for the methods
     *
     * @param apiKey
     * @param httpTools
     */
    public AbstractMethod(String apiKey, HttpTools httpTools) {
        this.apiKey = apiKey;
        this.httpTools = httpTools;
    }

    /**
     * Process the wrapper list and return the results
     *
     * @param <T> Type of list to process
     * @param typeRef
     * @param url URL of the page (Error output only)
     * @param errorMessageSuffix Error message to output (Error output only)
     * @return
     * @throws MovieDbException
     */
    public <T> List<T> processWrapperList(TypeReference typeRef, URL url, String errorMessageSuffix) throws MovieDbException {
        String webpage = httpTools.getRequest(url);
        try {
            // Due to type erasure, this doesn't work
            // TypeReference<WrapperGenericList<T>> typeRef = new TypeReference<WrapperGenericList<T>>() {};
            WrapperGenericList<T> results = MAPPER.readValue(webpage, typeRef);
            return results.getResults();
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get " + errorMessageSuffix, url, ex);
        }

    }

}
