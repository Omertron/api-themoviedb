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
import org.apache.commons.lang3.StringUtils;

/**
 * @author Stuart
 */
public class PersonCrew extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;

    /*
     * Properties
     */
    @JsonProperty("id")
    private int id;
    @JsonProperty("department")
    private String department;
    @JsonProperty("job")
    private String job;
    @JsonProperty("name")
    private String name;
    @JsonProperty("profile_path")
    private String profilePath;
    @JsonProperty("credit_id")
    private String creditId;

    public String getDepartment() {
        return department;
    }

    public int getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public void setDepartment(String department) {
        this.department = StringUtils.trimToEmpty(department);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJob(String job) {
        this.job = StringUtils.trimToEmpty(job);
    }

    public void setName(String name) {
        this.name = StringUtils.trimToEmpty(name);
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = StringUtils.trimToEmpty(profilePath);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonCrew other = (PersonCrew) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.department == null) ? (other.department != null) : !this.department.equals(other.department)) {
            return false;
        }
        if ((this.job == null) ? (other.job != null) : !this.job.equals(other.job)) {
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
        hash = 59 * hash + this.id;
        hash = 59 * hash + (this.department != null ? this.department.hashCode() : 0);
        hash = 59 * hash + (this.job != null ? this.job.hashCode() : 0);
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + (this.profilePath != null ? this.profilePath.hashCode() : 0);
        return hash;
    }
}
