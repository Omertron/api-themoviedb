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

/**
 * Language from TheMovieDB.org
 * @author stuart.boston
 *
 */
public class Language {

    private static final String UNKNOWN = MovieDB.UNKNOWN;

    private String isoCode      = UNKNOWN;  // The iso 639.1 Language code
    private String englishName  = UNKNOWN;    
    private String nativeName   = UNKNOWN;
    
    public Language() {
        this.isoCode = UNKNOWN;
        this.englishName = UNKNOWN;
        this.nativeName = UNKNOWN;
    }
    
    public Language(String isoCode, String englishName, String nativeName) {
        this.isoCode = isoCode;
        this.englishName = englishName;
        this.nativeName = nativeName;
    }
    
    public String getEnglishName() {
        return englishName;
    }
    
    public String getIsoCode() {
        return isoCode;
    }
    
    public String getNativeName() {
        return nativeName;
    }
    
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
    
    
}
