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
 * @author stuart.boston
 */
public class PersonCredit {
    /*
     * Logger
     */

    private static final Logger LOGGER = Logger.getLogger(PersonCredit.class);
    private static final String DEFAULT_STRING = "";
    /*
     * Properties
     */
    @JsonProperty("id")
    private int movieId = 0;
    @JsonProperty("character")
    private String character = DEFAULT_STRING;
    @JsonProperty("original_title")
    private String movieOriginalTitle = DEFAULT_STRING;
    @JsonProperty("poster_path")
    private String posterPath = DEFAULT_STRING;
    @JsonProperty("release_date")
    private String releaseDate = DEFAULT_STRING;
    @JsonProperty("title")
    private String movieTitle = DEFAULT_STRING;
    @JsonProperty("department")
    private String department = DEFAULT_STRING;
    @JsonProperty("job")
    private String job = DEFAULT_STRING;
    @JsonProperty("adult")
    private String adult = DEFAULT_STRING;
    private PersonType personType = PersonType.PERSON;

    //<editor-fold defaultstate="collapsed" desc="Getter Methods">
    public String getCharacter() {
        return character;
    }

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getAdult() {
        return adult;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter Methods">
    public void setCharacter(String character) {
        this.character = character;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setMovieOriginalTitle(String movieOriginalTitle) {
        this.movieOriginalTitle = movieOriginalTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setAdult(String adult) {
        this.adult = adult;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[PersonCredit=");
        sb.append("[movieId=").append(movieId);
        sb.append("],[personType=").append(personType);
        sb.append("],[originalTitle=").append(movieOriginalTitle);
        sb.append("],[movieTitle=").append(movieTitle);
        sb.append("],[posterPath=").append(posterPath);
        sb.append("],[releaseDate=").append(releaseDate);
        sb.append("],[character=").append(character);
        sb.append("],[department=").append(department);
        sb.append("],[job=").append(job);
        sb.append("],[adult=").append(adult);
        sb.append("]]");
        return sb.toString();
    }
}
