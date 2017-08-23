package com.fmi.twitter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
@PropertySource("twitter4j.properties")
public class TwitterConfig {
    @Value("${oauth.consumerKey}")
    private String consumerKey;

    @Value("${oauth.consumerSecret}")
    private String consumerSecret;

    @Value("${oauth.accessToken}")
    private String accessToken;

    @Value("${oauth.accessTokenSecret}")
    private String accessTokenSecret;

    @Bean
    public twitter4j.conf.Configuration getTwitterConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        return cb.build();
    }

    @Bean
    public Twitter getTwitter(twitter4j.conf.Configuration twitterConfig) {
        TwitterFactory tf = new TwitterFactory(twitterConfig);
        return tf.getInstance();
    }

    @Bean
    public TwitterStream getTwitterStream(twitter4j.conf.Configuration twitterConfig) {
        TwitterStreamFactory tsf = new TwitterStreamFactory(twitterConfig);
        return tsf.getInstance();
    }
}
