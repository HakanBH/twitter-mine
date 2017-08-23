package com.fmi.twitter.importer.service;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

@Service
public class TwitterStreamListener implements StreamListener {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(TwitterStreamListener.class);

    @Autowired
    private TweetEntityService tweetEntityService;

    @Override
    public void onTweet(Tweet tweet) {
        LOGGER.info("Tweet received: " + tweet.getId());
        tweetEntityService.save(tweet);
    }

    @Override
    public void onDelete(StreamDeleteEvent streamDeleteEvent) {
        LOGGER.info("Delete event for tweet: " + streamDeleteEvent.getTweetId());
    }

    @Override
    public void onLimit(int i) {
        LOGGER.info("Limited: " + i);
    }

    @Override
    public void onWarning(StreamWarningEvent streamWarningEvent) {
        LOGGER.warn("WARNING: " + streamWarningEvent.getMessage());
    }
}
