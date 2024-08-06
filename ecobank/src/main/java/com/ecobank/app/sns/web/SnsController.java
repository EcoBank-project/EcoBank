package com.ecobank.app.sns.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecobank.app.sns.service.SnsService;
import com.ecobank.app.sns.service.SnsVO;

@Controller
public class SnsController {
	
	private SnsService snsService;
	
	//DI 생성자 주입 방식=> 생성자 선언하기
	@Autowired
	public SnsController(SnsService snsService){
		this.snsService = snsService;
	}
	//전체조회
	@GetMapping("snsList")
	public String snsList(Model model) {
		List<SnsVO> list = snsService.snsList();
		model.addAttribute("snsList",list);
		return "sns/snsList";
	}
	
	//단건조회
	@GetMapping("snsInfo")
	public String snsInfo(SnsVO snsVO, Model model) {
		SnsVO findVO = snsService.snsInfo(snsVO);
		model.addAttribute("sns", findVO);
		return "sns/snsInfo";
	}
	
	//등록
	//@PostMapping("snsInsert")
	//public String snsInsertProcess(SnsVO snsVO) {
//		for()
//	}
	
	
	//수정
	
	
	
	
	
	
	//삭제
	@GetMapping("snsDelete")
	public String snsDelete(Integer feedNo) {
		snsService.deleteSns(feedNo);
		return "redirect:snsList";
	}

}
