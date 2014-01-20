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
package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author stuart.boston
 */
public class TmdbConfiguration extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;
    /*
     * Properties
     */
    @JsonProperty("base_url")
    private String baseUrl;
    @JsonProperty("secure_base_url")
    private String secureBaseUrl;
    @JsonProperty("poster_sizes")
    private List<String> posterSizes;
    @JsonProperty("backdrop_sizes")
    private List<String> backdropSizes;
    @JsonProperty("profile_sizes")
    private List<String> profileSizes;
    @JsonProperty("logo_sizes")
    private List<String> logoSizes;
    @JsonProperty("still_sizes")
    private List<String> stillSizes;

    public List<String> getBackdropSizes() {
        return backdropSizes;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public List<String> getProfileSizes() {
        return profileSizes;
    }

    public List<String> getLogoSizes() {
        return logoSizes;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public List<String> getStillSizes() {
        return stillSizes;
    }

    public void setBackdropSizes(List<String> backdropSizes) {
        this.backdropSizes = backdropSizes;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setPosterSizes(List<String> posterSizes) {
        this.posterSizes = posterSizes;
    }

    public void setProfileSizes(List<String> profileSizes) {
        this.profileSizes = profileSizes;
    }

    public void setLogoSizes(List<String> logoSizes) {
        this.logoSizes = logoSizes;
    }

    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secureBaseUrl = secureBaseUrl;
    }

    public void setStillSizes(List<String> stillSizes) {
        this.stillSizes = stillSizes;
    }

    /**
     * Copy the data from the passed object to this one
     *
     * @param config
     */
    public void clone(TmdbConfiguration config) {
        backdropSizes = config.getBackdropSizes();
        baseUrl = config.getBaseUrl();
        posterSizes = config.getPosterSizes();
        profileSizes = config.getProfileSizes();
        logoSizes = config.getLogoSizes();
    }

    /**
     * Check that the poster size is valid
     *
     * @param posterSize
     * @return
     */
    public boolean isValidPosterSize(String posterSize) {
        if (StringUtils.isBlank(posterSize) || posterSizes.isEmpty()) {
            return false;
        }
        return posterSizes.contains(posterSize);
    }

    /**
     * Check that the backdrop size is valid
     *
     * @param backdropSize
     * @return
     */
    public boolean isValidBackdropSize(String backdropSize) {
        if (StringUtils.isBlank(backdropSize) || backdropSizes.isEmpty()) {
            return false;
        }
        return backdropSizes.contains(backdropSize);
    }

    /**
     * Check that the profile size is valid
     *
     * @param profileSize
     * @return
     */
    public boolean isValidProfileSize(String profileSize) {
        if (StringUtils.isBlank(profileSize) || profileSizes.isEmpty()) {
            return false;
        }
        return profileSizes.contains(profileSize);
    }

    /**
     * Check that the logo size is valid
     *
     * @param logoSize
     * @return
     */
    public boolean isValidLogoSize(String logoSize) {
        if (StringUtils.isBlank(logoSize) || logoSizes.isEmpty()) {
            return false;
        }
        return logoSizes.contains(logoSize);
    }

    /**
     * Check to see if the size is valid for any of the images types
     *
     * @param sizeToCheck
     * @return
     */
    public boolean isValidSize(String sizeToCheck) {
        return (isValidPosterSize(sizeToCheck)
                || isValidBackdropSize(sizeToCheck)
                || isValidProfileSize(sizeToCheck)
                || isValidLogoSize(sizeToCheck));
    }
}
