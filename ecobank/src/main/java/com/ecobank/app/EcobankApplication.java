package com.ecobank.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.ecobank.app.**.service")
@MapperScan(basePackages = "com.ecobank.app.**.mapper")
public class EcobankApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcobankApplication.class, args);
	}

}
