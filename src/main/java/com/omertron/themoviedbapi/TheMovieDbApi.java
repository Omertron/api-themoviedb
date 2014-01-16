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
package com.omertron.themoviedbapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omertron.themoviedbapi.MovieDbException.MovieDbExceptionType;
import com.omertron.themoviedbapi.model.Account;
import com.omertron.themoviedbapi.model.AlternativeTitle;
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.ArtworkType;
import com.omertron.themoviedbapi.model.ChangeKeyItem;
import com.omertron.themoviedbapi.model.ChangedItem;
import com.omertron.themoviedbapi.model.ChangedMovie;
import com.omertron.themoviedbapi.model.Collection;
import com.omertron.themoviedbapi.model.CollectionInfo;
import com.omertron.themoviedbapi.model.Company;
import com.omertron.themoviedbapi.model.Discover;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.JobDepartment;
import com.omertron.themoviedbapi.model.Keyword;
import com.omertron.themoviedbapi.model.KeywordMovie;
import com.omertron.themoviedbapi.model.ListItemStatus;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.model.MovieDbList;
import com.omertron.themoviedbapi.model.MovieDbListStatus;
import com.omertron.themoviedbapi.model.MovieList;
import com.omertron.themoviedbapi.model.Person;
import com.omertron.themoviedbapi.model.PersonCredit;
import com.omertron.themoviedbapi.model.ReleaseInfo;
import com.omertron.themoviedbapi.model.Reviews;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.TmdbConfiguration;
import com.omertron.themoviedbapi.model.TokenAuthorisation;
import com.omertron.themoviedbapi.model.TokenSession;
import com.omertron.themoviedbapi.model.Trailer;
import com.omertron.themoviedbapi.model.Translation;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.results.TmdbResultsMap;
import com.omertron.themoviedbapi.tools.ApiUrl;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_ADULT;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_COUNTRY;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_END_DATE;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_ID;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_INCLUDE_ALL_MOVIES;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_LANGUAGE;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_PAGE;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_QUERY;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_SESSION;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_START_DATE;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_TOKEN;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_YEAR;
import com.omertron.themoviedbapi.tools.WebBrowser;
import com.omertron.themoviedbapi.wrapper.WrapperAlternativeTitles;
import com.omertron.themoviedbapi.wrapper.WrapperChanges;
import com.omertron.themoviedbapi.wrapper.WrapperCollection;
import com.omertron.themoviedbapi.wrapper.WrapperCompany;
import com.omertron.themoviedbapi.wrapper.WrapperCompanyMovies;
import com.omertron.themoviedbapi.wrapper.WrapperConfig;
import com.omertron.themoviedbapi.wrapper.WrapperGenres;
import com.omertron.themoviedbapi.wrapper.WrapperImages;
import com.omertron.themoviedbapi.wrapper.WrapperJobList;
import com.omertron.themoviedbapi.wrapper.WrapperKeywordMovies;
import com.omertron.themoviedbapi.wrapper.WrapperKeywords;
import com.omertron.themoviedbapi.wrapper.WrapperMovie;
import com.omertron.themoviedbapi.wrapper.WrapperMovieCasts;
import com.omertron.themoviedbapi.wrapper.WrapperMovieChanges;
import com.omertron.themoviedbapi.wrapper.WrapperMovieDbList;
import com.omertron.themoviedbapi.wrapper.WrapperMovieKeywords;
import com.omertron.themoviedbapi.wrapper.WrapperMovieList;
import com.omertron.themoviedbapi.wrapper.WrapperPerson;
import com.omertron.themoviedbapi.wrapper.WrapperPersonCredits;
import com.omertron.themoviedbapi.wrapper.WrapperPersonList;
import com.omertron.themoviedbapi.wrapper.WrapperReleaseInfo;
import com.omertron.themoviedbapi.wrapper.WrapperReviews;
import com.omertron.themoviedbapi.wrapper.WrapperTrailers;
import com.omertron.themoviedbapi.wrapper.WrapperTranslations;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

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
    private CommonHttpClient httpClient;
    private TmdbConfiguration tmdbConfig;
    // API Methods
    private static final String BASE_MOVIE = "movie/";
    private static final String BASE_PERSON = "person/";
    private static final String BASE_COMPANY = "company/";
    private static final String BASE_GENRE = "genre/";
    private static final String BASE_AUTH = "authentication/";
    private static final String BASE_COLLECTION = "collection/";
    private static final String BASE_ACCOUNT = "account/";
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
        this(apiKey, null);
    }

    /**
     * API for The Movie Db.
     *
     * @param apiKey
     * @param httpClient The httpClient to use for web requests.
     * @throws MovieDbException
     */
    public TheMovieDbApi(String apiKey, CommonHttpClient httpClient) throws MovieDbException {
        this.apiKey = apiKey;
        this.httpClient = httpClient;

        ApiUrl apiUrl = new ApiUrl(apiKey, "configuration");
        URL configUrl = apiUrl.buildUrl();
        String webpage = requestWebPage(configUrl);

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
     * @return
     */
    public String getApiKey() {
        return apiKey;
    }

    private String requestWebPage(URL url) throws MovieDbException {
        return requestWebPage(url, null, Boolean.FALSE);
    }

    private String requestWebPage(URL url, String jsonBody) throws MovieDbException {
        return requestWebPage(url, jsonBody, Boolean.FALSE);
    }

    private String requestWebPage(URL url, String jsonBody, boolean isDeleteRequest) throws MovieDbException {
        String webpage;
        // use HTTP client implementation
        if (httpClient == null) {
            // use web browser
            webpage = WebBrowser.request(url, jsonBody, isDeleteRequest);
        } else {
            try {
                HttpGet httpGet = new HttpGet(url.toURI());
                httpGet.addHeader("accept", "application/json");

                if (StringUtils.isNotBlank(jsonBody)) {
                    // TODO: Add the json body to the request
                    throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "Unable to proces JSON request");
                }

                if (isDeleteRequest) {
                    //TODO: Handle delete request
                    throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "Unable to proces delete request");
                }

                webpage = httpClient.requestContent(httpGet);
            } catch (URISyntaxException ex) {
                throw new MovieDbException(MovieDbException.MovieDbExceptionType.CONNECTION_ERROR, null, ex);
            } catch (IOException ex) {
                throw new MovieDbException(MovieDbException.MovieDbExceptionType.CONNECTION_ERROR, null, ex);
            } catch (RuntimeException ex) {
                throw new MovieDbException(MovieDbException.MovieDbExceptionType.HTTP_503_ERROR, "Service Unavailable", ex);
            }
        }
        return webpage;
    }

    /**
     * Set the proxy information
     *
     * @param host
     * @param port
     * @param username
     * @param password
     */
    public void setProxy(String host, int port, String username, String password) {
        // should be set in HTTP client already
        if (httpClient != null) {
            return;
        }

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
        // should be set in HTTP client already
        if (httpClient != null) {
            return;
        }

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
            String movieYear = moviedb.getReleaseDate().substring(0, 4);
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
     *
     * @return
     */
    public TmdbConfiguration getConfiguration() {
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
            throw new MovieDbException(MovieDbExceptionType.INVALID_IMAGE, requiredSize);
        }

        StringBuilder sb = new StringBuilder(tmdbConfig.getBaseUrl());
        sb.append(requiredSize);
        sb.append(imagePath);
        try {
            return (new URL(sb.toString()));
        } catch (MalformedURLException ex) {
            LOG.warn("Failed to create image URL: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.INVALID_URL, sb.toString(), ex);
        }
    }
    //</editor-fold>

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
     * @return
     * @throws MovieDbException
     */
    public TokenAuthorisation getAuthorisationToken() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_AUTH, "token/new");

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, TokenAuthorisation.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get Authorisation Token: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.AUTHORISATION_FAILURE, webpage, ex);
        }
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_AUTH, "session/new");

        if (!token.getSuccess()) {
            LOG.warn("Session token was not successful!");
            throw new MovieDbException(MovieDbExceptionType.AUTHORISATION_FAILURE, "Authorisation token was not successful!");
        }

        apiUrl.addArgument(PARAM_TOKEN, token.getRequestToken());
        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, TokenSession.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get Session Token: {}", ex.getMessage(), ex);
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
     * @return
     * @throws MovieDbException
     */
    public TokenSession getGuestSessionToken() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_AUTH, "guest_session/new");

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, TokenSession.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get Guest Session Token: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the basic information for an account. You will need to have a valid session id.
     *
     * @param sessionId
     * @return
     * @throws MovieDbException
     */
    public Account getAccount(String sessionId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT.replace("/", ""));

        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, Account.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get Account: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    public List<MovieDb> getFavoriteMovies(String sessionId, int accountId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, accountId + "/favorite_movies");
        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, WrapperMovie.class).getMovies();
        } catch (IOException ex) {
            LOG.warn("Failed to get favorite movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    public StatusCode changeFavoriteStatus(String sessionId, int accountId, Integer movieId, boolean isFavorite) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, accountId + "/favorite");

        apiUrl.addArgument(PARAM_SESSION, sessionId);

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("movie_id", movieId);
        body.put("favorite", isFavorite);
        String jsonBody = convertToJson(body);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url, jsonBody);

        try {
            return mapper.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get favorite status: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Add a movie to an accounts watch list.
     *
     * @param sessionId
     * @param accountId
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public StatusCode addToWatchList(String sessionId, int accountId, Integer movieId) throws MovieDbException {
        return modifyWatchList(sessionId, accountId, movieId, true);
    }

    /**
     * Remove a movie from an accounts watch list.
     *
     * @param sessionId
     * @param accountId
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public StatusCode removeFromWatchList(String sessionId, int accountId, Integer movieId) throws MovieDbException {
        return modifyWatchList(sessionId, accountId, movieId, false);
    }

    private StatusCode modifyWatchList(String sessionId, int accountId, Integer movieId, boolean add) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, accountId + "/movie_watchlist");

        apiUrl.addArgument(PARAM_SESSION, sessionId);

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("movie_id", movieId);
        body.put("movie_watchlist", add);
        String jsonBody = convertToJson(body);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url, jsonBody);

        try {
            return mapper.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            LOG.warn("Failed to modify watch list: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Movie Functions">
    /**
     * This method is used to retrieve all of the basic movie information.
     *
     * It will return the single highest rated poster and backdrop.
     *
     * MovieDbExceptionType.MOVIE_ID_NOT_FOUND will be thrown if there are no movies found.
     *
     * @param movieId
     * @param language
     * @param appendToResponse
     * @return
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
        String webpage = requestWebPage(url);
        try {
            MovieDb movie = mapper.readValue(webpage, MovieDb.class);
            if (movie == null || movie.getId() == 0) {
                LOG.warn("No movie found for ID '{}'", movieId);
                throw new MovieDbException(MovieDbExceptionType.MOVIE_ID_NOT_FOUND, "No movie found for ID: " + movieId);
            }
            return movie;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie info: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the basic movie information.
     *
     * It will return the single highest rated poster and backdrop.
     *
     * MovieDbExceptionType.MOVIE_ID_NOT_FOUND will be thrown if there are no movies found.
     *
     * @param imdbId
     * @param language
     * @param appendToResponse
     * @return
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
        String webpage = requestWebPage(url);
        try {
            MovieDb movie = mapper.readValue(webpage, MovieDb.class);
            if (movie == null || movie.getId() == 0) {
                LOG.warn("No movie found for IMDB ID: '{}'", imdbId);
                throw new MovieDbException(MovieDbExceptionType.MOVIE_ID_NOT_FOUND, "No movie found for IMDB ID: " + imdbId);
            }
            return movie;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie info: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, "Failed to get movie info", ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/alternative_titles");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(country)) {
            apiUrl.addArgument(PARAM_COUNTRY, country);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);
        try {
            WrapperAlternativeTitles wrapper = mapper.readValue(webpage, WrapperAlternativeTitles.class);
            TmdbResultsList<AlternativeTitle> results = new TmdbResultsList<AlternativeTitle>(wrapper.getTitles());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie alternative titles: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/casts");
        apiUrl.addArgument(PARAM_ID, movieId);

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperMovieCasts wrapper = mapper.readValue(webpage, WrapperMovieCasts.class);
            TmdbResultsList<Person> results = new TmdbResultsList<Person>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie casts: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/images");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie images: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/keywords");
        apiUrl.addArgument(PARAM_ID, movieId);

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperMovieKeywords wrapper = mapper.readValue(webpage, WrapperMovieKeywords.class);
            TmdbResultsList<Keyword> results = new TmdbResultsList<Keyword>(wrapper.getKeywords());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie keywords: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/releases");
        apiUrl.addArgument(PARAM_ID, movieId);
        apiUrl.addArgument(PARAM_LANGUAGE, language);

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperReleaseInfo wrapper = mapper.readValue(webpage, WrapperReleaseInfo.class);
            TmdbResultsList<ReleaseInfo> results = new TmdbResultsList<ReleaseInfo>(wrapper.getCountries());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie release information: {}", ex.getMessage(), ex);
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
     * @param appendToResponse
     * @return
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
        String webpage = requestWebPage(url);

        try {
            WrapperTrailers wrapper = mapper.readValue(webpage, WrapperTrailers.class);
            TmdbResultsList<Trailer> results = new TmdbResultsList<Trailer>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie trailers: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/translations");
        apiUrl.addArgument(PARAM_ID, movieId);

        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperTranslations wrapper = mapper.readValue(webpage, WrapperTranslations.class);
            TmdbResultsList<Translation> results = new TmdbResultsList<Translation>(wrapper.getTranslations());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie tranlations: {}", ex.getMessage(), ex);
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
     * @param appendToResponse
     * @return
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
        String webpage = requestWebPage(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get similar movies: {}", ex.getMessage(), ex);
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
        String webpage = requestWebPage(url);

        try {
            WrapperReviews wrapper = mapper.readValue(webpage, WrapperReviews.class);
            TmdbResultsList<Reviews> results = new TmdbResultsList<Reviews>(wrapper.getReviews());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get reviews: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        String webpage = requestWebPage(url);

        try {
            WrapperMovieList wrapper = mapper.readValue(webpage, WrapperMovieList.class);
            TmdbResultsList<MovieList> results = new TmdbResultsList<MovieList>(wrapper.getMovieList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie lists: {}", ex.getMessage(), ex);
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
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsMap<String, List<ChangedItem>> getMovieChanges(int movieId, String startDate, String endDate) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/changes");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(startDate)) {
            apiUrl.addArgument(PARAM_START_DATE, startDate);
        }

        if (StringUtils.isNotBlank(endDate)) {
            apiUrl.addArgument(PARAM_END_DATE, endDate);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);
        try {
            WrapperChanges wrapper = mapper.readValue(webpage, WrapperChanges.class);

            Map<String, List<ChangedItem>> results = new HashMap<String, List<ChangedItem>>();
            for (ChangeKeyItem changeItem : wrapper.getChangedItems()) {
                results.put(changeItem.getKey(), changeItem.getChangedItems());
            }

            return new TmdbResultsMap<String, List<ChangedItem>>(results);
        } catch (IOException ex) {
            LOG.warn("Failed to get movie changes: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }

    /**
     * This method is used to retrieve the newest movie that was added to TMDb.
     *
     * @return
     * @throws MovieDbException
     */
    public MovieDb getLatestMovie() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/latest");
        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, MovieDb.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get latest movie: {}", ex.getMessage(), ex);
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
     * @param language
     * @param page
     * @return
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
        String webpage = requestWebPage(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get upcoming movies: {}", ex.getMessage(), ex);
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
     * @return
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
        String webpage = requestWebPage(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get now playing movies: {}", ex.getMessage(), ex);
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
     * @return
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
        String webpage = requestWebPage(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get popular movie list: {}", ex.getMessage(), ex);
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
     * @return
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
        String webpage = requestWebPage(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get top rated movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, accountId + "/rated_movies");
        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, WrapperMovie.class).getMovies();
        } catch (IOException ex) {
            LOG.warn("Failed to get rated movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, movieId + "/rating");

        apiUrl.addArgument(PARAM_SESSION, sessionId);

        if (rating < 0 || rating > 10) {
            throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "Rating out of range");
        }

        String jsonBody = convertToJson(Collections.singletonMap("value", rating));
        LOG.info("Body: {}", jsonBody);
        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url, jsonBody);

        try {
            StatusCode status = mapper.readValue(webpage, StatusCode.class);
            LOG.info("Status: {}", status);
            int code = status.getStatusCode();
            return code == 12;
        } catch (IOException ex) {
            LOG.warn("Failed to post movie rating: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Collection Functions">
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_COLLECTION);
        apiUrl.addArgument(PARAM_ID, collectionId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, CollectionInfo.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get collection information: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_COLLECTION, "/images");
        apiUrl.addArgument(PARAM_ID, collectionId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll(ArtworkType.POSTER, ArtworkType.BACKDROP));
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get collection images: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="People Functions">
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON);

        apiUrl.addArgument(PARAM_ID, personId);
        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, Person.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get person info: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/credits");

        apiUrl.addArgument(PARAM_ID, personId);
        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperPersonCredits wrapper = mapper.readValue(webpage, WrapperPersonCredits.class);
            TmdbResultsList<PersonCredit> results = new TmdbResultsList<PersonCredit>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get person credits: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll(ArtworkType.PROFILE));
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get person images: {}", ex.getMessage(), ex);
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
        String webpage = requestWebPage(url);

        try {
            WrapperPersonList wrapper = mapper.readValue(webpage, WrapperPersonList.class);
            TmdbResultsList<Person> results = new TmdbResultsList<Person>(wrapper.getPersonList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get popular person: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
            return mapper.readValue(webpage, Person.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get latest person: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Company Functions">
    /**
     * This method is used to retrieve the basic information about a production company on TMDb.
     *
     * @param companyId
     * @return
     * @throws MovieDbException
     */
    public Company getCompanyInfo(int companyId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_COMPANY);

        apiUrl.addArgument(PARAM_ID, companyId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, Company.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get company information: {}", ex.getMessage(), ex);
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
     * @return
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
        String webpage = requestWebPage(url);

        try {
            WrapperCompanyMovies wrapper = mapper.readValue(webpage, WrapperCompanyMovies.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get company movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Genre Functions">
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_GENRE, "/list");
        apiUrl.addArgument(PARAM_LANGUAGE, language);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperGenres wrapper = mapper.readValue(webpage, WrapperGenres.class);
            TmdbResultsList<Genre> results = new TmdbResultsList<Genre>(wrapper.getGenres());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get genre list: {}", ex.getMessage(), ex);
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
     * @param includeAllMovies
     * @return
     * @throws MovieDbException
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
        String webpage = requestWebPage(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get genre movie list: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search Functions">
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

        String webpage = requestWebPage(url);
        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find movie: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "collection");

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

        String webpage = requestWebPage(url);
        try {
            WrapperCollection wrapper = mapper.readValue(webpage, WrapperCollection.class);
            TmdbResultsList<Collection> results = new TmdbResultsList<Collection>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find collection: {}", ex.getMessage(), ex);
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
     * @return
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
        String webpage = requestWebPage(url);

        try {
            WrapperPerson wrapper = mapper.readValue(webpage, WrapperPerson.class);
            TmdbResultsList<Person> results = new TmdbResultsList<Person>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find person: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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

        String webpage = requestWebPage(url);
        try {
            WrapperMovieList wrapper = mapper.readValue(webpage, WrapperMovieList.class);
            TmdbResultsList<MovieList> results = new TmdbResultsList<MovieList>(wrapper.getMovieList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find list: {}", ex.getMessage(), ex);
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
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Company> searchCompanies(String companyName, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "company");
        apiUrl.addArgument(PARAM_QUERY, companyName);

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);
        try {
            WrapperCompany wrapper = mapper.readValue(webpage, WrapperCompany.class);
            TmdbResultsList<Company> results = new TmdbResultsList<Company>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find company: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "keyword");

        if (StringUtils.isNotBlank(query)) {
            apiUrl.addArgument(PARAM_QUERY, query);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, Integer.toString(page));
        }

        URL url = apiUrl.buildUrl();

        String webpage = requestWebPage(url);
        try {
            WrapperKeywords wrapper = mapper.readValue(webpage, WrapperKeywords.class);
            TmdbResultsList<Keyword> results = new TmdbResultsList<Keyword>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find keyword: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

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
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, MovieDbList.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get list: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get all lists of a given user
     *
     * @param sessionId
     * @param accountID
     * @return The lists
     * @throws MovieDbException
     */
    public List<MovieDbList> getUserLists(String sessionId, int accountID) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, accountID + "/lists");
        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, WrapperMovieDbList.class).getLists();
        } catch (IOException ex) {
            LOG.warn("Failed to get user list: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, "list");
        apiUrl.addArgument(PARAM_SESSION, sessionId);

        Map<String, String> body = new HashMap<String, String>();
        body.put("name", StringUtils.trimToEmpty(name));
        body.put("description", StringUtils.trimToEmpty(description));

        String jsonBody = convertToJson(body);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url, jsonBody);

        try {
            return mapper.readValue(webpage, MovieDbListStatus.class).getListId();
        } catch (IOException ex) {
            LOG.warn("Failed to create list: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_LIST, listId + "/item_status");
        apiUrl.addArgument("movie_id", movieId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, ListItemStatus.class).isItemPresent();
        } catch (IOException ex) {
            LOG.warn("Failed to get item status: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        return modifyMovieList(sessionId, listId, movieId, "/add_item");
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
        return modifyMovieList(sessionId, listId, movieId, "/remove_item");
    }

    private StatusCode modifyMovieList(String sessionId, String listId, Integer movieId, String operation) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_LIST, listId + operation);

        apiUrl.addArgument(PARAM_SESSION, sessionId);

        String jsonBody = convertToJson(Collections.singletonMap("media_id", movieId + ""));

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url, jsonBody);

        try {
            return mapper.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            LOG.warn("Failed to remove movie from list: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the list of movies on an accounts watchlist.
     *
     * @param sessionId
     * @param accountId
     * @return The watchlist of the user
     * @throws MovieDbException
     */
    public List<MovieDb> getWatchList(String sessionId, int accountId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_ACCOUNT, accountId + "/movie_watchlist");
        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, WrapperMovie.class).getMovies();
        } catch (IOException ex) {
            LOG.warn("Failed to get watch list: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_LIST, listId);

        apiUrl.addArgument(PARAM_SESSION, sessionId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url, null, true);

        try {
            return mapper.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            LOG.warn("Failed to delete movie list: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

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
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, Keyword.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get keyword: {}", ex.getMessage(), ex);
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
        String webpage = requestWebPage(url);

        try {
            WrapperKeywordMovies wrapper = mapper.readValue(webpage, WrapperKeywordMovies.class);
            TmdbResultsList<KeywordMovie> results = new TmdbResultsList<KeywordMovie>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get keyword movies: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Changes Functions">
    /**
     * Get a list of movie ids that have been edited. By default we show the last 24 hours and only 100 items per page. The maximum
     * number of days that can be returned in a single request is 14. You can then use the movie changes API to get the actual data
     * that has been changed. Please note that the change log system to support this was changed on October 5, 2012 and will only
     * show movies that have been edited since.
     *
     * @param page
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return List of changed movie
     * @throws MovieDbException
     */
    public TmdbResultsList<ChangedMovie> getMovieChangesList(int page, String startDate, String endDate) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/changes");

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        if (StringUtils.isNotBlank(startDate)) {
            apiUrl.addArgument(PARAM_START_DATE, startDate);
        }

        if (StringUtils.isNotBlank(endDate)) {
            apiUrl.addArgument(PARAM_END_DATE, endDate);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);
        try {
            WrapperMovieChanges wrapper = mapper.readValue(webpage, WrapperMovieChanges.class);

            TmdbResultsList<ChangedMovie> results = new TmdbResultsList<ChangedMovie>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie changes: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    public void getPersonChangesList(int page, String startDate, String endDate) throws MovieDbException {
        throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Jobs">
    public TmdbResultsList<JobDepartment> getJobs() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_JOB, "/list");

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperJobList wrapper = mapper.readValue(webpage, WrapperJobList.class);
            TmdbResultsList<JobDepartment> results = new TmdbResultsList<JobDepartment>(wrapper.getJobs());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get job list: {}", ex.getMessage(), ex);
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
        String webpage = requestWebPage(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get discover list: {}", ex.getMessage(), ex);
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
    //</editor-fold>

    /**
     * Use Jackson to convert Map to JSON string.
     *
     * @param map
     * @return
     * @throws MovieDbException
     */
    public static String convertToJson(Map<String, ?> map) throws MovieDbException {
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException jpe) {
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, "JSON conversion failed", jpe);
        }
    }
}
