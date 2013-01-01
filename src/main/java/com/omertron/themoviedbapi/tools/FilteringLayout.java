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
package com.omertron.themoviedbapi.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Log4J Filtering routine to remove API keys from the output
 *
 * @author Stuart.Boston
 *
 */
public class FilteringLayout extends PatternLayout {

    private static final String REPLACEMENT = "[APIKEY]";
    private static Pattern replacementPattern = Pattern.compile("DO_NOT_MATCH");

    /**
     * Add the string to replace in the log output
     *
     * @param replacementString
     */
    public static void addReplacementString(String replacementString) {
        replacementPattern = Pattern.compile(replacementString);
    }

    /**
     * Extend the format to remove the API_KEYS from the output
     *
     * @param event
     * @return
     */
    @Override
    public String format(LoggingEvent event) {
        if (event.getMessage() instanceof String) {
            String message = event.getRenderedMessage();

            Matcher matcher = replacementPattern.matcher(message);
            if (matcher.find()) {
                String maskedMessage = matcher.replaceAll(REPLACEMENT);

                Throwable throwable = event.getThrowableInformation() != null
                        ? event.getThrowableInformation().getThrowable() : null;

                LoggingEvent maskedEvent = new LoggingEvent(event.fqnOfCategoryClass,
                        Logger.getLogger(event.getLoggerName()), event.timeStamp,
                        event.getLevel(), maskedMessage, throwable);

                return super.format(maskedEvent);
            }
        }
        return super.format(event);
    }
}
