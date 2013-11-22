/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.themoviedbapi.model.movie;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

/**
 * Custom deserializer for the MovieState
 * @author stuart.boston
 */
public class MovieStateDeserializer extends StdDeserializer<MovieState> {

    public MovieStateDeserializer() {
        super(MovieState.class);
    }

    @Override
    public MovieState deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = (ObjectNode) mapper.readTree(jp);

        MovieState movieState = new MovieState();
        movieState.setId(root.get("id").asInt());
        movieState.setFavorite(root.get("favorite").asBoolean());
        movieState.setWatchlist(root.get("watchlist").asBoolean());

        JsonNode ratedNode = root.get("rated");

        JsonNode valueNode = ratedNode.get("value");
        if (valueNode == null) {
            movieState.setRating(-1);
        } else {
            movieState.setRating(valueNode.asInt());
        }

        return movieState;
    }
}
