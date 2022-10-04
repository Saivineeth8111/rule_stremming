package com.qualcomm.rule.Dto;

import java.util.List;

import com.qualcomm.common.dto.BaseDto;
import com.qualcomm.rule.config.EventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	private String eventType;
	
	private Long eventTime;
	
	private String tenantId; 
	
	private String deviceId;
    
	
	private List<String> params;

}

