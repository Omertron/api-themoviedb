/*
 *      Copyright (c) 2004-2013 Stuart Boston
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

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.Logger;

public class ChangeValue {

    private static final long serialVersionUID = 1L;

    /*
     * Logger
     */
    private static final Logger logger = Logger.getLogger(MovieChanges.class);
    /*
     * Properties
     */
    @JsonProperty("poster")
    private Artwork poster;
    @JsonProperty("backdrop")
    private Artwork backdrop;
    @JsonProperty("title")
    private String title;
    @JsonProperty("iso_3166_1")
    private String language;
    @JsonProperty("site")
    private String site;
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private int id;

    //<editor-fold defaultstate="collapsed" desc="Getter Methods">
    public Artwork getPoster() {
        return poster;
    }

    public Artwork getBackdrop() {
        return backdrop;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public String getSite() {
        return site;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter Methods">
    public void setPoster(Artwork poster) {
        this.poster = poster;
    }

    public void setBackdrop(Artwork backdrop) {
        this.backdrop = backdrop;
        backdrop.setArtworkType(ArtworkType.BACKDROP);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    //</editor-fold>

    /**
     * Handle unknown properties and print a message
     *
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown property: '").append(key);
        sb.append("' value: '").append(value).append("'");
        logger.trace(sb.toString());
    }
}
