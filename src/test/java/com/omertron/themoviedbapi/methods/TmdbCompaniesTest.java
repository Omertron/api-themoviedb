/*
 *      Copyright (c) 2004-2015 Stuart Boston
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

import com.omertron.themoviedbapi.AbstractTests;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestSuite;
import com.omertron.themoviedbapi.model.company.Company;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.results.ResultList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbCompaniesTest extends AbstractTests {

    private static TmdbCompanies instance;
    private static final int ID_COMPANY = 2;

    public TmdbCompaniesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbCompanies(getApiKey(), getHttpTools());
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
        assertTrue("No company information found", company.getId() > 0);
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
        ResultList<MovieBasic> result = instance.getCompanyMovies(ID_COMPANY, LANGUAGE_DEFAULT, 0);
        TestSuite.test(result,"Company Movies");
    }
}
