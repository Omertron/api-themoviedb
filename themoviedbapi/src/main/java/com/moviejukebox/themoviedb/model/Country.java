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

/**
 *  Country from the MovieDB.org
 *  
 *  @author Stuart.Boston
 *
 */
public class Country implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final String UNKNOWN = MovieDB.UNKNOWN;

    private String url = UNKNOWN;
    private String name = UNKNOWN;
    private String code = UNKNOWN;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Country=[url=");
        builder.append(url);
        builder.append("][name=");
        builder.append(name);
        builder.append("][code=");
        builder.append(code);
        builder.append("]]");
        return builder.toString();
    }
}
