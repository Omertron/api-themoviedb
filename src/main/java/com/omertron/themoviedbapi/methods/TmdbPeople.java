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
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import com.omertron.themoviedbapi.model2.artwork.Artwork;
import com.omertron.themoviedbapi.model2.artwork.ArtworkMedia;
import com.omertron.themoviedbapi.model2.person.ExternalID;
import com.omertron.themoviedbapi.model2.person.Person;
import com.omertron.themoviedbapi.model2.person.PersonCredits;
import com.omertron.themoviedbapi.model2.person.PersonFind;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperChanges;
import com.omertron.themoviedbapi.wrapper.WrapperGenericList;
import com.omertron.themoviedbapi.wrapper.WrapperImages;
import java.io.IOException;
import java.net.URL;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the People Methods
 *
 * @author stuart.boston
 */
public class TmdbPeople extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbPeople(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Get the general person information for a specific id.
     *
     * @param personId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public Person getPersonInfo(int personId, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, Person.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person info", url, ex);
        }
    }

    /**
     * Get the movie credits for a specific person id.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public PersonCredits getPersonMovieCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.MOVIE_CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, PersonCredits.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person movie credits", url, ex);
        }
    }

    /**
     * Get the TV credits for a specific person id.
     *
     * To get the expanded details for each record, call the /credit method with
     * the provided credit_id.
     *
     * This will provide details about which episode and/or season the credit is
     * for.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public PersonCredits getPersonTVCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.TV_CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, PersonCredits.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person TV credits", url, ex);
        }
    }

    /**
     * Get the combined (movie and TV) credits for a specific person id.
     *
     * To get the expanded details for each TV record, call the /credit method
     * with the provided credit_id.
     *
     * This will provide details about which episode and/or season the credit is
     * for.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public PersonCredits getPersonCombinedCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.COMBINED_CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, PersonCredits.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person combined credits", url, ex);
        }
    }

    /**
     * Get the external ids for a specific person id.
     *
     * @param personId
     * @return
     * @throws MovieDbException
     */
    public ExternalID getPersonExternalIds(int personId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.EXTERNAL_IDS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, ExternalID.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person external IDs", url, ex);
        }
    }

    /**
     * Get the images for a specific person id.
     *
     * @param personId
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getPersonImages(int personId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.IMAGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperImages wrapper = MAPPER.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll(ArtworkType.PROFILE));
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person images", url, ex);
        }
    }

    /**
     * Get the images that have been tagged with a specific person id.
     *
     * We return all of the image results with a media object mapped for each
     * image.
     *
     * @param personId
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public TmdbResultsList<ArtworkMedia> getPersonTaggedImages(int personId, Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.TAGGED_IMAGES).buildUrl(parameters);

        WrapperGenericList<ArtworkMedia> wrapper;

        String webpage = httpTools.getRequest(url);
        try {
            TypeReference tr = new TypeReference<WrapperGenericList<ArtworkMedia>>() {
            };
            wrapper = MAPPER.readValue(webpage, tr);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get tagged images", url, ex);
        }

        TmdbResultsList<ArtworkMedia> results = new TmdbResultsList<ArtworkMedia>(wrapper.getResults());
        results.copyWrapper(wrapper);
        return results;
    }

    /**
     * Get the changes for a specific person id.
     *
     * Changes are grouped by key, and ordered by date in descending order.
     *
     * By default, only the last 24 hours of changes are returned.
     *
     * The maximum number of days that can be returned in a single request is
     * 14.
     *
     * The language is present on fields that are translatable.
     *
     * @param personId
     * @param startDate
     * @param endDate
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public WrapperChanges getPersonChanges(int personId, String startDate, String endDate) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.START_DATE, startDate);
        parameters.add(Param.END_DATE, endDate);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.CHANGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, WrapperChanges.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person changes", url, ex);
        }
    }

    /**
     * Get the list of popular people on The Movie Database.
     *
     * This list refreshes every day.
     *
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<PersonFind> getPersonPopular(Integer page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.POPULAR).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        WrapperGenericList<PersonFind> wrapper;
        try {
            TypeReference tr = new TypeReference<WrapperGenericList<PersonFind>>() {
            };
            wrapper = MAPPER.readValue(webpage, tr);
            TmdbResultsList<PersonFind> results = new TmdbResultsList<PersonFind>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person popular", url, ex);
        }
    }

    /**
     * Get the latest person id.
     *
     * @return
     * @throws MovieDbException
     */
    public Person getPersonLatest() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.LATEST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, Person.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get latest person", url, ex);
        }
    }
}
