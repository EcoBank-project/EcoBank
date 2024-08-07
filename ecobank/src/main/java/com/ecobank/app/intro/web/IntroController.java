package com.ecobank.app.intro.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecobank.app.intro.service.CarbonService;
import com.ecobank.app.intro.service.CarbonVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class IntroController {
	private CarbonService carbService;

	@Autowired
	public IntroController(CarbonService carbService) {
		this.carbService = carbService;
	}
	

	@GetMapping("/")
	public String home(Model model) {
		// 1. 기능수행

		// 2. 클라이언트에 전달할 데이터 담기

		// 3. 데이터를 출력할 페이지 결정
		return "main/home";
	}

	// 소개 페이지
	@GetMapping("about")
	public String about(Model model) {
		// 1. 기능수행
		List<CarbonVO> carbList = carbService.CarbonList();
		// 2. 클라이언트에 전달할 데이터 담기
		ObjectMapper objectMapper = new ObjectMapper();
        String myDataJson;
		try {
			myDataJson = objectMapper.writeValueAsString(carbList);
			model.addAttribute("carbList", myDataJson);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		// 3. 데이터를 출력할 페이지 결정
		return "main/about";
	}

}