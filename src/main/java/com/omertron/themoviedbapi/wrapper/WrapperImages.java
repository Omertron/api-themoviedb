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
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.ArtworkType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Stuart
 */
public class WrapperImages extends AbstractWrapperAll implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("backdrops")
    private List<Artwork> backdrops = Collections.EMPTY_LIST;
    @JsonProperty("posters")
    private List<Artwork> posters = Collections.EMPTY_LIST;
    @JsonProperty("profiles")
    private List<Artwork> profiles = Collections.EMPTY_LIST;

    public List<Artwork> getBackdrops() {
        return backdrops;
    }

    public List<Artwork> getPosters() {
        return posters;
    }

    public List<Artwork> getProfiles() {
        return profiles;
    }

    public void setBackdrops(List<Artwork> backdrops) {
        this.backdrops = backdrops;
    }

    public void setPosters(List<Artwork> posters) {
        this.posters = posters;
    }

    public void setProfiles(List<Artwork> profiles) {
        this.profiles = profiles;
    }

    /**
     * Return a list of all the artwork with their types.
     *
     * Leaving the parameters blank will return all types
     *
     * @param artworkList
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

        // Add all the posters to the list
        if (types.contains(ArtworkType.POSTER)) {
            updateArtworkType(posters, ArtworkType.POSTER);
            artwork.addAll(posters);
        }

        // Add all the backdrops to the list
        if (types.contains(ArtworkType.BACKDROP)) {
            updateArtworkType(backdrops, ArtworkType.BACKDROP);
            artwork.addAll(backdrops);
        }

        // Add all the backdrops to the list
        if (types.contains(ArtworkType.PROFILE)) {
            updateArtworkType(profiles, ArtworkType.PROFILE);
            artwork.addAll(profiles);
        }

        return artwork;
    }

    /**
     * Update the artwork type for the artwork list
     *
     * @param artworkList
     * @param type
     */
    private void updateArtworkType(List<Artwork> artworkList, ArtworkType type) {
        for (Artwork artwork : artworkList) {
            artwork.setArtworkType(type);
        }
    }
}
