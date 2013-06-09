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
package com.omertron.themoviedbapi.wrapper;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractWrapper {

    private Logger log;

    public AbstractWrapper(Class classToLog) {
        this.log = LoggerFactory.getLogger(classToLog);
    }

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

    /**
     * Handle unknown properties and print a message
     *
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown property: '").append(key);
        sb.append("' value: '").append(value).append("'");
        log.trace(sb.toString());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
