package com.qualcomm.rule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsynchConfiguration implements AsyncConfigurer{

	@Bean(name = "asyncRuleExecutor")
	  public TaskExecutor asyncExecutor() 
	  {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(3);
	    executor.setMaxPoolSize(3);
	    executor.setQueueCapacity(100);
	    executor.setWaitForTasksToCompleteOnShutdown(true);
	    executor.setThreadNamePrefix("AsynchRuleEngineThread-");
	    executor.initialize();
	    return executor;
	  }
}
