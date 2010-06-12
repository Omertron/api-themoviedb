/*
 *      Copyright (c) 2004-2009 YAMJ Members
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
import java.util.Iterator;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.moviejukebox.themoviedb.model.*;
import com.moviejukebox.themoviedb.tools.*;

/**
 * This is the main class for the API to connect to TheMovieDb.org The implementation is for v2.1 of the API as detailed here
 * http://api.themoviedb.org/2.1/docs/
 * 
 * @author Stuart.Boston
 * @version 1.1
 */
public class TheMovieDb {

    private String apiKey;
    private static String apiSite = "http://api.themoviedb.org/2.1/";
    private static String defaultLanguage = "en";
    private static Logger logger;

    public TheMovieDb(String apiKey) {        
        logger = Logger.getLogger("TheMovieDB");
        LogFormatter mjbFormatter = new LogFormatter();
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(mjbFormatter);
        ch.setLevel(Level.FINE);
        logger.addHandler(ch);
        logger.setUseParentHandlers(true);
        logger.setLevel(Level.ALL);
        
        this.apiKey = apiKey;
        mjbFormatter.addApiKey(apiKey);
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
        XMLEventReader xmlReader = null;
        MovieDB movie = null;
        
        language = validateLanguage(language);
        
        // If the title is null, then exit
        if (movieTitle == null || movieTitle.equals(""))
            return movie;

        try {
            String searchUrl = buildSearchUrl("Movie.search", URLEncoder.encode(movieTitle, "UTF-8"), language);
            xmlReader = XMLHelper.getEventReader(searchUrl);
            movie = parseMovieInfo(xmlReader);
        } catch (Exception error) {
            System.err.println("ERROR: TheMovieDb API -> " + error.getMessage());
        } finally {
            XMLHelper.closeEventReader(xmlReader);
        }
        return movie;
    }

    /**
     * Searches the database using the IMDd reference
     * 
     * @param imdbID    IMDb reference, must include the "tt" at the start
     * @param language  The two digit language code. E.g. en=English            
     * @return          A movie bean with the data extracted
     */
    public MovieDB moviedbImdbLookup(String imdbID, String language) {
        XMLEventReader xmlReader = null;
        MovieDB movie = null;
        
        language = validateLanguage(language);

        // If the imdbID is null, then exit
        if (imdbID == null || imdbID.equals(""))
            return movie;
        
        try {
            String searchUrl = buildSearchUrl("Movie.imdbLookup", imdbID, language);
            xmlReader = XMLHelper.getEventReader(searchUrl);
            movie = parseMovieInfo(xmlReader);
        } catch (Exception error) {
            System.err.println("ERROR: TheMovieDb API -> " + error.getMessage());
        } finally {
            XMLHelper.closeEventReader(xmlReader);
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
        XMLEventReader xmlReader = null;
        
        // If the tmdbID is null, then exit
        if (tmdbID == null || tmdbID.equals("") || tmdbID.equalsIgnoreCase("UNKNOWN"))
            return movie;
        
        language = validateLanguage(language);
        
        try {
            String searchUrl = buildSearchUrl("Movie.getImages", tmdbID, language);
            xmlReader = XMLHelper.getEventReader(searchUrl);
            movie = parseMovieInfo(xmlReader);
        } catch (Exception error) {
            System.err.println("ERROR: TheMovieDb API -> " + error.getMessage());
        } finally {
            XMLHelper.closeEventReader(xmlReader);
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
        XMLEventReader xmlReader = null;
        
        // If the searchTerm is null, then exit
        if (searchTerm == null || searchTerm.equals("") || searchTerm.equalsIgnoreCase("UNKNOWN"))
            return movie;
        
        language = validateLanguage(language);
        
        try {
            String searchUrl = buildSearchUrl("Movie.getImages", searchTerm, language);
            xmlReader = XMLHelper.getEventReader(searchUrl);
            movie = parseMovieInfo(xmlReader);
        } catch (Exception error) {
            System.err.println("ERROR: TheMovieDb API -> " + error.getMessage());
        } finally {
            XMLHelper.closeEventReader(xmlReader);
        }

        return movie;
    }
    
    /**
     * Search the XML passed and decode to a movieDB bean
     * 
     * @param xmlReader
     *            The XML stream read from TheMovieDB.org
     * @return a MovieDB bean with the data
     * @throws XMLStreamException
     */
    // TODO Waring if match is low (i.e. score != 1.0)
    @SuppressWarnings("unchecked")
    public MovieDB parseMovieInfo(XMLEventReader xmlReader) throws XMLStreamException {
        MovieDB movie = null;
        try {
        while (xmlReader.hasNext()) {
            XMLEvent event = xmlReader.nextEvent();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                
                if (startElement.getName().getLocalPart().equalsIgnoreCase("movie")) {
                    movie = new MovieDB();
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("popularity")) {
                        event = xmlReader.nextEvent();
                        movie.setPopularity(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("name")) {
                        event = xmlReader.nextEvent();
                        movie.setTitle(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("type")) {
                        event = xmlReader.nextEvent();
                        movie.setType(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("id")) {
                        event = xmlReader.nextEvent();
                        movie.setId(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("imdb_id")) {
                        event = xmlReader.nextEvent();
                        movie.setImdb(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("url")) {
                        event = xmlReader.nextEvent();
                        movie.setUrl(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("overview")) {
                        event = xmlReader.nextEvent();
                        movie.setOverview(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("rating")) {
                        event = xmlReader.nextEvent();
                        movie.setRating(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("released")) {
                        event = xmlReader.nextEvent();
                        movie.setReleaseDate(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("runtime")) {
                        event = xmlReader.nextEvent();
                        movie.setRuntime(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("budget")) {
                        event = xmlReader.nextEvent();
                        movie.setBudget(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("revenue")) {
                        event = xmlReader.nextEvent();
                        movie.setRevenue(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("homepage")) {
                        event = xmlReader.nextEvent();
                        movie.setHomepage(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("trailer")) {
                        event = xmlReader.nextEvent();
                        movie.setTrailer(event.asCharacters().getData());
                        continue;
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("categories")) {
                        event = xmlReader.nextEvent();
                        startElement = event.asStartElement();
                        
                        while (!event.isEndElement() && !event.asEndElement().getName().getLocalPart().equalsIgnoreCase("category")) {
                            Category category = new Category();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals("type"))
                                    category.setType(attribute.getValue());
                                if (attribute.getName().toString().equals("url"))
                                    category.setUrl(attribute.getValue());
                                if (attribute.getName().toString().equals("name"))
                                    category.setName(attribute.getValue());
                                
                            }
                            movie.addCategory(category);
                        }
                    }
                }
                
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("countries")) {
                        event = xmlReader.nextEvent();
                        startElement = event.asStartElement();

                        while (!event.isEndElement() && !event.asEndElement().getName().getLocalPart().equalsIgnoreCase("country")) {
                            Country country = new Country();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals("code"))
                                    country.setCode(attribute.getValue());
                                if (attribute.getName().toString().equals("url"))
                                    country.setUrl(attribute.getValue());
                                if (attribute.getName().toString().equals("name"))
                                    country.setName(attribute.getValue());
                            }
                            movie.addProductionCountry(country);
                        }
                    }
                }

                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase("cast")) {
                        event = xmlReader.nextEvent();
                        startElement = event.asStartElement();

                        while (!event.isEndElement() && !event.asEndElement().getName().getLocalPart().equalsIgnoreCase("person")) {
                            Person person = new Person();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals("url"))
                                    person.setUrl(attribute.getValue());
                                if (attribute.getName().toString().equals("name"))
                                    person.setName(attribute.getValue());
                                if (attribute.getName().toString().equals("job"))
                                    person.setJob(attribute.getValue());
                                if (attribute.getName().toString().equals("character"))
                                    person.setCharacter(attribute.getValue());
                                if (attribute.getName().toString().equals("id"))
                                    person.setId(attribute.getValue());
                            }
                            movie.addPerson(person);
                        }
                    }
                }

                /*
                 * This processes the image elements. There are two formats to deal with:
                 * Movie.imdbLookup, Movie.getInfo & Movie.search:
                 * <images>
                 *     <image type="poster" size="original" url="http://images.themoviedb.org/posters/60366/Fight_Club.jpg" id="60366"/>
                 *     <image type="backdrop" size="original" url="http://images.themoviedb.org/backdrops/56444/bscap00144.jpg" id="56444"/>
                 * </images>
                 * 
                 * Movie.getImages:
                 * <images>
                 *     <poster id="17066">
                 *         <image url="http://images.themoviedb.org/posters/17066/Fight_Club.jpg" size="original"/>
                 *         <image url="http://images.themoviedb.org/posters/17066/Fight_Club_thumb.jpg" size="thumb"/>
                 *         <image url="http://images.themoviedb.org/posters/17066/Fight_Club_cover.jpg" size="cover"/>
                 *         <image url="http://images.themoviedb.org/posters/17066/Fight_Club_mid.jpg" size="mid"/>
                 *     </poster>
                 *     <backdrop id="18593">
                 *         <image url="http://images.themoviedb.org/backdrops/18593/Fight_Club_on_the_street1.jpg" size="original"/>
                 *         <image url="http://images.themoviedb.org/backdrops/18593/Fight_Club_on_the_street1_poster.jpg" size="poster"/>
                 *         <image url="http://images.themoviedb.org/backdrops/18593/Fight_Club_on_the_street1_thumb.jpg" size="thumb"/>
                 *     </backdrop>
                 * </images> 
                 */
                if (checkStartEvent(event, "images")) {
                    event = xmlReader.nextEvent();
                    
                    while (!checkEndEvent(event, "images")) {
                        if (checkStartEvent(event, "image")) {
                            Artwork artwork = new Artwork();
                            Iterator<Attribute> attributes = event.asStartElement().getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equalsIgnoreCase("type"))
                                    artwork.setType(attribute.getValue());
                                if (attribute.getName().toString().equalsIgnoreCase("size"))
                                    artwork.setSize(attribute.getValue());
                                if (attribute.getName().toString().equalsIgnoreCase("url"))
                                    artwork.setUrl(attribute.getValue());
                                if (attribute.getName().toString().equalsIgnoreCase("id"))
                                    artwork.setId(attribute.getValue());
                            }
                            event = xmlReader.nextEvent(); // Skip the characters at the end of the attributes
                            movie.addArtwork(artwork);
                        }
                        
                        if (checkStartEvent(event, "poster")) {
                            Artwork artwork = new Artwork();
                            String imageId = getImageId(event);
                            event = xmlReader.nextEvent(); // Skip the characters at the end of the attributes
                            event = xmlReader.nextEvent();
                            
                            while (!checkEndEvent(event, "poster")) {
                                artwork = new Artwork();
                                artwork.setType(Artwork.ARTWORK_TYPE_POSTER);
                                artwork.setId(imageId);
                                
                                if (checkStartEvent(event, "image")) {
                                    Iterator<Attribute> attributes = event.asStartElement().getAttributes();
                                    while (attributes.hasNext()) {
                                        Attribute attribute = attributes.next();
                                        if (attribute.getName().toString().equalsIgnoreCase("url"))
                                            artwork.setUrl(attribute.getValue());
                                        if (attribute.getName().toString().equalsIgnoreCase("size"))
                                            artwork.setSize(attribute.getValue());
                                    }
                                    event = xmlReader.nextEvent(); // Skip the characters at the end of the attributes
                                    movie.addArtwork(artwork);
                                }
                                event = xmlReader.nextEvent();
                            }
                        }
                        
                        if (checkStartEvent(event, "backdrop")) {
                            Artwork artwork = new Artwork();
                            String imageId = getImageId(event);
                            event = xmlReader.nextEvent(); // Skip the characters at the end of the attributes
                            event = xmlReader.nextEvent();
                            
                            while (!checkEndEvent(event, "backdrop")) {
                                artwork = new Artwork();
                                artwork.setType(Artwork.ARTWORK_TYPE_BACKDROP);
                                artwork.setId(imageId);
                                
                                if (checkStartEvent(event, "image")) {
                                    Iterator<Attribute> attributes = event.asStartElement().getAttributes();
                                    while (attributes.hasNext()) {
                                        Attribute attribute = attributes.next();
                                        if (attribute.getName().toString().equalsIgnoreCase("url"))
                                            artwork.setUrl(attribute.getValue());
                                        if (attribute.getName().toString().equalsIgnoreCase("size"))
                                            artwork.setSize(attribute.getValue());
                                    }
                                    event = xmlReader.nextEvent(); // Skip the characters at the end of the attributes
                                    movie.addArtwork(artwork);
                                }
                                event = xmlReader.nextEvent();
                            }
                        }
                        event = xmlReader.nextEvent();
                    } // While "images"
                } // If "images"
            } // if start element
            
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (endElement.getName().getLocalPart().equalsIgnoreCase("movie")) {
                    break;
                }
            }
        }
        } catch (Exception error) {
            System.err.println("Error: " + error.getMessage());
            error.printStackTrace();
        }
        return movie;
    }

    /**
     * Check to see if the event passed is a start element and matches the eventName
     * @param event
     * @param endString
     * @return True if the event is an end element and matches the eventName
     */
    private boolean checkStartEvent(XMLEvent event, String eventName) {
        boolean validElement = false;
        
        if (event.isStartElement()) {
            if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(eventName)) {
                validElement = true;
            }
        }
        return validElement;
    }

    /**
     * Check to see if the event passed is an end element and matches the eventName
     * @param event
     * @param endString
     * @return True if the event is an end element and matches the eventName
     */
    private boolean checkEndEvent(XMLEvent event, String eventName) {
        boolean validElement = false;
        
        if (event.isEndElement()) {
            if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(eventName)) {
                validElement = true;
            }
        }
        return validElement;
    }
    
    /**
     * Find the ID in the element attributes
     * @param event
     * @return the imageId
     */
    @SuppressWarnings({"unchecked"})
    private String getImageId(XMLEvent event) {
        String imageId = null;

        try {
            // read the id attribute from the element
            Iterator<Attribute> attributes = event.asStartElement().getAttributes();
            while (attributes.hasNext()) {
                Attribute attribute = attributes.next();
                if (attribute.getName().toString().equalsIgnoreCase("id"))
                    imageId = attribute.getValue();
            }
        } catch (Exception error) {
            imageId = null;
        }
        
        return imageId;
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
}
