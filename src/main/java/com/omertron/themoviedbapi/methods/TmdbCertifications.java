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
import com.omertron.themoviedbapi.results.ResultsMap;
import com.omertron.themoviedbapi.tools.HttpTools;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.omertron.themoviedbapi.model.Certification;
import com.omertron.themoviedbapi.tools.ApiUrl;
import com.omertron.themoviedbapi.tools.MethodBase;
import com.omertron.themoviedbapi.tools.MethodSub;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Class to hold the Certification Methods
 *
 * @author stuart.boston
 */
public class TmdbCertifications extends AbstractMethod {

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpTools
     */
    public TmdbCertifications(String apiKey, HttpTools httpTools) {
        super(apiKey, httpTools);
    }

    /**
     * Get a list of movies certification.
     *
     * @return
     * @throws MovieDbException
     */
    public ResultsMap<String, List<Certification>> getMoviesCertification() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.CERTIFICATION).subMethod(MethodSub.MOVIE_LIST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            JsonNode node = MAPPER.readTree(webpage);
            Map<String, List<Certification>> results = MAPPER.readValue(node.elements().next().traverse(), new TypeReference<Map<String, List<Certification>>>() {
            });
            return new ResultsMap<>(results);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get movie certifications", url, ex);
        }
    }

    /**
     * Get a list of tv certification.
     *
     * @return
     * @throws MovieDbException
     */
    public ResultsMap<String, List<Certification>> getTvCertification() throws MovieDbException {
        URL url = new ApiUrl(apiKey, MethodBase.CERTIFICATION).subMethod(MethodSub.TV_LIST).buildUrl();
        String webpage = httpTools.getRequest(url);

        try {
            JsonNode node = MAPPER.readTree(webpage);
            Map<String, List<Certification>> results = MAPPER.readValue(node.elements().next().traverse(), new TypeReference<Map<String, List<Certification>>>() {
            });
            return new ResultsMap<>(results);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.MAPPING_FAILED, "Failed to get TV certifications", url, ex);
        }
    }
}
