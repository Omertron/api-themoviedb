/*
 *      Copyright (c) 2004-2014 Stuart Boston
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
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.LANGUAGE_DEFAULT;
import com.omertron.themoviedbapi.model.Company;
import com.omertron.themoviedbapi.model.movie.MovieDb;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

/**
 *
 * @author stuart.boston
 */
public class TmdbCompaniesTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbCompaniesTest.class);
    // API
    private static TmdbCompanies instance;
    private static final int ID_COMPANY = 2;

    public TmdbCompaniesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbCompanies(API_KEY, new DefaultPoolingHttpClient());
        TestLogger.Configure();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getCompanyInfo method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetCompanyInfo() throws MovieDbException {
        LOG.info("getCompanyInfo");
        Company company = instance.getCompanyInfo(ID_COMPANY);
        assertTrue("No company information found", company.getCompanyId() > 0);
        assertNotNull("No parent company found", company.getParentCompany());
    }

    /**
     * Test of getCompanyMovies method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetCompanyMovies() throws MovieDbException {
        LOG.info("getCompanyMovies");
        TmdbResultsList<MovieDb> result = instance.getCompanyMovies(ID_COMPANY, LANGUAGE_DEFAULT, 0);
        assertTrue("No company movies found", !result.getResults().isEmpty());
    }
}
