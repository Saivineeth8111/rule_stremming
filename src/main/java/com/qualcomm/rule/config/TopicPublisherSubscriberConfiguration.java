package com.qualcomm.rule.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.qualcomm.kafka.client.TopicPublisherClientImpl;
import com.qualcomm.kafka.client.TopicSubscriberClientImpl;
import com.qualcomm.kafka.model.GenericDeserializer;
import com.qualcomm.rulesImpl.DeviceRuleEngine.RuleInputDetails;
import com.qualcomm.stream.TopicPublisherClient;
import com.qualcomm.stream.TopicSubscriberClient;
import com.qualcomm.stream.model.TopicConnectionConfig;

/**
 * this class is for configuration of topic
 * 
 */

@Configuration
public class TopicPublisherSubscriberConfiguration {

	@Value("${bootstrap.address}")
	private String bootstrapAddress;

	@Value("${group.id}")
	private String groupId;

	@Value("${topic.name}")
	private String topicName;

	@Value("${maxPollRecords}")
	private String maxPollRecords;

	
	@Bean(name = "ruleEngineTopicSubscriberClient")
	@Scope("prototype")
	public TopicSubscriberClient topicSubscriberClientImpl() {
		Map<String, String> deserializerValueMap = new HashMap<>();
		deserializerValueMap.put(GenericDeserializer.CONFIG_VALUE_CLASS, RuleInputDetails.class.getName());
		TopicConnectionConfig topicConnectionConfig = new TopicConnectionConfig.Builder()
				.bootstrapAddress(bootstrapAddress).groupId(groupId).topicName(topicName).maxPollRecords(maxPollRecords)
				.deserializerValueMap(deserializerValueMap).build();
		return new TopicSubscriberClientImpl<RuleInputDetails>(topicConnectionConfig);
	}
	 

	@Bean(name = "ruleEngineTopicPublisherClient")
	@Scope("prototype")
	public TopicPublisherClient topicPublisherClientImpl() {

		return new TopicPublisherClientImpl();
	}

}
