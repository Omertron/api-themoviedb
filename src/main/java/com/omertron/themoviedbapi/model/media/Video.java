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
package com.omertron.themoviedbapi.model.media;

import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Video specific information
 *
 * @author Stuart
 */
public class Video extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("id")
    private String id;
    @JsonProperty("iso_639_1")
    private String language;
    @JsonProperty("key")
    private String key;
    @JsonProperty("name")
    private String name;
    @JsonProperty("site")
    private String site;
    @JsonProperty("size")
    private int size;
    @JsonProperty("type")
    private String type;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }

    public String getLanguage() {
        return language;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Video) {
            final Video other = (Video) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .append(name, other.name)
                    .append(size, other.size)
                    .append(key, other.key)
                    .append(language, other.language)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .append(size)
                .append(key)
                .append(site)
                .append(language)
                .toHashCode();
    }
}
