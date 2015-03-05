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
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.list.ListStatusCode;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.list.ListItem;
import com.omertron.themoviedbapi.model.list.ListItemStatus;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.PostBody;
import com.omertron.themoviedbapi.tools.PostTools;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the List Methods
 *
 * @author stuart.boston
 */
public class TmdbLists extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbLists(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Get a list by its ID
     *
     * @param listId
     * @return The list and its items
     * @throws MovieDbException
     */
    public ListItem<MovieInfo> getList(String listId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, listId);

        URL url = new ApiUrl(apiKey, MethodBase.LIST).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, new TypeReference<ListItem<MovieInfo>>() {
            });
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get list", url, ex);
        }
    }

    /**
     * Check to see if an ID is already on a list.
     *
     * @param listId
     * @param mediaId
     * @return true if the movie is on the list
     * @throws MovieDbException
     */
    public boolean checkItemStatus(String listId, int mediaId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, listId);
        parameters.add(Param.MOVIE_ID, mediaId);

        URL url = new ApiUrl(apiKey, MethodBase.LIST).subMethod(MethodSub.ITEM_STATUS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, ListItemStatus.class).isItemPresent();
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get item status", url, ex);
        }
    }

    /**
     * This method lets users create a new list. A valid session id is required.
     *
     * @param sessionId
     * @param name
     * @param description
     * @return The list id
     * @throws MovieDbException
     */
    public String createList(String sessionId, String name, String description) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION_ID, sessionId);

        String jsonBody = new PostTools()
                .add(PostBody.NAME, StringUtils.trimToEmpty(name))
                .add(PostBody.DESCRIPTION, StringUtils.trimToEmpty(description))
                .build();

        URL url = new ApiUrl(apiKey, MethodBase.LIST).buildUrl(parameters);
        String webpage = httpTools.postRequest(url, jsonBody);

        try {
            return MAPPER.readValue(webpage, ListStatusCode.class).getListId();
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to create list", url, ex);
        }
    }

    /**
     * This method lets users delete a list that they created. A valid session id is required.
     *
     * @param sessionId
     * @param listId
     * @return
     * @throws MovieDbException
     */
    public StatusCode deleteList(String sessionId, String listId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, listId);
        parameters.add(Param.SESSION_ID, sessionId);

        URL url = new ApiUrl(apiKey, MethodBase.LIST).buildUrl(parameters);
        String webpage = httpTools.deleteRequest(url);

        try {
            return MAPPER.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to delete list", url, ex);
        }
    }

    /**
     * Modify a list
     *
     * This can be used to add or remove an item from the list
     *
     * @param sessionId
     * @param listId
     * @param movieId
     * @param operation
     * @return
     * @throws MovieDbException
     */
    private StatusCode modifyMovieList(String sessionId, String listId, int movieId, MethodSub operation) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION_ID, sessionId);
        parameters.add(Param.ID, listId);

        String jsonBody = new PostTools()
                .add(PostBody.MEDIA_ID, movieId)
                .build();

        URL url = new ApiUrl(apiKey, MethodBase.LIST).subMethod(operation).buildUrl(parameters);
        String webpage = httpTools.postRequest(url, jsonBody);

        try {
            return MAPPER.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to remove item from list", url, ex);
        }
    }

    /**
     * This method lets users add new items to a list that they created.
     *
     * A valid session id is required.
     *
     * @param sessionId
     * @param listId
     * @param mediaId
     * @return
     * @throws MovieDbException
     */
    public StatusCode addItem(String sessionId, String listId, int mediaId) throws MovieDbException {
        return modifyMovieList(sessionId, listId, mediaId, MethodSub.ADD_ITEM);
    }

    /**
     * This method lets users delete items from a list that they created.
     *
     * A valid session id is required.
     *
     * @param sessionId
     * @param listId
     * @param mediaId
     * @return
     * @throws MovieDbException
     */
    public StatusCode removeItem(String sessionId, String listId, int mediaId) throws MovieDbException {
        return modifyMovieList(sessionId, listId, mediaId, MethodSub.REMOVE_ITEM);
    }

    /**
     * Clear all of the items within a list.
     *
     * This is a irreversible action and should be treated with caution.
     *
     * A valid session id is required. A call without confirm=true will return status code 29.
     *
     * @param sessionId
     * @param listId
     * @param confirm
     * @return
     * @throws MovieDbException
     */
    public StatusCode clear(String sessionId, String listId, boolean confirm) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION_ID, sessionId);
        parameters.add(Param.ID, listId);
        parameters.add(Param.CONFIRM, confirm);

        URL url = new ApiUrl(apiKey, MethodBase.LIST).subMethod(MethodSub.CLEAR).buildUrl(parameters);
        String webpage = httpTools.postRequest(url, "");

        try {
            return MAPPER.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to clear list", url, ex);
        }

    }

}
