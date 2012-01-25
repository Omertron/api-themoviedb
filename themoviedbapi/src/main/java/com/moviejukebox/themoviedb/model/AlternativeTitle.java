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

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Stuart
 */
public class AlternativeTitle {

    /*
     * Logger
     */
    private static final Logger logger = Logger.getLogger(AlternativeTitle.class);
    /*
     * Properties
     */
    @JsonProperty("iso_3166_1")
    private String country;
    @JsonProperty("title")
    private String title;

    // <editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getCountry() {
        return country;
    }

    public String getTitle() {
        return title;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setCountry(String country) {
        this.country = country;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    // </editor-fold>

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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AlternativeTitle other = (AlternativeTitle) obj;
        if ((this.country == null) ? (other.country != null) : !this.country.equals(other.country)) {
            return false;
        }
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.country != null ? this.country.hashCode() : 0);
        hash = 89 * hash + (this.title != null ? this.title.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[AlternativeTitle=");
        sb.append("[country=").append(country);
        sb.append("],[title=").append(title);
        sb.append("]]");
        return sb.toString();
    }
}
