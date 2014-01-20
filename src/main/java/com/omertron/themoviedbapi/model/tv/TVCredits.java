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
package com.omertron.themoviedbapi.model.tv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.omertron.themoviedbapi.model.person.PersonCast;
import com.omertron.themoviedbapi.model.person.PersonCrew;
import java.util.Collections;
import java.util.List;

/**
 * List of Credits for TV
 *
 * @author stuart.boston
 */
public class TVCredits extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private int id;
    @JsonProperty("cast")
    private List<PersonCast> cast = Collections.emptyList();
    @JsonProperty("crew")
    private List<PersonCrew> crew = Collections.emptyList();
    @JsonProperty("guest_stars")
    private List<PersonCast> guestStar = Collections.emptyList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PersonCast> getCast() {
        return cast;
    }

    public void setCast(List<PersonCast> cast) {
        this.cast = cast;
    }

    public List<PersonCrew> getCrew() {
        return crew;
    }

    public void setCrew(List<PersonCrew> crew) {
        this.crew = crew;
    }

    public List<PersonCast> getGuestStar() {
        return guestStar;
    }

    public void setGuestStar(List<PersonCast> guestStar) {
        this.guestStar = guestStar;
    }
}
