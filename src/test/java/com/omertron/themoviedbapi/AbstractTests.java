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

import com.omertron.themoviedbapi.methods.TmdbAccount;
import com.omertron.themoviedbapi.methods.TmdbAuthentication;
import com.omertron.themoviedbapi.model.Account;
import com.omertron.themoviedbapi.model.TokenAuthorisation;
import com.omertron.themoviedbapi.model.TokenSession;
import com.omertron.themoviedbapi.tools.HttpTools;
import java.io.File;
import java.util.Properties;
import org.apache.http.client.HttpClient;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

public class AbstractTests {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTests.class);
    private static final String PROP_FIlENAME = "testing.properties";
    private static final Properties props = new Properties();
    private static HttpClient httpClient;
    private static HttpTools httpTools;
    // Session informaion
    private static TokenSession tokenSession = null;
    private static Account account = null;
    // Constants
    protected static final String LANGUAGE_DEFAULT = "";
    protected static final String LANGUAGE_ENGLISH = "en";
    protected static final String LANGUAGE_RUSSIAN = "ru";

    public static final void doConfiguration() throws MovieDbException {
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

    public static final String getSessionId() throws MovieDbException {
        if (tokenSession == null) {
            TmdbAuthentication auth = new TmdbAuthentication(getApiKey(), getHttpTools());
            LOG.info("Test and create a session token for the rest of the tests");
            // 1: Create a request token
            TokenAuthorisation token = auth.getAuthorisationToken();
            assertTrue("Token (auth) is not valid", token.getSuccess());
            token = auth.getSessionTokenLogin(token, getUsername(), getPassword());
            assertTrue("Token (login) is not valid", token.getSuccess());
            // 3: Create the sessions ID
            tokenSession = auth.getSessionToken(token);
            assertTrue("Session token is not valid", tokenSession.getSuccess());

        }
        return tokenSession.getSessionId();
    }

    public static final int getAccountId() throws MovieDbException {
        if (account == null) {
            TmdbAccount instance = new TmdbAccount(getApiKey(), getHttpTools());
            // Get the account for later tests
            account = instance.getAccount(tokenSession.getSessionId());
        }
        return account.getId();
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
