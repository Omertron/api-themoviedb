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
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.tv.TVSeasonInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.results.WrapperImages;
import com.omertron.themoviedbapi.results.WrapperVideos;
import java.io.IOException;
import java.net.URL;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the TV Methods
 *
 * @author stuart.boston
 */
public class TmdbSeasons extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbSeasons(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Get the primary information about a TV season by its season number.
     *
     * @param tvID
     * @param seasonNumber
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TVSeasonInfo getSeasonInfo(int tvID, int seasonNumber, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, TVSeasonInfo.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get TV Season Info", url, ex);
        }
    }

    /**
     * Look up a TV season's changes by season ID.
     *
     * @param tvID
     * @param startDate
     * @param endDate
     * @return
     * @throws MovieDbException
     */
    public ResultList<ChangeKeyItem> getSeasonChanges(int tvID, String startDate, String endDate) throws MovieDbException {
        return getMediaChanges(tvID, startDate, endDate);
    }

    /**
     * This method lets users get the status of whether or not the TV episodes of a season have been rated.
     *
     * A valid session id is required.
     *
     * @param tvID
     * @param sessionID
     * @return
     * @throws MovieDbException
     */
    public MediaState getSeasonAccountState(int tvID, String sessionID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SESSION_ID, sessionID);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.ACCOUNT_STATES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, MediaState.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get account state", url, ex);
        }
    }

    /**
     * Get the cast & crew credits for a TV season by season number.
     *
     * @param tvID
     * @param seasonNumber
     * @return
     * @throws MovieDbException
     */
    public MediaCreditList getSeasonCredits(int tvID, int seasonNumber) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            return MAPPER.readValue(webpage, MediaCreditList.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get credits", url, ex);
        }
    }

    /**
     * Get the external ids that we have stored for a TV season by season number.
     *
     * @param tvID
     * @param seasonNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ExternalID getSeasonExternalID(int tvID, int seasonNumber, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.EXTERNAL_IDS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, ExternalID.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get external IDs", url, ex);
        }
    }

    /**
     * Get the images that we have stored for a TV season by season number.
     *
     * @param tvID
     * @param seasonNumber
     * @param language
     * @param includeImageLanguage
     * @return
     * @throws MovieDbException
     */
    public ResultList<Artwork> getSeasonImages(int tvID, int seasonNumber, String language, String... includeImageLanguage) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, includeImageLanguage);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.IMAGES).buildUrl(parameters);
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
     * Get the videos that have been added to a TV season (trailers, teasers, etc...)
     *
     * @param tvID
     * @param seasonNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<Video> getSeasonVideos(int tvID, int seasonNumber, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.VIDEOS).buildUrl(parameters);
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
}
