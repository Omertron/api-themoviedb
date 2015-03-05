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
import com.omertron.themoviedbapi.model.Certification;
import com.omertron.themoviedbapi.results.ResultsMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 * @author Luca Tagliani
 */
public class TmdbCertificationsTest extends AbstractTests {

    private static TmdbCertifications instance;

    public TmdbCertificationsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        instance = new TmdbCertifications(getApiKey(), getHttpTools());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getMoviesCertification method, of class TmdbCertifications.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetMovieCertifications() throws MovieDbException {
        LOG.info("getMovieCertifications");
        ResultsMap<String, List<Certification>> result = instance.getMoviesCertification();
        assertFalse("No movie certifications.", result.getResults().isEmpty());
        for (Map.Entry<String, List<Certification>> entry : result.getResults().entrySet()) {
            LOG.info("{} = {} entries", entry.getKey(), entry.getValue().size());
            assertFalse("No entries for " + entry.getKey(), entry.getValue().isEmpty());
        }
    }

    /**
     * Test of getTvCertification method, of class TmdbCertifications.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetTvCertifications() throws MovieDbException {
        LOG.info("getTvCertifications");
        ResultsMap<String, List<Certification>> result = instance.getTvCertification();
        assertFalse("No tv certifications.", result.getResults().isEmpty());
        for (Map.Entry<String, List<Certification>> entry : result.getResults().entrySet()) {
            LOG.info("{} = {} entries", entry.getKey(), entry.getValue().size());
            assertFalse("No entries for " + entry.getKey(), entry.getValue().isEmpty());
        }
    }
}
