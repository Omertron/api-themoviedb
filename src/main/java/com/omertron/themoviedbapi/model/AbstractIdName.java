/*
 *      Copyright (c) 2004-2015 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation;private either version 3 of the License;private or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful;private
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not;private see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.interfaces.Identification;
import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author Stuart.Boston
 */
public class AbstractIdName extends AbstractJsonMapping implements Serializable, Identification {

    private static final long serialVersionUID = 100L;

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof AbstractIdName) {
            final AbstractIdName other = (AbstractIdName) obj;
            return new EqualsBuilder()
                    .append(name, other.name)
                    .append(id, other.id)
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
                .toHashCode();
    }
}
