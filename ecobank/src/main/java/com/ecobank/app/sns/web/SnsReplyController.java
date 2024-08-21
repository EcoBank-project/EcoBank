package com.ecobank.app.sns.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecobank.app.sns.service.SnsReplyService;
import com.ecobank.app.sns.service.SnsReplyVO;
import com.ecobank.app.sns.service.SnsVO;

@RestController
public class SnsReplyController {

	private SnsReplyService snsReplyService;
	
	//ajax로 보내기!
	//DI 생성자 주입 방식=> 생성자 선언하기
	@Autowired
	public SnsReplyController(SnsReplyService snsReplyService){
		this.snsReplyService = snsReplyService;
	}
	@Autowired
	private HttpSession httpSession;
	
	//피드별 댓글 조회
	@GetMapping("snsReply")
	public List<SnsReplyVO> snsReplyInfo(SnsReplyVO snsReplyVO) {
		 return snsReplyService.snsReplyInfo(snsReplyVO);
	}
	
	//등록 처리
	@PostMapping("snsReply")
	public SnsReplyVO snsReplyInsertProcess(SnsReplyVO snsReplyVO) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsReplyVO.setUserNo(userNo);
		int fReplyno = snsReplyService.insertSnsReply(snsReplyVO);
		System.out.println("인서트"+ fReplyno);
		return snsReplyVO;
	}
	
	//삭제
	@DeleteMapping("snsReply")
	public int snsReplyDelete(Integer replyNo) {
		return snsReplyService.deleteSnsReply(replyNo);
	}
	
	//좋아요 등록
	@PostMapping("likeInsert")
	public int insertSnsLike(SnsReplyVO snsReplyVO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		snsReplyVO.setUserNo(userNo);
		System.out.println("좋아요"+snsReplyVO);
		return snsReplyService.insertSnsLike(snsReplyVO);
	}
	
	//좋아요 삭제
	@DeleteMapping("likeDelete")
	public int deleteSnsLike(SnsReplyVO snsReplyVO) {
		return snsReplyService.deleteSnsLike(snsReplyVO);
	}
	
	//팔로우 등록
	@PostMapping("insertFollow")
	public SnsReplyVO insertFollow(SnsReplyVO snsReplyVO) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsReplyVO.setUserNo(userNo);
		int followerId = snsReplyService.insertFollow(snsReplyVO);
		System.out.println("팔로우등록"+ followerId);
		System.out.println("팔로우등록"+ followerId);
		return snsReplyVO;
	}
	
	//팔로우 삭제
	@DeleteMapping("deleteFollow")
	public int deleteFollow(SnsReplyVO snsReplyVO) {
		return snsReplyService.deleteFollow(snsReplyVO);
	}
	
	//차단 등록
	@PostMapping("insertBlock")
	public SnsReplyVO insertBlock(SnsReplyVO snsReplyVO) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		
		//snsReplyVO.setBlockUserNo(userNo);
		
		int blockId = snsReplyService.insertBlock(snsReplyVO);
		System.out.println("내가 차단"+ userNo);
		System.out.println("차단된아이디"+ blockId);
		return snsReplyVO;
	}
	
	//차단 삭제
	@DeleteMapping("deleteBlock")
	public int deleteBlock(SnsReplyVO snsReplyVO) {
		return snsReplyService.deleteBlock(snsReplyVO);
	}
	
	
}
