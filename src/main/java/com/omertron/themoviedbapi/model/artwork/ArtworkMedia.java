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
package com.omertron.themoviedbapi.model.artwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.model.media.MediaBasic;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.model.tv.TVEpisodeBasic;
import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author Stuart
 */
public class ArtworkMedia extends Artwork implements Serializable {

    private static final long serialVersionUID = 100L;

    private MediaType mediaType;
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "media_type",
            defaultImpl = MediaBasic.class
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = MovieBasic.class, name = "movie"),
        @JsonSubTypes.Type(value = TVBasic.class, name = "tv"),
        @JsonSubTypes.Type(value = TVEpisodeBasic.class, name = "episode")
    })
    @JsonProperty("media")
    private MediaBasic media;

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    @JsonSetter("media_type")
    public void setMediaType(String mediaType) {
        this.mediaType = MediaType.fromString(mediaType);
    }

    public MediaBasic getMedia() {
        return media;
    }

    public void setMedia(MediaBasic media) {
        this.media = media;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(mediaType)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArtworkMedia) {
            final ArtworkMedia other = (ArtworkMedia) obj;
            return new EqualsBuilder()
                    .appendSuper(super.equals(obj))
                    .append(mediaType, other.mediaType)
                    .isEquals();
        } else {
            return false;
        }
    }

}
