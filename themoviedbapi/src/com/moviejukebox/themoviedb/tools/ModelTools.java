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
package com.moviejukebox.themoviedb.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.moviejukebox.themoviedb.model.Artwork;

public class ModelTools {
    private List<Artwork>  artwork     = new ArrayList<Artwork>();

    /**
     * Add a piece of artwork to the artwork array
     * @param artworkType must be one of Artwork.ARTWORK_TYPES
     * @param artworkSize must be one of Artwork.ARTWORK_SIZES
     * @param artworkUrl
     * @param posterId
     */
    public void addArtwork(String artworkType, String artworkSize, String artworkUrl, String artworkId) {
        if (validateElement(Artwork.ARTWORK_TYPES, artworkType) && validateElement(Artwork.ARTWORK_SIZES, artworkSize)) {
            Artwork newArtwork = new Artwork();

            newArtwork.setType(artworkType);
            newArtwork.setSize(artworkSize);
            newArtwork.setUrl(artworkUrl);
            newArtwork.setId(artworkId);

            artwork.add(newArtwork);
            Collections.sort(artwork);
        }
        return;
    }
    
    /**
     * Add a piece of artwork to the artwork array
     * @param newArtwork an Artwork object to add to the array
     */
    public void addArtwork(Artwork newArtwork) {
        if (validateElement(Artwork.ARTWORK_TYPES, newArtwork.getType()) && validateElement(Artwork.ARTWORK_SIZES, newArtwork.getSize())) {
            artwork.add(newArtwork);
            Collections.sort(artwork);
        }
        return;
    }
  
    /**
     * Get the first artwork that matches the Type and Size
     * @param artworkType
     * @param artworkSize
     * @return
     */
    public Artwork getFirstArtwork(String artworkType, String artworkSize) {
        return getArtwork(artworkType, artworkSize, 1);
    }
    
    /**
     * Check to see if element is contained in elementArray
     * @param elementArray
     * @param element
     * @return
     */
    private boolean validateElement(String[] elementArray, String element) {
        boolean valid = false;
        
        for (String arrayEntry : elementArray) {
            if (arrayEntry.equalsIgnoreCase(element)) {
                valid = true;
                break;
            }
        }
        
        return valid;
    }
        
    /**
     * Return all the artwork for a movie
     * @return
     */
    public List<Artwork> getArtwork() {
        return artwork;
    }

    /**
     * Get all the artwork of a specific type
     * @param artworkType
     * @return
     */
    public List<Artwork> getArtwork(String artworkType) {
        // Validate the Type and Size arguments
        if (!validateElement(Artwork.ARTWORK_TYPES, artworkType)) {
            return null;
        }

        List<Artwork> artworkList = new ArrayList<Artwork>();
        
        for (Artwork singleArtwork : artwork) {
            if (singleArtwork.getType().equalsIgnoreCase(artworkType)) {
                artworkList.add(singleArtwork);
            }
        }
        
        return artworkList;
    }

    /**
     * Get all artwork of a specific Type and Size
     * @param artworkType
     * @param artworkSize
     * @return
     */
    public List<Artwork> getArtwork(String artworkType, String artworkSize) {
        List<Artwork> artworkList = new ArrayList<Artwork>();
        // Validate the Type and Size arguments
        if (!validateElement(Artwork.ARTWORK_TYPES, artworkType) && !validateElement(Artwork.ARTWORK_SIZES, artworkSize)) {
            return null;
        }
        
        for (Artwork singleArtwork : artwork) {
            if (singleArtwork.getType().equalsIgnoreCase(artworkType) && singleArtwork.getSize().equalsIgnoreCase(artworkSize)) {
                artworkList.add(singleArtwork);
            }
        }
        
        return artworkList;
    }

    /**
     * Return a specific artwork entry for a Type & Size
     * @param artworkType
     * @param artworkSize
     * @param artworkNumber
     * @return
     */
    public Artwork getArtwork(String artworkType, String artworkSize, int artworkNumber) {
        // Validate the Type and Size arguments
        if (!validateElement(Artwork.ARTWORK_TYPES, artworkType) && !validateElement(Artwork.ARTWORK_SIZES, artworkSize)) {
            return null;
        }
        
        // Validate the number
        if (artworkNumber <= 0) {
            artworkNumber = 0;
        } else {
            // Artwork elements start at 0 (Zero)
            artworkNumber -= 1;
        }
        
        List<Artwork> artworkList = getArtwork(artworkType, artworkSize);
        
        int artworkCount = artworkList.size();
        if (artworkCount < 1) {
            return null;
        }
        
        // If the number requested is greater than the array size, loop around until it's within scope
        while (artworkNumber > artworkCount) {
            artworkNumber = artworkNumber - artworkCount;
        }
        
        return artworkList.get(artworkNumber);
    }

}
