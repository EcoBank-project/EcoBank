package com.ecobank.app.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecuritController {
	@GetMapping("login")
	public String intro(Model model) {
		// 1. 기능수행

		// 2. 클라이언트에 전달할 데이터 담기

		// 3. 데이터를 출력할 페이지 결정
		return "users/login";
	}

}
