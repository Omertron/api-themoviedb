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
package com.omertron.themoviedbapi.model.movie;

import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Stuart
 */
public class ReleaseInfo extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("iso_3166_1")
    private String country;
    @JsonProperty("certification")
    private String certification;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("primary")
    private boolean primary;

    public String getCertification() {
        return certification;
    }

    public String getCountry() {
        return country;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReleaseInfo) {
            final ReleaseInfo other = (ReleaseInfo) obj;
            return new EqualsBuilder()
                    .append(country, other.country)
                    .append(certification, other.certification)
                    .append(releaseDate, other.releaseDate)
                    .append(primary, other.primary)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(country)
                .append(certification)
                .append(releaseDate)
                .append(primary)
                .toHashCode();
    }
}
