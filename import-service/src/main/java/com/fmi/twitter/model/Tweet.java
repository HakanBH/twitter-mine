package com.fmi.twitter.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import twitter4j.Status;
import twitter4j.User;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = Tweet.INDEX_NAME, type = "tweet", shards = 2)
@Getter
@Setter
public class Tweet implements Serializable {

    public static final String INDEX_NAME = "twitter";

    @Id
    private Long id;

    @Field(type = FieldType.String)
    private String text;
    @Field(type = FieldType.Date)
    private Date createdAt;
    @Field(type = FieldType.Long)
    private Long inReplyToStatusId;
    @Field(type = FieldType.Long)
    private Long inReplyToUserId;
    @Field(type = FieldType.String)
    private String inReplyToScreenName;
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
    private User user;

    public Tweet(Status status) {
        this.id = status.getId();
        this.text = status.getText();
        this.createdAt = status.getCreatedAt();
        this.inReplyToStatusId = status.getInReplyToStatusId();
        this.inReplyToUserId = status.getInReplyToUserId();
        this.inReplyToScreenName = status.getInReplyToScreenName();
        this.languageCode = status.getLang();
        this.source = status.getSource();
        this.retweeted = status.isRetweeted();
        this.retweetCount = status.getRetweetCount();
        this.favorited = status.isFavorited();
        this.favoriteCount = status.getFavoriteCount();
        this.user = status.getUser();
    }
}
