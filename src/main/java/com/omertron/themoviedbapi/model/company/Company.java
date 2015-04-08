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
package com.omertron.themoviedbapi.model.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import com.omertron.themoviedbapi.interfaces.Identification;
import java.io.Serializable;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Company information
 *
 * @author Stuart
 */
public class Company extends AbstractJsonMapping implements Serializable, Identification {

    private static final long serialVersionUID = 100L;
    // Properties
    @JsonProperty("id")
    private int id = 0;
    @JsonProperty("name")
    private String name = EMPTY;
    @JsonProperty("description")
    private String description = EMPTY;
    @JsonProperty("headquarters")
    private String headquarters = EMPTY;
    @JsonProperty("homepage")
    private String homepage = EMPTY;
    @JsonProperty("logo_path")
    private String logoPath = EMPTY;
    @JsonProperty("parent_company")
    private Company parentCompany = null;

    @Override
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public String getName() {
        return name;
    }

    public Company getParentCompany() {
        return parentCompany;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentCompany(Company parentCompany) {
        this.parentCompany = parentCompany;
    }

    public void setParentCompany(int id, String name, String logoPath) {
        Company parent = new Company();
        parent.setId(id);
        parent.setName(name);
        parent.setLogoPath(logoPath);
        this.parentCompany = parent;
    }
}
