package com.moviejukebox.themoviedb;

import com.moviejukebox.themoviedb.model.Person;
import java.util.ArrayList;
import com.moviejukebox.themoviedb.model.MovieDB;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
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

    //@Test
    public void testMoviedbSearch() {
    }

    //@Test
    public void testMoviedbBrowse_3args() {
    }

    //@Test
    public void testMoviedbBrowse_4args() {
    }

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
