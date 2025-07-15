package org.example.dify_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DifyTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DifyTestApplication.class, args);
	}

}
