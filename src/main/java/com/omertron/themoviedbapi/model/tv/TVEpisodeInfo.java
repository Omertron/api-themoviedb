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
import com.omertron.themoviedbapi.enumeration.TVEpisodeMethod;
import com.omertron.themoviedbapi.interfaces.AppendToResponse;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.credits.MediaCreditCast;
import com.omertron.themoviedbapi.model.credits.MediaCreditCrew;
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
 * TV Episode information
 *
 * @author stuart.boston
 */
public class TVEpisodeInfo extends TVEpisodeBasic implements Serializable, AppendToResponse<TVEpisodeMethod> {

    private static final long serialVersionUID = 100L;

    @JsonProperty("crew")
    private List<MediaCreditCrew> crew;
    @JsonProperty("guest_stars")
    private List<MediaCreditCast> guestStars;
    @JsonProperty("production_code")
    private String productionCode;
    // AppendToResponse
    private final Set<TVEpisodeMethod> methods = EnumSet.noneOf(TVEpisodeMethod.class);
    // AppendToResponse Properties
    private MediaCreditList credits = new MediaCreditList();
    private ExternalID externalIDs = new ExternalID();
    private List<Artwork> images = Collections.emptyList();
    private List<Video> videos = Collections.emptyList();

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
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
    //</editor-fold>

    private void addMethod(TVEpisodeMethod method) {
        methods.add(method);
    }

    @Override
    public boolean hasMethod(TVEpisodeMethod method) {
        return methods.contains(method);
    }

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Setters">
    @JsonSetter("credits")
    public void setCredits(MediaCreditList credits) {
        this.credits = credits;
        addMethod(TVEpisodeMethod.CREDITS);
    }

    @JsonSetter("external_ids")
    public void setExternalIDs(ExternalID externalIDs) {
        this.externalIDs = externalIDs;
        addMethod(TVEpisodeMethod.EXTERNAL_IDS);
    }

    @JsonSetter("images")
    public void setImages(WrapperImages images) {
        this.images = images.getAll();
        addMethod(TVEpisodeMethod.IMAGES);
    }

    @JsonSetter("videos")
    public void setVideos(WrapperGenericList<Video> videos) {
        this.videos = videos.getResults();
        addMethod(TVEpisodeMethod.VIDEOS);
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
