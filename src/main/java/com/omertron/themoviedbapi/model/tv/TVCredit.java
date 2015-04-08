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
package com.omertron.themoviedbapi.model.tv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractIdName;
import java.io.Serializable;
import java.util.List;

/**
 * TV Favorite information
 *
 * @author stuart.boston
 */
public class TVCredit extends AbstractIdName implements Serializable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("original_name")
    private String originalName;
    @JsonProperty("character")
    private String character;
    @JsonProperty("seasons")
    private List<TVSeasonBasic> seasons;
    @JsonProperty("episodes")
    private List<TVEpisodeBasic> episodes;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public List<TVSeasonBasic> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<TVSeasonBasic> seasons) {
        this.seasons = seasons;
    }

    public List<TVEpisodeBasic> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<TVEpisodeBasic> episodes) {
        this.episodes = episodes;
    }

}
