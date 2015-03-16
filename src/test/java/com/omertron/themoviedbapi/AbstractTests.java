/*
 *      Copyright (c) 2004-2015 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General protected License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General protected License for more details.
 *
 *      You should have received a copy of the GNU General protected License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.themoviedbapi;

import com.omertron.themoviedbapi.enumeration.MovieMethod;
import com.omertron.themoviedbapi.interfaces.AppendToResponseMethod;
import com.omertron.themoviedbapi.methods.TmdbAccount;
import com.omertron.themoviedbapi.methods.TmdbAuthentication;
import com.omertron.themoviedbapi.model.account.Account;
import com.omertron.themoviedbapi.model.authentication.TokenAuthorisation;
import com.omertron.themoviedbapi.model.authentication.TokenSession;
import com.omertron.themoviedbapi.tools.HttpTools;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.http.client.HttpClient;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

public class AbstractTests {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTests.class);
    private static final String PROP_FIlENAME = "testing.properties";
    private static final String FILENAME_EXT = ".bin";
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

    /**
     * Do the initial configuration for the test cases
     *
     * @throws MovieDbException
     */
    protected static final void doConfiguration() throws MovieDbException {
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
                props.setProperty("GuestSession", "INSERT_YOUR_GUEST_SESSION_ID_HERE");

                TestLogger.saveProperties(props, f, "Properties file for tests");
                fail("Failed to get key information from properties file '" + PROP_FIlENAME + "'");
            }
        }
    }

    /**
     * Write out the object to a file
     *
     * @param object
     * @param filename
     * @return
     */
    private static boolean writeObject(final Serializable object, final String baseFilename) {
        String filename = baseFilename + FILENAME_EXT;
        File serFile = new File(filename);

        if (serFile.exists()) {
            serFile.delete();
        }

        try {
            byte[] serObject = SerializationUtils.serialize(object);
            FileUtils.writeByteArrayToFile(serFile, serObject);
            return true;
        } catch (IOException ex) {
            LOG.info("Failed to write object to '{}': {}", filename, ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * Read the object back from a file
     *
     * @param <T>
     * @param filename
     * @return
     */
    private static <T> T readObject(final String baseFilename) {
        String filename = baseFilename + FILENAME_EXT;
        File serFile = new File(filename);

        if (serFile.exists()) {
            long diff = System.currentTimeMillis() - serFile.lastModified();
            if (diff < TimeUnit.HOURS.toMillis(1)) {
                LOG.info("File '{}' is current, no need to reacquire", filename);
            } else {
                LOG.info("File '{}' is too old, re-acquiring", filename);
                return null;
            }
        } else {
            LOG.info("File '{}' doesn't exist", filename);
            return null;
        }

        LOG.info("Reading object from '{}'", filename);
        try {
            byte[] serObject = FileUtils.readFileToByteArray(serFile);
            return (T) SerializationUtils.deserialize(serObject);
        } catch (IOException ex) {
            LOG.info("Failed to read {}: {}", filename, ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * get the Session ID
     *
     * @return
     * @throws MovieDbException
     */
    protected static final String getSessionId() throws MovieDbException {
        if (tokenSession == null) {
            LOG.info("Create a session token for the rest of the tests");
            String filename = TokenSession.class.getSimpleName();
            // Try to read the object from a file
            tokenSession = readObject(filename);

            if (tokenSession == null) {
                TmdbAuthentication auth = new TmdbAuthentication(getApiKey(), getHttpTools());
                // 1: Create a request token
                TokenAuthorisation token = auth.getAuthorisationToken();
                assertTrue("Token (auth) is not valid", token.getSuccess());
                token = auth.getSessionTokenLogin(token, getUsername(), getPassword());
                assertTrue("Token (login) is not valid", token.getSuccess());
                // 3: Create the sessions ID
                tokenSession = auth.getSessionToken(token);
                assertTrue("Session token is not valid", tokenSession.getSuccess());

                // Write the object to a file
                writeObject(tokenSession, filename);
            }
        }
        return tokenSession.getSessionId();
    }

    /**
     * Get the Account information
     *
     * @return
     * @throws MovieDbException
     */
    protected static final int getAccountId() throws MovieDbException {
        if (account == null) {
            LOG.info("Getting account information");
            String filename = Account.class.getSimpleName();
            // Read the object from a file
            account = readObject(filename);

            if (account == null) {
                TmdbAccount instance = new TmdbAccount(getApiKey(), getHttpTools());
                // Get the account for later tests
                account = instance.getAccount(tokenSession.getSessionId());

                // Write the object to a file
                writeObject(account, filename);
            }
        }
        return account.getId();
    }

    /**
     * get the Http Client
     *
     * @return
     */
    protected static HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Get the Http Tools
     *
     * @return
     */
    protected static HttpTools getHttpTools() {
        return httpTools;
    }

    /**
     * Get the API Key
     *
     * @return
     */
    protected static String getApiKey() {
        return props.getProperty("API_Key");
    }

    /**
     * Get the Account username
     *
     * @return
     */
    protected static String getUsername() {
        return props.getProperty("Username");
    }

    /**
     * Get the Account password
     *
     * @return
     */
    protected static String getPassword() {
        return props.getProperty("Password");
    }

    /**
     * Get the Guest Session ID
     *
     * @return
     */
    protected static String getGuestSession() {
        return props.getProperty("GuestSession");
    }

    /**
     * Get the named property
     *
     * @param property
     * @return
     */
    protected static String getProperty(String property) {
        appendToResponseBuilder(MovieMethod.class);
        return props.getProperty(property);
    }

    /**
     * Build up a full list of the AppendToResponse methods into a string for
     * use in the URL
     *
     * @param <T>
     * @param methodClass
     * @return
     */
    protected static <T extends AppendToResponseMethod> String appendToResponseBuilder(Class<T> methodClass) {
        boolean first = true;
        StringBuilder atr = new StringBuilder();
        for (AppendToResponseMethod method : methodClass.getEnumConstants()) {
            if (first) {
                first = false;
            } else {
                atr.append(",");
            }
            atr.append(method.getPropertyString());
        }
        return atr.toString();
    }
}
