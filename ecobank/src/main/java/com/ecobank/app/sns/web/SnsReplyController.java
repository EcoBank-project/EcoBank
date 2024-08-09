package com.ecobank.app.sns.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecobank.app.sns.service.SnsReplyService;
import com.ecobank.app.sns.service.SnsReplyVO;

@RestController
public class SnsReplyController {

	private SnsReplyService snsReplyService;
	
	//ajax로 보내기!
	//DI 생성자 주입 방식=> 생성자 선언하기
	@Autowired
	public SnsReplyController(SnsReplyService snsReplyService){
		this.snsReplyService = snsReplyService;
	}
	
	//피드별 댓글 조회
	@GetMapping("snsReply")
	public List<SnsReplyVO> snsReplyInfo(SnsReplyVO snsReplyVO, Model model) {
		 return snsReplyService.snsReplyInfo(snsReplyVO);
	}
	
	//등록 처리
	@PostMapping("snsReply")
	public SnsReplyVO snsReplyInsertProcess(SnsReplyVO snsReplyVO, Model model) {
		int fReplyno = snsReplyService.insertSnsReply(snsReplyVO);
		System.out.println("인서트"+ fReplyno);
		return snsReplyVO;
	}
	
	//삭제
	@DeleteMapping("snsReply")
	public int snsReplyDelete(Integer replyNo) {
		return snsReplyService.deleteSnsReply(replyNo);
	}

}
