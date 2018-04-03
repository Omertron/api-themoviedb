/*
 *      Copyright (c) 2004-2016 Stuart Boston
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
package com.omertron.themoviedbapi.enumeration;

public enum Gender {
    MALE(2),
    FEMALE(1),
    UNKNOWN(0);

    private final int type;

    private Gender(int type) {
        this.type = type;
    }

    /**
     * Get the gender from an integer type
     *
     * @param type Integer to convert to enum
     * @return enum version of param
     */
    public static Gender fromInteger(int type) {
        for (Gender gender : Gender.values()) {
            if (gender.type == type) {
                return gender;
            }
        }
        return UNKNOWN;
    }

}
