package com.fmi.twitter.importer.controller;

import com.fmi.twitter.importer.service.TweetEntityService;
import com.fmi.twitter.importer.service.TwitterStreamListener;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.FilterStreamParameters;
import org.springframework.social.twitter.api.GeoCode;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Arrays;
import java.util.List;

@RestController("/")
public class TweetController {

    private static final Integer SECONDS_TO_MILLIS_MULTIPLIER = 1000;
    private TwitterStreamListener twitterStreamListener;
    private TweetEntityService tweetEntityService;
    private Twitter twitter;

    @Autowired
    public TweetController(
            Twitter twitter, TweetEntityService tweetEntityService, TwitterStreamListener twitterStreamListener) {
        this.twitter = twitter;
        this.tweetEntityService = tweetEntityService;
        this.twitterStreamListener = twitterStreamListener;
    }

    @GetMapping("/stream")
    public void stream(@RequestParam("language") String language, @RequestParam("duration") Integer duration)
            throws InterruptedException {
        FilterStreamParameters filterStreamParameters = new FilterStreamParameters();
        filterStreamParameters.language(language);
        filterStreamParameters.addLocation(-180, -90, 180, 90);
        Stream stream =
                twitter.streamingOperations().filter(filterStreamParameters, Arrays.asList(twitterStreamListener));
        Thread.sleep(duration * SECONDS_TO_MILLIS_MULTIPLIER);
        stream.close();
    }

    @GetMapping("/search")
    public List<Tweet> search(
            @RequestParam(value = "q") String query,
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "geo", required = false) String geo) {

        SearchParameters searchParameters = new SearchParameters(query);
        if (!StringUtils.isEmpty(lang)) {
            searchParameters.lang(lang);
        }
        if (!StringUtils.isEmpty(geo)) {
            String[] geoCode = geo.split(",");
            searchParameters.geoCode(
                    new GeoCode(Double.valueOf(geoCode[0]), Double.valueOf(geoCode[1]), Integer.valueOf(geoCode[2])));
        }

        SearchResults results = twitter.searchOperations().search(searchParameters);

        return results.getTweets();
    }

}
