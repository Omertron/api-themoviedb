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
import com.omertron.themoviedbapi.model.ArtworkType;
import com.omertron.themoviedbapi.model.Certification;
import com.omertron.themoviedbapi.model.ChangedMedia;
import com.omertron.themoviedbapi.model.Collection;
import com.omertron.themoviedbapi.model.CollectionInfo;
import com.omertron.themoviedbapi.model.Company;
import com.omertron.themoviedbapi.model.Configuration;
import com.omertron.themoviedbapi.model.Discover;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.JobDepartment;
import com.omertron.themoviedbapi.model.Keyword;
import com.omertron.themoviedbapi.model.KeywordMovie;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.model.MovieDbList;
import com.omertron.themoviedbapi.model.MovieList;
import com.omertron.themoviedbapi.model.Person;
import com.omertron.themoviedbapi.model.PersonCredit;
import com.omertron.themoviedbapi.model.ReleaseInfo;
import com.omertron.themoviedbapi.model.Reviews;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.TBD_ExternalSource;
import com.omertron.themoviedbapi.model.TBD_FindResults;
import com.omertron.themoviedbapi.model.TBD_Network;
import com.omertron.themoviedbapi.model.TBD_PersonCredits;
import com.omertron.themoviedbapi.model.TokenAuthorisation;
import com.omertron.themoviedbapi.model.TokenSession;
import com.omertron.themoviedbapi.model.Translation;
import com.omertron.themoviedbapi.model.Video;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.results.TmdbResultsMap;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperCollection;
import com.omertron.themoviedbapi.wrapper.WrapperCompany;
import com.omertron.themoviedbapi.wrapper.WrapperImages;
import com.omertron.themoviedbapi.wrapper.WrapperKeywords;
import com.omertron.themoviedbapi.wrapper.WrapperMovie;
import com.omertron.themoviedbapi.wrapper.WrapperMovieList;
import com.omertron.themoviedbapi.wrapper.WrapperPerson;
import com.omertron.themoviedbapi.wrapper.WrapperPersonCredits;
import com.omertron.themoviedbapi.wrapper.WrapperPersonList;
import java.io.IOException;
import java.net.URL;
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
 * This is for version 3 of the API as specified here:
 * http://help.themoviedb.org/kb/api/about-3
 *
 * @author stuart.boston
 */
public class TheMovieDbApi {

    private static final Logger LOG = LoggerFactory.getLogger(TheMovieDbApi.class);
    private String apiKey;
    private HttpTools httpTools;
    // Jackson JSON configuration
    private static final ObjectMapper MAPPER = new ObjectMapper();
    // Constants
    private static final int YEAR_LENGTH = 4;
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
    private static TmdbKeywords tmdbKeywords;
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
        initialise(apiKey, httpTools);
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
        tmdbKeywords = new TmdbKeywords(apiKey, httpTools);
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
     * @param maxDistance The Levenshtein Distance between the two titles. 0 =
     * exact match
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
     * This method is used to retrieve all of the cast & crew information for
     * the person.
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
     * Search Movies This is a good starting point to start finding movies on
     * TMDb.
     *
     * @param movieName
     * @param searchYear Limit the search to the provided year. Zero (0) will
     * get all years
     * @param language The language to include. Can be blank/null.
     * @param includeAdult true or false to include adult titles in the search
     * @param page The page of results to return. 0 to get the default (first
     * page)
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
     * The idea is to be a quick and light method so you can iterate through
     * people quickly.
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
     * You can use this method to search for production companies that are part
     * of TMDb. The company IDs will map to those returned on movie calls.
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
     * Get the basic information for an account. You will need to have a valid
     * session id.
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
     * This method is used to generate a valid request token for user based
     * authentication.
     *
     * A request token is required in order to request a session id.
     *
     * You can generate any number of request tokens but they will expire after
     * 60 minutes.
     *
     * As soon as a valid session id has been created the token will be
     * destroyed.
     *
     * @return
     * @throws MovieDbException
     */
    public TokenAuthorisation getAuthorisationToken() throws MovieDbException {
        return tmdbAuth.getAuthorisationToken();
    }

    /**
     * This method is used to generate a session id for user based
     * authentication.
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
     * This method is used to generate a session id for user based
     * authentication. User must provide their username and password
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
     * A guest session can be used to rate movies without having a registered
     * TMDb user account.
     *
     * You should only generate a single guest session per user (or device) as
     * you will be able to attach the ratings to a TMDb user account in the
     * future.
     *
     * There are also IP limits in place so you should always make sure it's the
     * end user doing the guest session actions.
     *
     * If a guest session is not used for the first time within 24 hours, it
     * will be automatically discarded.
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
     * The maximum number of days that can be returned in a single request is
     * 14.
     *
     * The language is present on fields that are translatable.
     *
     * TODO: DOES NOT WORK AT THE MOMENT. This is due to the "value" item
     * changing type in the ChangeItem
     *
     * @param id
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList getMovieChanges(int id, String startDate, String endDate) throws MovieDbException {
        return tmdbChanges.getMovieChanges(id, startDate, endDate);
    }

    /**
     * Get the changes for a specific TV id.
     *
     * Changes are grouped by key, and ordered by date in descending order.
     *
     * By default, only the last 24 hours of changes are returned.
     *
     * The maximum number of days that can be returned in a single request is
     * 14.
     *
     * The language is present on fields that are translatable.
     *
     * TODO: DOES NOT WORK AT THE MOMENT. This is due to the "value" item
     * changing type in the ChangeItem
     *
     * @param id
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList getTVChanges(int id, String startDate, String endDate) throws MovieDbException {
        return tmdbChanges.getMovieChanges(id, startDate, endDate);
    }

    /**
     * Get the changes for a specific person id.
     *
     * Changes are grouped by key, and ordered by date in descending order.
     *
     * By default, only the last 24 hours of changes are returned.
     *
     * The maximum number of days that can be returned in a single request is
     * 14.
     *
     * The language is present on fields that are translatable.
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList getPersonChanges(int id, String startDate, String endDate) throws MovieDbException {
        return tmdbChanges.getPersonChanges(id, startDate, endDate);
    }

    /**
     * Get a list of Movie IDs that have been edited.
     *
     * You can then use the movie changes API to get the actual data that has
     * been changed.
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
     * You can then use the TV changes API to get the actual data that has been
     * changed.
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
     * You can then use the person changes API to get the actual data that has
     * been changed.
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
    /**
     * This method is used to retrieve all of the basic information about a
     * movie collection.
     *
     * You can get the ID needed for this method by making a getMovieInfo
     * request for the belongs_to_collection.
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

    //<editor-fold defaultstate="collapsed" desc="Companies">
    /**
     * This method is used to retrieve the basic information about a production
     * company on TMDb.
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
     * These movies are returned in order of most recently released to oldest.
     * The default response will return 20 movies per page.
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

    //<editor-fold defaultstate="collapsed" desc="Configuration">
    /**
     * Get the configuration information
     *
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public Configuration getConfiguration() throws MovieDbException {
        return tmdbConfiguration.getConfig();
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
        return tmdbConfiguration.createImageUrl(imagePath, requiredSize);
    }

    /**
     * Get a list of valid jobs
     *
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<JobDepartment> getJobs() throws MovieDbException {
        return tmdbConfiguration.getJobs();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Credits">
    /**
     * Get the detailed information about a particular credit record.
     * <p>
     * This is currently only supported with the new credit model found in TV.
     * <br/>
     * These IDs can be found from any TV credit response as well as the
     * TV_credits and combined_credits methods for people. <br/>
     * The episodes object returns a list of episodes and are generally going to
     * be guest stars. <br/>
     * The season array will return a list of season numbers. <br/>
     * Season credits are credits that were marked with the "add to every
     * season" option in the editing interface and are assumed to be "season
     * regulars".
     *
     * @param creditId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public TBD_PersonCredits getCreditInfo(String creditId, String language) throws MovieDbException {
        return tmdbCredits.getCreditInfo(creditId, language);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Discover">
    /**
     * Discover movies by different types of data like average rating, number of
     * votes, genres and certifications.
     *
     * @param discover A discover object containing the search criteria required
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getDiscoverMovies(Discover discover) throws MovieDbException {
        return tmdbDiscover.getDiscoverMovies(discover);
    }

    /**
     * Discover movies by different types of data like average rating, number of
     * votes, genres and certifications.
     *
     * @param discover A discover object containing the search criteria required
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> getDiscoverTV(Discover discover) throws MovieDbException {
        return tmdbDiscover.getDiscoverTV(discover);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Find">
    /**
     * You con use this method to find movies, tv series or persons using
     * external ids.
     *
     * Supported query ids are
     * <ul>
     * <li>Movies: imdb_id</li>
     * <li>People: imdb_id, freebase_mid, freebase_id, tvrage_id</li>
     * <li>TV Series: imdb_id, freebase_mid, freebase_id, tvdb_id,
     * tvrage_id</li>
     * <li>TV Seasons: freebase_mid, freebase_id, tvdb_id, tvrage_id</li>
     * <li>TV Episodes: imdb_id, freebase_mid, freebase_id, tvdb_id,
     * tvrage_idimdb_id, freebase_mid, freebase_id, tvrage_id, tvdb_id.
     * </ul>
     *
     * For details see http://docs.themoviedb.apiary.io/#find
     *
     * @param id the external id
     * @param externalSource one of {@link ExternalSource}.
     * @param language the language
     * @return
     * @throws MovieDbException
     */
    public TBD_FindResults find(String id, TBD_ExternalSource externalSource, String language) throws MovieDbException {
        return tmdbFind.find(id, externalSource, language);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Genres">
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
     * It is important to understand that only movies with more than 10 votes
     * get listed.
     *
     * This prevents movies from 1 10/10 rating from being listed first and for
     * the first 5 pages.
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

    //<editor-fold defaultstate="collapsed" desc="Keywords">
    /**
     * Get the basic information for a specific keyword id.
     *
     * @param keywordId
     * @return
     * @throws MovieDbException
     */
    public Keyword getKeyword(String keywordId) throws MovieDbException {
        return tmdbKeywords.getKeyword(keywordId);
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
        return tmdbKeywords.getKeywordMovies(keywordId, language, page);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Lists">
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
        return tmdbList.createList(sessionId, name, description);
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
     * This method lets users add new movies to a list that they created. A
     * valid session id is required.
     *
     * @param sessionId
     * @param listId
     * @param movieId
     * @return true if the movie is on the list
     * @throws MovieDbException
     */
    public StatusCode addMovieToList(String sessionId, String listId, Integer movieId) throws MovieDbException {
        return tmdbList.modifyMovieList(sessionId, listId, movieId, MethodSub.ADD_ITEM);
    }

    /**
     * This method lets users remove movies from a list that they created. A
     * valid session id is required.
     *
     * @param sessionId
     * @param listId
     * @param movieId
     * @return true if the movie is on the list
     * @throws MovieDbException
     */
    public StatusCode removeMovieFromList(String sessionId, String listId, Integer movieId) throws MovieDbException {
        return tmdbList.modifyMovieList(sessionId, listId, movieId, MethodSub.REMOVE_ITEM);
    }

    /**
     * This method lets users delete a list that they created. A valid session
     * id is required.
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

    //<editor-fold defaultstate="collapsed" desc="Movies">
    /**
     * This method is used to retrieve all of the basic movie information.
     *
     * It will return the single highest rated poster and backdrop.
     *
     * ApiExceptionType.MOVIE_ID_NOT_FOUND will be thrown if there are no movies
     * found.
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
     * ApiExceptionType.MOVIE_ID_NOT_FOUND will be thrown if there are no movies
     * found.
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
     * This method is used to retrieve all of the alternative titles we have for
     * a particular movie.
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
        return tmdbMovies.getMovieCasts(movieId, appendToResponse);
    }

    /**
     * This method should be used when you’re wanting to retrieve all of the
     * images for a particular movie.
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
     * This method is used to retrieve all of the keywords that have been added
     * to a particular movie.
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
     * This method is used to retrieve all of the release and certification data
     * we have for a specific movie.
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
     * This method is used to retrieve all of the trailers for a particular
     * movie.
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
        return tmdbMovies.getMovieTrailers(movieId, language, appendToResponse);
    }

    /**
     * This method is used to retrieve a list of the available translations for
     * a specific movie.
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
     * The similar movies method will let you retrieve the similar movies for a
     * particular movie.
     *
     * This data is created dynamically but with the help of users votes on
     * TMDb.
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

    public TmdbResultsList<Reviews> getReviews(int movieId, String language, int page, String... appendToResponse) throws MovieDbException {
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
     * This is a curated list that will normally contain 100 movies. The default
     * response will return 20 movies.
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
     * This method is used to retrieve the top rated movies that have over 10
     * votes on TMDb.
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Networks">
    /**
     * This method is used to retrieve the basic information about a TV network.
     * <p>
     * You can use this ID to search for TV shows with the discover method.
     *
     * @param networkId
     * @return
     * @throws MovieDbException
     */
    public TBD_Network getNetworkInfo(int networkId) throws MovieDbException {
        return tmdbNetworks.getNetworkInfo(networkId);
    }
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
