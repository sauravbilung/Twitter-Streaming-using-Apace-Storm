package com.Twitter;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Hello world!
 *
 */
public class TopologyMain {
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("tweets-collector", new TwitterSpout());
		builder.setBolt("text-extractor", new ExtractStatusBolt()).shuffleGrouping("tweets-collector");
		
		LocalCluster cluster=new LocalCluster();
		Config conf=new Config();
		conf.setDebug(true);
		cluster.submitTopology("twitter-direct", conf, builder.createTopology());
		
		cluster.shutdown();
	}
}
