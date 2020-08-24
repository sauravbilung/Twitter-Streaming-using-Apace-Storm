package com.Twitter;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import twitter4j.Status;

public class ExtractStatusBolt extends BaseBasicBolt{

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		// Gets the tweets from the spout for further processing
		
		Status status=(Status)input.getValueByField("tweet");
		String tweetText=status.getText();
		collector.emit(new Values(tweetText));		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tweet"));		
	}

}
