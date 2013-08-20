package com.omertron.themoviedbapi.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.MovieDbList;

import java.util.List;

public class WrapperMovieDbList extends AbstractWrapperAll {


    @JsonProperty("results")
    private List<MovieDbList> lists;

    public WrapperMovieDbList() {
        super(WrapperMovieDbList.class);
    }

    public List<MovieDbList> getLists() {
        return lists;
    }

    public void setLists(List<MovieDbList> lists) {
        this.lists = lists;
    }
}
