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
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.tv.TVEpisodeInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.PostBody;
import com.omertron.themoviedbapi.tools.PostTools;
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
public class TmdbEpisodes extends AbstractMethod {

    private static final int RATING_MAX = 10;

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbEpisodes(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Get the primary information about a TV episode by combination of a season and episode number.
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TVEpisodeInfo getEpisodeInfo(int tvID, int seasonNumber, int episodeNumber, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.EPISODE_NUMBER, episodeNumber);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.EPISODE).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, TVEpisodeInfo.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get TV Episode Info", url, ex);
        }
    }

    /**
     * Look up a TV episode's changes by episode ID
     *
     * @param episodeID
     * @param startDate
     * @param endDate
     * @return
     * @throws MovieDbException
     */
    public ResultList<ChangeKeyItem> getEpisodeChanges(int episodeID, String startDate, String endDate) throws MovieDbException {
        return getMediaChanges(episodeID, startDate, endDate);
    }

    /**
     * This method lets users get the status of whether or not the TV episode has been rated.
     *
     * A valid session id is required.
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @param sessionID
     * @return
     * @throws MovieDbException
     */
    public MediaState getEpisodeAccountState(int tvID, int seasonNumber, int episodeNumber, String sessionID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SESSION_ID, sessionID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.EPISODE_NUMBER, episodeNumber);

        URL url = new ApiUrl(apiKey, MethodBase.EPISODE).subMethod(MethodSub.ACCOUNT_STATES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, MediaState.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get account state", url, ex);
        }
    }

    /**
     * Get the TV episode credits by combination of season and episode number.
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @return
     * @throws MovieDbException
     */
    public MediaCreditList getEpisodeCredits(int tvID, int seasonNumber, int episodeNumber) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.EPISODE_NUMBER, episodeNumber);

        URL url = new ApiUrl(apiKey, MethodBase.EPISODE).subMethod(MethodSub.CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            return MAPPER.readValue(webpage, MediaCreditList.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get credits", url, ex);
        }
    }

    /**
     * Get the external ids for a TV episode by comabination of a season and episode number.
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ExternalID getEpisodeExternalID(int tvID, int seasonNumber, int episodeNumber, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.EPISODE_NUMBER, episodeNumber);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.EPISODE).subMethod(MethodSub.EXTERNAL_IDS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, ExternalID.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get external IDs", url, ex);
        }
    }

    /**
     * Get the images (episode stills) for a TV episode by combination of a season and episode number.
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @return
     * @throws MovieDbException
     */
    public ResultList<Artwork> getEpisodeImages(int tvID, int seasonNumber, int episodeNumber) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.EPISODE_NUMBER, episodeNumber);

        URL url = new ApiUrl(apiKey, MethodBase.EPISODE).subMethod(MethodSub.IMAGES).buildUrl(parameters);
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
     * This method lets users rate a TV episode. A valid session id or guest session id is required.
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @param rating
     * @param sessionID
     * @param guestSessionID
     * @return
     * @throws MovieDbException
     */
    public StatusCode postEpisodeRating(int tvID, int seasonNumber, int episodeNumber, int rating, String sessionID, String guestSessionID) throws MovieDbException {
        if (rating < 0 || rating > RATING_MAX) {
            throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Rating out of range");
        }

        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.EPISODE_NUMBER, episodeNumber);
        parameters.add(Param.SESSION_ID, sessionID);
        parameters.add(Param.GUEST_SESSION_ID, guestSessionID);

        URL url = new ApiUrl(apiKey, MethodBase.EPISODE).subMethod(MethodSub.RATING).buildUrl(parameters);
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
     * Get the videos that have been added to a TV episode (teasers, clips, etc...)
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<Video> getEpisodeVideos(int tvID, int seasonNumber, int episodeNumber, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.EPISODE_NUMBER, episodeNumber);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.EPISODE).subMethod(MethodSub.VIDEOS).buildUrl(parameters);
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
