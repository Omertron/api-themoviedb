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
package com.omertron.themoviedbapi.results;

import java.util.HashMap;
import java.util.Map;

/**
 * Map of the results from TheMovieDb
 *
 * If the original request contains, or could contain, a page of results, this wrapper is returned to indicate what page was
 * returned and how many are available
 *
 * @author Stuart
 * @param <K>
 * @param <V>
 */
public final class ResultsMap<K, V> extends AbstractWrapperIdPages {

    private Map<K, V> results;

    public ResultsMap() {
        this(null);
    }

    public ResultsMap(Map<K, V> resultsMap) {
        if (resultsMap == null) {
            results = new HashMap<>();
        } else {
            results = new HashMap<>(resultsMap);
        }
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

    public Map<K, V> getResults() {
        return results;
    }

    public boolean containsKey(K key) {
        return results.containsKey(key);
    }

    public V get(K key) {
        return results.get(key);
    }

    public void put(K key, V value) {
        this.results.put(key, value);
    }

    public void setResults(Map<K, V> results) {
        this.results = results;
    }

    @Override
    public int getTotalResults() {
        if (super.getTotalResults() == 0) {
            return results.size();
        } else {
            return super.getTotalResults();
        }
    }
}
