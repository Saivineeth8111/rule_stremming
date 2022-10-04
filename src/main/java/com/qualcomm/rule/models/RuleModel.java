package com.qualcomm.rule.models;

import com.qualcomm.http.api.model.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RuleModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    String condition;
    String action;
	String category;
}
