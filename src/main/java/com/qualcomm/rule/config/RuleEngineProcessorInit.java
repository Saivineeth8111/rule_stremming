package com.qualcomm.rule.config;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.qualcomm.stream.TopicSubscriberClient;

/** The Constant log. */
@Slf4j
@Component
public class RuleEngineProcessorInit {

	/** The client. */
	@Autowired
	@Qualifier("ruleEngineTopicSubscriberClient")
	TopicSubscriberClient client;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		this.subscribeTopic();
		log.info("Started subscribing to aware-rule-engine-topic");
	}

	/**
	 * Method to Subscribe the topic. 
	 */
	public void subscribeTopic() {
		try {
			client.subscribe();

		} catch (Exception exception) {
			log.error("Failed to subscribe to the topic", exception);
		}
	}
}
