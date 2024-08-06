package com.ecobank.app.challenge.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecobank.app.challenge.service.ChallService;
import com.ecobank.app.challenge.service.ChallVO;

@Controller
public class ChallengController {
	private ChallService challService;
	
	@Autowired
	public ChallengController(ChallService challService) {
		this.challService = challService;
	}
	
	//챌린지전체조회
	@GetMapping("ready")
	public String challList(Model model) {
		List<ChallVO> list = challService.challList();
		model.addAttribute("challList", list);
		return "chall/ready";
	}
	
	//챌린지 등록
	@GetMapping("challInsert")
	public String challInsertForm() {
		return "admins/challInsert";
	}
	
}
