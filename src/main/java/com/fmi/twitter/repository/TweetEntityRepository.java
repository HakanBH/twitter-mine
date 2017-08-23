package com.fmi.twitter.repository;

import com.fmi.twitter.model.Tweet;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetEntityRepository extends ElasticsearchCrudRepository<Tweet, Long> {

}
