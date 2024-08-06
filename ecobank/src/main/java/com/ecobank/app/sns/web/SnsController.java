package com.ecobank.app.sns.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ecobank.app.sns.service.SnsService;
import com.ecobank.app.sns.service.SnsVO;
import com.ecobank.app.upload.service.FileService;

@Controller
public class SnsController {
	
	private SnsService snsService;
	private FileService fileService;
	
	
	//DI 생성자 주입 방식=> 생성자 선언하기
	@Autowired
	public SnsController(SnsService snsService, FileService fileService){
		this.snsService = snsService;
		this.fileService = fileService;
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
		
		fileService.insertFile(images, snsCode,snsNum); //피드번호
		int fno = snsService.insertSns(snsVO);

		
		return "redirect:snsInfo?feedNo=" + fno;
	}
	
	
	//수정
	
	
	
	
	
	
	//삭제
	@GetMapping("snsDelete")
	public String snsDelete(Integer feedNo) {
		snsService.deleteSns(feedNo);
		return "redirect:snsList";
	}

}
