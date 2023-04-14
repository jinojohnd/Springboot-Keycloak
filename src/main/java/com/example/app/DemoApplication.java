package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.app.security.SecurityContextCopyingDecorator;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.app"})
@EnableAsync
@EnableCaching
@EnableScheduling
public class DemoApplication {

public static void main(String[] args) {
		
		SpringApplication springApplication = new SpringApplication(DemoApplication.class);
		springApplication.addListeners(new ApplicationPidFileWriter("job_board.pid"));
		springApplication.run(args);
	}

	@Bean(name = "processExecutor")
	@Primary
	public TaskExecutor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(5);
		executor.setThreadNamePrefix("REG-");
		executor.initialize();
		executor.setTaskDecorator(new SecurityContextCopyingDecorator());
		return executor;
	}


}
