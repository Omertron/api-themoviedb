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

import com.omertron.themoviedbapi.enumeration.ExternalSource;
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.enumeration.SearchType;
import com.omertron.themoviedbapi.enumeration.SortBy;
import com.omertron.themoviedbapi.methods.TmdbAccount;
import com.omertron.themoviedbapi.methods.TmdbAuthentication;
import com.omertron.themoviedbapi.methods.TmdbCertifications;
import com.omertron.themoviedbapi.methods.TmdbChanges;
import com.omertron.themoviedbapi.methods.TmdbCollections;
import com.omertron.themoviedbapi.methods.TmdbCompanies;
import com.omertron.themoviedbapi.methods.TmdbConfiguration;
import com.omertron.themoviedbapi.methods.TmdbCredits;
import com.omertron.themoviedbapi.methods.TmdbDiscover;
import com.omertron.themoviedbapi.methods.TmdbEpisodes;
import com.omertron.themoviedbapi.methods.TmdbFind;
import com.omertron.themoviedbapi.methods.TmdbGenres;
import com.omertron.themoviedbapi.methods.TmdbKeywords;
import com.omertron.themoviedbapi.methods.TmdbLists;
import com.omertron.themoviedbapi.methods.TmdbMovies;
import com.omertron.themoviedbapi.methods.TmdbNetworks;
import com.omertron.themoviedbapi.methods.TmdbPeople;
import com.omertron.themoviedbapi.methods.TmdbReviews;
import com.omertron.themoviedbapi.methods.TmdbSearch;
import com.omertron.themoviedbapi.methods.TmdbSeasons;
import com.omertron.themoviedbapi.methods.TmdbTV;
import com.omertron.themoviedbapi.model.Certification;
import com.omertron.themoviedbapi.model.FindResults;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.account.Account;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.artwork.ArtworkMedia;
import com.omertron.themoviedbapi.model.authentication.TokenAuthorisation;
import com.omertron.themoviedbapi.model.authentication.TokenSession;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.change.ChangeListItem;
import com.omertron.themoviedbapi.model.collection.Collection;
import com.omertron.themoviedbapi.model.collection.CollectionInfo;
import com.omertron.themoviedbapi.model.company.Company;
import com.omertron.themoviedbapi.model.config.Configuration;
import com.omertron.themoviedbapi.model.config.JobDepartment;
import com.omertron.themoviedbapi.model.credits.CreditBasic;
import com.omertron.themoviedbapi.model.credits.CreditMovieBasic;
import com.omertron.themoviedbapi.model.credits.CreditTVBasic;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.list.ListItem;
import com.omertron.themoviedbapi.model.list.UserList;
import com.omertron.themoviedbapi.model.media.AlternativeTitle;
import com.omertron.themoviedbapi.model.media.MediaBasic;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.media.Translation;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.movie.ReleaseInfo;
import com.omertron.themoviedbapi.model.network.Network;
import com.omertron.themoviedbapi.model.person.ContentRating;
import com.omertron.themoviedbapi.model.person.CreditInfo;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.person.PersonCreditList;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.model.person.PersonInfo;
import com.omertron.themoviedbapi.model.review.Review;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.model.tv.TVEpisodeInfo;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.model.tv.TVSeasonInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.results.ResultsMap;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import java.net.URL;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

/**
 * The MovieInfo API
 * <p>
 * This is for version 3 of the API as specified here:
 * http://help.themoviedb.org/kb/api/about-3
 *
 * @author stuart.boston
 */
public class TheMovieDbApi {

    private HttpTools httpTools;
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
    private static TmdbSeasons tmdbSeasons;
    private static TmdbEpisodes tmdbEpisodes;

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
    public TheMovieDbApi(String apiKey, HttpClient httpClient) throws MovieDbException {
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
        tmdbSeasons = new TmdbSeasons(apiKey, httpTools);
        tmdbEpisodes = new TmdbEpisodes(apiKey, httpTools);
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
    public ResultList<UserList> getUserLists(String sessionId, int accountId) throws MovieDbException {
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
    public ResultList<MovieBasic> getFavoriteMovies(String sessionId, int accountId) throws MovieDbException {
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
    public StatusCode modifyFavoriteStatus(String sessionId, int accountId, Integer mediaId, MediaType mediaType, boolean isFavorite) throws MovieDbException {
        return tmdbAccount.modifyFavoriteStatus(sessionId, accountId, mediaType, mediaId, isFavorite);
    }

    /**
     * Get the list of rated movies (and associated rating) for an account.
     *
     * @param sessionId
     * @param accountId
     * @param page
     * @param sortBy
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieBasic> getRatedMovies(String sessionId, int accountId, Integer page, String sortBy, String language) throws MovieDbException {
        return tmdbAccount.getRatedMovies(sessionId, accountId, page, sortBy, language);
    }

    /**
     * Get the list of rated TV shows (and associated rating) for an account.
     *
     * @param sessionId
     * @param accountId
     * @param page
     * @param sortBy
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<TVBasic> getRatedTV(String sessionId, int accountId, Integer page, String sortBy, String language) throws MovieDbException {
        return tmdbAccount.getRatedTV(sessionId, accountId, page, sortBy, language);
    }

    /**
     * Get the list of movies on an accounts watchlist.
     *
     * @param sessionId
     * @param accountId
     * @param page
     * @param sortBy
     * @param language
     * @return The watchlist of the user
     * @throws MovieDbException
     */
    public ResultList<MovieBasic> getWatchListMovie(String sessionId, int accountId, Integer page, String sortBy, String language) throws MovieDbException {
        return tmdbAccount.getWatchListMovie(sessionId, accountId, page, sortBy, language);
    }

    /**
     * Get the list of movies on an accounts watchlist.
     *
     * @param sessionId
     * @param accountId
     * @param page
     * @param sortBy
     * @param language
     * @return The watchlist of the user
     * @throws MovieDbException
     */
    public ResultList<TVBasic> getWatchListTV(String sessionId, int accountId, Integer page, String sortBy, String language) throws MovieDbException {
        return tmdbAccount.getWatchListTV(sessionId, accountId, page, sortBy, language);
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
    public StatusCode addToWatchList(String sessionId, int accountId, MediaType mediaType, Integer mediaId) throws MovieDbException {
        return tmdbAccount.modifyWatchList(sessionId, accountId, mediaType, mediaId, true);
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
    public StatusCode removeFromWatchList(String sessionId, int accountId, MediaType mediaType, Integer mediaId) throws MovieDbException {
        return tmdbAccount.modifyWatchList(sessionId, accountId, mediaType, mediaId, false);
    }

    /**
     * Get the list of favorite TV series for an account.
     *
     * @param sessionId
     * @param accountId
     * @return
     * @throws MovieDbException
     */
    public ResultList<TVBasic> getFavoriteTv(String sessionId, int accountId) throws MovieDbException {
        return tmdbAccount.getFavoriteTv(sessionId, accountId);
    }

    /**
     * Get a list of rated movies for a specific guest session id.
     *
     * @param guestSessionId
     * @param language
     * @param page
     * @param sortBy only CREATED_AT_ASC or CREATED_AT_DESC is supported
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieBasic> getGuestRatedMovies(String guestSessionId, String language, Integer page, SortBy sortBy) throws MovieDbException {
        return tmdbAccount.getGuestRatedMovies(guestSessionId, language, page, sortBy);
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
    public ResultsMap<String, List<Certification>> getMoviesCertification() throws MovieDbException {
        return tmdbCertifications.getMoviesCertification();
    }

    /**
     * Get a list of tv certification.
     *
     * @return
     * @throws MovieDbException
     */
    public ResultsMap<String, List<Certification>> getTvCertification() throws MovieDbException {
        return tmdbCertifications.getTvCertification();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Changes">
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
    public ResultList<ChangeListItem> getMovieChangeList(Integer page, String startDate, String endDate) throws MovieDbException {
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
    public ResultList<ChangeListItem> getTvChangeList(Integer page, String startDate, String endDate) throws MovieDbException {
        return tmdbChanges.getChangeList(MethodBase.TV, page, startDate, endDate);
    }

    /**
     * Get a list of PersonInfo IDs that have been edited.
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
    public ResultList<ChangeListItem> getPersonChangeList(Integer page, String startDate, String endDate) throws MovieDbException {
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
    public ResultList<Artwork> getCollectionImages(int collectionId, String language) throws MovieDbException {
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
     * @param companyId
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieBasic> getCompanyMovies(int companyId, String language, Integer page) throws MovieDbException {
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
        return tmdbConfiguration.getConfig().createImageUrl(imagePath, requiredSize);
    }

    /**
     * Get a list of valid jobs
     *
     * @return
     * @throws MovieDbException
     */
    public ResultList<JobDepartment> getJobs() throws MovieDbException {
        return tmdbConfiguration.getJobs();
    }

    /**
     * Get the list of supported timezones for the API methods that support
     * them.
     *
     * @return @throws MovieDbException
     */
    public ResultsMap<String, List<String>> getTimezones() throws MovieDbException {
        return tmdbConfiguration.getTimezones();
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
    public CreditInfo getCreditInfo(String creditId, String language) throws MovieDbException {
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
    public ResultList<MovieBasic> getDiscoverMovies(Discover discover) throws MovieDbException {
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
    public ResultList<TVBasic> getDiscoverTV(Discover discover) throws MovieDbException {
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
    public FindResults find(String id, ExternalSource externalSource, String language) throws MovieDbException {
        return tmdbFind.find(id, externalSource, language);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Genres">
    /**
     * Get the list of Movie genres.
     *
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<Genre> getGenreMovieList(String language) throws MovieDbException {
        return tmdbGenre.getGenreMovieList(language);
    }

    /**
     * Get the list of TV genres..
     *
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<Genre> getGenreTVList(String language) throws MovieDbException {
        return tmdbGenre.getGenreTVList(language);
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
     * @param includeAdult
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieBasic> getGenreMovies(int genreId, String language, Integer page, Boolean includeAllMovies, Boolean includeAdult) throws MovieDbException {
        return tmdbGenre.getGenreMovies(genreId, language, page, includeAllMovies, includeAdult);
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
    public ResultList<MovieBasic> getKeywordMovies(String keywordId, String language, Integer page) throws MovieDbException {
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
    public ListItem<MovieInfo> getList(String listId) throws MovieDbException {
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
     * This method lets users delete a list that they created. A valid session
     * id is required.
     *
     * @param sessionId
     * @param listId
     * @return
     * @throws MovieDbException
     */
    public StatusCode deleteList(String sessionId, String listId) throws MovieDbException {
        return tmdbList.deleteList(sessionId, listId);
    }

    /**
     * Check to see if an item is already on a list.
     *
     * @param listId
     * @param mediaId
     * @return true if the item is on the list
     * @throws MovieDbException
     */
    public boolean checkItemStatus(String listId, Integer mediaId) throws MovieDbException {
        return tmdbList.checkItemStatus(listId, mediaId);
    }

    /**
     * This method lets users add new items to a list that they created.
     *
     * A valid session id is required.
     *
     * @param sessionId
     * @param listId
     * @param mediaId
     * @return true if the movie is on the list
     * @throws MovieDbException
     */
    public StatusCode addItemToList(String sessionId, String listId, Integer mediaId) throws MovieDbException {
        return tmdbList.addItem(sessionId, listId, mediaId);
    }

    /**
     * This method lets users remove items from a list that they created.
     *
     * A valid session id is required.
     *
     * @param sessionId
     * @param listId
     * @param mediaId
     * @return true if the movie is on the list
     * @throws MovieDbException
     */
    public StatusCode removeItemFromList(String sessionId, String listId, Integer mediaId) throws MovieDbException {
        return tmdbList.removeItem(sessionId, listId, mediaId);
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
    public MovieInfo getMovieInfo(int movieId, String language, String... appendToResponse) throws MovieDbException {
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
    public MovieInfo getMovieInfoImdb(String imdbId, String language, String... appendToResponse) throws MovieDbException {
        return tmdbMovies.getMovieInfoImdb(imdbId, language, appendToResponse);
    }

    /**
     * This method lets a user get the status of whether or not the movie has
     * been rated or added to their favourite or movie watch list.
     *
     * A valid session id is required.
     *
     * @param movieId
     * @param sessionId
     * @return
     * @throws MovieDbException
     */
    public MediaState getMovieAccountState(int movieId, String sessionId) throws MovieDbException {
        return tmdbMovies.getMovieAccountState(movieId, sessionId);
    }

    /**
     * This method is used to retrieve all of the alternative titles we have for
     * a particular movie.
     *
     * @param movieId
     * @param country
     * @return
     * @throws MovieDbException
     */
    public ResultList<AlternativeTitle> getMovieAlternativeTitles(int movieId, String country) throws MovieDbException {
        return tmdbMovies.getMovieAlternativeTitles(movieId, country);
    }

    /**
     * Get the cast and crew information for a specific movie id.
     *
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public MediaCreditList getMovieCredits(int movieId) throws MovieDbException {
        return tmdbMovies.getMovieCredits(movieId);
    }

    /**
     * This method should be used when you’re wanting to retrieve all of the
     * images for a particular movie.
     *
     * @param movieId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<Artwork> getMovieImages(int movieId, String language) throws MovieDbException {
        return tmdbMovies.getMovieImages(movieId, language);
    }

    /**
     * This method is used to retrieve all of the keywords that have been added
     * to a particular movie.
     *
     * Currently, only English keywords exist.
     *
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public ResultList<Keyword> getMovieKeywords(int movieId) throws MovieDbException {
        return tmdbMovies.getMovieKeywords(movieId);
    }

    /**
     * This method is used to retrieve all of the release and certification data
     * we have for a specific movie.
     *
     * @param movieId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<ReleaseInfo> getMovieReleaseInfo(int movieId, String language) throws MovieDbException {
        return tmdbMovies.getMovieReleaseInfo(movieId, language);
    }

    /**
     * This method is used to retrieve all of the trailers for a particular
     * movie.
     *
     * Supported sites are YouTube and QuickTime.
     *
     * @param movieId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<Video> getMovieVideos(int movieId, String language) throws MovieDbException {
        return tmdbMovies.getMovieVideos(movieId, language);
    }

    /**
     * This method is used to retrieve a list of the available translations for
     * a specific movie.
     *
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public ResultList<Translation> getMovieTranslations(int movieId) throws MovieDbException {
        return tmdbMovies.getMovieTranslations(movieId);
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
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieInfo> getSimilarMovies(int movieId, Integer page, String language) throws MovieDbException {
        return tmdbMovies.getSimilarMovies(movieId, page, language);
    }

    /**
     * Get the reviews for a particular movie id.
     *
     * @param movieId
     * @param page
     * @param language
     * @return
     * @throws MovieDbException
     */
    public ResultList<Review> getMovieReviews(int movieId, Integer page, String language) throws MovieDbException {
        return tmdbMovies.getMovieReviews(movieId, page, language);
    }

    /**
     * Get the lists that the movie belongs to
     *
     * @param movieId
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public ResultList<UserList> getMovieLists(int movieId, Integer page, String language) throws MovieDbException {
        return tmdbMovies.getMovieLists(movieId, page, language);
    }

    /**
     * Get the changes for a specific movie ID.
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
     * @param movieId
     * @param startDate
     * @param endDate
     * @return
     * @throws MovieDbException
     */
    public ResultList<ChangeKeyItem> getMovieChanges(int movieId, String startDate, String endDate) throws MovieDbException {
        return tmdbMovies.getMovieChanges(movieId, startDate, endDate);
    }

    /**
     * This method lets users rate a movie.
     *
     * A valid session id or guest session id is required.
     *
     * @param sessionId
     * @param movieId
     * @param rating
     * @param guestSessionId
     * @return
     * @throws MovieDbException
     */
    public StatusCode postMovieRating(int movieId, int rating, String sessionId, String guestSessionId) throws MovieDbException {
        return tmdbMovies.postMovieRating(movieId, rating, sessionId, guestSessionId);
    }

    /**
     * This method is used to retrieve the newest movie that was added to TMDb.
     *
     * @return
     * @throws MovieDbException
     */
    public MovieInfo getLatestMovie() throws MovieDbException {
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
    public ResultList<MovieInfo> getUpcoming(Integer page, String language) throws MovieDbException {
        return tmdbMovies.getUpcoming(page, language);
    }

    /**
     * This method is used to retrieve the movies currently in theatres.
     *
     * This is a curated list that will normally contain 100 movies. The default
     * response will return 20 movies.
     *
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieInfo> getNowPlayingMovies(Integer page, String language) throws MovieDbException {
        return tmdbMovies.getNowPlayingMovies(page, language);
    }

    /**
     * This method is used to retrieve the daily movie popularity list.
     *
     * This list is updated daily. The default response will return 20 movies.
     *
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieInfo> getPopularMovieList(Integer page, String language) throws MovieDbException {
        return tmdbMovies.getPopularMovieList(page, language);
    }

    /**
     * This method is used to retrieve the top rated movies that have over 10
     * votes on TMDb.
     *
     * The default response will return 20 movies.
     *
     * @param language
     * @param page
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieInfo> getTopRatedMovies(Integer page, String language) throws MovieDbException {
        return tmdbMovies.getTopRatedMovies(page, language);
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
    public Network getNetworkInfo(int networkId) throws MovieDbException {
        return tmdbNetworks.getNetworkInfo(networkId);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="People">
    /**
     * Get the general person information for a specific id.
     *
     * @param personId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public PersonInfo getPersonInfo(int personId, String... appendToResponse) throws MovieDbException {
        return tmdbPeople.getPersonInfo(personId, appendToResponse);
    }

    /**
     * Get the movie credits for a specific person id.
     *
     * @param personId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public PersonCreditList<CreditMovieBasic> getPersonMovieCredits(int personId, String language) throws MovieDbException {
        return tmdbPeople.getPersonMovieCredits(personId, language);
    }

    /**
     * Get the TV credits for a specific person id.
     *
     * To get the expanded details for each record, call the /credit method with
     * the provided credit_id.
     *
     * This will provide details about which episode and/or season the credit is
     * for.
     *
     * @param personId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public PersonCreditList<CreditTVBasic> getPersonTVCredits(int personId, String language) throws MovieDbException {
        return tmdbPeople.getPersonTVCredits(personId, language);
    }

    /**
     * Get the combined (movie and TV) credits for a specific person id.
     *
     * To get the expanded details for each TV record, call the /credit method
     * with the provided credit_id.
     *
     * This will provide details about which episode and/or season the credit is
     * for.
     *
     * @param personId
     * @param language
     * @return
     * @throws MovieDbException
     */
    public PersonCreditList<CreditBasic> getPersonCombinedCredits(int personId, String language) throws MovieDbException {
        return tmdbPeople.getPersonCombinedCredits(personId, language);
    }

    /**
     * Get the external ids for a specific person id.
     *
     * @param personId
     * @return
     * @throws MovieDbException
     */
    public ExternalID getPersonExternalIds(int personId) throws MovieDbException {
        return tmdbPeople.getPersonExternalIds(personId);
    }

    /**
     * Get the images for a specific person id.
     *
     * @param personId
     * @return
     * @throws MovieDbException
     */
    public ResultList<Artwork> getPersonImages(int personId) throws MovieDbException {
        return tmdbPeople.getPersonImages(personId);
    }

    /**
     * Get the images that have been tagged with a specific person id.
     *
     * We return all of the image results with a media object mapped for each
     * image.
     *
     * @param personId
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<ArtworkMedia> getPersonTaggedImages(int personId, Integer page, String language) throws MovieDbException {
        return tmdbPeople.getPersonTaggedImages(personId, page, language);
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
     * @param personId
     * @param startDate
     * @param endDate
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<ChangeKeyItem> getPersonChanges(int personId, String startDate, String endDate) throws MovieDbException {
        return tmdbPeople.getPersonChanges(personId, startDate, endDate);
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
    public ResultList<PersonFind> getPersonPopular(Integer page) throws MovieDbException {
        return tmdbPeople.getPersonPopular(page);
    }

    /**
     * Get the latest person id.
     *
     * @return
     * @throws MovieDbException
     */
    public PersonInfo getPersonLatest() throws MovieDbException {
        return tmdbPeople.getPersonLatest();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Review">
    /**
     *
     * @param reviewId
     * @return @throws MovieDbException
     */
    public Review getReviews(String reviewId) throws MovieDbException {
        return tmdbReviews.getReview(reviewId);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search">
    /**
     * Search Companies.
     *
     * You can use this method to search for production companies that are part
     * of TMDb. The company IDs will map to those returned on movie calls.
     *
     * http://help.themoviedb.org/kb/api/search-companies
     *
     * @param query
     * @param page
     * @return
     * @throws MovieDbException
     */
    public ResultList<Company> searchCompanies(String query, Integer page) throws MovieDbException {
        return tmdbSearch.searchCompanies(query, page);
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
    public ResultList<Collection> searchCollection(String query, Integer page, String language) throws MovieDbException {
        return tmdbSearch.searchCollection(query, page, language);
    }

    /**
     * Search for keywords by name
     *
     * @param query
     * @param page
     * @return
     * @throws MovieDbException
     */
    public ResultList<Keyword> searchKeyword(String query, Integer page) throws MovieDbException {
        return tmdbSearch.searchKeyword(query, page);
    }

    /**
     * Search for lists by name and description.
     *
     * @param query
     * @param includeAdult
     * @param page
     * @return
     * @throws MovieDbException
     */
    public ResultList<UserList> searchList(String query, Integer page, Boolean includeAdult) throws MovieDbException {
        return tmdbSearch.searchList(query, page, includeAdult);
    }

    /**
     * Search Movies This is a good starting point to start finding movies on
     * TMDb.
     *
     * @param query
     * @param searchYear Limit the search to the provided year. Zero (0) will
     * get all years
     * @param language The language to include. Can be blank/null.
     * @param includeAdult true or false to include adult titles in the search
     * @param page The page of results to return. 0 to get the default (first
     * page)
     * @param primaryReleaseYear
     * @param searchType
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieInfo> searchMovie(String query,
            Integer page,
            String language,
            Boolean includeAdult,
            Integer searchYear,
            Integer primaryReleaseYear,
            SearchType searchType) throws MovieDbException {
        return tmdbSearch.searchMovie(query, page, language, includeAdult, searchYear, primaryReleaseYear, searchType);
    }

    /**
     * Search the movie, tv show and person collections with a single query.
     *
     * Each item returned in the result array has a media_type field that maps
     * to either movie, tv or person.
     *
     * Each mapped result is the same response you would get from each
     * independent search
     *
     * @param query
     * @param page
     * @param language
     * @param includeAdult
     * @return
     * @throws MovieDbException
     */
    public ResultList<MediaBasic> searchMulti(String query, Integer page, String language, Boolean includeAdult) throws MovieDbException {
        return tmdbSearch.searchMulti(query, page, language, includeAdult);
    }

    /**
     * This is a good starting point to start finding people on TMDb.
     *
     * The idea is to be a quick and light method so you can iterate through
     * people quickly.
     *
     * @param query
     * @param includeAdult
     * @param page
     * @param searchType
     * @return
     * @throws MovieDbException
     */
    public ResultList<PersonFind> searchPeople(String query, Integer page, Boolean includeAdult, SearchType searchType) throws MovieDbException {
        return tmdbSearch.searchPeople(query, page, includeAdult, searchType);
    }

    /**
     * Search for TV shows by title.
     *
     * @param query
     * @param page
     * @param language
     * @param firstAirDateYear
     * @param searchType
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVBasic> searchTV(String query, Integer page, String language, Integer firstAirDateYear, SearchType searchType) throws MovieDbException {
        return tmdbSearch.searchTV(query, page, language, firstAirDateYear, searchType);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TV Shows">
    /**
     * Get the primary information about a TV series by id.
     *
     * @param tvID
     * @param language
     * @param appendToResponse
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public TVInfo getTVInfo(int tvID, String language, String... appendToResponse) throws MovieDbException {
        return tmdbTv.getTVInfo(tvID, language, appendToResponse);
    }

    /**
     * This method lets users get the status of whether or not the TV show has
     * been rated or added to their favourite or watch lists.
     *
     * A valid session id is required.
     *
     * @param tvID
     * @param sessionID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public MediaState getTVAccountState(int tvID, String sessionID) throws MovieDbException {
        return tmdbTv.getTVAccountState(tvID, sessionID);
    }

    /**
     * Get the alternative titles for a specific show ID.
     *
     * @param tvID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<AlternativeTitle> getTVAlternativeTitles(int tvID) throws MovieDbException {
        return tmdbTv.getTVAlternativeTitles(tvID);
    }

    /**
     * Get the changes for a specific TV show id.
     *
     * @param tvID
     * @param startDate
     * @param endDate
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<ChangeKeyItem> getTVChanges(int tvID, String startDate, String endDate) throws MovieDbException {
        return tmdbTv.getTVChanges(tvID, startDate, endDate);
    }

    /**
     * Get the content ratings for a specific TV show id.
     *
     * @param tvID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<ContentRating> getTVContentRatings(int tvID) throws MovieDbException {
        return tmdbTv.getTVContentRatings(tvID);
    }

    /**
     * Get the cast & crew information about a TV series.
     *
     * @param tvID
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public MediaCreditList getTVCredits(int tvID, String language) throws MovieDbException {
        return tmdbTv.getTVCredits(tvID, language);
    }

    /**
     * Get the external ids that we have stored for a TV series.
     *
     * @param tvID
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ExternalID getTVExternalIDs(int tvID, String language) throws MovieDbException {
        return tmdbTv.getTVExternalIDs(tvID, language);
    }

    /**
     * Get the images (posters and backdrops) for a TV series.
     *
     * @param tvID
     * @param language
     * @param includeImageLanguage
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<Artwork> getTVImages(int tvID, String language, String... includeImageLanguage) throws MovieDbException {
        return tmdbTv.getTVImages(tvID, language, includeImageLanguage);
    }

    /**
     * Get the plot keywords for a specific TV show id.
     *
     * @param tvID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<Keyword> getTVKeywords(int tvID) throws MovieDbException {
        return tmdbTv.getTVKeywords(tvID);
    }

    /**
     * This method lets users rate a TV show.
     *
     * A valid session id or guest session id is required.
     *
     * @param tvID
     * @param rating
     * @param sessionID
     * @param guestSessionID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public StatusCode postTVRating(int tvID, int rating, String sessionID, String guestSessionID) throws MovieDbException {
        return tmdbTv.postTVRating(tvID, rating, sessionID, guestSessionID);
    }

    /**
     * Get the similar TV shows for a specific tv id.
     *
     * @param tvID
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVInfo> getTVSimilar(int tvID, Integer page, String language) throws MovieDbException {
        return tmdbTv.getTVSimilar(tvID, page, language);
    }

    /**
     * Get the list of translations that exist for a TV series. These
     * translations cascade down to the episode level.
     *
     * @param tvID
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<Translation> getTVTranslations(int tvID) throws MovieDbException {
        return tmdbTv.getTVTranslations(tvID);
    }

    /**
     * Get the videos that have been added to a TV series (trailers, opening
     * credits, etc...)
     *
     * @param tvID
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<Video> getTVVideos(int tvID, String language) throws MovieDbException {
        return tmdbTv.getTVVideos(tvID, language);
    }

    /**
     * Get the latest TV show id.
     *
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public TVInfo getLatestTV() throws MovieDbException {
        return tmdbTv.getLatestTV();
    }

    /**
     * Get the list of TV shows that are currently on the air.
     *
     * This query looks for any TV show that has an episode with an air date in
     * the next 7 days.
     *
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVBasic> getTVOnTheAir(Integer page, String language) throws MovieDbException {
        return tmdbTv.getTVOnTheAir(page, language);
    }

    /**
     * Get the list of TV shows that air today.
     *
     * Without a specified timezone, this query defaults to EST
     *
     * @param page
     * @param language
     * @param timezone
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVBasic> getTVAiringToday(Integer page, String language, String timezone) throws MovieDbException {
        return tmdbTv.getTVAiringToday(page, language, timezone);
    }

    /**
     * Get the list of top rated TV shows.
     *
     * By default, this list will only include TV shows that have 2 or more
     * votes.
     *
     * This list refreshes every day.
     *
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVBasic> getTVTopRated(Integer page, String language) throws MovieDbException {
        return tmdbTv.getTVTopRated(page, language);
    }

    /**
     * Get the list of popular TV shows. This list refreshes every day.
     *
     * @param page
     * @param language
     * @return
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    public ResultList<TVBasic> getTVPopular(Integer page, String language) throws MovieDbException {
        return tmdbTv.getTVPopular(page, language);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TV Seasons">
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
    public TVSeasonInfo getSeasonInfo(int tvID, int seasonNumber, String language, String... appendToResponse) throws MovieDbException {
        return tmdbSeasons.getSeasonInfo(tvID, seasonNumber, language, appendToResponse);
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
    public ResultList<ChangeKeyItem> getSeasonChanges(int tvID, String startDate, String endDate) throws MovieDbException {
        return tmdbSeasons.getSeasonChanges(tvID, startDate, endDate);
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
    public MediaState getSeasonAccountState(int tvID, String sessionID) throws MovieDbException {
        return tmdbSeasons.getSeasonAccountState(tvID, sessionID);
    }

    /**
     * Get the cast & crew credits for a TV season by season number.
     *
     * @param tvID
     * @param seasonNumber
     * @return
     * @throws MovieDbException
     */
    public MediaCreditList getSeasonCredits(int tvID, int seasonNumber) throws MovieDbException {
        return tmdbSeasons.getSeasonCredits(tvID, seasonNumber);
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
    public ExternalID getSeasonExternalID(int tvID, int seasonNumber, String language) throws MovieDbException {
        return tmdbSeasons.getSeasonExternalID(tvID, seasonNumber, language);
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
    public ResultList<Artwork> getSeasonImages(int tvID, int seasonNumber, String language, String... includeImageLanguage) throws MovieDbException {
        return tmdbSeasons.getSeasonImages(tvID, seasonNumber, language, includeImageLanguage);
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
    public ResultList<Video> getSeasonVideos(int tvID, int seasonNumber, String language) throws MovieDbException {
        return tmdbSeasons.getSeasonVideos(tvID, seasonNumber, language);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TV Episodes">
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
    public TVEpisodeInfo getEpisodeInfo(int tvID, int seasonNumber, int episodeNumber, String language, String... appendToResponse) throws MovieDbException {
        return tmdbEpisodes.getEpisodeInfo(tvID, seasonNumber, episodeNumber, language, appendToResponse);
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
    public ResultList<ChangeKeyItem> getEpisodeChanges(int episodeID, String startDate, String endDate) throws MovieDbException {
        return tmdbEpisodes.getEpisodeChanges(episodeID, startDate, endDate);
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
    public MediaState getEpisodeAccountState(int tvID, int seasonNumber, int episodeNumber, String sessionID) throws MovieDbException {
        return tmdbEpisodes.getEpisodeAccountState(tvID, seasonNumber, episodeNumber, sessionID);
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
    public MediaCreditList getEpisodeCredits(int tvID, int seasonNumber, int episodeNumber) throws MovieDbException {
        return tmdbEpisodes.getEpisodeCredits(tvID, seasonNumber, episodeNumber);
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
    public ExternalID getEpisodeExternalID(int tvID, int seasonNumber, int episodeNumber, String language) throws MovieDbException {
        return tmdbEpisodes.getEpisodeExternalID(tvID, seasonNumber, episodeNumber, language);
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
    public ResultList<Artwork> getEpisodeImages(int tvID, int seasonNumber, int episodeNumber) throws MovieDbException {
        return tmdbEpisodes.getEpisodeImages(tvID, seasonNumber, episodeNumber);
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
    public StatusCode postEpisodeRating(int tvID, int seasonNumber, int episodeNumber, int rating, String sessionID, String guestSessionID) throws MovieDbException {
        return tmdbEpisodes.postEpisodeRating(tvID, seasonNumber, episodeNumber, rating, sessionID, guestSessionID);
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
    public ResultList<Video> getEpisodeVideos(int tvID, int seasonNumber, int episodeNumber, String language) throws MovieDbException {
        return tmdbEpisodes.getEpisodeVideos(tvID, seasonNumber, episodeNumber, language);
    }
//</editor-fold>
}
