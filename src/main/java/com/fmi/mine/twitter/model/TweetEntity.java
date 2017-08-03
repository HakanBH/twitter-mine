package com.fmi.mine.twitter.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.social.twitter.api.Tweet;

import java.util.Date;

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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public void setInReplyToStatusId(Long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public Long getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(Long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getRetweeted() {
        return retweeted;
    }

    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
}
