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
package com.omertron.themoviedbapi.wrapper.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.person.PersonMovie;
import com.omertron.themoviedbapi.model.type.PersonType;
import com.omertron.themoviedbapi.wrapper.AbstractWrapperAll;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stuart.boston
 */
public class WrapperPersonCredits extends AbstractWrapperAll {

    @JsonProperty("cast")
    private List<PersonMovie> cast;
    @JsonProperty("crew")
    private List<PersonMovie> crew;

    public List<PersonMovie> getCast() {
        return cast;
    }

    public void setCast(List<PersonMovie> cast) {
        this.cast = cast;
    }

    public List<PersonMovie> getCrew() {
        return crew;
    }

    public void setCrew(List<PersonMovie> crew) {
        this.crew = crew;
    }

    public List<PersonMovie> getAll(PersonType... typeList) {
        List<PersonMovie> personCredits = new ArrayList<PersonMovie>();
        List<PersonType> types = getTypeList(PersonType.class, typeList);

        // Add a cast member
        if (types.contains(PersonType.CAST)) {
            for (PersonMovie member : cast) {
//                member.setPersonType(PersonType.CAST);
                personCredits.add(member);
            }
        }

        // Add a crew member
        if (types.contains(PersonType.CREW)) {
            for (PersonMovie member : crew) {
//                member.setPersonType(PersonType.CREW);
                personCredits.add(member);
            }
        }

        return personCredits;
    }
}
