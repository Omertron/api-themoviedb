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
package com.omertron.themoviedbapi.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Stuart
 */
public class WrapperImages extends AbstractWrapperAll {

    @JsonProperty("backdrops")
    private List<Artwork> backdrops = Collections.emptyList();
    @JsonProperty("posters")
    private List<Artwork> posters = Collections.emptyList();
    @JsonProperty("profiles")
    private List<Artwork> profiles = Collections.emptyList();
    @JsonProperty("stills")
    private List<Artwork> stills = Collections.emptyList();

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

    public void setStills(List<Artwork> stills) {
        this.stills = stills;
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
        List<Artwork> artwork = new ArrayList<>();
        List<ArtworkType> types;

        if (artworkList.length > 0) {
            types = new ArrayList<>(Arrays.asList(artworkList));
        } else {
            types = new ArrayList<>(Arrays.asList(ArtworkType.values()));
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

        // Add all the profiles to the list
        if (types.contains(ArtworkType.PROFILE)) {
            updateArtworkType(profiles, ArtworkType.PROFILE);
            artwork.addAll(profiles);
        }

        // Add all the stills to the list
        if (types.contains(ArtworkType.STILL)) {
            updateArtworkType(stills, ArtworkType.STILL);
            artwork.addAll(stills);
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
