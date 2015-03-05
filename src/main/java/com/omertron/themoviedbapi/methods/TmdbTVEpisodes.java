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
import com.omertron.themoviedbapi.tools.HttpTools;

/**
 * Class to hold the TV Methods
 *
 * @author stuart.boston
 */
public class TmdbTVEpisodes extends AbstractMethod {

    private static final int RATING_MAX = 10;

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbTVEpisodes(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Get the primary information about a TV episode by combination of a season
     * and episode number.
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public String getEpisodeInfo(int tvID, int seasonNumber, int episodeNumber, String language, String... appendToResponse) throws MovieDbException {
        return null;
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
    public String getEpisodeChanges(int episodeID, String startDate, String endDate) throws MovieDbException {
        return null;
    }

    /**
     * This method lets users get the status of whether or not the TV episode
     * has been rated.
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
    public String getEpisodeAccountState(int tvID, int seasonNumber, int episodeNumber, String sessionID) throws MovieDbException {
        return null;
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
    public String getEpisodeCredits(int tvID, int seasonNumber, int episodeNumber) throws MovieDbException {
        return null;
    }

    /**
     * Get the external ids for a TV episode by comabination of a season and
     * episode number.
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public String getEpisodeExternalID(int tvID, int seasonNumber, int episodeNumber, String language) throws MovieDbException {
        return null;
    }

    /**
     * Get the images (episode stills) for a TV episode by combination of a
     * season and episode number.
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @return
     * @throws MovieDbException
     */
    public String getEpisodeImages(int tvID, int seasonNumber, int episodeNumber) throws MovieDbException {
        return null;
    }

    /**
     * This method lets users rate a TV episode. A valid session id or guest
     * session id is required.
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
    public String postEpisodeRating(int tvID, int seasonNumber, int episodeNumber, int rating, String sessionID, String guestSessionID) throws MovieDbException {
        return null;
    }

    /**
     * Get the videos that have been added to a TV episode (teasers, clips,
     * etc...)
     *
     * @param tvID
     * @param seasonNumber
     * @param episodeNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public String getEpisodeVideos(int tvID, int seasonNumber, int episodeNumber, String language) throws MovieDbException {
        return null;
    }

}
