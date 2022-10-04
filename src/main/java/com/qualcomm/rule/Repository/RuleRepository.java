package com.qualcomm.rule.Repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qualcomm.common.enums.DeviceTypeEnum;
import com.qualcomm.http.api.dao.BaseSqlDao;
import com.qualcomm.rule.Entity.Rule;

@Repository
public interface RuleRepository extends BaseSqlDao<Rule> {

	List<Rule> findByCategory(String category);

	List<Rule> findByDeviceIdAndCategory(String deviceId, String category);

	List<Rule> findByDeviceIdAndCategoryAndTenantIdAndDeviceType(String deviceId, String category,
			 String tenantId,DeviceTypeEnum deviceType);

}
