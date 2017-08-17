package com.fmi.twitter.importer.repository;

import com.fmi.twitter.importer.model.TweetEntity;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetEntityRepository extends ElasticsearchCrudRepository<TweetEntity, String> {

}
