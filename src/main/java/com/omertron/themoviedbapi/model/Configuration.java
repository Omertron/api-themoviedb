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
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.type.ArtworkType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration information about The MovieDB
 *
 * Contains the sizes of the various images as well as the base URL for these images.
 *
 * @author stuart.boston
 */
public class Configuration extends AbstractJsonMapping {

    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);
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

    /**
     * Get all backdrop sizes
     *
     * @return
     */
    public List<String> getBackdropSizes() {
        return backdropSizes;
    }

    /**
     * get the base URL
     *
     * @return
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Get all poster sizes
     *
     * @return
     */
    public List<String> getPosterSizes() {
        return posterSizes;
    }

    /**
     * Get all profile sizes
     *
     * @return
     */
    public List<String> getProfileSizes() {
        return profileSizes;
    }

    /**
     * Get all logo sizes
     *
     * @return
     */
    public List<String> getLogoSizes() {
        return logoSizes;
    }

    /**
     * Get the secure base URL
     *
     * @return
     */
    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    /**
     * Get the still sizes
     *
     * @return
     */
    public List<String> getStillSizes() {
        return stillSizes;
    }

    /**
     * Set the backdrop sizes
     *
     * @param backdropSizes
     */
    public void setBackdropSizes(List<String> backdropSizes) {
        this.backdropSizes = backdropSizes;
    }

    /**
     * Set the base URL
     *
     * @param baseUrl
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Set the poster sizes
     *
     * @param posterSizes
     */
    public void setPosterSizes(List<String> posterSizes) {
        this.posterSizes = posterSizes;
    }

    /**
     * Set the profile sizes
     *
     * @param profileSizes
     */
    public void setProfileSizes(List<String> profileSizes) {
        this.profileSizes = profileSizes;
    }

    /**
     * Set the logo sizes
     *
     * @param logoSizes
     */
    public void setLogoSizes(List<String> logoSizes) {
        this.logoSizes = logoSizes;
    }

    /**
     * Set the secure base URL
     *
     * @param secureBaseUrl
     */
    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secureBaseUrl = secureBaseUrl;
    }

    /**
     * Set the still sizes
     *
     * @param stillSizes
     */
    public void setStillSizes(List<String> stillSizes) {
        this.stillSizes = stillSizes;
    }

    /**
     * Copy the data from the passed object to this one
     *
     * @param config
     */
    public void clone(Configuration config) {
        baseUrl = config.getBaseUrl();
        secureBaseUrl = config.getSecureBaseUrl();
        backdropSizes = config.getBackdropSizes();
        posterSizes = config.getPosterSizes();
        profileSizes = config.getProfileSizes();
        logoSizes = config.getLogoSizes();
        stillSizes = config.getStillSizes();
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
     * Check to see if the size is valid for the artwork type
     *
     * @param artworkType
     * @param sizeToCheck
     * @return
     */
    public boolean isValidSize(ArtworkType artworkType, String sizeToCheck) {
        boolean valid;

        switch (artworkType) {
            case BACKDROP:
                valid = isValidBackdropSize(sizeToCheck);
                break;
            case LOGO:
                valid = isValidLogoSize(sizeToCheck);
                break;
            case POSTER:
                valid = isValidPosterSize(sizeToCheck);
                break;
            case PROFILE:
                valid = isValidProfileSize(sizeToCheck);
                break;
            default:
                valid = Boolean.FALSE;
        }
        return valid;
    }

    /**
     * Generate the full image URL from the size and image path
     *
     * @param imagePath
     * @param artworkType
     * @param requiredSize
     * @return
     * @throws MovieDbException
     */
    public URL createImageUrl(String imagePath, ArtworkType artworkType, String requiredSize) throws MovieDbException {
        if (!isValidSize(artworkType, requiredSize)) {
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.INVALID_IMAGE, requiredSize);
        }

        StringBuilder sb = new StringBuilder(getBaseUrl());
        sb.append(requiredSize);
        sb.append(imagePath);
        try {
            return (new URL(sb.toString()));
        } catch (MalformedURLException ex) {
            LOG.warn("Failed to create image URL: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.INVALID_URL, sb.toString(), ex);
        }
    }
}
