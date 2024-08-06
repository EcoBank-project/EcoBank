package com.ecobank.app.challenge.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.ecobank.app.challenge.service.ChallService;
import com.ecobank.app.challenge.service.ChallVO;

@Controller
public class ChallengController {
	@Value("${file.upload.path}")
	private String uploadPath;
	
	private ChallService challService;
	
	@Autowired
	public ChallengController(ChallService challService) {
		this.challService = challService;
	}
	
	//챌린지 전체 조회 - 회원용
	@GetMapping("ready")
	public String challready(Model model) {
		List<ChallVO> list = challService.challList();
		model.addAttribute("ready", list);
		return "chall/ready";
	}
	
	//챌린지 목록 - 관리자
	@GetMapping("challList")
	public String challList(Model model) {
		List<ChallVO> list = challService.challList();
		model.addAttribute("challList", list);
		return "admins/adminChallList";
	}
	
	//챌린지 단건 조회 - 관리자
	@GetMapping("challInfo")
	public String challInfo(ChallVO challVO, Model model) {
		ChallVO findVO = challService.challInfo(challVO);
		model.addAttribute("challenge", findVO);
		return "admins/adminChallInfo";
	}
	
	//챌린지 등록 - 페이지
	//@Scheduled
	@GetMapping("challInsert")
	public String challInsertForm() {
		return "admins/adminChallInsert";
	}
	
	//@PostMapping("challInsert")
	public String challInsertProcess(ChallVO challVO) {
		int cno = challService.challInsert(challVO);
		return "redirect:adminChallInfo?challNo=" + cno;
	}
	    
	// 등록 - 처리 : URI - boardInsert / PARAMETER - BoardVO(QueryString)
	//             RETURN - 단건조회 다시 호출
	@PostMapping("challInsert")
	public String challInsertProcess(ChallVO challVO, @RequestPart MultipartFile[] images) {
		//log.info(images[0].getOriginalFilename()); //파일 이름만 가져온거
		int index = 0;
		
		for(MultipartFile image : images) {
			//1)원래 파일이름
			String fileName = image.getOriginalFilename();
			
			//고유한 식별자로 이미지 저장해서 클라이언트가 업로드했을때 파일이름이 겹치지 않도록 하는거
			UUID uuid = UUID.randomUUID();
			String uniqueFileName = uuid + "_" + fileName;
			
			//2)실제로 저장할 경로를 생성 : 서버의 업로드 경로 + 파일이름
			String saveName = uploadPath + File.separator + uniqueFileName; //""가 /와 같아
			
			Path savePath = Paths.get(saveName); //여기에 경로 담았음
			
			if(index == 0) {
				challVO.setMainImg(uniqueFileName); //파일의 정보를 가져와서 challVO에 파일의 이름을 넣어줌
			}else {
				challVO.setDetailImg(uniqueFileName);
			}
			index++;
			
			//3)*파일 작성(파일 업로드)
			try {
				image.transferTo(savePath); //*실제 경로 지정 /Path는 경로/transferTo=햇살
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		int cno = challService.challInsert(challVO);
		return "redirect:adminChallInfo?challNo=" + cno;
	}
	
}
