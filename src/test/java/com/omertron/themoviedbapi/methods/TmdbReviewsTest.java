/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TestLogger;
import static com.omertron.themoviedbapi.TheMovieDbApiTest.API_KEY;
import com.omertron.themoviedbapi.model.Review;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

/**
 *
 * @author stuart.boston
 */
public class TmdbReviewsTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TmdbAuthenticationTest.class);
    // API
    private static TmdbReviews instance;

    public TmdbReviewsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        instance = new TmdbReviews(API_KEY, new DefaultPoolingHttpClient());
        TestLogger.Configure();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getReview method, of class TmdbReviews.
     *
     * @throws com.omertron.themoviedbapi.MovieDbException
     */
    @Test
    public void testGetReview() throws MovieDbException {
        LOG.info("getReview");
        String reviewId = "5013bc76760ee372cb00253e";
        Review result = instance.getReview(reviewId);
        LOG.info(result.toString());
        assertTrue("No Author", StringUtils.isNotBlank(result.getAuthor()));
        assertTrue("No Content", StringUtils.isNotBlank(result.getContent()));
    }

}
