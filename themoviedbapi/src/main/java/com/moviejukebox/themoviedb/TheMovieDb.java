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

import com.moviejukebox.themoviedb.MovieDbException.MovieDbExceptionType;
import com.moviejukebox.themoviedb.model.*;
import com.moviejukebox.themoviedb.tools.ApiUrl;
import com.moviejukebox.themoviedb.tools.FilteringLayout;
import com.moviejukebox.themoviedb.tools.WebBrowser;
import com.moviejukebox.themoviedb.wrapper.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
    public TheMovieDb(String apiKey) throws MovieDbException {
        this.apiKey = apiKey;
        URL configUrl = tmdbConfigUrl.getQueryUrl("");
        String webPage = WebBrowser.request(configUrl);
        FilteringLayout.addApiKey(apiKey);

        try {
            WrapperConfig wc = mapper.readValue(webPage, WrapperConfig.class);
            tmdbConfig = wc.getTmdbConfiguration();
        } catch (IOException error) {
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, "Failed to read configuration");
        }
    }

    /**
     * Get the API key that is to be used
     *
     * @return
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Set the proxy information
     *
     * @param host
     * @param port
     * @param username
     * @param password
     */
    public void setProxy(String host, String port, String username, String password) {
        WebBrowser.setProxyHost(host);
        WebBrowser.setProxyPort(port);
        WebBrowser.setProxyUsername(username);
        WebBrowser.setProxyPassword(password);
    }

    /**
     * Set the connection and read time out values
     *
     * @param connect
     * @param read
     */
    public void setTimeout(int connect, int read) {
        WebBrowser.setWebTimeoutConnect(connect);
        WebBrowser.setWebTimeoutRead(read);
    }

    /**
     * Search Movies This is a good starting point to start finding movies on
     * TMDb. The idea is to be a quick and light method so you can iterate
     * through movies quickly. http://help.themoviedb.org/kb/api/search-movies
     * TODO: Make the allResults work
     */
    public List<MovieDb> searchMovie(String movieName, String language, boolean allResults) throws MovieDbException {

        URL url = tmdbSearchMovie.getQueryUrl(movieName, language, 1);
        String webPage = WebBrowser.request(url);
        try {
            WrapperResultList resultList = mapper.readValue(webPage, WrapperResultList.class);
            return resultList.getResults();
        } catch (IOException error) {
            LOGGER.warn("Failed to find movie: " + error.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
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
    public MovieDb getMovieInfo(int movieId, String language) throws MovieDbException {

        URL url = tmdbMovieInfo.getIdUrl(movieId, language);
        String webPage = WebBrowser.request(url);
        try {
            return mapper.readValue(webPage, MovieDb.class);
        } catch (IOException error) {
            LOGGER.warn("Failed to get movie info: " + error.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
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
    public MovieDb getMovieInfoImdb(String imdbId, String language) throws MovieDbException {

        URL url = tmdbMovieInfo.getIdUrl(imdbId, language);
        String webPage = WebBrowser.request(url);
        try {
            return mapper.readValue(webPage, MovieDb.class);
        } catch (IOException error) {
            LOGGER.warn("Failed to get movie info: " + error.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
    }

    /**
     * This method is used to retrieve all of the alternative titles we have for
     * a particular movie.
     *
     * @param movieId
     * @param country
     * @return
     */
    public List<AlternativeTitle> getMovieAlternativeTitles(int movieId, String country) throws MovieDbException {

        URL url = tmdbMovieAltTitles.getIdUrl(movieId, country);
        String webPage = WebBrowser.request(url);
        try {
            WrapperAlternativeTitles at = mapper.readValue(webPage, WrapperAlternativeTitles.class);
            return at.getTitles();
        } catch (IOException error) {
            LOGGER.warn("Failed to get movie alternative titles: " + error.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
    }

    /**
     * This method is used to retrieve all of the movie cast information. TODO:
     * Add a function to enrich the data with the people methods
     *
     * @param movieId
     * @return
     */
    public List<Person> getMovieCasts(int movieId) throws MovieDbException {

        List<Person> people = new ArrayList<Person>();

        URL url = tmdbMovieCasts.getIdUrl(movieId);
        String webPage = WebBrowser.request(url);
        try {
            WrapperMovieCasts mc = mapper.readValue(webPage, WrapperMovieCasts.class);

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
        } catch (IOException error) {
            LOGGER.warn("Failed to get movie casts: " + error.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
    }

    /**
     * This method should be used when you’re wanting to retrieve all of the
     * images for a particular movie.
     *
     * @param movieId
     * @param language
     * @return
     */
    public List<Artwork> getMovieImages(int movieId, String language) throws MovieDbException {

        List<Artwork> artwork = new ArrayList<Artwork>();
        URL url = tmdbMovieImages.getIdUrl(movieId, language);
        String webPage = WebBrowser.request(url);
        try {
            WrapperImages mi = mapper.readValue(webPage, WrapperImages.class);

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
        } catch (IOException error) {
            LOGGER.warn("Failed to get movie images: " + error.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
    }

    /**
     * This method is used to retrieve all of the keywords that have been added
     * to a particular movie. Currently, only English keywords exist.
     *
     * @param movieId
     * @return
     */
    public List<Keyword> getMovieKeywords(int movieId) throws MovieDbException {

        URL url = tmdbMovieKeywords.getIdUrl(movieId);
        String webPage = WebBrowser.request(url);

        try {
            WrapperMovieKeywords mk = mapper.readValue(webPage, WrapperMovieKeywords.class);
            return mk.getKeywords();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie keywords: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
    }

    /**
     * This method is used to retrieve all of the release and certification data
     * we have for a specific movie.
     *
     * @param movieId
     * @param language
     * @return
     */
    public List<ReleaseInfo> getMovieReleaseInfo(int movieId, String language) throws MovieDbException {

        URL url = tmdbMovieReleaseInfo.getIdUrl(movieId);
        String webPage = WebBrowser.request(url);

        try {
            WrapperReleaseInfo ri = mapper.readValue(webPage, WrapperReleaseInfo.class);
            return ri.getCountries();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie release information: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
    }

    /**
     * This method is used to retrieve all of the trailers for a particular
     * movie. Supported sites are YouTube and QuickTime.
     *
     * @param movieId
     * @param language
     * @return
     */
    public List<Trailer> getMovieTrailers(int movieId, String language) throws MovieDbException {

        List<Trailer> trailers = new ArrayList<Trailer>();
        URL url = tmdbMovieTrailers.getIdUrl(movieId, language);
        String webPage = WebBrowser.request(url);

        try {
            WrapperTrailers wt = mapper.readValue(webPage, WrapperTrailers.class);

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
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
    }

    /**
     * This method is used to retrieve a list of the available translations for
     * a specific movie.
     *
     * @param movieId
     * @return
     */
    public List<Translation> getMovieTranslations(int movieId) throws MovieDbException {

        URL url = tmdbMovieTranslations.getIdUrl(movieId);
        String webPage = WebBrowser.request(url);

        try {
            WrapperTranslations wt = mapper.readValue(webPage, WrapperTranslations.class);
            return wt.getTranslations();
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie tranlations: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
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
    public CollectionInfo getCollectionInfo(int movieId, String language) throws MovieDbException {

        URL url = tmdbCollectionInfo.getIdUrl(movieId);
        String webPage = WebBrowser.request(url);

        try {
            return mapper.readValue(webPage, CollectionInfo.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie tranlations: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
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
    public URL createImageUrl(String imagePath, String requiredSize) throws MovieDbException {
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
            return (new URL(sb.toString()));
        } catch (MalformedURLException ex) {
            LOGGER.warn("Failed to create image URL: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.INVALID_URL, returnUrl.toString());
        }
    }

    /**
     * This is a good starting point to start finding people on TMDb. The idea
     * is to be a quick and light method so you can iterate through people
     * quickly. TODO: Fix allResults
     */
    public List<Person> searchPeople(String personName, boolean allResults) throws MovieDbException {

        URL url = tmdbSearchPeople.getQueryUrl(personName, "", 1);
        String webPage = WebBrowser.request(url);

        try {
            WrapperPerson resultList = mapper.readValue(webPage, WrapperPerson.class);
            return resultList.getResults();
        } catch (IOException ex) {
            LOGGER.warn("Failed to find person: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
    }

    /**
     * This method is used to retrieve all of the basic person information. It
     * will return the single highest rated profile image.
     *
     * @param personId
     * @return
     */
    public Person getPersonInfo(int personId) throws MovieDbException {

        URL url = tmdbPersonInfo.getIdUrl(personId);
        String webPage = WebBrowser.request(url);

        try {
            return mapper.readValue(webPage, Person.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get movie info: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
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
    public List<PersonCredit> getPersonCredits(int personId) throws MovieDbException {

        List<PersonCredit> personCredits = new ArrayList<PersonCredit>();

        URL url = tmdbPersonCredits.getIdUrl(personId);
        String webPage = WebBrowser.request(url);

        try {
            WrapperPersonCredits pc = mapper.readValue(webPage, WrapperPersonCredits.class);

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
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
    }

    /**
     * This method is used to retrieve all of the profile images for a person.
     *
     * @param personId
     * @return
     */
    public List<Artwork> getPersonImages(int personId) throws MovieDbException {

        List<Artwork> personImages = new ArrayList<Artwork>();

        URL url = tmdbPersonImages.getIdUrl(personId);
        String webPage = WebBrowser.request(url);

        try {
            WrapperImages images = mapper.readValue(webPage, WrapperImages.class);

            // Update the image type
            for (Artwork artwork : images.getProfiles()) {
                artwork.setArtworkType(ArtworkType.PROFILE);
                personImages.add(artwork);
            }
            return personImages;
        } catch (IOException ex) {
            LOGGER.warn("Failed to get person images: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
        }
    }

    /**
     * This method is used to retrieve the newest movie that was added to TMDb.
     *
     * @return
     */
    public MovieDb getLatestMovie() throws MovieDbException {

        URL url = tmdbLatestMovie.getIdUrl("");
        String webPage = WebBrowser.request(url);

        try {
            return mapper.readValue(webPage, MovieDb.class);
        } catch (IOException ex) {
            LOGGER.warn("Failed to get latest movie: " + ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.MAPPING_FAILED, webPage);
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

        if (StringUtils.isNotBlank(year) && !year.equalsIgnoreCase("UNKNOWN") && StringUtils.isNotBlank(moviedb.getReleaseDate())) {
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

        // Compare without year
        if (moviedb.getOriginalTitle().equalsIgnoreCase(title)) {
            return true;
        }

        if (moviedb.getTitle().equalsIgnoreCase(title)) {
            return true;
        }

        return false;
    }
}
