package com.ecobank.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ecobank.app.googleuser.service.CustomOAuth2UserService;
import com.ecobank.app.security.handler.CustomAuthenticationSuccessHandler;
import com.ecobank.app.security.handler.GoogleAuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	private GoogleAuthenticationSuccessHandler googleAuthenticationSuccessHandler;
	
    private final CustomOAuth2UserService customOAuth2UserService;

//1. 암호화/복호화에 사용하는 Bean 등록
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/images/**").permitAll() // 정적 자원 허용
                .antMatchers("/", "/login*", "/signup", "/user/**", "/about", "/find*", "/ip-info", "/set-country", "/reset_pw", "/introduce").permitAll() // 인증 필요 없는 경로
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                .and()
            .formLogin()
                .loginPage("/login") // 로그인 페이지 설정
                .defaultSuccessUrl("/", true) // 로그인 성공 시 리다이렉트 URL
                .successHandler(customAuthenticationSuccessHandler) // 커스텀 로그인 성공 핸들러
                .permitAll() // 로그인 페이지는 모든 사용자에게 허용
                .and()
            .logout()
                .logoutUrl("/logout") // 로그아웃 URL 설정
                .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 URL
                .permitAll() // 로그아웃은 모든 사용자에게 허용
        	.and()
        		.oauth2Login()
                .loginPage("/login") // 로그인 페이지 설정
        		.successHandler(googleAuthenticationSuccessHandler)
	        		.userInfoEndpoint()
	        		.userService(customOAuth2UserService);
        			
        return http.build();
    }
}