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
 *  This is the new bean for the Artwork 
 *  
 *  @author Stuart.Boston
 *
 */
public class Artwork implements Comparable<Object> {
    public static String ARTWORK_TYPE_POSTER    = "poster";
    public static String ARTWORK_TYPE_BACKDROP  = "backdrop";
    public static String ARTWORK_TYPE_PERSON    = "profile";
    public static String[] ARTWORK_TYPES        = {ARTWORK_TYPE_POSTER, ARTWORK_TYPE_BACKDROP, ARTWORK_TYPE_PERSON};

    public static String ARTWORK_SIZE_ORIGINAL  = "original";
    public static String ARTWORK_SIZE_THUMB     = "thumb";
    public static String ARTWORK_SIZE_MID       = "mid";
    public static String ARTWORK_SIZE_COVER     = "cover";
    public static String ARTWORK_SIZE_POSTER    = "poster";
    public static String ARTWORK_SIZE_PROFILE   = "profile";
    public static String[] ARTWORK_SIZES        = {ARTWORK_SIZE_ORIGINAL, ARTWORK_SIZE_THUMB, ARTWORK_SIZE_MID, ARTWORK_SIZE_COVER, ARTWORK_SIZE_POSTER, ARTWORK_SIZE_PROFILE};

    public String type;
    public String size;
    public String url;
    public int    id;
    
    public String[] getArtworkSizes() {
        return ARTWORK_SIZES;
    }
    
    public String[] getArtworkTypes() {
        return ARTWORK_TYPES;
    }
    
    public String getType() {
        if (type == null) {
            return MovieDB.UNKNOWN;
        } else {
                return type;
        }
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getSize() {
        if (size == null) {
            return MovieDB.UNKNOWN;
        } else {
            return size;
        }
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }
    
    public void setId(String id) {
        try {
            this.id = Integer.parseInt(id);
        } catch (Exception ignore) {
            // If there is an issue with casting the Id then use Zero
            this.id = 0;
        }
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public int compareTo(Object otherArtwork) throws ClassCastException {
        if (!(otherArtwork instanceof Artwork))
            throw new ClassCastException("TheMovieDB API: An Artwork object is expected.");
        int anotherId = ((Artwork) otherArtwork).getId();  
        return this.id - anotherId;    
      }
 }
