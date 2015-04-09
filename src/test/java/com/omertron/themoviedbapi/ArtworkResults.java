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

import com.omertron.themoviedbapi.enumeration.ArtworkType;
import java.util.EnumMap;
import java.util.Map;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtworkResults {

    private static final Logger LOG = LoggerFactory.getLogger(ArtworkResults.class);
    private final Map<ArtworkType, Boolean> results;

    public ArtworkResults() {
        results = new EnumMap<>(ArtworkType.class);
        for (ArtworkType at : ArtworkType.values()) {
            results.put(at, false);
        }
    }

    public void found(ArtworkType artworkType) {
        results.put(artworkType, Boolean.TRUE);
    }

    public void validateResults(ArtworkType expectedType) {
        validateResults(new ArtworkType[]{expectedType});
    }

    public void validateResults(ArtworkType expectedType1, ArtworkType expectedType2) {
        validateResults(new ArtworkType[]{expectedType1, expectedType2});
    }

    public void validateResults(ArtworkType[] expectedTypes) {
        LOG.trace("Results: {}", results);
        for (ArtworkType artworkType : expectedTypes) {
            assertTrue("No " + artworkType.name() + " found", results.get(artworkType));
            results.remove(artworkType);
        }

        for (Map.Entry<ArtworkType, Boolean> entry : results.entrySet()) {
            assertFalse(entry.getKey() + " was also found", entry.getValue());
        }
    }

}
