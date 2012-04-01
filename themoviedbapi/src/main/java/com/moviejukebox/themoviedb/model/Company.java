/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.themoviedb.model;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Stuart
 */
public class Company {
    // Logger
    private static final Logger LOGGER = Logger.getLogger(Company.class);
    // Properties
    @JsonProperty("id")
    private int companyId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("headquarters")
    private String headquarters;
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("logo_path")
    private String logoPath;
    @JsonProperty("parent_company")
    private String parentCompany;

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
        LOGGER.warn(sb.toString());
    }
}
