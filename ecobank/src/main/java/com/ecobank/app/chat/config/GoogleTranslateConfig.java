package com.ecobank.app.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

@Configuration
public class GoogleTranslateConfig {
	//@Value("${google.api.key}")
    private String apiKey;
	
	@Bean
    public Translate translate() {
        return TranslateOptions.newBuilder().setApiKey(apiKey).build().getService();
    }
}
