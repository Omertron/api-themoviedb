/*
 *      Copyright (c) 2004-2013 Stuart Boston
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
import com.omertron.themoviedbapi.MovieDbException.MovieDbExceptionType;
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.ExternalIds;
import com.omertron.themoviedbapi.model.person.PersonCredits;
import com.omertron.themoviedbapi.model.tv.TVEpisode;
import com.omertron.themoviedbapi.model.tv.TVSeason;
import com.omertron.themoviedbapi.model.tv.TVSeries;
import com.omertron.themoviedbapi.model.type.VideoType;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.omertron.themoviedbapi.tools.ApiUrl.*;
import com.omertron.themoviedbapi.wrapper.WrapperImages;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the TV methods
 *
 * @author stuart.boston
 */
public class TmdbTV extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbTV.class);
    // API URL Parameters
    private static final String BASE_TV = "tv/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbTV(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    //<editor-fold defaultstate="collapsed" desc="TmdbTV methods">
    /**
     * Get the primary information about a TmdbTV series by id.
     *
     * @param id
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TVSeries getTv(int id, String language, String[] appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV);

        apiUrl.addArgument(PARAM_ID, id);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();

        String webpage = requestWebPage(url);
        try {
            TVSeries series = mapper.readValue(webpage, TVSeries.class);
            return series;
        } catch (IOException ex) {
            LOG.warn("Failed to find series: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }

    /**
     * Get the cast & crew information about a TmdbTV series. <br/>
     * Just like the website, this information is pulled from the LAST season of the series.
     *
     * @param id
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public PersonCredits getTvCredits(int id, String language, String[] appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV, "/credits");

        apiUrl.addArgument(PARAM_ID, id);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
//            WrapperCasts wrapper = mapper.readValue(webpage, WrapperCasts.class);
//            TmdbResultsList<PersonMovie> results = new TmdbResultsList<PersonMovie>(wrapper.getAll());
//            results.copyWrapper(wrapper);

            PersonCredits pc = mapper.readValue(webpage, PersonCredits.class);
            pc.setVideoType(VideoType.TV);
            return pc;

//            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get TV Credis: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the external ids that we have stored for a TmdbTV series.
     *
     * @param id
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ExternalIds getTvExternalIds(int id, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV, "/external_ids");

        apiUrl.addArgument(PARAM_ID, id);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        if (StringUtils.isBlank(webpage)) {
            return new ExternalIds();
        }

        try {
            ExternalIds results = mapper.readValue(webpage, ExternalIds.class);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get External IDs: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the images (posters and backdrops) for a TmdbTV series.
     *
     * @param id
     * @param language
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getTvImages(int id, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV, "/images");

        apiUrl.addArgument(PARAM_ID, id);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll());
            results.copyWrapper(wrapper);
            results.setTotalResults(results.getResults().size());
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get External IDs: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TmdbTV Season methods">
    /**
     * Get the primary information about a TmdbTV season by its season number.
     *
     * @param id
     * @param seasonNumber
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TVSeason getTvSeason(int id, int seasonNumber, String language, String[] appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV);

        apiUrl.addArgument(PARAM_ID, id);
        apiUrl.addArgument(PARAM_SEASON_NUMBER, seasonNumber);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            TVSeason result = mapper.readValue(webpage, TVSeason.class);
            return result;
        } catch (IOException ex) {
            LOG.warn("Failed to get TV Season: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the external ids that we have stored for a TmdbTV season by season number.
     *
     * @param id
     * @param seasonNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ExternalIds getTvSeasonExternalIds(int id, int seasonNumber, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV, "/external_ids");

        apiUrl.addArgument(PARAM_ID, id);
        apiUrl.addArgument(PARAM_SEASON_NUMBER, seasonNumber);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        if (StringUtils.isBlank(webpage)) {
            return new ExternalIds();
        }

        try {
            ExternalIds results = mapper.readValue(webpage, ExternalIds.class);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get External IDs: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the images (posters) that we have stored for a TmdbTV season by season number.
     *
     * @param id
     * @param seasonNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getTvSeasonImages(int id, int seasonNumber, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV, "/images");

        apiUrl.addArgument(PARAM_ID, id);
        apiUrl.addArgument(PARAM_SEASON_NUMBER, seasonNumber);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll());
            results.copyWrapper(wrapper);
            results.setTotalResults(results.getResults().size());
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get External IDs: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TmdbTV Episode methods">
    /**
     * Get the primary information about a TmdbTV episode by combination of a season and episode number.
     *
     * @param id
     * @param seasonNumber
     * @param episodeNumber
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TVEpisode getTvEpisode(int id, int seasonNumber, int episodeNumber, String language, String[] appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV);

        apiUrl.addArgument(PARAM_ID, id);
        apiUrl.addArgument(PARAM_SEASON_NUMBER, seasonNumber);
        apiUrl.addArgument(PARAM_EPISODE_NUMBER, episodeNumber);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            TVEpisode result = mapper.readValue(webpage, TVEpisode.class);
            return result;
        } catch (IOException ex) {
            LOG.warn("Failed to get External IDs: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the TmdbTV episode credits by combination of season and episode number.
     *
     * @param id
     * @param seasonNumber
     * @param episodeNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public PersonCredits getTvEpisodeCredits(int id, int seasonNumber, int episodeNumber, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV, "/credits");

        apiUrl.addArgument(PARAM_ID, id);
        apiUrl.addArgument(PARAM_SEASON_NUMBER, seasonNumber);
        apiUrl.addArgument(PARAM_EPISODE_NUMBER, episodeNumber);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
//            WrapperCasts wrapper = mapper.readValue(webpage, WrapperCasts.class);
//            TmdbResultsList<PersonMovie> results = new TmdbResultsList<PersonMovie>(wrapper.getAll());
//            results.copyWrapper(wrapper);
//            return results;
            PersonCredits pc = mapper.readValue(webpage, PersonCredits.class);
            pc.setVideoType(VideoType.TV);
            return pc;
        } catch (IOException ex) {
            LOG.warn("Failed to get TV Episode Credis: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the external ids for a TmdbTV episode by combination of a season and episode number.
     *
     * @param id
     * @param seasonNumber
     * @param episodeNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ExternalIds getTvEpisodeExternalIds(int id, int seasonNumber, int episodeNumber, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_TV, "/external_ids");

        apiUrl.addArgument(PARAM_ID, id);
        apiUrl.addArgument(PARAM_SEASON_NUMBER, seasonNumber);
        apiUrl.addArgument(PARAM_EPISODE_NUMBER, episodeNumber);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        LOG.info(webpage);

        try {
            ExternalIds results = mapper.readValue(webpage, ExternalIds.class);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get External IDs: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the images (episode stills) for a TmdbTV episode by combination of a season and episode number.
     *
     * @param id
     * @param seasonNumber
     * @param episodeNumber
     * @param language
     * @return
     * @throws MovieDbException
     */
    public String getTvEpisodeImages(int id, int seasonNumber, int episodeNumber, String language) throws MovieDbException {
        return null;
    }
    //</editor-fold>

}
