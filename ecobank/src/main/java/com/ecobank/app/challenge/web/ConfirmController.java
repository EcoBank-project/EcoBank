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
import com.ecobank.app.challenge.service.ReviewDTO;
import com.ecobank.app.common.service.CodeVO;
import com.ecobank.app.common.service.CommonService;
import com.ecobank.app.upload.service.FileService;
import com.ecobank.app.upload.service.FileVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ConfirmController {
	private final ChallConfirmService challConfirmService;
	private final FileService fileService;
	private final ChallService challService;
	private final CommonService commonService;
	
	@Autowired
	private HttpSession httpSession;
	
	//참가자 인증 전체 목록
	@GetMapping("others")
	public String confirmList(Model model, int challNo) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		List<ChallConfirmVO> confirmList = challConfirmService.confirmList(challNo);

		model.addAttribute("confirmList", confirmList);
		return "chall/others";
	}
	
	//참가자 인증 상세(나의 인증 상세랑 같은 service 호출, 같은 html)
	@GetMapping("othersInfo")
	public String othersInfo(Model model, ChallConfirmVO challConfirmVO, int userNo) {
		int nowUserNo = (Integer) httpSession.getAttribute("userNo");
		challConfirmVO.setUserNo(userNo);
		ChallConfirmVO findVO = challConfirmService.myConfirmInfo(challConfirmVO);
		
		List<FileVO> list = fileService.selectGetMyInfo(challConfirmVO.getConfirmNo());
		
		model.addAttribute("myConfirm", findVO);
		model.addAttribute("list", list);
		model.addAttribute("nowUserNo", nowUserNo); //글쓴 유저가 맞는지 확인하려고
		
		//인증 신고 목록
		List<CodeVO> declarelist = commonService.codeList("0C");
		model.addAttribute("confirmDeclare", declarelist);
		return "chall/myConfirmDetail";
	}
	
	//나의 인증 내역
	@GetMapping("getMyConfirm")
	public String myConfirm(Model model, @RequestParam("challNo") Integer challNo, FileVO fileVO) {
		//model에 review.html안에 th에 넣는 ""값이 여기에 있는거 기억하기
		//detailImg를 여기에 따로 추가해야해
		int userNo = (Integer) httpSession.getAttribute("userNo");
		List<FileVO> list = fileService.selectFileInfo(userNo, challNo, "J3");	
		model.addAttribute("fileList", list);
		return "chall/myConfirm";
	}
	
	//나의 인증 상세
	@GetMapping("confirmInfo")
	public String myConfirmInfo(Model model, ChallConfirmVO challConfirmVO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		challConfirmVO.setUserNo(userNo);
		ChallConfirmVO findVO = challConfirmService.myConfirmInfo(challConfirmVO);
		
		List<FileVO> list = fileService.selectGetMyInfo(challConfirmVO.getConfirmNo());
		
		model.addAttribute("myConfirm", findVO);
		model.addAttribute("list", list);
		model.addAttribute("nowUserNo", userNo);
		System.out.println(findVO + "파인드브오에 뭐 있는지, 신고");
		
		//인증 신고 목록
		List<CodeVO> declarelist = commonService.codeList("0C");
		model.addAttribute("confirmDeclare", declarelist);
		return "chall/myConfirmDetail";
	}
	
	//인증 댓글 목록
	@GetMapping("replyList")
	public String replyList(Model model, ChallConfirmVO challConfirmVO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		List<ChallConfirmVO> list = challConfirmService.confirmReplyList(challConfirmVO);
		//ChallConfirmVO findVO = challConfirmService.myConfirmInfo(challConfirmVO);
		model.addAttribute("userNo", userNo);
		model.addAttribute("replyList", list);
		//model.addAttribute("myConfirm", findVO);
		System.out.println(list + "리스트에 뭐있는지");
		//System.out.println(findVO + "파인드 브오에 뭐있는지");
		return "chall/reply";
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
	
	//챌린지 상세에서 상세 이미지(+리뷰 목록)
	@GetMapping("review")
	public String reviewList(ChallVO challVO, Model model) {
		//상세
		ChallVO findVO = challService.challInfo(challVO);
		model.addAttribute("detail", findVO);
		
		//리뷰 목록
		int userNo = (Integer) httpSession.getAttribute("userNo");
		challVO.getUserNo();
		List<ReviewDTO> list = challService.reviewList(challVO);
		model.addAttribute("reviewList", list);
		model.addAttribute("userNo", userNo);
		//System.out.println(list + "상세에 리뷰안에 뭐있는지 확인 - 여기에 다른 유저번호");
		//System.out.println(userNo + "유저번호 뭔지 - 현재 접속한 유저 번호");
		return "chall/review";
	}	
	
}