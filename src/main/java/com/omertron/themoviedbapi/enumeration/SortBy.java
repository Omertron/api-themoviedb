package com.omertron.themoviedbapi.enumeration;

public enum SortBy {

    CREATED_AT_ASC,
    CREATED_AT_DESC,
    POPULARITY_ASC,
    POPULARITY_DESC,
    RELEASE_DATE_ASC,
    RELEASE_DATE_DESC,
    REVENUE_ASC,
    REVENUE_DESC,
    PRIMARY_RELEASE_DATE_ASC,
    PRIMARY_RELEASE_DATE_DESC,
    ORIGINAL_TITLE_ASC,
    ORIGINAL_TITLE_DESC,
    VOTE_AVERAGE_ASC,
    VOTE_AVERAGE_DESC,
    VOTE_COUNT_ASC,
    VOTE_COUNT_DESC;

    public String getPropertyString() {
        return this.name().toLowerCase().replace("_asc", ".asc").replaceAll("_desc", ".desc");
    }
}
