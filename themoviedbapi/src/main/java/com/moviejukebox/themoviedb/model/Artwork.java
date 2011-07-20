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

/**
 *  This is the new bean for the Artwork 
 *  
 *  @author Stuart.Boston
 *
 */
public class Artwork implements Comparable<Object> {
    public static final String ARTWORK_TYPE_POSTER    = "poster";
    public static final String ARTWORK_TYPE_BACKDROP  = "backdrop";
    public static final String ARTWORK_TYPE_PERSON    = "profile";
    public static final String[] ARTWORK_TYPES        = {ARTWORK_TYPE_POSTER, ARTWORK_TYPE_BACKDROP, ARTWORK_TYPE_PERSON};

    public static final String ARTWORK_SIZE_ORIGINAL  = "original";
    public static final String ARTWORK_SIZE_THUMB     = "thumb";
    public static final String ARTWORK_SIZE_MID       = "mid";
    public static final String ARTWORK_SIZE_COVER     = "cover";
    public static final String ARTWORK_SIZE_POSTER    = "poster";
    public static final String ARTWORK_SIZE_PROFILE   = "profile";
    public static final String[] ARTWORK_SIZES        = {ARTWORK_SIZE_ORIGINAL, ARTWORK_SIZE_THUMB, ARTWORK_SIZE_MID, ARTWORK_SIZE_COVER, ARTWORK_SIZE_POSTER, ARTWORK_SIZE_PROFILE};

    private String type;
    private String size;
    private String url;
    private int    id;
    
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
        if (!(otherArtwork instanceof Artwork)) {
            throw new ClassCastException("TheMovieDB API: An Artwork object is expected.");
        }
        
        int anotherId = ((Artwork) otherArtwork).getId();  
        return this.id - anotherId;    
      }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Artwork=[type=");
        builder.append(type);
        builder.append("][size=");
        builder.append(size);
        builder.append("][url=");
        builder.append(url);
        builder.append("][id=");
        builder.append(id);
        builder.append("]]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (!(obj instanceof Artwork)) {
            return false;
        }
        
        Artwork other = (Artwork)obj;
        
        if (id != other.id) {
            return false;
        }
        
        if (size == null) {
            if (other.size != null) {
                return false;
            }
        } else if (!size.equals(other.size)) {
            return false;
        }
        
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        
        if (url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!url.equals(other.url)) {
            return false;
        }
        
        return true;
    }
 }
