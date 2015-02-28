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
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import static com.omertron.themoviedbapi.methods.AbstractMethod.getTypeReference;
import com.omertron.themoviedbapi.model.person.Person;
import com.omertron.themoviedbapi.model.person.PersonCredit;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperGenericList;
import com.omertron.themoviedbapi.wrapper.WrapperImages;
import com.omertron.themoviedbapi.wrapper.WrapperPersonList;
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
    public TmdbResultsList<PersonCredit> getPersonMovieCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);
        URL url = new ApiUrl(apiKey, MethodBase.PERSON).setSubMethod(MethodSub.MOVIE_CREDITS).buildUrl(parameters);

        WrapperGenericList<PersonCredit> wrapper = processWrapper(getTypeReference(PersonCredit.class), url, "person movie credits");
        TmdbResultsList<PersonCredit> results = new TmdbResultsList<PersonCredit>(wrapper.getResults());
        results.copyWrapper(wrapper);
        return results;
    }

    /**
     * Get the TV credits for a specific person id.
     *
     * To get the expanded details for each record, call the /credit method with the provided credit_id.
     *
     * This will provide details about which episode and/or season the credit is for.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Person> getPersonTVCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        return null;
    }

    /**
     * Get the combined (movie and TV) credits for a specific person id.
     *
     * To get the expanded details for each TV record, call the /credit method with the provided credit_id.
     *
     * This will provide details about which episode and/or season the credit is for.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Person> getPersonCombinedCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        return null;
    }

    /**
     * Get the external ids for a specific person id.
     *
     * @param personId
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Person> getPersonExternalIds(int personId) throws MovieDbException {
        return null;
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

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).setSubMethod(MethodSub.IMAGES).buildUrl(parameters);
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
     * We return all of the image results with a media object mapped for each image.
     *
     * @param personId
     * @param page
     * @param language
     * @return
     */
    public TmdbResultsList<Person> getPersonTaggedImages(int personId, Integer page, String language) {
        return null;
    }

    /**
     * Get the changes for a specific person id.
     *
     * Changes are grouped by key, and ordered by date in descending order.
     *
     * By default, only the last 24 hours of changes are returned.
     *
     * The maximum number of days that can be returned in a single request is 14.
     *
     * The language is present on fields that are translatable.
     *
     * @param persondId
     * @param startDate
     * @param endDate
     * @return
     */
    public TmdbResultsList<Person> getPersonChanges(int persondId, String startDate, String endDate) {
        return null;
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
    public TmdbResultsList<Person> getPersonPopular(Integer page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).setSubMethod(MethodSub.POPULAR).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperPersonList wrapper = MAPPER.readValue(webpage, WrapperPersonList.class);
            TmdbResultsList<Person> results = new TmdbResultsList<Person>(wrapper.getPersonList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get popular person", url, ex);
        }
    }

    /**
     * Get the latest person id.
     *
     * @return
     * @throws MovieDbException
     */
    public Person getPersonLatest() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.PERSON).setSubMethod(MethodSub.LATEST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, Person.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get latest person", url, ex);
        }
    }

}
