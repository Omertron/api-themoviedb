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
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.Keyword;
import com.omertron.themoviedbapi.model.movie.MovieDbBasic;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_ID;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_LANGUAGE;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_PAGE;
import com.omertron.themoviedbapi.wrapper.WrapperKeywordMovies;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the Keywords methods
 *
 * @author stuart.boston
 */
public class TmdbKeywords extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbKeywords.class);
    // API URL Parameters
    private static final String BASE_KEYWORD = "keyword/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbKeywords(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

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
            LOG.warn("Failed to get keyword: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
    public TmdbResultsList<MovieDbBasic> getKeywordMovies(String keywordId, String language, int page) throws MovieDbException {
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
            TmdbResultsList<MovieDbBasic> results = new TmdbResultsList<MovieDbBasic>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get keyword movies: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }

    }
}
