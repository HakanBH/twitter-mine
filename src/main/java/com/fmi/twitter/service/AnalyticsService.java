package com.fmi.twitter.service;

import com.fmi.twitter.model.Tweet;
import com.fmi.twitter.utils.SentimentUtils;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.fmi.twitter.utils.FileWriterUtils.appendFile;

@Service
public class AnalyticsService {

    public final static String SENTIMENT_ANALYSIS_OUTPUT_FILE = "out/sentimentAnalysis.txt";
    public final static String TRENDING_HASHTAGS_OUTPUT_FILE = "out/trendingHashtags.txt";

    public void sentimentAnalysis(JavaDStream<Tweet> stream) {
        JavaDStream<String> tweets = stream
                .map(status -> status.getText())
                .map(tweetText -> SentimentUtils.sanitizeTweet(tweetText));

        JavaPairDStream<String, Double> tweetWithScoreDStream =
                tweets.mapToPair(tweetText -> new Tuple2<>(tweetText, Double.valueOf(SentimentUtils.calculateWeightedSentimentScore(tweetText))));

        tweetWithScoreDStream.foreachRDD(rdd -> {
            appendFile(rdd.collect(), SENTIMENT_ANALYSIS_OUTPUT_FILE);
            return null;
        });
    }

    public void trendingHashtags(JavaDStream<Tweet> stream) {
        JavaDStream<String> hashTagRDD = stream
                .flatMap(status -> Arrays.asList(status.getText().split(" ")))
                .filter(line -> line.startsWith("#"))
                .map(line -> line.trim());

        JavaPairDStream<Integer, String> topicCounts60RDD = hashTagRDD
                .mapToPair(hashTag -> new Tuple2<>(hashTag, 1))
                .reduceByKeyAndWindow((integer1, integer2) -> (integer1 + integer2), Durations.seconds(3600), Durations.seconds(10))
                .mapToPair(tuple -> tuple.swap())
                .transformToPair(integerStringJavaPairRDD -> integerStringJavaPairRDD.sortByKey(false));

        topicCounts60RDD.foreachRDD(pairRDD -> {
            List<Tuple2<Integer, String>> top10Topics = pairRDD.take(10); // get Top 10.
            appendFile(String.valueOf(new Date()), TRENDING_HASHTAGS_OUTPUT_FILE);
            appendFile(String.valueOf(top10Topics), TRENDING_HASHTAGS_OUTPUT_FILE);
            return null;
        });
    }
}
