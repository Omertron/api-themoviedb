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
public class PersonCast {
    /*
     * Logger
     */

    private static final Logger logger = Logger.getLogger(PersonCast.class);
    /*
     * Properties
     */
    @JsonProperty("id")
    private int id;
    @JsonProperty("character")
    private String character;
    @JsonProperty("name")
    private String name;
    @JsonProperty("order")
    private int order;
    @JsonProperty("profile_path")
    private String profilePath;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getCharacter() {
        return character;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public String getProfilePath() {
        return profilePath;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setCharacter(String character) {
        this.character = character;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
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
        final PersonCast other = (PersonCast) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.character == null) ? (other.character != null) : !this.character.equals(other.character)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        if ((this.profilePath == null) ? (other.profilePath != null) : !this.profilePath.equals(other.profilePath)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        hash = 41 * hash + (this.character != null ? this.character.hashCode() : 0);
        hash = 41 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 41 * hash + this.order;
        hash = 41 * hash + (this.profilePath != null ? this.profilePath.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[PersonCast=");
        sb.append("id=").append(id);
        sb.append("],[character=").append(character);
        sb.append("],[name=").append(name);
        sb.append("],[order=").append(order);
        sb.append("],[profilePath=").append(profilePath);
        sb.append("]]");
        return sb.toString();
    }
}
