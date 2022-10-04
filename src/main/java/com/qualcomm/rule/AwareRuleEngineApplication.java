package com.qualcomm.rule;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.qualcomm.http.api.GenericrestapiApplication;

@SpringBootApplication
@EntityScan("com*")
@EnableJpaRepositories("com*")
@ComponentScan(basePackages = { "com*" })
public class AwareRuleEngineApplication extends GenericrestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwareRuleEngineApplication.class, args);
	}

}