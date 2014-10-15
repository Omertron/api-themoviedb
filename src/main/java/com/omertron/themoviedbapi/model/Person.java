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
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
        if (obj instanceof Person) {
            final Person other = (Person) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .append(name, other.name)
                    .append(profilePath, other.profilePath)
                    .append(personType, other.personType)
                    .append(department, other.department)
                    .append(job, other.job)
                    .append(character, other.character)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .append(profilePath)
                .append(personType)
                .append(department)
                .append(job)
                .append(character)
                .toHashCode();
    }
}
