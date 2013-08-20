package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieDbListStatus extends StatusCode {

    @JsonProperty("list_id")
    private String listId;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }
}
