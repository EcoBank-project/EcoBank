package com.ecobank.app.challenge.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.ecobank.app.challenge.service.ChallConfirmVO;
import com.ecobank.app.challenge.service.ChallService;
import com.ecobank.app.challenge.service.ChallVO;
import com.ecobank.app.challenge.service.impl.ChallConfirmServiceImpl;

@Controller
public class ChallengController {
	@Value("${file.upload.path}")
	private String uploadPath;
	
	private ChallService challService;
	
	@Autowired
	public ChallengController(ChallService challService) {
		this.challService = challService;
	}
	
	//챌린지 목록 - 회원용
	//오픈 예정 챌린지(D1)
	@GetMapping("ready")
	public String challready(Model model, String challState) {
		List<ChallVO> list = challService.getDList("D1"); //리스트에 D1만 보이게
        int countD1 = challService.countChallengesByState("D1"); //D1 챌린지 개수

        model.addAttribute("count", countD1);
        model.addAttribute("status", "D1");
		model.addAttribute("list", list);
		return "chall/challenge";
	}
	
	//진행 중인 챌린지(D2)
	@GetMapping("progress")
	public String challprogress(Model model, String challState) {
		List<ChallVO> list = challService.getDList("D2"); //리스트에 D2만 보이게
        int countD2 = challService.countChallengesByState("D2"); //D2 챌린지 개수

        model.addAttribute("count", countD2);
        model.addAttribute("status", "D2");
		model.addAttribute("list", list);
		return "chall/challenge";
	}
	
	//완료된 챌린지(D3)
	@GetMapping("end") 
	public String challend(Model model, String challState) {
		List<ChallVO> list = challService.getDList("D3"); //리스트에 D3만 보이게
        int countD3 = challService.countChallengesByState("D3"); //D3 챌린지 개수

        model.addAttribute("count", countD3);
        model.addAttribute("status", "D3");
		model.addAttribute("list", list);
		return "chall/challenge";
	}
	
	//챌린지 단건 조회 - 회원
	@GetMapping("detail")
	public String challdetail(ChallVO challVO, Model model) {
		ChallVO findVO = challService.challInfo(challVO);
		model.addAttribute("status", "D1");
		model.addAttribute("status", "D2");
		model.addAttribute("status", "D3");
		model.addAttribute("detail", findVO);
		return "chall/detail";
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
