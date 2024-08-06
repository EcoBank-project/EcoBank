package com.ecobank.app.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

//1. 암호화/복호화에 사용하는 Bean 등록
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//2. 인증 및 인가 설정
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authorize -> authorize
            .antMatchers("/css/**", "/js/**", "/img/**").permitAll() // 정적 자원 허용
            .antMatchers("/", "/login", "/signup").permitAll() // 인증 필요 없는 경로
            .anyRequest().authenticated() // 나머지 요청은 인증 필요
        )
        .formLogin(formLogin -> formLogin
            .loginPage("/login") // 로그인 페이지 설정
            .defaultSuccessUrl("/", true) // 로그인 성공 시 리다이렉트 URL
            .permitAll() // 로그인 페이지는 모든 사용자에게 허용
        )
        .logout(logout -> logout
            .logoutUrl("/logout") // 로그아웃 URL 설정
            .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 URL
            .permitAll() // 로그아웃은 모든 사용자에게 허용
        );
		return http.build();
	}
}
