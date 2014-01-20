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
package com.omertron.themoviedbapi.wrapper;

import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public abstract class AbstractWrapper extends AbstractJsonMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Get a list of the enums passed
     *
     * @param <E>
     * @param clz Class of the enum
     * @param typeList Array of the enums
     * @return
     */
    public <E extends Enum<E>> List<E> getTypeList(Class<E> clz, E[] typeList) {
        if (typeList.length > 0) {
            return new ArrayList<E>(Arrays.asList(typeList));
        } else {
            return new ArrayList<E>(EnumSet.allOf(clz));
        }
    }
}
