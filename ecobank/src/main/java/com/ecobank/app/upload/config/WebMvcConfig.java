package com.ecobank.app.upload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	//설정파일 모아두는 곳(자동으로 설정되어있는거 수정하는 곳)
	//스프링한테 웹 환경구현한다고 알리는거
	@Value("${file.upload.url}")
	private String uploadPath;
	
	//경로 등록
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//WebMvcConfigurer.super.addResourceHandlers(registry);
		registry
			.addResourceHandler("/images/**") //URL
			.addResourceLocations("file:///" + uploadPath, ""); //실제 경로
	} 
	
}
