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
public class Translation {
    /*
     * Logger
     */

    private static final Logger logger = Logger.getLogger(Translation.class);
    /*
     * Properties
     */
    @JsonProperty("english_name")
    private String englishName;
    @JsonProperty("iso_639_1")
    private String isoCode;
    @JsonProperty("name")
    private String name;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getEnglishName() {
        return englishName;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getName() {
        return name;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public void setName(String name) {
        this.name = name;
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
        final Translation other = (Translation) obj;
        if ((this.englishName == null) ? (other.englishName != null) : !this.englishName.equals(other.englishName)) {
            return false;
        }
        if ((this.isoCode == null) ? (other.isoCode != null) : !this.isoCode.equals(other.isoCode)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.englishName != null ? this.englishName.hashCode() : 0);
        hash = 29 * hash + (this.isoCode != null ? this.isoCode.hashCode() : 0);
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Translation=");
        sb.append("[englishName=").append(englishName);
        sb.append("],[isoCode=").append(isoCode);
        sb.append("],[name=").append(name);
        sb.append("]]");
        return sb.toString();
    }
}
