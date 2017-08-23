package com.fmi.twitter.mining.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Value("${apache.spark.appname}")
    private String appName;
    @Value("${apache.spark.master}")
    private String master;

    @Bean
    public SparkConf sparkConf() {
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        conf.set("es.index.auto.create", "true");
        conf.set("es.resource", "twitter/tweet");
        return conf;
    }

    @Bean
    public JavaStreamingContext javaStreamingContext(SparkConf sparkConf) {
        return new JavaStreamingContext(sparkConf, new Duration(1000));
    }
}
