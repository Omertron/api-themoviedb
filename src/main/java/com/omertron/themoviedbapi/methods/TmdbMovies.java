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
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.list.UserList;
import com.omertron.themoviedbapi.model.media.AlternativeTitle;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.MediaState;
import com.omertron.themoviedbapi.model.media.Translation;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.movie.ReleaseInfo;
import com.omertron.themoviedbapi.model.review.Review;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.results.WrapperAlternativeTitles;
import com.omertron.themoviedbapi.results.WrapperGenericList;
import com.omertron.themoviedbapi.results.WrapperImages;
import com.omertron.themoviedbapi.results.WrapperMovieKeywords;
import com.omertron.themoviedbapi.results.WrapperReleaseInfo;
import com.omertron.themoviedbapi.results.WrapperTranslations;
import com.omertron.themoviedbapi.results.WrapperVideos;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.PostBody;
import com.omertron.themoviedbapi.tools.PostTools;
import com.omertron.themoviedbapi.tools.TmdbParameters;
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
    public MovieInfo getMovieInfo(int movieId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            MovieInfo movie = MAPPER.readValue(webpage, MovieInfo.class);
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
    public MovieInfo getMovieInfoImdb(String imdbId, String language, String... appendToResponse) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, imdbId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.APPEND, appendToResponse);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            MovieInfo movie = MAPPER.readValue(webpage, MovieInfo.class);
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
     * @return
     * @throws MovieDbException
     */
    public ResultList<AlternativeTitle> getMovieAlternativeTitles(int movieId, String country) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.COUNTRY, country);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.ALT_TITLES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);
        try {
            WrapperAlternativeTitles wrapper = MAPPER.readValue(webpage, WrapperAlternativeTitles.class);
            ResultList<AlternativeTitle> results = new ResultList<>(wrapper.getTitles());
            wrapper.setResultProperties(results);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get alternative titles", url, ex);
        }
    }

    /**
     * Get the cast and crew information for a specific movie id.
     *
     * @param movieId
     * @return
     * @throws MovieDbException
     */
    public MediaCreditList getMovieCredits(int movieId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);

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
     * @return
     * @throws MovieDbException
     */
    public ResultList<Artwork> getMovieImages(int movieId, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.IMAGES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperImages wrapper = MAPPER.readValue(webpage, WrapperImages.class);
            ResultList<Artwork> results = new ResultList<>(wrapper.getAll());
            wrapper.setResultProperties(results);
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
     * @return
     * @throws MovieDbException
     */
    public ResultList<Keyword> getMovieKeywords(int movieId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.KEYWORDS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMovieKeywords wrapper = MAPPER.readValue(webpage, WrapperMovieKeywords.class);
            ResultList<Keyword> results = new ResultList<>(wrapper.getKeywords());
            wrapper.setResultProperties(results);
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
     * @return
     * @throws MovieDbException
     */
    public ResultList<ReleaseInfo> getMovieReleaseInfo(int movieId, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.RELEASES).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperReleaseInfo wrapper = MAPPER.readValue(webpage, WrapperReleaseInfo.class);
            ResultList<ReleaseInfo> results = new ResultList<>(wrapper.getCountries());
            wrapper.setResultProperties(results);
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
     * @return
     * @throws MovieDbException
     */
    public ResultList<Video> getMovieVideos(int movieId, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.VIDEOS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperVideos wrapper = MAPPER.readValue(webpage, WrapperVideos.class);
            ResultList<Video> results = new ResultList<>(wrapper.getVideos());
            wrapper.setResultProperties(results);
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
     * @return
     * @throws MovieDbException
     */
    public ResultList<Translation> getMovieTranslations(int movieId) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.TRANSLATIONS).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperTranslations wrapper = MAPPER.readValue(webpage, WrapperTranslations.class);
            ResultList<Translation> results = new ResultList<>(wrapper.getTranslations());
            wrapper.setResultProperties(results);
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
     * @return
     * @throws MovieDbException
     */
    public ResultList<MovieInfo> getSimilarMovies(int movieId, Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.SIMILAR).buildUrl(parameters);
        WrapperGenericList<MovieInfo> wrapper = processWrapper(getTypeReference(MovieInfo.class), url, "similar movies");
        return wrapper.getResultsList();
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.REVIEWS).buildUrl(parameters);
        WrapperGenericList<Review> wrapper = processWrapper(getTypeReference(Review.class), url, "review");
        return wrapper.getResultsList();
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.ID, movieId);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.LISTS).buildUrl(parameters);
        WrapperGenericList<UserList> wrapper = processWrapper(getTypeReference(UserList.class), url, "movie lists");
        return wrapper.getResultsList();
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
        return getMediaChanges(movieId, startDate, endDate);
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
    public MovieInfo getLatestMovie() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.LATEST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            return MAPPER.readValue(webpage, MovieInfo.class);
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
    public ResultList<MovieInfo> getUpcoming(Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.UPCOMING).buildUrl(parameters);
        WrapperGenericList<MovieInfo> wrapper = processWrapper(getTypeReference(MovieInfo.class), url, "upcoming movies");
        return wrapper.getResultsList();
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.NOW_PLAYING).buildUrl(parameters);
        WrapperGenericList<MovieInfo> wrapper = processWrapper(getTypeReference(MovieInfo.class), url, "now playing movies");
        return wrapper.getResultsList();
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.POPULAR).buildUrl(parameters);
        WrapperGenericList<MovieInfo> wrapper = processWrapper(getTypeReference(MovieInfo.class), url, "popular movie list");
        return wrapper.getResultsList();
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.MOVIE).subMethod(MethodSub.TOP_RATED).buildUrl(parameters);
        WrapperGenericList<MovieInfo> wrapper = processWrapper(getTypeReference(MovieInfo.class), url, "top rated movies");
        return wrapper.getResultsList();
    }

}
