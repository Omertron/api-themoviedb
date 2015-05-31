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

import com.omertron.themoviedbapi.model.credits.CreditBasic;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.omertron.themoviedbapi.interfaces.Identification;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.util.List;

/**
 * @author stuart.boston
 * @param <T>
 */
public class PersonCreditList<T extends CreditBasic> extends AbstractJsonMapping implements Identification {

    private static final long serialVersionUID = 101L;

    @JsonProperty("id")
    private int id;
    private List<T> cast;
    private List<T> crew;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public List<T> getCast() {
        return cast;
    }

    @JsonSetter("cast")
    public void setCast(List<T> cast) {
        this.cast = cast;
    }

    public List<T> getCrew() {
        return crew;
    }

    @JsonSetter("crew")
    public void setCrew(List<T> crew) {
        this.crew = crew;
    }
}
