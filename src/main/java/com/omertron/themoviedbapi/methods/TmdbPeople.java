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
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import static com.omertron.themoviedbapi.methods.AbstractMethod.MAPPER;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.artwork.ArtworkMedia;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.credits.CreditBasic;
import com.omertron.themoviedbapi.model.credits.CreditMovieBasic;
import com.omertron.themoviedbapi.model.credits.CreditTVBasic;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.person.PersonCreditList;
import com.omertron.themoviedbapi.model.person.PersonCreditsMixIn;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.model.person.PersonInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.results.WrapperGenericList;
import com.omertron.themoviedbapi.results.WrapperImages;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
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
    public PersonInfo getPersonInfo(int personId, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.APPEND, appendToResponse);

        // Switch combined credits for tv & movie.
        String atr = (String) parameters.get(Param.APPEND);
        if (atr.contains("combined_credits")) {
            atr = atr.replace("combined_credits", "tv_credits,movie_credits");
            parameters.add(Param.APPEND, atr);
        }

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, PersonInfo.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person info", url, ex);
        }
    }

    /**
     * Get the movie credits for a specific person id.
     *
     * @param personId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public PersonCreditList<CreditMovieBasic> getPersonMovieCredits(int personId, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.MOVIE_CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            TypeReference tr = new TypeReference<PersonCreditList<CreditMovieBasic>>() {
            };
            return MAPPER.readValue(webpage, tr);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person movie credits", url, ex);
        }
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
     * @return
     * @throws MovieDbException
     */
    public PersonCreditList<CreditTVBasic> getPersonTVCredits(int personId, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.TV_CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            TypeReference tr = new TypeReference<PersonCreditList<CreditTVBasic>>() {
            };
            return MAPPER.readValue(webpage, tr);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get person TV credits", url, ex);
        }
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
     * @return
     * @throws MovieDbException
     */
    public PersonCreditList<CreditBasic> getPersonCombinedCredits(int personId, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.COMBINED_CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.addMixIn(PersonCreditList.class, PersonCreditsMixIn.class);
            TypeReference tr = new TypeReference<PersonCreditList<CreditBasic>>() {
            };
            return mapper.readValue(webpage, tr);
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
    public ResultList<Artwork> getPersonImages(int personId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.IMAGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperImages wrapper = MAPPER.readValue(webpage, WrapperImages.class);
            ResultList<Artwork> results = new ResultList<>(wrapper.getAll(ArtworkType.PROFILE));
            wrapper.setResultProperties(results);
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
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<ArtworkMedia> getPersonTaggedImages(int personId, Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.TAGGED_IMAGES).buildUrl(parameters);
        WrapperGenericList<ArtworkMedia> wrapper = processWrapper(getTypeReference(ArtworkMedia.class), url, "tagged images");
        return wrapper.getResultsList();
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
     * @param personId
     * @param startDate
     * @param endDate
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<ChangeKeyItem> getPersonChanges(int personId, String startDate, String endDate) throws MovieDbException {
        return getMediaChanges(personId, startDate, endDate);
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
    public ResultList<PersonFind> getPersonPopular(Integer page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.POPULAR).buildUrl(parameters);
        WrapperGenericList<PersonFind> wrapper = processWrapper(getTypeReference(PersonFind.class), url, "person popular");
        return wrapper.getResultsList();
    }

    /**
     * Get the latest person id.
     *
     * @return
     * @throws MovieDbException
     */
    public PersonInfo getPersonLatest() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.LATEST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, PersonInfo.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get latest person", url, ex);
        }
    }
}
