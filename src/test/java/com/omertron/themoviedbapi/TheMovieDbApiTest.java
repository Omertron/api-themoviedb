/*
 *      Copyright (c) 2004-2013 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.themoviedbapi;

import com.omertron.themoviedbapi.model.*;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.results.TmdbResultsMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test cases for TheMovieDbApi API
 *
 * @author stuart.boston
 */
public class TheMovieDbApiTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TheMovieDbApiTest.class);
    // API Key
    private static final String API_KEY = "5a1a77e2eba8984804586122754f969f";
    private static TheMovieDbApi tmdb;
    // Test data
    private static final int ID_MOVIE_BLADE_RUNNER = 78;
    private static final int ID_MOVIE_THE_AVENGERS = 24428;
    private static final int ID_COLLECTION_STAR_WARS = 10;
    private static final int ID_PERSON_BRUCE_WILLIS = 62;
    private static final int ID_COMPANY = 2;
    private static final String COMPANY_NAME = "Marvel Studios";
    private static final int ID_GENRE_ACTION = 28;
    private static final String ID_KEYWORD = "1721";
    // Languages
    private static final String LANGUAGE_DEFAULT = "";
    private static final String LANGUAGE_ENGLISH = "en";
    private static final String LANGUAGE_RUSSIAN = "ru";

    // session id of test users named 'apitests'
    private static final String SESSION_ID_APITESTS = "63c85deb39337e29b69d78265eb28d639cbd6f72";

    public TheMovieDbApiTest() throws MovieDbException {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        tmdb = new TheMovieDbApi(API_KEY);
        TestLogger.Configure();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getConfiguration method, of class TheMovieDbApi.
     */
    @Test
    public void testConfiguration() throws IOException {
        LOG.info("Test Configuration");

        TmdbConfiguration tmdbConfig = tmdb.getConfiguration();
        assertNotNull("Configuration failed", tmdbConfig);
        assertTrue("No base URL", StringUtils.isNotBlank(tmdbConfig.getBaseUrl()));
        assertTrue("No backdrop sizes", tmdbConfig.getBackdropSizes().size() > 0);
        assertTrue("No poster sizes", tmdbConfig.getPosterSizes().size() > 0);
        assertTrue("No profile sizes", tmdbConfig.getProfileSizes().size() > 0);
        LOG.info(tmdbConfig.toString());
    }

    /**
     * Test of searchMovie method, of class TheMovieDbApi.
     */
    @Test
    public void testSearchMovie() throws MovieDbException {
        LOG.info("searchMovie");

        // Try a movie with less than 1 page of results
        TmdbResultsList<MovieDb> movieList = tmdb.searchMovie("Blade Runner", 0, "", true, 0);
//        List<MovieDb> movieList = tmdb.searchMovie("Blade Runner", "", true);
        assertTrue("No movies found, should be at least 1", movieList.getResults().size() > 0);

        // Try a russian langugage movie
        movieList = tmdb.searchMovie("О чём говорят мужчины", 0, LANGUAGE_RUSSIAN, true, 0);
        assertTrue("No 'RU' movies found, should be at least 1", movieList.getResults().size() > 0);

        // Try a movie with more than 20 results
        movieList = tmdb.searchMovie("Star Wars", 0, LANGUAGE_ENGLISH, false, 0);
        assertTrue("Not enough movies found, should be over 15, found " + movieList.getResults().size(), movieList.getResults().size() >= 15);
    }

    /**
     * Test of getMovieInfo method, of class TheMovieDbApi.
     */
    @Test
    public void testGetMovieInfo() throws MovieDbException {
        LOG.info("getMovieInfo");
        MovieDb result = tmdb.getMovieInfo(ID_MOVIE_BLADE_RUNNER, LANGUAGE_ENGLISH, "alternative_titles,casts,images,keywords,releases,trailers,translations,similar_movies,reviews,lists");
        assertEquals("Incorrect movie information", "Blade Runner", result.getOriginalTitle());
    }

    /**
     * Test of getMovieAlternativeTitles method, of class TheMovieDbApi.
     */
    @Test
    public void testGetMovieAlternativeTitles() throws MovieDbException {
        LOG.info("getMovieAlternativeTitles");
        String country = "";
        TmdbResultsList<AlternativeTitle> result = tmdb.getMovieAlternativeTitles(ID_MOVIE_BLADE_RUNNER, country, "casts,images,keywords,releases,trailers,translations,similar_movies,reviews,lists");
        assertTrue("No alternative titles found", result.getResults().size() > 0);

        country = "US";
        result = tmdb.getMovieAlternativeTitles(ID_MOVIE_BLADE_RUNNER, country);
        assertTrue("No alternative titles found", result.getResults().size() > 0);

    }

    /**
     * Test of getMovieCasts method, of class TheMovieDbApi.
     */
    @Test
    public void testGetMovieCasts() throws MovieDbException {
        LOG.info("getMovieCasts");
        TmdbResultsList<Person> people = tmdb.getMovieCasts(ID_MOVIE_BLADE_RUNNER, "alternative_titles,casts,images,keywords,releases,trailers,translations,similar_movies,reviews,lists");
        assertTrue("No cast information", people.getResults().size() > 0);

        String name1 = "Harrison Ford";
        String name2 = "Charles Knode";
        boolean foundName1 = Boolean.FALSE;
        boolean foundName2 = Boolean.FALSE;

        for (Person person : people.getResults()) {
            if (!foundName1 && person.getName().equalsIgnoreCase(name1)) {
                foundName1 = Boolean.TRUE;
            }

            if (!foundName2 && person.getName().equalsIgnoreCase(name2)) {
                foundName2 = Boolean.TRUE;
            }
        }
        assertTrue("Couldn't find " + name1, foundName1);
        assertTrue("Couldn't find " + name2, foundName2);
    }

    /**
     * Test of getMovieImages method, of class TheMovieDbApi.
     */
    @Test
    public void testGetMovieImages() throws MovieDbException {
        LOG.info("getMovieImages");
        String language = "";
        TmdbResultsList<Artwork> result = tmdb.getMovieImages(ID_MOVIE_BLADE_RUNNER, language);
        assertFalse("No artwork found", result.getResults().isEmpty());
    }

    /**
     * Test of getMovieKeywords method, of class TheMovieDbApi.
     */
    @Test
    public void testGetMovieKeywords() throws MovieDbException {
        LOG.info("getMovieKeywords");
        TmdbResultsList<Keyword> result = tmdb.getMovieKeywords(ID_MOVIE_BLADE_RUNNER);
        assertFalse("No keywords found", result.getResults().isEmpty());
    }

    /**
     * Test of getMovieReleaseInfo method, of class TheMovieDbApi.
     */
    @Test
    public void testGetMovieReleaseInfo() throws MovieDbException {
        LOG.info("getMovieReleaseInfo");
        TmdbResultsList<ReleaseInfo> result = tmdb.getMovieReleaseInfo(ID_MOVIE_BLADE_RUNNER, "");
        assertFalse("Release information missing", result.getResults().isEmpty());
    }

    /**
     * Test of getMovieTrailers method, of class TheMovieDbApi.
     */
    @Test
    public void testGetMovieTrailers() throws MovieDbException {
        LOG.info("getMovieTrailers");
        TmdbResultsList<Trailer> result = tmdb.getMovieTrailers(ID_MOVIE_BLADE_RUNNER, "");
        assertFalse("Movie trailers missing", result.getResults().isEmpty());
    }

    /**
     * Test of getMovieTranslations method, of class TheMovieDbApi.
     */
    @Test
    public void testGetMovieTranslations() throws MovieDbException {
        LOG.info("getMovieTranslations");
        TmdbResultsList<Translation> result = tmdb.getMovieTranslations(ID_MOVIE_BLADE_RUNNER);
        assertFalse("No translations found", result.getResults().isEmpty());
    }

    /**
     * Test of getCollectionInfo method, of class TheMovieDbApi.
     */
    @Test
    public void testGetCollectionInfo() throws MovieDbException {
        LOG.info("getCollectionInfo");
        String language = "";
        CollectionInfo result = tmdb.getCollectionInfo(ID_COLLECTION_STAR_WARS, language);
        assertFalse("No collection information", result.getParts().isEmpty());
    }

    /**
     * Test of createImageUrl method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testCreateImageUrl() throws MovieDbException {
        LOG.info("createImageUrl");
        MovieDb movie = tmdb.getMovieInfo(ID_MOVIE_BLADE_RUNNER, "");
        String result = tmdb.createImageUrl(movie.getPosterPath(), "original").toString();
        assertTrue("Error compiling image URL", !result.isEmpty());
    }

    /**
     * Test of getMovieInfoImdb method, of class TheMovieDbApi.
     */
    @Test
    public void testGetMovieInfoImdb() throws MovieDbException {
        LOG.info("getMovieInfoImdb");
        MovieDb result = tmdb.getMovieInfoImdb("tt0076759", "en-US");
        assertTrue("Error getting the movie from IMDB ID", result.getId() == 11);
    }

    /**
     * Test of getApiKey method, of class TheMovieDbApi.
     */
    @Test
    public void testGetApiKey() {
        // Not required
    }

    /**
     * Test of getApiBase method, of class TheMovieDbApi.
     */
    @Test
    public void testGetApiBase() {
        // Not required
    }

    /**
     * Test of getConfiguration method, of class TheMovieDbApi.
     */
    @Test
    public void testGetConfiguration() {
        // Not required
    }

    /**
     * Test of searchPeople method, of class TheMovieDbApi.
     */
    @Test
    public void testSearchPeople() throws MovieDbException {
        LOG.info("searchPeople");
        String personName = "Bruce Willis";
        boolean includeAdult = false;
        TmdbResultsList<Person> result = tmdb.searchPeople(personName, includeAdult, 0);
        assertTrue("Couldn't find the person", result.getResults().size() > 0);
    }

    /**
     * Test of getPersonInfo method, of class TheMovieDbApi.
     */
    @Test
    public void testGetPersonInfo() throws MovieDbException {
        LOG.info("getPersonInfo");
        Person result = tmdb.getPersonInfo(ID_PERSON_BRUCE_WILLIS);
        assertTrue("Wrong actor returned", result.getId() == ID_PERSON_BRUCE_WILLIS);
    }

    /**
     * Test of getPersonCredits method, of class TheMovieDbApi.
     */
    @Test
    public void testGetPersonCredits() throws MovieDbException {
        LOG.info("getPersonCredits");

        TmdbResultsList<PersonCredit> result = tmdb.getPersonCredits(ID_PERSON_BRUCE_WILLIS);
        assertTrue("No cast information", result.getResults().size() > 0);
    }

    /**
     * Test of getPersonImages method, of class TheMovieDbApi.
     */
    @Test
    public void testGetPersonImages() throws MovieDbException {
        LOG.info("getPersonImages");

        TmdbResultsList<Artwork> result = tmdb.getPersonImages(ID_PERSON_BRUCE_WILLIS);
        assertTrue("No cast information", result.getResults().size() > 0);
    }

    /**
     * Test of getLatestMovie method, of class TheMovieDbApi.
     */
    @Test
    public void testGetLatestMovie() throws MovieDbException {
        LOG.info("getLatestMovie");
        MovieDb result = tmdb.getLatestMovie();
        assertTrue("No latest movie found", result != null);
        assertTrue("No latest movie found", result.getId() > 0);
    }

    /**
     * Test of compareMovies method, of class TheMovieDbApi.
     */
    @Test
    public void testCompareMovies() {
        // Not required
    }

    /**
     * Test of setProxy method, of class TheMovieDbApi.
     */
    @Test
    public void testSetProxy() {
        // Not required
    }

    /**
     * Test of setTimeout method, of class TheMovieDbApi.
     */
    @Test
    public void testSetTimeout() {
        // Not required
    }

    /**
     * Test of getNowPlayingMovies method, of class TheMovieDbApi.
     */
    @Test
    public void testGetNowPlayingMovies() throws MovieDbException {
        LOG.info("getNowPlayingMovies");
        TmdbResultsList<MovieDb> result = tmdb.getNowPlayingMovies(LANGUAGE_DEFAULT, 0);
        assertTrue("No now playing movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of getPopularMovieList method, of class TheMovieDbApi.
     */
    @Test
    public void testGetPopularMovieList() throws MovieDbException {
        LOG.info("getPopularMovieList");
        TmdbResultsList<MovieDb> result = tmdb.getPopularMovieList(LANGUAGE_DEFAULT, 0);
        assertTrue("No popular movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of getTopRatedMovies method, of class TheMovieDbApi.
     */
    @Test
    public void testGetTopRatedMovies() throws MovieDbException {
        LOG.info("getTopRatedMovies");
        TmdbResultsList<MovieDb> result = tmdb.getTopRatedMovies(LANGUAGE_DEFAULT, 0);
        assertTrue("No top rated movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of getCompanyInfo method, of class TheMovieDbApi.
     */
    @Test
    public void testGetCompanyInfo() throws MovieDbException {
        LOG.info("getCompanyInfo");
        Company company = tmdb.getCompanyInfo(ID_COMPANY);
        assertTrue("No company information found", company.getCompanyId() > 0);
        assertNotNull("No parent company found", company.getParentCompany());
    }

    /**
     * Test of getCompanyMovies method, of class TheMovieDbApi.
     */
    @Test
    public void testGetCompanyMovies() throws MovieDbException {
        LOG.info("getCompanyMovies");
        TmdbResultsList<MovieDb> result = tmdb.getCompanyMovies(ID_COMPANY, LANGUAGE_DEFAULT, 0);
        assertTrue("No company movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of searchCompanies method, of class TheMovieDbApi.
     */
    @Test
    public void testSearchCompanies() throws MovieDbException {
        LOG.info("searchCompanies");
        TmdbResultsList<Company> result = tmdb.searchCompanies(COMPANY_NAME, 0);
        assertTrue("No company information found", !result.getResults().isEmpty());
    }

    /**
     * Test of getSimilarMovies method, of class TheMovieDbApi.
     */
    @Test
    public void testGetSimilarMovies() throws MovieDbException {
        LOG.info("getSimilarMovies");
        TmdbResultsList<MovieDb> result = tmdb.getSimilarMovies(ID_MOVIE_BLADE_RUNNER, LANGUAGE_DEFAULT, 0);
        assertTrue("No similar movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of getGenreList method, of class TheMovieDbApi.
     */
    @Test
    public void testGetGenreList() throws MovieDbException {
        LOG.info("getGenreList");
        TmdbResultsList<Genre> result = tmdb.getGenreList(LANGUAGE_DEFAULT);
        assertTrue("No genres found", !result.getResults().isEmpty());
    }

    /**
     * Test of getGenreMovies method, of class TheMovieDbApi.
     */
    @Test
    public void testGetGenreMovies() throws MovieDbException {
        LOG.info("getGenreMovies");
        TmdbResultsList<MovieDb> result = tmdb.getGenreMovies(ID_GENRE_ACTION, LANGUAGE_DEFAULT, 0, Boolean.TRUE);
        assertTrue("No genre movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of getUpcoming method, of class TheMovieDbApi.
     */
    @Test
    public void testGetUpcoming() throws Exception {
        LOG.info("getUpcoming");
        TmdbResultsList<MovieDb> result = tmdb.getUpcoming(LANGUAGE_DEFAULT, 0);
        assertTrue("No upcoming movies found", !result.getResults().isEmpty());
    }

    /**
     * Test of getCollectionImages method, of class TheMovieDbApi.
     */
    @Test
    public void testGetCollectionImages() throws Exception {
        LOG.info("getCollectionImages");
        TmdbResultsList<Artwork> result = tmdb.getCollectionImages(ID_COLLECTION_STAR_WARS, LANGUAGE_DEFAULT);
        assertFalse("No artwork found", result.getResults().isEmpty());
    }

    /**
     * Test of getAuthorisationToken method, of class TheMovieDbApi.
     */
    @Test
    public void testGetAuthorisationToken() throws Exception {
        LOG.info("getAuthorisationToken");
        TokenAuthorisation result = tmdb.getAuthorisationToken();
        assertFalse("Token is null", result == null);
        assertTrue("Token is not valid", result.getSuccess());
        LOG.info(result.toString());
    }

    /**
     * Test of getSessionToken method, of class TheMovieDbApi.
     *
     * TODO: Cannot be tested without a HTTP authorisation: http://help.themoviedb.org/kb/api/user-authentication
     */
    public void testGetSessionToken() throws Exception {
        LOG.info("getSessionToken");
        TokenAuthorisation token = tmdb.getAuthorisationToken();
        assertFalse("Token is null", token == null);
        assertTrue("Token is not valid", token.getSuccess());
        LOG.info(token.toString());

        TokenSession result = tmdb.getSessionToken(token);
        assertFalse("Session token is null", result == null);
        assertTrue("Session token is not valid", result.getSuccess());
        LOG.info(result.toString());
    }

    /**
     * Test of getGuestSessionToken method, of class TheMovieDbApi.
     */
    @Ignore("Not ready yet")
    public void testGetGuestSessionToken() throws Exception {
        LOG.info("getGuestSessionToken");
        TokenSession result = tmdb.getGuestSessionToken();

        assertTrue("Failed to get guest session", result.getSuccess());
    }

    @Test
    public void testGetMovieLists() throws Exception {
        LOG.info("getMovieLists");
        TmdbResultsList<MovieList> result = tmdb.getMovieLists(ID_MOVIE_BLADE_RUNNER, LANGUAGE_ENGLISH, 0);
        assertNotNull("No results found", result);
        assertTrue("No results found", result.getResults().size() > 0);
    }

    /**
     * Test of getMovieChanges method,of class TheMovieDbApi
     *
     * TODO: Do not test this until it is fixed
     */
    @Ignore("Do not test this until it is fixed")
    public void testGetMovieChanges() throws Exception {
        LOG.info("getMovieChanges");

        String startDate = "";
        String endDate = null;

        // Get some popular movies
        TmdbResultsList<MovieDb> movieList = tmdb.getPopularMovieList(LANGUAGE_DEFAULT, 0);
        for (MovieDb movie : movieList.getResults()) {
            TmdbResultsMap<String, List<ChangedItem>> result = tmdb.getMovieChanges(movie.getId(), startDate, endDate);
            LOG.info("{} has {} changes.", movie.getTitle(), result.getResults().size());
            assertTrue("No changes found", result.getResults().size() > 0);
            break;
        }
    }

    @Test
    public void testGetPersonLatest() throws Exception {
        LOG.info("getPersonLatest");

        Person result = tmdb.getPersonLatest();

        assertNotNull("No results found", result);
        assertTrue("No results found", StringUtils.isNotBlank(result.getName()));
    }

    /**
     * Test of searchCollection method, of class TheMovieDbApi.
     */
    @Test
    public void testSearchCollection() throws Exception {
        LOG.info("searchCollection");
        String query = "batman";
        int page = 0;
        TmdbResultsList<Collection> result = tmdb.searchCollection(query, LANGUAGE_DEFAULT, page);
        assertFalse("No collections found", result == null);
        assertTrue("No collections found", result.getResults().size() > 0);
    }

    /**
     * Test of searchList method, of class TheMovieDbApi.
     */
    @Test
    public void testSearchList() throws Exception {
        LOG.info("searchList");
        String query = "watch";
        int page = 0;
        TmdbResultsList<MovieList> result = tmdb.searchList(query, LANGUAGE_DEFAULT, page);
        assertFalse("No lists found", result.getResults() == null);
        assertTrue("No lists found", result.getResults().size() > 0);
    }

    /**
     * Test of searchKeyword method, of class TheMovieDbApi.
     */
    @Test
    public void testSearchKeyword() throws Exception {
        LOG.info("searchKeyword");
        String query = "action";
        int page = 0;
        TmdbResultsList<Keyword> result = tmdb.searchKeyword(query, page);
        assertFalse("No keywords found", result.getResults() == null);
        assertTrue("No keywords found", result.getResults().size() > 0);
    }

    /**
     * Test of postMovieRating method, of class TheMovieDbApi.
     *
     * TODO: Cannot be tested without a HTTP authorisation: http://help.themoviedb.org/kb/api/user-authentication
     */
    @Test
    public void testPostMovieRating() throws Exception {
        LOG.info("postMovieRating");
        Integer movieID = 68724;
        Integer rating = 3;

        boolean wasPosted = tmdb.postMovieRating(SESSION_ID_APITESTS, movieID, rating);

        assertTrue(wasPosted);
    }

    /**
     * Test of getPersonChanges method, of class TheMovieDbApi.
     *
     */
    @Ignore("Not ready yet")
    public void testGetPersonChanges() throws Exception {
        LOG.info("getPersonChanges");
        String startDate = "";
        String endDate = "";
        tmdb.getPersonChanges(ID_PERSON_BRUCE_WILLIS, startDate, endDate);
    }

    /**
     * Test of getList method, of class TheMovieDbApi.
     */
    @Test
    public void testGetList() throws Exception {
        LOG.info("getList");
        String listId = "509ec17b19c2950a0600050d";
        MovieDbList result = tmdb.getList(listId);
        assertFalse("List not found", result.getItems().isEmpty());
    }

    /**
     * Test of getKeyword method, of class TheMovieDbApi.
     */
    @Test
    public void testGetKeyword() throws Exception {
        LOG.info("getKeyword");
        Keyword result = tmdb.getKeyword(ID_KEYWORD);
        assertEquals("fight", result.getName());
    }

    /**
     * Test of getKeywordMovies method, of class TheMovieDbApi.
     */
    @Test
    public void testGetKeywordMovies() throws Exception {
        LOG.info("getKeywordMovies");
        int page = 0;
        TmdbResultsList<KeywordMovie> result = tmdb.getKeywordMovies(ID_KEYWORD, LANGUAGE_DEFAULT, page);
        assertFalse("No keyword movies found", result.getResults().isEmpty());
    }

    /**
     * Test of getReviews method, of class TheMovieDbApi.
     */
    @Test
    public void testGetReviews() throws Exception {
        LOG.info("getReviews");
        int page = 0;
        TmdbResultsList<Reviews> result = tmdb.getReviews(ID_MOVIE_THE_AVENGERS, LANGUAGE_DEFAULT, page);

        assertFalse("No reviews found", result.getResults().isEmpty());
    }

    /**
     * Test of compareMovies method, of class TheMovieDbApi.
     */
    @Ignore("Not required")
    public void testCompareMovies_3args() {
    }

    /**
     * Test of compareMovies method, of class TheMovieDbApi.
     */
    @Ignore("Not required")
    public void testCompareMovies_4args() {
    }

    /**
     * Test of getPersonPopular method, of class TheMovieDbApi.
     */
    @Ignore("Not required")
    public void testGetPersonPopular_0args() throws Exception {
    }

    /**
     * Test of getPersonPopular method, of class TheMovieDbApi.
     */
    @Test
    public void testGetPersonPopular_int() throws Exception {
        LOG.info("getPersonPopular");
        int page = 0;
        TmdbResultsList<Person> result = tmdb.getPersonPopular(page);
        assertFalse("No popular people", result.getResults().isEmpty());
    }

    /**
     * Test of getGenreMovies method, of class TheMovieDbApi.
     */
    @Ignore("Not required")
    public void testGetGenreMovies_3args() throws Exception {
    }

    /**
     * Test of getGenreMovies method, of class TheMovieDbApi.
     */
    @Ignore("Not required")
    public void testGetGenreMovies_4args() throws Exception {
    }

    /**
     * Test of getMovieChangesList method, of class TheMovieDbApi.
     */
    @Ignore("Not ready yet")
    public void testGetMovieChangesList() throws Exception {
        LOG.info("getMovieChangesList");
        int page = 0;
        String startDate = "";
        String endDate = "";
        tmdb.getMovieChangesList(page, startDate, endDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonChangesList method, of class TheMovieDbApi.
     */
    @Ignore("Not ready yet")
    public void testGetPersonChangesList() throws Exception {
        LOG.info("getPersonChangesList");
        int page = 0;
        String startDate = "";
        String endDate = "";
        tmdb.getPersonChangesList(page, startDate, endDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJobs method, of class TheMovieDbApi.
     */
    @Test
    public void testGetJobs() throws Exception {
        LOG.info("getJobs");
        TmdbResultsList<JobDepartment> result = tmdb.getJobs();
        assertFalse("No jobs found", result.getResults().isEmpty());
    }

    /**
     * Test of getDiscover method, of class TheMovieDbApi.
     */
    @Ignore("Not required")
    public void testGetDiscover_14args() throws Exception {
    }

    /**
     * Test of getDiscover method, of class TheMovieDbApi.
     */
    @Test
    public void testGetDiscover_Discover() throws Exception {
        LOG.info("getDiscover");
        Discover discover = new Discover();
        discover.year(2013).language(LANGUAGE_ENGLISH);

        TmdbResultsList<MovieDb> result = tmdb.getDiscover(discover);
        assertFalse("No movies discovered", result.getResults().isEmpty());
    }
}
