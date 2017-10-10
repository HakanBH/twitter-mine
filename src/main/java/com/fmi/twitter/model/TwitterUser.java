package com.fmi.twitter.model;

import twitter4j.User;

import java.io.Serializable;
import java.util.Date;

public class TwitterUser implements Serializable{

    private Long id;
    private String name;
    private String screenName;
    private String location;
    private String description;
    private Integer followersCount;
    private Integer friendsCount;
    private Date createdAt;
    private Integer favouritesCount;
    private Integer statusesCount;
    private String profileImageURL;
    private Boolean verified;

    public TwitterUser(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.screenName = user.getScreenName();
        this.location = user.getLocation();
        this.description = user.getDescription();
        this.followersCount = user.getFollowersCount();
        this.friendsCount = user.getFriendsCount();
        this.createdAt = user.getCreatedAt();
        this.favouritesCount = user.getFavouritesCount();
        this.statusesCount = user.getStatusesCount();
        this.profileImageURL = user.getProfileImageURL();
        this.verified = user.isVerified();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(Integer favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public Integer getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(Integer statusesCount) {
        this.statusesCount = statusesCount;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
