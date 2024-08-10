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
	
	// 2. 인증 및 인가 설정
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Security가 체크하는 경로 및 각 경로별 권한
		//.antMatchers("/**") // 루트경로 기반
		// .antMatchers("/admin/**") // 어드민
		// .antMatchers("/user/**") // 유저
		// 포괄적인 부분이 밑으로 가야함, ex user -> root
		http
			.authorizeHttpRequests()
				.antMatchers("/main/**") 			// 경로
					.permitAll();
		
		http.csrf().disable();
		return http.build();
	}
	
//	// 테스트용) 메모리 인증 방식
//	@Bean
//	InMemoryUserDetailsManager inMemoryUserDetailsService() {
//		UserDetails user = User.builder()
//								.username("user1")
//								.password(passwordEncoder().encode("1234"))
//								.authorities("ROLE_USER")
//								// .roles("USER") // ROLE_USER
//								.build();
//		
//		
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("1234"))
//				.authorities("ROLE_ADMIN")
//				// .roles("USER") // ROLE_USER
//				.build();
//		return new InMemoryUserDetailsManager(user, admin);
//	}
}
