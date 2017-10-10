package com.fmi.twitter.service;

import com.fmi.twitter.model.Tweet;
import com.fmi.twitter.repository.TweetEntityRepository;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    @Autowired
    private TweetEntityRepository tweetEntityRepository;

    public Tweet save(Tweet tweet) {
        return tweetEntityRepository.save(tweet);
    }

    public Iterable<Tweet> save(List<Tweet> tweets) {
        return tweetEntityRepository.save(tweets);
    }
}
