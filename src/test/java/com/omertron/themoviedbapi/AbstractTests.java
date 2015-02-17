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
package com.omertron.themoviedbapi;

import com.omertron.themoviedbapi.tools.HttpTools;
import java.io.File;
import java.util.Properties;
import org.apache.http.impl.client.HttpClient;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

public class AbstractTests {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTests.class);
    private static final String PROP_FIlENAME = "testing.properties";
    private static final Properties props = new Properties();
    private static HttpClient httpClient;
    private static HttpTools httpTools;
    // Constants
    protected static final String LANGUAGE_DEFAULT = "";
    protected static final String LANGUAGE_ENGLISH = "en";
    protected static final String LANGUAGE_RUSSIAN = "ru";

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        TestLogger.Configure();
        httpClient = new SimpleHttpClientBuilder().build();
        httpTools = new HttpTools(httpClient);

        if (props.isEmpty()) {
            File f = new File(PROP_FIlENAME);
            if (f.exists()) {
                LOG.info("Loading properties from '{}'", PROP_FIlENAME);
                TestLogger.loadProperties(props, f);
            } else {
                LOG.info("Property file '{}' not found, creating dummy file.", PROP_FIlENAME);

                props.setProperty("API_Key", "INSERT_YOUR_KEY_HERE");
                props.setProperty("Username", "INSERT_YOUR_USERNAME_HERE");
                props.setProperty("Password", "INSERT_YOUR_PASSWORD_HERE");

                TestLogger.saveProperties(props, f, "Properties file for tests");
                fail("Failed to get key information from properties file '" + PROP_FIlENAME + "'");
            }
        }
    }

    @AfterClass
    public static void tearDownClass() throws MovieDbException {
    }

    @Before
    public void setUp() throws MovieDbException {
    }

    @After
    public void tearDown() throws MovieDbException {
    }

    public static HttpClient getHttpClient() {
        return httpClient;
    }

    public static HttpTools getHttpTools() {
        return httpTools;
    }

    public static String getApiKey() {
        return props.getProperty("API_Key");
    }

    public static String getUsername() {
        return props.getProperty("Username");
    }

    public static String getPassword() {
        return props.getProperty("Password");
    }

    public static String getProperty(String property) {
        return props.getProperty(property);
    }
}
