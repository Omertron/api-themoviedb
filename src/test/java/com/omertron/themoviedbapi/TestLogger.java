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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.LogManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogger {

    private static final Logger LOG = LoggerFactory.getLogger(TestLogger.class);
    private static final String CRLF = "\n";

    private TestLogger() {
        throw new UnsupportedOperationException("Class can not be instantiated");
    }

    /**
     * Configure the logger with a simple in-memory file for the required log level
     *
     * @param level The logging level required
     * @return True if successful
     */
    public static boolean Configure(String level) {
        StringBuilder config = new StringBuilder("handlers = java.util.logging.ConsoleHandler\n");
        config.append(".level = ").append(level).append(CRLF);
        config.append("java.util.logging.ConsoleHandler.level = ").append(level).append(CRLF);
        // Only works with Java 7 or later
        config.append("java.util.logging.SimpleFormatter.format = [%1$tH:%1$tM:%1$tS %4$6s] %2$s - %5$s %6$s%n").append(CRLF);
        // Exclude http logging
        config.append("sun.net.www.protocol.http.HttpURLConnection.level = OFF").append(CRLF);
        config.append("org.apache.http.level = SEVERE").append(CRLF);

        try (InputStream ins = new ByteArrayInputStream(config.toString().getBytes())) {
            LogManager.getLogManager().readConfiguration(ins);
        } catch (IOException e) {
            LOG.warn("Failed to configure log manager due to an IO problem", e);
            return Boolean.FALSE;
        }
        LOG.debug("Logger initialized to '{}' level", level);
        return Boolean.TRUE;
    }

    /**
     * Set the logging level to "ALL"
     *
     * @return True if successful
     */
    public static boolean Configure() {
        return Configure("ALL");
    }

    /**
     * Load properties from a file
     *
     * @param props
     * @param propertyFile
     */
    public static void loadProperties(Properties props, File propertyFile) {
        InputStream is = null;
        try {
            is = new FileInputStream(propertyFile);
            props.load(is);
        } catch (Exception ex) {
            LOG.warn("Failed to load properties file", ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    LOG.warn("Failed to close properties file", ex);
                }
            }
        }
    }

    /**
     * Save properties to a file
     *
     * @param props
     * @param propertyFile
     * @param headerText
     */
    public static void saveProperties(Properties props, File propertyFile, String headerText) {
        OutputStream out = null;

        try {
            out = new FileOutputStream(propertyFile);
            if (StringUtils.isNotBlank(headerText)) {
                props.store(out, headerText);
            }
        } catch (FileNotFoundException ex) {
            LOG.warn("Failed to find properties file", ex);
        } catch (IOException ex) {
            LOG.warn("Failed to read properties file", ex);
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException ex) {
                    LOG.warn("Failed to close properties file", ex);
                }
            }
        }
    }
}
