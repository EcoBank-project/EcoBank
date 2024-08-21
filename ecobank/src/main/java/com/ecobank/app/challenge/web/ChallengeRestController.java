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
import com.ecobank.app.challenge.service.ChallService;
import com.ecobank.app.challenge.service.ChallVO;
import com.ecobank.app.challenge.service.LikeDTO;
import com.ecobank.app.challenge.service.MyConfirmDTO;
import com.ecobank.app.challenge.service.ReplyVO;
import com.ecobank.app.challenge.service.ReviewDTO;

@RestController
public class ChallengeRestController {
	private ChallConfirmService challConfirmService;
	private ChallService challService;
	
	@Autowired
	public ChallengeRestController(ChallConfirmService challConfirmService, ChallService challService) {
		this.challConfirmService = challConfirmService;
		this.challService = challService;
	}
	
	@Autowired
	private HttpSession httpSession;
	
	//챌린지 좋아요 개수
	@GetMapping("challLikeCnt")
	public int getLikeCnt(@RequestParam("challNo") int challNo) {
		return challService.challLikeCnt(challNo);
	}
	
	//챌린지 좋아요 여부
	@GetMapping("challLikeStatus")
	public int challLikeStatus(@RequestParam("challNo") int challNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		int status = challService.challLikeStatus(userNo, challNo);
		return status;
	}
	
	//챌린지 좋아요 등록
	@PostMapping("challLikeInsert")
	public int challLikeInsert(ChallVO challVO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		challVO.setUserNo(userNo);
		return challService.challLikeInsert(challVO);
	}
	
	//챌린지 좋아요 삭제
	@PostMapping("challLikeDelete")
	public int challLikeDelete(@RequestParam("challNo") int challNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		int result = challService.challLikeDelete(userNo, challNo);
		return result;
	}
	
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
	
	//챌린지 참가 여부
	@GetMapping("enterStatus")
	public boolean isUserParticipated(@RequestParam("challNo") int challNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		return challConfirmService.isUserParticipated(userNo, challNo);
	}
	
	//인증 상태 여부
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
	
	//인증 좋아요 등록
	@PostMapping("confirmLikeInsert")
	public LikeDTO confirmLikeInsert(ChallConfirmVO challConfirmVO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		challConfirmVO.setUserNo(userNo);
		return challConfirmService.confirmLikeInsert(challConfirmVO);
	}
	
	//인증 좋아요 상태 여부
	@GetMapping("confirmLikeStatus")
	public int confirmLikeStatus(int confirmNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		int status = challConfirmService.confirmLikeStatus(userNo, confirmNo);
		return status;
	}
	
	//인증 신고 등록
	@PostMapping("confirmDeclareInsert")
	public int confirmDeclareProcess(ChallConfirmVO challConfirmVO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		challConfirmVO.setUserNo(userNo);
		return challConfirmService.declareInsert(challConfirmVO);
	}
	
	//챌린지 후기 등록
	@PostMapping("reviewInsert")
	public int reviewInsertProcess(ReviewDTO reviewDTO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		reviewDTO.setUserNo(userNo);
		return challService.reviewInsert(reviewDTO);
	}
	
	//챌린지 후기 등록 여부
	@GetMapping("reviewStatus")
	public boolean reviewStatus(@RequestParam("reviewNo") int reviewNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		return challService.reviewStatus(userNo, reviewNo);
	}
	
	//챌린지 후기 삭제
	@PostMapping("reviewDelete")
	public int reviewDelete(@RequestParam("reviewNo") int reviewNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		int result = challService.reviewDelete(userNo, reviewNo);
		return result;
	}

}
