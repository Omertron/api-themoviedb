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
package com.omertron.themoviedbapi.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.Artwork;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stuart
 */
public class WrapperImages extends WrapperBase {
    /*
     * Properties
     */
    @JsonProperty("backdrops")
    private List<Artwork> backdrops;
    @JsonProperty("posters")
    private List<Artwork> posters;
    @JsonProperty("profiles")
    private List<Artwork> profiles;

    public WrapperImages() {
        super(LoggerFactory.getLogger(WrapperImages.class));
    }

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public List<Artwork> getBackdrops() {
        return backdrops;
    }

    public List<Artwork> getPosters() {
        return posters;
    }

    public List<Artwork> getProfiles() {
        return profiles;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setBackdrops(List<Artwork> backdrops) {
        this.backdrops = backdrops;
    }

    public void setPosters(List<Artwork> posters) {
        this.posters = posters;
    }

    public void setProfiles(List<Artwork> profiles) {
        this.profiles = profiles;
    }
    //</editor-fold>
}
