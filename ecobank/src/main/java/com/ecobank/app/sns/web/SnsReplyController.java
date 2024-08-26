package com.ecobank.app.sns.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecobank.app.common.service.CommonService;
import com.ecobank.app.sns.service.SnsReplyService;
import com.ecobank.app.sns.service.SnsReplyVO;
import com.ecobank.app.sns.service.SnsService;
import com.ecobank.app.upload.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SnsReplyController {

	private final SnsReplyService snsReplyService;
	private final FileService fileService;
	//ajax로 보내기!
	//DI 생성자 주입 방식=> 생성자 선언하기
	

	
	@Autowired
	private HttpSession httpSession;
	
	//피드별 댓글 조회
	@GetMapping("snsReply")
	public List<SnsReplyVO> snsReplyInfo(SnsReplyVO snsReplyVO) {
		 return snsReplyService.snsReplyInfo(snsReplyVO);
	}
	
	//등록 처리
	@PostMapping("snsReply")
	public SnsReplyVO snsReplyInsertProcess(SnsReplyVO snsReplyVO) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsReplyVO.setUserNo(userNo);
		int fReplyno = snsReplyService.insertSnsReply(snsReplyVO);
		return snsReplyVO;
	}
	
	//삭제
	@DeleteMapping("snsReply")
	public int snsReplyDelete(Integer replyNo) {
		return snsReplyService.deleteSnsReply(replyNo);
	}
	
	//좋아요 등록
	@PostMapping("likeInsert")
	public int insertSnsLike(SnsReplyVO snsReplyVO) {
		int userNo = (Integer) httpSession.getAttribute("userNo");
		snsReplyVO.setUserNo(userNo);
		return snsReplyService.insertSnsLike(snsReplyVO);
	}
	
	//좋아요 삭제
	@DeleteMapping("likeDelete")
	public int deleteSnsLike(SnsReplyVO snsReplyVO) {
		return snsReplyService.deleteSnsLike(snsReplyVO);
	}
	
	//팔로우 등록
	@PostMapping("insertFollow")
	public SnsReplyVO insertFollow(SnsReplyVO snsReplyVO) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		snsReplyVO.setUserNo(userNo);
		int followerId = snsReplyService.insertFollow(snsReplyVO);
		return snsReplyVO;
	}
	
	//팔로우 삭제
	@DeleteMapping("deleteFollow")
	public int deleteFollow(SnsReplyVO snsReplyVO) {
		return snsReplyService.deleteFollow(snsReplyVO);
	}
	
	//차단 등록
	@PostMapping("insertBlock")
	public SnsReplyVO insertBlock(SnsReplyVO snsReplyVO) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		int blockId = snsReplyService.insertBlock(snsReplyVO);
		return snsReplyVO;
	}
	
	//차단 삭제
	@DeleteMapping("deleteBlock")
	public int deleteBlock(SnsReplyVO snsReplyVO) {
		return snsReplyService.deleteBlock(snsReplyVO);
	}
	
	//파일 삭제
	@DeleteMapping("deletesnsImg")
	public int deleteFile(int feedNo) {
		return fileService.deleteFile(feedNo);
	}
}
