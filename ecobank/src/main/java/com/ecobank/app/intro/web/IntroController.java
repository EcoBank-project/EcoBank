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
	private CarbUserService userService;
	private ScoreService scoreService;
	
	@Autowired
	public IntroController(CarbonService carbService, CarbUserService userService, ScoreService scoreService) {
		this.carbService = carbService;
		this.userService = userService;
		this.scoreService = scoreService;
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
		// 탄소배출량 데이터
		List<CarbonVO> carbList = carbService.CarbonList();
		// 사이트 회원 총 사용점수
		Integer totalScore = scoreService.selectTotalUseScore();
		Integer challEnterUserCount = scoreService.getChallEnterUserCount();
		// 회원 사용 가능 점수
		
		// 2. 클라이언트에 전달할 데이터 담기
		ObjectMapper objectMapper = new ObjectMapper();
		String myDataJson;
		try {
			myDataJson = objectMapper.writeValueAsString(carbList);
			model.addAttribute("carbList", myDataJson);
			model.addAttribute("totalScore",totalScore);
			model.addAttribute("challEnterUserCount",challEnterUserCount);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 3. 데이터를 출력할 페이지 결정
		return "main/about";
	}

	@GetMapping("ip-info")
	@ResponseBody
	public ResponseEntity<String> getIpInfo(HttpServletRequest request) {
		try {
			// String ip = getClientIp(request);
			String apiUrl = "https://api.ip.pe.kr/json/";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
			System.out.println(response.getBody());
			return ResponseEntity.ok(response.getBody());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching IP information.");
		}
	}

	@PostMapping("set-country")
	@ResponseBody
	public ResponseEntity<String> setCountry(@RequestBody Map<String, String> requestData) {

		// countryCode userId
		String countryCode = requestData.get("country_code");
		String userId = requestData.get("user_id");

		// 요청 처리(user테이블 국가코드 update 프로시저 실행)
		System.out.println(countryCode);
		System.out.println(userId);
		// 처리 결과에 따라 적절한 응답을 반환합니다.
		// 처리 결과에 따라 적절한 응답을 반환합니다.
		if (countryCode != null && userId != null) {
			try {
				userService.updateCountryInfo(userId, countryCode);
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON) // 응답 Content-Type 설정
						.body("{\"message\":\"Country Code Update Success!\"}");
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
						.body("{\"message\":\"Error updating country information.\"}");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"Invalid data\"}");
		}
	}

}