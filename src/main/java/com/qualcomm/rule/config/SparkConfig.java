package com.qualcomm.rule.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

	@Value("${spark.app.name}")
	private String appName;
	@Value("${spark.master}")
	private String masterUri;
	//@Value("${spark.home}")
	//private String sparkHome;

	@Bean
	public SparkConf conf() {
//		System.setProperty("hadoop.home.dir", "C:\\PI2\\rule_engine\\Spark_Job_Infra\\spark-3.3.0-bin-hadoop3");
		return new SparkConf().setAppName(appName).setMaster(masterUri);
	}

	@Bean
	public SparkSession ss() {
		SparkSession spark = SparkSession.builder().config(conf()).getOrCreate();
		return spark;
	}

}
