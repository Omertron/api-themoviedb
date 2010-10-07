/*
 *      Copyright (c) 2004-2010 YAMJ Members
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moviejukebox.themoviedb.tools.ModelTools;

/**
 *  This is the new bean for the Person 
 *  
 *  @author Stuart.Boston
 *
 */
public class Person extends ModelTools {
    private static String UNKNOWN = MovieDB.UNKNOWN;

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
    
    public void addAka(String alsoKnownAs) {
        this.aka.add(alsoKnownAs);
    }
    
    public void addFilm(Filmography film) {
        this.filmography.add(film);
    }
    
    public void addImage(Artwork image) {
        if (image != null) {
            this.images.add(image);
        }
    }
    
    public List<String> getAka() {
        return aka;
    }
    
    public String getBiography() {
        return biography;
    }
    
    public Date getBirthday() {
        return birthday;
    }
    
    public String getBirthPlace() {
        return birthPlace;
    }
    
    public int getCastId() {
        return castId;
    }
    
    public String getCharacter() {
        return character;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public List<Filmography> getFilmography() {
        return filmography;
    }
    
    public String getId() {
        return id;
    }
    
    public List<Artwork> getImages() {
        return images;
    }
    
    public String getJob() {
        return job;
    }

    public int getKnownMovies() {
        return knownMovies;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }
    
    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public String getUrl() {
        return url;
    }
    
    public int getVersion() {
        return version;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthday(String sBirthday) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date birthday = df.parse(sBirthday);
            setBirthday(birthday);
        } catch (Exception ignore) {
            return;
        }
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public void setFilmography(List<Filmography> filmography) {
        this.filmography = filmography;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImages(List<Artwork> images) {
        this.images = images;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setKnownMovies(int knownMovies) {
        this.knownMovies = knownMovies;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public void setLastModifiedAt(String lastModifiedAt) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        try {
            Date lma = df.parse(lastModifiedAt);
            setLastModifiedAt(lma);
        } catch (Exception ignore) {
            return;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
}
