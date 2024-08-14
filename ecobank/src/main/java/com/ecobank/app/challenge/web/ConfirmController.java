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

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ConfirmController {
	private final ChallConfirmService challConfirmService;
	private final FileService fileService;
	private final ChallService challService;
	
	@Autowired
	private HttpSession httpSession;
	
	//인증 목록
	@GetMapping("others")
	public String confirmList(Model model, int challNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		System.out.println(userNo + "유저넘버");
		//List<ChallConfirmVO> list = challCofirmService.confirmList(challNo);
		List<FileVO> list = fileService.selectFileOtherInfo(challNo);
		System.out.println(list + "파일 vo");
		model.addAttribute("confirmList", list);
		return "chall/others";
	}
	
	//나의 인증 내역
	@GetMapping("getMyConfirm")
	public String myConfirm(Model model, @RequestParam("challNo") Integer challNo, FileVO fileVO) {
		//model에 review.html안에 th에 넣는 ""값이 여기에 있는거 기억하기
		//detailImg를 여기에 따로 추가해야해
		int userNo = (Integer) httpSession.getAttribute("userNo");
		System.out.println(userNo);
		List<FileVO> list = fileService.selectFileInfo(userNo, challNo, "J3");	
		model.addAttribute("fileList", list);
		//System.out.println(list + "파일리스트 확인");
		return "chall/myConfirm";
	}
	
	//나의 인증 상세
	@GetMapping("confirmInfo")
	public String myConfirmInfo(Model model, ChallConfirmVO challConfirmVO, FileVO fileVO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		challConfirmVO.setUserNo(userNo);
		ChallConfirmVO findVO = challConfirmService.myConfirmInfo(challConfirmVO);
		
		List<FileVO> list = fileService.selectGetMyInfo(challConfirmVO.getConfirmNo());
		List<ChallConfirmVO> replyList = challConfirmService.confirmReplyList(challConfirmVO);
		System.out.println(list);
		System.out.println(findVO);
		System.out.println(replyList);
		
		model.addAttribute("list", list);
		model.addAttribute("myConfirm", findVO);
		model.addAttribute("replyList", replyList);
		return "chall/myConfirmDetail";
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
	//@GetMapping("confirm")
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