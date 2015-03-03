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
import com.omertron.themoviedbapi.enumeration.SearchType;
import static com.omertron.themoviedbapi.methods.AbstractMethod.MAPPER;
import com.omertron.themoviedbapi.model2.movie.MovieDb;
import com.omertron.themoviedbapi.model2.media.MediaBasic;
import com.omertron.themoviedbapi.model2.keyword.Keyword;
import com.omertron.themoviedbapi.model2.collection.Collection;
import com.omertron.themoviedbapi.model2.company.Company;
import com.omertron.themoviedbapi.model2.list.UserList;
import com.omertron.themoviedbapi.model2.person.PersonFind;
import com.omertron.themoviedbapi.model2.tv.TVBasic;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.wrapper.WrapperGenericList;
import com.omertron.themoviedbapi.wrapper.WrapperMultiSearch;
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
     * Search Companies.
     *
     * You can use this method to search for production companies that are part of TMDb. The company IDs will map to those returned
     * on movie calls.
     *
     * http://help.themoviedb.org/kb/api/search-companies
     *
     * @param query
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Company> searchCompanies(String query, Integer page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.COMPANY).buildUrl(parameters);
        WrapperGenericList<Company> wrapper = processWrapper(getTypeReference(Company.class), url, "company");
        TmdbResultsList<Company> results = new TmdbResultsList<Company>(wrapper.getResults());
        results.copyWrapper(wrapper);
        return results;
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
    public TmdbResultsList<Collection> searchCollection(String query, Integer page, String language) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.COLLECTION).buildUrl(parameters);
        WrapperGenericList<Collection> wrapper = processWrapper(getTypeReference(Collection.class), url, "collection");
        TmdbResultsList<Collection> results = new TmdbResultsList<Collection>(wrapper.getResults());
        results.copyWrapper(wrapper);
        return results;
    }

    /**
     * Search for keywords by name
     *
     * @param query
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Keyword> searchKeyword(String query, Integer page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.KEYWORD).buildUrl(parameters);
        WrapperGenericList<Keyword> wrapper = processWrapper(getTypeReference(Keyword.class), url, "keyword");
        TmdbResultsList<Keyword> results = new TmdbResultsList<Keyword>(wrapper.getResults());
        results.copyWrapper(wrapper);
        return results;
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
    public TmdbResultsList<UserList> searchList(String query, Integer page, Boolean includeAdult) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.INCLUDE_ADULT, includeAdult);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.LIST).buildUrl(parameters);
        WrapperGenericList<UserList> wrapper = processWrapper(getTypeReference(UserList.class), url, "list");
        TmdbResultsList<UserList> results = new TmdbResultsList<UserList>(wrapper.getResults());
        results.copyWrapper(wrapper);
        return results;
    }

    /**
     * Search Movies This is a good starting point to start finding movies on TMDb.
     *
     * @param query
     * @param searchYear Limit the search to the provided year. Zero (0) will get all years
     * @param language The language to include. Can be blank/null.
     * @param includeAdult true or false to include adult titles in the search
     * @param page The page of results to return. 0 to get the default (first page)
     * @param primaryReleaseYear
     * @param searchType
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MovieDb> searchMovie(String query,
            Integer page,
            String language,
            Boolean includeAdult,
            Integer searchYear,
            Integer primaryReleaseYear,
            SearchType searchType) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.ADULT, includeAdult);
        parameters.add(Param.YEAR, searchYear);
        parameters.add(Param.PRIMARY_RELEASE_YEAR, primaryReleaseYear);
        if (searchType != null) {
            parameters.add(Param.SEARCH_TYPE, searchType.getPropertyString());
        }
        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.MOVIE).buildUrl(parameters);

        WrapperGenericList<MovieDb> wrapper = processWrapper(getTypeReference(MovieDb.class), url, "movie");
        TmdbResultsList<MovieDb> results = new TmdbResultsList<MovieDb>(wrapper.getResults());
        results.copyWrapper(wrapper);
        return results;
    }

    /**
     * Search the movie, tv show and person collections with a single query.
     *
     * Each item returned in the result array has a media_type field that maps to either movie, tv or person.
     *
     * Each mapped result is the same response you would get from each independent search
     *
     * @param query
     * @param page
     * @param language
     * @param includeAdult
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<MediaBasic> searchMulti(String query, Integer page, String language, Boolean includeAdult) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.ADULT, includeAdult);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.MULTI).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMultiSearch wrapper = MAPPER.readValue(webpage, WrapperMultiSearch.class);
            TmdbResultsList<MediaBasic> results = new TmdbResultsList<MediaBasic>(null);
            results.getResults().addAll(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get multi search", url, ex);
        }    }

    /**
     * This is a good starting point to start finding people on TMDb.
     *
     * The idea is to be a quick and light method so you can iterate through people quickly.
     *
     * @param query
     * @param includeAdult
     * @param page
     * @param searchType
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<PersonFind> searchPeople(String query, Integer page, Boolean includeAdult, SearchType searchType) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.ADULT, includeAdult);
        parameters.add(Param.PAGE, page);
        if (searchType != null) {
            parameters.add(Param.SEARCH_TYPE, searchType.getPropertyString());
        }
        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.PERSON).buildUrl(parameters);

        WrapperGenericList<PersonFind> wrapper = processWrapper(getTypeReference(PersonFind.class), url, "person");
        TmdbResultsList<PersonFind> results = new TmdbResultsList<PersonFind>(wrapper.getResults());
        results.copyWrapper(wrapper);
        return results;
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
    public TmdbResultsList<TVBasic> searchTV(String query, Integer page, String language, Integer firstAirDateYear, SearchType searchType) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.FIRST_AIR_DATE_YEAR, firstAirDateYear);
        if (searchType != null) {
            parameters.add(Param.SEARCH_TYPE, searchType.getPropertyString());
        }
        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).setSubMethod(MethodSub.MOVIE).buildUrl(parameters);

        WrapperGenericList<TVBasic> wrapper = processWrapper(getTypeReference(TVBasic.class), url, "TV Show");
        TmdbResultsList<TVBasic> results = new TmdbResultsList<TVBasic>(wrapper.getResults());
        results.copyWrapper(wrapper);
        return results;
    }

}
