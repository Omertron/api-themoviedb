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
package com.omertron.themoviedbapi.model.type;

/**
 * By default, the search type is 'phrase'. <br/>
 * This is almost guaranteed the option you will want. <br/>
 * It's a great all purpose search type and by far the most tuned for every day querying. <br/>
 * For those wanting more of an "autocomplete" type search, set this option to 'ngram'
 *
 * @author stuart.boston
 */
public enum SearchType {

    phrase,
    ngram;
}
