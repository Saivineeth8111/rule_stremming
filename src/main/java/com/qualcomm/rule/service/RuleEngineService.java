/*
 * 
 */
package com.qualcomm.rule.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.base.Enums;
import com.qualcomm.common.enums.DeviceTypeEnum;
import com.qualcomm.rule.Dto.RuleDto;
import com.qualcomm.rule.Dto.NotificationDto;
import com.qualcomm.rule.Entity.Rule;
import com.qualcomm.rule.Repository.RuleRepository;
import com.qualcomm.rule.config.CategoryType;
import com.qualcomm.rule.factory.RuleEngineInferenceFactory;
import com.qualcomm.rulesImpl.DeviceRuleEngine.RuleEngineStatus;
import com.qualcomm.rulesImpl.DeviceRuleEngine.RuleInputDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RuleEngineService {

	@Autowired(required = true)
	private RuleRepository rulesRepository;

	@Autowired
	private RuleEngineInferenceFactory ruleFactory;

	@Autowired
	private RuleEngineProcessorService ruleEngineProcessorService;
	
	public Object run1(RuleInputDetails inputData, List<RuleDto> allRulesByCategory) {

		// TODO: Here for each call, we are fetching all rules from db. It should be
		// cache.
		Object result = ruleFactory.getInferenceEngine(inputData.getDeviceId(), inputData.getRuleType(),
				inputData.getDeviceType(), inputData.getTenantId()).run(allRulesByCategory, inputData);
		log.info("Rule Engine Action : " + result);
		log.info("Rule Engine Action1 : " + inputData);
		
		RuleEngineStatus status = (RuleEngineStatus) result;

		if (status != null && StringUtils.containsAnyIgnoreCase(status.toString(), "true")) {
			log.info("-----------------------inside the result");

			NotificationDto notificationDto = new NotificationDto();
			notificationDto.setTenantId(inputData.getTenantId());
			notificationDto.setDeviceId(inputData.getDeviceId());
			notificationDto.setEventTime(inputData.getEventTime());
			notificationDto.setEventType(status.getNotificationEventType());
			notificationDto.setParams(inputData.getParams());

			ruleEngineProcessorService.publishRuleEngineMessage(notificationDto);
		}

		return result;
	}
	
	
	@Async("asyncRuleExecutor")
	public Object run(RuleInputDetails inputData) {

		// TODO: Here for each call, we are fetching all rules from db. It should be
		// cache.
		List<RuleDto> allRulesByCategory = getAllRuleCategory(inputData.getDeviceId(), inputData.getRuleType().name(),
				inputData.getTenantId(), inputData.getDeviceType());
		System.out.println(allRulesByCategory);
		Object result = ruleFactory.getInferenceEngine(inputData.getDeviceId(), inputData.getRuleType(),
				inputData.getDeviceType(), inputData.getTenantId()).run(allRulesByCategory, inputData);
		log.info("Rule Engine Action : " + result);
		log.info("Rule Engine Action1 : " + inputData);
		
		RuleEngineStatus status = (RuleEngineStatus) result;

		if (status != null && StringUtils.containsAnyIgnoreCase(status.toString(), "true")) {
			log.info("-----------------------inside the result");

			NotificationDto notificationDto = new NotificationDto();
			notificationDto.setTenantId(inputData.getTenantId());
			notificationDto.setDeviceId(inputData.getDeviceId());
			notificationDto.setEventTime(inputData.getEventTime());
			notificationDto.setEventType(status.getNotificationEventType());
			notificationDto.setParams(inputData.getParams());

			ruleEngineProcessorService.publishRuleEngineMessage(notificationDto);
		}

		return result;
	}

	public List<RuleDto> getAllRuleCategory(String deviceId, String category, String tenantId,
			DeviceTypeEnum deviceType) {

		return rulesRepository
				.findByDeviceIdAndCategoryAndTenantIdAndDeviceType(deviceId, category, tenantId, deviceType).stream()
				.map(ruleModel -> mapFromDbModel(ruleModel)).collect(Collectors.toList());
	}

	private RuleDto mapFromDbModel(Rule ruleDbModel) {
		CategoryType category1 = Enums.getIfPresent(CategoryType.class, ruleDbModel.getCategory().toUpperCase())
				.or(CategoryType.DEFAULT);
		return RuleDto.builder().category(category1).condition(ruleDbModel.getCondition())
				.action(ruleDbModel.getAction()).deviceId(ruleDbModel.getDeviceId()).tenantId(ruleDbModel.getTenantId())
				.deviceType(ruleDbModel.getDeviceType()).build();
	}

}
