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
import com.omertron.themoviedbapi.model.collection.Collection;
import com.omertron.themoviedbapi.model.company.Company;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.list.UserList;
import com.omertron.themoviedbapi.model.media.MediaBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.HttpTools;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import com.omertron.themoviedbapi.tools.Param;
import com.omertron.themoviedbapi.tools.TmdbParameters;
import com.omertron.themoviedbapi.results.WrapperGenericList;
import com.omertron.themoviedbapi.results.WrapperMultiSearch;
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
    public ResultList<Company> searchCompanies(String query, Integer page) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).subMethod(MethodSub.COMPANY).buildUrl(parameters);
        WrapperGenericList<Company> wrapper = processWrapper(getTypeReference(Company.class), url, "company");
        return wrapper.getResultsList();
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).subMethod(MethodSub.COLLECTION).buildUrl(parameters);
        WrapperGenericList<Collection> wrapper = processWrapper(getTypeReference(Collection.class), url, "collection");
        return wrapper.getResultsList();
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).subMethod(MethodSub.KEYWORD).buildUrl(parameters);
        WrapperGenericList<Keyword> wrapper = processWrapper(getTypeReference(Keyword.class), url, "keyword");
        return wrapper.getResultsList();
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.INCLUDE_ADULT, includeAdult);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).subMethod(MethodSub.LIST).buildUrl(parameters);
        WrapperGenericList<UserList> wrapper = processWrapper(getTypeReference(UserList.class), url, "list");
        return wrapper.getResultsList();
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
    public ResultList<MovieInfo> searchMovie(String query,
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
        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).subMethod(MethodSub.MOVIE).buildUrl(parameters);

        WrapperGenericList<MovieInfo> wrapper = processWrapper(getTypeReference(MovieInfo.class), url, "movie");
        return wrapper.getResultsList();
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
    public ResultList<MediaBasic> searchMulti(String query, Integer page, String language, Boolean includeAdult) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.ADULT, includeAdult);

        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).subMethod(MethodSub.MULTI).buildUrl(parameters);
        String webpage = httpTools.getRequest(url);

        try {
            WrapperMultiSearch wrapper = MAPPER.readValue(webpage, WrapperMultiSearch.class);
            ResultList<MediaBasic> results = new ResultList<>();
            results.getResults().addAll(wrapper.getResults());
            wrapper.setResultProperties(results);
            return results;
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get multi search", url, ex);
        }
    }

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
    public ResultList<PersonFind> searchPeople(String query, Integer page, Boolean includeAdult, SearchType searchType) throws MovieDbException {
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.ADULT, includeAdult);
        parameters.add(Param.PAGE, page);
        if (searchType != null) {
            parameters.add(Param.SEARCH_TYPE, searchType.getPropertyString());
        }
        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).subMethod(MethodSub.PERSON).buildUrl(parameters);
        WrapperGenericList<PersonFind> wrapper = processWrapper(getTypeReference(PersonFind.class), url, "person");
        return wrapper.getResultsList();
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
        TmdbParameters parameters = new TmdbParameters();
        parameters.add(Param.QUERY, query);
        parameters.add(Param.PAGE, page);
        parameters.add(Param.LANGUAGE, language);
        parameters.add(Param.FIRST_AIR_DATE_YEAR, firstAirDateYear);
        if (searchType != null) {
            parameters.add(Param.SEARCH_TYPE, searchType.getPropertyString());
        }
        URL url = new ApiUrl(apiKey, MethodBase.SEARCH).subMethod(MethodSub.TV).buildUrl(parameters);
        WrapperGenericList<TVBasic> wrapper = processWrapper(getTypeReference(TVBasic.class), url, "TV Show");
        return wrapper.getResultsList();
    }

}
