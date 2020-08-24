package com.Twitter;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSpout extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private TwitterStream twitterStream;
	private LinkedBlockingQueue<Status> queue;

	@Override
	public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context, SpoutOutputCollector collector) {

		this.collector = collector;
		ConfigurationBuilder configurationbuilder = new ConfigurationBuilder();
		configurationbuilder.setDebugEnabled(true).setOAuthConsumerKey("uTc5sNyxtdfNO7XNKSkA1p7p0")
				.setOAuthConsumerSecret("nWcuexlfQF2sgRSuqIC6H6ntLjeB0R2EMXFqww5wWTLXBbG7Tx")
				.setOAuthAccessToken("177237335-92KvhFTuLa3qOnFP54WCRoNJGkopTKPJeEb9hF0S")
				.setOAuthAccessTokenSecret("fbfxtGpp9bF7owVWy2ViKqSDRHpUM4eDklh4H7JfrmYJH");

		// Instantiating a twitter stream
		this.twitterStream = new TwitterStreamFactory(configurationbuilder.build()).getInstance();
		// incoming tweets will be stored here for the spout to be read and emitted to
		// the next component. In this project this queue is polled by Apache Storm
		// for the tweets.
		this.queue = new LinkedBlockingQueue<Status>();

		// Tweets will be added to this queue using the status listener
		// We are creating a status listener which will listen the twitter stream

		final StatusListener listener = new StatusListener() {

			@Override
			public void onException(Exception ex) {
			}

			@Override
			public void onStatus(Status status) {

				// Adds the new element to the end of the queue
				queue.offer(status);
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
			}

			@Override
			public void onStallWarning(StallWarning warning) {
			}

		};

		// Registering the listener with the twitter stream
		twitterStream.addListener(listener);
		// We do not want to track all the tweets. We are only tracking the tweets that
		// contains a specific word or term.
		final FilterQuery query = new FilterQuery();
		query.track(new String[] { "chocolate" });
		twitterStream.filter(query);

	}

	@Override
	public void nextTuple() {

		// Here the tweet is polled and is forwarded to the next component.

		final Status status = queue.poll();

		if (status == null) {
			Utils.sleep(100);
		} else {
			collector.emit(new Values(status));
		}

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tweet"));
	}

	@Override
	public void close() {
		// It is run when the spout is shutdown
		twitterStream.shutdown();
	}

	
}
