package com.fmi.mine.twitter.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = TweetEntity.INDEX_NAME, type = "tweet", shards = 2)
public class TweetEntity {

    public static final String INDEX_NAME = "tweets";

    @Id
    private String id;

    @Field(type = FieldType.String)
    private String text;
    @Field(type = FieldType.Date)
    private final Date createdAt;
    @Field(type = FieldType.String)
    private String fromUser;
    @Field(type = FieldType.String)
    private String profileImageUrl;
    @Field(type = FieldType.Long)
    private Long toUserId;
    @Field(type = FieldType.Long)
    private Long inReplyToStatusId;
    @Field(type = FieldType.Long)
    private Long inReplyToUserId;
    @Field(type = FieldType.String)
    private String inReplyToScreenName;
    @Field(type = FieldType.Long)
    private Long fromUserId;
    @Field(type = FieldType.String)
    private String languageCode;
    @Field(type = FieldType.String)
    private String source;
    @Field(type = FieldType.Boolean)
    private Boolean retweeted;
    @Field(type = FieldType.Integer)
    private Integer retweetCount;
    @Field(type = FieldType.Boolean)
    private Boolean favorited;
    @Field(type = FieldType.Integer)
    private Integer favoriteCount;
    @Field(type = FieldType.Nested)
    private TwitterProfile user;

    public TweetEntity(Tweet tweet) {
        this.id = tweet.getId();
        this.text = tweet.getText();
        this.createdAt = tweet.getCreatedAt();
        this.fromUser = tweet.getFromUser();
        this.profileImageUrl = tweet.getProfileImageUrl();
        this.toUserId = tweet.getToUserId();
        this.inReplyToStatusId = tweet.getInReplyToStatusId();
        this.inReplyToUserId = tweet.getInReplyToUserId();
        this.inReplyToScreenName = tweet.getInReplyToScreenName();
        this.fromUserId = tweet.getFromUserId();
        this.languageCode = tweet.getLanguageCode();
        this.source = tweet.getSource();
        this.retweeted = tweet.isRetweeted();
        this.retweetCount = tweet.getRetweetCount();
        this.favorited = tweet.isFavorited();
        this.favoriteCount = tweet.getFavoriteCount();
        this.user = tweet.getUser();
    }
}
