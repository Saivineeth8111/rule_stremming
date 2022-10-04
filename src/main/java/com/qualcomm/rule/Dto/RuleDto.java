package com.qualcomm.rule.Dto;

//import javax.persistence.Column;

import com.qualcomm.common.dto.BaseDto;
import com.qualcomm.common.enums.DeviceTypeEnum;
import com.qualcomm.rule.config.CategoryType;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RuleDto extends BaseDto{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String condition;
	
	private String Id;
	
    @NotNull
	private String action;
    
	 
	private CategoryType category;
	
	private DeviceTypeEnum deviceType;
	
	private String tenantId;
	
	private String deviceId;

}
