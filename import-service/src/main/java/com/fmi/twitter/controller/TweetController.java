package com.fmi.twitter.controller;

import com.fmi.twitter.model.Tweet;
import com.fmi.twitter.service.AnalyticsService;
import com.fmi.twitter.service.TweetService;
import com.fmi.twitter.utils.FileWriterUtils;
import com.fmi.twitter.utils.SentimentUtils;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scala.Tuple2;
import twitter4j.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/")
public class TweetController {

    @Autowired
    private Twitter twitter;

    @Autowired
    private JavaStreamingContext jssc;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/stream")
    public void stream(@RequestParam("lang") String lang)
            throws InterruptedException {
        JavaReceiverInputDStream<Status> stream = TwitterUtils.createStream(jssc);

//        JavaDStream<Tweet> tweetJavaDStream = stream
//                .filter(status -> status.getLang().equalsIgnoreCase(lang))
//                .map(status -> new Tweet(status));
//
//        analyticsService.trendingHashtags(tweetJavaDStream);
//        tweetService.saveToElasticsearch(tweetJavaDStream);

        JavaDStream<String> tweets = stream
                .filter(status -> status.getLang().equalsIgnoreCase(lang))
                .map(status -> status.getText())
                .map(tweetText -> SentimentUtils.sanitizeTweet(tweetText));

        JavaPairDStream<String, Double> tweetWithScoreDStream =
                tweets.mapToPair(tweetText -> new Tuple2<>(tweetText, Double.valueOf(SentimentUtils.calculateWeightedSentimentScore(tweetText))));

        tweetWithScoreDStream.foreachRDD(rdd -> {
            FileWriterUtils.appendFile(rdd.toDebugString());
            return null;
        });

        jssc.start();
        jssc.awaitTermination();
    }

    @GetMapping("/search")
    public List<Tweet> search(
            @RequestParam(value = "q") String text,
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "geo", required = false) String geo,
            @RequestParam(value = "radius", required = false) Double radius) throws TwitterException {

        Query query = new Query(text);

        if (!StringUtils.isEmpty(lang)) {
            query.lang(lang);
        }
        if (!StringUtils.isEmpty(geo)) {
            String[] geoCode = geo.split(",");
            query.geoCode(new GeoLocation(Double.valueOf(geoCode[0]), Double.valueOf(geoCode[1])), radius, String.valueOf(Query.Unit.km));
        }

        QueryResult result = twitter.search(query);

        return result.getTweets().stream()
                .map(status -> new Tweet(status))
                .collect(Collectors.toList());
    }
}
