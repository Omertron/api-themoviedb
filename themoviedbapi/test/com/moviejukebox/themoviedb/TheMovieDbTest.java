package com.moviejukebox.themoviedb;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import com.moviejukebox.themoviedb.model.Person;
import com.moviejukebox.themoviedb.model.MovieDB;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        tmdb = new TheMovieDb(apikey);
    }

    @After
    public void tearDown() {
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
    public void testMoviedbSearch_withWrongTitle(){
        List<MovieDB> movies = tmdb.moviedbSearch("à(é!àç'(è!çé(èçéè'(éàç!'(èéàç!(èç'", "en");
        assertTrue(movies.isEmpty());
    }

    @Test
    public void testMoviedbSearch_withEmptyTitle(){
        List<MovieDB> movies = tmdb.moviedbSearch("", "en");
        assertTrue(movies.isEmpty());
    }

    @Test
    public void testMoviedbSearch_withNullTitle(){
        List<MovieDB> movies = tmdb.moviedbSearch((String) null, "en");
        assertTrue(movies.isEmpty());
    }


    //*** Start moviedbBrowse
    @Test
    public void testMoviedbBrowseRatingAsc() {
        List<MovieDB> movies = tmdb.moviedbBrowse("rating", "asc", "en");
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowseReleaseAsc() {
        List<MovieDB> movies = tmdb.moviedbBrowse("release", "asc", "en");
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowseTitleAsc() {
        List<MovieDB> movies = tmdb.moviedbBrowse("title", "asc", "en");
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowseRatingDesc() {
        List<MovieDB> movies = tmdb.moviedbBrowse("rating", "desc", "en");
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowseReleaseDesc() {
        List<MovieDB> movies = tmdb.moviedbBrowse("release", "desc", "en");
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviedbBrowseTitleDesc() {
        List<MovieDB> movies = tmdb.moviedbBrowse("title", "desc", "en");
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

    //@Test
    public void testMoviedbImdbLookup() {
    }

    //@Test
    public void testMoviedbGetInfo_String_String() {
    }

    //@Test
    public void testMoviedbGetInfo_3args() {
    }

    /**
     * Test of moviedbGetLatest method, of class TheMovieDb.
     */
    @Test
    public void testMoviedbGetLatest() {
        MovieDB movie = tmdb.moviedbGetLatest("en");
        assertFalse(movie.getTitle().equals(MovieDB.UNKNOWN));
        assertFalse(movie.getId().equals(MovieDB.UNKNOWN));
        assertFalse(movie.getImdb().equals(MovieDB.UNKNOWN));
    }

    /**
     * Test of moviedbGetVersion method, of class TheMovieDb.
     */
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

    //@Test
    public void testMoviedbGetImages_String_String() {
    }

    //@Test
    public void testMoviedbGetImages_3args() {
    }

    //@Test
    public void testPersonSearch() {
    }

    //@Test
    public void testPersonGetInfo() {
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

    //@Test
    public void testGetCategories() {
    }

    //@Test
    public void testFindMovie() {
    }

    //@Test
    public void testCompareMovies() {
    }
}
