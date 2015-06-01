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
import static com.omertron.themoviedbapi.methods.AbstractMethod.MAPPER;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.media.AlternativeTitle;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.media.Translation;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.person.ContentRating;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.results.WrapperGenericList;
import com.omertron.themoviedbapi.results.WrapperImages;
import com.omertron.themoviedbapi.results.WrapperTranslations;
import com.omertron.themoviedbapi.results.WrapperVideos;
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
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the TV Methods
 *
 * @author stuart.boston
 */
public class TmdbTV extends AbstractMethod {

    private static final int RATING_MAX = 10;

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbTV(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Get the primary information about a TV series by id.
     *
     * @param tvID
     * @param language
     * @param appendToResponse
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public TVInfo getTVInfo(int tvID, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.TV).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, TVInfo.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get TV Info", url, ex);
        }
    }

    /**
     * This method lets users get the status of whether or not the TV show has
     * been rated or added to their favourite or watch lists.
     *
     * A valid session id is required.
     *
     * @param tvID
     * @param sessionID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public MediaState getTVAccountState(int tvID, String sessionID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SESSION_ID, sessionID);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.ACCOUNT_STATES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, MediaState.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get account state", url, ex);
        }
    }

    /**
     * Get the alternative titles for a specific show ID.
     *
     * @param tvID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<AlternativeTitle> getTVAlternativeTitles(int tvID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.ALT_TITLES).buildUrl(parameters);
        WrapperGenericList<AlternativeTitle> wrapper = processWrapper(getTypeReference(AlternativeTitle.class), url, "alternative titles");
        return wrapper.getResultsList();
    }

    /**
     * Get the changes for a specific TV show id.
     *
     * @param tvID
     * @param startDate
     * @param endDate
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<ChangeKeyItem> getTVChanges(int tvID, String startDate, String endDate) throws MovieDbException {
        return getMediaChanges(tvID, startDate, endDate);
    }

    /**
     * Get the content ratings for a specific TV show id.
     *
     * @param tvID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<ContentRating> getTVContentRatings(int tvID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.CONTENT_RATINGS).buildUrl(parameters);
        WrapperGenericList<ContentRating> wrapper = processWrapper(getTypeReference(ContentRating.class), url, "content rating");
        return wrapper.getResultsList();
    }

    /**
     * Get the cast & crew information about a TV series.
     *
     * @param tvID
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public MediaCreditList getTVCredits(int tvID, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            return MAPPER.readValue(webpage, MediaCreditList.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get credits", url, ex);
        }
    }

    /**
     * Get the external ids that we have stored for a TV series.
     *
     * @param tvID
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ExternalID getTVExternalIDs(int tvID, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.EXTERNAL_IDS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, ExternalID.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get external IDs", url, ex);
        }
    }

    /**
     * Get the images (posters and backdrops) for a TV series.
     *
     * @param tvID
     * @param language
     * @param includeImageLanguage
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<Artwork> getTVImages(int tvID, String language, String... includeImageLanguage) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.INCLUDE_IMAGE_LANGUAGE, includeImageLanguage);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.IMAGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperImages wrapper = MAPPER.readValue(webpage, WrapperImages.class);
            ResultList<Artwork> results = new ResultList<>(wrapper.getAll());
            wrapper.setResultProperties(results);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get images", url, ex);
        }
    }

    /**
     * Get the plot keywords for a specific TV show id.
     *
     * @param tvID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<Keyword> getTVKeywords(int tvID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.KEYWORDS).buildUrl(parameters);
        WrapperGenericList<Keyword> wrapper = processWrapper(getTypeReference(Keyword.class), url, "keywords");
        return wrapper.getResultsList();
    }

    /**
     * This method lets users rate a TV show.
     *
     * A valid session id or guest session id is required.
     *
     * @param tvID
     * @param rating
     * @param sessionID
     * @param guestSessionID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public StatusCode postTVRating(int tvID, int rating, String sessionID, String guestSessionID) throws MovieDbException {
        if (rating < 0 || rating > RATING_MAX) {
            throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Rating out of range");
        }

        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SESSION_ID, sessionID);
        parameters.add(Param.GUEST_SESSION_ID, guestSessionID);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.RATING).buildUrl(parameters);
        String jsonBody = new PostTools()
                .add(PostBody.VALUE, rating)
                .build();
        String webpage = httpTools.postRequest(url, jsonBody);

        try {
            return MAPPER.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to post rating", url, ex);
        }
    }

    /**
     * Get the similar TV shows for a specific tv id.
     *
     * @param tvID
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVInfo> getTVSimilar(int tvID, Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.SIMILAR).buildUrl(parameters);
        WrapperGenericList<TVInfo> wrapper = processWrapper(getTypeReference(TVInfo.class), url, "similar TV shows");
        return wrapper.getResultsList();
    }

    /**
     * Get the list of translations that exist for a TV series. These
     * translations cascade down to the episode level.
     *
     * @param tvID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<Translation> getTVTranslations(int tvID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.TRANSLATIONS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperTranslations wrapper = MAPPER.readValue(webpage, WrapperTranslations.class);
            ResultList<Translation> results = new ResultList<>(wrapper.getTranslations());
            wrapper.setResultProperties(results);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get translations", url, ex);
        }
    }

    /**
     * Get the videos that have been added to a TV series (trailers, opening
     * credits, etc...)
     *
     * @param tvID
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<Video> getTVVideos(int tvID, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.VIDEOS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperVideos wrapper = MAPPER.readValue(webpage, WrapperVideos.class);
            ResultList<Video> results = new ResultList<>(wrapper.getVideos());
            wrapper.setResultProperties(results);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get videos", url, ex);
        }
    }

    /**
     * Get the latest TV show id.
     *
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public TVInfo getLatestTV() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.LATEST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, TVInfo.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get latest TV show", url, ex);
        }
    }

    /**
     * Get the list of TV shows that are currently on the air.
     *
     * This query looks for any TV show that has an episode with an air date in
     * the next 7 days.
     *
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVBasic> getTVOnTheAir(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.ON_THE_AIR).buildUrl(parameters);
        WrapperGenericList<TVBasic> wrapper = processWrapper(getTypeReference(TVBasic.class), url, "on the air");
        return wrapper.getResultsList();
    }

    /**
     * Get the list of TV shows that air today.
     *
     * Without a specified timezone, this query defaults to EST
     *
     * @param page
     * @param language
     * @param timezone
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVBasic> getTVAiringToday(Integer page, String language, String timezone) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.TIMEZONE, timezone);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.AIRING_TODAY).buildUrl(parameters);
        WrapperGenericList<TVBasic> wrapper = processWrapper(getTypeReference(TVBasic.class), url, "airing today");
        return wrapper.getResultsList();
    }

    /**
     * Get the list of top rated TV shows.
     *
     * By default, this list will only include TV shows that have 2 or more
     * votes.
     *
     * This list refreshes every day.
     *
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVBasic> getTVTopRated(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.TOP_RATED).buildUrl(parameters);
        WrapperGenericList<TVBasic> wrapper = processWrapper(getTypeReference(TVBasic.class), url, "top rated TV shows");
        return wrapper.getResultsList();
    }

    /**
     * Get the list of popular TV shows. This list refreshes every day.
     *
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVBasic> getTVPopular(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.POPULAR).buildUrl(parameters);
        WrapperGenericList<TVBasic> wrapper = processWrapper(getTypeReference(TVBasic.class), url, "popular TV shows");
        return wrapper.getResultsList();
    }
}
