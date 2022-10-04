package com.qualcomm.rule.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import com.qualcomm.http.api.dao.BaseSqlDao;
import com.qualcomm.http.api.service.GenericHttpService;
import com.qualcomm.http.api.validator.BaseValidator;
import com.qualcomm.rule.Dto.RuleDto;
import com.qualcomm.rule.Entity.Rule;
@ExtendWith(MockitoExtension.class)
class RuleServiceTest {
	
	
	
	@InjectMocks
    RuleService ruleService ;

   @InjectMocks
    GenericHttpService<RuleDto, Rule> genericHttpService;
   
   @Mock
   BaseSqlDao<Rule> baseSqlDao;
   @Mock
   BaseValidator<?> baseValidator;
   
   @BeforeEach
   public void setUp() throws Exception {
       MockitoAnnotations.openMocks(this);
       ReflectionTestUtils.setField(ruleService , "modelMapper", new ModelMapper());
   }
   

	@Test
	void patchByIdtest() {
		RuleDto dto=new RuleDto();
		dto.setId("7e54af7d-02e1-49e6-9589-96b50b40b7d7");
//		dto.setCreatedBy("SYSTEM");
//		dto.setUpdatedBy("SYSTEM");
//		dto.setCreatedTime(11313131L);
//		dto.setUpdatedTime(31231331L);;
		dto.setCondition("condition");
		dto.setAction("action");
//		dto.setCategory("category");
		
		Rule rule= new Rule();
		rule.setId("7e54af7d-02e1-49e6-9589-96b50b40b7d7");
		rule.setCreatedBy("SYSTEM");
		rule.setUpdatedBy("SYSTEM");
		rule.setCreatedTime(11313131L);
		rule.setUpdatedTime(31231331L);;
		rule.setCondition("condition2");
		rule.setAction("action2");
//		rule.setCategory("category2");
		
		Optional<Rule> ruleOptional = Optional.ofNullable(rule);
        when(baseSqlDao.findById(Mockito.any())).thenReturn(ruleOptional );
        when(baseSqlDao.saveAndFlush(Mockito.any())).thenReturn(rule);
        RuleDto pid = ruleService.patchById(dto);
        assertNotNull(pid);
	}

}
