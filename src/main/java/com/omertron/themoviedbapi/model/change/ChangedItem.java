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
package com.omertron.themoviedbapi.model.change;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.io.Serializable;

public class ChangedItem extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 100L;
    @JsonProperty("id")
    private String id;
    @JsonProperty("action")
    private String action;
    @JsonProperty("time")
    private String time;
    @JsonProperty("iso_639_1")
    private String language;
    @JsonProperty("value")
    private Object value;
    @JsonProperty("original_value")
    private Object originalValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(Object originalValue) {
        this.originalValue = originalValue;
    }

}
