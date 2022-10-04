package com.qualcomm.rule.controller;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.spark.sql.streaming.StreamingQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.qualcomm.common.dto.GenericSearchDto;
import com.qualcomm.common.dto.ResponseDto;
import com.qualcomm.http.api.controller.GenericHttpController;
import com.qualcomm.rule.Dto.RuleDto;
import com.qualcomm.rule.Entity.Rule;
import com.qualcomm.rule.service.InferenceEngine;
import com.qualcomm.rule.service.RuleEngineService;
import com.qualcomm.rule.service.RuleService;
import com.qualcomm.rule.spark.RuleEngineSparkJob;
import com.qualcomm.rulesImpl.DeviceRuleEngine.RuleInputDetails;
import com.qualcomm.rulesImpl.DeviceRuleEngine.RuleEngineStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/v1/rule")
@CrossOrigin(origins = "*")
public class RuleEngineController extends GenericHttpController<RuleDto, Rule> {

	@Autowired
	private RuleEngineService ruleEngineService;

	@Autowired
	RuleService service;

	@Autowired
	RuleEngineSparkJob sparkjob;
	
	@GetMapping("/testingspark")
	@Operation(summary = "API for getting details of all rules", tags = "Rule Engine")
	@ResponseStatus(HttpStatus.OK)
	public void getTrigger() {
		try {
			sparkjob.sparkjobs(null);
		} catch (StreamingQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@PostMapping("/")
	@Operation(summary = "API for saving rule", tags = "Rule Engine")
	public ResponseEntity<String> createPreference(@RequestBody RuleDto ruleDto) {
		RuleDto dto = service.create(ruleDto);
		if (dto != null) {
			return new ResponseEntity<String>("Rules saved with id : " + dto.getId(), HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("Rules not saved", HttpStatus.BAD_REQUEST);
	}

	@PatchMapping("/")
	@Operation(summary = "API for updating rules", tags = "Rule Engine")
	public ResponseEntity<String> patchPreference(@RequestBody RuleDto ruleDto) {
		RuleDto dto = service.patchById(ruleDto);
		if (dto != null) {
			return new ResponseEntity<String>("Rules updated", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Rules not updated", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/{rule-id}")
	@Operation(summary = "API for getting rule details based on rule id", tags = "Rule Engine")
	@ResponseStatus(HttpStatus.OK)
	public RuleDto getById(@Parameter(description = "unique id") @PathVariable String id) {
		RuleDto masterDto = service.get(id);
		return masterDto;
	}

	@GetMapping("/")
	@Operation(summary = "API for getting details of all rules", tags = "Rule Engine")
	@ResponseStatus(HttpStatus.OK)
	public List<RuleDto> getRules(@ModelAttribute GenericSearchDto searchDto) {
		List<RuleDto> listOfRules = service.list(searchDto);
		return listOfRules;
	}

	@PostMapping(value = "/execution")
	@Operation(summary = "API for aware rule execution", tags = "Rule Engine")
	public ResponseEntity<?> executeRules(@RequestBody RuleInputDetails ruleInputDetails) {
		RuleEngineStatus result = (RuleEngineStatus) ruleEngineService.run(ruleInputDetails);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "This API is not supported", hidden = true)
	public ResponseDto delete(@Parameter(description = "unique id") @PathVariable(value = "id") String id) {
		throw new UnsupportedOperationException();
	}

	@Operation(summary = "This API is not supported", hidden = true)
	public RuleDto get(@Parameter(description = "unique id") @PathVariable(value = "id") String id) {
		throw new UnsupportedOperationException();
	}

	@Operation(summary = "This API is not supported", hidden = true)
	public List<RuleDto> list(
			@Parameter(description = " params are pageSize,pageNo,sortBy,sortOrder") @ModelAttribute GenericSearchDto searchDto) {
		throw new UnsupportedOperationException();
	}

	@Operation(summary = "This API is not supported", hidden = true)
	public RuleDto create(@RequestBody RuleDto entity) {
		throw new UnsupportedOperationException();
	}

	@Operation(summary = "This API is not supported", hidden = true)
	public RuleDto update(@Parameter(description = "unique id") @PathVariable(value = "id") String id,
			@RequestBody RuleDto entity) {
		throw new UnsupportedOperationException();
	}

	@Operation(summary = "This API is not supported", hidden = true)
	public ResponseDto delete() {
		throw new UnsupportedOperationException();
	}

	@Operation(summary = "This API is not supported", hidden = true)
	public long get() {
		throw new UnsupportedOperationException();
	}

	@Operation(summary = "This API is not supported", hidden = true)
	public RuleDto patch(@Parameter(description = "unique id") @PathVariable(value = "id") String id,
			@RequestBody RuleDto entity) {
		throw new UnsupportedOperationException();
	}

}
