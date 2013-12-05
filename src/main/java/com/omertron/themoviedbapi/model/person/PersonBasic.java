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
package com.omertron.themoviedbapi.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

/**
 * Basic information about a person
 *
 * @author stuart.boston
 */
public class PersonBasic extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("profile_path")
    private String profilePath;
    @JsonProperty("adult")
    private Boolean adult;
    @JsonProperty("popularity")
    private float popularity = 0F;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    /**
     * Is the person an adult performer?
     *
     * @return Null if not set!
     */
    public Boolean isAdult() {
        return adult;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 89 * hash + (this.profilePath != null ? this.profilePath.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonBasic other = (PersonBasic) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!StringUtils.equals(this.name, other.name)) {
            return false;
        }
        return !StringUtils.equals(this.profilePath, other.profilePath);
    }
}
