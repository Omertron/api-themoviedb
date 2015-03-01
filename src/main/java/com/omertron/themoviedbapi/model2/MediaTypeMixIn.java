package com.omertron.themoviedbapi.model2;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.omertron.themoviedbapi.model2.movie.MovieBasic;
import com.omertron.themoviedbapi.model2.tv.TVBasic;
import com.omertron.themoviedbapi.model2.tv.TVEpisodeBasic;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "media_type",
        defaultImpl = MovieBasic.class
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MovieBasic.class, name = "movie"),
    @JsonSubTypes.Type(value = TVBasic.class, name = "tv"),
    @JsonSubTypes.Type(value = TVEpisodeBasic.class, name = "episode")
})
public class MediaTypeMixIn {

}
