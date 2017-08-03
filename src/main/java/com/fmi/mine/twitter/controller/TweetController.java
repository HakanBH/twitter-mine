package com.fmi.mine.twitter.controller;

import com.fmi.mine.twitter.service.TweetEntityService;
import com.fmi.mine.twitter.service.TwitterStreamListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.FilterStreamParameters;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController("/")
public class TweetController {

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
    public void stream() throws InterruptedException {
        FilterStreamParameters filterStreamParameters = new FilterStreamParameters();
        filterStreamParameters.language("en");
        filterStreamParameters.addLocation(-180, -90, 180, 90);
        Stream stream =
                twitter.streamingOperations().filter(filterStreamParameters, Arrays.asList(twitterStreamListener));
    }
}
