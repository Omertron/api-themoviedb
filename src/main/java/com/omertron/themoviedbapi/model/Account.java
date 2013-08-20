package com.omertron.themoviedbapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account extends AbstractJsonMapping {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("johndoe")
    private String johndoe;

    @JsonProperty("include_adult")
    private boolean includeAdult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJohndoe() {
        return johndoe;
    }

    public void setJohndoe(String johndoe) {
        this.johndoe = johndoe;
    }

    public boolean isIncludeAdult() {
        return includeAdult;
    }

    public void setIncludeAdult(boolean includeAdult) {
        this.includeAdult = includeAdult;
    }
}
