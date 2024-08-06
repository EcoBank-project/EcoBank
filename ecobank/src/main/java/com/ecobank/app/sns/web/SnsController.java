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
	
	@Autowired
	SnsService snsService;
	
	//전체조회
	@GetMapping("snsList")
	public String snsList(Model model) {
		List<SnsVO> list = snsService.snsList();
		model.addAttribute("snsList",list);
		return "sns/snsList";
	}
}
