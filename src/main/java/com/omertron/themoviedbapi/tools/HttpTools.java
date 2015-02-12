package com.omertron.themoviedbapi.tools;

import com.omertron.themoviedbapi.MovieDbException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
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

    private final CloseableHttpClient httpClient;
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final int HTTP_STATUS_300 = 300;
    private static final int HTTP_STATUS_500 = 500;

    public HttpTools(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * GET data from the URL
     *
     * @param url URL to use in the request
     * @return String content
     * @throws MovieDbException
     */
    public String getRequest(final URL url) throws MovieDbException {
        try {
            HttpGet httpGet = new HttpGet(url.toURI());
            httpGet.addHeader("accept", "application/json");
            return validateResponse(DigestedResponseReader.requestContent(httpClient, httpGet, CHARSET), url);
        } catch (URISyntaxException ex) {
            throw new MovieDbException(ApiExceptionType.CONNECTION_ERROR, null, url, ex);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.CONNECTION_ERROR, null, url, ex);
        } catch (RuntimeException ex) {
            throw new MovieDbException(ApiExceptionType.HTTP_503_ERROR, "Service Unavailable", url, ex);
        }
    }

    /**
     * Execute a DELETE on the URL
     *
     * @param url URL to use in the request
     * @return String content
     * @throws MovieDbException
     */
    public String deleteRequest(final URL url) throws MovieDbException {
        try {
            HttpDelete httpDel = new HttpDelete(url.toURI());
            return validateResponse(DigestedResponseReader.deleteContent(httpClient, httpDel, CHARSET), url);
        } catch (URISyntaxException ex) {
            throw new MovieDbException(ApiExceptionType.CONNECTION_ERROR, null, url, ex);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.CONNECTION_ERROR, null, url, ex);
        }
    }

    /**
     * POST content to the URL with the specified body
     *
     * @param url URL to use in the request
     * @param jsonBody Body to use in the request
     * @return String content
     * @throws MovieDbException
     */
    public String postRequest(final URL url, final String jsonBody) throws MovieDbException {
        try {
            HttpPost httpPost = new HttpPost(url.toURI());
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
            StringEntity params = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
            httpPost.setEntity(params);

            return validateResponse(DigestedResponseReader.postContent(httpClient, httpPost, CHARSET), url);
        } catch (URISyntaxException ex) {
            throw new MovieDbException(ApiExceptionType.CONNECTION_ERROR, null, url, ex);
        } catch (IOException ex) {
            throw new MovieDbException(ApiExceptionType.CONNECTION_ERROR, null, url, ex);
        }
    }

    /**
     * Check the status codes of the response and throw exceptions if needed
     *
     * @param response DigestedResponse to process
     * @param url URL for notification purposes
     * @return String content
     * @throws MovieDbException
     */
    private String validateResponse(final DigestedResponse response, final URL url) throws MovieDbException {
        if (response.getStatusCode() >= HTTP_STATUS_500) {
            throw new MovieDbException(ApiExceptionType.HTTP_503_ERROR, response.getContent(), response.getStatusCode(), url, null);
        } else if (response.getStatusCode() >= HTTP_STATUS_300) {
            throw new MovieDbException(ApiExceptionType.HTTP_404_ERROR, response.getContent(), response.getStatusCode(), url, null);
        }

        return response.getContent();
    }

}
