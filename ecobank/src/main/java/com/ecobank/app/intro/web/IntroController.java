package com.ecobank.app.intro.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ecobank.app.intro.service.CarbUserService;
import com.ecobank.app.intro.service.CarbonService;
import com.ecobank.app.intro.service.CarbonVO;
import com.ecobank.app.score.service.ScoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class IntroController {
	private CarbonService carbService;
	private ScoreService scoreService;
	
	@Autowired
	public IntroController(CarbonService carbService, CarbUserService userService, ScoreService scoreService) {
		this.carbService = carbService;
		this.scoreService = scoreService;
	}

	// 소개 페이지
	@GetMapping("about")
	public String about(Model model) {
		// 1. 기능수행
		// 탄소배출량 데이터
		List<CarbonVO> carbList = carbService.CarbonList();
		// 사이트 회원 총 사용점수
		Integer totalScore = scoreService.selectTotalUseScore();
		Integer challEnterUserCount = scoreService.getChallEnterUserCount();
		// 회원 사용 가능 점수
		
		// 나무
		Integer treeCount = (totalScore*1000)/20;
		// 해수면
		double seaLevel = ((totalScore*1000.0)/100000000.0)*0.5;
		System.out.println(seaLevel);
		// 2. 클라이언트에 전달할 데이터 담기
		ObjectMapper objectMapper = new ObjectMapper();
		String myDataJson;
		try {
			myDataJson = objectMapper.writeValueAsString(carbList);
			model.addAttribute("carbList", myDataJson);
			model.addAttribute("totalScore",totalScore);
			model.addAttribute("challEnterUserCount",challEnterUserCount);
			model.addAttribute("treeCount",treeCount);
			model.addAttribute("seaLevel",seaLevel);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 3. 데이터를 출력할 페이지 결정
		return "main/about";
	}
}