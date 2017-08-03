package com.fmi.mine.twitter.repository;

import com.fmi.mine.twitter.model.TweetEntity;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetEntityRepository extends ElasticsearchCrudRepository<TweetEntity, String> {

}
