package com.fmi.twitter.mining.controller;

import com.fmi.twitter.mining.util.TupleComparator;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import scala.Tuple2;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private JavaStreamingContext jsc;

    @GetMapping("/trending")
    public List<Tuple2<String, Integer>> getTrendingTags() {
        JavaPairRDD<String, Map<String, Object>> esRDD = JavaEsSpark.esRDD(jsc.sparkContext(), "twitter/tweet");
        JavaPairRDD<String, Integer> counts = esRDD
                .map(tweet -> (String) tweet._2().get("text"))
                .flatMap(text -> Arrays.asList(text.split(" ")).iterator())
                .filter(word -> word.startsWith("#"))
                .mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2<>(s, 1))
                .reduceByKey((Function2<Integer, Integer, Integer>) (x, y) -> x + y);

        return counts.takeOrdered(100, TupleComparator.getInstance());
    }

    @GetMapping("/sentiment")
    public void sentimentAnalysis(){

    }


}
