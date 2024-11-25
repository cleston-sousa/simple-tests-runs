package com.hachinet.simple_tests_runs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SimpleTestsRunsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleTestsRunsApplication.class, args);
	}

}
