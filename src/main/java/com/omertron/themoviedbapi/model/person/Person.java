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
package com.omertron.themoviedbapi.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stuart.boston
 */
public class Person extends PersonBasic {

    private static final long serialVersionUID = 1L;

    /*
     * Static fields for default cast information
     */
    private static final String CAST_DEPARTMENT = "acting";
    private static final String CAST_JOB = "actor";
    private static final String DEFAULT_STRING = "";
    /*
     * Properties
     */
    private PersonType personType = PersonType.PERSON;
    private String department = DEFAULT_STRING;  // Crew
    private String job = DEFAULT_STRING;         // Crew
    private String character = DEFAULT_STRING;   // Cast
    private int order = -1;                      // Cast
    @JsonProperty("adult")
    private boolean adult = false;  // Person info
    @JsonProperty("also_known_as")
    private List<String> aka = new ArrayList<String>();
    @JsonProperty("biography")
    private String biography = DEFAULT_STRING;
    @JsonProperty("birthday")
    private String birthday = DEFAULT_STRING;
    @JsonProperty("deathday")
    private String deathday = DEFAULT_STRING;
    @JsonProperty("homepage")
    private String homepage = DEFAULT_STRING;
    @JsonProperty("place_of_birth")
    private String birthplace = DEFAULT_STRING;
    @JsonProperty("imdb_id")
    private String imdbId = DEFAULT_STRING;
    @JsonProperty("popularity")
    private float popularity = 0.0f;

    /**
     * Add a crew member
     *
     * @param id
     * @param name
     * @param profilePath
     * @param department
     * @param job
     */
    public void addCrew(int id, String name, String profilePath, String department, String job) {
        setPersonType(PersonType.CREW);
        setId(id);
        setName(name);
        setProfilePath(profilePath);
        setDepartment(department);
        setJob(job);
        setCharacter("");
        setOrder(-1);
    }

    /**
     * Add a cast member
     *
     * @param id
     * @param name
     * @param profilePath
     * @param character
     * @param order
     */
    public void addCast(int id, String name, String profilePath, String character, int order) {
        setPersonType(PersonType.CAST);
        setId(id);
        setName(name);
        setProfilePath(profilePath);
        setCharacter(character);
        setOrder(order);
        setDepartment(CAST_DEPARTMENT);
        setJob(CAST_JOB);
    }

    // <editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getCharacter() {
        return character;
    }

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    public int getOrder() {
        return order;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public boolean isAdult() {
        return adult;
    }

    public List<String> getAka() {
        return aka;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public float getPopularity() {
        return popularity;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setCharacter(String character) {
        this.character = character;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJob(String job) {
        this.job = StringUtils.trimToEmpty(job);
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public void setBiography(String biography) {
        this.biography = StringUtils.trimToEmpty(biography);
    }

    public void setBirthday(String birthday) {
        this.birthday = StringUtils.trimToEmpty(birthday);
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = StringUtils.trimToEmpty(birthplace);
    }

    public void setDeathday(String deathday) {
        this.deathday = StringUtils.trimToEmpty(deathday);
    }

    public void setHomepage(String homepage) {
        this.homepage = StringUtils.trimToEmpty(homepage);
    }

    public void setImdbId(String imdbId) {
        this.imdbId = StringUtils.trimToEmpty(imdbId);
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }
    // </editor-fold>

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.personType != other.personType) {
            return false;
        }
        if (!StringUtils.equals(this.department, other.department)) {
            return false;
        }
        if (!StringUtils.equals(this.job, other.job)) {
            return false;
        }

        return StringUtils.equals(this.character, other.character);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.getId();
        hash = 37 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        hash = 37 * hash + (this.getProfilePath() != null ? this.getProfilePath().hashCode() : 0);
        hash = 37 * hash + (this.personType != null ? this.personType.hashCode() : 0);
        hash = 37 * hash + (this.department != null ? this.department.hashCode() : 0);
        hash = 37 * hash + (this.job != null ? this.job.hashCode() : 0);
        hash = 37 * hash + (this.character != null ? this.character.hashCode() : 0);
        return hash;
    }
}
