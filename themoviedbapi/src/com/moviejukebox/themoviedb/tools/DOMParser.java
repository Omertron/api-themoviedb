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
package com.moviejukebox.themoviedb.tools;

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
import com.moviejukebox.themoviedb.model.MovieDB;
import com.moviejukebox.themoviedb.model.Person;
import com.moviejukebox.themoviedb.model.Studio;

public class DOMParser {

    static Logger logger = TheMovieDb.getLogger();

    /**
     * Returns a list of MovieDB object parsed from the DOM Document
     * even if there is only one movie
     * @param doc DOM Document
     * @return
     */
    public static List<MovieDB> parseMovies(Document doc) {
        List<MovieDB> movies = new ArrayList<MovieDB>();
        NodeList nlMovies = doc.getElementsByTagName("movie");
        if ((nlMovies == null) || nlMovies.getLength() == 0) {
            return movies;
        }

        MovieDB movie = null;

        for (int i = 0; i < nlMovies.getLength(); i++) {
            Node movieNode = nlMovies.item(i);
            if (movieNode.getNodeType() == Node.ELEMENT_NODE) {
                Element movieElement = (Element) movieNode;
                movie = DOMParser.parseMovieInfo(movieElement);
                if (movie != null) {
                    movies.add(movie);
                }
            }
        }
        return movies;
    }

    /**
     * Returns the first MovieDB from the DOM Document.
     * @param doc a DOM Document
     * @return
     */
    public static MovieDB parseMovie(Document doc) {
        MovieDB movie = new MovieDB();
        NodeList nlMovies = doc.getElementsByTagName("movie");
        if ((nlMovies == null) || nlMovies.getLength() == 0) {
            return movie;
        }

        Node nMovie = nlMovies.item(0);
        if (nMovie.getNodeType() == Node.ELEMENT_NODE) {
            Element eMovie = (Element) nMovie;
            movie = DOMParser.parseMovieInfo(eMovie);
        }

        return movie;
    }

    public static Person parsePersonInfo(Document doc) {
        Person person = null;

        try {
            person = new Person();
            NodeList personNodeList = doc.getElementsByTagName("person");

            // Only get the first movie from the list
            Node personNode = personNodeList.item(0);

            if (personNode == null) {
                logger.finest("Person not found");
                return person;
            }

            if (personNode.getNodeType() == Node.ELEMENT_NODE) {
                Element personElement = (Element) personNode;

                person.setName(DOMHelper.getValueFromElement(personElement, "name"));
                person.setId(DOMHelper.getValueFromElement(personElement, "id"));
                person.setBiography(DOMHelper.getValueFromElement(personElement, "biography"));
                person.setKnownMovies(Integer.parseInt(DOMHelper.getValueFromElement(personElement, "known_movies")));
                person.setBirthday(DOMHelper.getValueFromElement(personElement, "birthday"));
                person.setBirthPlace(DOMHelper.getValueFromElement(personElement, "birthplace"));
                person.setUrl(DOMHelper.getValueFromElement(personElement, "url"));
                person.setVersion(Integer.parseInt(DOMHelper.getValueFromElement(personElement, "version")));
                person.setLastModifiedAt(DOMHelper.getValueFromElement(personElement, "last_modified_at"));

                NodeList artworkNodeList = doc.getElementsByTagName("image");
                for (int nodeLoop = 0; nodeLoop < artworkNodeList.getLength(); nodeLoop++) {
                    Node artworkNode = artworkNodeList.item(nodeLoop);
                    if (artworkNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element artworkElement = (Element) artworkNode;
                        Artwork artwork = new Artwork();
                        artwork.setType(artworkElement.getAttribute("type"));
                        artwork.setUrl(artworkElement.getAttribute("url"));
                        artwork.setSize(artworkElement.getAttribute("size"));
                        artwork.setId(artworkElement.getAttribute("id"));
                        person.addArtwork(artwork);
                    }
                }

                NodeList filmNodeList = doc.getElementsByTagName("movie");
                for (int nodeLoop = 0; nodeLoop < filmNodeList.getLength(); nodeLoop++) {
                    Node filmNode = filmNodeList.item(nodeLoop);
                    if (filmNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element filmElement = (Element) filmNode;
                        Filmography film = new Filmography();

                        film.setCharacter(filmElement.getAttribute("character"));
                        film.setDepartment(filmElement.getAttribute("department"));
                        film.setId(filmElement.getAttribute("id"));
                        film.setJob(filmElement.getAttribute("job"));
                        film.setName(filmElement.getAttribute("name"));
                        film.setUrl(filmElement.getAttribute("url"));

                        person.addFilm(film);
                    }
                }
            }
        } catch (Exception error) {
            logger.severe("ERROR: " + error.getMessage());
            error.printStackTrace();
        }

        return person;
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
            movie.setLanguage(DOMHelper.getValueFromElement(movieElement, "language"));
            movie.setOriginalName(DOMHelper.getValueFromElement(movieElement, "original_name"));
            movie.setTitle(DOMHelper.getValueFromElement(movieElement, "name"));
            movie.setAlternativeName(DOMHelper.getValueFromElement(movieElement, "alternative_name"));
            movie.setType(DOMHelper.getValueFromElement(movieElement, "type"));
            movie.setId(DOMHelper.getValueFromElement(movieElement, "id"));
            movie.setImdb(DOMHelper.getValueFromElement(movieElement, "imdb_id"));
            movie.setUrl(DOMHelper.getValueFromElement(movieElement, "url"));
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

                            category.setType(subElement.getAttribute("type"));
                            category.setUrl(subElement.getAttribute("url"));
                            category.setName(subElement.getAttribute("name"));
                            category.setId(subElement.getAttribute("id"));

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

                            studio.setUrl(subElement.getAttribute("url"));
                            studio.setName(subElement.getAttribute("name"));
                            studio.setId(subElement.getAttribute("id"));

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

                            country.setName(subElement.getAttribute("name"));
                            country.setCode(subElement.getAttribute("code"));
                            country.setUrl(subElement.getAttribute("url"));

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

                            person.setName(subElement.getAttribute("name"));
                            person.setCharacter(subElement.getAttribute("character"));
                            person.setJob(subElement.getAttribute("job"));
                            person.setId(subElement.getAttribute("id"));
                            person.addArtwork(Artwork.ARTWORK_TYPE_PERSON,
                                    Artwork.ARTWORK_SIZE_THUMB,
                                    subElement.getAttribute("thumb"), "-1");
                            person.setDepartment(subElement.getAttribute("department"));
                            person.setUrl(subElement.getAttribute("url"));
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
                                artwork.setType(subElement.getAttribute("type"));
                                artwork.setSize(subElement.getAttribute("size"));
                                artwork.setUrl(subElement.getAttribute("url"));
                                artwork.setId(subElement.getAttribute("id"));
                                movie.addArtwork(artwork);
                            } else if (subElement.getNodeName().equalsIgnoreCase("backdrop")
                                    || subElement.getNodeName().equalsIgnoreCase("poster")) {
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
                                logger.severe("UNKNOWN Image type: " + subElement.getNodeName());
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
     * Parse a DOM document and returns a list of Person
     * @param doc a DOM document
     * @return
     */
    public static List<Person> parsePersonGetVersion(Document doc) {
        List<Person> people = new ArrayList<Person>();
        NodeList movies = doc.getElementsByTagName("movie");
        if ((movies == null) || movies.getLength() == 0) {
            return people;
        }

        for (int i = 0; i < movies.getLength(); i++) {
            Node node = movies.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Person person = new Person();
                person.setName(DOMHelper.getValueFromElement(element, "name"));
                person.setId(DOMHelper.getValueFromElement(element, "id"));
                person.setVersion(Integer.valueOf(DOMHelper.getValueFromElement(element, "version")));
                person.setLastModifiedAt(DOMHelper.getValueFromElement(element, "last_modified_at"));
                people.add(person);
            }
        }

        return people;
    }

    /**
     * Retrieve a list of valid genres within TMDb.
     * @param doc a DOM document
     * @return
     */
    public static List<Category> parseCategories(Document doc) {
        List<Category> categories = new ArrayList<Category>();
        NodeList genres = doc.getElementsByTagName("genre");
        if ((genres == null) || genres.getLength() == 0) {
            return categories;
        }

        for (int i = 0; i < genres.getLength(); i++) {
            Node node = genres.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Category category = new Category();
                category.setName(element.getAttribute("name"));
                category.setId(DOMHelper.getValueFromElement(element, "id"));
                category.setUrl(DOMHelper.getValueFromElement(element, "url"));
                category.setType(""); // there are no type in the XML
                categories.add(category);
            }
        }

        return categories;
    }
    
    /**
     * Parse a DOM document and returns the latest Movie.
     * @param doc
     * @return
     */
    public static MovieDB parseLatestMovie(Document doc) {
        MovieDB movie = new MovieDB();
        NodeList movies = doc.getElementsByTagName("movie");
        if ((movies == null) || movies.getLength() == 0) {
            return movie;
        }

        Node node = movies.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            movie.setTitle(DOMHelper.getValueFromElement(element, "name"));
            movie.setId(DOMHelper.getValueFromElement(element, "id"));
            movie.setImdb(DOMHelper.getValueFromElement(element, "imdb_id"));
            // to be done:
            //movie.setVersion(DOMHelper.getValueFromElement(element, "version"));
            //movie.setLastModifiedAt(DOMHelper.getValueFromElement(element, "last_modified_at"));
        }

        return movie;
    }
}
