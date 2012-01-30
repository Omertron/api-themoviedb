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

/**
 *
 * @author Stuart
 */
public class Trailer {
    /*
     * Logger
     */

    private static final Logger LOGGER = Logger.getLogger(Trailer.class);
    /*
     * Website sources
     */
    public static final String WEBSITE_YOUTUBE = "youtube";
    public static final String WEBSITE_QUICKTIME = "quicktime";
    /*
     * Properties
     */
    private String name;
    private String size;
    private String source;
    private String website;    // The website of the trailer

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public String getWebsite() {
        return website;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    //</editor-fold>

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
        LOGGER.warn(sb.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trailer other = (Trailer) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.size == null) ? (other.size != null) : !this.size.equals(other.size)) {
            return false;
        }
        if ((this.source == null) ? (other.source != null) : !this.source.equals(other.source)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 61 * hash + (this.size != null ? this.size.hashCode() : 0);
        hash = 61 * hash + (this.source != null ? this.source.hashCode() : 0);
        hash = 61 * hash + (this.website != null ? this.website.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Trailer=");
        sb.append("name=").append(name);
        sb.append("],[size=").append(size);
        sb.append("],[source=").append(source);
        sb.append("],[website=").append(website);
        sb.append("]]");
        return sb.toString();
    }
}
