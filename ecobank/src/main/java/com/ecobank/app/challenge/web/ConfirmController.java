package com.ecobank.app.challenge.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecobank.app.challenge.service.ChallCofirmService;
import com.ecobank.app.challenge.service.ChallConfirmVO;

@Controller
public class ConfirmController {
	private ChallCofirmService challCofirmService;
	
	@Autowired
	public ConfirmController(ChallCofirmService challCofirmService) {
		this.challCofirmService = challCofirmService;
	}
	
	//인증 목록
	@GetMapping("others")
	public String confirmList(Model model) {
		List<ChallConfirmVO> list = challCofirmService.confirmList();
		model.addAttribute("confirmList", list);
		return "chall/confirm";
	}
}
