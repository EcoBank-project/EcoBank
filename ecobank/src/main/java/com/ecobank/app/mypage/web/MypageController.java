package com.ecobank.app.mypage.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MypageController {

	@GetMapping("mypage")
	public String getMypage() {
		return "mypage/mypage";
	}
	
}
