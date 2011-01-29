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
package com.moviejukebox.themoviedb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.moviejukebox.themoviedb.model.Category;
import com.moviejukebox.themoviedb.model.Language;
import com.moviejukebox.themoviedb.model.MovieDB;
import com.moviejukebox.themoviedb.model.Person;
import com.moviejukebox.themoviedb.tools.MovieDbParser;
import com.moviejukebox.themoviedb.tools.LogFormatter;
import com.moviejukebox.themoviedb.tools.WebBrowser;

/**
 * This is the main class for the API to connect to TheMovieDb.org.
 * The implementation is for v2.1 of the API as detailed here:
 * http://api.themoviedb.org/2.1
 * 
 * @author Stuart.Boston
 * @version 1.3
 */
public class TheMovieDb {
    private String apiKey;
    private static Logger logger = null;
    private static LogFormatter tmdbFormatter = new LogFormatter();
    private static ConsoleHandler tmdbConsoleHandler = new ConsoleHandler();
    private static final String apiSite = "http://api.themoviedb.org/2.1/";
    private static final String defaultLanguage = "en-US";
    
    /**
     * Compare the MovieDB object with a title & year
     * @param moviedb   The moviedb object to compare too
     * @param title     The title of the movie to compare
     * @param year      The year of the movie to compare
     * @return          True if there is a match, False otherwise.
     */
    public static boolean compareMovies(MovieDB moviedb, String title, String year) {
        if ((moviedb == null) || (!isValidString(title))) {
            return false;
        }

        if (isValidString(year)) {
            if (isValidString(moviedb.getReleaseDate())) {
                // Compare with year
                String movieYear = moviedb.getReleaseDate().substring(0, 4);
                if (movieYear.equals(year)) {
                    if (moviedb.getOriginalName().equalsIgnoreCase(title)) {
                        return true;
                    }

                    if (moviedb.getTitle().equalsIgnoreCase(title)) {
                        return true;
                    }

                    // Try matching the alternative name too
                    if (moviedb.getAlternativeName().equalsIgnoreCase(title)) {
                        return true;
                    }
                }
            }
        } else {
            // Compare without year
            if (moviedb.getOriginalName().equalsIgnoreCase(title)) {
                return true;
            }

            if (moviedb.getTitle().equalsIgnoreCase(title)) {
                return true;
            }

            // Try matching the alternative name too
            if (moviedb.getAlternativeName().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Search a list of movies and return the one that matches the title & year
     * @param movieList The list of movies to search
     * @param title     The title to search for
     * @param year      The year of the title to search for
     * @return          The matching movie
     */
    public static MovieDB findMovie(Collection<MovieDB> movieList, String title, String year) {
        if ((movieList == null) || (movieList.isEmpty()) || (!isValidString(title))) {
            return null;
        }

        for (MovieDB moviedb : movieList) {
            if (compareMovies(moviedb, title, year)) {
                return moviedb;
            }
        }

        return null;
    }
    
    /**
     * Check the string passed to see if it contains a value.
     * @param testString The string to test
     * @return False if the string is empty, null or UNKNOWN, True otherwise
     */
    private static boolean isValidString(String testString) {
        if ((testString == null)
                || (testString.trim().equals(""))
                || (testString.equalsIgnoreCase(MovieDB.UNKNOWN))) {
            return false;
        }
        return true;
    }
    
    /*
     * API Methods
     * http://api.themoviedb.org/2.1
     * Note: This is currently a read-only interface and as such, no write methods exist.
     */
    
    /*
     * Media
     */
    @SuppressWarnings("unused")
    private static final String MEDIA_GET_INFO      = "Media.getInfo";
    
    /*
     * Movies
     */
    private static final String MOVIE_BROWSE        = "Movie.browse";
    private static final String MOVIE_GET_IMAGES    = "Movie.getImages";
    private static final String MOVIE_GET_INFO      = "Movie.getInfo";
    private static final String MOVIE_GET_LATEST    = "Movie.getLatest";
    private static final String MOVIE_GET_TRANSLATIONS = "Movie.getTranslations";
    private static final String MOVIE_GET_VERSION   = "Movie.getVersion";
    private static final String MOVIE_IMDB_LOOKUP   = "Movie.imdbLookup";
    private static final String MOVIE_SEARCH        = "Movie.search";
    
    /*
     * People
     */
    private static final String PERSON_GET_INFO     = "Person.getInfo";
    private static final String PERSON_GET_LATEST   = "Person.getLatest";
    private static final String PERSON_GET_VERSION  = "Person.getVersion";
    private static final String PERSON_SEARCH       = "Person.search";

    /*
     * Misc
     */
    private static final String GENRES_GET_LIST     = "Genres.getList";

    public static Logger getLogger() {
        return logger;
    }

    /**
     * Constructor with default logger.
     * @param apiKey
     */
    public TheMovieDb(String apiKey) {
        setLogger(Logger.getLogger("TheMovieDB"));
        if (!isValidString(apiKey)) {
            logger.severe("TheMovieDb was initialized with a wrong API key!");
        }
        setApiKey(apiKey);
    }

    public TheMovieDb(String apiKey, Logger logger) {
        setLogger(logger);
        if (!isValidString(apiKey)) {
            logger.severe("TheMovieDb was initialized with a wrong API key!");
        }
        setApiKey(apiKey);
    }

    /**
     * Build comma separated ids for Movie.getLatest and Movie.getVersion.
     * @param ids a List of ids
     * @return
     */
    private String buildIds(List<String> ids) {
        String s = "";
        for (int i = 0; i < ids.size(); i++) {
            if (i == 0) {
                s += ids.get(i);
                continue;
            }
            s += "," + ids.get(i);
        }
        return s;
    }

    /**
     * Build the URL that is used to get the XML from TMDb.
     *
     * @param prefix        The search prefix before the movie title
     * @param language      The two digit language code. E.g. en=English
     * @param searchTerm    The search key to use, e.g. movie title or IMDb ID
     * @return              The search URL
     */
    private String buildUrl(String prefix, String searchTerm, String language) {
        String url = apiSite + prefix + "/" + language + "/xml/" + apiKey;

        if (!isValidString(searchTerm)) {
            return url;
        }

        String encodedSearchTerm;

        try {
            encodedSearchTerm = URLEncoder.encode(searchTerm, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            encodedSearchTerm = searchTerm;
        }

        if (prefix.equals(MOVIE_BROWSE)) {
            url += "?";
        } else {
            url += "/";
        }

        url += encodedSearchTerm;

        logger.finest("Search URL: " + url);
        return url;
    }

    /**
     * Return the API key.
     * @return
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Retrieve a list of valid genres within TMDb.
     * @param language the two digit language code. E.g. en=English
     * @return
     */
    public List<Category> getCategories(String language) {
        return MovieDbParser.parseCategories(this.buildUrl(GENRES_GET_LIST, "", language));
    }

    /**
     * Return the TMDb default language: en-US.
     * @return
     */
    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public List<Language> getTranslations(String movieId, String language) {
        return  MovieDbParser.parseLanguages(this.buildUrl(MOVIE_GET_TRANSLATIONS, movieId, language));
    }

    /**
     * Browse the database using optional parameters.
     * http://api.themoviedb.org/2.1/methods/Movie.browse
     *
     * @param orderBy either <code>rating</code>,
     *                <code>release</code> or <code>title</code>
     * @param order how results are ordered. Either <code>asc</code> or
     *              <code>desc</code>
     * @param parameters a Map of optional parameters. See the complete list
     *                   in the url above.
     * @param language the two digit language code. E.g. en=English
     * @return a list of MovieDB objects
     */
    public List<MovieDB> moviedbBrowse(String orderBy, String order,
            Map<String, String> parameters, String language) {

        List<MovieDB> movies = new ArrayList<MovieDB>();
        if (!isValidString(orderBy) || (!isValidString(order))
                || (parameters == null)) {
            return movies;
        }

        List<String> validParameters = new ArrayList<String>();
        validParameters.add("per_page");
        validParameters.add("page");
        validParameters.add("query");
        validParameters.add("min_votes");
        validParameters.add("rating_min");
        validParameters.add("rating_max");
        validParameters.add("genres");
        validParameters.add("genres_selector");
        validParameters.add("release_min");
        validParameters.add("release_max");
        validParameters.add("year");
        validParameters.add("certifications");
        validParameters.add("companies");
        validParameters.add("countries");

        String url = "order_by=" + orderBy + "&order=" + order;
        if(!parameters.isEmpty()) {
            for (String key : validParameters) {
                if (parameters.containsKey(key)) {
                    url += "&" + key + "=" + parameters.get(key);
                }
            }
        }

        String searchUrl = buildUrl(MOVIE_BROWSE, url, language);
        return MovieDbParser.parseMovies(searchUrl);

    }

    /**
     * Browse the database using the default parameters.
     * http://api.themoviedb.org/2.1/methods/Movie.browse
     *
     * @param orderBy either <code>rating</code>,
     *                <code>release</code> or <code>title</code>
     * @param order how results are ordered. Either <code>asc</code> or
     *              <code>desc</code>
     * @param language the two digit language code. E.g. en=English
     * @return a list of MovieDB objects
     */
    public List<MovieDB> moviedbBrowse(String orderBy, String order, String language) {
        return this.moviedbBrowse(orderBy, order, new HashMap<String, String>(), language);
    }

    /**
     * The Movie.getImages method is used to retrieve all of the backdrops and
     * posters for a particular movie. This is useful to scan for updates, or
     * new images if that's all you're after.
     * @param movieId the TMDb or IMDB ID (starting with tt) of the movie you
     *                are searching for.
     * @param movie a MovieDB object
     * @param language the two digit language code. E.g. en=English
     * @return
     */
    public MovieDB moviedbGetImages(String movieId, MovieDB movie, String language) {
        // If the searchTerm is null, then exit
        if (!isValidString(movieId)) {
            return movie;
        }

        String searchUrl = buildUrl(MOVIE_GET_IMAGES, movieId, language);
        return MovieDbParser.parseMovie(searchUrl);
    }

    /**
     * The Movie.getImages method is used to retrieve all of the backdrops and
     * posters for a particular movie. This is useful to scan for updates, or
     * new images if that's all you're after.
     * @param movieId the TMDb or IMDB ID (starting with tt) of the movie you
     *                are searching for.
     * @param language the two digit language code. E.g. en=English
     * @return
     */
    public MovieDB moviedbGetImages(String movieId, String language) {
        return moviedbGetImages(movieId, new MovieDB(), language);
    }

    /**
     * Gets all the information for a given TheMovieDb ID
     * 
     * @param movie
     *            An existing MovieDB object to populate with the data
     * @param tmdbID
     *            The Movie Db ID for the movie to get information for
     * @param language
     *            The two digit language code. E.g. en=English            
     * @return A movie bean with all of the information
     */
    public MovieDB moviedbGetInfo(String tmdbID, MovieDB movie, String language) {
        // If the tmdbID is invalid, then exit
        if (!isValidString(tmdbID)) {
            return movie;
        }

        String searchUrl = buildUrl(MOVIE_GET_INFO, tmdbID, language);
        movie = MovieDbParser.parseMovie(searchUrl);

        if (movie == null && !language.equalsIgnoreCase(defaultLanguage)) {
            logger.fine("Trying to get the '" + defaultLanguage + "' version");
            searchUrl = buildUrl(MOVIE_GET_INFO, tmdbID, defaultLanguage);
            movie = MovieDbParser.parseMovie(searchUrl);
        }

        return movie;
    }

    /**
     * Passes a null MovieDB object to the full function
     * 
     * @param tmdbID    TheMovieDB ID of the movie to get the information for
     * @param language  The two digit language code. E.g. en=English            
     * @return          A movie bean with all of the information
     */
    public MovieDB moviedbGetInfo(String tmdbID, String language) {
        MovieDB movie = null;
        movie = moviedbGetInfo(tmdbID, movie, language);
        return movie;
    }

    /**
     * The Movie.getLatest method is a simple method. It returns the ID of the
     * last movie created in the database. This is useful if you are scanning
     * the database and want to know which id to stop at. <br/>
     * The MovieDB object returned only has its title, TMDb id, IMDB id,
     * version and last modified date initialized.
     * @param language the two digit language code. E.g. en=English
     * @return
     */
    public MovieDB moviedbGetLatest(String language) {
        return MovieDbParser.parseLatestMovie(buildUrl(MOVIE_GET_LATEST, "", language));
    }

    /**
     * The Movie.getVersion method is used to retrieve the last modified time
     * along with the current version number of the called object(s). This is
     * useful if you've already called the object sometime in the past and
     * simply want to do a quick check for updates. <br/>
     * The MovieDB object returned only has its title, TMDb id, IMDB id,
     * version and last modified date initialized.
     * @param movieIds the ID of the TMDb movie you are looking for.
     *                 This field supports an integer value (TMDb movie id) an
     *                 IMDB ID or a combination of both.
     * @param language the two digit language code. E.g. en=English
     * @return
     */
    public List<MovieDB> moviedbGetVersion(List<String> movieIds, String language) {
        List<MovieDB> movies = new ArrayList<MovieDB>();

        if ((movieIds == null) || movieIds.isEmpty()) {
            logger.warning("There are no Movie ids!");
            return movies;
        }

        String url = buildUrl(MOVIE_GET_VERSION, this.buildIds(movieIds), language);
        return MovieDbParser.parseMovieGetVersion(url);

    }

    /**
     * The Movie.getVersion method is used to retrieve the last modified time
     * along with the current version number of the called object(s). This is
     * useful if you've already called the object sometime in the past and
     * simply want to do a quick check for updates. <br/>
     * The MovieDB object returned only has its title, TMDb id, IMDB id,
     * version and last modified date initialized.
     * @param movieId the TMDb ID or IMDB ID of the movie
     * @param language the two digit language code. E.g. en=English
     * @return
     */
    public MovieDB moviedbGetVersion(String movieId, String language) {
        List<MovieDB> movies = this.moviedbGetVersion(Arrays.asList(movieId), language);
        if (movies.isEmpty()) {
            return new MovieDB();
        }
        return movies.get(0);
    }

    /**
     * Searches the database using the IMDb reference
     * 
     * @param imdbID    IMDb reference, must include the "tt" at the start
     * @param language  The two digit language code. E.g. en=English            
     * @return          A movie bean with the data extracted
     */
    public MovieDB moviedbImdbLookup(String imdbID, String language) {
        MovieDB movie = new MovieDB();

        // If the imdbID is null, then exit
        if (!isValidString(imdbID)) {
            return movie;
        }

        String searchUrl = buildUrl(MOVIE_IMDB_LOOKUP, imdbID, language);
        return MovieDbParser.parseMovie(searchUrl);
    }

    /**
     * Searches the database using the movie title passed
     * 
     * @param movieTitle    The title to search for
     * @param language      The two digit language code. E.g. en=English
     * @return              A movie bean with the data extracted
     */
    public List<MovieDB> moviedbSearch(String movieTitle, String language) {
        // If the title is null, then exit
        if (!isValidString(movieTitle)) {
            return new ArrayList<MovieDB>();
        }

        String searchUrl = buildUrl(MOVIE_SEARCH, movieTitle, language);
        return MovieDbParser.parseMovies(searchUrl);
    }

    /**
     * The Person.getInfo method is used to retrieve the full filmography, known movies, 
     * images and things like birthplace for a specific person in the TMDb database.
     * 
     * @param personID
     * @param language
     * @return
     */
    public Person personGetInfo(String personID, String language) {
        if (!isValidString(personID)) {
            return new Person();
        }

        String searchUrl = buildUrl(PERSON_GET_INFO, personID, language);
        return MovieDbParser.parsePersonInfo(searchUrl);
    }

    /**
     * The Person.getLatest method is a simple method. It returns the ID of the
     * last person created in the db. This is useful if you are scanning the
     * database and want to know which id to stop at.
     * @param language the two digit language code. E.g. en=English
     * @return
     */
    public Person personGetLatest(String language) {
        return MovieDbParser.parseLatestPerson(buildUrl(PERSON_GET_LATEST, "", language));
    }

    /**
     * The Person.getVersion method is used to retrieve the last modified time
     * along with the current version number of the called object(s). This is
     * useful if you've already called the object sometime in the past and
     * simply want to do a quick check for updates.
     * @param personIDs one or multiple Person TMDb ids
     * @param language the two digit language code. E.g. en=English
     * @return
     */
    public List<Person> personGetVersion(List<String> personIDs, String language) {
        if ((personIDs == null) || (personIDs.isEmpty())) {
            logger.warning("There are no Person ids!");
            return new ArrayList<Person>();
        }

        String searchUrl = buildUrl(PERSON_GET_VERSION, this.buildIds(personIDs), language);
        return MovieDbParser.parsePersonGetVersion(searchUrl);
    }

    /**
     * The Person.getVersion method is used to retrieve the last modified time 
     * along with the current version number of the called object(s). This is
     * useful if you've already called the object sometime in the past and
     * simply want to do a quick check for updates.
     * 
     * @param personID a Person TMDb id
     * @param language the two digit language code. E.g. en=English
     * @return
     */
    public Person personGetVersion(String personID, String language) {
        Person person = new Person();
        if (!isValidString(personID)) {
            return person;
        }

        List<Person> people = this.personGetVersion(Arrays.asList(personID), language);
        if (people.isEmpty()) {
            return person;
        }

        return people.get(0);
    }

    /**
     * The Person.search method is used to search for an actor, actress or production member.
     * http://api.themoviedb.org/2.1/methods/Person.search
     * 
     * @param personName
     * @param language
     * @return
     */
    public Person personSearch(String personName, String language) {
        if (!isValidString(personName)) {
            return new Person();
        }

        String searchUrl = buildUrl(PERSON_SEARCH, personName, language);
        return MovieDbParser.parsePersonInfo(searchUrl);
    }

    /**
     * Set the TMDb API key.
     * @param apiKey a valid TMDb API key.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
        tmdbFormatter.addApiKey(apiKey);
    }

    public void setLogger(Logger logger) {
        if (logger == null) {
            return;
        }

        TheMovieDb.logger = logger;
        tmdbConsoleHandler.setFormatter(tmdbFormatter);
        tmdbConsoleHandler.setLevel(Level.FINE);
        logger.addHandler(tmdbConsoleHandler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
    }

    /**
     * Set proxy parameters.
     * @param host proxy host URL
     * @param port proxy port
     * @param username proxy username
     * @param password proxy password
     */
    public void setProxy(String host, String port, String username, String password) {
        WebBrowser.setProxyHost(host);
        WebBrowser.setProxyPort(port);
        WebBrowser.setProxyUsername(username);
        WebBrowser.setProxyPassword(password);
    }

    /**
     * Set web browser timeout.
     * @param webTimeoutConnect
     * @param webTimeoutRead
     */
    public void setTimeout(int webTimeoutConnect, int webTimeoutRead) {
        WebBrowser.setWebTimeoutConnect(webTimeoutConnect);
        WebBrowser.setWebTimeoutRead(webTimeoutRead);
    }

}
