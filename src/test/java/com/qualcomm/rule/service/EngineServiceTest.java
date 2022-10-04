package com.qualcomm.rule.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.qualcomm.common.enums.DeviceTypeEnum;
import com.qualcomm.rule.Dto.RuleDto;
import com.qualcomm.rule.Entity.Rule;
import com.qualcomm.rule.Repository.RuleRepository;
import com.qualcomm.rule.config.CategoryType;
import com.qualcomm.rule.config.EventType;
import com.qualcomm.rule.factory.RuleEngineInferenceFactory;
import com.qualcomm.rule.parser.RuleParser;
import com.qualcomm.rulesImpl.DeviceRuleEngine.DeviceInferenceEngine;
import com.qualcomm.rulesImpl.DeviceRuleEngine.RuleEngineStatus;
import com.qualcomm.rulesImpl.DeviceRuleEngine.RuleInputDetails;

@ExtendWith(MockitoExtension.class)
class EngineServiceTest {

	@InjectMocks
	RuleEngineService ruleEngineService;

	@Mock
	RuleRepository rulesRepository;

	@Mock
	RuleEngineInferenceFactory ruleFactory;

	@Mock
	RuleEngineProcessorService ruleEngineProcessorService;

	@Mock
	InferenceEngine inferenceEngine;

	@Mock
	RuleParser ruleParser;

	@Mock
	DeviceInferenceEngine deviceInferenceEngine;

	@Test
	void testRun() {
		CategoryType type = CategoryType.DEVICE;
		List<RuleDto> listDto = new ArrayList<RuleDto>();
		RuleDto ruleDto = new RuleDto();
		ruleDto.setAction("output.setRuleEngineSatus(true)");
		ruleDto.setCategory(CategoryType.DEVICE);
		ruleDto.setCondition("CONDITIONBREACH");
		ruleDto.setCreatedBy("SYSTEM");
		ruleDto.setCreatedTime(123456789L);
		ruleDto.setDeviceId("a666-f299-8");
		ruleDto.setDeviceType(DeviceTypeEnum.LABEL);
		ruleDto.setId("a12345-gr536354-3455h");
		ruleDto.setTenantId("811");
		ruleDto.setUpdatedBy("SYSTEM");
		ruleDto.setUpdatedTime(1678432178l);
		listDto.add(ruleDto);

		List<Rule> ruleList1 = new ArrayList<>();
		Rule rule1 = new Rule();
		rule1.setAction("Action");
		rule1.setCategory(type.name());
		rule1.setCondition("Condition");
		rule1.setCreatedBy("System");
		rule1.setCreatedTime(12346L);
		rule1.setId("123-rwer-456");
		rule1.setUpdatedBy("System");
		rule1.setUpdatedTime(34579L);
		rule1.setDeviceId("1234-device-56");
		rule1.setDeviceType(DeviceTypeEnum.TRACKER);
		rule1.setTenantId("123-tenant-456");
		ruleList1.add(rule1);

		RuleInputDetails ruleInputDetails = new RuleInputDetails();
		ruleInputDetails.setDeviceHumid(-10F);
		ruleInputDetails.setDeviceId("a666-b2958c3ee8f8");
		ruleInputDetails.setDevicePresr(900F);
		ruleInputDetails.setDeviceStatus(true);
		ruleInputDetails.setDeviceTemp(45F);
		ruleInputDetails.setDeviceType(DeviceTypeEnum.TRACKER);
		ruleInputDetails.setEventTime(12389L);
		ruleInputDetails.setEventType(EventType.CONDITIONBREACH);
		ruleInputDetails.setRuleType(CategoryType.DEVICE);
		ruleInputDetails.setTenantId("811");

		RuleEngineStatus ruleEngineStatus = new RuleEngineStatus();
		ruleEngineStatus.setNotificationEventType(EventType.CONDITIONBREACH.name());
		ruleEngineStatus.setRuleEngineStatus(true);

		when(rulesRepository.findByDeviceIdAndCategoryAndTenantIdAndDeviceType(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(ruleList1);
		when(ruleEngineService.getAllRuleCategory(ruleDto.getDeviceId(), ruleDto.getCategory().toString(),
				ruleDto.getTenantId(), ruleDto.getDeviceType())).thenReturn(listDto);
		when(ruleParser.parseAction(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(ruleEngineStatus);
		when(inferenceEngine.run(Mockito.any(), Mockito.any())).thenReturn(ruleEngineStatus);
		when(ruleFactory.getInferenceEngine(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(inferenceEngine);
		ruleEngineService.run(ruleInputDetails);
	}

}
