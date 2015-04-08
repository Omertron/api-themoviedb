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
package com.omertron.themoviedbapi.model.authentication;

import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class TokenAuthorisation extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 100L;
    @JsonProperty("expires_at")
    private String expires;
    @JsonProperty("request_token")
    private String requestToken;
    @JsonProperty("success")
    private Boolean success;

    public String getExpires() {
        return expires;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
