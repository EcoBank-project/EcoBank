package com.ecobank.app.sns.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecobank.app.common.service.CodeVO;
import com.ecobank.app.common.service.CommonService;
import com.ecobank.app.sns.service.SnsService;
import com.ecobank.app.sns.service.SnsVO;
import com.ecobank.app.upload.service.FileService;
import com.ecobank.app.upload.service.FileVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SnsController {

	// 파일등록
	@Value("${file.upload.path}")
	private String uploadPath;

	private final SnsService snsService;
	private final FileService fileService;
	private final CommonService commonService;

	@Autowired
	private HttpSession httpSession;

	// DI 생성자 주입 방식=> 생성자 선언하기
	// @RequiredArgsConstructor 로 처리함

	// 전체조회
	@GetMapping("sns")
	public String snsList(SnsVO snsVO, Model model) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsVO.setUserNo(userNo);
		List<SnsVO> list = snsService.snsList(snsVO);
		model.addAttribute("snsList", list);
		return "sns/sns";
	}

	// 전체인기조회
	@GetMapping("snsP")
	public String snsPList(SnsVO snsVO, Model model) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsVO.setUserNo(userNo);
		snsVO.setOrderSns(1);
		List<SnsVO> list = snsService.snsList(snsVO);
		model.addAttribute("snsList", list);
		return "sns/sns";
	}

	//검색조회
	@GetMapping("snsSearch")
	public String snsSearchProcess(@RequestParam(required=false) String keyword, SnsVO snsVO, Model model) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsVO.setUserNo(userNo);
		List<SnsVO> list = snsService.snsSearch(keyword, userNo);
		model.addAttribute("snsSearch", list);
		return "sns/snsSearch";
	}

	// 단건조회
	@GetMapping("snsInfo")
	public String snsInfo(SnsVO snsVO, FileVO fileVO, Model model) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsVO.setUserNo(userNo);
		SnsVO findVO = snsService.snsInfo(snsVO);
		List<FileVO> list = fileService.selectFileInfo(snsVO.getFeedNo());
		model.addAttribute("snsFileInfo", list);
		model.addAttribute("sns", findVO);
		// 신고사유목록
		List<CodeVO> declarelist = commonService.codeList("0E");
		model.addAttribute("snsDeclare", declarelist);

		return "sns/snsInfo";
	}

	// 등록 페이지 이동
	@GetMapping("snsInsert")
	public String snsInsert() {
		return "sns/snsInsert";
	}

	// 등록 처리
	@PostMapping("snsInsert")
	public String snsInsertProcess(MultipartFile[] images, SnsVO snsVO) {
		// 이미지, 파일코드, 파일번호(피드코드번호)
		String snsCode = "J1";
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsVO.setUserNo(userNo);
		int fno = snsService.insertSns(snsVO);
		int snsNum = snsVO.getFeedNo();
		fileService.insertFile(images, snsCode, snsNum); // 피드번호
		return "redirect:snsInfo?feedNo=" + snsNum;
	}

	// 수정 페이지
	@GetMapping("snsUpdate")
	public String snsUpdateForm(SnsVO snsVO, FileVO fileVO, Model model) {
		SnsVO findVO = snsService.snsInfo(snsVO);
		List<FileVO> list  = fileService.selectFileInfo(snsVO.getFeedNo());
		//int delete = fileService.deleteFile(snsVO.getFeedNo(),fileVO.getFileNo());
		model.addAttribute("snsInfo", findVO);
		model.addAttribute("fileInfo", list);
		//model.addAttribute("filedelete", delete);
		System.out.println("수정"+findVO);
		System.out.println("이미지"+list);
		//System.out.println("삭제"+delete);
		return "sns/snsUpdate";
	}

	// 수정 처리
	@PostMapping("snsUpdate")
	@ResponseBody
	public Map<String, Object> snsUpdateAJAXJSON(@ModelAttribute SnsVO snsVO, 
			                                     @RequestPart(value="images",required=false) MultipartFile[] images) {
		String snsCode = "J1";
		int snsNum = snsVO.getFeedNo();
		if(images != null) {
			fileService.insertFile(images, snsCode, snsNum); // 피드번호
		}
		System.out.println("피드번호"+snsNum);
		System.out.println("이미지"+images);
		return snsService.updateSns(snsVO);
	}

	// 삭제
	@GetMapping("snsDelete")
	public String snsDelete(Integer feedNo) {
		snsService.deleteSns(feedNo);
		return "sns/sns";
	}

	// 신고하기
	@PostMapping("declareInsert")
	public String snsDeclareProcess(SnsVO snsVO) {
		// 신고하기
		snsService.insertsnsDeclare(snsVO);
		return "redirect:sns";
	}

	// 마이피드 조회
	@GetMapping("mySns")
	public String mySns(SnsVO snsVO, Model model) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsVO.setUserNo(userNo);
		List<SnsVO> list = snsService.mySns(userNo, userNo);
		SnsVO findVO = snsService.countMySns(snsVO);
		List<SnsVO> followerList = snsService.followerInfo(snsVO);
		List<SnsVO> followingList = snsService.followingInfo(snsVO);
		List<SnsVO> blockList = snsService.blockInfo(snsVO);
		model.addAttribute("userNo", userNo);
		model.addAttribute("mySns", list);
		model.addAttribute("countMySns", findVO);
		model.addAttribute("follower", followerList);
		model.addAttribute("following", followingList);
		model.addAttribute("block", blockList);

		return "sns/mySns";
	}
	
	// 회원피드 조회
	@GetMapping("userSns")
	public String otherSns(SnsVO snsVO, Model model) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		Integer result = snsService.selectFollow(userNo, snsVO.getUserNo());
		List<SnsVO> list = snsService.mySns(snsVO.getUserNo(), userNo);
		SnsVO findVO = snsService.countMySns(snsVO);
		model.addAttribute("userSns", list);
		model.addAttribute("countMySns", findVO);
		model.addAttribute("followCheck", result);
		System.out.println("나는"+userNo);
		System.out.println("글쓴사람은"+ snsVO.getUserNo());
		if(userNo == findVO.getUserNo()) {
			return "sns/mySns";
		}
		return "sns/userSns";
	}
}
