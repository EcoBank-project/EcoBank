package com.ecobank.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.ecobank.app.**.mapper")
@EnableScheduling
public class EcobankApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcobankApplication.class, args);
	}

}
