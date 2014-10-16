package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import static com.omertron.themoviedbapi.methods.AbstractMethod.MAPPER;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_EXTERNAL_SOURCE;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_ID;
import static com.omertron.themoviedbapi.methods.ApiUrl.PARAM_LANGUAGE;
import com.omertron.themoviedbapi.model.FindResults;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

public class TmdbFind extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbFind.class);
    // API URL Parameters
    private static final String BASE_FIND = "find/";

    public enum ExternalSource {

        imdb_id, freebase_mid, freebase_id, tvrage_id, tvdb_id
    }

    public TmdbFind(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * You con use this method to find movies, tv series or persons using external ids.
     * 
     * Supported query ids are
     * <ul>
     * <li>Movies: imdb_id</li>
     * <li>People: imdb_id, freebase_mid, freebase_id, tvrage_id</li>
     * <li>TV Series: imdb_id, freebase_mid, freebase_id, tvdb_id, tvrage_id</li>
     * <li>TV Seasons: freebase_mid, freebase_id, tvdb_id, tvrage_id</li>
     * <li>TV Episodes: imdb_id, freebase_mid, freebase_id, tvdb_id, tvrage_idimdb_id, freebase_mid, freebase_id, tvrage_id, tvdb_id. 
     * </ul>
     * 
     * For details see http://docs.themoviedb.apiary.io/#find
     * 
     * @param id the external id
     * @param externalSource one of {@link ExternalSource}.
     * @param language the language
     * @return 
     * @throws MovieDbException
     */
    public FindResults find(String id, ExternalSource externalSource, String language) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_FIND);
        apiUrl.addArgument(PARAM_ID, id);
        apiUrl.addArgument(PARAM_EXTERNAL_SOURCE, externalSource.toString());

        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }
        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            FindResults results = MAPPER.readValue(webpage, FindResults.class);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie certifications: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }
}
