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
package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Stuart
 */
public class PersonCast extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;

    /*
     * Properties
     */
    @JsonProperty("id")
    private int id;
    @JsonProperty("character")
    private String character;
    @JsonProperty("name")
    private String name;
    @JsonProperty("order")
    private int order;
    @JsonProperty("profile_path")
    private String profilePath;
    @JsonProperty("cast_id")
    private int castId;
    @JsonProperty("credit_id")
    private String creditId;

    public String getCharacter() {
        return character;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public int getCastId() {
        return castId;
    }

    public void setCharacter(String character) {
        this.character = StringUtils.trimToEmpty(character);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = StringUtils.trimToEmpty(name);
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = StringUtils.trimToEmpty(profilePath);
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonCast other = (PersonCast) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.character == null) ? (other.character != null) : !this.character.equals(other.character)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        if ((this.profilePath == null) ? (other.profilePath != null) : !this.profilePath.equals(other.profilePath)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        hash = 41 * hash + (this.character != null ? this.character.hashCode() : 0);
        hash = 41 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 41 * hash + this.order;
        hash = 41 * hash + (this.profilePath != null ? this.profilePath.hashCode() : 0);
        return hash;
    }
}
