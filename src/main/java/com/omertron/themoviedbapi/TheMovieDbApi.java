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
import com.omertron.themoviedbapi.tools.ApiUrl;
import static com.omertron.themoviedbapi.tools.ApiUrl.*;
import com.omertron.themoviedbapi.tools.WebBrowser;
import com.omertron.themoviedbapi.wrapper.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    /*
     * API Methods
     *
     * These are not set to static so that multiple instances of
     * the API can co-exist
     */
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
    // Account
    /*
     private final ApiUrl tmdbAccount = new ApiUrl(this, BASE_ACCOUNT);
     private final ApiUrl tmdbFavouriteMovies = new ApiUrl(this, BASE_ACCOUNT, "/favorite_movies");
     private final ApiUrl tmdbPostFavourite = new ApiUrl(this, BASE_ACCOUNT, "/favorite");
     private final ApiUrl tmdbRatedMovies = new ApiUrl(this, BASE_ACCOUNT, "/rated_movies");
     private final ApiUrl tmdbMovieWatchList = new ApiUrl(this, BASE_ACCOUNT, "/movie_watchlist");
     private final ApiUrl tmdbPostMovieWatchList = new ApiUrl(this, BASE_ACCOUNT, "/movie_watchlist");
     */
    /*
     * Jackson JSON configuration
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * API for The Movie Db.
     *
     * @param apiKey
     * @throws MovieDbException
     */
    public TheMovieDbApi(String apiKey) throws MovieDbException {
        this.apiKey = apiKey;
        ApiUrl apiUrl = new ApiUrl(this, "configuration");
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
        ApiUrl apiUrl = new ApiUrl(this, BASE_AUTH, "token/new");

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
        ApiUrl apiUrl = new ApiUrl(this, BASE_AUTH, "session/new");

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
        ApiUrl apiUrl = new ApiUrl(this, BASE_AUTH, "guest_session/new");

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
    public MovieDb getMovieInfo(int movieId, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE);

        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

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
    public MovieDb getMovieInfoImdb(String imdbId, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE);

        apiUrl.addArgument(PARAM_ID, imdbId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

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
    public List<AlternativeTitle> getMovieAlternativeTitles(int movieId, String country) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/alternative_titles");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(country)) {
            apiUrl.addArgument(PARAM_COUNTRY, country);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            WrapperAlternativeTitles wrapper = mapper.readValue(webpage, WrapperAlternativeTitles.class);
            return wrapper.getTitles();
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
    public List<Person> getMovieCasts(int movieId) throws MovieDbException {
        List<Person> people = new ArrayList<Person>();

        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/casts");
        apiUrl.addArgument(PARAM_ID, movieId);
        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovieCasts wrapper = mapper.readValue(webpage, WrapperMovieCasts.class);

            // Add a cast member
            for (PersonCast cast : wrapper.getCast()) {
                Person person = new Person();
                person.addCast(cast.getId(), cast.getName(), cast.getProfilePath(), cast.getCharacter(), cast.getOrder());
                people.add(person);
            }

            // Add a crew member
            for (PersonCrew crew : wrapper.getCrew()) {
                Person person = new Person();
                person.addCrew(crew.getId(), crew.getName(), crew.getProfilePath(), crew.getDepartment(), crew.getJob());
                people.add(person);
            }

            return people;
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
    public List<Artwork> getMovieImages(int movieId, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/images");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        List<Artwork> artwork = new ArrayList<Artwork>();
        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);

            // Add all the posters to the list
            for (Artwork poster : wrapper.getPosters()) {
                poster.setArtworkType(ArtworkType.POSTER);
                artwork.add(poster);
            }

            // Add all the backdrops to the list
            for (Artwork backdrop : wrapper.getBackdrops()) {
                backdrop.setArtworkType(ArtworkType.BACKDROP);
                artwork.add(backdrop);
            }

            return artwork;
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
    public List<Keyword> getMovieKeywords(int movieId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/keywords");
        apiUrl.addArgument(PARAM_ID, movieId);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovieKeywords wrapper = mapper.readValue(webpage, WrapperMovieKeywords.class);
            return wrapper.getKeywords();
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
    public List<ReleaseInfo> getMovieReleaseInfo(int movieId, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/releases");
        apiUrl.addArgument(PARAM_ID, movieId);
        apiUrl.addArgument(PARAM_LANGUAGE, language);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperReleaseInfo wrapper = mapper.readValue(webpage, WrapperReleaseInfo.class);
            return wrapper.getCountries();
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
    public List<Trailer> getMovieTrailers(int movieId, String language) throws MovieDbException {
        List<Trailer> trailers = new ArrayList<Trailer>();

        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/trailers");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperTrailers wrapper = mapper.readValue(webpage, WrapperTrailers.class);

            // Add the trailer to the return list along with it's source
            for (Trailer trailer : wrapper.getQuicktime()) {
                trailer.setWebsite(Trailer.WEBSITE_QUICKTIME);
                trailers.add(trailer);
            }
            // Add the trailer to the return list along with it's source
            for (Trailer trailer : wrapper.getYoutube()) {
                trailer.setWebsite(Trailer.WEBSITE_YOUTUBE);
                trailers.add(trailer);
            }
            return trailers;
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
    public List<Translation> getMovieTranslations(int movieId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/translations");
        apiUrl.addArgument(PARAM_ID, movieId);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperTranslations wrapper = mapper.readValue(webpage, WrapperTranslations.class);
            return wrapper.getTranslations();
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
    public List<MovieDb> getSimilarMovies(int movieId, String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/similar_movies");
        apiUrl.addArgument(PARAM_ID, movieId);

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
            return wrapper.getMovies();
        } catch (IOException ex) {
            LOG.warn("Failed to get similar movies: {}", ex.getMessage());
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
    public List<MovieList> getMovieLists(int movieId, String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/lists");
        apiUrl.addArgument(PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovieList wrapper = mapper.readValue(webpage, WrapperMovieList.class);
            return wrapper.getMovieList();
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
    public List<MovieChanges> getMovieChanges(int movieId, String startDate, String endDate) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/changes");
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
            return wrapper.getChanges();
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
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/latest");
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
    public List<MovieDb> getUpcoming(String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "upcoming");

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
            return wrapper.getMovies();
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
    public List<MovieDb> getNowPlayingMovies(String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "now-playing");

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
            return wrapper.getMovies();
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
    public List<MovieDb> getPopularMovieList(String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "popular");

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
            return wrapper.getMovies();
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
    public List<MovieDb> getTopRatedMovies(String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "top-rated");

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
            return wrapper.getMovies();
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
        ApiUrl apiUrl = new ApiUrl(this, BASE_MOVIE, "/rating");

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
        ApiUrl apiUrl = new ApiUrl(this, BASE_COLLECTION);
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
    public List<Artwork> getCollectionImages(int collectionId, String language) throws MovieDbException {
        List<Artwork> artwork = new ArrayList<Artwork>();
        ApiUrl apiUrl = new ApiUrl(this, BASE_COLLECTION, "/images");
        apiUrl.addArgument(PARAM_ID, collectionId);

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);

            // Add all the posters to the list
            for (Artwork poster : wrapper.getPosters()) {
                poster.setArtworkType(ArtworkType.POSTER);
                artwork.add(poster);
            }

            // Add all the backdrops to the list
            for (Artwork backdrop : wrapper.getBackdrops()) {
                backdrop.setArtworkType(ArtworkType.BACKDROP);
                artwork.add(backdrop);
            }

            return artwork;
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
        ApiUrl apiUrl = new ApiUrl(this, BASE_PERSON);

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
    public List<PersonCredit> getPersonCredits(int personId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_PERSON, "/credits");

        List<PersonCredit> personCredits = new ArrayList<PersonCredit>();

        apiUrl.addArgument(PARAM_ID, personId);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperPersonCredits wrapper = mapper.readValue(webpage, WrapperPersonCredits.class);

            // Add a cast member
            for (PersonCredit cast : wrapper.getCast()) {
                cast.setPersonType(PersonType.CAST);
                personCredits.add(cast);
            }
            // Add a crew member
            for (PersonCredit crew : wrapper.getCrew()) {
                crew.setPersonType(PersonType.CREW);
                personCredits.add(crew);
            }
            return personCredits;
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
    public List<Artwork> getPersonImages(int personId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_PERSON, "/images");

        List<Artwork> personImages = new ArrayList<Artwork>();

        apiUrl.addArgument(PARAM_ID, personId);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);

            // Update the image type
            for (Artwork artwork : wrapper.getProfiles()) {
                artwork.setArtworkType(ArtworkType.PROFILE);
                personImages.add(artwork);
            }
            return personImages;
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
     * Get the latest person id.
     *
     * @throws MovieDbException
     */
    public Person getPersonLatest() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_PERSON, "/latest");
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
        ApiUrl apiUrl = new ApiUrl(this, BASE_COMPANY);

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
    public List<MovieDb> getCompanyMovies(int companyId, String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_COMPANY, "/movies");

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
            return wrapper.getResults();
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
    public List<Genre> getGenreList(String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_GENRE, "/list");
        apiUrl.addArgument(PARAM_LANGUAGE, language);

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperGenres wrapper = mapper.readValue(webpage, WrapperGenres.class);
            return wrapper.getGenres();
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
    public List<MovieDb> getGenreMovies(int genreId, String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_GENRE, "/movies");
        apiUrl.addArgument(PARAM_ID, genreId);

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
            return wrapper.getMovies();
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
    public List<MovieDb> searchMovie(String movieName, int searchYear, String language, boolean includeAdult, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_SEARCH, "movie");
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
            return wrapper.getMovies();
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
    public List<Collection> searchCollection(String query, String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_SEARCH, "collections");

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
            return wrapper.getResults();
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
    public List<Person> searchPeople(String personName, boolean includeAdult, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_SEARCH, "person");
        apiUrl.addArgument(PARAM_QUERY, personName);
        apiUrl.addArgument(PARAM_ADULT, includeAdult);

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperPerson wrapper = mapper.readValue(webpage, WrapperPerson.class);
            return wrapper.getResults();
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
    public List<MovieList> searchList(String query, String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_SEARCH, "list");

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
            return wrapper.getMovieList();
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
    public List<Company> searchCompanies(String companyName, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_SEARCH, "company");
        apiUrl.addArgument(PARAM_QUERY, companyName);

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            WrapperCompany wrapper = mapper.readValue(webpage, WrapperCompany.class);
            return wrapper.getResults();
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
    public List<Keyword> searchKeyword(String query, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_SEARCH, "keyword");

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
            return wrapper.getResults();
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
        ApiUrl apiUrl = new ApiUrl(this, BASE_LIST);
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
        ApiUrl apiUrl = new ApiUrl(this, BASE_KEYWORD);
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
    public List<KeywordMovie> getKeywordMovies(String keywordId, String language, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(this, BASE_KEYWORD, "/movies");
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
            return wrapper.getResults();
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
}
