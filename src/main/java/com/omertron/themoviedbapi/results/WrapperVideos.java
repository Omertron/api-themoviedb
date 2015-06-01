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

import com.fasterxml.jackson.annotation.JsonSetter;
import com.omertron.themoviedbapi.model.media.Trailer;
import com.omertron.themoviedbapi.model.media.Video;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stuart
 */
public class WrapperVideos extends AbstractWrapperId {

    private List<Video> videos = null;

    public List<Video> getVideos() {
        return videos;
    }

    @JsonSetter("results")
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @JsonSetter("quicktime")
    public void setQuickTime(List<Trailer> trailers) {
        if (this.videos == null) {
            this.videos = new ArrayList<>();
        }

        for (Trailer t : trailers) {
            videos.add(convertTrailer(t, "quicktime"));
        }
    }

    @JsonSetter("youtube")
    public void setYouTube(List<Trailer> trailers) {
        if (this.videos == null) {
            this.videos = new ArrayList<>();
        }

        for (Trailer t : trailers) {
            videos.add(convertTrailer(t, "youtube"));
        }
    }

    private Video convertTrailer(final Trailer trailer, final String site) {
        Video video = new Video();

        video.setId("");
        video.setLanguage("");
        video.setSize(0);
        video.setName(trailer.getName());
        video.setKey(trailer.getSource());
        video.setType(trailer.getType());
        video.setSite(site);

        return video;
    }
}
