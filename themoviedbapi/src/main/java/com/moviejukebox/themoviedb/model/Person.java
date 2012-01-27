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

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author stuart.boston
 */
public class Person {
    /*
     * Logger
     */

    private static final Logger LOGGER = Logger.getLogger(Person.class);

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
        this.personType = PersonType.CREW;
        this.id = id;
        this.name = name;
        this.profilePath = profilePath;
        this.department = department;
        this.job = job;
        this.character = "";
        this.order = -1;
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
        this.personType = PersonType.CAST;
        this.id = id;
        this.name = name;
        this.profilePath = profilePath;
        this.character = character;
        this.order = order;
        this.department = CAST_DEPARTMENT;
        this.job = CAST_JOB;
    }

    // <editor-fold defaultstate="collapsed" desc="Getter methods">
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">
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
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
    // </editor-fold>

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Person=");
        sb.append("[id=").append(id);
        sb.append("],[name=").append(name);
        sb.append("],[profilePath=").append(profilePath);
        sb.append("],[personType=").append(personType);
        sb.append("],[department=").append(department);
        sb.append("],[job=").append(job);
        sb.append("],[character=").append(character);
        sb.append("],[order=").append(order);
        sb.append("],[adult=").append(adult);
        sb.append("],[=aka").append(aka.toString());
        sb.append("],[biography=").append(biography);
        sb.append("],[birthday=").append(birthday);
        sb.append("],[deathday=").append(deathday);
        sb.append("],[homepage=").append(homepage);
        sb.append("],[birthplace=").append(birthplace);
        sb.append("]]");
        return sb.toString();
    }
}
