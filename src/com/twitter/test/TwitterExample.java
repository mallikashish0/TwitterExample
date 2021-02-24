package com.twitter.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterExample {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException, TwitterException {

		System.out.println("Please enter the tweet:");
		String tweets = sc.nextLine();
		TwitterExample example = new TwitterExample();

		
		List<String> hashTags = example.getHashTagFromSentence(tweets);
		Twitter twitter = example.InitializeConfiguration();
		List<Status> hashTagResult = null;
		for(String hashTag:hashTags) {
			hashTagResult = twitter.search(new Query(hashTag)).getTweets();
			System.out.println("Count of "+hashTag+" is :"+hashTagResult.size());
		}

        Trends trends = twitter.getPlaceTrends(1);
        int count = 0;
        for (Trend trend : trends.getTrends()) {
            if (count < 10) {
                System.out.println(trend.getName());
                count++;
            }
        }
	}

	public List<String> getHashTagFromSentence(String sentence) {
		Pattern pattern = Pattern.compile("#[a-zA-Z0-9]+");
		Matcher matcher = pattern.matcher(sentence);
		List<String> hashTags = new ArrayList<String>();
		while(matcher.find()) {
			hashTags.add(matcher.group());
		}
		return hashTags;
	}
	
	public Twitter InitializeConfiguration() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("API-CONSUMER-KEY")
				.setOAuthConsumerSecret("API-CONSUMER-SECRET")
				.setOAuthAccessToken("ACCESS-TOKEN")
				.setOAuthAccessTokenSecret("ACCESS-SECRET");
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}
}
