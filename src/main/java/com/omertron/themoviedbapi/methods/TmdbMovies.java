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
import static com.omertron.themoviedbapi.methods.AbstractMethod.MAPPER;
import com.omertron.themoviedbapi.model.StatusCode;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.list.UserList;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.movie.AlternativeTitle;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.model.movie.ReleaseInfo;
import com.omertron.themoviedbapi.model.movie.Translation;
import com.omertron.themoviedbapi.model.movie.Video;
import com.omertron.themoviedbapi.model.review.Review;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.PostBody;
import com.omertron.themoviedbapi.tools.PostTools;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperAlternativeTitles;
import com.omertron.themoviedbapi.wrapper.WrapperChanges;
import com.omertron.themoviedbapi.wrapper.WrapperGenericList;
import com.omertron.themoviedbapi.wrapper.WrapperImages;
import com.omertron.themoviedbapi.wrapper.WrapperMovieKeywords;
import com.omertron.themoviedbapi.wrapper.WrapperReleaseInfo;
import com.omertron.themoviedbapi.wrapper.WrapperTranslations;
import com.omertron.themoviedbapi.wrapper.WrapperVideos;
import java.io.IOException;
import java.net.URL;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the Movie Methods
 *
 * @author stuart.boston
 */
public class TmdbMovies extends AbstractMethod {

    private static final int RATING_MAX = 10;

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbMovies(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            MovieDb movie = MAPPER.readValue(webpage, MovieDb.class);
            if (movie == null || movie.getId() == 0) {
                throw new MovieDbException(ApiExceptionType.ID_NOT_FOUND, "No movie found for ID: " + movieId, url);
            }
            return movie;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get info", url, ex);
        }
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, imdbId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            MovieDb movie = MAPPER.readValue(webpage, MovieDb.class);
            if (movie == null || movie.getId() == 0) {
                throw new MovieDbException(ApiExceptionType.ID_NOT_FOUND, "No movie found for IMDB ID: " + imdbId, url);
            }
            return movie;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get info (IMDB)", url, ex);
        }
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.SESSION_ID, sessionId);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.ACCOUNT_STATES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, MediaState.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get account state", url, ex);
        }
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.COUNTRY, country);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.ALT_TITLES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            WrapperAlternativeTitles wrapper = MAPPER.readValue(webpage, WrapperAlternativeTitles.class);
            TmdbResultsList<AlternativeTitle> results = new TmdbResultsList<AlternativeTitle>(wrapper.getTitles());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get alternative titles", url, ex);
        }
    }

    /**
     * Get the cast and crew information for a specific movie id.
     *
     * @param movieId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public MediaCreditList getMovieCredits(int movieId, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.CREDITS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            return MAPPER.readValue(webpage, MediaCreditList.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get credits", url, ex);
        }
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.IMAGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperImages wrapper = MAPPER.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get images", url, ex);
        }
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.KEYWORDS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovieKeywords wrapper = MAPPER.readValue(webpage, WrapperMovieKeywords.class);
            TmdbResultsList<Keyword> results = new TmdbResultsList<Keyword>(wrapper.getKeywords());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get keywords", url, ex);
        }
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.RELEASES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperReleaseInfo wrapper = MAPPER.readValue(webpage, WrapperReleaseInfo.class);
            TmdbResultsList<ReleaseInfo> results = new TmdbResultsList<ReleaseInfo>(wrapper.getCountries());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get release information", url, ex);
        }
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
    public TmdbResultsList<Video> getMovieVideos(int movieId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.VIDEOS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperVideos wrapper = MAPPER.readValue(webpage, WrapperVideos.class);
            TmdbResultsList<Video> results = new TmdbResultsList<Video>(wrapper.getVideos());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get videos", url, ex);
        }
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.TRANSLATIONS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperTranslations wrapper = MAPPER.readValue(webpage, WrapperTranslations.class);
            TmdbResultsList<Translation> results = new TmdbResultsList<Translation>(wrapper.getTranslations());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get translations", url, ex);
        }
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
    public TmdbResultsList<MovieDb> getSimilarMovies(int movieId, Integer page, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.SIMILAR).buildUrl(parameters);
        WrapperGenericList<MovieDb> wrapper = processWrapper(getTypeReference(MovieDb.class), url, "similar movies");
        return wrapper.getTmdbResultsList();
    }

    /**
     * Get the reviews for a particular movie id.
     *
     * @param movieId
     * @param page
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Review> getMovieReviews(int movieId, Integer page, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.REVIEWS).buildUrl(parameters);
        WrapperGenericList<Review> wrapper = processWrapper(getTypeReference(Review.class), url, "review");
        return wrapper.getTmdbResultsList();
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
    public TmdbResultsList<UserList> getMovieLists(int movieId, Integer page, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.LISTS).buildUrl(parameters);
        WrapperGenericList<UserList> wrapper = processWrapper(getTypeReference(UserList.class), url, "movie lists");
        return wrapper.getTmdbResultsList();
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
    public WrapperChanges getMovieChanges(int movieId, String startDate, String endDate) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.START_DATE, startDate);
        parameters.add(Param.END_DATE, endDate);

        URL url = new ApiUrl(apiKey, MethodBase.PERSON).subMethod(MethodSub.CHANGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, WrapperChanges.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get changes", url, ex);
        }
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
        if (rating < 0 || rating > RATING_MAX) {
            throw new MovieDbException(ApiExceptionType.UNKNOWN_CAUSE, "Rating out of range");
        }

        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.SESSION_ID, sessionId);
        parameters.add(Param.GUEST_SESSION_ID, guestSessionId);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.RATING).buildUrl(parameters);

        String jsonBody = new PostTools()
                .add(PostBody.VALUE, rating)
                .build();
        String webpage = httpTools.postRequest(url, jsonBody);

        try {
            return MAPPER.readValue(webpage, StatusCode.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to post rating", url, ex);
        }
    }

    /**
     * This method is used to retrieve the newest movie that was added to TMDb.
     *
     * @return
     * @throws MovieDbException
     */
    public MovieDb getLatestMovie() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.LATEST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, MovieDb.class);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get latest movie", url, ex);
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
    public TmdbResultsList<MovieDb> getUpcoming(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.UPCOMING).buildUrl(parameters);
        WrapperGenericList<MovieDb> wrapper = processWrapper(getTypeReference(MovieDb.class), url, "upcoming movies");
        return wrapper.getTmdbResultsList();
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
    public TmdbResultsList<MovieDb> getNowPlayingMovies(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.NOW_PLAYING).buildUrl(parameters);
        WrapperGenericList<MovieDb> wrapper = processWrapper(getTypeReference(MovieDb.class), url, "now playing movies");
        return wrapper.getTmdbResultsList();
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
    public TmdbResultsList<MovieDb> getPopularMovieList(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.POPULAR).buildUrl(parameters);
        WrapperGenericList<MovieDb> wrapper = processWrapper(getTypeReference(MovieDb.class), url, "popular movie list");
        return wrapper.getTmdbResultsList();
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
    public TmdbResultsList<MovieDb> getTopRatedMovies(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.TOP_RATED).buildUrl(parameters);
        WrapperGenericList<MovieDb> wrapper = processWrapper(getTypeReference(MovieDb.class), url, "top rated movies");
        return wrapper.getTmdbResultsList();
    }

}
