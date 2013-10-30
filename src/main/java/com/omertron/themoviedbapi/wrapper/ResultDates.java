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
package com.omertron.themoviedbapi.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.io.Serializable;

/**
 *
 * @author Stuart
 */
public class ResultDates extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("minimum")
    private String minimum = "";
    @JsonProperty("maximum")
    private String maximum = "";

    // <editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getMinimum() {
        return minimum;
    }

    public String getMaximum() {
        return maximum;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }
    // </editor-fold>
}
