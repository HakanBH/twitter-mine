package com.fmi.mine.twitter.controller;

import com.fmi.mine.twitter.service.TweetEntityService;
import com.fmi.mine.twitter.service.TwitterStreamListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.FilterStreamParameters;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController("/")
public class TweetController {

    private static final Integer SECONDS_TO_MILLIS_MULTIPLIER = 1000;
    private TwitterStreamListener twitterStreamListener;
    private Twitter twitter;
    private TweetEntityService tweetEntityService;

    @Autowired
    public TweetController(
            TweetEntityService tweetEntityService, TwitterStreamListener twitterStreamListener, Twitter twitter) {
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
}
