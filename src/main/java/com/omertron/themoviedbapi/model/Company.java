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
import java.io.Serializable;
import org.apache.log4j.Logger;

/**
 * Company information
 *
 * @author Stuart
 */
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    // Logger
    private static final Logger logger = Logger.getLogger(Company.class);
    private static final String DEFAULT_STRING = "";
    // Properties
    @JsonProperty("id")
    private int companyId = 0;
    @JsonProperty("name")
    private String name = DEFAULT_STRING;
    @JsonProperty("description")
    private String description = DEFAULT_STRING;
    @JsonProperty("headquarters")
    private String headquarters = DEFAULT_STRING;
    @JsonProperty("homepage")
    private String homepage = DEFAULT_STRING;
    @JsonProperty("logo_path")
    private String logoPath = DEFAULT_STRING;
    @JsonProperty("parent_company")
    private String parentCompany = DEFAULT_STRING;

    //<editor-fold defaultstate="collapsed" desc="Getter Methods">
    public int getCompanyId() {
        return companyId;
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

    public String getParentCompany() {
        return parentCompany;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter Methods">
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
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

    @Override
    public String toString() {
        return "Company{" + "companyId=" + companyId + ", name=" + name + ", description=" + description + ", headquarters=" + headquarters + ", homepage=" + homepage + ", logoPath=" + logoPath + ", parentCompany=" + parentCompany + '}';
    }
}
