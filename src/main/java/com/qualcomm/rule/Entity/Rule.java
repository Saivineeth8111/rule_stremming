package com.qualcomm.rule.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.qualcomm.common.enums.DeviceTypeEnum;
import com.qualcomm.http.api.model.BaseModel;
import com.qualcomm.http.api.util.PostgresqlEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(name = "t_rules")
@TypeDef(name = "pgsql_enum", typeClass = PostgresqlEnum.class)
public class Rule extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "condition")
	private String condition;

	@Column(name = "action")
	private String action;

	@Column(name = "category")
	private String category;

	@Enumerated(EnumType.STRING)
	@Column(name = "device_type")
	@Type(type = "pgsql_enum")
	private DeviceTypeEnum deviceType;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "device_id")
	private String deviceId;

}
