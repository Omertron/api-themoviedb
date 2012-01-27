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

import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Movie Bean
 * @author stuart.boston
 */
public class MovieDb {

    /*
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(MovieDb.class);
    /*
     * Properties
     */
    @JsonProperty(("backdrop_path"))
    private String backdropPath;
    @JsonProperty(("id"))
    private int id;
    @JsonProperty(("original_title"))
    private String originalTitle;
    @JsonProperty(("popularity"))
    private float popularity;
    @JsonProperty(("poster_path"))
    private String posterPath;
    @JsonProperty(("release_date"))
    private String releaseDate;
    @JsonProperty(("title"))
    private String title;
    @JsonProperty("adult")
    private boolean adult;
    @JsonProperty("belongs_to_collection")
    private Collection belongsToCollection;
    @JsonProperty("budget")
    private int budget;
    @JsonProperty("genres")
    private List<Genre> genres;
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("imdb_id")
    private String imdbID;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("production_companies")
    private List<ProductionCompany> productionCompanies;
    @JsonProperty("production_countries")
    private List<ProductionCountry> productionCountries;
    @JsonProperty("revenue")
    private int revenue;
    @JsonProperty("runtime")
    private int runtime;
    @JsonProperty("spoken_languages")
    private List<Language> spokenLanguages;
    @JsonProperty("tagline")
    private String tagline;
    @JsonProperty("vote_average")
    private float voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;

    // <editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getBackdropPath() {
        return backdropPath;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAdult() {
        return adult;
    }

    public Collection getBelongsToCollection() {
        return belongsToCollection;
    }

    public int getBudget() {
        return budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getOverview() {
        return overview;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<Language> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getTagline() {
        return tagline;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setBelongsToCollection(Collection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setSpokenLanguages(List<Language> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
    // </editor-fold>

    /**
     * Handle unknown properties and print a message
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown property: '").append(key);
        sb.append("' value: '").append(value).append("'");
        LOGGER.warn(sb.toString());
    }

    //<editor-fold defaultstate="collapsed" desc="Equals and HashCode">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MovieDb other = (MovieDb) obj;
        if ((this.backdropPath == null) ? (other.backdropPath != null) : !this.backdropPath.equals(other.backdropPath)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if ((this.originalTitle == null) ? (other.originalTitle != null) : !this.originalTitle.equals(other.originalTitle)) {
            return false;
        }
        if (Float.floatToIntBits(this.popularity) != Float.floatToIntBits(other.popularity)) {
            return false;
        }
        if ((this.posterPath == null) ? (other.posterPath != null) : !this.posterPath.equals(other.posterPath)) {
            return false;
        }
        if ((this.releaseDate == null) ? (other.releaseDate != null) : !this.releaseDate.equals(other.releaseDate)) {
            return false;
        }
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if (this.adult != other.adult) {
            return false;
        }
        if (this.belongsToCollection != other.belongsToCollection && (this.belongsToCollection == null || !this.belongsToCollection.equals(other.belongsToCollection))) {
            return false;
        }
        if (this.budget != other.budget) {
            return false;
        }
        if (this.genres != other.genres && (this.genres == null || !this.genres.equals(other.genres))) {
            return false;
        }
        if ((this.homepage == null) ? (other.homepage != null) : !this.homepage.equals(other.homepage)) {
            return false;
        }
        if ((this.imdbID == null) ? (other.imdbID != null) : !this.imdbID.equals(other.imdbID)) {
            return false;
        }
        if ((this.overview == null) ? (other.overview != null) : !this.overview.equals(other.overview)) {
            return false;
        }
        if (this.productionCompanies != other.productionCompanies && (this.productionCompanies == null || !this.productionCompanies.equals(other.productionCompanies))) {
            return false;
        }
        if (this.productionCountries != other.productionCountries && (this.productionCountries == null || !this.productionCountries.equals(other.productionCountries))) {
            return false;
        }
        if (this.revenue != other.revenue) {
            return false;
        }
        if (this.runtime != other.runtime) {
            return false;
        }
        if (this.spokenLanguages != other.spokenLanguages && (this.spokenLanguages == null || !this.spokenLanguages.equals(other.spokenLanguages))) {
            return false;
        }
        if ((this.tagline == null) ? (other.tagline != null) : !this.tagline.equals(other.tagline)) {
            return false;
        }
        if (Float.floatToIntBits(this.voteAverage) != Float.floatToIntBits(other.voteAverage)) {
            return false;
        }
        if (this.voteCount != other.voteCount) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.backdropPath != null ? this.backdropPath.hashCode() : 0);
        hash = 97 * hash + this.id;
        hash = 97 * hash + (this.originalTitle != null ? this.originalTitle.hashCode() : 0);
        hash = 97 * hash + Float.floatToIntBits(this.popularity);
        hash = 97 * hash + (this.posterPath != null ? this.posterPath.hashCode() : 0);
        hash = 97 * hash + (this.releaseDate != null ? this.releaseDate.hashCode() : 0);
        hash = 97 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 97 * hash + (this.adult ? 1 : 0);
        hash = 97 * hash + (this.belongsToCollection != null ? this.belongsToCollection.hashCode() : 0);
        hash = 97 * hash + this.budget;
        hash = 97 * hash + (this.genres != null ? this.genres.hashCode() : 0);
        hash = 97 * hash + (this.homepage != null ? this.homepage.hashCode() : 0);
        hash = 97 * hash + (this.imdbID != null ? this.imdbID.hashCode() : 0);
        hash = 97 * hash + (this.overview != null ? this.overview.hashCode() : 0);
        hash = 97 * hash + (this.productionCompanies != null ? this.productionCompanies.hashCode() : 0);
        hash = 97 * hash + (this.productionCountries != null ? this.productionCountries.hashCode() : 0);
        hash = 97 * hash + this.revenue;
        hash = 97 * hash + this.runtime;
        hash = 97 * hash + (this.spokenLanguages != null ? this.spokenLanguages.hashCode() : 0);
        hash = 97 * hash + (this.tagline != null ? this.tagline.hashCode() : 0);
        hash = 97 * hash + Float.floatToIntBits(this.voteAverage);
        hash = 97 * hash + this.voteCount;
        return hash;
    }
    //</editor-fold>

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[MovieDB=");
        sb.append("[backdropPath=").append(backdropPath);
        sb.append("],[id=").append(id);
        sb.append("],[originalTitle=").append(originalTitle);
        sb.append("],[popularity=").append(popularity);
        sb.append("],[posterPath=").append(posterPath);
        sb.append("],[releaseDate=").append(releaseDate);
        sb.append("],[title=").append(title);
        sb.append("],[adult=").append(adult);
        sb.append("],[belongsToCollection=").append(belongsToCollection);
        sb.append("],[budget=").append(budget);
        sb.append("],[genres=").append(genres);
        sb.append("],[homepage=").append(homepage);
        sb.append("],[imdbID=").append(imdbID);
        sb.append("],[overview=").append(overview);
        sb.append("],[productionCompanies=").append(productionCompanies);
        sb.append("],[productionCountries=").append(productionCountries);
        sb.append("],[revenue=").append(revenue);
        sb.append("],[runtime=").append(runtime);
        sb.append("],[spokenLanguages=").append(spokenLanguages);
        sb.append("],[tagline=").append(tagline);
        sb.append("],[voteAverage=").append(voteAverage);
        sb.append("],[voteCount=").append(voteCount);
        sb.append("]]");
        return sb.toString();
    }
}
