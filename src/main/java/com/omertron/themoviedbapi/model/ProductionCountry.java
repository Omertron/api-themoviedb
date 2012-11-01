/*
 *      Copyright (c) 2004-2012 Stuart Boston
 *
 *      This software is licensed under a Creative Commons License
 *      See the LICENCE.txt file included in this package
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.io.Serializable;
import org.apache.log4j.Logger;

/**
 *
 * @author stuart.boston
 */
@JsonRootName("production_country")
public class ProductionCountry implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(ProductionCountry.class);
    /*
     * Properties
     */
    @JsonProperty("iso_3166_1")
    private String isoCode;
    @JsonProperty("name")
    private String name;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getIsoCode() {
        return isoCode;
    }

    public String getName() {
        return name;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public void setName(String name) {
        this.name = name;
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
        LOGGER.trace(sb.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductionCountry other = (ProductionCountry) obj;
        if ((this.isoCode == null) ? (other.isoCode != null) : !this.isoCode.equals(other.isoCode)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.isoCode != null ? this.isoCode.hashCode() : 0);
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ProductionCountry=");
        sb.append("[isoCode=").append(isoCode);
        sb.append("],[name=").append(name);
        sb.append("]]");
        return sb.toString();
    }
}
