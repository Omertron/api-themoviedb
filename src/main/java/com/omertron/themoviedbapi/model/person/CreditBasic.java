
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.omertron.themoviedbapi.enumeration.CreditType;
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.interfaces.IIdentification;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;

/**
 * @author stuart.boston
 */
public class CreditBasic extends AbstractJsonMapping implements IIdentification {

    private static final long serialVersionUID = 1L;

    private CreditType creditType;
    private MediaType mediaType;

    @JsonProperty("credit_id")
    private String creditId;
    @JsonProperty("id")
    private int id;
    @JsonProperty("poster_path")
    private String posterPath;

    //cast
    @JsonProperty("character")
    private String character;
    //crew
    @JsonProperty("department")
    private String department;
    @JsonProperty("job")
    private String job;

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    @JsonSetter("media_type")
    public void setMediaType(String mediaType) {
        this.mediaType = MediaType.fromString(mediaType);
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
        setCreditType(CreditType.CAST);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
        setCreditType(CreditType.CREW);
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
        setCreditType(CreditType.CREW);
    }

}
