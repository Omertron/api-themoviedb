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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.Trailer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stuart
 */
public class WrapperTrailers extends AbstractWrapperId implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("quicktime")
    private List<Trailer> quicktime;
    @JsonProperty("youtube")
    private List<Trailer> youtube;

    public List<Trailer> getQuicktime() {
        return quicktime;
    }

    public List<Trailer> getYoutube() {
        return youtube;
    }

    public void setQuicktime(List<Trailer> quicktime) {
        this.quicktime = quicktime;
    }

    public void setYoutube(List<Trailer> youtube) {
        this.youtube = youtube;
    }

    /**
     * Get a combined list of the trailers with their source
     *
     * @return
     */
    public List<Trailer> getAll() {
        List<Trailer> trailers = new ArrayList<Trailer>();

        // Add the trailer to the return list along with it's source
        if (quicktime != null) {
            for (Trailer trailer : quicktime) {
                trailer.setWebsite(Trailer.WEBSITE_QUICKTIME);
                trailers.add(trailer);
            }
        }

        // Add the trailer to the return list along with it's source
        if (youtube != null) {
            for (Trailer trailer : youtube) {
                trailer.setWebsite(Trailer.WEBSITE_YOUTUBE);
                trailers.add(trailer);
            }
        }

        return trailers;
    }
}
