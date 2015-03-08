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
package com.omertron.themoviedbapi.model.media;

import com.omertron.themoviedbapi.model.credits.MediaCreditCrew;
import com.omertron.themoviedbapi.model.credits.MediaCreditCast;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.interfaces.IIdentification;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.io.Serializable;
import java.util.List;

/**
 * @author stuart.boston
 */
public class MediaCreditList extends AbstractJsonMapping implements Serializable, IIdentification {

    private static final long serialVersionUID = 4L;

    @JsonProperty("id")
    private int id;
    @JsonProperty("cast")
    private List<MediaCreditCast> cast;
    @JsonProperty("guest_stars")
    private List<MediaCreditCast> guestStars;
    @JsonProperty("crew")
    private List<MediaCreditCrew> crew;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public List<MediaCreditCast> getCast() {
        return cast;
    }

    public void setCast(List<MediaCreditCast> cast) {
        this.cast = cast;
    }

    public List<MediaCreditCrew> getCrew() {
        return crew;
    }

    public void setCrew(List<MediaCreditCrew> crew) {
        this.crew = crew;
    }

    public List<MediaCreditCast> getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(List<MediaCreditCast> guestStars) {
        this.guestStars = guestStars;
    }

}
