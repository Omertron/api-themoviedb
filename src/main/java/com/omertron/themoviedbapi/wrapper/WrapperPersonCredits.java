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
import com.omertron.themoviedbapi.model.PersonCredit;
import com.omertron.themoviedbapi.model.PersonType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stuart.boston
 */
public class WrapperPersonCredits extends AbstractWrapperAll {

    @JsonProperty("cast")
    private List<PersonCredit> cast;
    @JsonProperty("crew")
    private List<PersonCredit> crew;

    public List<PersonCredit> getCast() {
        return cast;
    }

    public void setCast(List<PersonCredit> cast) {
        this.cast = cast;
    }

    public List<PersonCredit> getCrew() {
        return crew;
    }

    public void setCrew(List<PersonCredit> crew) {
        this.crew = crew;
    }

    public List<PersonCredit> getAll(PersonType... typeList) {
        List<PersonCredit> personCredits = new ArrayList<PersonCredit>();
        List<PersonType> types = getTypeList(PersonType.class, typeList);

        // Add a cast member
        if (types.contains(PersonType.CAST)) {
            for (PersonCredit member : cast) {
                member.setPersonType(PersonType.CAST);
                personCredits.add(member);
            }
        }

        // Add a crew member
        if (types.contains(PersonType.CREW)) {
            for (PersonCredit member : crew) {
                member.setPersonType(PersonType.CREW);
                personCredits.add(member);
            }
        }

        return personCredits;
    }
}
