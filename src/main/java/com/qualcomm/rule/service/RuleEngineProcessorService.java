package com.qualcomm.rule.service;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeoutException;

import org.apache.spark.sql.streaming.StreamingQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.qualcomm.rule.Dto.NotificationDto;
import com.qualcomm.rule.spark.RuleEngineSparkJob;
import com.qualcomm.rulesImpl.DeviceRuleEngine.RuleInputDetails;
import com.qualcomm.stream.StreamMessageProcessor;
import com.qualcomm.stream.TopicPublishResult;
import com.qualcomm.stream.TopicPublisherClient;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class TrackerMessageProcessor processes the sensorTrackingDto
 */
@Slf4j
@Service
public class RuleEngineProcessorService implements StreamMessageProcessor<RuleInputDetails> {

	@Autowired
	@Qualifier("ruleEngineTopicPublisherClient")
	private TopicPublisherClient topicPublisherClient;
	
	@Autowired
	RuleEngineSparkJob sparkJob;
	

	@Autowired
	private RuleEngineService ruleEngineService;

	@SuppressWarnings("unchecked")
	public void publishRuleEngineMessage(NotificationDto notificationDto) {
		CompletionStage<TopicPublishResult> publishResultFuture = (CompletionStage<TopicPublishResult>) topicPublisherClient
				.publish(notificationDto, "aware-notification-topic", "1e3b1801-373b-41b6-9124-43a42c3953f8");
		publishResultFuture.whenComplete((publishResult, e) -> {
			if (publishResult == null) {
				log.error("Fatal Exception occured: ", e);
			} else {
				log.info("publishResult" + publishResult);
			}
		});
	}

	@Override
	public void processMessage(List<RuleInputDetails> ruleInputDetails) {

		log.info("RuleInputDetails size :-------- " + ruleInputDetails.size());
		try {
			sparkJob.sparkjobs(ruleInputDetails);
		} catch (StreamingQueryException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * ruleInputDetails.parallelStream().forEach(ruleInputs -> {
		 * log.info("Message received in NotificationMessageProcesser :-------- " +
		 * ruleInputs); if ((ruleInputs != null)) { ruleEngineService.run(ruleInputs); }
		 * else {
		 * log.error("processMessage in ProcessService : Invalid rule received {} ",
		 * ruleInputs); }
		 * 
		 * });
		 */
	}

}
