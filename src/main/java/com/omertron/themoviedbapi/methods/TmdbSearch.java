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
import static com.omertron.themoviedbapi.methods.AbstractMethod.MAPPER;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_ADULT;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_FIRST_AIR_DATE_YEAR;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_LANGUAGE;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_PAGE;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_QUERY;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_SEARCH_TYPE;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_YEAR;
import com.omertron.themoviedbapi.model.Collection;
import com.omertron.themoviedbapi.model.Company;
import com.omertron.themoviedbapi.model.Keyword;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.model.movie.MovieList;
import com.omertron.themoviedbapi.model.person.PersonMovieOld;
import com.omertron.themoviedbapi.model.tv.TVSeriesBasic;
import com.omertron.themoviedbapi.model.type.SearchType;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.wrapper.WrapperCollection;
import com.omertron.themoviedbapi.wrapper.WrapperCompany;
import com.omertron.themoviedbapi.wrapper.WrapperKeywords;
import com.omertron.themoviedbapi.wrapper.movie.WrapperMovie;
import com.omertron.themoviedbapi.wrapper.movie.WrapperMovieList;
import com.omertron.themoviedbapi.wrapper.person.WrapperPerson;
import com.omertron.themoviedbapi.wrapper.tv.WrapperTVSeries;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the Search methods
 *
 * @author stuart.boston
 */
public class TmdbSearch extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbSearch.class);
    // API URL Parameters
    private static final String BASE_SEARCH = "search/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbSearch(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

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
            WrapperMovie wrapper = MAPPER.readValue(webpage, WrapperMovie.class);
            TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getMovies());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find movie: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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

        String webpage = requestWebPage(url);
        try {
            WrapperCollection wrapper = MAPPER.readValue(webpage, WrapperCollection.class);
            TmdbResultsList<Collection> results = new TmdbResultsList<Collection>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find collection: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
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
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "tv");
        if (StringUtils.isNotBlank(name)) {
            apiUrl.addArgument(PARAM_QUERY, name);
        }

        if (searchYear > 0) {
            apiUrl.addArgument(PARAM_FIRST_AIR_DATE_YEAR, Integer.toString(searchYear));
        }

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, Integer.toString(page));
        }

        if (searchType != null) {
            apiUrl.addArgument(PARAM_SEARCH_TYPE, searchType.toString());
        }

        URL url = apiUrl.buildUrl();

        String webpage = requestWebPage(url);
        try {
            WrapperTVSeries wrapper = MAPPER.readValue(webpage, WrapperTVSeries.class);
            TmdbResultsList<TVSeriesBasic> results = new TmdbResultsList<TVSeriesBasic>(wrapper.getSeries());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find TV Series: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
    public TmdbResultsList<PersonMovieOld> searchPeople(String personName, boolean includeAdult, int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_SEARCH, "person");
        apiUrl.addArgument(PARAM_QUERY, personName);
        apiUrl.addArgument(PARAM_ADULT, includeAdult);

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperPerson wrapper = MAPPER.readValue(webpage, WrapperPerson.class);
            TmdbResultsList<PersonMovieOld> results = new TmdbResultsList<PersonMovieOld>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find person: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
            WrapperMovieList wrapper = MAPPER.readValue(webpage, WrapperMovieList.class);
            TmdbResultsList<MovieList> results = new TmdbResultsList<MovieList>(wrapper.getMovieList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find list: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
            WrapperCompany wrapper = MAPPER.readValue(webpage, WrapperCompany.class);
            TmdbResultsList<Company> results = new TmdbResultsList<Company>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find company: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
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
            WrapperKeywords wrapper = MAPPER.readValue(webpage, WrapperKeywords.class);
            TmdbResultsList<Keyword> results = new TmdbResultsList<Keyword>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to find keyword: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
}
