package com.omertron.themoviedbapi.model.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;
import java.util.List;

public class ReleaseDates extends AbstractJsonMapping {

    @JsonProperty("iso_3166_1")
    private String country;
    @JsonProperty("release_dates")
    private List<ReleaseDate> releaseDate;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<ReleaseDate> getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(List<ReleaseDate> releaseDate) {
        this.releaseDate = releaseDate;
    }

}
