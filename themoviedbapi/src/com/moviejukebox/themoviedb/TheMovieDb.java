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
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;

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
    private static String defaultLanguage = "en";
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
    public MovieDB moviedbSearch(String movieTitle, String language) {
        MovieDB movie = null;
        // If the title is null, then exit
        if (movieTitle == null || movieTitle.equals(""))
            return movie;

        Document doc = null;
        language = validateLanguage(language);

        try {
            String searchUrl = buildSearchUrl("Movie.search", URLEncoder.encode(movieTitle, "UTF-8"), language);
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            movie = DOMParser.parseMovieInfo(doc);

        } catch (Exception error) {
            logger.severe("TheMovieDb Error: " + error.getMessage());
        }
        return movie;
    }

    /**
     * Searches the database using the IMDb reference
     * 
     * @param imdbID    IMDb reference, must include the "tt" at the start
     * @param language  The two digit language code. E.g. en=English            
     * @return          A movie bean with the data extracted
     */
    public MovieDB moviedbImdbLookup(String imdbID, String language) {
        MovieDB movie = null;

        // If the imdbID is null, then exit
        if (imdbID == null || imdbID.equals(""))
            return movie;
        
        Document doc = null;
        language = validateLanguage(language);

        try {
            String searchUrl = buildSearchUrl("Movie.imdbLookup", imdbID, language);

            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            movie = DOMParser.parseMovieInfo(doc);
        
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
        if (tmdbID == null || tmdbID.equals("") || tmdbID.equalsIgnoreCase("UNKNOWN"))
            return movie;
        
        Document doc = null;
        language = validateLanguage(language);
        
        try {
            String searchUrl = buildSearchUrl("Movie.getInfo", tmdbID, language);
            
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            movie = DOMParser.parseMovieInfo(doc);
            
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
        if (searchTerm == null || searchTerm.equals("") || searchTerm.equalsIgnoreCase("UNKNOWN"))
            return movie;
        
        Document doc = null;
        language = validateLanguage(language);
        
        try {
            String searchUrl = buildSearchUrl("Movie.getImages", searchTerm, language);
            
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            movie = DOMParser.parseMovieInfo(doc);

        } catch (Exception error) {
            logger.severe("TheMovieDb Error: " + error.getMessage());
        }

        return movie;
    }
    
    /**
     * This function will check the passed language against a list of known themoviedb.org languages
     * Currently the only available language is English "en" and so that is what this function returns
     * @param language
     * @return
     */
    private String validateLanguage(String language) {
        if (language == null) {
            language = defaultLanguage;
        } else {
            language = defaultLanguage;
        }
        return language;
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
        if (personName == null || personName.equals("")) {
            return person;
        }

        Document doc = null;
        language = validateLanguage(language);
        
        try {
            String searchUrl = buildSearchUrl("Person.search", personName, language);
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
        if (personID == null || personID.equals("")) {
            return person;
        }
        
        Document doc = null;
        language = validateLanguage(language);

        try {
            String searchUrl = buildSearchUrl("Person.getInfo", personID, language);
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
        if (personID == null || personID.equals("")) {
            return person;
        }

        Document doc = null;
        language = validateLanguage(language);

        try {
            String searchUrl = buildSearchUrl("Person.getVersion", personID, language);
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
            person = DOMParser.parsePersonGetVersion(doc);
        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
        }
        
        return person;
    }
}
