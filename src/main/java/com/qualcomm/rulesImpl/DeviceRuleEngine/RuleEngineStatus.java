package com.qualcomm.rulesImpl.DeviceRuleEngine;

import com.qualcomm.rule.config.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleEngineStatus {
	
	Boolean ruleEngineStatus;
	
	String notificationEventType;

}
