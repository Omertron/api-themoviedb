/*
 *      Copyright (c) 2004-2014 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.wrapper.*;

import java.util.List;

/**
 * Movie Bean
 *
 * @author stuart.boston
 */
public class MovieDb extends AbstractJsonMapping {

    private static final long serialVersionUID = 1L;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("id")
    private int id;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("popularity")
    private float popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("title")
    private String title;
    @JsonProperty("adult")
    private boolean adult;
    @JsonProperty("belongs_to_collection")
    private Collection belongsToCollection;
    @JsonProperty("budget")
    private long budget;
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
    private long revenue;
    @JsonProperty("runtime")
    private int runtime;
    @JsonProperty("spoken_languages")
    private List<Language> spokenLanguages;
    @JsonProperty("tagline")
    private String tagline;
    @JsonProperty("rating")
    private float userRating;
    @JsonProperty("vote_average")
    private float voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    @JsonProperty("status")
    private String status;
    // AppendToResponse Properties
    @JsonProperty("alternative_titles")
    private WrapperAlternativeTitles alternativeTitles;
    @JsonProperty("casts")
    private WrapperMovieCasts casts;
    @JsonProperty("images")
    private WrapperImages images;
    @JsonProperty("keywords")
    private WrapperMovieKeywords keywords;
    @JsonProperty("releases")
    private WrapperReleaseInfo releases;
    @JsonProperty("trailers")
    private WrapperTrailers trailers;
    @JsonProperty("translations")
    private WrapperTranslations translations;
    @JsonProperty("similar_movies")
    private WrapperMovie similarMovies;
    @JsonProperty("reviews")
    private WrapperReviews reviews;
    @JsonProperty("lists")
    private WrapperMovieList lists;

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

    public long getBudget() {
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

    public long getRevenue() {
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

    public String getStatus() {
        return status;
    }

    public float getUserRating() {
        return userRating;
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

    public void setBudget(long budget) {
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

    public void setRevenue(long revenue) {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Getters">
    public List<AlternativeTitle> getAlternativeTitles() {
        return alternativeTitles.getTitles();
    }

    public List<PersonCast> getCast() {
        return casts.getCast();
    }

    public List<PersonCrew> getCrew() {
        return casts.getCrew();
    }

    public List<Artwork> getImages() {
        return images.getAll();
    }

    public List<Keyword> getKeywords() {
        return keywords.getKeywords();
    }

    public List<ReleaseInfo> getReleases() {
        return releases.getCountries();
    }

    public List<Trailer> getTrailers() {
        return trailers.getAll();
    }

    public List<Translation> getTranslations() {
        return translations.getTranslations();
    }

    public List<MovieDb> getSimilarMovies() {
        return similarMovies.getMovies();
    }

    public List<MovieList> getLists() {
        return lists.getMovieList();
    }

    public List<Reviews> getReviews() {
        return reviews.getReviews();
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Setters">
    public void setAlternativeTitles(WrapperAlternativeTitles alternativeTitles) {
        this.alternativeTitles = alternativeTitles;
    }

    public void setCasts(WrapperMovieCasts casts) {
        this.casts = casts;
    }

    public void setImages(WrapperImages images) {
        this.images = images;
    }

    public void setKeywords(WrapperMovieKeywords keywords) {
        this.keywords = keywords;
    }

    public void setReleases(WrapperReleaseInfo releases) {
        this.releases = releases;
    }

    public void setTrailers(WrapperTrailers trailers) {
        this.trailers = trailers;
    }

    public void setTranslations(WrapperTranslations translations) {
        this.translations = translations;
    }

    public void setSimilarMovies(WrapperMovie similarMovies) {
        this.similarMovies = similarMovies;
    }

    public void setLists(WrapperMovieList lists) {
        this.lists = lists;
    }

    public void setReviews(WrapperReviews reviews) {
        this.reviews = reviews;
    }
    // </editor-fold>

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
        if (this.id != other.id) {
            return false;
        }
        if ((this.imdbID == null) ? (other.imdbID != null) : !this.imdbID.equals(other.imdbID)) {
            return false;
        }
        if (this.runtime != other.runtime) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
        hash = 89 * hash + (this.imdbID != null ? this.imdbID.hashCode() : 0);
        hash = 89 * hash + this.runtime;
        return hash;
    }
    // </editor-fold>
}
