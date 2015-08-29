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
import com.omertron.themoviedbapi.model.artwork.ArtworkMedia;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.change.ChangeListItem;
import com.omertron.themoviedbapi.model.collection.Collection;
import com.omertron.themoviedbapi.model.company.Company;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.list.UserList;
import com.omertron.themoviedbapi.model.media.AlternativeTitle;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.person.ContentRating;
import com.omertron.themoviedbapi.model.person.PersonInfo;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.model.review.Review;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.results.WrapperChanges;
import com.omertron.themoviedbapi.results.WrapperGenericList;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static final Map<Class, TypeReference> TYPE_REFS = new HashMap<>();

    static {
        TYPE_REFS.put(MovieBasic.class, new TypeReference<WrapperGenericList<MovieBasic>>() {
        });
        TYPE_REFS.put(TVBasic.class, new TypeReference<WrapperGenericList<TVBasic>>() {
        });
        TYPE_REFS.put(UserList.class, new TypeReference<WrapperGenericList<UserList>>() {
        });
        TYPE_REFS.put(Company.class, new TypeReference<WrapperGenericList<Company>>() {
        });
        TYPE_REFS.put(Collection.class, new TypeReference<WrapperGenericList<Collection>>() {
        });
        TYPE_REFS.put(Keyword.class, new TypeReference<WrapperGenericList<Keyword>>() {
        });
        TYPE_REFS.put(MovieInfo.class, new TypeReference<WrapperGenericList<MovieInfo>>() {
        });
        TYPE_REFS.put(PersonInfo.class, new TypeReference<WrapperGenericList<PersonInfo>>() {
        });
        TYPE_REFS.put(PersonFind.class, new TypeReference<WrapperGenericList<PersonFind>>() {
        });
        TYPE_REFS.put(Review.class, new TypeReference<WrapperGenericList<Review>>() {
        });
        TYPE_REFS.put(ChangeListItem.class, new TypeReference<WrapperGenericList<ChangeListItem>>() {
        });
        TYPE_REFS.put(ArtworkMedia.class, new TypeReference<WrapperGenericList<ArtworkMedia>>() {
        });
        TYPE_REFS.put(ContentRating.class, new TypeReference<WrapperGenericList<ContentRating>>() {
        });
        TYPE_REFS.put(TVInfo.class, new TypeReference<WrapperGenericList<TVInfo>>() {
        });
        TYPE_REFS.put(AlternativeTitle.class, new TypeReference<WrapperGenericList<AlternativeTitle>>() {
        });

    }

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
     * Helper function to get a pre-generated TypeReference for a class
     *
     * @param aClass
     * @return
     * @throws MovieDbException
     */
    protected static TypeReference getTypeReference(Class aClass) throws MovieDbException {
        if (TYPE_REFS.containsKey(aClass)) {
            return TYPE_REFS.get(aClass);
        } else {
            throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Class type reference for '" + aClass.getSimpleName() + "' not found!");
        }
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
    protected <T> List<T> processWrapperList(TypeReference typeRef, URL url, String errorMessageSuffix) throws MovieDbException {
        WrapperGenericList<T> val = processWrapper(typeRef, url, errorMessageSuffix);
        return val.getResults();
    }

    /**
     * Process the wrapper list and return the whole wrapper
     *
     * @param <T> Type of list to process
     * @param typeRef
     * @param url URL of the page (Error output only)
     * @param errorMessageSuffix Error message to output (Error output only)
     * @return
     * @throws MovieDbException
     */
    protected <T> WrapperGenericList<T> processWrapper(TypeReference typeRef, URL url, String errorMessageSuffix) throws MovieDbException {
        String webpage = httpTools.getRequest(url);
        try {
            // Due to type erasure, this doesn't work
            // TypeReference<WrapperGenericList<T>> typeRef = new TypeReference<WrapperGenericList<T>>() {};
            return MAPPER.readValue(webpage, typeRef);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get " + errorMessageSuffix, url, ex);
        }
    }

    /**
     *
     * Look up the media's changes by ID
     *
     * @param mediaID
     * @param startDate
     * @param endDate
     * @return
     * @throws MovieDbException
     */
    protected ResultList<ChangeKeyItem> getMediaChanges(int mediaID, String startDate, String endDate) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, mediaID);
        parameters.add(Param.START_DATE, startDate);
        parameters.add(Param.END_DATE, endDate);

        URL url = new ApiUrl(apiKey, MethodBase.EPISODE).subMethod(MethodSub.CHANGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperChanges wrapper = MAPPER.readValue(webpage, WrapperChanges.class);
            ResultList<ChangeKeyItem> results = new ResultList<>(wrapper.getChangedItems());
            wrapper.setResultProperties(results);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get changes", url, ex);
        }
    }
}
