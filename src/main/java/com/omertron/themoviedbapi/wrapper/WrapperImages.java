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
import com.omertron.themoviedbapi.model.ArtworkType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    private List<Artwork> backdrops = Collections.EMPTY_LIST;
    @JsonProperty("posters")
    private List<Artwork> posters = Collections.EMPTY_LIST;
    @JsonProperty("profiles")
    private List<Artwork> profiles = Collections.EMPTY_LIST;

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

    /**
     * Return a list of all the artwork with their types.
     *
     * Leaving the parameters blank will return all types
     *
     * @return
     */
    public List<Artwork> getAll(ArtworkType... artworkList) {
        List<Artwork> artwork = new ArrayList<Artwork>();
        List<ArtworkType> types;

        if (artworkList.length > 0) {
            types = new ArrayList<ArtworkType>(Arrays.asList(artworkList));
        } else {
            types = new ArrayList<ArtworkType>(Arrays.asList(ArtworkType.values()));
        }

        if (types.contains(ArtworkType.POSTER)) {
            // Add all the posters to the list
            for (Artwork poster : posters) {
                poster.setArtworkType(ArtworkType.POSTER);
                artwork.add(poster);
            }
        }

        if (types.contains(ArtworkType.BACKDROP)) {
            // Add all the backdrops to the list
            for (Artwork backdrop : backdrops) {
                backdrop.setArtworkType(ArtworkType.BACKDROP);
                artwork.add(backdrop);
            }
        }

        if (types.contains(ArtworkType.PROFILE)) {
            // Add all the backdrops to the list
            for (Artwork backdrop : profiles) {
                backdrop.setArtworkType(ArtworkType.PROFILE);
                artwork.add(backdrop);
            }
        }

        return artwork;
    }
}
