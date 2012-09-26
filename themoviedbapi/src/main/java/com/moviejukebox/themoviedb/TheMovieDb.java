/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.themoviedb;

import com.moviejukebox.themoviedb.MovieDbException.MovieDbExceptionType;
import com.moviejukebox.themoviedb.model.*;
import com.moviejukebox.themoviedb.tools.ApiUrl;
import com.moviejukebox.themoviedb.tools.FilteringLayout;
import com.moviejukebox.themoviedb.tools.WebBrowser;
import com.moviejukebox.themoviedb.wrapper.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * The MovieDb API
 *
 * This is for version 3 of the API as specified here: http://help.themoviedb.org/kb/api/about-3
 *
 * @author stuart.boston
 */
public class TheMovieDb {

    private static final Logger LOGGER = Logger.getLogger(TheMovieDb.class);
    private String apiKey;
    private TmdbConfiguration tmdbConfig;
    /*
     * API Methods
     *
     * These are not set to static so that multiple instances of
     * the API can co-exist
     *
     * TODO: See issue 9 http://code.google.com/p/themoviedbapi/issues/detail?id=9
     */
    private static final String BASE_MOVIE = "movie/";
    private static final String BASE_PERSON = "person/";
    private static final String BASE_COMPANY = "company/";
    private static final String BASE_GENRE = "genre/";
    private static final String BASE_AUTH = "authentication/";
    private static final String BASE_COLLECTION = "collection/";
    private static final String BASE_ACCOUNT = "account/";
    private static final String BASE_SEARCH = "search/";
    // Configuration
    private final ApiUrl tmdbConfigUrl = new ApiUrl(this, "configuration");
    // Authentication
    private final ApiUrl tmdbAuthorisationToken = new ApiUrl(this, BASE_AUTH, "token/new");
    private final ApiUrl tmdbAuthorisationSession = new ApiUrl(this, BASE_AUTH, "session/new");
    // Account
    private final ApiUrl tmdbAccount = new ApiUrl(this, BASE_ACCOUNT);
    private final ApiUrl tmdbFavouriteMovies = new ApiUrl(this, BASE_ACCOUNT, "/favorite_movies");
    private final ApiUrl tmdbPostFavourite = new ApiUrl(this, BASE_ACCOUNT, "/favorite");
    private final ApiUrl tmdbRatedMovies = new ApiUrl(this, BASE_ACCOUNT, "/rated_movies");
    private final ApiUrl tmdbMovieWatchList = new ApiUrl(this, BASE_ACCOUNT, "/movie_watchlist");
    private final ApiUrl tmdbPostMovieWatchList = new ApiUrl(this, BASE_ACCOUNT, "/movie_watchlist");
    // Movies
    private final ApiUrl tmdbMovieInfo = new ApiUrl(this, BASE_MOVIE);
    private final ApiUrl tmdbMovieAltTitles = new ApiUrl(this, BASE_MOVIE, "/alternative_titles");
    private final ApiUrl tmdbMovieCasts = new ApiUrl(this, BASE_MOVIE, "/casts");
    private final ApiUrl tmdbMovieImages = new ApiUrl(this, BASE_MOVIE, "/images");
    private final ApiUrl tmdbMovieKeywords = new ApiUrl(this, BASE_MOVIE, "/keywords");
    private final ApiUrl tmdbMovieReleaseInfo = new ApiUrl(this, BASE_MOVIE, "/releases");
    private final ApiUrl tmdbMovieTrailers = new ApiUrl(this, BASE_MOVIE, "/trailers");
    private final ApiUrl tmdbMovieTranslations = new ApiUrl(this, BASE_MOVIE, "/translations");
    private final ApiUrl tmdbMovieSimilarMovies = new ApiUrl(this, BASE_MOVIE, "/similar_movies");
    private final ApiUrl tmdbLatestMovie = new ApiUrl(this, BASE_MOVIE, "/latest");
    private final ApiUrl tmdbUpcoming = new ApiUrl(this, BASE_MOVIE, "upcoming");
    private final ApiUrl tmdbNowPlaying = new ApiUrl(this, BASE_MOVIE, "now-playing");
    private final ApiUrl tmdbPopularMovieList = new ApiUrl(this, BASE_MOVIE, "popular");
    private final ApiUrl tmdbTopRatedMovies = new ApiUrl(this, BASE_MOVIE, "top-rated");
    private final ApiUrl tmdbPostRating = new ApiUrl(this, BASE_MOVIE, "/rating");
    // Collections
    private final ApiUrl tmdbCollectionInfo = new ApiUrl(this, BASE_COLLECTION);
    private final ApiUrl tmdbCollectionImages = new ApiUrl(this, BASE_COLLECTION, "/images");
    // People
    private final ApiUrl tmdbPersonInfo = new ApiUrl(this, BASE_PERSON);
    private final ApiUrl tmdbPersonCredits = new ApiUrl(this, BASE_PERSON, "/credits");
    private final ApiUrl tmdbPersonImages = new ApiUrl(this, BASE_PERSON, "/images");
    // Companies
    private final ApiUrl tmdbCompanyInfo = new ApiUrl(this, BASE_COMPANY);
    private final ApiUrl tmdbCompanyMovies = new ApiUrl(this, BASE_COMPANY, "/movies");
    // Genres
    private final ApiUrl tmdbGenreList = new ApiUrl(this, BASE_GENRE, "/list");
    private final ApiUrl tmdbGenreMovies = new ApiUrl(this, BASE_GENRE, "/movies");
    // Search
    private final ApiUrl tmdbSearchMovie = new ApiUrl(this, BASE_SEARCH, "movie");
    private final ApiUrl tmdbSearchPeople = new ApiUrl(this, BASE_SEARCH, "person");
    private final ApiUrl tmdbSearchCompanies = new ApiUrl(this, BASE_SEARCH, "company");

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
    public TheMovieDb(String apiKey) throws MovieDbException {
        this.apiKey = apiKey;
        URL configUrl = tmdbConfigUrl.buildUrl();
        String webpage = WebBrowser.request(configUrl);
        FilteringLayout.addReplacementString(apiKey);

        try {
            WrapperConfig wc = mapper.readValue(webpage, WrapperConfig.class);
            tmdbConfig = wc.getTmdbConfiguration();
        } catch (IOException ex) {
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, "Failed to read configuration", ex);
        }
    }

    /**
     * Output the API version information to the debug log
     */
    public static void showVersion() {
        String apiTitle = TheMovieDb.class.getPackage().getSpecificationTitle();

        if (StringUtils.isNotBlank(apiTitle)) {
            String apiVersion = TheMovieDb.class.getPackage().getSpecificationVersion();
            String apiRevision = TheMovieDb.class.getPackage().getImplementationVersion();
            StringBuilder sv = new StringBuilder();
            sv.append(apiTitle).append(" ");
            sv.append(apiVersion).append(" r");
            sv.append(apiRevision);
            LOGGER.debug(sv.toString());
        } else {
            LOGGER.debug("API-TheMovieDb version/revision information not available");
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
     * @param year The year of the movie to compare
     * @return True if there is a match, False otherwise.
     */
    public static boolean compareMovies(MovieDb moviedb, String title, String year) {
        if ((moviedb == null) || (StringUtils.isBlank(title))) {
            return false;
        }

        if (StringUtils.isNotBlank(year) && !year.equalsIgnoreCase("UNKNOWN") && StringUtils.isNotBlank(moviedb.getReleaseDate())) {
            // Compare with year
            String movieYear = moviedb.getReleaseDate().substring(0, 4);
            if (movieYear.equals(year)) {
                if (moviedb.getOriginalTitle().equalsIgnoreCase(title)) {
                    return true;
                }

                if (moviedb.getTitle().equalsIgnoreCase(title)) {
                    return true;
                }
            }
        }

        // Compare without year
        if (moviedb.getOriginalTitle().equalsIgnoreCase(title)) {
            return true;
        }

        if (moviedb.getTitle().equalsIgnoreCase(title)) {
            return true;
        }

        return false;
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
            LOGGER.warn("Failed to create image URL: " + ex.getMessage());
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
     * @return
     * @throws MovieDbException
     */
    public TokenAuthorisation getAuthorisationToken() throws MovieDbException {
        URL url = tmdbAuthorisationToken.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, TokenAuthorisation.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get Authorisation Token: " + ex.getMessage());
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
        if (!token.getSuccess()) {
            LOGGER.warn("Authorisation token was not successful!");
            throw new MovieDbException(MovieDbExceptionType.AUTHORISATION_FAILURE, "Authorisation token was not successful!");
        }

        tmdbAuthorisationSession.addArgument(ApiUrl.PARAM_TOKEN, token.getRequestToken());
        URL url = tmdbAuthorisationSession.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, TokenSession.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get Session Token: " + ex.getMessage());
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
     * @return
     * @throws MovieDbException
     */
    public MovieDb getMovieInfo(int movieId, String language) throws MovieDbException {

        tmdbMovieInfo.addArgument(ApiUrl.PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            tmdbMovieInfo.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        URL url = tmdbMovieInfo.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            return mapper.readValue(webpage, MovieDb.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie info: " + ex.getMessage());
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
     * @return
     * @throws MovieDbException
     */
    public MovieDb getMovieInfoImdb(String imdbId, String language) throws MovieDbException {

        tmdbMovieInfo.addArgument(ApiUrl.PARAM_ID, imdbId);

        if (StringUtils.isNotBlank(language)) {
            tmdbMovieInfo.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        URL url = tmdbMovieInfo.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            return mapper.readValue(webpage, MovieDb.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie info: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the alternative titles we have for a particular movie.
     *
     * @param movieId
     * @param country
     * @return
     * @throws MovieDbException
     */
    public List<AlternativeTitle> getMovieAlternativeTitles(int movieId, String country) throws MovieDbException {
        tmdbMovieAltTitles.addArgument(ApiUrl.PARAM_ID, movieId);

        if (StringUtils.isNotBlank(country)) {
            tmdbMovieAltTitles.addArgument(ApiUrl.PARAM_COUNTRY, country);
        }

        URL url = tmdbMovieAltTitles.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            WrapperAlternativeTitles wrapper = mapper.readValue(webpage, WrapperAlternativeTitles.class);
            return wrapper.getTitles();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie alternative titles: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the cast information for a specific movie id.
     *
     * TODO: Add a function to enrich the data with the people methods
     *
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public List<Person> getMovieCasts(int movieId) throws MovieDbException {
        List<Person> people = new ArrayList<Person>();

        tmdbMovieCasts.addArgument(ApiUrl.PARAM_ID, movieId);
        URL url = tmdbMovieCasts.buildUrl();
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
            LOGGER.warn("Failed to get movie casts: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method should be used when you’re wanting to retrieve all of the images for a particular movie.
     *
     * @param movieId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public List<Artwork> getMovieImages(int movieId, String language) throws MovieDbException {

        tmdbMovieImages.addArgument(ApiUrl.PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            tmdbMovieImages.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        List<Artwork> artwork = new ArrayList<Artwork>();
        URL url = tmdbMovieImages.buildUrl();
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
            LOGGER.warn("Failed to get movie images: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the keywords that have been added to a particular movie.
     *
     * Currently, only English keywords exist.
     *
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public List<Keyword> getMovieKeywords(int movieId) throws MovieDbException {

        tmdbMovieKeywords.addArgument(ApiUrl.PARAM_ID, movieId);

        URL url = tmdbMovieKeywords.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovieKeywords wrapper = mapper.readValue(webpage, WrapperMovieKeywords.class);
            return wrapper.getKeywords();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie keywords: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the release and certification data we have for a specific movie.
     *
     * @param movieId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public List<ReleaseInfo> getMovieReleaseInfo(int movieId, String language) throws MovieDbException {

        tmdbMovieReleaseInfo.addArgument(ApiUrl.PARAM_ID, movieId);
        tmdbMovieReleaseInfo.addArgument(ApiUrl.PARAM_LANGUAGE, language);

        URL url = tmdbMovieReleaseInfo.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperReleaseInfo wrapper = mapper.readValue(webpage, WrapperReleaseInfo.class);
            return wrapper.getCountries();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie release information: " + ex.getMessage());
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
     * @return
     * @throws MovieDbException
     */
    public List<Trailer> getMovieTrailers(int movieId, String language) throws MovieDbException {

        List<Trailer> trailers = new ArrayList<Trailer>();

        tmdbMovieTrailers.addArgument(ApiUrl.PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            tmdbMovieTrailers.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        URL url = tmdbMovieTrailers.buildUrl();
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
            LOGGER.warn("Failed to get movie trailers: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve a list of the available translations for a specific movie.
     *
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public List<Translation> getMovieTranslations(int movieId) throws MovieDbException {

        tmdbMovieTranslations.addArgument(ApiUrl.PARAM_ID, movieId);
        URL url = tmdbMovieTranslations.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperTranslations wrapper = mapper.readValue(webpage, WrapperTranslations.class);
            return wrapper.getTranslations();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie tranlations: " + ex.getMessage());
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
     * @param allResults
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> getSimilarMovies(int movieId, String language, int page) throws MovieDbException {
        tmdbMovieSimilarMovies.addArgument(ApiUrl.PARAM_ID, movieId);

        if (StringUtils.isNotBlank(language)) {
            tmdbMovieSimilarMovies.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            tmdbMovieSimilarMovies.addArgument(ApiUrl.PARAM_PAGE, page);
        }

        URL url = tmdbMovieSimilarMovies.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            return wrapper.getMovies();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get similar movies: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve the newest movie that was added to TMDb.
     *
     * @return
     */
    public MovieDb getLatestMovie() throws MovieDbException {

        URL url = tmdbLatestMovie.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, MovieDb.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get latest movie: " + ex.getMessage());
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
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> getUpcoming(String language, int page) throws MovieDbException {
        if (StringUtils.isNotBlank(language)) {
            tmdbUpcoming.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            tmdbUpcoming.addArgument(ApiUrl.PARAM_PAGE, page);
        }

        URL url = tmdbUpcoming.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            return wrapper.getMovies();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get upcoming movies: " + ex.getMessage());
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
     * @param allResults
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> getNowPlayingMovies(String language, int page) throws MovieDbException {

        if (StringUtils.isNotBlank(language)) {
            tmdbNowPlaying.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            tmdbNowPlaying.addArgument(ApiUrl.PARAM_PAGE, page);
        }

        URL url = tmdbNowPlaying.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            return wrapper.getMovies();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get now playing movies: " + ex.getMessage());
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
     * @param allResults
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> getPopularMovieList(String language, int page) throws MovieDbException {
        if (StringUtils.isNotBlank(language)) {
            tmdbPopularMovieList.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            tmdbPopularMovieList.addArgument(ApiUrl.PARAM_PAGE, page);
        }

        URL url = tmdbPopularMovieList.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            return wrapper.getMovies();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get popular movie list: " + ex.getMessage());
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
     * @param allResults
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> getTopRatedMovies(String language, int page) throws MovieDbException {
        if (StringUtils.isNotBlank(language)) {
            tmdbTopRatedMovies.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            tmdbTopRatedMovies.addArgument(ApiUrl.PARAM_PAGE, page);
        }

        URL url = tmdbTopRatedMovies.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            return wrapper.getMovies();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get top rated movies: " + ex.getMessage());
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
     * @return
     * @throws MovieDbException
     */
    public boolean postMovieRating(String sessionId, String rating) throws MovieDbException {

        tmdbPostRating.addArgument(ApiUrl.PARAM_SESSION, sessionId);
        tmdbPostRating.addArgument(ApiUrl.PARAM_VALUE, rating);

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
     * @return
     * @throws MovieDbException
     */
    public CollectionInfo getCollectionInfo(int collectionId, String language) throws MovieDbException {

        tmdbCollectionInfo.addArgument(ApiUrl.PARAM_ID, collectionId);

        if (StringUtils.isNotBlank(language)) {
            tmdbCollectionInfo.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        URL url = tmdbCollectionInfo.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, CollectionInfo.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get collection information: " + ex.getMessage());
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
    public List<Artwork> getCollectionImages(int collectionId, String language) throws MovieDbException {
        List<Artwork> artwork = new ArrayList<Artwork>();

        tmdbCollectionImages.addArgument(ApiUrl.PARAM_ID, collectionId);

        if (StringUtils.isNotBlank(language)) {
            tmdbCollectionImages.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        URL url = tmdbCollectionImages.buildUrl();
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
            LOGGER.warn("Failed to get collection images: " + ex.getMessage());
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
     * @return
     * @throws MovieDbException
     */
    public Person getPersonInfo(int personId) throws MovieDbException {

        tmdbPersonInfo.addArgument(ApiUrl.PARAM_ID, personId);

        URL url = tmdbPersonInfo.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, Person.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie info: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the cast & crew information for the person.
     *
     * It will return the single highest rated poster for each movie record.
     *
     * @param personId
     * @return
     * @throws MovieDbException
     */
    public List<PersonCredit> getPersonCredits(int personId) throws MovieDbException {

        List<PersonCredit> personCredits = new ArrayList<PersonCredit>();

        tmdbPersonCredits.addArgument(ApiUrl.PARAM_ID, personId);

        URL url = tmdbPersonCredits.buildUrl();
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
            LOGGER.warn("Failed to get person credits: " + ex.getMessage());
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
    public List<Artwork> getPersonImages(int personId) throws MovieDbException {

        List<Artwork> personImages = new ArrayList<Artwork>();

        tmdbPersonImages.addArgument(ApiUrl.PARAM_ID, personId);

        URL url = tmdbPersonImages.buildUrl();
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
            LOGGER.warn("Failed to get person images: " + ex.getMessage());
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
     * @return
     * @throws MovieDbException
     */
    public Company getCompanyInfo(int companyId) throws MovieDbException {

        tmdbCompanyInfo.addArgument(ApiUrl.PARAM_ID, companyId);

        URL url = tmdbCompanyInfo.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            return mapper.readValue(webpage, Company.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get company information: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve the movies associated with a company.
     *
     * These movies are returned in order of most recently released to oldest. The default response will return 20
     * movies per page.
     *
     * TODO: Implement more than 20 movies
     *
     * @param companyId
     * @param language
     * @param allResults
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> getCompanyMovies(int companyId, String language, int page) throws MovieDbException {

        tmdbCompanyMovies.addArgument(ApiUrl.PARAM_ID, companyId);

        if (StringUtils.isNotBlank(language)) {
            tmdbCompanyMovies.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            tmdbCompanyMovies.addArgument(ApiUrl.PARAM_PAGE, page);
        }

        URL url = tmdbCompanyMovies.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperCompanyMovies wrapper = mapper.readValue(webpage, WrapperCompanyMovies.class);
            return wrapper.getResults();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get company movies: " + ex.getMessage());
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
     * @return
     */
    public List<Genre> getGenreList(String language) throws MovieDbException {
        tmdbGenreList.addArgument(ApiUrl.PARAM_LANGUAGE, language);

        URL url = tmdbGenreList.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperGenres wrapper = mapper.readValue(webpage, WrapperGenres.class);
            return wrapper.getGenres();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get genre list: " + ex.getMessage());
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
     * @param allResults
     * @return
     */
    public List<MovieDb> getGenreMovies(int genreId, String language, int page) throws MovieDbException {

        tmdbGenreMovies.addArgument(ApiUrl.PARAM_ID, genreId);

        if (StringUtils.isNotBlank(language)) {
            tmdbGenreMovies.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            tmdbGenreMovies.addArgument(ApiUrl.PARAM_PAGE, page);
        }

        URL url = tmdbGenreMovies.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            return wrapper.getMovies();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get genre movie list: " + ex.getMessage());
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
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> searchMovie(String movieName, int searchYear, String language, boolean includeAdult, int page) throws MovieDbException {
        if (StringUtils.isNotBlank(movieName)) {
            tmdbSearchMovie.addArgument(ApiUrl.PARAM_QUERY, movieName);
        }

        if (searchYear > 0) {
            tmdbSearchMovie.addArgument(ApiUrl.PARAM_YEAR, Integer.toString(searchYear));
        }

        if (StringUtils.isNotBlank(language)) {
            tmdbSearchMovie.addArgument(ApiUrl.PARAM_LANGUAGE, language);
        }

        tmdbSearchMovie.addArgument(ApiUrl.PARAM_ADULT, Boolean.toString(includeAdult));

        if (page > 0) {
            tmdbSearchMovie.addArgument(ApiUrl.PARAM_PAGE, Integer.toString(page));
        }

        URL url = tmdbSearchMovie.buildUrl();
        LOGGER.info(url.toString());

        String webpage = WebBrowser.request(url);
        try {
            WrapperMovie wrapper = mapper.readValue(webpage, WrapperMovie.class);
            return wrapper.getMovies();
        } catch (IOException ex) {
            LOGGER.warn("Failed to find movie: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }

    /**
     * Search Companies.
     *
     * You can use this method to search for production companies that are part of TMDb. The company IDs will map to
     * those returned on movie calls.
     *
     * http://help.themoviedb.org/kb/api/search-companies
     *
     * @param companyName
     * @param page
     * @return
     * @throws MovieDbException
     */
    public List<Company> searchCompanies(String companyName, int page) throws MovieDbException {
        tmdbSearchCompanies.addArgument(ApiUrl.PARAM_QUERY, companyName);

        if (page > 0) {
            tmdbSearchCompanies.addArgument(ApiUrl.PARAM_PAGE, page);
        }

        URL url = tmdbSearchCompanies.buildUrl();
        String webpage = WebBrowser.request(url);
        try {
            WrapperCompany wrapper = mapper.readValue(webpage, WrapperCompany.class);
            return wrapper.getResults();
        } catch (IOException ex) {
            LOGGER.warn("Failed to find company: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This is a good starting point to start finding people on TMDb.
     *
     * The idea is to be a quick and light method so you can iterate through people quickly.
     *
     * TODO: Fix allResults
     *
     * @param personName
     * @param allResults
     * @return
     * @throws MovieDbException
     */
    public List<Person> searchPeople(String personName, boolean includeAdult, int page) throws MovieDbException {
        tmdbSearchPeople.addArgument(ApiUrl.PARAM_QUERY, personName);
        tmdbSearchPeople.addArgument(ApiUrl.PARAM_ADULT, includeAdult);

        if (page > 0) {
            tmdbSearchPeople.addArgument(ApiUrl.PARAM_PAGE, page);
        }

        URL url = tmdbSearchPeople.buildUrl();
        String webpage = WebBrowser.request(url);

        try {
            WrapperPerson wrapper = mapper.readValue(webpage, WrapperPerson.class);
            return wrapper.getResults();
        } catch (IOException ex) {
            LOGGER.warn("Failed to find person: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    //</editor-fold>
    //
    /*
     * Deprecated Functions.
     *
     * Will be removed in next version: 3.3
     */
    //<editor-fold defaultstate="collapsed" desc="Deprecated Functions">
    /**
     * This interface will be deprecated in the next version
     *
     * @param movieName
     * @param language
     * @param allResults
     * @return
     * @throws MovieDbException
     * @deprecated
     */
    @Deprecated
    public List<MovieDb> searchMovie(String movieName, String language, boolean allResults) throws MovieDbException {
        return searchMovie(movieName, 0, language, allResults, 0);
    }

    /**
     * This interface will be deprecated in the next version
     *
     * @param companyName
     * @param language
     * @param allResults
     * @return
     * @throws MovieDbException
     * @deprecated
     */
    @Deprecated
    public List<Company> searchCompanies(String companyName, String language, boolean allResults) throws MovieDbException {
        return searchCompanies(companyName, 0);
    }

    /**
     * This interface will be deprecated in the next version
     *
     * @param personName
     * @param allResults
     * @return
     * @throws MovieDbException
     * @deprecated
     */
    @Deprecated
    public List<Person> searchPeople(String personName, boolean allResults) throws MovieDbException {
        return searchPeople(personName, allResults, 0);
    }

    /**
     * This interface will be deprecated in the next version
     *
     * @param movieId
     * @param language
     * @param allResults
     * @return
     * @throws MovieDbException
     * @deprecated
     */
    @Deprecated
    public List<MovieDb> getSimilarMovies(int movieId, String language, boolean allResults) throws MovieDbException {
        return getSimilarMovies(movieId, language, 0);
    }

    /**
     * This interface will be deprecated in the next version
     *
     * @param language
     * @return
     * @throws MovieDbException
     * @deprecated
     */
    @Deprecated
    public List<MovieDb> getUpcoming(String language) throws MovieDbException {
        return getUpcoming(language, 0);
    }

    /**
     * This interface will be deprecated in the next version
     *
     * @param language
     * @param allResults
     * @return
     * @throws MovieDbException
     * @deprecated
     */
    @Deprecated
    public List<MovieDb> getNowPlayingMovies(String language, boolean allResults) throws MovieDbException {
        return getNowPlayingMovies(language, 0);
    }

    /**
     * This interface will be deprecated in the next version
     *
     * @param language
     * @param allResults
     * @return
     * @throws MovieDbException
     * @deprecated
     */
    @Deprecated
    public List<MovieDb> getPopularMovieList(String language, boolean allResults) throws MovieDbException {
        return getPopularMovieList(language, 0);
    }

    /**
     * This interface will be deprecated in the next version
     *
     * @param language
     * @param allResults
     * @return
     * @throws MovieDbException
     * @deprecated
     */
    @Deprecated
    public List<MovieDb> getTopRatedMovies(String language, boolean allResults) throws MovieDbException {
        return getTopRatedMovies(language, 0);
    }

    /**
     * This interface will be deprecated in the next version
     *
     * @param companyId
     * @param language
     * @param allResults
     * @return
     * @throws MovieDbException
     * @deprecated
     */
    @Deprecated
    public List<MovieDb> getCompanyMovies(int companyId, String language, boolean allResults) throws MovieDbException {
        return getCompanyMovies(companyId, language, 0);
    }

    /**
     * This interface will be deprecated in the next version
     *
     * @param genreId
     * @param language
     * @param allResults
     * @return
     * @throws MovieDbException
     * @deprecated
     */
    @Deprecated
    public List<MovieDb> getGenreMovies(int genreId, String language, boolean allResults) throws MovieDbException {
        return getGenreMovies(genreId, language, 0);
    }
    //</editor-fold>
}
