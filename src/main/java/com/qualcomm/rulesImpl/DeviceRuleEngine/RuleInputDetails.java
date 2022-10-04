package com.qualcomm.rulesImpl.DeviceRuleEngine;

import java.util.List;

import com.qualcomm.common.enums.DeviceTypeEnum;
import com.qualcomm.rule.config.CategoryType;
import com.qualcomm.rule.config.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleInputDetails {
	String deviceId;
	Boolean deviceStatus;
	Float deviceTemp;
	Float devicePresr;
	Float deviceHumid;
	CategoryType ruleType;
	DeviceTypeEnum deviceType;
	String tenantId;
	Long eventTime;
	EventType eventType;
	List<String> params;
}
