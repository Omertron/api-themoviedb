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

import java.io.Serializable;

public class Filmography implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String UNKNOWN = MovieDB.UNKNOWN;

    private String url = UNKNOWN;
    private String name = UNKNOWN;
    private String department = UNKNOWN;
    private String character = UNKNOWN;
    private String job = UNKNOWN;
    private String id = UNKNOWN;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getCharacter() {
        return character;
    }
    
    public void setCharacter(String character) {
        this.character = character;
    }
    
    public String getJob() {
        return job;
    }
    
    public void setJob(String job) {
        this.job = job;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Filmography=[url=");
        builder.append(url);
        builder.append("][name=");
        builder.append(name);
        builder.append("][department=");
        builder.append(department);
        builder.append("][character=");
        builder.append(character);
        builder.append("][job=");
        builder.append(job);
        builder.append("][id=");
        builder.append(id);
        builder.append("]]");
        return builder.toString();
    }
}
