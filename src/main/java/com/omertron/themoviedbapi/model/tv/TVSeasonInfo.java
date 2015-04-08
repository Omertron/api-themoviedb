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
import com.fasterxml.jackson.annotation.JsonSetter;
import com.omertron.themoviedbapi.enumeration.TVSeasonMethod;
import com.omertron.themoviedbapi.interfaces.AppendToResponse;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.results.WrapperGenericList;
import com.omertron.themoviedbapi.results.WrapperImages;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * TV Season information
 *
 * @author stuart.boston
 */
public class TVSeasonInfo extends TVSeasonBasic implements Serializable, AppendToResponse<TVSeasonMethod> {

    private static final long serialVersionUID = 100L;

    @JsonProperty("name")
    private String name;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("episodes")
    private List<TVEpisodeInfo> episodes;
    // AppendToResponse
    private final Set<TVSeasonMethod> methods = EnumSet.noneOf(TVSeasonMethod.class);
    // AppendToResponse Properties
    private MediaCreditList credits = new MediaCreditList();
    private ExternalID externalIDs = new ExternalID();
    private List<Artwork> images = Collections.emptyList();
    private List<Video> videos = Collections.emptyList();

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<TVEpisodeInfo> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<TVEpisodeInfo> episodes) {
        this.episodes = episodes;
    }
    //</editor-fold>

    private void addMethod(TVSeasonMethod method) {
        methods.add(method);
    }

    @Override
    public boolean hasMethod(TVSeasonMethod method) {
        return methods.contains(method);
    }

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Setters">
    @JsonSetter("credits")
    public void setCredits(MediaCreditList credits) {
        this.credits = credits;
        addMethod(TVSeasonMethod.CREDITS);
    }

    @JsonSetter("external_ids")
    public void setExternalIDs(ExternalID externalIDs) {
        this.externalIDs = externalIDs;
        addMethod(TVSeasonMethod.EXTERNAL_IDS);
    }

    @JsonSetter("images")
    public void setImages(WrapperImages images) {
        this.images = images.getAll();
        addMethod(TVSeasonMethod.IMAGES);
    }

    @JsonSetter("videos")
    public void setVideos(WrapperGenericList<Video> videos) {
        this.videos = videos.getResults();
        addMethod(TVSeasonMethod.VIDEOS);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Getters">
    public MediaCreditList getCredits() {
        return credits;
    }

    public ExternalID getExternalIDs() {
        return externalIDs;
    }

    public List<Artwork> getImages() {
        return images;
    }

    public List<Video> getVideos() {
        return videos;
    }
    //</editor-fold>

}
