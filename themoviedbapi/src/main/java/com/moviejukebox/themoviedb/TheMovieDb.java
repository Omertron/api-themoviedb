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
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * The MovieDb API. This is for version 3 of the API as specified here:
 * http://help.themoviedb.org/kb/api/about-3
 *
 * @author stuart.boston
 */
public class TheMovieDb {

    private static final Logger LOGGER = Logger.getLogger(TheMovieDb.class);
    private String apiKey;
    private TmdbConfiguration tmdbConfig;
    /*
     * API Methods These are not set to static so that multiple instances of the
     * API can co-exist
     */
    private static final String BASE_MOVIE = "movie/";
    private static final String BASE_PERSON = "person/";
    private final ApiUrl tmdbConfigUrl = new ApiUrl(this, "configuration");
    private final ApiUrl tmdbSearchMovie = new ApiUrl(this, "search/movie");
    private final ApiUrl tmdbSearchPeople = new ApiUrl(this, "search/person");
    private final ApiUrl tmdbCollectionInfo = new ApiUrl(this, "collection/");
    private final ApiUrl tmdbMovieInfo = new ApiUrl(this, BASE_MOVIE);
    private final ApiUrl tmdbMovieAltTitles = new ApiUrl(this, BASE_MOVIE, "/alternative_titles");
    private final ApiUrl tmdbMovieCasts = new ApiUrl(this, BASE_MOVIE, "/casts");
    private final ApiUrl tmdbMovieImages = new ApiUrl(this, BASE_MOVIE, "/images");
    private final ApiUrl tmdbMovieKeywords = new ApiUrl(this, BASE_MOVIE, "/keywords");
    private final ApiUrl tmdbMovieReleaseInfo = new ApiUrl(this, BASE_MOVIE, "/releases");
    private final ApiUrl tmdbMovieTrailers = new ApiUrl(this, BASE_MOVIE, "/trailers");
    private final ApiUrl tmdbMovieTranslations = new ApiUrl(this, BASE_MOVIE, "/translations");
    private final ApiUrl tmdbPersonInfo = new ApiUrl(this, BASE_PERSON);
    private final ApiUrl tmdbPersonCredits = new ApiUrl(this, BASE_PERSON, "/credits");
    private final ApiUrl tmdbPersonImages = new ApiUrl(this, BASE_PERSON, "/images");
    private final ApiUrl tmdbLatestMovie = new ApiUrl(this, "latest/movie");

    /*
     * Jackson JSON configuration
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * API for The Movie Db.
     *
     * @param apiKey
     * @throws IOException
     */
    public TheMovieDb(String apiKey) throws IOException {
        this.apiKey = apiKey;
        URL configUrl = tmdbConfigUrl.getQueryUrl("");
        mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
        tmdbConfig = mapper.readValue(configUrl, TmdbConfiguration.class);
        mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, false);
        FilteringLayout.addApiKey(apiKey);
    }

    /**
     * Get the API key that is to be used
     * @return
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Search Movies This is a good starting point to start finding movies on
     * TMDb. The idea is to be a quick and light method so you can iterate
     * through movies quickly. http://help.themoviedb.org/kb/api/search-movies
     * TODO: Make the allResults work
     */
    public List<MovieDb> searchMovie(String movieName, String language, boolean allResults) {
        try {
            URL url = tmdbSearchMovie.getQueryUrl(movieName, language, 1);
            WrapperResultList resultList = mapper.readValue(url, WrapperResultList.class);
            return resultList.getResults();
        } catch (IOException ex) {
            LOGGER.warn("Failed to find movie: " + ex.getMessage());
            return new ArrayList<MovieDb>();
        }
    }

    /**
     * This method is used to retrieve all of the basic movie information. It
     * will return the single highest rated poster and backdrop.
     *
     * @param movieId
     * @param language
     * @return
     */
    public MovieDb getMovieInfo(int movieId, String language) {
        try {
            URL url = tmdbMovieInfo.getIdUrl(movieId, language);
            return mapper.readValue(url, MovieDb.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie info: " + ex.getMessage());
        }
        return new MovieDb();
    }

    /**
     * This method is used to retrieve all of the basic movie information. It
     * will return the single highest rated poster and backdrop.
     *
     * @param movieId
     * @param language
     * @return
     */
    public MovieDb getMovieInfoImdb(String imdbId, String language) {
        try {
            URL url = tmdbMovieInfo.getIdUrl(imdbId, language);
            return mapper.readValue(url, MovieDb.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie info: " + ex.getMessage());
        }
        return new MovieDb();
    }

    /**
     * This method is used to retrieve all of the alternative titles we have for
     * a particular movie.
     *
     * @param movieId
     * @param country
     * @return
     */
    public List<AlternativeTitle> getMovieAlternativeTitles(int movieId, String country) {
        try {
            URL url = tmdbMovieAltTitles.getIdUrl(movieId, country);
            WrapperAlternativeTitles at = mapper.readValue(url, WrapperAlternativeTitles.class);
            return at.getTitles();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie alternative titles: " + ex.getMessage());
        }
        return new ArrayList<AlternativeTitle>();
    }

    /**
     * This method is used to retrieve all of the movie cast information. TODO:
     * Add a function to enrich the data with the people methods
     *
     * @param movieId
     * @return
     */
    public List<Person> getMovieCasts(int movieId) {
        List<Person> people = new ArrayList<Person>();

        try {
            URL url = tmdbMovieCasts.getIdUrl(movieId);
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
            LOGGER.warn("Failed to get movie casts: " + ex.getMessage());
        }
        return people;
    }

    /**
     * This method should be used when you’re wanting to retrieve all of the
     * images for a particular movie.
     *
     * @param movieId
     * @param language
     * @return
     */
    public List<Artwork> getMovieImages(int movieId, String language) {
        List<Artwork> artwork = new ArrayList<Artwork>();
        try {
            URL url = tmdbMovieImages.getIdUrl(movieId, language);
            WrapperImages mi = mapper.readValue(url, WrapperImages.class);

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
            LOGGER.warn("Failed to get movie images: " + ex.getMessage());
        }
        return artwork;
    }

    /**
     * This method is used to retrieve all of the keywords that have been added
     * to a particular movie. Currently, only English keywords exist.
     *
     * @param movieId
     * @return
     */
    public List<Keyword> getMovieKeywords(int movieId) {
        try {
            URL url = tmdbMovieKeywords.getIdUrl(movieId);
            WrapperMovieKeywords mk = mapper.readValue(url, WrapperMovieKeywords.class);
            return mk.getKeywords();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie keywords: " + ex.getMessage());
        }
        return new ArrayList<Keyword>();
    }

    /**
     * This method is used to retrieve all of the release and certification data
     * we have for a specific movie.
     *
     * @param movieId
     * @param language
     * @return
     */
    public List<ReleaseInfo> getMovieReleaseInfo(int movieId, String language) {
        try {
            URL url = tmdbMovieReleaseInfo.getIdUrl(movieId);
            WrapperReleaseInfo ri = mapper.readValue(url, WrapperReleaseInfo.class);
            return ri.getCountries();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie release information: " + ex.getMessage());
        }
        return new ArrayList<ReleaseInfo>();
    }

    /**
     * This method is used to retrieve all of the trailers for a particular
     * movie. Supported sites are YouTube and QuickTime.
     *
     * @param movieId
     * @param language
     * @return
     */
    public List<Trailer> getMovieTrailers(int movieId, String language) {
        List<Trailer> trailers = new ArrayList<Trailer>();
        try {
            URL url = tmdbMovieTrailers.getIdUrl(movieId);
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
            LOGGER.warn("Failed to get movie trailers: " + ex.getMessage());
        }
        return trailers;
    }

    /**
     * This method is used to retrieve a list of the available translations for
     * a specific movie.
     *
     * @param movieId
     * @return
     */
    public List<Translation> getMovieTranslations(int movieId) {
        try {
            URL url = tmdbMovieTranslations.getIdUrl(movieId);
            WrapperTranslations wt = mapper.readValue(url, WrapperTranslations.class);
            return wt.getTranslations();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie tranlations: " + ex.getMessage());
        }
        return new ArrayList<Translation>();
    }

    /**
     * This method is used to retrieve all of the basic information about a
     * movie collection. You can get the ID needed for this method by making a
     * getMovieInfo request for the belongs_to_collection.
     *
     * @param movieId
     * @param language
     * @return
     */
    public CollectionInfo getCollectionInfo(int movieId, String language) {
        try {
            URL url = tmdbCollectionInfo.getIdUrl(movieId);
            return mapper.readValue(url, CollectionInfo.class);
        } catch (IOException ex) {
            return new CollectionInfo();
        }
    }

    /**
     * Get the configuration information
     *
     * @return
     */
    public TmdbConfiguration getConfiguration() {
        return tmdbConfig;
    }

    /**
     * Generate the full image URL from the size and image path
     *
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
            LOGGER.warn(sb.toString());
            return returnUrl;
        }

        try {
            sb = new StringBuilder(tmdbConfig.getBaseUrl());
            sb.append(requiredSize);
            sb.append(imagePath);
            returnUrl = new URL(sb.toString());
        } catch (MalformedURLException ex) {
            LOGGER.warn("Failed to create image URL: " + ex.getMessage());
        }

        return returnUrl;
    }

    /**
     * This is a good starting point to start finding people on TMDb. The idea
     * is to be a quick and light method so you can iterate through people
     * quickly. TODO: Fix allResults
     */
    public List<Person> searchPeople(String personName, boolean allResults) {

        try {
            URL url = tmdbSearchPeople.getQueryUrl(personName, "", 1);
            WrapperPerson resultList = mapper.readValue(url, WrapperPerson.class);
            return resultList.getResults();
        } catch (IOException ex) {
            LOGGER.warn("Failed to find person: " + ex.getMessage());
            return new ArrayList<Person>();
        }
    }

    /**
     * This method is used to retrieve all of the basic person information. It
     * will return the single highest rated profile image.
     *
     * @param personId
     * @return
     */
    public Person getPersonInfo(int personId) {
        try {
            URL url = tmdbPersonInfo.getIdUrl(personId);
            return mapper.readValue(url, Person.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie info: " + ex.getMessage());
            return new Person();
        }
    }

    /**
     * This method is used to retrieve all of the cast & crew information for
     * the person. It will return the single highest rated poster for each movie
     * record.
     *
     * @param personId
     * @return
     */
    public List<PersonCredit> getPersonCredits(int personId) {
        List<PersonCredit> personCredits = new ArrayList<PersonCredit>();

        try {
            URL url = tmdbPersonCredits.getIdUrl(personId);
            WrapperPersonCredits pc = mapper.readValue(url, WrapperPersonCredits.class);

            // Add a cast member
            for (PersonCredit cast : pc.getCast()) {
                cast.setPersonType(PersonType.CAST);
                personCredits.add(cast);
            }

            // Add a crew member
            for (PersonCredit crew : pc.getCrew()) {
                crew.setPersonType(PersonType.CREW);
                personCredits.add(crew);
            }

            return personCredits;
        } catch (IOException ex) {
            LOGGER.warn("Failed to get person credits: " + ex.getMessage());
            return personCredits;
        }
    }

    /**
     * This method is used to retrieve all of the profile images for a person.
     *
     * @param personId
     * @return
     */
    public List<Artwork> getPersonImages(int personId) {
        List<Artwork> personImages = new ArrayList<Artwork>();

        try {
            URL url = tmdbPersonImages.getIdUrl(personId);
            WrapperImages images = mapper.readValue(url, WrapperImages.class);

            // Update the image type
            for (Artwork artwork : images.getProfiles()) {
                artwork.setArtworkType(ArtworkType.PROFILE);
                personImages.add(artwork);
            }

            return personImages;
        } catch (IOException ex) {
            LOGGER.warn("Failed to get person images: " + ex.getMessage());
            return personImages;
        }
    }

    /**
     * This method is used to retrieve the newest movie that was added to TMDb.
     * @return
     */
    public MovieDb getLatestMovie() {
        try {
            URL url = tmdbLatestMovie.getIdUrl("");
            return mapper.readValue(url, MovieDb.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get latest movie: " + ex.getMessage());
            return new MovieDb();
        }
    }

    /**
     * Compare the MovieDB object with a title & year
     *
     * @param moviedb The moviedb object to compare too
     * @param title The title of the movie to compare
     * @param year The year of the movie to compare
     * @return True if there is a match, False otherwise.
     */
    public static boolean compareMovies(MovieDb moviedb, String title, String year) {
        if ((moviedb == null) || (StringUtils.isBlank(title))) {
            return false;
        }

        if (StringUtils.isNotBlank(year)) {
            if (StringUtils.isNotBlank(moviedb.getReleaseDate())) {
                // Compare with year
                String movieYear = moviedb.getReleaseDate().substring(0, 4);
                if (movieYear.equals(year)) {
                    if (moviedb.getOriginalTitle().equalsIgnoreCase(title)) {
                        return true;
                    }

                    if (moviedb.getTitle().equalsIgnoreCase(title)) {
                        return true;
                    }
                }
            }
        } else {
            // Compare without year
            if (moviedb.getOriginalTitle().equalsIgnoreCase(title)) {
                return true;
            }

            if (moviedb.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }
}
