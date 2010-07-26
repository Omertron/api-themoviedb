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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  This is the Movie Search bean for the MovieDb.org search
 *   
 *  @author Stuart.Boston
 */

public class MovieDB {
    public static String UNKNOWN = "UNKNOWN";

    private String  score       = UNKNOWN;
    private String  popularity  = UNKNOWN;
    private String  title       = UNKNOWN;
    private String  type        = UNKNOWN;
    private String  id          = UNKNOWN;
    private String  imdb        = UNKNOWN;
    private String  url         = UNKNOWN;
    private String  overview    = UNKNOWN;
    private String  rating      = UNKNOWN;
    private String  releaseDate = UNKNOWN;
    private String  runtime     = UNKNOWN;
    private String  budget      = UNKNOWN;
    private String  revenue     = UNKNOWN;
    private String  homepage    = UNKNOWN;
    private String  trailer     = UNKNOWN;
    private List<Artwork>  artwork     = new ArrayList<Artwork>();
    private List<Country>  countries   = new ArrayList<Country>();
    private List<Person>   people      = new ArrayList<Person>();
    private List<Category> categories  = new ArrayList<Category>();

    public String getScore() {
        return score;
    }
    
    public void setScore(String score) {
        this.score = score;
    }

    public String getPopularity() {
        return popularity;
    }
    
    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getImdb() {
        return imdb;
    }
    
    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public String getOverview() {
        return overview;
    }
    
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }
    
    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRuntime() {
        return runtime;
    }
    
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getBudget() {
        return budget;
    }
    
    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getRevenue() {
        return revenue;
    }
    
    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getHomepage() {
        return homepage;
    }
    
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getTrailer() {
        return trailer;
    }
    
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

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
        
    public List<Country> getProductionCountries() {
        return countries;
    }
    
    public void addProductionCountry(Country country) {
        if (country != null) {
            countries.add(country);
        }
    }
    
    public List<Person> getPeople() {
        return people;
    }
    
    public void addPerson(Person person) {
        if (person != null) {
            people.add(person);
        }
    }

    public List<Category> getCategories() {
        return categories;
    }
    
    public void addCategory(Category category) {
        if (category != null) {
            categories.add(category);
        }
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
        
    /**
     * Get the first artwork that matches the Type and Size
     * @param artworkType
     * @param artworkSize
     * @return
     */
    public Artwork getFirstArtwork(String artworkType, String artworkSize) {
        return getArtwork(artworkType, artworkSize, 1);
    }
    
}
