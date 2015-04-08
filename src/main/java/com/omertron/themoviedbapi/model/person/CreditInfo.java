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
package com.omertron.themoviedbapi.model.person;

import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.tv.TVCredit;
import java.io.Serializable;

/**
 * @author stuart.boston
 */
public class CreditInfo extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("id")
    private String id;
    @JsonProperty("credit_type")
    private String creditType;
    @JsonProperty("department")
    private String department;
    @JsonProperty("job")
    private String job;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("person")
    private PersonBasic person;
    @JsonProperty("media")
    private TVCredit media;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public PersonBasic getPerson() {
        return person;
    }

    public void setPerson(PersonBasic person) {
        this.person = person;
    }

    public TVCredit getMedia() {
        return media;
    }

    public void setMedia(TVCredit media) {
        this.media = media;
    }

}
