package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.ChangedMovie;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_END_DATE;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_PAGE;
import static com.omertron.themoviedbapi.tools.ApiUrl.PARAM_START_DATE;
import com.omertron.themoviedbapi.wrapper.movie.WrapperMovieChanges;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

public class TmdbChanges extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbChanges.class);
    // API URL Parameters
    private static final String BASE_MOVIE = "movie/";

    public TmdbChanges(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * Get a list of movie ids that have been edited. By default we show the last 24 hours and only 100 items per page. The maximum
     * number of days that can be returned in a single request is 14. You can then use the movie changes API to get the actual data
     * that has been changed. Please note that the change log system to support this was changed on October 5, 2012 and will only
     * show movies that have been edited since.
     *
     * @param page
     * @param startDate the start date of the changes, optional
     * @param endDate the end date of the changes, optional
     * @return List of changed movie
     * @throws MovieDbException
     */
    public TmdbResultsList<ChangedMovie> getMovieChangesList(int page, String startDate, String endDate) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_MOVIE, "/changes");

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        if (StringUtils.isNotBlank(startDate)) {
            apiUrl.addArgument(PARAM_START_DATE, startDate);
        }

        if (StringUtils.isNotBlank(endDate)) {
            apiUrl.addArgument(PARAM_END_DATE, endDate);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);
        try {
            WrapperMovieChanges wrapper = mapper.readValue(webpage, WrapperMovieChanges.class);

            TmdbResultsList<ChangedMovie> results = new TmdbResultsList<ChangedMovie>(wrapper.getResults());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie changes: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    public String getPersonChangesList(int page, String startDate, String endDate) throws MovieDbException {
        throw new MovieDbException(MovieDbException.MovieDbExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }

}
