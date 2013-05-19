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
package com.omertron.themoviedbapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omertron.themoviedbapi.MovieDbException.MovieDbExceptionType;
import com.omertron.themoviedbapi.model.*;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import static com.omertron.themoviedbapi.tools.ApiUrl.*;
import com.omertron.themoviedbapi.tools.WebBrowser;
import com.omertron.themoviedbapi.wrapper.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The MovieDb API <p> This is for version 3 of the API as specified here: http://help.themoviedb.org/kb/api/about-3
 *
 * @author stuart.boston
 */
public class TheMovieDbApi {

    private static final Logger LOG = LoggerFactory.getLogger(TheMovieDbApi.class);
    private String apiKey;
    private TmdbConfiguration tmdbConfig;
    // API Methods
    private static final String BASE_MOVIE = "movie/";
    private static final String BASE_PERSON = "person/";
    private static final String BASE_COMPANY = "company/";
    private static final String BASE_GENRE = "genre/";
    private static final String BASE_AUTH = "authentication/";
    private static final String BASE_COLLECTION = "collection/";
//    private static final String BASE_ACCOUNT = "account/";
    private static final String BASE_SEARCH = "search/";
    private static final String BASE_LIST = "list/";
    private static final String BASE_KEYWORD = "keyword/";
    private static final String BASE_JOB = "job/";
    private static final String BASE_DISCOVER = "discover/";
    // Jackson JSON configuration
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * API for The Movie Db.
     *
     * @param apiKey
     * @throws MovieDbException
     */
    public TheMovieDbApi(String apiKey) throws MovieDbException {
        this.apiKey = apiKey;
        ApiUrl apiUrl = new ApiUrl(apiKey, "configuration");
        URL configUrl = apiUrl.buildUrl();
        String webpage = WebBrowser.request(configUrl);

        try {
            WrapperConfig wc = mapper.readValue(webpage, WrapperConfig.class);
            tmdbConfig = wc.getTmdbConfiguration();
        } catch (IOException ex) {
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, "Failed to read configuration", ex);
        }
    }

    /**
     * Get the API key that is to be used
     *
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Set the proxy information
     *
     * @param host
     * @param port
     * @param username
     * @param password
     */
    public void setProxy(String host, String port, String username, String password) {
        WebBrowser.setProxyHost(host);
        WebBrowser.setProxyPort(port);
        WebBrowser.setProxyUsername(username);
        WebBrowser.setProxyPassword(password);
    }

    /**
     * Set the connection and read time out values
     *
     * @param connect
     * @param read
     */
    public void setTimeout(int connect, int read) {
        WebBrowser.setWebTimeoutConnect(connect);
        WebBrowser.setWebTimeoutRead(read);
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
     * @return True if there is a match, False otherwise.
     */
    public static boolean compareMovies(MovieDb moviedb, String title, String year, int maxDistance) {
        if ((moviedb == null) || (StringUtils.isBlank(title))) {
            return Boolean.FALSE;
        }

        if (isValidYear(year) && isValidYear(moviedb.getReleaseDate())) {
            // Compare with year
            String movieYear = moviedb.getReleaseDate().substring(0, 4);
            if (movieYear.equals(year)) {
                if (compareDistance(moviedb.getOriginalTitle(), title, maxDistance)) {
                    return Boolean.TRUE;
                }

                if (compareDistance(moviedb.getTitle(), title, maxDistance)) {
                    return Boolean.TRUE;
                }
            }
        }

        // Compare without year
        if (compareDistance(moviedb.getOriginalTitle(), title, maxDistance)) {
            return Boolean.TRUE;
        }

        if (compareDistance(moviedb.getTitle(), title, maxDistance)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Compare the Levenshtein Distance between the two strings
     *
     * @param title1
     * @param title2
     * @param distance
     */
    private static boolean compareDistance(String title1, String title2, int distance) {
        return (StringUtils.getLevenshteinDistance(title1, title2) <= distance);
    }

    /**
     * Check the year is not blank or UNKNOWN
     *
     * @param year
     */
    private static boolean isValidYear(String year) {
        return (StringUtils.isNotBlank(year) && !year.equals("UNKNOWN"));
    }

    //<editor-fold defaultstate="collapsed" desc="Configuration Functions">
    /**
     * Get the configuration information
     */
    public TmdbConfiguration getConfiguration() {
        return tmdbConfig;
    }

    /**
     * Generate the full image URL from the size and image path
     *
     * @param imagePath
     * @param requiredSize
     * @throws MovieDbException
     */
    public URL createImageUrl(String imagePath, String requiredSize) throws MovieDbException {
        if (!tmdbConfig.isValidSize(requiredSize)) {
            throw new MovieDbException(MovieDbExceptionType.INVALID_IMAGE, requiredSize);
        }

        StringBuilder sb = new StringBuilder(tmdbConfig.getBaseUrl());
        sb.append(requiredSize);
        sb.append(imagePath);
        try {
            return (new URL(sb.toString()));
        } catch (MalformedURLException ex) {
            LOG.warn("Failed to create image URL: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.INVALID_URL, sb.toString(), ex);
        }
    }

    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="Authentication Functions">
    /**
     * This method is used to generate a valid request token for user based authentication.
     *
     * A request token is required in order to request a session id.
     *
     * You can generate any number of request tokens but they will expire after 60 minutes.
     *
     * As soon as a valid session id has been created the token will be destroyed.
     *
     * @throws MovieDbException
     */
    public TokenAuthorisation getAuthorisationToken() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_AUTH, "token/new");

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, TokenAuthorisation.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get Authorisation Token: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.AUTHORISATION_FAILURE, webpage, ex);
        }
    }

    /**
     * This method is used to generate a session id for user based authentication.
     *
     * A session id is required in order to use any of the write methods.
     *
     * @param token
     * @throws MovieDbException
     */
    public TokenSession getSessionToken(TokenAuthorisation token) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_AUTH, "session/new");

        if (!token.getSuccess()) {
            LOG.warn("Authorisation token was not successful!");
            throw new MovieDbException(MovieDbExceptionType.AUTHORISATION_FAILURE, "Authorisation token was not successful!");
        }

        apiUrl.addArgument(PARAM_TOKEN, token.getRequestToken());
        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, TokenSession.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get Session Token: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
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
     * @throws MovieDbException
     */
    public TokenSession getGuestSessionToken() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_AUTH, "guest_session/new");

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, TokenSession.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get Session Token: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="Account Functions">
    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="Movie Functions">
    /**
     * This method is used to retrieve all of the basic movie information.
     *
     * It will return the single highest rated poster and backdrop.
     *
     * @param movieId
     * @param language
     * @throws MovieDbException
     */
    public MovieDb getMovieInfo(int movieId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE);

        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            return mapper.readValue(webpage, MovieDb.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get movie info: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the basic movie information.
     *
     * It will return the single highest rated poster and backdrop.
     *
     * @param imdbId
     * @param language
     * @throws MovieDbException
     */
    public MovieDb getMovieInfoImdb(String imdbId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE);

        apiUrl.addArgument(PARAM_ID, imdbId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            return mapper.readValue(webpage, MovieDb.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get movie info: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the alternative titles we have for a particular movie.
     *
     * @param movieId
     * @param country
     * @throws MovieDbException
     */
    public TmdbResultsList<AlternativeTitle> getMovieAlternativeTitles(int movieId, String country, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/alternative_titles");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(country)) {
            apiUrl.addArgument(PARAM_COUNTRY, country);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            WrapperAlternativeTitles wrapper = mapper.readValue(webpage, WrapperAlternativeTitles.class);
            TmdbResultsList<AlternativeTitle> results = new TmdbResultsList<AlternativeTitle>(wrapper.getTitles());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie alternative titles: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the cast information for a specific movie id.
     *
     * TODO: Add a function to enrich the data with the people methods
     *
     * @param movieId
     * @throws MovieDbException
     */
    public TmdbResultsList<Person> getMovieCasts(int movieId, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/casts");
        apiUrl.addArgument(PARAM_ID, movieId);

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovieCasts wrapper = mapper.readValue(webpage, WrapperMovieCasts.class);
            TmdbResultsList<Person> results = new TmdbResultsList<Person>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie casts: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method should be used when you’re wanting to retrieve all of the images for a particular movie.
     *
     * @param movieId
     * @param language
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getMovieImages(int movieId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/images");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie images: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the keywords that have been added to a particular movie.
     *
     * Currently, only English keywords exist.
     *
     * @param movieId
     * @throws MovieDbException
     */
    public TmdbResultsList<Keyword> getMovieKeywords(int movieId, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/keywords");
        apiUrl.addArgument(PARAM_ID, movieId);

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovieKeywords wrapper = mapper.readValue(webpage, WrapperMovieKeywords.class);
            TmdbResultsList<Keyword> results = new TmdbResultsList<Keyword>(wrapper.getKeywords());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie keywords: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the release and certification data we have for a specific movie.
     *
     * @param movieId
     * @param language
     * @throws MovieDbException
     */
    public TmdbResultsList<ReleaseInfo> getMovieReleaseInfo(int movieId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/releases");
        apiUrl.addArgument(PARAM_ID, movieId);
        apiUrl.addArgument(PARAM_LANGUAGE, language);

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperReleaseInfo wrapper = mapper.readValue(webpage, WrapperReleaseInfo.class);
            TmdbResultsList<ReleaseInfo> results = new TmdbResultsList<ReleaseInfo>(wrapper.getCountries());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie release information: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the trailers for a particular movie.
     *
     * Supported sites are YouTube and QuickTime.
     *
     * @param movieId
     * @param language
     * @throws MovieDbException
     */
    public TmdbResultsList<Trailer> getMovieTrailers(int movieId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/trailers");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperTrailers wrapper = mapper.readValue(webpage, WrapperTrailers.class);
            TmdbResultsList<Trailer> results = new TmdbResultsList<Trailer>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie trailers: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve a list of the available translations for a specific movie.
     *
     * @param movieId
     * @throws MovieDbException
     */
    public TmdbResultsList<Translation> getMovieTranslations(int movieId, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/translations");
        apiUrl.addArgument(PARAM_ID, movieId);

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperTranslations wrapper = mapper.readValue(webpage, WrapperTranslations.class);
            TmdbResultsList<Translation> results = new TmdbResultsList<Translation>(wrapper.getTranslations());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie tranlations: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getSimilarMovies(int movieId, String language, int page, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/similar_movies");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get similar movies: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    public TmdbResultsList<Reviews> getReviews(int movieId, String language, int page, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/reviews");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperReviews wrapper = mapper.readValue(webpage, WrapperReviews.class);
            TmdbResultsList<Reviews> results = new TmdbResultsList<Reviews>(wrapper.getReviews());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get reviews: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the lists that the movie belongs to
     *
     * @param movieId
     * @param language
     * @param page
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieList> getMovieLists(int movieId, String language, int page, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/lists");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovieList wrapper = mapper.readValue(webpage, WrapperMovieList.class);
            TmdbResultsList<MovieList> results = new TmdbResultsList<MovieList>(wrapper.getMovieList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie lists: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

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
     * @throws MovieDbException
     */
    @Deprecated
    public TmdbResultsList<MovieChanges> getMovieChanges(int movieId, String startDate, String endDate) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/changes");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(startDate)) {
            apiUrl.addArgument("start_date", startDate);
        }

        if (StringUtils.isNotBlank(endDate)) {
            apiUrl.addArgument("end_date", endDate);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperChanges wrapper = mapper.readValue(webpage, WrapperChanges.class);
            TmdbResultsList<MovieChanges> results = new TmdbResultsList<MovieChanges>(wrapper.getChanges());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie changes: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }

    /**
     * This method is used to retrieve the newest movie that was added to TMDb.
     *
     */
    public MovieDb getLatestMovie() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/latest");
        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, MovieDb.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get latest movie: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the list of upcoming movies.
     *
     * This list refreshes every day.
     *
     * The maximum number of items this list will include is 100.
     *
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getUpcoming(String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "upcoming");

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get upcoming movies: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getNowPlayingMovies(String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "now-playing");

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get now playing movies: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getPopularMovieList(String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "popular");

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get popular movie list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getTopRatedMovies(String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "top-rated");

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get top rated movies: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method lets users rate a movie.
     *
     * A valid session id is required.
     *
     * @param sessionId
     * @param rating
     * @throws MovieDbException
     */
    public boolean postMovieRating(String sessionId, String rating) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/rating");

        apiUrl.addArgument(PARAM_SESSION, sessionId);
        apiUrl.addArgument(PARAM_VALUE, rating);

        throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }

    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="Collection Functions">
    /**
     * This method is used to retrieve all of the basic information about a movie collection.
     *
     * You can get the ID needed for this method by making a getMovieInfo request for the belongs_to_collection.
     *
     * @param collectionId
     * @param language
     * @throws MovieDbException
     */
    public CollectionInfo getCollectionInfo(int collectionId, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_COLLECTION);
        apiUrl.addArgument(PARAM_ID, collectionId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, CollectionInfo.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get collection information: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get all of the images for a particular collection by collection id.
     *
     * @param collectionId
     * @param language
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getCollectionImages(int collectionId, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_COLLECTION, "/images");
        apiUrl.addArgument(PARAM_ID, collectionId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll(ArtworkType.POSTER, ArtworkType.BACKDROP));
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get collection images: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="People Functions">
    /**
     * This method is used to retrieve all of the basic person information.
     *
     * It will return the single highest rated profile image.
     *
     * @param personId
     * @throws MovieDbException
     */
    public Person getPersonInfo(int personId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON);

        apiUrl.addArgument(PARAM_ID, personId);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, Person.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get movie info: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the cast & crew information for the person.
     *
     * It will return the single highest rated poster for each movie record.
     *
     * @param personId
     * @throws MovieDbException
     */
    public TmdbResultsList<PersonCredit> getPersonCredits(int personId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/credits");

        apiUrl.addArgument(PARAM_ID, personId);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperPersonCredits wrapper = mapper.readValue(webpage, WrapperPersonCredits.class);
            TmdbResultsList<PersonCredit> results = new TmdbResultsList<PersonCredit>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get person credits: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the profile images for a person.
     *
     * @param personId
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getPersonImages(int personId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/images");

        apiUrl.addArgument(PARAM_ID, personId);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll(ArtworkType.PROFILE));
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get person images: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
     * @throws MovieDbException
     */
    public void getPersonChanges(int personId, String startDate, String endDate) throws MovieDbException {
        throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/popular");

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperPersonList wrapper = mapper.readValue(webpage, WrapperPersonList.class);
            TmdbResultsList<Person> results = new TmdbResultsList<Person>(wrapper.getPersonList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get person images: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the latest person id.
     *
     * @throws MovieDbException
     */
    public Person getPersonLatest() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/latest");
        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, Person.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get latest person: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="Company Functions">
    /**
     * This method is used to retrieve the basic information about a production company on TMDb.
     *
     * @param companyId
     * @throws MovieDbException
     */
    public Company getCompanyInfo(int companyId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_COMPANY);

        apiUrl.addArgument(PARAM_ID, companyId);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, Company.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get company information: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getCompanyMovies(int companyId, String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_COMPANY, "/movies");

        apiUrl.addArgument(PARAM_ID, companyId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperCompanyMovies wrapper = mapper.readValue(webpage, WrapperCompanyMovies.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get company movies: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="Genre Functions">
    /**
     * You can use this method to retrieve the list of genres used on TMDb.
     *
     * These IDs will correspond to those found in movie calls.
     *
     * @param language
     */
    public TmdbResultsList<Genre> getGenreList(String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_GENRE, "/list");
        apiUrl.addArgument(PARAM_LANGUAGE, language);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperGenres wrapper = mapper.readValue(webpage, WrapperGenres.class);
            TmdbResultsList<Genre> results = new TmdbResultsList<Genre>(wrapper.getGenres());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get genre list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
     */
    public TmdbResultsList<MovieDb> getGenreMovies(int genreId, String language, int page, boolean includeAllMovies) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_GENRE, "/movies");
        apiUrl.addArgument(PARAM_ID, genreId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        apiUrl.addArgument(PARAM_INCLUDE_ALL_MOVIES, includeAllMovies);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get genre movie list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="Search Functions">

    /**
     * Search Movies This is a good starting point to start finding movies on TMDb.
     *
     * @param movieName
     * @param searchYear Limit the search to the provided year. Zero (0) will get all years
     * @param language The language to include. Can be blank/null.
     * @param includeAdult true or false to include adult titles in the search
     * @param page The page of results to return. 0 to get the default (first page)
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> searchMovie(String movieName, int searchYear, String language, boolean includeAdult, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "movie");
        if (StringUtils.isNotBlank(movieName)) {
            apiUrl.addArgument(PARAM_QUERY, movieName);
        }

        if (searchYear > 0) {
            apiUrl.addArgument(PARAM_YEAR, Integer.toString(searchYear));
        }

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.addArgument(PARAM_ADULT, Boolean.toString(includeAdult));

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, Integer.toString(page));
        }

        URL url = apiUrl.buildUrl();

        String webpage = WebBrowser.request(url);
        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find movie: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }

    /**
     * Search for collections by name.
     *
     * @param query
     * @param language
     * @param page
     * @throws MovieDbException
     */
    public TmdbResultsList<Collection> searchCollection(String query, String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "collections");

        if (StringUtils.isNotBlank(query)) {
            apiUrl.addArgument(PARAM_QUERY, query);
        }

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, Integer.toString(page));
        }

        URL url = apiUrl.buildUrl();

        String webpage = WebBrowser.request(url);
        try {
            WrapperCollection wrapper = mapper.readValue(webpage, WrapperCollection.class);
            TmdbResultsList<Collection> results = new TmdbResultsList<Collection>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find collection: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
     * @throws MovieDbException
     */
    public TmdbResultsList<Person> searchPeople(String personName, boolean includeAdult, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "person");
        apiUrl.addArgument(PARAM_QUERY, personName);
        apiUrl.addArgument(PARAM_ADULT, includeAdult);

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperPerson wrapper = mapper.readValue(webpage, WrapperPerson.class);
            TmdbResultsList<Person> results = new TmdbResultsList<Person>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find person: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Search for lists by name and description.
     *
     * @param query
     * @param language
     * @param page
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieList> searchList(String query, String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "list");

        if (StringUtils.isNotBlank(query)) {
            apiUrl.addArgument(PARAM_QUERY, query);
        }

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, Integer.toString(page));
        }

        URL url = apiUrl.buildUrl();

        String webpage = WebBrowser.request(url);
        try {
            WrapperMovieList wrapper = mapper.readValue(webpage, WrapperMovieList.class);
            TmdbResultsList<MovieList> results = new TmdbResultsList<MovieList>(wrapper.getMovieList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
     * @throws MovieDbException
     */
    public TmdbResultsList<Company> searchCompanies(String companyName, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "company");
        apiUrl.addArgument(PARAM_QUERY, companyName);

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            WrapperCompany wrapper = mapper.readValue(webpage, WrapperCompany.class);
            TmdbResultsList<Company> results = new TmdbResultsList<Company>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find company: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Search for keywords by name
     *
     * @param query
     * @param page
     * @throws MovieDbException
     */
    public TmdbResultsList<Keyword> searchKeyword(String query, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "keyword");

        if (StringUtils.isNotBlank(query)) {
            apiUrl.addArgument(PARAM_QUERY, query);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, Integer.toString(page));
        }

        URL url = apiUrl.buildUrl();

        String webpage = WebBrowser.request(url);
        try {
            WrapperKeywords wrapper = mapper.readValue(webpage, WrapperKeywords.class);
            TmdbResultsList<Keyword> results = new TmdbResultsList<Keyword>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find keyword: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="List Functions">

    /**
     * Get a list by its ID
     *
     * @param listId
     * @return The list and its items
     * @throws MovieDbException
     */
    public MovieDbList getList(String listId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_LIST);
        apiUrl.addArgument(PARAM_ID, listId);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, MovieDbList.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="Keyword Functions">

    /**
     * Get the basic information for a specific keyword id.
     *
     * @param keywordId
     * @return
     * @throws MovieDbException
     */
    public Keyword getKeyword(String keywordId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_KEYWORD);
        apiUrl.addArgument(PARAM_ID, keywordId);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, Keyword.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get keyword: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_KEYWORD, "/movies");
        apiUrl.addArgument(PARAM_ID, keywordId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperKeywordMovies wrapper = mapper.readValue(webpage, WrapperKeywordMovies.class);
            TmdbResultsList<KeywordMovie> results = new TmdbResultsList<KeywordMovie>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get top rated movies: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }
    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="Changes Functions">

    public void getMovieChangesList(int page, String startDate, String endDate) throws MovieDbException {
        throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }

    public void getPersonChangesList(int page, String startDate, String endDate) throws MovieDbException {
        throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Jobs">
    public TmdbResultsList<JobDepartment> getJobs() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_JOB, "/list");

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperJobList wrapper = mapper.readValue(webpage, WrapperJobList.class);
            TmdbResultsList<JobDepartment> results = new TmdbResultsList<JobDepartment>(wrapper.getJobs());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get job list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Discover">
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_DISCOVER, "/movie");

        apiUrl.setArguments(discover.getParams());

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get discover list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>
}
