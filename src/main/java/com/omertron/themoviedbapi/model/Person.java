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

import java.util.ArrayList;
import java.util.List;

/**
 * @author stuart.boston
 */
public class Person extends AbstractJsonMapping {

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
    @JsonProperty("id")
    private int id = -1;
    @JsonProperty("name")
    private String name = "";
    @JsonProperty("profile_path")
    private String profilePath = DEFAULT_STRING;
    private PersonType personType = PersonType.PERSON;
    // Crew
    private String department = DEFAULT_STRING;
    // Crew
    private String job = DEFAULT_STRING;
    // Cast
    private String character = DEFAULT_STRING;
    // Cast
    private int order = -1;
    @JsonProperty("adult")
    // Person info
    private boolean adult = false;
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
    @JsonProperty("known_for")
    private List<PersonCredit> knownFor;

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

    public String getCharacter() {
        return character;
    }

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

    public int getOrder() {
        return order;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public String getProfilePath() {
        return profilePath;
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

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public void setOrder(int order) {
        this.order = order;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = StringUtils.trimToEmpty(profilePath);
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

    public List<PersonCredit> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(List<PersonCredit> knownFor) {
        this.knownFor = knownFor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.profilePath == null) ? (other.profilePath != null) : !this.profilePath.equals(other.profilePath)) {
            return false;
        }
        if (this.personType != other.personType) {
            return false;
        }
        if ((this.department == null) ? (other.department != null) : !this.department.equals(other.department)) {
            return false;
        }
        if ((this.job == null) ? (other.job != null) : !this.job.equals(other.job)) {
            return false;
        }
        if ((this.character == null) ? (other.character != null) : !this.character.equals(other.character)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + (this.profilePath != null ? this.profilePath.hashCode() : 0);
        hash = 37 * hash + (this.personType != null ? this.personType.hashCode() : 0);
        hash = 37 * hash + (this.department != null ? this.department.hashCode() : 0);
        hash = 37 * hash + (this.job != null ? this.job.hashCode() : 0);
        hash = 37 * hash + (this.character != null ? this.character.hashCode() : 0);
        return hash;
    }
}
