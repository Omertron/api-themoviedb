/*
 *      Copyright (c) 2004-2015 Stuart Boston
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
package com.omertron.themoviedbapi.model.tv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.omertron.themoviedbapi.enumeration.TVMethod;
import com.omertron.themoviedbapi.interfaces.AppendToResponse;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.change.ChangeKeyItem;
import com.omertron.themoviedbapi.model.keyword.Keyword;
import com.omertron.themoviedbapi.model.media.AlternativeTitle;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.media.Translation;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.movie.ProductionCompany;
import com.omertron.themoviedbapi.model.network.Network;
import com.omertron.themoviedbapi.model.person.ContentRating;
import com.omertron.themoviedbapi.model.person.ExternalID;
import com.omertron.themoviedbapi.model.person.PersonBasic;
import com.omertron.themoviedbapi.results.WrapperChanges;
import com.omertron.themoviedbapi.results.WrapperGenericList;
import com.omertron.themoviedbapi.results.WrapperImages;
import com.omertron.themoviedbapi.results.WrapperTranslations;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Stuart
 */
public class TVInfo extends TVBasic implements Serializable, AppendToResponse<TVMethod> {

    private static final long serialVersionUID = 100L;

    @JsonProperty("created_by")
    private List<PersonBasic> createdBy;
    @JsonProperty("episode_run_time")
    private List<Integer> episodeRunTime;
    @JsonProperty("genres")
    private List<Genre> genres;
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("in_production")
    private boolean inProduction;
    @JsonProperty("languages")
    private List<String> languages;
    @JsonProperty("last_air_date")
    private String lastAirDate;
    @JsonProperty("networks")
    private List<Network> networks;
    @JsonProperty("number_of_episodes")
    private int numberOfEpisodes;
    @JsonProperty("number_of_seasons")
    private int numberOfSeasons;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("production_companies")
    private List<ProductionCompany> productionCompanies;
    @JsonProperty("seasons")
    private List<TVSeasonBasic> seasons;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
    // AppendToResponse
    private final Set<TVMethod> methods = EnumSet.noneOf(TVMethod.class);
    // AppendToResponse Properties
    private List<AlternativeTitle> alternativeTitles = Collections.emptyList();
    private List<ChangeKeyItem> changes = Collections.emptyList();
    private List<ContentRating> contentRatings = Collections.emptyList();
    private MediaCreditList credits = new MediaCreditList();
    private ExternalID externalIDs = new ExternalID();
    private List<Artwork> images = Collections.emptyList();
    private List<Keyword> keywords = Collections.emptyList();
    private List<Translation> translations = Collections.emptyList();
    private List<TVInfo> similarTV = Collections.emptyList();
    private List<Video> videos = Collections.emptyList();

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public List<PersonBasic> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<PersonBasic> createdBy) {
        this.createdBy = createdBy;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<TVSeasonBasic> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<TVSeasonBasic> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    //</editor-fold>

    private void addMethod(TVMethod method) {
        methods.add(method);
    }

    @Override
    public boolean hasMethod(TVMethod method) {
        return methods.contains(method);
    }

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Setters">
    @JsonSetter("alternative_titles")
    public void setAlternativeTitles(WrapperGenericList<AlternativeTitle> alternativeTitles) {
        this.alternativeTitles = alternativeTitles.getResults();
        addMethod(TVMethod.ALTERNATIVE_TITLES);
    }

    @JsonSetter("changes")
    public void setChanges(WrapperChanges changes) {
        this.changes = changes.getChangedItems();
        addMethod(TVMethod.CHANGES);
    }

    @JsonSetter("content_ratings")
    public void setContentRatings(WrapperGenericList<ContentRating> contentRatings) {
        this.contentRatings = contentRatings.getResults();
        addMethod(TVMethod.CONTENT_RATINGS);
    }

    @JsonSetter("credits")
    public void setCredits(MediaCreditList credits) {
        this.credits = credits;
        addMethod(TVMethod.CREDITS);
    }

    @JsonSetter("external_ids")
    public void setExternalIDs(ExternalID externalIDs) {
        this.externalIDs = externalIDs;
        addMethod(TVMethod.EXTERNAL_IDS);
    }

    @JsonSetter("images")
    public void setImages(WrapperImages images) {
        this.images = images.getAll();
        addMethod(TVMethod.IMAGES);
    }

    @JsonSetter("keywords")
    public void setKeywords(WrapperGenericList<Keyword> keywords) {
        this.keywords = keywords.getResults();
        addMethod(TVMethod.KEYWORDS);
    }

    @JsonSetter("translations")
    public void setTranslations(WrapperTranslations translations) {
        this.translations = translations.getTranslations();
        addMethod(TVMethod.TRANSLATIONS);
    }

    @JsonSetter("similar")
    public void setSimilarTV(WrapperGenericList<TVInfo> similarTV) {
        this.similarTV = similarTV.getResults();
        addMethod(TVMethod.SIMILAR);
    }

    @JsonSetter("videos")
    public void setVideos(WrapperGenericList<Video> videos) {
        this.videos = videos.getResults();
        addMethod(TVMethod.VIDEOS);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Getters">
    public List<AlternativeTitle> getAlternativeTitles() {
        return alternativeTitles;
    }

    public List<ChangeKeyItem> getChanges() {
        return changes;
    }

    public List<ContentRating> getContentRatings() {
        return contentRatings;
    }

    public MediaCreditList getCredits() {
        return credits;
    }

    public ExternalID getExternalIDs() {
        return externalIDs;
    }

    public List<Artwork> getImages() {
        return images;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public List<TVInfo> getSimilarTV() {
        return similarTV;
    }

    public List<Video> getVideos() {
        return videos;
    }
    //</editor-fold>
}
