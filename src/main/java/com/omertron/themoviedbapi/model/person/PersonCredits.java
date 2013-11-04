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

import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.omertron.themoviedbapi.model.type.VideoType;
import java.util.Collections;
import java.util.List;

/**
 * Cast & Crew credits for a person
 *
 * @author Stuart
 */
public class PersonCredits extends AbstractJsonMapping {

    private int id;
    private VideoType videoType;
    private final List<PersonCast> cast = Collections.emptyList();
    private final List<PersonCast> guestStar = Collections.emptyList();
    private final List<PersonCrew> crew = Collections.emptyList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VideoType getVideoType() {
        return videoType;
    }

    public void setVideoType(VideoType videoType) {
        this.videoType = videoType;
    }

    public List<PersonCast> getCast() {
        return cast;
    }

    public List<PersonCast> getGuestStar() {
        return guestStar;
    }

    public List<PersonCrew> getCrew() {
        return crew;
    }

    public void addCast(PersonCast cast) {
        this.cast.add(cast);
    }

    public void addCrew(PersonCrew crew) {
        this.crew.add(crew);
    }

    public void addGuestStar(PersonCast guest) {
        this.guestStar.add(guest);
    }

}
