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
import java.util.List;

import com.moviejukebox.themoviedb.tools.ModelTools;

/**
 *  This is the Movie Search bean for the MovieDb.org search
 *   
 *  @author Stuart.Boston
 */

public class MovieDB extends ModelTools {
    public static String UNKNOWN = "UNKNOWN";

    private String popularity       = UNKNOWN;
    private String translated       = UNKNOWN;
    private String adult            = UNKNOWN;
    private String language         = UNKNOWN;
    private String title            = UNKNOWN; // "name" in the XML
    private String originalName     = UNKNOWN; // "original_name" in the XML
    private String alternativeName  = UNKNOWN; // "alternative_name" in the XML
    private String type             = UNKNOWN;
    private String id               = UNKNOWN;
    private String imdb             = UNKNOWN; // "imdb_id" in the XML
    private String url              = UNKNOWN;
    private String overview         = UNKNOWN;
    private String rating           = UNKNOWN;
    private String tagline          = UNKNOWN;
    private String certification    = UNKNOWN;
    private String releaseDate      = UNKNOWN; // "released" in the XML
    private String runtime          = UNKNOWN;
    private String budget           = UNKNOWN;
    private String revenue          = UNKNOWN;
    private String homepage         = UNKNOWN;
    private String trailer          = UNKNOWN;
    private List<Category> categories = new ArrayList<Category>();
    private List<Studio>   studios    = new ArrayList<Studio>();
    private List<Country>  countries  = new ArrayList<Country>();
    private List<Person>   people     = new ArrayList<Person>();
    private List<Artwork>  artwork    = new ArrayList<Artwork>();

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

    public String getTranslated() {
        return translated;
    }

    public String getAdult() {
        return adult;
    }

    public String getLanguage() {
        return language;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public String getTagline() {
        return tagline;
    }

    public String getCertification() {
        return certification;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<Artwork> getArtwork() {
        return artwork;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }
    
    public void addStudio(Studio studio) {
        if (studio != null) {
            this.studios.add(studio);
        }
    }
    
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public void setArtwork(List<Artwork> artwork) {
        this.artwork = artwork;
    }
}
