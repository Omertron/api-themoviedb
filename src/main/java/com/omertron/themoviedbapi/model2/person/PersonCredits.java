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
package com.omertron.themoviedbapi.model2.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.omertron.themoviedbapi.model2.AbstractJsonMapping;
import com.omertron.themoviedbapi.model2.MediaBasic;
import java.util.List;

/**
 * @author stuart.boston
 * @param <T>
 */
public class PersonCredits<T> extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private int id;
//    @JsonProperty("cast")
    private List<T> cast;
//    @JsonProperty("crew")
    private List<T> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<T> getCast() {
        return cast;
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "media_type",
            defaultImpl = MediaBasic.class
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = CreditMovieBasic.class, name = "movie"),
        @JsonSubTypes.Type(value = CreditTVBasic.class, name = "tv")
    })
    @JsonSetter("cast")
    public void setCast(List<T> cast) {
        this.cast = cast;
    }

    public List<T> getCrew() {
        return crew;
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "media_type",
            defaultImpl = MediaBasic.class
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = CreditMovieBasic.class, name = "movie"),
        @JsonSubTypes.Type(value = CreditTVBasic.class, name = "tv")
    })
    @JsonSetter("crew")
    public void setCrew(List<T> crew) {
        this.crew = crew;
    }
}
