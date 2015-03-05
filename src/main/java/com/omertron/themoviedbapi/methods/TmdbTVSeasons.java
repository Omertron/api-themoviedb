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
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import java.net.URL;

/**
 * Class to hold the TV Methods
 *
 * @author stuart.boston
 */
public class TmdbTVSeasons extends AbstractMethod {

    private static final int RATING_MAX = 10;

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbTVSeasons(String apiKey, HttpTools httpTools) {
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
    public String getSeasonInfo(int tvID, int seasonNumber, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).buildUrl(parameters);
        return null;
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
    public String getSeasonChanges(int tvID, String startDate, String endDate) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.START_DATE, startDate);
        parameters.add(Param.END_DATE, endDate);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.CHANGES).buildUrl(parameters);
        return null;
    }

    /**
     * This method lets users get the status of whether or not the TV episodes
     * of a season have been rated.
     *
     * A valid session id is required.
     *
     * @param tvID
     * @param sessionID
     * @return
     * @throws MovieDbException
     */
    public String getSeasonAccountState(int tvID, String sessionID) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SESSION_ID, sessionID);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.ACCOUNT_STATES).buildUrl(parameters);
        return null;
    }

    /**
     * Get the cast & crew credits for a TV season by season number.
     *
     * @param tvID
     * @param seasonNumber
     * @return
     * @throws MovieDbException
     */
    public String getSeasonCredits(int tvID, int seasonNumber) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.CREDITS).buildUrl(parameters);
        return null;
    }

    /**
     * Get the external ids that we have stored for a TV season by season
     * number.
     *
     * @param tvID
     * @param seasonNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public String getSeasonExternalID(int tvID, int seasonNumber, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.EXTERNAL_IDS).buildUrl(parameters);
        return null;
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
    public String getSeasonImages(int tvID, int seasonNumber, String language, String... includeImageLanguage) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, includeImageLanguage);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.IMAGES).buildUrl(parameters);
        return null;
    }

    /**
     * Get the videos that have been added to a TV season (trailers, teasers,
     * etc...)
     *
     * @param tvID
     * @param seasonNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public String getSeasonVideos(int tvID, int seasonNumber, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, tvID);
        parameters.add(Param.SEASON_NUMBER, seasonNumber);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.SEASON).subMethod(MethodSub.VIDEOS).buildUrl(parameters);
        return null;
    }
}
