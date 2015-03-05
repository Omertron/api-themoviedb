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
package com.omertron.themoviedbapi.model.tv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.media.MediaCreditCast;
import com.omertron.themoviedbapi.model.media.MediaCreditCrew;
import java.util.List;

/**
 * TV Episode information
 *
 * @author stuart.boston
 */
public class TVEpisodeInfo extends TVEpisodeBasic {

    @JsonProperty("crew")
    private List<MediaCreditCrew> crew;
    @JsonProperty("guest_stars")
    private List<MediaCreditCast> guestStars;
    @JsonProperty("production_code")
    private String productionCode;

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

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }
}
