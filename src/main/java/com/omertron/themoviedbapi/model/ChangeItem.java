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

public class ChangeItem {

    private static final long serialVersionUID = 1L;

    /*
     * Logger
     */
    private static final Logger logger = Logger.getLogger(MovieChanges.class);
    /*
     * Properties
     */
    @JsonProperty("id")
    private String id;
    @JsonProperty("action")
    private String action;
    @JsonProperty("time")
    private String time;
    @JsonProperty("value")
    private ChangeValue value;
    @JsonProperty("original_value")
    private ChangeValue originalValue;
    @JsonProperty("iso_639_1")
    private String language;

    //<editor-fold defaultstate="collapsed" desc="Getter Methods">
    public String getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public String getTime() {
        return time;
    }

    public ChangeValue getValue() {
        return value;
    }

    public ChangeValue getOriginalValue() {
        return originalValue;
    }

    public String getLanguage() {
        return language;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter Methods">
    public void setId(String id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setValue(ChangeValue value) {
        this.value = value;
    }

    public void setOriginalValue(ChangeValue originalValue) {
        this.originalValue = originalValue;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    //</editor-fold>

    @Override
    public String toString() {
        return "ChangeItem{" + "id=" + id + ", action=" + action + ", time=" + time + ", value=" + value + '}';
    }

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
