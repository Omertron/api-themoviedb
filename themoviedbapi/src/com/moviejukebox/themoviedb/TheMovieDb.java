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

package com.moviejukebox.themoviedb;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.moviejukebox.themoviedb.model.MovieDB;
import com.moviejukebox.themoviedb.model.Person;
import com.moviejukebox.themoviedb.tools.DOMHelper;
import com.moviejukebox.themoviedb.tools.DOMParser;
import com.moviejukebox.themoviedb.tools.LogFormatter;

/**
 * This is the main class for the API to connect to TheMovieDb.org The implementation is for v2.1 
 * of the API as detailed here http://api.themoviedb.org/2.1/docs/
 * 
 * @author Stuart.Boston
 * @version 1.3
 */
public class TheMovieDb {

    private String apiKey;
    private static String apiSite = "http://api.themoviedb.org/2.1/";
    private static String defaultLanguage = "en-US";
    private static Logger logger;
    private static LogFormatter tmdbFormatter = new LogFormatter();
    private static ConsoleHandler tmdbConsoleHandler = new ConsoleHandler();

    public TheMovieDb(String apiKey) {
        setLogger(Logger.getLogger("TheMovieDB"));
        setApiKey(apiKey);
    }
    
    public TheMovieDb(String apiKey, Logger logger) {
        setLogger(logger);
        setApiKey(apiKey);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        TheMovieDb.logger = logger;
        tmdbConsoleHandler.setFormatter(tmdbFormatter);
        tmdbConsoleHandler.setLevel(Level.FINE);
        logger.addHandler(tmdbConsoleHandler);
        logger.setUseParentHandlers(true);
        logger.setLevel(Level.ALL);
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
        tmdbFormatter.addApiKey(apiKey);
    }

    /**
     * Build the search URL from the search prefix and movie title.
     * This will change between v2.0 and v2.1 of the API
     * 
     * @param prefix        The search prefix before the movie title
     * @param language      The two digit language code. E.g. en=English            
     * @param searchTerm    The search key to use, e.g. movie title or IMDb ID
     * @return              The search URL
     */
    private String buildSearchUrl(String prefix, String searchTerm, String language) {
        String searchUrl = apiSite + prefix + "/" + language + "/xml/" + apiKey + "/" + searchTerm;
        logger.finest("Search URL: " + searchUrl);
        return searchUrl;
    }

    /**
     * Searches the database using the movie title passed
     * 
     * @param movieTitle    The title to search for
     * @param language      The two digit language code. E.g. en=English
     * @return              A movie bean with the data extracted
     */
    public List<MovieDB> moviedbSearch(String movieTitle, String language) {
        MovieDB movie = null;
        List<MovieDB> movieList = new ArrayList<MovieDB>();
        
        // If the title is null, then exit
        if (!isValidString(movieTitle)) {
            return movieList;
        }

        Document doc = null;

        try {
            String searchUrl = buildSearchUrl("Movie.search", URLEncoder.encode(movieTitle, "UTF-8"), validateLanguage(language));
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            NodeList nlMovies = doc.getElementsByTagName("movie");
            if (nlMovies == null) {
                return movieList;
            }
            
            for (int loop = 0; loop < nlMovies.getLength(); loop++) {
                Node nMovie = nlMovies.item(loop);
                if (nMovie.getNodeType() == Node.ELEMENT_NODE) {
                    Element eMovie = (Element) nMovie;
                    movie = DOMParser.parseMovieInfo(eMovie);
                    if (movie != null) {
                        movieList.add(movie);
                    }
                }
            }
        } catch (Exception error) {
            logger.severe("TheMovieDb Error: " + error.getMessage());
        }
        return movieList;
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
        
        Document doc = null;

        try {
            String searchUrl = buildSearchUrl("Movie.imdbLookup", imdbID, validateLanguage(language));

            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            NodeList nlMovies = doc.getElementsByTagName("movie");
            if (nlMovies == null) {
                return movie;
            }

            for (int loop = 0; loop < nlMovies.getLength(); loop++) {
                Node nMovie = nlMovies.item(loop);
                if (nMovie.getNodeType() == Node.ELEMENT_NODE) {
                    Element eMovie = (Element) nMovie;
                    movie = DOMParser.parseMovieInfo(eMovie);
                }
            }
        } catch (Exception error) {
            logger.severe("TheMovieDb Error: " + error.getMessage());
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
        // If the tmdbID is null, then exit
        if (!isValidString(tmdbID))
            return movie;
        
        Document doc = null;
        
        try {
            String searchUrl = buildSearchUrl("Movie.getInfo", tmdbID, validateLanguage(language));
            
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            NodeList nlMovies = doc.getElementsByTagName("movie");
            if (nlMovies == null) {
                return movie;
            }

            for (int loop = 0; loop < nlMovies.getLength(); loop++) {
                Node nMovie = nlMovies.item(loop);
                if (nMovie.getNodeType() == Node.ELEMENT_NODE) {
                    Element eMovie = (Element) nMovie;
                    movie = DOMParser.parseMovieInfo(eMovie);
                }
            }
        } catch (Exception error) {
            logger.severe("TheMovieDb Error: " + error.getMessage());
        }
        return movie;
    }

    public MovieDB moviedbGetImages(String searchTerm, String language) {
        MovieDB movie = null;
        movie = moviedbGetInfo(searchTerm, movie, language);
        return movie;
    }
    
    /**
     * Get all the image information from TheMovieDb.
     * @param searchTerm    Can be either the IMDb ID or TMDb ID
     * @param movie
     * @param language
     * @return
     */
    public MovieDB moviedbGetImages(String searchTerm, MovieDB movie, String language) {
        // If the searchTerm is null, then exit
        if (isValidString(searchTerm))
            return movie;
        
        Document doc = null;
        
        try {
            String searchUrl = buildSearchUrl("Movie.getImages", searchTerm, validateLanguage(language));
            
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            NodeList nlMovies = doc.getElementsByTagName("movie");
            if (nlMovies == null) {
                return movie;
            }

            for (int loop = 0; loop < nlMovies.getLength(); loop++) {
                Node nMovie = nlMovies.item(loop);
                if (nMovie.getNodeType() == Node.ELEMENT_NODE) {
                    Element eMovie = (Element) nMovie;
                    movie = DOMParser.parseMovieInfo(eMovie);
                }
            }

        } catch (Exception error) {
            logger.severe("TheMovieDb Error: " + error.getMessage());
        }

        return movie;
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
        Person person = new Person();
        if (!isValidString(personName)) {
            return person;
        }

        Document doc = null;
        
        try {
            String searchUrl = buildSearchUrl("Person.search", personName, validateLanguage(language));
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            person = DOMParser.parsePersonInfo(doc);
        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
        }
        
        return person;
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
        Person person = new Person();
        if (!isValidString(personID)) {
            return person;
        }
        
        Document doc = null;

        try {
            String searchUrl = buildSearchUrl("Person.getInfo", personID, validateLanguage(language));
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            person = DOMParser.parsePersonInfo(doc);
        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
        }
        
        return person;
    }
    
    /**
     * The Person.getVersion method is used to retrieve the last modified time along with 
     * the current version number of the called object(s). This is useful if you've already 
     * called the object sometime in the past and simply want to do a quick check for updates.
     * 
     * @param personID
     * @param language
     * @return
     */
    public Person personGetVersion(String personID, String language) {
        Person person = new Person();
        if (!isValidString(personID)) {
            return person;
        }

        Document doc = null;

        try {
            String searchUrl = buildSearchUrl("Person.getVersion", personID, validateLanguage(language));
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            person = DOMParser.parsePersonGetVersion(doc);
        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
        }
        
        return person;
    }
    
    /**
     * This function will check the passed language against a list of known themoviedb.org languages
     * Currently the only available language is English "en" and so that is what this function returns
     * @param language
     * @return
     */
    private String validateLanguage(String language) {
        if (!isValidString(language)) {
            return defaultLanguage;
        } else {
            /*
             * Rather than check every conceivable language, we'll just validate the format of the language
             * The language should either be 2 or 5 characters "xx" or "xx-YY"
             * http://api.themoviedb.org/2.1/language-tags
             */
            if (language.length() == 2) {
                return language.toLowerCase();
            } else if (language.length() == 5) {
                return language.substring(1, 2).toLowerCase() + "-" + language.substring(4, 5).toUpperCase();
            } else {
                // The format of the language is wrong, so just cut the first two characters and use that
                // The site will take care of invalid languages
                return language.substring(1, 2).toLowerCase();
            }
        }
    }
    
    /**
     * Check the string passed to see if it contains a value.
     * @param testString The string to test
     * @return False if the string is empty, null or UNKNOWN, True otherwise
     */
    public static boolean isValidString(String testString) {
        if (testString == null) {
            return false;
        }
        
        if (testString.equalsIgnoreCase(MovieDB.UNKNOWN)) {
            return false;
        }
        
        if (testString.trim().equals("")) {
            return false;
        }
        
        return true;
    }

    /**
     * Search a list of movies and return the one that matches the title & year
     * @param movieList The list of movies to search
     * @param title     The title to search for
     * @param year      The year of the title to search for
     * @return          The matching movie
     */
    public static MovieDB findMovie(Collection<MovieDB> movieList, String title, String year) {
        if (movieList == null || movieList.isEmpty()) {
            return null;
        }
        
        System.out.println("Looking for: " + title + " - Year: " + year);
        
        for (MovieDB moviedb : movieList) {
            if (compareMovies(moviedb, title, year)) {
                System.out.println("Matched: " + moviedb.getTitle());
                return moviedb;
            } else {
                System.out.println("Not Matched: " + moviedb.getTitle() + " - " + moviedb.getReleaseDate());
            }
        }
        
        return null;
    }
    
    /**
     * Compare the MovieDB object with a title & year
     * @param moviedb   The moviedb object to compare too
     * @param title     The title of the movie to compare
     * @param year      The year of the movie to compare
     * @return          True if there is a match, False otherwise.
     */
    public static boolean compareMovies(MovieDB moviedb, String title, String year) {
        if (!isValidString(title)) {
            return false;
        }

        if (isValidString(year)) {
            if (isValidString(moviedb.getReleaseDate())) {
                // Compare with year
                String movieYear = moviedb.getReleaseDate().substring(0, 4);
                logger.fine("Comparing against: " + moviedb.getTitle() + " - " + moviedb.getReleaseDate() + " - " + movieYear);
                if (moviedb.getTitle().equalsIgnoreCase(title) && movieYear.equals(year)) {
                    return true;
                }
            }
        } else {
            // Compare without year
            if (moviedb.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }
}
