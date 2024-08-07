package com.ecobank.app.sns.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecobank.app.sns.service.SnsReplyService;
import com.ecobank.app.sns.service.SnsReplyVO;
import com.ecobank.app.sns.service.SnsVO;
import com.ecobank.app.upload.service.FileVO;

@Controller
public class SnsReplyController {

	private SnsReplyService snsReplyService;
	
	
	//DI 생성자 주입 방식=> 생성자 선언하기
	@Autowired
	public SnsReplyController(SnsReplyService snsReplyService){
		this.snsReplyService = snsReplyService;
	}
	
	
	//단건조회
	@GetMapping("snsReplyInfo")
	public String snsInfo(SnsReplyVO snsReplyVO, Model model) {
		List<SnsReplyVO> list = snsReplyService.snsReplyInfo(snsReplyVO);

		model.addAttribute("sns", list);
		return "sns/snsInfo";
	}
	

	//등록 페이지 이동
	@GetMapping("snsReplyInsert")
	public String snsInsert() {
		return "sns/snsInsert";
	}
	
	//등록 처리
	@PostMapping("snsReplyInsert")
	public String snsInsertProcess(SnsReplyVO snsReplyVO, Model model) {

		int fReplyno = snsReplyService.insertSnsReply(snsReplyVO);
		System.out.println("인서트"+ fReplyno);

		return "redirect:snsInfo?feedNo=" + fReplyno;
	}
	
		
	
	//삭제
	@GetMapping("snsReplyDelete")
	public String snsDelete(Integer replyNo) {
		snsReplyService.deleteSnsReply(replyNo);
		return "redirect:sns";
	}

}
