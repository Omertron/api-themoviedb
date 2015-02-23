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
import com.omertron.themoviedbapi.model2.collection.Collection;
import com.omertron.themoviedbapi.model.Company;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.model.MovieList;
import com.omertron.themoviedbapi.model.person.Person;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperCollection;
import com.omertron.themoviedbapi.wrapper.WrapperCompany;
import com.omertron.themoviedbapi.wrapper.WrapperKeywords;
import com.omertron.themoviedbapi.wrapper.WrapperMovie;
import com.omertron.themoviedbapi.wrapper.WrapperMovieList;
import com.omertron.themoviedbapi.wrapper.WrapperPerson;
import java.io.IOException;
import java.net.URL;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the Search Methods
 *
 * @author stuart.boston
 */
public class TmdbSearch extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbSearch(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
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
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to find movie", url, ex);
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
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to find collection", url, ex);
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
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to find person", url, ex);
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
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to find list", url, ex);
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
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to find company", url, ex);
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
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to find keyword", url, ex);
        }
    }

}
