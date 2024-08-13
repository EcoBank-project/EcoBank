package com.ecobank.app.challenge.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecobank.app.challenge.service.ChallConfirmService;
import com.ecobank.app.challenge.service.ChallConfirmVO;
import com.ecobank.app.challenge.service.ChallService;
import com.ecobank.app.challenge.service.ChallVO;
import com.ecobank.app.upload.service.FileService;
import com.ecobank.app.upload.service.FileVO;

@Controller
public class ConfirmController {
	private ChallConfirmService challConfirmService;
	private FileService fileService;
	private ChallService challService;
	
	@Autowired
	public ConfirmController(ChallConfirmService challConfirmService, FileService fileService, ChallService challService) {
		this.challConfirmService = challConfirmService;
		this.fileService = fileService;
		this.challService = challService;
	}
	
	@Autowired
	private HttpSession httpSession;
	
	//인증 목록
	@GetMapping("others")
	public String confirmList(Model model, int challNo) {
		//List<ChallConfirmVO> list = challCofirmService.confirmList(challNo);
		List<FileVO> list = fileService.selectFileOtherInfo(challNo);
		//System.out.println(list);
		model.addAttribute("confirmList", list);
		return "chall/others";
	}
	
	//나의 인증 내역
	@GetMapping("getMyConfirm")
	public String myConfirm(Model model, @RequestParam("challNo") Integer challNo) {
		//model에 review.html안에 th에 넣는 ""값이 여기에 있는거 기억하기
		//detailImg를 여기에 따로 추가해야해
		int userNo = (Integer) httpSession.getAttribute("userNo");
		List<FileVO> list = fileService.selectFileInfo(userNo, challNo, "J3");	
		//System.out.println(list + "찍히나");
		model.addAttribute("fileList", list);
		return "chall/myConfirm";
	}
	
	//나의 캘린더(페이지)
	@GetMapping("getMyCalendar")
	public String getMyCalendar() {
		return "chall/calendar";
	}
	
	//인증 등록 페이지
	@GetMapping("confirmInsert")
	public String confirmInsert() {
		return "chall/confirmForm";
	}
	
	//인증 등록 처리
	@PostMapping("confirmInsert")
	public String confirmInsertProcess(MultipartFile[] images, ChallConfirmVO challConfirmVO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		challConfirmVO.setUserNo(userNo);
		int confirmNo = challConfirmService.confirmInsert(challConfirmVO);
		
		String confirmCode = "J3";
		fileService.insertFile(images, confirmCode, confirmNo);
		
		return "redirect:detail?challNo=" + challConfirmVO.getChallNo();
	}
	
	//메인에서 인증 페이지
	@GetMapping("confirm")
	public String confirmList(Model model) {
		return "chall/confirm";
	}
	
	//챌린지 상세에서 상세 이미지(+리뷰)
	@GetMapping("review")
	public String reviewList(ChallVO challVO, Model model) {
		//상세
		ChallVO findVO = challService.challInfo(challVO);
		model.addAttribute("detail", findVO);
		//System.out.println(findVO);
		
		//리뷰
		return "chall/review";
	}
	
	
}