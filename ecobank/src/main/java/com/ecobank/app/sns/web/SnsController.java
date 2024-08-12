package com.ecobank.app.sns.web;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecobank.app.sns.service.SnsService;
import com.ecobank.app.sns.service.SnsVO;
import com.ecobank.app.upload.service.FileService;
import com.ecobank.app.upload.service.FileVO;

@Controller
public class SnsController {
	

	//파일등록
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@GetMapping("getPath")
	@ResponseBody
	public String getPath() {
		return uploadPath;
	}
	
	private SnsService snsService;
	private FileService fileService;
	
	@Autowired
	private HttpSession httpSession;
	
	//DI 생성자 주입 방식=> 생성자 선언하기
	@Autowired
	public SnsController(SnsService snsService, FileService fileService){
		this.snsService = snsService;
		this.fileService = fileService;
	}
	
	//전체조회
	@GetMapping("sns")
	public String snsList(Model model) {
		List<SnsVO> list = snsService.snsList();
		System.out.println("no찾기"+list);
		model.addAttribute("snsList",list);
		return "sns/sns";
	}
	
	//단건조회
	@GetMapping("snsInfo")
	public String snsInfo(SnsVO snsVO, FileVO fileVO, Model model) {
		SnsVO findVO = snsService.snsInfo(snsVO);
		List<FileVO> list = fileService.selectFileInfo(snsVO.getFeedNo());
		model.addAttribute("snsFileInfo",list);
		model.addAttribute("sns", findVO);
		
		List<SnsVO> declarelist = snsService.snsDeclareList();
		model.addAttribute("snsDeclare",declarelist);
		
		
		return "sns/snsInfo";
	}
	
	//등록 페이지 이동
	@GetMapping("snsInsert")
	public String snsInsert() {
		return "sns/snsInsert";
	}
	
	//등록 처리
	@PostMapping("snsInsert")
	public String snsInsertProcess(MultipartFile[] images, SnsVO snsVO, Model model) {
		//이미지, 파일코드, 파일번호(피드코드번호)
		String snsCode = "J1";
		int snsNum = snsService.selectSnsNum();
		System.out.println("번호생성"+snsNum);
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsVO.setUserNo(userNo);
		int fno = snsService.insertSns(snsVO);
		System.out.println("인서트"+fno);
		fileService.insertFile(images, snsCode,snsNum); //피드번호
		
		//	System.out.println("내번호는"+userNo);
		model.addAttribute("userNo",userNo);
		System.out.println("아이디"+userNo);
		return "redirect:snsInfo?feedNo=" + fno;
	}
	
	
	//수정 페이지
	@GetMapping("snsUpdate")
	public String snsUpdateForm(@RequestParam Integer feedNo, Model model) {
		SnsVO snsVO = new SnsVO();
		snsVO.setFeedNo(feedNo);
		SnsVO findVO = snsService.snsInfo(snsVO);
		model.addAttribute("snsInfo", findVO);
		return "sns/snsUpdate";
	}
	
	//수정 처리
	@PostMapping("snsUpdate")
	@ResponseBody
	public Map<String, Object> snsUpdateAJAXJSON(@RequestBody SnsVO snsVO){
		return snsService.updateSns(snsVO);
	}


	//삭제
	@GetMapping("snsDelete")
	public String snsDelete(MultipartFile[] images, Integer feedNo) {
		fileService.deleteFile(feedNo);
		snsService.deleteSns(feedNo);
		return "sns/sns";
	}
	

}
