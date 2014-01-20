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
package com.omertron.themoviedbapi.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.omertron.themoviedbapi.model.type.MediaType;
import java.util.Collections;
import java.util.List;

/**
 * Cast & Crew credits for a person
 *
 * @author Stuart
 */
public class PersonMovieCredits extends AbstractJsonMapping {

    private int id;
    private MediaType videoType;
    @JsonProperty("cast")
    private final List<PersonMovieCast> cast = Collections.emptyList();
    @JsonProperty("crew")
    private final List<PersonMovieCrew> crew = Collections.emptyList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MediaType getVideoType() {
        return videoType;
    }

    public void setVideoType(MediaType videoType) {
        this.videoType = videoType;
    }

    public List<PersonMovieCast> getCast() {
        return cast;
    }

    public List<PersonMovieCrew> getCrew() {
        return crew;
    }

    public void addCast(PersonMovieCast cast) {
        this.cast.add(cast);
    }

    public void addCrew(PersonMovieCrew crew) {
        this.crew.add(crew);
    }
}
