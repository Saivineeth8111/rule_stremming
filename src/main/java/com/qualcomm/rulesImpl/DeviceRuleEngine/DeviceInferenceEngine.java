package com.qualcomm.rulesImpl.DeviceRuleEngine;

import org.springframework.stereotype.Component;

import com.qualcomm.rule.config.CategoryType;
import com.qualcomm.rule.service.InferenceEngine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeviceInferenceEngine extends InferenceEngine<RuleInputDetails, RuleEngineStatus> {

    @Override
    protected CategoryType getCategoryType() {
        return CategoryType.DEVICE;
    }

    @Override
    protected RuleEngineStatus initializeOutputResult() {
        return RuleEngineStatus.builder().build();
    }
}
