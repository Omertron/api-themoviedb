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
package com.moviejukebox.themoviedb;

import com.moviejukebox.themoviedb.model.*;
import com.moviejukebox.themoviedb.tools.ApiUrl;
import com.moviejukebox.themoviedb.tools.FilteringLayout;
import com.moviejukebox.themoviedb.wrapper.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * The MovieDB API.
 * This is for version 3 of the API as specified here:
 * http://help.themoviedb.org/kb/api/about-3
 * @author stuart.boston
 */
public class TheMovieDB {

    private static final Logger logger = Logger.getLogger(TheMovieDB.class);
    private static String API_KEY;
    private static TmdbConfiguration tmdbConfig;
    /*
     * TheMovieDB API URLs
     */
    protected static final String TMDB_API_BASE = "http://api.themoviedb.org/3/";
    /*
     * API  Methods
     */
    protected static final ApiUrl TMDB_CONFIG_URL = new ApiUrl("configuration");
    protected static final ApiUrl TMDB_SEARCH_MOVIE = new ApiUrl("search/movie");
    protected static final ApiUrl TMDB_SEARCH_PEOPLE = new ApiUrl("search/person");
    protected static final ApiUrl TMDB_COLLECTION_INFO = new ApiUrl("collection/");
    protected static final ApiUrl TMDB_MOVIE_INFO = new ApiUrl("movie/");
    protected static final ApiUrl TMDB_MOVIE_ALT_TITLES = new ApiUrl("movie/", "/alternative_titles");
    protected static final ApiUrl TMDB_MOVIE_CASTS = new ApiUrl("movie/", "/casts");
    protected static final ApiUrl TMDB_MOVIE_IMAGES = new ApiUrl("movie/", "/images");
    protected static final ApiUrl TMDB_MOVIE_KEYWORDS = new ApiUrl("movie/", "/keywords");
    protected static final ApiUrl TMDB_MOVIE_RELEASE_INFO = new ApiUrl("movie/", "/releases");
    protected static final ApiUrl TMDB_MOVIE_TRAILERS = new ApiUrl("movie/", "/trailers");
    protected static final ApiUrl TMDB_MOVIE_TRANSLATIONS = new ApiUrl("movie/", "/translations");
    protected static final ApiUrl TMDB_PERSON_INFO = new ApiUrl("person");
    protected static final ApiUrl TMDB_PERSON_CREDITS = new ApiUrl("person/", "/credits");
    protected static final ApiUrl TMDB_PERSON_IMAGES = new ApiUrl("person/", "/images");
    protected static final ApiUrl TMDB_LATEST_MOVIE = new ApiUrl("latest/movie");

    /*
     * Jackson JSON configuration
     */
    private static ObjectMapper mapper = new ObjectMapper();

    public TheMovieDB(String apiKey) throws IOException {
        TheMovieDB.API_KEY = apiKey;
        URL configUrl = TMDB_CONFIG_URL.getQueryUrl("");
        mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
        tmdbConfig = mapper.readValue(configUrl, TmdbConfiguration.class);
        mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, false);
        FilteringLayout.addApiKey(apiKey);
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getApiBase() {
        return TMDB_API_BASE;
    }

    /**
     * Search Movies
     * This is a good starting point to start finding movies on TMDb.
     * The idea is to be a quick and light method so you can iterate through movies quickly.
     * http://help.themoviedb.org/kb/api/search-movies
     */
    public List<MovieDB> searchMovie(String movieName, String language, boolean allResults) {
        try {
            URL url = TMDB_SEARCH_MOVIE.getQueryUrl(movieName, language, 1);
            WrapperResultList resultList = mapper.readValue(url, WrapperResultList.class);
            return resultList.getResults();
        } catch (IOException ex) {
            logger.warn("Failed to find movie: " + ex.getMessage());
            return new ArrayList<MovieDB>();
        }
    }

    /**
     * This method is used to retrieve all of the basic movie information.
     * It will return the single highest rated poster and backdrop.
     * @param movieId
     * @param language
     * @return
     */
    public MovieDB getMovieInfo(int movieId, String language) {
        try {
            URL url = TMDB_MOVIE_INFO.getIdUrl(movieId, language);
            MovieDB movieDb = mapper.readValue(url, MovieDB.class);
            return movieDb;
        } catch (IOException ex) {
            logger.warn("Failed to get movie info: " + ex.getMessage());
        }
        return new MovieDB();
    }

    /**
     * This method is used to retrieve all of the alternative titles we have for a particular movie.
     * @param movieId
     * @param country
     * @return
     */
    public List<AlternativeTitle> getMovieAlternativeTitles(int movieId, String country) {
        try {
            URL url = TMDB_MOVIE_ALT_TITLES.getIdUrl(movieId, country);
            WrapperAlternativeTitles at = mapper.readValue(url, WrapperAlternativeTitles.class);
            return at.getTitles();
        } catch (IOException ex) {
            logger.warn("Failed to get movie alternative titles: " + ex.getMessage());
        }
        return new ArrayList<AlternativeTitle>();
    }

    /**
     * This method is used to retrieve all of the movie cast information.
     * @param movieId
     * @return
     */
    public List<Person> getMovieCasts(int movieId) {
        List<Person> people = new ArrayList<Person>();

        try {
            URL url = TMDB_MOVIE_CASTS.getIdUrl(movieId);
            WrapperMovieCasts mc = mapper.readValue(url, WrapperMovieCasts.class);

            // Add a cast member
            for (PersonCast cast : mc.getCast()) {
                Person person = new Person();
                person.addCast(cast.getId(), cast.getName(), cast.getProfilePath(), cast.getCharacter(), cast.getOrder());
                people.add(person);
            }

            // Add a crew member
            for (PersonCrew crew : mc.getCrew()) {
                Person person = new Person();
                person.addCrew(crew.getId(), crew.getName(), crew.getProfilePath(), crew.getDepartment(), crew.getJob());
                people.add(person);
            }

            return people;
        } catch (IOException ex) {
            logger.warn("Failed to get movie casts: " + ex.getMessage());
        }
        return people;
    }

    /**
     * This method should be used when you’re wanting to retrieve all of the images for a particular movie.
     * @param movieId
     * @param language
     * @return
     */
    public List<Artwork> getMovieImages(int movieId, String language) {
        List<Artwork> artwork = new ArrayList<Artwork>();
        try {
            URL url = TMDB_MOVIE_IMAGES.getIdUrl(movieId, language);
            WrapperMovieImages mi = mapper.readValue(url, WrapperMovieImages.class);

            // Add all the posters to the list
            for (Artwork poster : mi.getPosters()) {
                poster.setArtworkType(ArtworkType.POSTER);
                artwork.add(poster);
            }

            // Add all the backdrops to the list
            for (Artwork backdrop : mi.getBackdrops()) {
                backdrop.setArtworkType(ArtworkType.BACKDROP);
                artwork.add(backdrop);
            }

            return artwork;
        } catch (IOException ex) {
            logger.warn("Failed to get movie images: " + ex.getMessage());
        }
        return artwork;
    }

    /**
     * This method is used to retrieve all of the keywords that have been added to a particular movie.
     * Currently, only English keywords exist.
     * @param movieId
     * @return
     */
    public List<Keyword> getMovieKeywords(int movieId) {
        try {
            URL url = TMDB_MOVIE_KEYWORDS.getIdUrl(movieId);
            WrapperMovieKeywords mk = mapper.readValue(url, WrapperMovieKeywords.class);
            return mk.getKeywords();
        } catch (IOException ex) {
            logger.warn("Failed to get movie keywords: " + ex.getMessage());
        }
        return new ArrayList<Keyword>();
    }

    /**
     * This method is used to retrieve all of the release and certification data we have for a specific movie.
     * @param movieId
     * @param language
     * @return
     */
    public List<ReleaseInfo> getMovieReleaseInfo(int movieId, String language) {
        try {
            URL url = TMDB_MOVIE_RELEASE_INFO.getIdUrl(movieId);
            WrapperReleaseInfo ri = mapper.readValue(url, WrapperReleaseInfo.class);
            return ri.getCountries();
        } catch (IOException ex) {
            logger.warn("Failed to get movie release information: " + ex.getMessage());
        }
        return new ArrayList<ReleaseInfo>();
    }

    /**
     * This method is used to retrieve all of the trailers for a particular movie.
     * Supported sites are YouTube and QuickTime.
     * @param movieId
     * @param language
     * @return
     */
    public List<Trailer> getMovieTrailers(int movieId, String language) {
        List<Trailer> trailers = new ArrayList<Trailer>();
        try {
            URL url = TMDB_MOVIE_TRAILERS.getIdUrl(movieId);
            WrapperTrailers wt = mapper.readValue(url, WrapperTrailers.class);

            // Add the trailer to the return list along with it's source
            for (Trailer trailer : wt.getQuicktime()) {
                trailer.setWebsite(Trailer.WEBSITE_QUICKTIME);
                trailers.add(trailer);
            }

            // Add the trailer to the return list along with it's source
            for (Trailer trailer : wt.getYoutube()) {
                trailer.setWebsite(Trailer.WEBSITE_YOUTUBE);
                trailers.add(trailer);
            }
            return trailers;
        } catch (IOException ex) {
            logger.warn("Failed to get movie trailers: " + ex.getMessage());
        }
        return trailers;
    }

    /**
     * This method is used to retrieve a list of the available translations for a specific movie.
     * @param movieId
     * @return
     */
    public List<Translation> getMovieTranslations(int movieId) {
        try {
            URL url = TMDB_MOVIE_TRANSLATIONS.getIdUrl(movieId);
            WrapperTranslations wt = mapper.readValue(url, WrapperTranslations.class);
            return wt.getTranslations();
        } catch (IOException ex) {
            logger.warn("Failed to get movie tranlations: " + ex.getMessage());
        }
        return new ArrayList<Translation>();
    }

    /**
     * This method is used to retrieve all of the basic information about a movie collection.
     * You can get the ID needed for this method by making a getMovieInfo request for the belongs_to_collection.
     * @param movieId
     * @param language
     * @return
     */
    public CollectionInfo getCollectionInfo(int movieId, String language) {
        try {
            URL url = TMDB_COLLECTION_INFO.getIdUrl(movieId);
            CollectionInfo col = mapper.readValue(url, CollectionInfo.class);
            return col;
        } catch (IOException ex) {
            return new CollectionInfo();
        }
    }

    /**
     * Get the configuration information
     * @return
     */
    public TmdbConfiguration getConfiguration() {
        return tmdbConfig;
    }

    /**
     * Generate the full image URL from the size and image path
     * @param imagePath
     * @param requiredSize
     * @return
     */
    public URL createImageUrl(String imagePath, String requiredSize) {
        URL returnUrl = null;
        StringBuilder sb;

        if (!tmdbConfig.isValidSize(requiredSize)) {
            sb = new StringBuilder();
            sb.append(" - Invalid size requested: ").append(requiredSize);
            logger.warn(sb.toString());
            return returnUrl;
        }

        try {
            sb = new StringBuilder(tmdbConfig.getBaseUrl());
            sb.append(requiredSize);
            sb.append(imagePath);
            returnUrl = new URL(sb.toString());
        } catch (MalformedURLException ex) {
            logger.warn("Failed to create image URL: " + ex.getMessage());
        }

        return returnUrl;
    }
}
