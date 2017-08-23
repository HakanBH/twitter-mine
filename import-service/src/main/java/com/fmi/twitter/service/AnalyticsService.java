package com.fmi.twitter.service;

import com.fmi.twitter.model.Tweet;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

import static com.fmi.twitter.utils.FileWriterUtils.appendFile;

@Service
public class AnalyticsService {

    public void trendingHashtags(JavaDStream<Tweet> stream) {
        JavaDStream<String> hashTagRDD = stream
                .flatMap(status -> Arrays.asList(status.getText().split(" ")))
                .filter(line -> line.startsWith("#"))
                .map(line -> line.trim());

        JavaPairDStream<Integer, String> topicCounts60RDD = hashTagRDD
                .mapToPair(hashTag -> new Tuple2<>(hashTag, 1))
                .reduceByKeyAndWindow((integer1, integer2) -> (integer1 + integer2), Durations.seconds(3600))
                .mapToPair(tuple -> tuple.swap())
                .transformToPair(integerStringJavaPairRDD -> integerStringJavaPairRDD.sortByKey(false));

        topicCounts60RDD.foreachRDD(pairRDD -> {
            List<Tuple2<Integer, String>> top10Topics = pairRDD.take(10); // get Top 10.
            top10Topics.forEach(tuple -> System.err.println(String.format("%s, (%d tweets)", tuple._2(), tuple._1())));
            appendFile(String.valueOf(top10Topics));
            return null;
        });
    }
}
