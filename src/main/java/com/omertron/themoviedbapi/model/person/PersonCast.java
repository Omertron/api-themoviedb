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
import org.apache.commons.lang3.StringUtils;

/**
 * @author Stuart
 */
public class PersonCast extends PersonBasic {

    private static final long serialVersionUID = 2L;

    @JsonProperty("character")
    private String character;
    @JsonProperty("order")
    private int order;
    @JsonProperty("cast_id")
    private int castId;
    @JsonProperty("credit_id")
    private String creditId;

    public String getCharacter() {
        return character;
    }

    public int getOrder() {
        return order;
    }

    public int getCastId() {
        return castId;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCharacter(String character) {
        this.character = StringUtils.trimToEmpty(character);
    }

    /**
     * Set the character name. Used by TV cast
     *
     * @param character
     */
    @JsonProperty("character_name")
    public void setCharacterName(String character) {
        setCharacter(character);
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Set the sort order. Used by TV cast
     *
     * @param order
     */
    @JsonProperty("sort_order")
    public void setSortOrder(int order) {
        setOrder(order);
    }

    public void setCastId(int castId) {
        this.castId = castId;
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
        if (!super.equals(obj)) {
            return false;
        }
        final PersonCast other = (PersonCast) obj;
        if (!StringUtils.equals(this.character, other.character)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        if (this.castId != other.castId) {
            return false;
        }
        return StringUtils.equals(this.creditId, other.creditId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.getId();
        hash = 41 * hash + (this.character != null ? this.character.hashCode() : 0);
        hash = 41 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        hash = 41 * hash + this.order;
        hash = 41 * hash + (this.getProfilePath() != null ? this.getProfilePath().hashCode() : 0);
        return hash;
    }
}
