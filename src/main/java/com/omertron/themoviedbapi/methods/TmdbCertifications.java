/*
 *      Copyright (c) 2004-2014 Stuart Boston
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.omertron.themoviedbapi.MovieDbException;
import static com.omertron.themoviedbapi.methods.AbstractMethod.MAPPER;
import com.omertron.themoviedbapi.model.Certification;
import com.omertron.themoviedbapi.results.TmdbResultsMap;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Class to hold the Certifications methods
 *
 * @author stuart.boston
 */
public class TmdbCertifications extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbCertifications.class);
    // API URL Parameters
    private static final String BASE_CERTIFICATION = "certification/";

    /**
     * Constructor
     *
     * @param apiKey
     * @param httpClient
     */
    public TmdbCertifications(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * Get a list of movies certification.
     *
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsMap<String, List<Certification>> getMoviesCertification() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_CERTIFICATION, "/movie/list");

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);
        try {
            JsonNode node = MAPPER.readTree(webpage);
            Map<String, List<Certification>> results = MAPPER.readValue(node.elements().next().traverse(), new TypeReference<Map<String, List<Certification>>>(){});
            return new TmdbResultsMap<String, List<Certification>>(results);
        } catch (IOException ex) {
            LOG.warn("Failed to get movie certifications: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get a list of tv certification.
     *
     * @return 
     * @throws MovieDbException 
     */
    public TmdbResultsMap<String, List<Certification>> getTvCertification() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_CERTIFICATION, "/tv/list");

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);
        try {
            JsonNode node = MAPPER.readTree(webpage);
            Map<String, List<Certification>>  results = MAPPER.readValue(node.elements().next().traverse(), new TypeReference<Map<String, List<Certification>>>(){});
            return new TmdbResultsMap<String, List<Certification>>(results);
        } catch (IOException ex) {
            LOG.warn("Failed to get movie certifications: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
}
