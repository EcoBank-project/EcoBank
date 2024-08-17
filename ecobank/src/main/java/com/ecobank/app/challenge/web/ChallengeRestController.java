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
import com.ecobank.app.challenge.service.MyConfirmDTO;
import com.ecobank.app.challenge.service.ReplyVO;

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
	
	//댓글 등록(아작스로 보낼거라서)
	@PostMapping("replyInsert")
	public int replyInsert(ReplyVO replyVO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		int result = challConfirmService.replyInsert(userNo, replyVO);
		return result;
	}
	
	//인증 댓글 삭제
	@PostMapping("replyDelete")
	public int replyDelete(@RequestParam("confirmReplyNo") int confirmReplyNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		int result = challConfirmService.replyDelete(userNo, confirmReplyNo);
		return result;
	}
	
	//인증 글 삭제
	@PostMapping("confirmDelete")
	public int confirmDelete(@RequestParam("confirmNo") int confirmNo) { 
		int userNo = (Integer) httpSession.getAttribute("userNo");
		int result = challConfirmService.confirmDelete(userNo, confirmNo);
		
		return result;
	}

}
