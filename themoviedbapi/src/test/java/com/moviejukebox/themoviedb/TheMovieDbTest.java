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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.moviejukebox.themoviedb.model.Category;
import com.moviejukebox.themoviedb.model.MovieDB;
import com.moviejukebox.themoviedb.model.Person;

/**
 * JUnit tests for TheMovieDb class. The tester must enter its IMDb API key for
 * these tests to work. Require JUnit 4.5.
 * @author mledoze
 */
public class TheMovieDbTest {

    private static String apikey = "";
    private TheMovieDb tmdb;

    public TheMovieDbTest() {
    }

    @Before
    public void setUp() {
        tmdb = new TheMovieDb(apikey);
    }

    @Test
    public void testGetApiKey() {
        assertEquals(apikey, tmdb.getApiKey());
    }

    @Test
    public void testGetDefaultLanguage() {
        assertEquals("en-US", tmdb.getDefaultLanguage());
    }

    @Test
    public void testMoviedbSearch() {
        String title = "Inception";
        List<MovieDB> movies = tmdb.moviedbSearch(title, "en");
        assertFalse(movies.isEmpty());
        assertEquals(title, movies.get(0).getTitle());
    }

    @Test
    public void testMoviedbSearch_withWrongTitle() {
        List<MovieDB> movies = tmdb.moviedbSearch("Ã (Ã©!Ã Ã§'(Ã¨!Ã§Ã©(Ã¨Ã§Ã©Ã¨'(Ã©Ã Ã§!'(Ã¨Ã©Ã Ã§!(Ã¨Ã§'", "en");
        assertTrue(movies.isEmpty());
    }

    @Test
    public void testMoviedbSearch_withEmptyTitle() {
        List<MovieDB> movies = tmdb.moviedbSearch("", "en");
        assertTrue(movies.isEmpty());
    }

    @Test
    public void testMoviedbSearch_withNullTitle() {
        List<MovieDB> movies = tmdb.moviedbSearch((String) null, "en");
        assertTrue(movies.isEmpty());
    }

    //*** Start moviedbBrowse
    @Test
    public void testMoviedbBrowseRatingAsc() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("year", "2011");
        
        List<MovieDB> movies = tmdb.moviedbBrowse("rating", "asc", params, "en");
        
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowseReleaseAsc() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("year", "2011");
        
        List<MovieDB> movies = tmdb.moviedbBrowse("release", "asc", params, "en");
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowseTitleAsc() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("year", "2011");

        List<MovieDB> movies = tmdb.moviedbBrowse("title", "asc", params, "en");
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowseRatingDesc() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("year", "2011");

        List<MovieDB> movies = tmdb.moviedbBrowse("rating", "desc", params, "en");
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowseReleaseDesc() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("year", "2011");

        List<MovieDB> movies = tmdb.moviedbBrowse("release", "desc", params, "en");
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowseTitleDesc() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("year", "2011");

        List<MovieDB> movies = tmdb.moviedbBrowse("title", "desc", params, "en");
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowse_withEmptyOrderBy() {
        assertTrue(tmdb.moviedbBrowse("", "asc", "en").isEmpty());
    }

    @Test
    public void testMoviedbBrowse_withNullOrderBy() {
        assertTrue(tmdb.moviedbBrowse((String) null, "asc", "en").isEmpty());
    }

    @Test
    public void testMoviedbBrowse_withEmptyOrder() {
        assertTrue(tmdb.moviedbBrowse("rating", "", "en").isEmpty());
    }

    @Test
    public void testMoviedbBrowse_withNullOrder() {
        assertTrue(tmdb.moviedbBrowse("rating", (String) null, "en").isEmpty());
    }

    @Test
    public void testMoviedbBrowse_incorrectParameters() {
        assertTrue(tmdb.moviedbBrowse("bla", "bla", "en").isEmpty());
    }

    @Test
    public void testMoviedbBrowse_withNullParameters() {
        assertTrue(tmdb.moviedbBrowse("rating", "asc", (Map<String, String>) null, "en").isEmpty());
    }

    @Test
    public void testMoviedbBrowse_withInvalidParameters() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("bla", "bla");
        params.put("yo", "yo");
        List<MovieDB> movies = tmdb.moviedbBrowse("title", "desc", params, "en");

        // even if parameters are incorrect we should get the result of
        // the search with the default parameters (orderBy and order) so the
        // list of movies is not empty
        assertFalse(movies.isEmpty());
    }
    //*** End moviedbBrowse

    @Test
    public void testMoviedbImdbLookup() {
        MovieDB movie = tmdb.moviedbImdbLookup("tt0137523", "en");
        assertEquals("Fight Club", movie.getTitle());
        assertEquals("550", movie.getId());
        assertEquals("tt0137523", movie.getImdb());
        assertEquals("138", movie.getRuntime());
    }

    @Test
    public void testMoviedbImdbLookup_withEmptyId() {
        MovieDB movie = tmdb.moviedbImdbLookup("", "en");
        assertTrue(movie.getTitle().equals(MovieDB.UNKNOWN));
        assertTrue(movie.getId().equals(MovieDB.UNKNOWN));
        assertTrue(movie.getImdb().equals(MovieDB.UNKNOWN));
    }

    @Test
    public void testMoviedbImdbLookup_withNullId() {
        MovieDB movie = tmdb.moviedbImdbLookup((String) null, "en");
        assertTrue(movie.getTitle().equals(MovieDB.UNKNOWN));
        assertTrue(movie.getId().equals(MovieDB.UNKNOWN));
        assertTrue(movie.getImdb().equals(MovieDB.UNKNOWN));
    }

    @Test
    public void testMoviedbGetInfo() {
        MovieDB movie = tmdb.moviedbGetInfo("187", "en");
        assertEquals("Sin City", movie.getTitle());
        assertEquals("187", movie.getId());
        assertEquals("tt0401792", movie.getImdb());
        assertEquals("124", movie.getRuntime());

    }

    @Test
    public void testMoviedbGetInfo_withExistingMovie() {
        MovieDB movie = tmdb.moviedbGetInfo("200", new MovieDB(), "en");
        assertEquals("Star Trek: Insurrection", movie.getTitle());
        assertEquals("200", movie.getId());
        assertEquals("tt0120844", movie.getImdb());
        assertEquals("103", movie.getRuntime());
    }

    @Test
    public void testMoviedbGetInfo_withNullMovie() {
        MovieDB movie = tmdb.moviedbGetInfo("306", null, "en");
        assertEquals("Beverly Hills Cop III", movie.getTitle());
        assertEquals("306", movie.getId());
        assertEquals("tt0109254", movie.getImdb());
        assertEquals("104", movie.getRuntime());
    }

    @Test
    public void testMoviedbGetInfo_withNullMovieAndEmptyId() {
        MovieDB movie = tmdb.moviedbGetInfo("", null, "en");
        assertNull(movie);
    }

    @Test
    public void testMoviedbGetInfo_withNullMovieAndNullId() {
        MovieDB movie = tmdb.moviedbGetInfo((String) null, null, "en");
        assertNull(movie);
    }

    @Test
    public void testMoviedbGetInfo_withInitializedMovie() {
        MovieDB input = new MovieDB();
        String title = "The 300 Spartans";
        String id = "19972";
        input.setTitle(title);
        MovieDB movie = tmdb.moviedbGetInfo(id, input, "en");
        assertEquals(title, movie.getTitle());
        assertEquals(id, movie.getId());
    }

    @Test
    public void testMoviedbGetLatest() {
        MovieDB movie = tmdb.moviedbGetLatest("en");
        assertFalse(movie.getTitle().equals(MovieDB.UNKNOWN));
        assertFalse(movie.getId().equals(MovieDB.UNKNOWN));
        assertFalse(movie.getImdb().equals(MovieDB.UNKNOWN));
    }

    @Test
    public void testMoviedbGetVersion_String_String() {
        MovieDB movie = tmdb.moviedbGetVersion("155", "en");
        assertEquals("The Dark Knight", movie.getTitle());
        assertEquals("155", movie.getId());
        assertEquals("tt0468569", movie.getImdb());
    }

    @Test
    public void testMoviedbGetVersion_withWrongId() {
        MovieDB movie = tmdb.moviedbGetVersion("0", "en");
        assertEquals(MovieDB.UNKNOWN, movie.getTitle());
        assertEquals(MovieDB.UNKNOWN, movie.getId());
    }

    @Test
    public void testMoviedbGetVersion_withNullId() {
        MovieDB movie = tmdb.moviedbGetVersion((String) null, "en");
        assertEquals(MovieDB.UNKNOWN, movie.getTitle());
        assertEquals(MovieDB.UNKNOWN, movie.getId());
    }

    @Test
    public void testMoviedbGetVersion_withEmptyId() {
        MovieDB movie = tmdb.moviedbGetVersion("", "en");
        assertEquals(MovieDB.UNKNOWN, movie.getTitle());
        assertEquals(MovieDB.UNKNOWN, movie.getId());
    }

    @Test
    public void testMoviedbGetVersion_List_String() {
        List<String> ids = new ArrayList<String>();
        ids.add("585");
        ids.add("11");
        List<MovieDB> movies = tmdb.moviedbGetVersion(ids, "en");

        assertEquals("Monsters, Inc.", movies.get(0).getTitle());
        assertEquals("585", movies.get(0).getId());
        assertEquals("tt0198781", movies.get(0).getImdb());

        assertEquals("Star Wars: Episode IV - A New Hope", movies.get(1).getTitle());
        assertEquals("11", movies.get(1).getId());
        assertEquals("tt0076759", movies.get(1).getImdb());

    }

    @Test
    public void testMoviedbGetVersion_withEmptyList() {
        List<MovieDB> movies = tmdb.moviedbGetVersion(new ArrayList<String>(), "en");
        assertTrue(movies.isEmpty());
    }

    @Test
    public void testMoviedbGetVersion_withNullList() {
        List<MovieDB> movies = tmdb.moviedbGetVersion((List<String>) null, "en");
        assertTrue(movies.isEmpty());
    }

    @Test
    public void testMoviedbGetImages_String_String() {
    }

    @Test
    public void testMoviedbGetImages_3args() {
    }

    @Test
    public void testPersonSearch() {
        ArrayList<Person> people = tmdb.personSearch("Tom Cruise", "en");
        
        Person person = new Person();
        
        for (Person foundPerson : people) {
            if (foundPerson.getId().equals("500")) {
                person = foundPerson;
                break;
            }
        }
        
        assertEquals("Tom Cruise", person.getName());
        assertEquals("500", person.getId());
    }

    @Test
    public void testPersonSearch_withEmptyName() {
        ArrayList<Person> people = tmdb.personSearch("", "en");
        assertTrue(people.isEmpty());
    }

    @Test
    public void testPersonSearch_withNullName() {
        ArrayList<Person> people = tmdb.personSearch((String) null, "en");
        assertTrue(people.isEmpty());
    }

    @Test
    public void testPersonGetInfo() {
        ArrayList<Person> people = tmdb.personGetInfo("260", "en");
        
        Person person = new Person();
        
        for (Person foundPerson : people) {
            if (foundPerson.getId().equals("260")) {
                person = foundPerson;
                break;
            }
        }

        assertEquals("Marco Pérez", person.getName());
        assertEquals("260", person.getId());
    }

    @Test
    public void testPersonGetInfo_withEmptyId() {
        ArrayList<Person> people = tmdb.personGetInfo("", "en");
        assertTrue(people.isEmpty());
    }

    @Test
    public void testPersonGetInfo_withNullId() {
        ArrayList<Person> people = tmdb.personGetInfo((String) null, "en");
        assertTrue(people.isEmpty());
    }

    @Test
    public void testPersonGetLatest() {
        Person person = tmdb.personGetLatest("en");
        assertFalse(person.getName().equals(MovieDB.UNKNOWN));
    }

    @Test
    public void testPersonGetVersion() {
        Person person = tmdb.personGetVersion("288", "en");
        assertEquals("Jon Seda", person.getName());
        assertEquals("288", person.getId());
    }

    @Test
    public void testPersonGetVersion_withWrongId() {
        Person person = tmdb.personGetVersion("0", "en");
        assertEquals(MovieDB.UNKNOWN, person.getName());
        assertEquals(MovieDB.UNKNOWN, person.getId());
    }

    @Test
    public void testPersonGetVersion_withNullId() {
        Person person = tmdb.personGetVersion((String) null, "en");
        assertEquals(MovieDB.UNKNOWN, person.getName());
        assertEquals(MovieDB.UNKNOWN, person.getId());
    }

    @Test
    public void testPersonGetVersion_withEmptyId() {
        Person person = tmdb.personGetVersion("", "en");
        assertEquals(MovieDB.UNKNOWN, person.getName());
        assertEquals(MovieDB.UNKNOWN, person.getId());
    }

    @Test
    public void testPersonGetVersion_List_String() {
        List<String> ids = new ArrayList<String>();
        ids.add("287");
        ids.add("5064");
        List<Person> people = tmdb.personGetVersion(ids, "en");

        assertEquals("Brad Pitt", people.get(0).getName());
        assertEquals("287", people.get(0).getId());

        assertEquals("Meryl Streep", people.get(1).getName());
        assertEquals("5064", people.get(1).getId());
    }

    @Test
    public void testPersonGetVersion_withEmptyList() {
        List<Person> people = tmdb.personGetVersion(new ArrayList<String>(), "en");
        assertTrue(people.isEmpty());
    }

    @Test
    public void testPersonGetVersion_withNullList() {
        List<Person> people = tmdb.personGetVersion((List<String>) null, "en");
        assertTrue(people.isEmpty());
    }

    @Test
    public void testGetCategories() {
        List<Category> genres = tmdb.getCategories("en");
        assertFalse(genres.isEmpty());
        assertTrue(genres.size() > 0);
    }

    @Test
    public void testFindMovie() {
    }

    @Test
    public void testCompareMovies() {
        MovieDB movie = tmdb.moviedbGetInfo("27205", "en");
        assertTrue(TheMovieDb.compareMovies(movie, "Inception", "2010"));
    }

    @Test
    public void testCompareMovies_sameTitleAndDifferentYear() {
        MovieDB movie = tmdb.moviedbGetInfo("27205", "en");
        assertFalse(TheMovieDb.compareMovies(movie, "Inception", "1999"));
    }

    @Test
    public void testCompareMovies_differentTitleAndSameYear() {
        MovieDB movie = tmdb.moviedbGetInfo("27205", "en");
        assertFalse(TheMovieDb.compareMovies(movie, "xxx", "2010"));
    }

    @Test
    public void testCompareMovies_wrongArgument() {
        assertFalse(TheMovieDb.compareMovies(null, "", "2010"));
    }
}
