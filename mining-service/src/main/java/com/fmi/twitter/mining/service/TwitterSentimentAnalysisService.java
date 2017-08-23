package com.fmi.twitter.mining.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitterSentimentAnalysisService {

    /**
     * Strips the extra characters in tweets. And also removes stop words from the tweet text.
     *
     * @param tweetText -- Complete text of a tweet.
     * @param stopWords -- List of stop words to be removed from the tweets.
     * @return List<String> after removing additional characters and stop words from the tweet.
     */
    public List<String> removeStopWords(String tweetText, List<String> stopWords) {
        List<String> cleanedTweets =
                Arrays.asList(tweetText.toLowerCase()
                                       .replaceAll("\n", "")
                                       .replaceAll("rt\\s+", "")
                                       .replaceAll("\\s+@\\w+", "")
                                       .replaceAll("@\\w+", "")
                                       .replaceAll("\\s+#\\w+", "")
                                       .replaceAll("#\\w+", "")
                                       .replaceAll("(?:https?|http?)://[\\w/%.-]+", "")
                                       .replaceAll("(?:https?|http?)://[\\w/%.-]+\\s+", "")
                                       .replaceAll("(?:https?|http?)//[\\w/%.-]+\\s+", "")
                                       .replaceAll("(?:https?|http?)//[\\w/%.-]+", "")
                                       .split("\\W+"));

        return cleanedTweets.stream()
                            .filter(s -> s.matches("^[a-zA-Z]+$"))
                            .filter(s -> !stopWords.contains(s))
                            .collect(Collectors.toList());
    }
}
