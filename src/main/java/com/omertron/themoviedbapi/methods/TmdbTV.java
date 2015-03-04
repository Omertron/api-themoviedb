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
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.movie.AlternativeTitle;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperAlternativeTitles;
import com.omertron.themoviedbapi.wrapper.WrapperChanges;
import java.io.IOException;
import java.net.URL;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the TV Methods
 *
 * @author stuart.boston
 */
public class TmdbTV extends AbstractMethod {

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
     * This method lets users get the status of whether or not the TV show has been rated or added to their favourite or watch
     * lists.
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
        parameters.add(Param.SESSION, sessionID);

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
    public TmdbResultsList<AlternativeTitle> getTVAlternativeTitles(int tvID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.ALT_TITLES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            WrapperAlternativeTitles wrapper = MAPPER.readValue(webpage, WrapperAlternativeTitles.class);
            TmdbResultsList<AlternativeTitle> results = new TmdbResultsList<AlternativeTitle>(wrapper.getTitles());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get alternative titles", url, ex);
        }
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
    public WrapperChanges getTVChanges(int tvID, String startDate, String endDate) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.START_DATE, startDate);
        parameters.add(Param.END_DATE, endDate);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.CHANGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, WrapperChanges.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get movie changes", url, ex);
        }
    }

    /**
     * Get the content ratings for a specific TV show id.
     *
     * @param tvID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVContentRatings(int tvID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.CONTENT_RATINGS).buildUrl(parameters);
        return null;
    }

    /**
     * Get the cast & crew information about a TV series.
     *
     * @param tvID
     * @param language
     * @param appendToResponse
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVCredits(int tvID, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.CREDITS).buildUrl(parameters);
        return null;
    }

    /**
     * Get the external ids that we have stored for a TV series.
     *
     * @param tvID
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVExternalIDs(int tvID, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.EXTERNAL_IDS).buildUrl(parameters);
        return null;
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
    public String getTVImages(int tvID, String language, String... includeImageLanguage) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.IMAGES).buildUrl(parameters);
        return null;
    }

    /**
     * Get the plot keywords for a specific TV show id.
     *
     * @param tvID
     * @param appendToResponse
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVKeywords(int tvID, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.KEYWORDS).buildUrl(parameters);
        return null;
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
    public String postTVRating(int tvID, int rating, String sessionID, String guestSessionID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SESSION, sessionID);
        parameters.add(Param.GUEST_SESSION_ID, guestSessionID);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.RATING).buildUrl(parameters);
        return null;
    }

    /**
     * Get the similar TV shows for a specific tv id.
     *
     * @param tvID
     * @param page
     * @param language
     * @param appendToResponse
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVSimilar(int tvID, Integer page, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.SIMILAR).buildUrl(parameters);
        return null;
    }

    /**
     * Get the list of translations that exist for a TV series. These translations cascade down to the episode level.
     *
     * @param tvID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVTranslations(int tvID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.TRANSLATIONS).buildUrl(parameters);
        return null;
    }

    /**
     * Get the videos that have been added to a TV series (trailers, opening credits, etc...)
     *
     * @param tvID
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVVideos(int tvID, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.VIDEOS).buildUrl(parameters);
        return null;
    }

    /**
     * Get the latest TV show id.
     *
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVLatest() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.LATEST).buildUrl();
        return null;
    }

    /**
     * Get the list of TV shows that are currently on the air.
     *
     * This query looks for any TV show that has an episode with an air date in the next 7 days.
     *
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVOnTheAir(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.ON_THE_AIR).buildUrl(parameters);
        return null;
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
    public String getTVAiringToday(Integer page, String language, String timezone) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.TIMEZONE, timezone);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.AIRING_TODAY).buildUrl(parameters);
        return null;
    }

    /**
     * Get the list of top rated TV shows.
     *
     * By default, this list will only include TV shows that have 2 or more votes.
     *
     * This list refreshes every day.
     *
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVTopRated(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.TOP_RATED).buildUrl(parameters);
        return null;
    }

    /**
     * Get the list of popular TV shows. This list refreshes every day.
     *
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public String getTVPopular(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.TV).subMethod(MethodSub.POPULAR).buildUrl(parameters);
        return null;
    }
}
