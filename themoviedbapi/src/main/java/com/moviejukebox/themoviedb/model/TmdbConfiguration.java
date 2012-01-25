/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.themoviedb.model;

import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 *
 * @author stuart.boston
 */
@JsonRootName("images")
public class TmdbConfiguration {

    /*
     * Logger
     */
    private static final Logger logger = Logger.getLogger(TmdbConfiguration.class);
    /*
     * Properties
     */
    @JsonProperty("base_url")
    private String baseUrl;
    @JsonProperty("poster_sizes")
    private List<String> posterSizes;
    @JsonProperty("backdrop_sizes")
    private List<String> backdropSizes;
    @JsonProperty("profile_sizes")
    private List<String> profileSizes;

    // <editor-fold defaultstate="collapsed" desc="Getter methods">//GEN-BEGIN:getterMethods
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">//GEN-BEGIN:setterMethods
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
// </editor-fold>

    /**
     * Copy the data from the passed object to this one
     * @param config
     */
    public void clone(TmdbConfiguration config) {
        backdropSizes = config.getBackdropSizes();
        baseUrl = config.getBaseUrl();
        posterSizes = config.getPosterSizes();
        profileSizes = config.getProfileSizes();
    }

    /**
     * Check that the poster size is valid
     * @param posterSize
     * @return
     */
    public boolean isValidPosterSize(String posterSize) {
        return posterSizes.contains(posterSize);
    }

    /**
     * Check that the backdrop size is valid
     * @param backdropSize
     * @return
     */
    public boolean isValidBackdropSize(String backdropSize) {
        return backdropSizes.contains(backdropSize);
    }

    /**
     * Check that the profile size is valid
     * @param profileSize
     * @return
     */
    public boolean isValidProfileSize(String profileSize) {
        return profileSizes.contains(profileSize);
    }

    /**
     * Check to see if the size is valid for any of the images types
     * @param sizeToCheck
     * @return
     */
    public boolean isValidSize(String sizeToCheck) {
        return (isValidPosterSize(sizeToCheck) || isValidBackdropSize(sizeToCheck) || isValidProfileSize(sizeToCheck));
    }

    /**
     * Handle unknown properties and print a message
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown property: '").append(key);
        sb.append("' value: '").append(value).append("'");
        logger.warn(sb.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ImageConfiguration=");
        sb.append("[baseUrl=").append(baseUrl);
        sb.append("],[posterSizes=").append(posterSizes.toString());
        sb.append("],[backdropSizes=").append(backdropSizes.toString());
        sb.append("],[profileSizes=").append(profileSizes.toString());
        sb.append(("]]"));
        return sb.toString();
    }
}
