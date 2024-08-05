package com.ecobank.app.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminController {

	
	// 전체조회
	@GetMapping("admin")
	public String intro(Model model) {
	
		return "admins/admin";
	}
}
