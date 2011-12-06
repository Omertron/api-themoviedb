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

import com.moviejukebox.themoviedb.TheMovieDb;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.moviejukebox.themoviedb.tools.ModelTools;
import java.util.logging.Logger;

/**
 *  This is the Movie Search bean for the MovieDb.org search
 *
 *  @author Stuart.Boston
 */

public class MovieDB extends ModelTools implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = TheMovieDb.getLogger();

    public static final String UNKNOWN = "UNKNOWN";

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
    private int version             = -1;
    private Date lastModifiedAt;
    private List<Category> categories = new ArrayList<Category>();
    private List<Studio>   studios    = new ArrayList<Studio>();
    private List<Country>  countries  = new ArrayList<Country>();
    private List<Person>   people     = new ArrayList<Person>();

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

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public void setLastModifiedAt(String lastModifiedAt) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            setLastModifiedAt(df.parse(lastModifiedAt));
        } catch (ParseException ex) {
            logger.fine("MovieDB: Error parsing date: " + lastModifiedAt);
        }
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[MovieDB=[popularity=");
        builder.append(popularity);
        builder.append("][translated=");
        builder.append(translated);
        builder.append("][adult=");
        builder.append(adult);
        builder.append("][language=");
        builder.append(language);
        builder.append("][title=");
        builder.append(title);
        builder.append("][originalName=");
        builder.append(originalName);
        builder.append("][alternativeName=");
        builder.append(alternativeName);
        builder.append("][type=");
        builder.append(type);
        builder.append("][id=");
        builder.append(id);
        builder.append("][imdb=");
        builder.append(imdb);
        builder.append("][url=");
        builder.append(url);
        builder.append("][overview=");
        builder.append(overview);
        builder.append("][rating=");
        builder.append(rating);
        builder.append("][tagline=");
        builder.append(tagline);
        builder.append("][certification=");
        builder.append(certification);
        builder.append("][releaseDate=");
        builder.append(releaseDate);
        builder.append("][runtime=");
        builder.append(runtime);
        builder.append("][budget=");
        builder.append(budget);
        builder.append("][revenue=");
        builder.append(revenue);
        builder.append("][homepage=");
        builder.append(homepage);
        builder.append("][trailer=");
        builder.append(trailer);
        builder.append("][version=");
        builder.append(version);
        builder.append("][lastModifiedAt=");
        builder.append(lastModifiedAt);
        builder.append("][categories=");
        builder.append(categories);
        builder.append("][studios=");
        builder.append(studios);
        builder.append("][countries=");
        builder.append(countries);
        builder.append("][people=");
        builder.append(people);
        builder.append("]]");
        return builder.toString();
    }
}
