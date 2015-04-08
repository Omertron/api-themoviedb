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
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.model.media.MediaBasic;
import java.io.Serializable;
import java.util.List;

/**
 * Basic TV information
 *
 * @author stuart.boston
 */
public class TVBasic extends MediaBasic implements Serializable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("name")
    private String name;
    @JsonProperty("original_name")
    private String originalName;
    @JsonProperty("first_air_date")
    private String firstAirDate;
    @JsonProperty("origin_country")
    private List<String> originCountry;
    @JsonProperty("rating")
    private float rating = -1f;

    public TVBasic() {
        super.setMediaType(MediaType.TV);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

}
