package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan("com")
@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class,org.activiti.spring.boot.SecurityAutoConfiguration.class})
@EnableTransactionManagement (proxyTargetClass = true)
public class BpmApplication{ 

	public static void main(String[] args) {
		SpringApplication.run(BpmApplication.class, args);
	}

}