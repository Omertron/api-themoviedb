/*
 *      Copyright (c) 2004-2014 Stuart Boston
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
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_ID;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_LANGUAGE;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_PAGE;
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.person.NewPersonCredits;
import com.omertron.themoviedbapi.model.person.Person;
import com.omertron.themoviedbapi.model.person.PersonBasic;
import com.omertron.themoviedbapi.model.type.ArtworkType;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.wrapper.WrapperImages;
import com.omertron.themoviedbapi.wrapper.person.WrapperPersonList;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the People methods
 *
 * @author stuart.boston
 */
public class TmdbPeople extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbPeople.class);
    // API URL Parameters
    private static final String BASE_PERSON = "person/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbPeople(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * This method is used to retrieve all of the basic person information.
     *
     * It will return the single highest rated profile image.
     *
     * @param personId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    /**
     * This method is used to retrieve all of the basic person information.It will return the single highest rated profile image.
     *
     * @param personId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public Person getPersonInfo(int personId, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON);

        apiUrl.addArgument(PARAM_ID, personId);
        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return MAPPER.readValue(webpage, Person.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get movie info: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
    public NewPersonCredits getPersonMovieCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/movie_credits");

        apiUrl.addArgument(PARAM_ID, personId);
        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }
        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return MAPPER.readValue(webpage, NewPersonCredits.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get movie credits: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the TV credits for a specific person id.
     * <p>
     * To get the expanded details for each record, call the /credit method with the provided credit_id.
     * <p>
     * This will provide details about which episode and/or season the credit is for.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public NewPersonCredits getPersonTvCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/tv_credits");

        apiUrl.addArgument(PARAM_ID, personId);
        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }
        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return MAPPER.readValue(webpage, NewPersonCredits.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get TV credits: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the combined (movie and TV) credits for a specific person id.<p>
     * To get the expanded details for each record, call the /credit method with the provided credit_id.
     * <p>
     * This will provide details about which episode and/or season the credit is for.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public NewPersonCredits getPersonCombinedCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/combined_credits");

        apiUrl.addArgument(PARAM_ID, personId);
        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }
        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return MAPPER.readValue(webpage, NewPersonCredits.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get combined credits: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the profile images for a person.
     *
     * @param personId
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getPersonImages(int personId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/images");

        apiUrl.addArgument(PARAM_ID, personId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperImages wrapper = MAPPER.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll(ArtworkType.PROFILE));
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get person images: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
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
     * @throws MovieDbException
     */
    public String getPersonChanges(int personId, String startDate, String endDate) throws MovieDbException {
        throw new MovieDbException(MovieDbException.MovieDbExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }

    /**
     * Get the list of popular people on The Movie Database.
     *
     * This list refreshes every day.
     *
     * @param page Use 0 (zero) to specify no page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<PersonBasic> getPersonPopular(int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/popular");

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperPersonList wrapper = MAPPER.readValue(webpage, WrapperPersonList.class);
            TmdbResultsList<PersonBasic> results = new TmdbResultsList<PersonBasic>(wrapper.getPersonList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get person images: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the latest person id.
     *
     * @return
     * @throws MovieDbException
     */
    public Person getPersonLatest() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/latest");
        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return MAPPER.readValue(webpage, Person.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get latest person: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

}
