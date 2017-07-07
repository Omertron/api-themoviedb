/*
 *      Copyright (c) 2004-2016 Stuart Boston
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
package com.omertron.themoviedbapi.tools;

import com.omertron.themoviedbapi.MovieDbException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.yamj.api.common.exception.ApiExceptionType;
import org.yamj.api.common.http.DigestedResponse;
import org.yamj.api.common.http.DigestedResponseReader;

/**
 * HTTP tools to aid in processing web requests
 *
 * @author Stuart.Boston
 */
public class HttpTools {

    private final HttpClient httpClient;
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final String APPLICATION_JSON = "application/json";
    private static final long RETRY_DELAY = 1;
    private static final int RETRY_MAX = 5;
    private static final int STATUS_TOO_MANY_REQUESTS = 429;

    public HttpTools(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * GET data from the URL
     *
     * @param url URL to use in the request
     * @return String content
     * @throws MovieDbException exception
     */
    public String getRequest(final URL url) throws MovieDbException {
        try {
            HttpGet httpGet = new HttpGet(url.toURI());
            httpGet.addHeader(HttpHeaders.ACCEPT, APPLICATION_JSON);
            DigestedResponse response = DigestedResponseReader.requestContent(httpClient, httpGet, CHARSET);
            long retryCount = 0L;

            // If we have a 429 response, wait and try again
            while (response.getStatusCode() == STATUS_TOO_MANY_REQUESTS && retryCount++ <= RETRY_MAX) {
                delay(retryCount);

                // Retry the request
                response = DigestedResponseReader.requestContent(httpClient, httpGet, CHARSET);
            }

            return validateResponse(response, url);
        } catch (URISyntaxException | IOException ex) {
            throw new MovieDbException(ApiExceptionType.CONNECTION_ERROR, null, url, ex);
        } catch (RuntimeException ex) {
            throw new MovieDbException(ApiExceptionType.HTTP_503_ERROR, "Service Unavailable", url, ex);
        }
    }

    /**
     * Sleep for a period of time
     *
     * @param multiplier number of seconds to use for delay
     */
    private void delay(long multiplier) {
        try {
            // Wait for the timeout to finish
            Thread.sleep(TimeUnit.SECONDS.toMillis(RETRY_DELAY * multiplier));
        } catch (InterruptedException ex) {
            // Doesn't matter if we're interrupted
        }
    }

    /**
     * Execute a DELETE on the URL
     *
     * @param url URL to use in the request
     * @return String content
     * @throws MovieDbException exception
     */
    public String deleteRequest(final URL url) throws MovieDbException {
        try {
            HttpDelete httpDel = new HttpDelete(url.toURI());
            return validateResponse(DigestedResponseReader.deleteContent(httpClient, httpDel, CHARSET), url);
        } catch (URISyntaxException | IOException ex) {
            throw new MovieDbException(ApiExceptionType.CONNECTION_ERROR, null, url, ex);
        }
    }

    /**
     * POST content to the URL with the specified body
     *
     * @param url URL to use in the request
     * @param jsonBody Body to use in the request
     * @return String content
     * @throws MovieDbException exception
     */
    public String postRequest(final URL url, final String jsonBody) throws MovieDbException {
        try {
            HttpPost httpPost = new HttpPost(url.toURI());
            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
            httpPost.addHeader(HttpHeaders.ACCEPT, APPLICATION_JSON);
            StringEntity params = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
            httpPost.setEntity(params);

            return validateResponse(DigestedResponseReader.postContent(httpClient, httpPost, CHARSET), url);
        } catch (URISyntaxException | IOException ex) {
            throw new MovieDbException(ApiExceptionType.CONNECTION_ERROR, null, url, ex);
        }
    }

    /**
     * Check the status codes of the response and throw exceptions if needed
     *
     * @param response DigestedResponse to process
     * @param url URL for notification purposes
     * @return String content
     * @throws MovieDbException exception
     */
    private String validateResponse(final DigestedResponse response, final URL url) throws MovieDbException {
        if (response.getStatusCode() == 0) {
            throw new MovieDbException(ApiExceptionType.CONNECTION_ERROR, response.getContent(), response.getStatusCode(), url, null);
        } else if (response.getStatusCode() >= HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            throw new MovieDbException(ApiExceptionType.HTTP_503_ERROR, response.getContent(), response.getStatusCode(), url, null);
        } else if (response.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES) {
            throw new MovieDbException(ApiExceptionType.HTTP_404_ERROR, response.getContent(), response.getStatusCode(), url, null);
        }

        return response.getContent();
    }

}
