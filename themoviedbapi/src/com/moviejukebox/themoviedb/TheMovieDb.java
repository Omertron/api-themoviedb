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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.moviejukebox.themoviedb.model.Artwork;
import com.moviejukebox.themoviedb.model.Category;
import com.moviejukebox.themoviedb.model.Country;
import com.moviejukebox.themoviedb.model.MovieDB;
import com.moviejukebox.themoviedb.model.Person;
import com.moviejukebox.themoviedb.tools.LogFormatter;

/**
 * This is the main class for the API to connect to TheMovieDb.org The implementation is for v2.1 
 * of the API as detailed here http://api.themoviedb.org/2.1/docs/
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
        MovieDB movie = null;
        Document doc = null;
        
        language = validateLanguage(language);
        
        // If the title is null, then exit
        if (movieTitle == null || movieTitle.equals(""))
            return movie;

        try {
            String searchUrl = buildSearchUrl("Movie.search", URLEncoder.encode(movieTitle, "UTF-8"), language);
            doc = getEventDocFromUrl(searchUrl);
            movie = parseMovieInfo(doc);

        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
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
        MovieDB movie = null;
        Document doc = null;
        
        language = validateLanguage(language);

        // If the imdbID is null, then exit
        if (imdbID == null || imdbID.equals(""))
            return movie;
        
        try {
            String searchUrl = buildSearchUrl("Movie.imdbLookup", imdbID, language);
            //xmlReader = XMLHelper.getEventReader(searchUrl);
            //movie = parseMovieInfo(xmlReader);

            doc = getEventDocFromUrl(searchUrl);
            movie = parseMovieInfo(doc);
        
        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
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
        Document doc = null;
        
        // If the tmdbID is null, then exit
        if (tmdbID == null || tmdbID.equals("") || tmdbID.equalsIgnoreCase("UNKNOWN"))
            return movie;
        
        language = validateLanguage(language);
        
        try {
            String searchUrl = buildSearchUrl("Movie.getImages", tmdbID, language);
            //xmlReader = XMLHelper.getEventReader(searchUrl);
            //movie = parseMovieInfo(xmlReader);
            
            doc = getEventDocFromUrl(searchUrl);
            movie = parseMovieInfo(doc);
            
        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
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
        Document doc = null;
        
        // If the searchTerm is null, then exit
        if (searchTerm == null || searchTerm.equals("") || searchTerm.equalsIgnoreCase("UNKNOWN"))
            return movie;
        
        language = validateLanguage(language);
        
        try {
            String searchUrl = buildSearchUrl("Movie.getImages", searchTerm, language);
            //xmlReader = XMLHelper.getEventReader(searchUrl);
            //movie = parseMovieInfo(xmlReader);
            
            doc = getEventDocFromUrl(searchUrl);
            movie = parseMovieInfo(doc);

        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
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

    public MovieDB parseMovieInfo(Document doc) {
        // Borrowed from http://www.java-tips.org/java-se-tips/javax.xml.parsers/how-to-read-xml-file-in-java.html
        MovieDB movie = null;
        NodeList movieNodeList, subNodeList;
        Node movieNode, subNode;
        Element movieElement, subElement;
        
        try {
            movie = new MovieDB();
            movieNodeList = doc.getElementsByTagName("movie");

            // Only get the first movie from the list
            movieNode = movieNodeList.item(0);

            if (movieNode.getNodeType() == Node.ELEMENT_NODE) {
                movieElement = (Element) movieNode;

                movie.setTitle(getValueFromElement(movieElement, "name"));
                movie.setPopularity(getValueFromElement(movieElement, "popularity"));
                movie.setType(getValueFromElement(movieElement, "type"));
                movie.setId(getValueFromElement(movieElement, "id"));
                movie.setImdb(getValueFromElement(movieElement, "imdb_id"));
                movie.setUrl(getValueFromElement(movieElement, "url")); 
                movie.setOverview(getValueFromElement(movieElement, "overview"));
                movie.setRating(getValueFromElement(movieElement, "rating"));
                movie.setReleaseDate(getValueFromElement(movieElement, "released"));
                movie.setRuntime(getValueFromElement(movieElement, "runtime"));
                movie.setBudget(getValueFromElement(movieElement, "budget"));
                movie.setRevenue(getValueFromElement(movieElement, "revenue"));
                movie.setHomepage(getValueFromElement(movieElement, "homepage"));
                movie.setTrailer(getValueFromElement(movieElement, "trailer"));

                // Process the "categories"
                subNodeList = doc.getElementsByTagName("categories");

                for (int nodeLoop = 0; nodeLoop < subNodeList.getLength(); nodeLoop++) {
                    subNode = subNodeList.item(nodeLoop);
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        subElement = (Element) subNode;
                        Category category = new Category();
                        
                        category.setType(getValueFromElement(subElement, "type"));
                        category.setUrl(getValueFromElement(subElement, "url"));
                        category.setName(getValueFromElement(subElement, "name"));
                     
                        movie.addCategory(category);
                    }
                }
                
                // Process the "countries"
                subNodeList = doc.getElementsByTagName("countries");

                for (int nodeLoop = 0; nodeLoop < subNodeList.getLength(); nodeLoop++) {
                    subNode = subNodeList.item(nodeLoop);
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        subElement = (Element) subNode;
                        Country country = new Country();
                        
                        country.setCode(getValueFromElement(subElement, "code"));
                        country.setUrl(getValueFromElement(subElement, "url"));
                        country.setName(getValueFromElement(subElement, "name"));
                     
                        movie.addProductionCountry(country);
                    }
                }
                
                // Process the "cast"
                subNodeList = doc.getElementsByTagName("cast");

                for (int nodeLoop = 0; nodeLoop < subNodeList.getLength(); nodeLoop++) {
                    subNode = subNodeList.item(nodeLoop);
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        subElement = (Element) subNode;
                        Person person = new Person();
                        
                        person.setUrl(getValueFromElement(subElement, "url"));
                        person.setName(getValueFromElement(subElement, "name"));
                        person.setJob(getValueFromElement(subElement, "job"));
                        person.setCharacter(getValueFromElement(subElement, "character"));
                        person.setId(getValueFromElement(subElement, "id"));
                     
                        movie.addPerson(person);
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
                subNodeList = doc.getElementsByTagName("images");

                for (int nodeLoop = 0; nodeLoop < subNodeList.getLength(); nodeLoop++) {
                    subNode = subNodeList.item(nodeLoop);
                    
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.println("Element Node: " + subNode.getNodeName() + " Attribs: " + subNode.hasAttributes() + " Children: " + subNode.hasChildNodes());
                        
                        NodeList artworkNodeList = subNode.getChildNodes();
                        for (int artworkLoop = 0; artworkLoop < artworkNodeList.getLength(); artworkLoop++) {
                            Node artworkNode = artworkNodeList.item(artworkLoop);
                            if (artworkNode.getNodeType() == Node.ELEMENT_NODE) {
                                subElement = (Element) artworkNode;

                                if (subElement.getNodeName().equalsIgnoreCase("image")) {
                                    // This is the format used in Movie.imdbLookup, Movie.getInfo & Movie.search
                                    Artwork artwork = new Artwork();
                                    artwork.setType(subElement.getAttribute("type"));
                                    artwork.setSize(subElement.getAttribute("size"));
                                    artwork.setUrl(subElement.getAttribute("url"));
                                    artwork.setId(subElement.getAttribute("id"));
                                    movie.addArtwork(artwork);
                                } else if (subElement.getNodeName().equalsIgnoreCase("backdrop") || 
                                           subElement.getNodeName().equalsIgnoreCase("poster")) {
                                    // This is the format used in Movie.getImages
                                    String artworkId = subElement.getAttribute("id");
                                    String artworkType = subElement.getNodeName();
                                    
                                    // We need to decode and loop round the child nodes to get the data
                                    NodeList imageNodeList = subElement.getChildNodes();
                                    for (int imageLoop = 0; imageLoop < imageNodeList.getLength(); imageLoop++) {
                                        Node imageNode = imageNodeList.item(imageLoop);
                                        if (imageNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element imageElement = (Element) imageNode;
                                            Artwork artwork = new Artwork();
                                            artwork.setId(artworkId);
                                            artwork.setType(artworkType);
                                            artwork.setUrl(imageElement.getAttribute("url"));
                                            artwork.setSize(imageElement.getAttribute("size"));
                                            movie.addArtwork(artwork);
                                        }
                                    }
                                } else {
                                    // This is a classic, it should never happen error
                                    logger.severe("UNKNOWN Image type");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
            error.printStackTrace();
        }
        return movie;
    }

    /**
     * Gets the string value of the tag element name passed
     * @param element
     * @param tagName
     * @return
     */
    private String getValueFromElement(Element element, String tagName) {
        String returnValue = "";
        
        try {
            NodeList elementNodeList = element.getElementsByTagName(tagName);
            Element tagElement = (Element) elementNodeList.item(0);
            NodeList tagNodeList = tagElement.getChildNodes();
            returnValue = ((Node) tagNodeList.item(0)).getNodeValue();
        } catch (Exception ignore) {
            return returnValue;
        }
        
        return returnValue;
    }

    /**
     * Get a DOM document from the supplied URL
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static Document getEventDocFromUrl(String url) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        InputStream in = (new URL(url)).openStream();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(in);
        doc.getDocumentElement().normalize();
        return doc;
    }
}
