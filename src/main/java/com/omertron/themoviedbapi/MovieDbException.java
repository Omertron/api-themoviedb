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
package com.omertron.themoviedbapi;

import java.net.URL;

public class MovieDbException extends Exception {

    private static final long serialVersionUID = 1L;

    public enum MovieDbExceptionType {
        /*
         * Unknown error occured
         */
        UNKNOWN_CAUSE,
        /*
         * URL is invalid
         */
        INVALID_URL,
        /*
         * Page not found
         */
        HTTP_404_ERROR,
        /*
         * The movie id was not found
         */
        MOVIE_ID_NOT_FOUND,
        /*
         * Mapping failed from target to internal onbjects
         */
        MAPPING_FAILED,
        /*
         * Error connecting to the service
         */
        CONNECTION_ERROR,
        /*
         * Image was invalid
         */
        INVALID_IMAGE,
        /*
         * Autorisation rejected
         */
        AUTHORISATION_FAILURE,
        /*
         * Service Unavailable, usually temporary
         */
        HTTP_503_ERROR;
    }

    private final MovieDbExceptionType exceptionType;
    private final String response;
    private final String url;

    public MovieDbException(final MovieDbExceptionType exceptionType, final String response, final String url) {
        super();
        this.exceptionType = exceptionType;
        this.response = response;
        this.url = url;
    }
    
    public MovieDbException(final MovieDbExceptionType exceptionType, final String response, final URL url) {
      super();
      this.exceptionType = exceptionType;
      this.response = response;
      this.url = url.toExternalForm();
  }

    public MovieDbException(final MovieDbExceptionType exceptionType, final String response, final String url, final Throwable cause) {
        super(cause);
        this.exceptionType = exceptionType;
        this.response = response;
        this.url = url;
    }
    
    public MovieDbException(final MovieDbExceptionType exceptionType, final String response, final URL url, final Throwable cause) {
      super(cause);
      this.exceptionType = exceptionType;
      this.response = response;
      this.url = url.toExternalForm();
  }

    public MovieDbExceptionType getExceptionType() {
        return exceptionType;
    }

    public String getResponse() {
        return response;
    }
    
    public String getUrl(){
      return url;
    }
}
