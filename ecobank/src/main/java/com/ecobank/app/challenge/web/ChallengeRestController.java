package com.ecobank.app.challenge.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecobank.app.challenge.service.ChallConfirmService;
import com.ecobank.app.challenge.service.ChallConfirmVO;
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
	
	@PostMapping("goChallenge")
	public int insertChallEnter(@RequestParam("challNo") int challNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		int result = challConfirmService.insertChallEnter(userNo, challNo);
        return result;
	}
	
	@GetMapping("enterStatus")
	public boolean isUserParticipated(@RequestParam("challNo") int challNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		return challConfirmService.isUserParticipated(userNo, challNo);
	}
	
	@GetMapping("confirmStatus")
	public boolean isConfirmed(@RequestParam("challNo") int challNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		boolean isConfirmed = challConfirmService.isConfirmed(userNo, challNo);
		return isConfirmed;
	}
	
	//인증 댓글 목록
	@GetMapping("replyList")
	public List<ChallConfirmVO> replyList(ChallConfirmVO challConfirmVO) {
		System.out.println(challConfirmVO);
		return challConfirmService.confirmReplyList(challConfirmVO);
	}
}
