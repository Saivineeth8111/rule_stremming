package com.qualcomm.rule.spark;

import java.util.List;

import org.apache.spark.sql.SparkSession;

import com.qualcomm.rule.Dto.RuleDto;

public class SparkJob {

	public List<RuleDto> session() {
		SparkSession spark = SparkSession.builder().appName("DataFrame-DatasetConversion").master("local[4]")
				.getOrCreate();
		
		spark.stop();
		
	return null;
	
	}

}
