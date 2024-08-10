package com.ecobank.app.challenge.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecobank.app.challenge.service.ChallConfirmService;
import com.ecobank.app.challenge.service.MyConfirmDTO;

@RestController
public class ChallengeRestController {
	private ChallConfirmService challConfirmService;
	
	@Autowired
	public ChallengeRestController(ChallConfirmService challConfirmService) {
		this.challConfirmService = challConfirmService;
	}
	@Autowired
	private HttpSession httpSession;
	
	@GetMapping("myConfirm")
	public MyConfirmDTO myConfirm(@RequestParam("challNo") int ChallNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		return challConfirmService.myConfirm(userNo, ChallNo);
	}
	
	@GetMapping("calendar")
	public List<Date> myCalendar(@RequestParam("challNo") int challNo){
		int userNo = (Integer) httpSession.getAttribute("userNo");
		
		return challConfirmService.myCalendar(userNo, challNo);
	}
}
