package com.qualcomm.rule.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qualcomm.common.enums.DeviceTypeEnum;
import com.qualcomm.rule.config.CategoryType;
import com.qualcomm.rule.service.InferenceEngine;
import com.qualcomm.rulesImpl.DeviceRuleEngine.DeviceInferenceEngine;

@Component
public class RuleEngineInferenceFactory {

	@Autowired
	private DeviceInferenceEngine deviceInferenceEngine;

	/**
	 * The method will invoke the specific inference engine based on the type.
	 * 
	 * @param CategoryType as type
	 * @return InferenceEngine
	 */
	public InferenceEngine getInferenceEngine(String deviceId, CategoryType type, DeviceTypeEnum deviceType,
			String tenantId) {
		switch (type) {
		case DEVICE:
			return deviceInferenceEngine;
		default:
			return null;
		}
	}

}
