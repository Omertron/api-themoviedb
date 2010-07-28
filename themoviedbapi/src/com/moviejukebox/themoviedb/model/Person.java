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
    private String  biography;
    private String  character;
    private String  id;
    private String  job;
    private String  name;
    private String  url;
    private int     version;
    private Date    lastModifiedAt;
    private List<Filmography> filmography = new ArrayList<Filmography>();
    private List<String>      aka         = new ArrayList<String>();
    private int     knownMovies;
    private Date    birthday;
    private String  birthPlace;

    
    public String getBiography() {
        return biography;
    }
    
    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    public String getCharacter() {
        return character;
    }
    
    public void setCharacter(String character) {
        this.character = character;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getJob() {
        return job;
    }
    
    public void setJob(String job) {
        this.job = job;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public int getVersion() {
        return version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }

    public List<Filmography> getFilmography() {
        return filmography;
    }

    public void setFilmography(List<Filmography> filmography) {
        this.filmography = filmography;
    }
    
    public void addFilm(Filmography film) {
        this.filmography.add(film);
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }
    
    public void addAka(String alsoKnownAs) {
        this.aka.add(alsoKnownAs);
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
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

    public int getKnownMovies() {
        return knownMovies;
    }

    public void setKnownMovies(int knownMovies) {
        this.knownMovies = knownMovies;
    }

    public Date getBirthday() {
        return birthday;
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

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
}
