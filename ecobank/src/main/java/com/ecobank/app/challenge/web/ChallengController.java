package com.ecobank.app.challenge.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
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
        int countD1 = challService.countChallengesByState("D1");
        int countD2 = challService.countChallengesByState("D2");
        int countD3 = challService.countChallengesByState("D3");

        model.addAttribute("countD1", countD1);
        model.addAttribute("countD2", countD2);
        model.addAttribute("countD3", countD3);
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
		return "redirect:challInfo?challNo=" + cno;
	}
	    
	// 등록 - 처리 : URI - boardInsert / PARAMETER - BoardVO(QueryString)
	//             RETURN - 단건조회 다시 호출
	@PostMapping("challInsert")
	public String challInsertProcess(ChallVO challVO, @RequestPart MultipartFile[] images) {
		int index = 0;
		
		for(MultipartFile image : images) {
			//1)원래 파일이름
			String fileName = image.getOriginalFilename();
			//System.out.println(fileName);
			//고유한 식별자로 이미지 저장해서 클라이언트가 업로드했을때 파일이름이 겹치지 않도록 하는거
			UUID uuid = UUID.randomUUID();
			String uniqueFileName = uuid + "_" + fileName;
			
			//2)실제로 저장할 경로를 생성 : 서버의 업로드 경로 + 파일이름
			String saveName = uploadPath + File.separator + uniqueFileName; //""가 /와 같아
			//System.out.println(saveName);
			Path savePath = Paths.get(saveName); //여기에 경로 담았음
			//System.out.println(savePath);
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
//		String challCode = "J2";
//		int challNo = challService.selectChallNum();
//		
//		fileService.insertFile(images, challCode, challNo); //피드번호
		int cno = challService.challInsert(challVO);
		return "redirect:challInfo?challNo=" + cno;
	}
	
	// 수정 - 페이지
	@GetMapping("challUpdate")
	public String challUpdateForm(ChallVO challVO, Model model) {
		ChallVO findVO = challService.challInfo(challVO);
		model.addAttribute("challup", findVO);
		return "admins/adminChallUpdate";
	}
	
	// 수정 - 처리 
	@PostMapping("challUpdate")
	@ResponseBody
	public Map<String, Object> challUpdateProcess(ChallVO challVO, @RequestPart MultipartFile[] images){ 
		System.out.println("ddddddd");
		int index = 0;
		    	
		for(MultipartFile image : images) {
			//1)원래 파일이름
			String fileName = image.getOriginalFilename();
			//System.out.println(fileName);
			//고유한 식별자로 이미지 저장해서 클라이언트가 업로드했을때 파일이름이 겹치지 않도록 하는거
			UUID uuid = UUID.randomUUID();
			String uniqueFileName = uuid + "_" + fileName;
			
			//2)실제로 저장할 경로를 생성 : 서버의 업로드 경로 + 파일이름
			String saveName = uploadPath + File.separator + uniqueFileName; //""가 /와 같아
			//System.out.println(saveName);
			Path savePath = Paths.get(saveName); //여기에 경로 담았음
			//System.out.println(savePath);

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
//		String challCode = "J2";
//		int challNo = challService.selectChallNum();
//		
//		fileService.insertFile(images, challCode, challNo); //피드번호
		return challService.challUpdate(challVO);
	}
	
	//챌린지 목록 - 관리자
	@GetMapping("scoreList")
	public String scoreList(Model model) {
		List<Map<String, Object>> list = challService.scoreList();
		
		model.addAttribute("scoreList", list);
		return "admins/adminScoreList";
	}
	
	
	
}
