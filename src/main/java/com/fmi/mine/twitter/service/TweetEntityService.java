package com.fmi.mine.twitter.service;

import com.fmi.mine.twitter.model.TweetEntity;
import com.fmi.mine.twitter.repository.TweetEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

@Service
public class TweetEntityService {

    @Autowired
    private TweetEntityRepository tweetEntityRepository;

    public TweetEntity save(Tweet tweet) {
        TweetEntity tweetEntity = new TweetEntity(tweet);
        return tweetEntityRepository.save(tweetEntity);
    }
}
