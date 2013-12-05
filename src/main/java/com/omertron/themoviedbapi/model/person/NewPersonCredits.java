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
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Cast & Crew crews for a person
 *
 * @author Stuart
 */
public class NewPersonCredits extends AbstractJsonMapping {

    private int id;
    @JsonProperty("cast")
    private final List<NewCast> cast = Collections.emptyList();
    @JsonProperty("crew")
    private final List<NewCrew> crew = Collections.emptyList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<NewCast> getCast() {
        return cast;
    }

    public List<NewCrew> getCrew() {
        return crew;
    }

    public void addCast(NewCast cast) {
        this.cast.add(cast);
    }

    public void addCrew(NewCrew crew) {
        this.crew.add(crew);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
                append("id", id).
                append("crew", crew.size()).
                append("cast", cast.size()).
                toString();
    }
}
