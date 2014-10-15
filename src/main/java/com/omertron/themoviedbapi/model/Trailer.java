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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Stuart
 */
public class Trailer extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;

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
    // The website of the trailer
    private String website;
    private String type;

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

    public String getType() {
        return type;
    }

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

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Trailer) {
            final Trailer other = (Trailer) obj;
            return new EqualsBuilder()
                    .append(name, other.name)
                    .append(size, other.size)
                    .append(source, other.source)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(size)
                .append(source)
                .append(website)
                .toHashCode();
    }
}
