package com.qualcomm.rulesImpl.DeviceRuleEngine;



import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeDetails {
    Long accountNumber;
    Double monthlySalary;
    Time time;
}
