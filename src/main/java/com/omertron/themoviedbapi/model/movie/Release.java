package com.omertron.themoviedbapi.model.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omertron.themoviedbapi.model.AbstractJsonMapping;

public class Release extends AbstractJsonMapping {

    @JsonProperty("certification")
    private String certification;
    @JsonProperty("release_date")
    private String releaseDate;

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
