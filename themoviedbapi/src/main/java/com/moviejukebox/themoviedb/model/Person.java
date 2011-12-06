/*
 *      Copyright (c) 2004-2011 YAMJ Members
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

import com.moviejukebox.themoviedb.TheMovieDb;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moviejukebox.themoviedb.tools.ModelTools;
import java.util.logging.Logger;

/**
 *  This is the new bean for the Person
 *
 *  @author Stuart.Boston
 *
 */
public class Person extends ModelTools implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = TheMovieDb.getLogger();

    private static final String UNKNOWN = MovieDB.UNKNOWN;

    private String  name        = UNKNOWN;
    private String  character   = UNKNOWN;
    private String  job         = UNKNOWN;
    private String  id          = UNKNOWN;
    private String  department  = UNKNOWN;
    private String  biography   = UNKNOWN;
    private String  url         = UNKNOWN;
    private int     order       = -1;
    private int     castId      = -1;
    private int     version     = -1;
    private Date    lastModifiedAt;
    private int     knownMovies = -1;
    private Date    birthday;
    private String  birthPlace  = UNKNOWN;
    private List<Filmography> filmography = new ArrayList<Filmography>();
    private List<String>      aka         = new ArrayList<String>();
    private List<Artwork>     images      = new ArrayList<Artwork>();

    /**
     * Add a single AKA
     * @param alsoKnownAs
     */
    public void addAka(String alsoKnownAs) {
        this.aka.add(alsoKnownAs);
    }

    /**
     * Add a film for the person
     * @param film
     */
    public void addFilm(Filmography film) {
        this.filmography.add(film);
    }

    /**
     * Add an artwork image to the person
     * @param image
     */
    public void addImage(Artwork image) {
        if (image != null) {
            this.images.add(image);
        }
    }

    /**
     * Get all the AKA values
     * @return
     */
    public List<String> getAka() {
        return aka;
    }

    /**
     * Get the biography information
     * @return
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Get the birthday of the person
     * @return
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Get the birthplace
     * @return
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * get the cast ID
     * @return
     */
    public int getCastId() {
        return castId;
    }

    /**
     * get the character
     * @return
     */
    public String getCharacter() {
        return character;
    }

    /**
     * get the department
     * @return
     */
    public String getDepartment() {
        return department;
    }

    /**
     * get the list of films
     * @return
     */
    public List<Filmography> getFilmography() {
        return filmography;
    }

    /**
     * get the ID of the person
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * get a list of images for the person
     * @return
     */
    public List<Artwork> getImages() {
        return images;
    }

    /**
     * get the job
     * @return
     */
    public String getJob() {
        return job;
    }

    /**
     * get the known movies
     * @return
     */
    public int getKnownMovies() {
        return knownMovies;
    }

    /**
     * get the last modified date for the person
     * @return
     */
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * get the name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * get the order
     * @return
     */
    public int getOrder() {
        return order;
    }

    /**
     * get the URL for the person
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * get the version
     * @return
     */
    public int getVersion() {
        return version;
    }

    /**
     * Set the AKA list for the person
     * @param aka
     */
    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    /**
     * Set the biography
     * @param biography
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Set the person's birthday
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Set the person's birthday
     * @param sBirthday
     */
    public void setBirthday(String sBirthday) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            setBirthday(df.parse(sBirthday));
        } catch (ParseException ex) {
            logger.fine("TheMovieDB - Person: Error parsing birthday: " + sBirthday);
        }
    }

    /**
     * Set the birth place
     * @param birthPlace
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * Set the cast ID for the person
     * @param castId
     */
    public void setCastId(int castId) {
        this.castId = castId;
    }

    /**
     * Set the cast ID for the person
     * @param castId
     */
    public void setCastId(String castId) {
        try {
            this.castId = Integer.parseInt(castId);
        } catch (Exception ignore) {
            this.castId = -1;
        }
    }

    /**
     * Set the character
     * @param character
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * set the Department
     * @param department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Add a list of films
     * @param filmography
     */
    public void setFilmography(List<Filmography> filmography) {
        this.filmography = filmography;
    }

    /**
     * Set the ID of the person
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Set a list of images for the person
     * @param images
     */
    public void setImages(List<Artwork> images) {
        this.images = images;
    }

    /**
     * Set the job for the person
     * @param job
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * Set the known movie for the person
     * @param knownMovies
     */
    public void setKnownMovies(int knownMovies) {
        this.knownMovies = knownMovies;
    }

    /**
     * Set the last modified date
     * @param lastModifiedAt
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Set the last modified date
     * @param lastModifiedAt
     */
    public void setLastModifiedAt(String lastModifiedAt) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date lma = df.parse(lastModifiedAt);
            setLastModifiedAt(lma);
        } catch (Exception ignore) {
            return;
        }
    }

    /**
     * Set the person's anme
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *Set the order
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Set the order
     * @param order
     */
    public void setOrder(String order) {
        try {
            this.order = Integer.parseInt(order);
        } catch (Exception ignore) {
            this.order = -1;
        }
    }

    /**
     * Set the URL
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Set the version
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Generate a String representation of the person
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Person=[name=");
        builder.append(name);
        builder.append("][character=");
        builder.append(character);
        builder.append("][job=");
        builder.append(job);
        builder.append("][id=");
        builder.append(id);
        builder.append("][department=");
        builder.append(department);
        builder.append("][biography=");
        builder.append(biography);
        builder.append("][url=");
        builder.append(url);
        builder.append("][order=");
        builder.append(order);
        builder.append("][castId=");
        builder.append(castId);
        builder.append("][version=");
        builder.append(version);
        builder.append("][lastModifiedAt=");
        builder.append(lastModifiedAt);
        builder.append("][knownMovies=");
        builder.append(knownMovies);
        builder.append("][birthday=");
        builder.append(birthday);
        builder.append("][birthPlace=");
        builder.append(birthPlace);
        builder.append("][filmography=");
        builder.append(filmography);
        builder.append("][aka=");
        builder.append(aka);
        builder.append("][images=");
        builder.append(images);
        builder.append("]]");
        return builder.toString();
    }
}
