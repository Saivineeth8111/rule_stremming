package com.qualcomm.rule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qualcomm.http.api.service.GenericHttpService;
import com.qualcomm.rule.Dto.RuleDto;
import com.qualcomm.rule.Entity.Rule;
import com.qualcomm.rule.Repository.RuleRepository;

@Service
public class RuleService extends GenericHttpService<RuleDto, Rule > {
	
	@Autowired
	private RuleRepository rulerepository;

		public RuleDto patchById(RuleDto ruleDto) {
			RuleDto ruleDto1 = super.get(ruleDto.getId());
			ruleDto1.setCondition(ruleDto.getCondition());
			ruleDto1.setAction(ruleDto.getAction());
			ruleDto1.setCategory(ruleDto.getCategory());
			ruleDto1.setCreatedBy(null);
	        ruleDto1.setCreatedTime(null);

			return super.patch(ruleDto.getId(), ruleDto1);
		}


	
	
}
