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
package com.omertron.themoviedbapi.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.Person;
import com.omertron.themoviedbapi.model.PersonCast;
import com.omertron.themoviedbapi.model.PersonCrew;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stuart
 */
public class WrapperMovieCasts extends AbstractWrapperId implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("cast")
    private List<PersonCast> cast;
    @JsonProperty("crew")
    private List<PersonCrew> crew;

    public List<PersonCast> getCast() {
        return cast;
    }

    public List<PersonCrew> getCrew() {
        return crew;
    }

    public void setCast(List<PersonCast> cast) {
        this.cast = cast;
    }

    public void setCrew(List<PersonCrew> crew) {
        this.crew = crew;
    }

    public List<Person> getAll() {
        List<Person> people = new ArrayList<Person>();

        // Add a cast member
        for (PersonCast member : cast) {
            Person person = new Person();
            person.addCast(member.getId(), member.getName(), member.getProfilePath(), member.getCharacter(), member.getOrder());
            people.add(person);
        }

        // Add a crew member
        for (PersonCrew member : crew) {
            Person person = new Person();
            person.addCrew(member.getId(), member.getName(), member.getProfilePath(), member.getDepartment(), member.getJob());
            people.add(person);
        }

        return people;
    }
}
