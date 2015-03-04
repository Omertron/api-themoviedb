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
import com.omertron.themoviedbapi.model2.artwork.Artwork;
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import com.omertron.themoviedbapi.model2.collection.CollectionInfo;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperImages;
import java.io.IOException;
import java.net.URL;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the Collections Methods
 *
 * @author stuart.boston
 */
public class TmdbCollections extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbCollections(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * This method is used to retrieve all of the basic information about a
     * movie collection.
     *
     * You can get the ID needed for this method by making a getMovieInfo
     * request for the belongs_to_collection.
     *
     * @param collectionId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public CollectionInfo getCollectionInfo(int collectionId, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, collectionId);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.COLLECTION).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, CollectionInfo.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get collection information", url, ex);
        }
    }

    /**
     * Get all of the images for a particular collection by collection id.
     *
     * @param collectionId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getCollectionImages(int collectionId, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, collectionId);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.COLLECTION).subMethod(MethodSub.IMAGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperImages wrapper = MAPPER.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll(ArtworkType.POSTER, ArtworkType.BACKDROP));
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get collection images", url, ex);
        }
    }
}
