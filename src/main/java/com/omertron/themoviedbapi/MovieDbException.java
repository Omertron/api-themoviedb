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
package com.omertron.themoviedbapi;

public class MovieDbException extends Exception {

    private static final long serialVersionUID = -8952129102483143278L;

    public enum MovieDbExceptionType {
        UNKNOWN_CAUSE, INVALID_URL, HTTP_404_ERROR, MOVIE_ID_NOT_FOUND, MAPPING_FAILED, CONNECTION_ERROR, INVALID_IMAGE, AUTHORISATION_FAILURE;
    }

    private final MovieDbExceptionType exceptionType;
    private final String response;

    public MovieDbException(final MovieDbExceptionType exceptionType, final String response) {
        super();
        this.exceptionType = exceptionType;
        this.response = response;
    }

    public MovieDbException(final MovieDbExceptionType exceptionType, final String response, final Throwable cause) {
        super(cause);
        this.exceptionType = exceptionType;
        this.response = response;
    }

    public MovieDbExceptionType getExceptionType() {
        return exceptionType;
    }

    public String getResponse() {
        return response;
    }
}
