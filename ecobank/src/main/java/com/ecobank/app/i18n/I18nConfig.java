package com.ecobank.app.i18n;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class I18nConfig implements WebMvcConfigurer {

	// CookieLocaleResolver를 사용하였고 쿠키이름을 lang으로 설정했다.
	// 값이 없을 경우에는 기본값(한국)을 사용하도록 설정.
    @Bean
    public LocaleResolver localeResolver() {

        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.KOREA);
        resolver.setCookieName("lang");
        return resolver;
    }

    // Locale 값이 변경되면 동작.
    // url의 query parameter에 지정한 값이 들어올 때 동작.
    // ex) http://localhost:8080?lang=ko
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }
    // 스프링이 작성한 언어 리소스들을 사용할 수 있게 등록, 설정하는 클래스.
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // 언어 리소스들이 있는 경로 지정.
        messageSource.setBasename("classpath:/i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}