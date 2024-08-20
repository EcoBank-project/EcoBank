package com.ecobank.app.home.web;

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

import com.ecobank.app.home.service.ChallengeVO;
import com.ecobank.app.home.service.HomeService;
import com.ecobank.app.home.service.RankingVO;
import com.ecobank.app.intro.service.CarbUserService;
import com.ecobank.app.score.service.ScoreService;

@Controller
public class HomeController {
	private CarbUserService userService;
	private ScoreService scoreService;
	private HomeService homeService;
	@Autowired
	public HomeController(HomeService homeService, CarbUserService userService, ScoreService scoreService) {
		this.userService = userService;
		this.scoreService = scoreService;
		this.homeService = homeService;
	}

	@GetMapping("/")
	public String home(Model model) {
		// 1. 기능수행

		// 2. 클라이언트에 전달할 데이터 담기
		ChallengeVO chall = homeService.getMostPopularChallenge();
		List<ChallengeVO> overSoonList = homeService.getOverSoonChallenges();
		List<ChallengeVO> finishedList = homeService.getTopFiveFinishedChallenges();
		List<RankingVO> topUsers = homeService.getTopRankedUsers();
		System.out.println(finishedList);  
		
		model.addAttribute("chall", chall);
		model.addAttribute("overSoonList", overSoonList);
		model.addAttribute("finishedList", finishedList);
		model.addAttribute("topUsers", topUsers);
		System.out.println("랭킹 : ");
		System.out.println(topUsers);
		// 3. 데이터를 출력할 페이지 결정
		return "main/home";
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
