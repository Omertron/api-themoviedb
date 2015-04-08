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

public class TokenSession extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 100L;
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status_code")
    private String statusCode;
    @JsonProperty("status_message")
    private String statusMessage;
    @JsonProperty("guest_session_id")
    private String guestSessionId;
    @JsonProperty("expires_at")
    private String expiresAt;

    public String getSessionId() {
        return sessionId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getGuestSessionId() {
        return guestSessionId;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setGuestSessionId(String guestSessionId) {
        this.guestSessionId = guestSessionId;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }
}
