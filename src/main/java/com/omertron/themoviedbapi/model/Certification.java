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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author stuart.boston
 */
@JsonRootName("certification")
public class Certification extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;
    /*
     * Properties
     */
    @JsonProperty("certification")
    private String certification;
    @JsonProperty("meaning")
    private String meaning;
    @JsonProperty("order")
    private int order;

    public int getOrder() {
        return order;
    }

    public String getCertification() {
        return certification;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }
    
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Certification other = (Certification) obj;
        if (this.order != other.order) {
            return false;
        }
        if ((this.meaning == null) ? (other.meaning != null) : !this.meaning.equals(other.meaning)) {
            return false;
        }
        if ((this.certification == null) ? (other.certification != null) : !this.certification.equals(other.certification)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.order;
        hash = 53 * hash + (this.meaning != null ? this.meaning.hashCode() : 0);
        hash = 53 * hash + (this.certification != null ? this.certification.hashCode() : 0);
        return hash;
    }
}
