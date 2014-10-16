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
import com.omertron.themoviedbapi.model.Account;
import com.omertron.themoviedbapi.model.AlternativeTitle;
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.Certification;
import com.omertron.themoviedbapi.model.ChangedItem;
import com.omertron.themoviedbapi.model.ChangedMovie;
import com.omertron.themoviedbapi.model.Collection;
import com.omertron.themoviedbapi.model.CollectionInfo;
import com.omertron.themoviedbapi.model.Company;
import com.omertron.themoviedbapi.model.Configuration;
import com.omertron.themoviedbapi.model.ExternalIds;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.JobDepartment;
import com.omertron.themoviedbapi.model.Keyword;
import com.omertron.themoviedbapi.model.ReleaseInfo;
import com.omertron.themoviedbapi.model.Review;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.StatusCodeList;
import com.omertron.themoviedbapi.model.TokenAuthorisation;
import com.omertron.themoviedbapi.model.TokenSession;
import com.omertron.themoviedbapi.model.Trailer;
import com.omertron.themoviedbapi.model.Translation;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.model.movie.MovieDbBasic;
import com.omertron.themoviedbapi.model.movie.MovieDbList;
import com.omertron.themoviedbapi.model.movie.MovieList;
import com.omertron.themoviedbapi.model.movie.MovieState;
import com.omertron.themoviedbapi.model.person.NewPersonCredits;
import com.omertron.themoviedbapi.model.person.Person;
import com.omertron.themoviedbapi.model.person.PersonBasic;
import com.omertron.themoviedbapi.model.person.PersonCredits;
import com.omertron.themoviedbapi.model.person.PersonMovieOld;
import com.omertron.themoviedbapi.model.tv.Network;
import com.omertron.themoviedbapi.model.tv.TVCredits;
import com.omertron.themoviedbapi.model.tv.TVEpisode;
import com.omertron.themoviedbapi.model.tv.TVSeason;
import com.omertron.themoviedbapi.model.tv.TVSeries;
import com.omertron.themoviedbapi.model.tv.TVSeriesBasic;
import com.omertron.themoviedbapi.model.type.SearchType;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.results.TmdbResultsMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.yamj.api.common.http.CommonHttpClient;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

/**
 * The MovieDb API
 * <p>
 * This is for version 3 of the API as specified here: http://help.themoviedb.org/kb/api/about-3
 *
 * @author stuart.boston
 */
public class TheMovieDbApi {

    private CommonHttpClient httpClient;
    private Configuration config = null;
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
        // Use a default pooling client if one is not provided
        this(apiKey, new DefaultPoolingHttpClient());
    }

    /**
     * API for The Movie Db.
     *
     * @param apiKey
     * @param httpClient The httpClient to use for web requests.
     * @throws MovieDbException
     */
    public TheMovieDbApi(String apiKey, CommonHttpClient httpClient) throws MovieDbException {
        this.httpClient = httpClient;
        initialise(apiKey, httpClient);
    }

    /**
     * Initialise the sub-classes once the API key and http client are known
     *
     * @param apiKey
     * @param httpClient
     */
    private void initialise(String apiKey, CommonHttpClient httpClient) {
        tmdbAccount = new TmdbAccount(apiKey, httpClient);
        tmdbAuth = new TmdbAuthentication(apiKey, httpClient);
        tmdbCertifications = new TmdbCertifications(apiKey, httpClient);
        tmdbChanges = new TmdbChanges(apiKey, httpClient);
        tmdbCollections = new TmdbCollections(apiKey, httpClient);
        tmdbCompany = new TmdbCompanies(apiKey, httpClient);
        tmdbConfiguration = new TmdbConfiguration(apiKey, httpClient);
        tmdbCredits = new TmdbCredits(apiKey, httpClient);
        tmdbDiscover = new TmdbDiscover(apiKey, httpClient);
        tmdbFind = new TmdbFind(apiKey,httpClient);
        tmdbGenre = new TmdbGenres(apiKey, httpClient);
        tmdbJobs = new TmdbJobs(apiKey, httpClient);
        tmdbKeyword = new TmdbKeywords(apiKey, httpClient);
        tmdbList = new TmdbLists(apiKey, httpClient);
        tmdbMovies = new TmdbMovies(apiKey, httpClient);
        tmdbNetworks = new TmdbNetworks(apiKey, httpClient);
        tmdbPeople = new TmdbPeople(apiKey, httpClient);
        tmdbReviews = new TmdbReviews(apiKey, httpClient);
        tmdbSearch = new TmdbSearch(apiKey, httpClient);
        tmdbTv = new TmdbTV(apiKey, httpClient);
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
     *
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public Configuration getConfiguration() throws MovieDbException {
        if (config == null) {
            config = tmdbConfiguration.getConfig();
        }
        return config;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Account Functions">
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
     * @param accountID
     * @return The lists
     * @throws MovieDbException
     */
    public List<MovieDbList> getUserLists(String sessionId, int accountID) throws MovieDbException {
        return tmdbAccount.getUserLists(sessionId, accountID);
    }

    /**
     * Get the list of favorite movies for an account.
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
     * Add or remove a movie to an accounts favorite list.
     *
     * @param sessionId
     * @param accountId
     * @param movieId
     * @param isFavorite
     * @return
     * @throws MovieDbException
     */
    public StatusCode changeFavoriteStatus(String sessionId, int accountId, Integer movieId, boolean isFavorite) throws MovieDbException {
        return tmdbAccount.changeFavoriteStatus(sessionId, accountId, movieId, isFavorite);
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
     * Get the list of movies on an accounts watchlist.
     *
     * @param sessionId
     * @param accountId
     * @return The watchlist of the user
     * @throws MovieDbException
     */
    public List<MovieDb> getWatchList(String sessionId, int accountId) throws MovieDbException {
        return tmdbAccount.getWatchList(sessionId, accountId);
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
        return tmdbAccount.modifyWatchList(sessionId, accountId, movieId, true);
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
        return tmdbAccount.modifyWatchList(sessionId, accountId, movieId, false);
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
    
    //<editor-fold defaultstate="collapsed" desc="Certification Functions">
    /**
     * You can use this method to retrieve the list of movie certification used 
     * on TMDb.
     *
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsMap<String, List<Certification>> getMovieCertificationList() throws MovieDbException {
        return tmdbCertifications.getMoviesCertification();
    }

    /**
     * You can use this method to retrieve the list of TV certification used 
     * on TMDb.
     *
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsMap<String, List<Certification>> getTvCertificationList() throws MovieDbException {
        return tmdbCertifications.getTvCertification();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Changes Functions">
    /**
     * Get a list of movie IDs that have been edited.
     * <p>
     * By default we show the last 24 hours and only 100 items per page. <br/>
     * The maximum number of days that can be returned in a single request is 14. <br/>
     * You can then use the movie changes API to get the actual data that has been changed. <br/>
     * Please note that the change log system to support this was changed on October 5, 2012 and will only show movies that have
     * been edited since.
     *
     * @param page
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return List of changed movie
     * @throws MovieDbException
     */
    public TmdbResultsList<ChangedMovie> getMovieChangesList(int page, String startDate, String endDate) throws MovieDbException {
        return tmdbChanges.getMovieChangesList(page, startDate, endDate);
    }

    /**
     * Get a list of people IDs that have been edited.
     * <p>
     * By default we show the last 24 hours and only 100 items per page. <br/>
     * The maximum number of days that can be returned in a single request is 14. <br/>
     * You can then use the person changes API to get the actual data that has been changed. <br/>
     * Please note that the change log system to support this was changed on October 5, 2012 and will only show people that have
     * been edited since.
     *
     * @param page
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return List of changed movie
     * @throws MovieDbException
     */
    public String getPersonChangesList(int page, String startDate, String endDate) throws MovieDbException {
        return tmdbChanges.getPersonChangesList(page, startDate, endDate);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Collections Functions">
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
        return tmdbCollections.getCollectionInfo(collectionId, language);
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
        return tmdbCollections.getCollectionImages(collectionId, language);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Companies Functions">
    /**
     * This method is used to retrieve the basic information about a production company on TMDb.
     *
     * @param companyId
     * @return
     * @throws MovieDbException
     */
    public Company getCompanyInfo(int companyId) throws MovieDbException {
        return tmdbCompany.getCompanyInfo(companyId);
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
        return tmdbCompany.getCompanyMovies(companyId, language, page);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Credit Functions">
    /**
     * Get the detailed information about a particular credit record.
     * <p>
     * This is currently only supported with the new credit model found in TV. <br/>
     * These IDs can be found from any TV credit response as well as the TV_credits and combined_credits methods for people. <br/>
     * The episodes object returns a list of episodes and are generally going to be guest stars. <br/>
     * The season array will return a list of season numbers. <br/>
     * Season credits are credits that were marked with the "add to every season" option in the editing interface and are assumed to
     * be "season regulars".
     *
     * @param creditId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public PersonCredits getCreditInfo(String creditId, String language) throws MovieDbException {
        return tmdbCredits.getCreditInfo(creditId, language);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Discover Functions">
    /**
     * Discover movies by different types of data like average rating, number of votes, genres and certifications.
     * <p>
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
    public TmdbResultsList<MovieDb> getDiscoverMovie(int page, String language, String sortBy, boolean includeAdult, int year,
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

        return tmdbDiscover.getDiscoverMovie(discover);
    }

    /**
     * Discover movies by different types of data like average rating, number of votes, genres and certifications.
     *
     * @param discover A discover object containing the search criteria required
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getDiscoverMovie(Discover discover) throws MovieDbException {
        return tmdbDiscover.getDiscoverMovie(discover);
    }

    /**
     * Discover TV shows by different types of data like average rating, number of votes, genres, the network they aired on and air
     * dates.
     * <p>
     * You can alternatively create a "discover" object and pass it to this method to cut out the requirement for all of these
     * parameters.
     *
     * @param page Minimum value is 1, expected value is an integer.
     * @param language ISO 639-1 code.
     * @param sortBy Available options are vote_average.desc, vote_average.asc, release_date.desc, release_date.asc,
     * popularity.desc, popularity.asc
     * @param firstAirDateYear Filter the results release dates to matches that include this value. Expected value is a year
     * @param voteCountGte Only include movies that are equal to, or have a vote count higher than this value
     * @param voteAverageGte Only include movies that are equal to, or have a higher average rating than this value
     * @param withGenres Only include movies with the specified genres. Expected value is an integer (the id of a genre). Multiple
     * values can be specified. Comma separated indicates an 'AND' query, while a pipe (|) separated value indicates an 'OR'.
     * @param withNetworks Filter TV shows to include a specific network. Expected value is an integer (the id of a network). They
     * can be comma separated to indicate an 'AND' query.
     * @param firstAirDateGte The minimum release to include. Expected format is YYYY-MM-DD.
     * @param firstAirDateLte The maximum release to include. Expected format is YYYY-MM-DD.
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<TVSeriesBasic> getDiscoverTv(int page, String language, String sortBy, int firstAirDateYear, int voteCountGte,
            float voteAverageGte, String withGenres, String withNetworks, String firstAirDateGte, String firstAirDateLte) throws MovieDbException {

        Discover discover = new Discover();
        discover.page(page)
                .language(language)
                .sortBy(sortBy)
                .firstAirDateYear(firstAirDateYear)
                .voteCountGte(voteCountGte)
                .voteAverageGte(voteAverageGte)
                .withGenres(withGenres)
                .withNetworks(withNetworks)
                .firstAirDateGte(firstAirDateGte)
                .firstAirDateLte(firstAirDateLte);

        return tmdbDiscover.getDiscoverTv(discover);
    }

    /**
     * Discover TV shows by different types of data like average rating, number of votes, genres, the network they aired on and air
     * dates.
     *
     * @param discover A discover object containing the search criteria required
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<TVSeriesBasic> getDiscoverTv(Discover discover) throws MovieDbException {
        return tmdbDiscover.getDiscoverTv(discover);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Find Functions">
    /**
     * You can use this method to retrieve the list of movie using external ids.
     *
     * @param externalId the external id of the movie.
     * @param externalSource one of {@link TmdbFind.ExternalSource}.
     * @param language the language
     * @return
     * @throws MovieDbException
     */
    public List<MovieDb> findMoviesFromExternalId(String externalId, TmdbFind.ExternalSource externalSource, String language) throws MovieDbException {
        return tmdbFind.find(externalId, externalSource, language).getMovieResults();
    }

    /**
     * You can use this method to retrieve the list of tv series using external ids.
     *
     * @param externalId the external id of the movie.
     * @param externalSource one of {@link TmdbFind.ExternalSource}.
     * @param language the language
     * @return
     * @throws MovieDbException
     */
    public List<TVSeries> findTvSeriesFromExternalId(String externalId, TmdbFind.ExternalSource externalSource, String language) throws MovieDbException {
        return tmdbFind.find(externalId, externalSource, language).getTvResults();
    }
    
    /**
     * You can use this method to retrieve the list of persons using external ids.
     *
     * @param externalId the external id of the movie.
     * @param externalSource one of {@link TmdbFind.ExternalSource}.
     * @param language the language
     * @return
     * @throws MovieDbException
     */
    public List<Person> findPersonsFromExternalId(String externalId, TmdbFind.ExternalSource externalSource, String language) throws MovieDbException {
        return tmdbFind.find(externalId, externalSource, language).getPersonResults();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Genres Functions">
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
        return tmdbGenre.getGenreList(language);
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
        return tmdbGenre.getGenreMovies(genreId, language, page, includeAllMovies);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Jobs Functions">
    /**
     * Get a list of valid jobs.
     *
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<JobDepartment> getJobs() throws MovieDbException {
        return tmdbJobs.getJobs();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Keywords Functions">
    /**
     * Get the basic information for a specific keyword id.
     *
     * @param keywordId
     * @return
     * @throws MovieDbException
     */
    public Keyword getKeyword(String keywordId) throws MovieDbException {
        return tmdbKeyword.getKeyword(keywordId);
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
    public TmdbResultsList<MovieDbBasic> getKeywordMovies(String keywordId, String language, int page) throws MovieDbException {
        return tmdbKeyword.getKeywordMovies(keywordId, language, page);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Lists Functions">
    /**
     * Get a list by its ID
     *
     * @param listId
     * @return The list and its items
     * @throws MovieDbException
     */
    public MovieDbList getList(String listId) throws MovieDbException {
        return tmdbList.getList(listId);
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
        StatusCodeList scl = tmdbList.createList(sessionId, name, description);
        if (scl != null) {
            return scl.getListId();
        } else {
            return null;
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
        return tmdbList.isMovieOnList(listId, movieId);
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
        return tmdbList.addMovieToList(sessionId, listId, movieId);
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
        return tmdbList.removeMovieFromList(sessionId, listId, movieId);
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
        return tmdbList.deleteMovieList(sessionId, listId);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Movies Functions">
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
        return tmdbMovies.getMovieInfo(movieId, language, appendToResponse);
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
        return tmdbMovies.getMovieInfoImdb(imdbId, language, appendToResponse);
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
        return tmdbMovies.getMovieAlternativeTitles(movieId, country, appendToResponse);
    }

    /**
     * Get the cast and crew information for a specific movie id.
     *
     * TODO: Add a function to enrich the data with the people methods
     *
     * @param movieId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<PersonMovieOld> getMovieCredits(int movieId, String... appendToResponse) throws MovieDbException {
        return tmdbMovies.getMovieCredits(movieId, appendToResponse);
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
        return tmdbMovies.getMovieImages(movieId, language, appendToResponse);
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
        return tmdbMovies.getMovieKeywords(movieId, appendToResponse);
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
        return tmdbMovies.getMovieReleaseInfo(movieId, language, appendToResponse);
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
        return tmdbMovies.getMovieTrailers(movieId, language, appendToResponse);
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
        return tmdbMovies.getMovieTranslations(movieId, appendToResponse);
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
        return tmdbMovies.getSimilarMovies(movieId, language, page, appendToResponse);
    }

    /**
     * Get the reviews for a particular movie id.
     *
     * @param movieId
     * @param language
     * @param page
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Review> getReviews(int movieId, String language, int page, String... appendToResponse) throws MovieDbException {
        return tmdbMovies.getReviews(movieId, language, page, appendToResponse);
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
        return tmdbMovies.getMovieLists(movieId, language, page, appendToResponse);
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
        return tmdbMovies.getMovieChanges(movieId, startDate, endDate);
    }

    /**
     * This method is used to retrieve the newest movie that was added to TMDb.
     *
     * @return
     * @throws MovieDbException
     */
    public MovieDb getLatestMovie() throws MovieDbException {
        return tmdbMovies.getLatestMovie();
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
        return tmdbMovies.getUpcoming(language, page);
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
        return tmdbMovies.getNowPlayingMovies(language, page);
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
        return tmdbMovies.getPopularMovieList(language, page);
    }

    /**
     * This method lets users get the status of whether or not the movie has been rated or added to their favourite or watch
     * lists.<br/>
     *
     * A valid session id is required.
     *
     * @param sessionId
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public MovieState getMovieStatus(String sessionId, int movieId) throws MovieDbException {
        return tmdbMovies.getMovieStatus(sessionId, movieId);
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
        return tmdbMovies.postMovieRating(sessionId, movieId, rating);
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
        return tmdbMovies.getTopRatedMovies(language, page);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Networks Functions">
    /**
     * This method is used to retrieve the basic information about a TV network.
     * <p>
     * You can use this ID to search for TV shows with the discover method.
     *
     * @param networkId
     * @return
     * @throws MovieDbException
     */
    public Network getNetworkInfo(int networkId) throws MovieDbException {
        return tmdbNetworks.getNetworkInfo(networkId);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="People Functions">
    /**
     * This method is used to retrieve all of the basic person information.It will return the single highest rated profile image.
     *
     * @param personId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public Person getPersonInfo(int personId, String... appendToResponse) throws MovieDbException {
        return tmdbPeople.getPersonInfo(personId, appendToResponse);
    }

    /**
     * Get the movie credits for a specific person id.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public NewPersonCredits getPersonMovieCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        return tmdbPeople.getPersonMovieCredits(personId, language, appendToResponse);
    }

    /**
     * Get the TV credits for a specific person id.
     * <p>
     * To get the expanded details for each record, call the /credit method with the provided credit_id.
     * <p>
     * This will provide details about which episode and/or season the credit is for.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public NewPersonCredits getPersonTvCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        return tmdbPeople.getPersonTvCredits(personId, language, appendToResponse);
    }

    /**
     * Get the combined (movie and TV) credits for a specific person id.<p>
     * To get the expanded details for each record, call the /credit method with the provided credit_id.
     * <p>
     * This will provide details about which episode and/or season the credit is for.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public NewPersonCredits getPersonCombinedCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        return tmdbPeople.getPersonCombinedCredits(personId, language, appendToResponse);
    }

    /**
     * This method is used to retrieve all of the profile images for a person.
     *
     * @param personId
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getPersonImages(int personId) throws MovieDbException {
        return tmdbPeople.getPersonImages(personId);
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
     * @return
     * @throws MovieDbException
     */
    public String getPersonChanges(int personId, String startDate, String endDate) throws MovieDbException {
        return tmdbPeople.getPersonChanges(personId, startDate, endDate);
    }

    /**
     * Get the list of popular people on The Movie Database.
     *
     * This list refreshes every day.
     *
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<PersonBasic> getPersonPopular() throws MovieDbException {
        return tmdbPeople.getPersonPopular(0);
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
    public TmdbResultsList<PersonBasic> getPersonPopular(int page) throws MovieDbException {
        return tmdbPeople.getPersonPopular(page);
    }

    /**
     * Get the latest person id.
     *
     * @return
     * @throws MovieDbException
     */
    public Person getPersonLatest() throws MovieDbException {
        return tmdbPeople.getPersonLatest();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Review Functions">
    /**
     * Get the full details of a review by ID.
     *
     * @param reviewId
     * @return
     * @throws MovieDbException
     */
    public Review getReview(String reviewId) throws MovieDbException {
        return tmdbReviews.getReview(reviewId);
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
        return tmdbSearch.searchMovie(movieName, searchYear, language, includeAdult, page);
    }

    /**
     * Search for TmdbTV shows by title.
     *
     * @param name
     * @param searchYear
     * @param language
     * @param searchType
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<TVSeriesBasic> searchTv(String name, int searchYear, String language, SearchType searchType, int page) throws MovieDbException {
        return tmdbSearch.searchTv(name, searchYear, language, searchType, page);
    }

    /**
     * Search for TmdbTV shows by title.
     *
     * @param name
     * @param searchYear
     * @param language
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<TVSeriesBasic> searchTv(String name, int searchYear, String language) throws MovieDbException {
        return tmdbSearch.searchTv(name, searchYear, language, null, 0);
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
        return tmdbSearch.searchCollection(query, language, page);
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
    public TmdbResultsList<PersonMovieOld> searchPeople(String personName, boolean includeAdult, int page) throws MovieDbException {
        return tmdbSearch.searchPeople(personName, includeAdult, page);
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
        return tmdbSearch.searchList(query, language, page);
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
        return tmdbSearch.searchCompanies(companyName, page);
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
        return tmdbSearch.searchKeyword(query, page);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TV Functions">
    /**
     * Get the primary information about a TmdbTV series by id.
     *
     * @param id
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TVSeries getTv(int id, String language, String... appendToResponse) throws MovieDbException {
        return tmdbTv.getTv(id, language, appendToResponse);
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
    public TVCredits getTvCredits(int id, String language, String... appendToResponse) throws MovieDbException {
        return tmdbTv.getTvCredits(id, language, appendToResponse);
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
        return tmdbTv.getTvExternalIds(id, language);
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
        return tmdbTv.getTvImages(id, language);
    }

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
    public TVSeason getTvSeason(int id, int seasonNumber, String language, String... appendToResponse) throws MovieDbException {
        return tmdbTv.getTvSeason(id, seasonNumber, language, appendToResponse);
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
        return tmdbTv.getTvSeasonExternalIds(id, seasonNumber, language);
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
        return tmdbTv.getTvSeasonImages(id, seasonNumber, language);
    }

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
    public TVEpisode getTvEpisode(int id, int seasonNumber, int episodeNumber, String language, String... appendToResponse) throws MovieDbException {
        return tmdbTv.getTvEpisode(id, seasonNumber, episodeNumber, language, appendToResponse);
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
    public TVCredits getTvEpisodeCredits(int id, int seasonNumber, int episodeNumber, String language) throws MovieDbException {
        return tmdbTv.getTvEpisodeCredits(id, seasonNumber, episodeNumber, language);
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
        return tmdbTv.getTvEpisodeExternalIds(id, seasonNumber, episodeNumber, language);
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
        return tmdbTv.getTvEpisodeImages(id, seasonNumber, episodeNumber, language);
    }
    //</editor-fold>

    /**
     * Set the proxy information
     *
     * @param host
     * @param port
     * @param username
     * @param password
     */
    public void setProxy(String host, int port, String username, String password) {
        httpClient.setProxy(host, port, username, password);
    }

    /**
     * Set the web browser timeout settings
     *
     * @param webTimeoutConnect
     * @param webTimeoutRead
     */
    public void setTimeout(int webTimeoutConnect, int webTimeoutRead) {
        httpClient.setTimeouts(webTimeoutConnect, webTimeoutRead);
    }

}
