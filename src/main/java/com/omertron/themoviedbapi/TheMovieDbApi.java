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
package com.omertron.themoviedbapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.methods.TmdbAccount;
import com.omertron.themoviedbapi.methods.TmdbAuthentication;
import com.omertron.themoviedbapi.methods.TmdbCertifications;
import com.omertron.themoviedbapi.methods.TmdbChanges;
import com.omertron.themoviedbapi.methods.TmdbCollections;
import com.omertron.themoviedbapi.methods.TmdbCompanies;
import com.omertron.themoviedbapi.methods.TmdbConfiguration;
import com.omertron.themoviedbapi.methods.TmdbCredits;
import com.omertron.themoviedbapi.methods.TmdbDiscover;
import com.omertron.themoviedbapi.methods.TmdbFind;
import com.omertron.themoviedbapi.methods.TmdbGenres;
import com.omertron.themoviedbapi.methods.TmdbJobs;
import com.omertron.themoviedbapi.methods.TmdbKeywords;
import com.omertron.themoviedbapi.methods.TmdbLists;
import com.omertron.themoviedbapi.methods.TmdbMovies;
import com.omertron.themoviedbapi.methods.TmdbNetworks;
import com.omertron.themoviedbapi.methods.TmdbPeople;
import com.omertron.themoviedbapi.methods.TmdbReviews;
import com.omertron.themoviedbapi.methods.TmdbSearch;
import com.omertron.themoviedbapi.methods.TmdbTV;
import com.omertron.themoviedbapi.model.*;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.results.TmdbResultsMap;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.exception.ApiExceptionType;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

/**
 * The MovieDb API
 * <p>
 * This is for version 3 of the API as specified here: http://help.themoviedb.org/kb/api/about-3
 *
 * @author stuart.boston
 */
public class TheMovieDbApi {

    private static final Logger LOG = LoggerFactory.getLogger(TheMovieDbApi.class);
    private String apiKey;
    private Configuration tmdbConfig;
    private HttpTools httpTools;
    // Jackson JSON configuration
    private static final ObjectMapper MAPPER = new ObjectMapper();
    // Constants
    private static final String MOVIE_ID = "movie_id";
    private static final int YEAR_LENGTH = 4;
    private static final int RATING_MAX = 10;
    private static final int POST_SUCCESS_STATUS_CODE = 12;
    // Sub-methods
    private static TmdbAccount tmdbAccount;
    private static TmdbAuthentication tmdbAuth;
    private static TmdbCertifications tmdbCertifications;
    private static TmdbChanges tmdbChanges;
    private static TmdbCollections tmdbCollections;
    private static TmdbCompanies tmdbCompany;
    private static TmdbConfiguration tmdbConfiguration;
    private static TmdbCredits tmdbCredits;
    private static TmdbDiscover tmdbDiscover;
    private static TmdbFind tmdbFind;
    private static TmdbGenres tmdbGenre;
    private static TmdbJobs tmdbJobs;
    private static TmdbKeywords tmdbKeyword;
    private static TmdbLists tmdbList;
    private static TmdbMovies tmdbMovies;
    private static TmdbNetworks tmdbNetworks;
    private static TmdbPeople tmdbPeople;
    private static TmdbReviews tmdbReviews;
    private static TmdbSearch tmdbSearch;
    private static TmdbTV tmdbTv;

    /**
     * API for The Movie Db.
     *
     * @param apiKey
     * @throws MovieDbException
     */
    public TheMovieDbApi(String apiKey) throws MovieDbException {
        this(apiKey, new SimpleHttpClientBuilder().build());
    }

    /**
     * API for The Movie Db.
     *
     * @param apiKey
     * @param httpClient The httpClient to use for web requests.
     * @throws MovieDbException
     */
    public TheMovieDbApi(String apiKey, CloseableHttpClient httpClient) throws MovieDbException {
        this.apiKey = apiKey;
        this.httpTools = new HttpTools(httpClient);

        URL configUrl = new ApiUrl(apiKey, MethodBase.CONFIGURATION).buildUrl();
        String webpage = httpTools.getRequest(configUrl);

        try {
            WrapperConfig wc = MAPPER.readValue(webpage, WrapperConfig.class);
            tmdbConfig = wc.getTmdbConfiguration();
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to read configuration", configUrl, ex);
        }
    }

    /**
     * Initialise the sub-classes once the API key and http client are known
     *
     * @param apiKey
     * @param httpTools
     */
    private void initialise(String apiKey, HttpTools httpTools) {
        tmdbAccount = new TmdbAccount(apiKey, httpTools);
        tmdbAuth = new TmdbAuthentication(apiKey, httpTools);
        tmdbCertifications = new TmdbCertifications(apiKey, httpTools);
        tmdbChanges = new TmdbChanges(apiKey, httpTools);
        tmdbCollections = new TmdbCollections(apiKey, httpTools);
        tmdbCompany = new TmdbCompanies(apiKey, httpTools);
        tmdbConfiguration = new TmdbConfiguration(apiKey, httpTools);
        tmdbCredits = new TmdbCredits(apiKey, httpTools);
        tmdbDiscover = new TmdbDiscover(apiKey, httpTools);
        tmdbFind = new TmdbFind(apiKey, httpTools);
        tmdbGenre = new TmdbGenres(apiKey, httpTools);
        tmdbJobs = new TmdbJobs(apiKey, httpTools);
        tmdbKeyword = new TmdbKeywords(apiKey, httpTools);
        tmdbList = new TmdbLists(apiKey, httpTools);
        tmdbMovies = new TmdbMovies(apiKey, httpTools);
        tmdbNetworks = new TmdbNetworks(apiKey, httpTools);
        tmdbPeople = new TmdbPeople(apiKey, httpTools);
        tmdbReviews = new TmdbReviews(apiKey, httpTools);
        tmdbSearch = new TmdbSearch(apiKey, httpTools);
        tmdbTv = new TmdbTV(apiKey, httpTools);
    }

    /**
     * Compare the MovieDB object with a title & year
     *
     * @param moviedb The moviedb object to compare too
     * @param title The title of the movie to compare
     * @param year The year of the movie to compare exact match
     * @return True if there is a match, False otherwise.
     */
    public static boolean compareMovies(MovieDb moviedb, String title, String year) {
        return compareMovies(moviedb, title, year, 0);
    }

    /**
     * Compare the MovieDB object with a title & year
     *
     * @param moviedb The moviedb object to compare too
     * @param title The title of the movie to compare
     * @param year The year of the movie to compare
     * @param maxDistance The Levenshtein Distance between the two titles. 0 = exact match
     * @param caseSensitive true if the comparison is to be case sensitive
     * @return True if there is a match, False otherwise.
     */
    public static boolean compareMovies(MovieDb moviedb, String title, String year, int maxDistance, boolean caseSensitive) {
        if ((moviedb == null) || (StringUtils.isBlank(title))) {
            return Boolean.FALSE;
        }

        String cmpTitle, cmpOtherTitle, cmpOriginalTitle;
        if (caseSensitive) {
            cmpTitle = title;
            cmpOtherTitle = moviedb.getOriginalTitle();
            cmpOriginalTitle = moviedb.getTitle();
        } else {
            cmpTitle = title.toLowerCase();
            cmpOtherTitle = moviedb.getTitle().toLowerCase();
            cmpOriginalTitle = moviedb.getOriginalTitle().toLowerCase();
        }

        if (isValidYear(year) && isValidYear(moviedb.getReleaseDate())) {
            // Compare with year
            String movieYear = moviedb.getReleaseDate().substring(0, YEAR_LENGTH);
            if (movieYear.equals(year)) {
                if (compareDistance(cmpOriginalTitle, cmpTitle, maxDistance)) {
                    return Boolean.TRUE;
                }

                if (compareDistance(cmpOtherTitle, cmpTitle, maxDistance)) {
                    return Boolean.TRUE;
                }
            }
        }

        // Compare without year
        if (compareDistance(cmpOriginalTitle, cmpTitle, maxDistance)) {
            return Boolean.TRUE;
        }

        if (compareDistance(cmpOtherTitle, cmpTitle, maxDistance)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Compare the MovieDB object with a title & year, case sensitive
     *
     * @param moviedb
     * @param title
     * @param year
     * @param maxDistance
     * @return
     */
    public static boolean compareMovies(MovieDb moviedb, String title, String year, int maxDistance) {
        return compareMovies(moviedb, title, year, maxDistance, true);
    }

    /**
     * Compare the Levenshtein Distance between the two strings
     *
     * @param title1
     * @param title2
     * @param distance
     */
    private static boolean compareDistance(String title1, String title2, int distance) {
        return StringUtils.getLevenshteinDistance(title1, title2) <= distance;
    }

    /**
     * Check the year is not blank or UNKNOWN
     *
     * @param year
     */
    private static boolean isValidYear(String year) {
        return StringUtils.isNotBlank(year) && !"UNKNOWN".equals(year);
    }

    /**
     * Get the configuration information
     *
     * @return
     */
    public Configuration getConfiguration() {
        return tmdbConfig;
    }

    /**
     * Generate the full image URL from the size and image path
     *
     * @param imagePath
     * @param requiredSize
     * @return
     * @throws MovieDbException
     */
    public URL createImageUrl(String imagePath, String requiredSize) throws MovieDbException {
        if (!tmdbConfig.isValidSize(requiredSize)) {
            throw new MovieDbException(ApiExceptionType.INVALID_IMAGE, requiredSize);
        }

        StringBuilder sb = new StringBuilder(tmdbConfig.getBaseUrl());
        sb.append(requiredSize);
        sb.append(imagePath);
        try {
            return new URL(sb.toString());
        } catch (MalformedURLException ex) {
            LOG.warn("Failed to create image URL: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.INVALID_URL, sb.toString(), "", ex);
        }
    }

    /**
     * This method is used to retrieve all of the basic movie information.
     *
     * It will return the single highest rated poster and backdrop.
     *
     * ApiExceptionType.MOVIE_ID_NOT_FOUND will be thrown if there are no movies found.
     *
     * @param movieId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public MovieDb getMovieInfo(int movieId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            MovieDb movie = MAPPER.readValue(webpage, MovieDb.class);
            if (movie == null || movie.getId() == 0) {
                LOG.warn("No movie found for ID '{}'", movieId);
                throw new MovieDbException(ApiExceptionType.ID_NOT_FOUND, "No movie found for ID: " + movieId, url);
            }
            return movie;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie info: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve all of the basic movie information.
     *
     * It will return the single highest rated poster and backdrop.
     *
     * ApiExceptionType.MOVIE_ID_NOT_FOUND will be thrown if there are no movies found.
     *
     * @param imdbId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public MovieDb getMovieInfoImdb(String imdbId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, imdbId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            MovieDb movie = MAPPER.readValue(webpage, MovieDb.class);
            if (movie == null || movie.getId() == 0) {
                LOG.warn("No movie found for IMDB ID: '{}'", imdbId);
                throw new MovieDbException(ApiExceptionType.ID_NOT_FOUND, "No movie found for IMDB ID: " + imdbId, url);
            }
            return movie;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie info: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get movie info", url, ex);
        }
    }

    /**
     * This method is used to retrieve all of the alternative titles we have for a particular movie.
     *
     * @param movieId
     * @param country
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<AlternativeTitle> getMovieAlternativeTitles(int movieId, String country, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.COUNTRY, country);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.ALT_TITLES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            WrapperAlternativeTitles wrapper = MAPPER.readValue(webpage, WrapperAlternativeTitles.class);
            TmdbResultsList<AlternativeTitle> results = new TmdbResultsList<AlternativeTitle>(wrapper.getTitles());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie alternative titles: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Get the cast information for a specific movie id.
     *
     * TODO: Add a function to enrich the data with the people methods
     *
     * @param movieId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Person> getMovieCasts(int movieId, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.CASTS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovieCasts wrapper = MAPPER.readValue(webpage, WrapperMovieCasts.class);
            TmdbResultsList<Person> results = new TmdbResultsList<Person>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie casts: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method should be used when you’re wanting to retrieve all of the images for a particular movie.
     *
     * @param movieId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getMovieImages(int movieId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.IMAGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperImages wrapper = MAPPER.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie images: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve all of the keywords that have been added to a particular movie.
     *
     * Currently, only English keywords exist.
     *
     * @param movieId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Keyword> getMovieKeywords(int movieId, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.KEYWORDS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovieKeywords wrapper = MAPPER.readValue(webpage, WrapperMovieKeywords.class);
            TmdbResultsList<Keyword> results = new TmdbResultsList<Keyword>(wrapper.getKeywords());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie keywords: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve all of the release and certification data we have for a specific movie.
     *
     * @param movieId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<ReleaseInfo> getMovieReleaseInfo(int movieId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.RELEASES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperReleaseInfo wrapper = MAPPER.readValue(webpage, WrapperReleaseInfo.class);
            TmdbResultsList<ReleaseInfo> results = new TmdbResultsList<ReleaseInfo>(wrapper.getCountries());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie release information: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve all of the trailers for a particular movie.
     *
     * Supported sites are YouTube and QuickTime.
     *
     * @param movieId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Video> getMovieTrailers(int movieId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.VIDEOS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperVideos wrapper = MAPPER.readValue(webpage, WrapperVideos.class);
            TmdbResultsList<Video> results = new TmdbResultsList<Video>(wrapper.getVideos());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie trailers: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve a list of the available translations for a specific movie.
     *
     * @param movieId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Translation> getMovieTranslations(int movieId, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.TRANSLATIONS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperTranslations wrapper = MAPPER.readValue(webpage, WrapperTranslations.class);
            TmdbResultsList<Translation> results = new TmdbResultsList<Translation>(wrapper.getTranslations());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie tranlations: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * The similar movies method will let you retrieve the similar movies for a particular movie.
     *
     * This data is created dynamically but with the help of users votes on TMDb.
     *
     * The data is much better with movies that have more keywords
     *
     * @param movieId
     * @param language
     * @param page
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getSimilarMovies(int movieId, String language, int page, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.SIMILAR_MOVIES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get similar movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    public TmdbResultsList<Reviews> getReviews(int movieId, String language, int page, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.REVIEWS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperReviews wrapper = MAPPER.readValue(webpage, WrapperReviews.class);
            TmdbResultsList<Reviews> results = new TmdbResultsList<Reviews>(wrapper.getReviews());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get reviews: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Get the lists that the movie belongs to
     *
     * @param movieId
     * @param language
     * @param page
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieList> getMovieLists(int movieId, String language, int page, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.LISTS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovieList wrapper = MAPPER.readValue(webpage, WrapperMovieList.class);
            TmdbResultsList<MovieList> results = new TmdbResultsList<MovieList>(wrapper.getMovieList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie lists: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve the newest movie that was added to TMDb.
     *
     * @return
     * @throws MovieDbException
     */
    public MovieDb getLatestMovie() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.LATEST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, MovieDb.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get latest movie: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Get the list of upcoming movies.
     *
     * This list refreshes every day.
     *
     * The maximum number of items this list will include is 100.
     *
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getUpcoming(String language, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.UPCOMING).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get upcoming movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }

    }

    /**
     * This method is used to retrieve the movies currently in theatres.
     *
     * This is a curated list that will normally contain 100 movies. The default response will return 20 movies.
     *
     * TODO: Implement more than 20 movies
     *
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getNowPlayingMovies(String language, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.NOW_PLAYING).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get now playing movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve the daily movie popularity list.
     *
     * This list is updated daily. The default response will return 20 movies.
     *
     * TODO: Implement more than 20 movies
     *
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getPopularMovieList(String language, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.POPULAR).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get popular movie list: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve the top rated movies that have over 10 votes on TMDb.
     *
     * The default response will return 20 movies.
     *
     * TODO: Implement more than 20 movies
     *
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getTopRatedMovies(String language, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(MethodSub.TOP_RATED).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get top rated movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method lets users rate a movie.
     *
     * A valid session id is required.
     *
     * @param sessionId
     * @param movieId
     * @param rating
     * @return
     * @throws MovieDbException
     */
    public boolean postMovieRating(String sessionId, Integer movieId, Integer rating) throws MovieDbException {
        if (rating < 0 || rating > RATING_MAX) {
            throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Rating out of range");
        }

        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).setSubMethod(movieId, MethodSub.RATING).buildUrl(parameters);

        String jsonBody = convertToJson(Collections.singletonMap("value", rating));
        String webpage = httpTools.postRequest(url, jsonBody);

        try {
            StatusCode status = MAPPER.readValue(webpage, StatusCode.class);
            LOG.info("Status: {}", status);
            int code = status.getStatusCode();
            return code == POST_SUCCESS_STATUS_CODE;
        } catch (IOException ex) {
            LOG.warn("Failed to post movie rating: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve all of the basic information about a movie collection.
     *
     * You can get the ID needed for this method by making a getMovieInfo request for the belongs_to_collection.
     *
     * @param collectionId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public CollectionInfo getCollectionInfo(int collectionId, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, collectionId);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.COLLECTION).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, CollectionInfo.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get collection information: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Get all of the images for a particular collection by collection id.
     *
     * @param collectionId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getCollectionImages(int collectionId, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, collectionId);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.COLLECTION).setSubMethod(MethodSub.IMAGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperImages wrapper = MAPPER.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll(ArtworkType.POSTER, ArtworkType.BACKDROP));
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get collection images: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
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
    public Person getPersonInfo(int personId, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, Person.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get person info: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve all of the cast & crew information for the person.
     *
     * It will return the single highest rated poster for each movie record.
     *
     * @param personId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<PersonCredit> getPersonCredits(int personId, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, personId);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).setSubMethod(MethodSub.CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperPersonCredits wrapper = MAPPER.readValue(webpage, WrapperPersonCredits.class);
            TmdbResultsList<PersonCredit> results = new TmdbResultsList<PersonCredit>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get person credits: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
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
            LOG.warn("Failed to get person images: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Get the list of popular people on The Movie Database.
     *
     * This list refreshes every day.
     *
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Person> getPersonPopular() throws MovieDbException {
        return getPersonPopular(0);
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
    public TmdbResultsList<Person> getPersonPopular(int page) throws MovieDbException {
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
            LOG.warn("Failed to get popular person: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
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
            LOG.warn("Failed to get latest person: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve the basic information about a production company on TMDb.
     *
     * @param companyId
     * @return
     * @throws MovieDbException
     */
    public Company getCompanyInfo(int companyId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, companyId);

        URL url = new ApiUrl(apiKey, MethodBase.COMPANY).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, Company.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get company information: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method is used to retrieve the movies associated with a company.
     *
     * These movies are returned in order of most recently released to oldest. The default response will return 20 movies per page.
     *
     * TODO: Implement more than 20 movies
     *
     * @param companyId
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getCompanyMovies(int companyId, String language, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, companyId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.COMPANY).setSubMethod(MethodSub.MOVIES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperCompanyMovies wrapper = MAPPER.readValue(webpage, WrapperCompanyMovies.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get company movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * You can use this method to retrieve the list of genres used on TMDb.
     *
     * These IDs will correspond to those found in movie calls.
     *
     * @param language
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Genre> getGenreList(String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.GENRE).setSubMethod(MethodSub.LIST).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperGenres wrapper = MAPPER.readValue(webpage, WrapperGenres.class);
            TmdbResultsList<Genre> results = new TmdbResultsList<Genre>(wrapper.getGenres());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get genre list: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Get a list of movies per genre.
     *
     * It is important to understand that only movies with more than 10 votes get listed.
     *
     * This prevents movies from 1 10/10 rating from being listed first and for the first 5 pages.
     *
     * @param genreId
     * @param language
     * @param page
     * @param includeAllMovies
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getGenreMovies(int genreId, String language, int page, boolean includeAllMovies) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, genreId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.INCLUDE_ALL_MOVIES, includeAllMovies);

        URL url = new ApiUrl(apiKey, MethodBase.GENRE).setSubMethod(MethodSub.MOVIES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get genre movie list: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Search Movies This is a good starting point to start finding movies on TMDb.
     *
     * @param movieName
     * @param searchYear Limit the search to the provided year. Zero (0) will get all years
     * @param language The language to include. Can be blank/null.
     * @param includeAdult true or false to include adult titles in the search
     * @param page The page of results to return. 0 to get the default (first page)
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> searchMovie(String movieName, int searchYear, String language, boolean includeAdult, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, movieName);
        parameters.add(Param.YEAR, searchYear);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.ADULT, includeAdult);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.MOVIE).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find movie: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }

    }

    /**
     * Search for collections by name.
     *
     * @param query
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Collection> searchCollection(String query, String language, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.COLLECTION).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperCollection wrapper = MAPPER.readValue(webpage, WrapperCollection.class);
            TmdbResultsList<Collection> results = new TmdbResultsList<Collection>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find collection: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This is a good starting point to start finding people on TMDb.
     *
     * The idea is to be a quick and light method so you can iterate through people quickly.
     *
     * @param personName
     * @param includeAdult
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Person> searchPeople(String personName, boolean includeAdult, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, personName);
        parameters.add(Param.ADULT, includeAdult);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.PERSON).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperPerson wrapper = MAPPER.readValue(webpage, WrapperPerson.class);
            TmdbResultsList<Person> results = new TmdbResultsList<Person>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find person: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Search for lists by name and description.
     *
     * @param query
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieList> searchList(String query, String language, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.LIST).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovieList wrapper = MAPPER.readValue(webpage, WrapperMovieList.class);
            TmdbResultsList<MovieList> results = new TmdbResultsList<MovieList>(wrapper.getMovieList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find list: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Search Companies.
     *
     * You can use this method to search for production companies that are part of TMDb. The company IDs will map to those returned
     * on movie calls.
     *
     * http://help.themoviedb.org/kb/api/search-companies
     *
     * @param companyName
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Company> searchCompanies(String companyName, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, companyName);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.COMPANY).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperCompany wrapper = MAPPER.readValue(webpage, WrapperCompany.class);
            TmdbResultsList<Company> results = new TmdbResultsList<Company>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find company: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Search for keywords by name
     *
     * @param query
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Keyword> searchKeyword(String query, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.KEYWORD).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperKeywords wrapper = MAPPER.readValue(webpage, WrapperKeywords.class);
            TmdbResultsList<Keyword> results = new TmdbResultsList<Keyword>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find keyword: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Get a list by its ID
     *
     * @param listId
     * @return The list and its items
     * @throws MovieDbException
     */
    public MovieDbList getList(String listId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, listId);

        URL url = new ApiUrl(apiKey, MethodBase.LIST).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, MovieDbList.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get list: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method lets users create a new list. A valid session id is required.
     *
     * @param sessionId
     * @param name
     * @param description
     * @return The list id
     * @throws MovieDbException
     */
    public String createList(String sessionId, String name, String description) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);

        Map<String, String> body = new HashMap<String, String>();
        body.put("name", StringUtils.trimToEmpty(name));
        body.put("description", StringUtils.trimToEmpty(description));
        String jsonBody = convertToJson(body);

        URL url = new ApiUrl(apiKey, MethodBase.LIST).buildUrl(parameters);
        String webpage = httpTools.postRequest(url, jsonBody);

        try {
            return MAPPER.readValue(webpage, MovieDbListStatus.class).getListId();
        } catch (IOException ex) {
            LOG.warn("Failed to create list: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Check to see if a movie ID is already added to a list.
     *
     * @param listId
     * @param movieId
     * @return true if the movie is on the list
     * @throws MovieDbException
     */
    public boolean isMovieOnList(String listId, Integer movieId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);

        URL url = new ApiUrl(apiKey, MethodBase.LIST).setSubMethod(listId, MethodSub.ITEM_STATUS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, ListItemStatus.class).isItemPresent();
        } catch (IOException ex) {
            LOG.warn("Failed to get item status: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method lets users add new movies to a list that they created. A valid session id is required.
     *
     * @param sessionId
     * @param listId
     * @param movieId
     * @return true if the movie is on the list
     * @throws MovieDbException
     */
    public StatusCode addMovieToList(String sessionId, String listId, Integer movieId) throws MovieDbException {
        return modifyMovieList(sessionId, listId, movieId, MethodSub.ADD_ITEM);
    }

    /**
     * This method lets users remove movies from a list that they created. A valid session id is required.
     *
     * @param sessionId
     * @param listId
     * @param movieId
     * @return true if the movie is on the list
     * @throws MovieDbException
     */
    public StatusCode removeMovieFromList(String sessionId, String listId, Integer movieId) throws MovieDbException {
        return modifyMovieList(sessionId, listId, movieId, MethodSub.REMOVE_ITEM);
    }

    private StatusCode modifyMovieList(String sessionId, String listId, Integer movieId, MethodSub operation) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.SESSION, sessionId);

        String jsonBody = convertToJson(Collections.singletonMap("media_id", String.valueOf(movieId)));

        URL url = new ApiUrl(apiKey, MethodBase.LIST).setSubMethod(listId, operation).buildUrl(parameters);
        String webpage = httpTools.postRequest(url, jsonBody);

        try {
            return MAPPER.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            LOG.warn("Failed to remove movie from list: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * This method lets users delete a list that they created. A valid session id is required.
     *
     * @param sessionId
     * @param listId
     * @return
     * @throws MovieDbException
     */
    public StatusCode deleteMovieList(String sessionId, String listId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, listId);
        parameters.add(Param.SESSION, sessionId);

        URL url = new ApiUrl(apiKey, MethodBase.LIST).buildUrl(parameters);
        String webpage = httpTools.deleteRequest(url);

        try {
            return MAPPER.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            LOG.warn("Failed to delete movie list: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Get the basic information for a specific keyword id.
     *
     * @param keywordId
     * @return
     * @throws MovieDbException
     */
    public Keyword getKeyword(String keywordId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, keywordId);

        URL url = new ApiUrl(apiKey, MethodBase.KEYWORD).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, Keyword.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get keyword: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }

    }

    /**
     * Get the list of movies for a particular keyword by id.
     *
     * @param keywordId
     * @param language
     * @param page
     * @return List of movies with the keyword
     * @throws MovieDbException
     */
    public TmdbResultsList<KeywordMovie> getKeywordMovies(String keywordId, String language, int page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, keywordId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.KEYWORD).setSubMethod(MethodSub.MOVIES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperKeywordMovies wrapper = MAPPER.readValue(webpage, WrapperKeywordMovies.class);
            TmdbResultsList<KeywordMovie> results = new TmdbResultsList<KeywordMovie>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get keyword movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }

    }

    public void getPersonChangesList(int page, String startDate, String endDate) throws MovieDbException {
        LOG.trace("getPersonChangesList: page: {}, start: {}, end: {}", page, startDate, endDate);
        throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Not implemented yet", "");
    }

    public TmdbResultsList<JobDepartment> getJobs() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.JOB).setSubMethod(MethodSub.LIST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            WrapperJobList wrapper = MAPPER.readValue(webpage, WrapperJobList.class);
            TmdbResultsList<JobDepartment> results = new TmdbResultsList<JobDepartment>(wrapper.getJobs());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get job list: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Discover movies by different types of data like average rating, number of votes, genres and certifications.
     *
     * You can alternatively create a "discover" object and pass it to this method to cut out the requirement for all of these
     * parameters
     *
     * @param page Minimum value is 1
     * @param language ISO 639-1 code.
     * @param sortBy Available options are vote_average.desc, vote_average.asc, release_date.desc, release_date.asc,
     * popularity.desc, popularity.asc
     * @param includeAdult Toggle the inclusion of adult titles
     * @param year Filter the results release dates to matches that include this value
     * @param primaryReleaseYear Filter the results so that only the primary release date year has this value
     * @param voteCountGte Only include movies that are equal to, or have a vote count higher than this value
     * @param voteAverageGte Only include movies that are equal to, or have a higher average rating than this value
     * @param withGenres Only include movies with the specified genres. Expected value is an integer (the id of a genre). Multiple
     * values can be specified. Comma separated indicates an 'AND' query, while a pipe (|) separated value indicates an 'OR'.
     * @param releaseDateGte The minimum release to include. Expected format is YYYY-MM-DD
     * @param releaseDateLte The maximum release to include. Expected format is YYYY-MM-DD
     * @param certificationCountry Only include movies with certifications for a specific country. When this value is specified,
     * 'certificationLte' is required. A ISO 3166-1 is expected.
     * @param certificationLte Only include movies with this certification and lower. Expected value is a valid certification for
     * the specified 'certificationCountry'.
     * @param withCompanies Filter movies to include a specific company. Expected value is an integer (the id of a company). They
     * can be comma separated to indicate an 'AND' query.
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getDiscover(int page, String language, String sortBy, boolean includeAdult, int year,
            int primaryReleaseYear, int voteCountGte, float voteAverageGte, String withGenres, String releaseDateGte,
            String releaseDateLte, String certificationCountry, String certificationLte, String withCompanies) throws MovieDbException {

        Discover discover = new Discover();
        discover.page(page)
                .language(language)
                .sortBy(sortBy)
                .includeAdult(includeAdult)
                .year(year)
                .primaryReleaseYear(primaryReleaseYear)
                .voteCountGte(voteCountGte)
                .voteAverageGte(voteAverageGte)
                .withGenres(withGenres)
                .releaseDateGte(releaseDateGte)
                .releaseDateLte(releaseDateLte)
                .certificationCountry(certificationCountry)
                .certificationLte(certificationLte)
                .withCompanies(withCompanies);

        return getDiscover(discover);
    }

    /**
     * Discover movies by different types of data like average rating, number of votes, genres and certifications.
     *
     * @param discover A discover object containing the search criteria required
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getDiscover(Discover discover) throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.DISCOVER).setSubMethod(MethodSub.MOVIE).buildUrl(discover.getParams());
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get discover list: {}", ex.getMessage(), ex);
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, webpage, url, ex);
        }
    }

    /**
     * Use Jackson to convert Map to JSON string.
     *
     * @param map
     * @return
     * @throws MovieDbException
     */
    public static String convertToJson(Map<String, ?> map) throws MovieDbException {
        try {
            return MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException jpe) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "JSON conversion failed", "", jpe);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Account">
    /**
     * Get the basic information for an account. You will need to have a valid session id.
     *
     * @param sessionId
     * @return
     * @throws MovieDbException
     */
    public Account getAccount(String sessionId) throws MovieDbException {
        return tmdbAccount.getAccount(sessionId);
    }

    /**
     * Get all lists of a given user
     *
     * @param sessionId
     * @param accountId
     * @return The lists
     * @throws MovieDbException
     */
    public List<MovieDbList> getUserLists(String sessionId, int accountId) throws MovieDbException {
        return tmdbAccount.getUserLists(sessionId, accountId);
    }

    /**
     * Get the account favourite movies
     *
     * @param sessionId
     * @param accountId
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> getFavoriteMovies(String sessionId, int accountId) throws MovieDbException {
        return tmdbAccount.getFavoriteMovies(sessionId, accountId);
    }

    /**
     * Add or remove a movie to an accounts favourite list.
     *
     * @param sessionId
     * @param accountId
     * @param mediaId
     * @param mediaType
     * @param isFavorite
     * @return
     * @throws MovieDbException
     */
    public StatusCode changeFavoriteStatus(String sessionId, int accountId, Integer mediaId, MediaType mediaType, boolean isFavorite) throws MovieDbException {
        return tmdbAccount.changeFavoriteStatus(sessionId, accountId, mediaId, mediaType, isFavorite);
    }

    /**
     * Get the list of rated movies (and associated rating) for an account.
     *
     * @param sessionId
     * @param accountId
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> getRatedMovies(String sessionId, int accountId) throws MovieDbException {
        return tmdbAccount.getRatedMovies(sessionId, accountId);
    }

    /**
     * Get the list of rated TV shows (and associated rating) for an account.
     *
     * @param sessionId
     * @param accountId
     * @return
     * @throws MovieDbException
     */
    public List getRatedTV(String sessionId, int accountId) throws MovieDbException {
        return tmdbAccount.getRatedTV(sessionId, accountId);
    }

    /**
     * Get the list of movies on an accounts watchlist.
     *
     * @param sessionId
     * @param accountId
     * @return The watchlist of the user
     * @throws MovieDbException
     */
    public List<MovieDb> getWatchListMovie(String sessionId, int accountId) throws MovieDbException {
        return tmdbAccount.getWatchListMovie(sessionId, accountId);
    }

    /**
     * Get the list of movies on an accounts watchlist.
     *
     * @param sessionId
     * @param accountId
     * @return The watchlist of the user
     * @throws MovieDbException
     */
    public List getWatchListTV(String sessionId, int accountId) throws MovieDbException {
        return tmdbAccount.getWatchListTV(sessionId, accountId);
    }

    /**
     * Add a movie to an accounts watch list.
     *
     * @param sessionId
     * @param accountId
     * @param mediaId
     * @param mediaType
     * @return
     * @throws MovieDbException
     */
    public StatusCode addToWatchList(String sessionId, int accountId, Integer mediaId, MediaType mediaType) throws MovieDbException {
        return tmdbAccount.modifyWatchList(sessionId, accountId, mediaId, mediaType, true);
    }

    /**
     * Remove a movie from an accounts watch list.
     *
     * @param sessionId
     * @param accountId
     * @param mediaId
     * @param mediaType
     * @return
     * @throws MovieDbException
     */
    public StatusCode removeFromWatchList(String sessionId, int accountId, Integer mediaId, MediaType mediaType) throws MovieDbException {
        return tmdbAccount.modifyWatchList(sessionId, accountId, mediaId, mediaType, false);
    }

    /**
     * Get the list of favorite TV series for an account.
     *
     * @param sessionId
     * @param accountId
     * @return
     * @throws MovieDbException
     */
    public List getFavoriteTv(String sessionId, int accountId) throws MovieDbException {
        return tmdbAccount.getFavoriteTv(sessionId, accountId);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Authentication">
    /**
     * This method is used to generate a valid request token for user based authentication.
     *
     * A request token is required in order to request a session id.
     *
     * You can generate any number of request tokens but they will expire after 60 minutes.
     *
     * As soon as a valid session id has been created the token will be destroyed.
     *
     * @return
     * @throws MovieDbException
     */
    public TokenAuthorisation getAuthorisationToken() throws MovieDbException {
        return tmdbAuth.getAuthorisationToken();
    }

    /**
     * This method is used to generate a session id for user based authentication.
     *
     * A session id is required in order to use any of the write methods.
     *
     * @param token
     * @return
     * @throws MovieDbException
     */
    public TokenSession getSessionToken(TokenAuthorisation token) throws MovieDbException {
        return tmdbAuth.getSessionToken(token);
    }

    /**
     * This method is used to generate a session id for user based authentication. User must provide their username and password
     *
     * A session id is required in order to use any of the write methods.
     *
     * @param token Session token
     * @param username User's username
     * @param password User's password
     * @return
     * @throws MovieDbException
     */
    public TokenAuthorisation getSessionTokenLogin(TokenAuthorisation token, String username, String password) throws MovieDbException {
        return tmdbAuth.getSessionTokenLogin(token, username, password);
    }

    /**
     * This method is used to generate a guest session id.
     *
     * A guest session can be used to rate movies without having a registered TMDb user account.
     *
     * You should only generate a single guest session per user (or device) as you will be able to attach the ratings to a TMDb user
     * account in the future.
     *
     * There are also IP limits in place so you should always make sure it's the end user doing the guest session actions.
     *
     * If a guest session is not used for the first time within 24 hours, it will be automatically discarded.
     *
     * @return
     * @throws MovieDbException
     */
    public TokenSession getGuestSessionToken() throws MovieDbException {
        return tmdbAuth.getGuestSessionToken();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Certifications">
    /**
     * Get a list of movies certification.
     *
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsMap<String, List<Certification>> getMoviesCertification() throws MovieDbException {
        return tmdbCertifications.getMoviesCertification();
    }

    /**
     * Get a list of tv certification.
     *
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsMap<String, List<Certification>> getTvCertification() throws MovieDbException {
        return tmdbCertifications.getTvCertification();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Changes">
    /**
     * Get the changes for a specific movie id.
     *
     * Changes are grouped by key, and ordered by date in descending order.
     *
     * By default, only the last 24 hours of changes are returned.
     *
     * The maximum number of days that can be returned in a single request is 14.
     *
     * The language is present on fields that are translatable.
     *
     * TODO: DOES NOT WORK AT THE MOMENT. This is due to the "value" item changing type in the ChangeItem
     *
     * @param movieId
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsMap<String, List<ChangedItem>> getMovieChanges(int movieId, String startDate, String endDate) throws MovieDbException {
        return tmdbChanges.getMovieChanges(movieId, startDate, endDate);
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
     * @throws MovieDbException
     */
    public void getPersonChanges(int personId, String startDate, String endDate) throws MovieDbException {
        tmdbChanges.getPersonChanges(personId, startDate, endDate);
    }

    /**
     * Get a list of Movie IDs that have been edited.
     *
     * You can then use the movie changes API to get the actual data that has been changed.
     *
     * @param page
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return List of changed movie
     * @throws MovieDbException
     */
    public TmdbResultsList<ChangedMedia> getMovieChangeList(int page, String startDate, String endDate) throws MovieDbException {
        return tmdbChanges.getChangeList(MethodBase.MOVIE, page, startDate, endDate);
    }

    /**
     * Get a list of TV IDs that have been edited.
     *
     * You can then use the TV changes API to get the actual data that has been changed.
     *
     * @param page
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return List of changed movie
     * @throws MovieDbException
     */
    public TmdbResultsList<ChangedMedia> getTvChangeList(int page, String startDate, String endDate) throws MovieDbException {
        return tmdbChanges.getChangeList(MethodBase.TV, page, startDate, endDate);
    }

    /**
     * Get a list of Person IDs that have been edited.
     *
     * You can then use the person changes API to get the actual data that has been changed.
     *
     * @param page
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return List of changed movie
     * @throws MovieDbException
     */
    public TmdbResultsList<ChangedMedia> getPersonChangeList(int page, String startDate, String endDate) throws MovieDbException {
        return tmdbChanges.getChangeList(MethodBase.PERSON, page, startDate, endDate);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Collections">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Companies">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Configuration">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Credits">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Discover">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Find">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Genres">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Jobs">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Keywords">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Lists">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Movies">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Networks">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="People">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Reviews">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Search">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="TV">
    //</editor-fold>
}
