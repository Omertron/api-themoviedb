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
package com.moviejukebox.themoviedb.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.moviejukebox.themoviedb.TheMovieDb;
import com.moviejukebox.themoviedb.model.Artwork;
import com.moviejukebox.themoviedb.model.Category;
import com.moviejukebox.themoviedb.model.Country;
import com.moviejukebox.themoviedb.model.Filmography;
import com.moviejukebox.themoviedb.model.Language;
import com.moviejukebox.themoviedb.model.MovieDB;
import com.moviejukebox.themoviedb.model.Person;
import com.moviejukebox.themoviedb.model.Studio;

public class MovieDbParser {

    private static Logger logger = TheMovieDb.getLogger();
    
    private static final String NAME = "name";
    private static final String GENRE = "genre";
    private static final String ID = "id";
    private static final String URL = "url";
    private static final String LANGUAGE = "language";
    private static final String MOVIE = "movie";
    private static final String PERSON = "person";
    private static final String TYPE = "type";

    /**
     * Retrieve a list of valid genres within TMDb.
     * @param doc a DOM document
     * @return
     */
    public static List<Category> parseCategories(String searchUrl) {
        Document doc = null;
        List<Category> categories = new ArrayList<Category>();

        try {
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
        } catch (Exception error) {
            return categories;
        }

        if (doc == null) {
            return categories;
        }

        NodeList genres = doc.getElementsByTagName(GENRE);
        if ((genres == null) || genres.getLength() == 0) {
            return categories;
        }

        for (int i = 0; i < genres.getLength(); i++) {
            Node node = genres.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Category category = new Category();
                category.setName(element.getAttribute(NAME));
                category.setId(DOMHelper.getValueFromElement(element, ID));
                category.setUrl(DOMHelper.getValueFromElement(element, URL));
                categories.add(category);
            }
        }

        return categories;
    }

    public static List<Language> parseLanguages(String url) {
        List<Language> languages = new ArrayList<Language>();
        Document doc = null;
        
        try {
            doc = DOMHelper.getEventDocFromUrl(url);
        } catch (Exception e) {
            logger.severe("Movie.getTranslations error: " + e.getMessage());
            return languages;
        }
        
        if (doc == null) {
            return languages;
        }
        
        NodeList nlLanguages = doc.getElementsByTagName(LANGUAGE);
        
        if ((nlLanguages == null) || nlLanguages.getLength() == 0) {
            return languages;
        }
        
        for (int i = 0; i < nlLanguages.getLength(); i++) {
            Node node = nlLanguages.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                languages.add(parseSimpleLanguage(element));
            }
        }
        
        return languages;
    }

    /**
     * Parse a DOM document and returns the latest Movie.
     * This method is used for Movie.getLatest and Movie.getVersion where only
     * a few fields are initialized.
     * @param doc
     * @return
     */
    public static MovieDB parseLatestMovie(String searchUrl) {
        MovieDB movie = null;
        Document doc = null;

        try {
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
        } catch (Exception error) {
            logger.severe("GetLatest error: " + error.getMessage());
            return movie;
        }

        if (doc == null) {
            return movie;
        }

        NodeList nlMovies = doc.getElementsByTagName(MOVIE);

        if ((nlMovies == null) || nlMovies.getLength() == 0) {
            return movie;
        }

        Node node = nlMovies.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            movie = MovieDbParser.parseSimpleMovie(element);
        }

        return movie;
    }

    public static Person parseLatestPerson(String url) {
        Person person = new Person();
        Document doc = null;

        try {
            doc = DOMHelper.getEventDocFromUrl(url);
        } catch (Exception error) {
            logger.severe("Person.getLatest error: " + error.getMessage());
            return person;
        }

        if (doc == null) {
            return person;
        }

        NodeList nlMovies = doc.getElementsByTagName(PERSON);

        if ((nlMovies == null) || nlMovies.getLength() == 0) {
            return person;
        }

        Node node = nlMovies.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            person = MovieDbParser.parseSimplePerson(element);
        }

        return person;
    }

    /**
     * Returns the first MovieDB from the DOM Document.
     * @param doc a DOM Document
     * @return
     */
    public static MovieDB parseMovie(String searchUrl) {
        MovieDB movie = null;
        Document doc = null;

        try {
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
        } catch (Exception error) {
            logger.severe("TheMovieDb Error: " + error.getMessage());
            return movie;
        }

        if (doc == null) {
            return movie;
        }

        NodeList nlMovies = doc.getElementsByTagName(MOVIE);
        if ((nlMovies == null) || nlMovies.getLength() == 0) {
            return movie;
        }

        Node nMovie = nlMovies.item(0);
        if (nMovie.getNodeType() == Node.ELEMENT_NODE) {
            Element eMovie = (Element) nMovie;
            movie = parseMovieInfo(eMovie);
        }

        return movie;
    }

    public static List<MovieDB> parseMovieGetVersion(String url) {
        List<MovieDB> movies = new ArrayList<MovieDB>();
        Document doc = null;

        try {
            doc = DOMHelper.getEventDocFromUrl(url);
        } catch (Exception e) {
            logger.severe("Movie.getVersion error: " + e.getMessage());
            return movies;
        }

        if (doc == null) {
            return movies;
        }

        NodeList nlMovies = doc.getElementsByTagName(MOVIE);

        if ((nlMovies == null) || nlMovies.getLength() == 0) {
            return movies;
        }

        for (int i = 0; i < nlMovies.getLength(); i++) {
            Node node = nlMovies.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                movies.add(MovieDbParser.parseSimpleMovie(element));
            }
        }

        return movies;
    }

    private static MovieDB parseMovieInfo(Element movieElement) {
        // Inspired by
        // http://www.java-tips.org/java-se-tips/javax.xml.parsers/how-to-read-xml-file-in-java.html
        MovieDB movie = new MovieDB();
        NodeList subNodeList;
        Node subNode;
        Element subElement;

        try {
            movie.setPopularity(DOMHelper.getValueFromElement(movieElement, "popularity"));
            movie.setTranslated(DOMHelper.getValueFromElement(movieElement, "translated"));
            movie.setAdult(DOMHelper.getValueFromElement(movieElement, "adult"));
            movie.setLanguage(DOMHelper.getValueFromElement(movieElement, LANGUAGE));
            movie.setOriginalName(DOMHelper.getValueFromElement(movieElement, "original_name"));
            movie.setTitle(DOMHelper.getValueFromElement(movieElement, NAME));
            movie.setAlternativeName(DOMHelper.getValueFromElement(movieElement, "alternative_name"));
            movie.setType(DOMHelper.getValueFromElement(movieElement, TYPE));
            movie.setId(DOMHelper.getValueFromElement(movieElement, ID));
            movie.setImdb(DOMHelper.getValueFromElement(movieElement, "imdb_id"));
            movie.setUrl(DOMHelper.getValueFromElement(movieElement, URL));
            movie.setOverview(DOMHelper.getValueFromElement(movieElement, "overview"));
            movie.setRating(DOMHelper.getValueFromElement(movieElement, "rating"));
            movie.setTagline(DOMHelper.getValueFromElement(movieElement, "tagline"));
            movie.setCertification(DOMHelper.getValueFromElement(movieElement, "certification"));
            movie.setReleaseDate(DOMHelper.getValueFromElement(movieElement, "released"));
            movie.setRuntime(DOMHelper.getValueFromElement(movieElement, "runtime"));
            movie.setBudget(DOMHelper.getValueFromElement(movieElement, "budget"));
            movie.setRevenue(DOMHelper.getValueFromElement(movieElement, "revenue"));
            movie.setHomepage(DOMHelper.getValueFromElement(movieElement, "homepage"));
            movie.setTrailer(DOMHelper.getValueFromElement(movieElement, "trailer"));

            // Process the "categories"
            subNodeList = movieElement.getElementsByTagName("categories");

            for (int nodeLoop = 0; nodeLoop < subNodeList.getLength(); nodeLoop++) {
                subNode = subNodeList.item(nodeLoop);
                if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                    subElement = (Element) subNode;

                    NodeList castList = subNode.getChildNodes();
                    for (int i = 0; i < castList.getLength(); i++) {
                        Node personNode = castList.item(i);
                        if (personNode.getNodeType() == Node.ELEMENT_NODE) {
                            subElement = (Element) personNode;
                            Category category = new Category();

                            category.setType(subElement.getAttribute(TYPE));
                            category.setUrl(subElement.getAttribute(URL));
                            category.setName(subElement.getAttribute(NAME));
                            category.setId(subElement.getAttribute(ID));

                            movie.addCategory(category);
                        }
                    }
                }
            }

            // Process the "studios"
            subNodeList = movieElement.getElementsByTagName("studios");

            for (int nodeLoop = 0; nodeLoop < subNodeList.getLength(); nodeLoop++) {
                subNode = subNodeList.item(nodeLoop);
                if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                    subElement = (Element) subNode;

                    NodeList studioList = subNode.getChildNodes();
                    for (int i = 0; i < studioList.getLength(); i++) {
                        Node studioNode = studioList.item(i);
                        if (studioNode.getNodeType() == Node.ELEMENT_NODE) {
                            subElement = (Element) studioNode;
                            Studio studio = new Studio();

                            studio.setUrl(subElement.getAttribute(URL));
                            studio.setName(subElement.getAttribute(NAME));
                            studio.setId(subElement.getAttribute(ID));

                            movie.addStudio(studio);
                        }
                    }
                }
            }

            // Process the "countries"
            subNodeList = movieElement.getElementsByTagName("countries");

            for (int nodeLoop = 0; nodeLoop < subNodeList.getLength(); nodeLoop++) {
                subNode = subNodeList.item(nodeLoop);
                if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                    subElement = (Element) subNode;

                    NodeList countryList = subNode.getChildNodes();
                    for (int i = 0; i < countryList.getLength(); i++) {
                        Node countryNode = countryList.item(i);
                        if (countryNode.getNodeType() == Node.ELEMENT_NODE) {
                            subElement = (Element) countryNode;
                            Country country = new Country();

                            country.setName(subElement.getAttribute(NAME));
                            country.setCode(subElement.getAttribute("code"));
                            country.setUrl(subElement.getAttribute(URL));

                            movie.addProductionCountry(country);
                        }
                    }
                }
            }

            // Process the "cast"
            subNodeList = movieElement.getElementsByTagName("cast");

            for (int nodeLoop = 0; nodeLoop < subNodeList.getLength(); nodeLoop++) {
                subNode = subNodeList.item(nodeLoop);
                if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                    subElement = (Element) subNode;

                    NodeList castList = subNode.getChildNodes();
                    for (int i = 0; i < castList.getLength(); i++) {
                        Node personNode = castList.item(i);
                        if (personNode.getNodeType() == Node.ELEMENT_NODE) {
                            subElement = (Element) personNode;
                            Person person = new Person();

                            person.setName(subElement.getAttribute(NAME));
                            person.setCharacter(subElement.getAttribute("character"));
                            person.setJob(subElement.getAttribute("job"));
                            person.setId(subElement.getAttribute(ID));
                            person.addArtwork(Artwork.ARTWORK_TYPE_PERSON,
                                    Artwork.ARTWORK_SIZE_THUMB,
                                    subElement.getAttribute("thumb"), "-1");
                            person.setDepartment(subElement.getAttribute("department"));
                            person.setUrl(subElement.getAttribute(URL));
                            person.setOrder(subElement.getAttribute("order"));
                            person.setCastId(subElement.getAttribute("cast_id"));

                            movie.addPerson(person);
                        }
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
            subNodeList = movieElement.getElementsByTagName("images");

            for (int nodeLoop = 0; nodeLoop < subNodeList.getLength(); nodeLoop++) {
                subNode = subNodeList.item(nodeLoop);

                if (subNode.getNodeType() == Node.ELEMENT_NODE) {

                    NodeList artworkNodeList = subNode.getChildNodes();
                    for (int artworkLoop = 0; artworkLoop < artworkNodeList.getLength(); artworkLoop++) {
                        Node artworkNode = artworkNodeList.item(artworkLoop);
                        if (artworkNode.getNodeType() == Node.ELEMENT_NODE) {
                            subElement = (Element) artworkNode;

                            if (subElement.getNodeName().equalsIgnoreCase("image")) {
                                // This is the format used in Movie.imdbLookup, Movie.getInfo & Movie.search
                                Artwork artwork = new Artwork();
                                artwork.setType(subElement.getAttribute(TYPE));
                                artwork.setSize(subElement.getAttribute("size"));
                                artwork.setUrl(subElement.getAttribute(URL));
                                artwork.setId(subElement.getAttribute(ID));
                                movie.addArtwork(artwork);
                            } else if (subElement.getNodeName().equalsIgnoreCase("backdrop")
                                    || subElement.getNodeName().equalsIgnoreCase("poster")) {
                                // This is the format used in Movie.getImages
                                String artworkId = subElement.getAttribute(ID);
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
                                        artwork.setUrl(imageElement.getAttribute(URL));
                                        artwork.setSize(imageElement.getAttribute("size"));
                                        movie.addArtwork(artwork);
                                    }
                                }
                            } else {
                                // This is a classic, it should never happen error
                                logger.severe("UNKNOWN Image type: " + subElement.getNodeName());
                            }
                        }
                    }
                }
            }
        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
            final Writer eResult = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(eResult);
            error.printStackTrace(printWriter);
            logger.severe(eResult.toString());
        }
        return movie;
    }

    /**
     * Returns a list of MovieDB object parsed from the DOM Document
     * even if there is only one movie
     * @param doc DOM Document
     * @return
     */
    public static List<MovieDB> parseMovies(String searchUrl) {
        List<MovieDB> movies = new ArrayList<MovieDB>();

        Document doc = null;

        try {
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
        } catch (Exception error) {
            logger.severe("TheMovieDb Error: " + error.getMessage());
            return movies;
        }

        if (doc == null) {
            return movies;
        }

        NodeList nlMovies = doc.getElementsByTagName(MOVIE);

        if ((nlMovies == null) || nlMovies.getLength() == 0) {
            return movies;
        }

        MovieDB movie = null;

        for (int i = 0; i < nlMovies.getLength(); i++) {
            Node movieNode = nlMovies.item(i);
            if (movieNode.getNodeType() == Node.ELEMENT_NODE) {
                Element movieElement = (Element) movieNode;
                movie = parseMovieInfo(movieElement);
                if (movie != null) {
                    movies.add(movie);
                }
            }
        }
        return movies;
    }

    /**
     * Parse a DOM document and returns a list of Person
     * @param doc a DOM document
     * @return
     */
    public static List<Person> parsePersonGetVersion(String searchUrl) {
        List<Person> people = new ArrayList<Person>();
        Document doc = null;

        try {
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
        } catch (Exception error) {
            logger.severe("PersonGetVersion error: " + error.getMessage());
            return people;
        }

        if (doc == null) {
            return people;
        }

        NodeList movies = doc.getElementsByTagName(MOVIE);
        if ((movies == null) || movies.getLength() == 0) {
            return people;
        }

        for (int i = 0; i < movies.getLength(); i++) {
            Node node = movies.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                people.add(MovieDbParser.parseSimplePerson(element));
            }
        }

        return people;
    }

    public static ArrayList<Person> parsePersonInfo(String searchUrl) {
        ArrayList<Person> people = new ArrayList<Person>();
        Person person = null;
        Document doc = null;

        try {
            doc = DOMHelper.getEventDocFromUrl(searchUrl);
        } catch (Exception error) {
            logger.severe("PersonSearch error: " + error.getMessage());
            return people;
        }

        if (doc == null) {
            return people;
        }

        NodeList personNodeList = doc.getElementsByTagName(PERSON);

        
        if ((personNodeList == null) || personNodeList.getLength() == 0) {
            return people;
        }
        
        for (int loop = 0; loop < personNodeList.getLength(); loop++) {
            Node personNode = personNodeList.item(loop);
            person = new Person();
            
            if (personNode == null) {
                logger.finest("Person not found");
                return people;
            }

            if (personNode.getNodeType() == Node.ELEMENT_NODE) {
                try {
                    Element personElement = (Element) personNode;
    
                    person.setName(DOMHelper.getValueFromElement(personElement, NAME));
                    person.setId(DOMHelper.getValueFromElement(personElement, ID));
                    person.setBiography(DOMHelper.getValueFromElement(personElement, "biography"));
                    
                    try {
                        person.setKnownMovies(Integer.parseInt(DOMHelper.getValueFromElement(personElement, "known_movies")));
                    } catch (NumberFormatException error) {
                        person.setKnownMovies(0);
                    }
                    
                    person.setBirthday(DOMHelper.getValueFromElement(personElement, "birthday"));
                    person.setBirthPlace(DOMHelper.getValueFromElement(personElement, "birthplace"));
                    person.setUrl(DOMHelper.getValueFromElement(personElement, URL));
                    person.setVersion(Integer.parseInt(DOMHelper.getValueFromElement(personElement, "version")));
                    person.setLastModifiedAt(DOMHelper.getValueFromElement(personElement, "last_modified_at"));
    
                    NodeList artworkNodeList = doc.getElementsByTagName("image");
                    for (int nodeLoop = 0; nodeLoop < artworkNodeList.getLength(); nodeLoop++) {
                        Node artworkNode = artworkNodeList.item(nodeLoop);
                        if (artworkNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element artworkElement = (Element) artworkNode;
                            Artwork artwork = new Artwork();
                            artwork.setType(artworkElement.getAttribute(TYPE));
                            artwork.setUrl(artworkElement.getAttribute(URL));
                            artwork.setSize(artworkElement.getAttribute("size"));
                            artwork.setId(artworkElement.getAttribute(ID));
                            person.addArtwork(artwork);
                        }
                    }
    
                    NodeList filmNodeList = doc.getElementsByTagName(MOVIE);
                    for (int nodeLoop = 0; nodeLoop < filmNodeList.getLength(); nodeLoop++) {
                        Node filmNode = filmNodeList.item(nodeLoop);
                        if (filmNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element filmElement = (Element) filmNode;
                            Filmography film = new Filmography();
    
                            film.setCharacter(filmElement.getAttribute("character"));
                            film.setDepartment(filmElement.getAttribute("department"));
                            film.setId(filmElement.getAttribute(ID));
                            film.setJob(filmElement.getAttribute("job"));
                            film.setName(filmElement.getAttribute(NAME));
                            film.setUrl(filmElement.getAttribute(URL));
    
                            person.addFilm(film);
                        }
                    }
                    
                    people.add(person);
                } catch (Exception error) {
                    logger.severe("PersonInfo: " + error.getMessage());
                    final Writer eResult = new StringWriter();
                    final PrintWriter printWriter = new PrintWriter(eResult);
                    error.printStackTrace(printWriter);
                    logger.severe(eResult.toString());
                }
            }
        }
        
        return people;
    }

    /**
     * Parse a "simple" Language in the form:
     * <language iso_639_1="en">
     *      <english_name>English</english_name>
     *      <native_name>English</native_name>
     * </language>
     * @param element
     * @return
     */
    private static Language parseSimpleLanguage(Element element) {
        Language language = new Language();
        language.setIsoCode(element.getAttribute("iso_639_1"));
        language.setEnglishName(DOMHelper.getValueFromElement(element, "english_name"));
        language.setNativeName(DOMHelper.getValueFromElement(element, "native_name"));
        return language;
    }

    /**
     * Parse a "simple" Movie in the form:
     * <movie>
     *  <name>Inception</name>
     *  <id>36462</id>
     *  <imdb_id>tt1375666</imdb_id>
     *  <version>11</version>
     *  <last_modified_at>2010-07-26 17:06:18</last_modified_at>
     * </movie>
     * @param element
     * @return
     */
    private static MovieDB parseSimpleMovie(Element element) {
        MovieDB movie = new MovieDB();
        movie.setTitle(DOMHelper.getValueFromElement(element, NAME));
        movie.setId(DOMHelper.getValueFromElement(element, ID));
        movie.setImdb(DOMHelper.getValueFromElement(element, "imdb_id"));
        movie.setVersion(Integer.valueOf(DOMHelper.getValueFromElement(element, "version")));
        movie.setLastModifiedAt(DOMHelper.getValueFromElement(element, "last_modified_at"));
        return movie;
    }
    
    /**
     * Parse a "simple" Person in the form:
     * <person>
     *  <name>John Joseph</name>
     *  <id>111830</id>
     *  <version>3</version>
     *  <last_modified_at>2010-07-19 10:59:13</last_modified_at>
     * </person>
     * @param element
     * @return
     */
    private static Person parseSimplePerson(Element element) {
        Person person = new Person();
        person.setName(DOMHelper.getValueFromElement(element, NAME));
        person.setId(DOMHelper.getValueFromElement(element, ID));
        person.setVersion(Integer.valueOf(DOMHelper.getValueFromElement(element, "version")));
        person.setLastModifiedAt(DOMHelper.getValueFromElement(element, "last_modified_at"));
        return person;
    }

}
