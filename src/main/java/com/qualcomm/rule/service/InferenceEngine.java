package com.qualcomm.rule.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qualcomm.rule.Dto.RuleDto;
import com.qualcomm.rule.config.CategoryType;
import com.qualcomm.rule.models.RuleModel;
import com.qualcomm.rule.parser.RuleParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public abstract class InferenceEngine<INPUT_DATA, OUTPUT_RESULT> {

	@Autowired
	private RuleParser<INPUT_DATA, OUTPUT_RESULT> ruleParser;

	/**
	 * Run inference engine on set of rules for given data.
	 * 
	 * @param listOfRules
	 * @param inputData
	 * @return
	 */
	public OUTPUT_RESULT run(List<RuleDto> listOfRules, INPUT_DATA inputData) {
		if (null == listOfRules || listOfRules.isEmpty()) {
			return null;
		}

		// STEP 1 (MATCH) : Match the facts and data against the set of rules.
		List<RuleDto> conflictSet = match(listOfRules, inputData);

		// STEP 2 (RESOLVE) : Resolve the conflict and give the selected one rule.
		RuleDto resolvedRule = resolve(conflictSet);
		if (null == resolvedRule) {
			return null;
		}

		// STEP 3 (EXECUTE) : Run the action of the selected rule on given data and
		// return the output.
		OUTPUT_RESULT outputResult = executeRule(resolvedRule, inputData);

		return outputResult;
	}

	/**
	 * We can use here any pattern matching algo: 1. Rete 2. Linear 3. Treat 4.
	 * Leaps
	 *
	 * Here we are using Linear matching algorithm for pattern matching.
	 * 
	 * @param listOfRules
	 * @param inputData
	 * @return
	 */
	protected List<RuleDto> match(List<RuleDto> listOfRules, INPUT_DATA inputData) {
		return listOfRules.stream().filter(rule -> {
			String condition = rule.getCondition();
			return ruleParser.parseCondition(condition, inputData);
		}).collect(Collectors.toList());
	}

	/**
	 * We can use here any resolving techniques: 1. Lex 2. Recency 3. MEA 4.
	 * Refactor 5. Priority wise
	 *
	 * Here we are using find first rule logic.
	 * 
	 * @param conflictSet
	 * @return
	 */
	protected RuleDto resolve(List<RuleDto> conflictSet) {
		Optional<RuleDto> rule = conflictSet.stream().findFirst();
		if (rule.isPresent()) {
			return rule.get();
		}
		return null;
	}

	/**
	 * Execute selected rule on input data.
	 * 
	 * @param rule
	 * @param inputData
	 * @return
	 */
	protected OUTPUT_RESULT executeRule(RuleDto rule, INPUT_DATA inputData) {
		OUTPUT_RESULT outputResult = initializeOutputResult();
		return ruleParser.parseAction(rule.getAction(), inputData, outputResult);
	}

	protected abstract OUTPUT_RESULT initializeOutputResult();

	protected abstract CategoryType getCategoryType();
}
