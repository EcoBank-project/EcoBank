package com.ecobank.app.challenge.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecobank.app.challenge.mapper.ChallConfirmMapper;
import com.ecobank.app.challenge.service.ChallConfirmService;
import com.ecobank.app.challenge.service.ChallConfirmVO;
import com.ecobank.app.challenge.service.ChallVO;
import com.ecobank.app.challenge.service.LikeDTO;
import com.ecobank.app.challenge.service.MyConfirmDTO;
import com.ecobank.app.challenge.service.ReplyVO;
import com.ecobank.app.upload.mapper.FileMapper;
import com.ecobank.app.upload.service.FileVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChallConfirmServiceImpl implements ChallConfirmService{
	@Value("${file.upload.path}")
	private String uploadPath;
	
	private final ChallConfirmMapper challConfirmMapper;
	private final FileMapper fileMapper;

	@Override
	public List<ChallConfirmVO> confirmList(int challNo) {
		return challConfirmMapper.selectConfirmAll(challNo);
	}

	@Override
	public MyConfirmDTO myConfirm(int userNo, int challNo) {
		MyConfirmDTO dto = challConfirmMapper.myConfirm(userNo, challNo);
		
		//남은 인증 횟수 구하기 - 오늘 했는지(카운트 그대로) or 오늘 인증 안했어(카운트 +1)
		//매퍼에서 인증횟수를 가져와
		int cnt = challConfirmMapper.getConfirmCnt(challNo);
		//오늘 날짜 구하기
		LocalDate today = LocalDate.now();  // 오늘 날짜를 LocalDate로 얻음
		boolean isTodayPresent = false; //현재 날짜가 인증날짜랑 다를때
		List<Date> dates = myCalendar(userNo, challNo); //인증날짜가져와
		for(Date date : dates) { //인증날짜 for문 돌려
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            if(localDate.equals(today)) { //인증날짜랑 오늘날짜 비교했을때
                isTodayPresent = true; //오늘 날짜가 맞다면 멈춰 -> 그대로
                break;
            }
		}
		if(!isTodayPresent) { //오늘 날짜가 인증날짜랑 다르면 cnt에 +1해줘
			cnt += 1;
		}
		dto.setRemainCount(cnt);
		//System.out.println(isTodayPresent);
		return dto;
	}
	
	//나의 인증 상세
	@Override
	public ChallConfirmVO myConfirmInfo(ChallConfirmVO challConfirmVO) {
		return challConfirmMapper.myConfirmDetail(challConfirmVO);
	}
	
	//나의 캘린더
	@Override
	public List<Date> myCalendar(int userNo, int challNo) {
		return challConfirmMapper.getConfirmDate(userNo, challNo);
	}

	@Override
	public ChallVO reviewList(ChallVO challVO) {
		return challConfirmMapper.reviewList(challVO);
	}
	
	//챌린지 참가
	@Override
	public int insertChallEnter(int userNo, int challNo) {
		int result = challConfirmMapper.challEnterInsert(userNo, challNo);
		return result;
	}
	
	//챌린지 참가했는지 안했는지(여부)
	@Override
	public boolean isUserParticipated(int userNo, int challNo) {
		int cnt = challConfirmMapper.enterStatus(userNo, challNo);
		return cnt > 0;
	}
	
	//인증 등록
	@Override
	public int confirmInsert(ChallConfirmVO challConfirmVO) {
		int result = challConfirmMapper.insertConfirmInfo(challConfirmVO);
		return result == 1 ? challConfirmVO.getConfirmNo() : -1;
	}

	//챌린지 인증 삭제(현재 접속한 userno(session), 다른 userno, confirmNo)
	@Transactional
	@Override
	public int confirmDelete(int userNo, int confirmNo) {
		int otherUserNo = challConfirmMapper.findUserNoByConfirmNo(confirmNo);
		if(userNo == otherUserNo) {
			//인증 글에 올린 로컬 파일을 삭제하기 위해서 
			//기존 filemapper에 있던 상세 내역 가져오고(파일 경로 가져오려고)
			List<FileVO> fileList = fileMapper.getMyConfirmFile(confirmNo);
			
			//인증 파일 DB에서 삭제
			fileMapper.deleteConfirmFileInfo(confirmNo);
			
			//로컬에서 실제 파일 삭제
			if(fileList != null && !fileList.isEmpty()) {
				//파일 여러장 삭제
				for(FileVO fileVO : fileList) {
					String filePath = fileVO.getFilePath();
					//실제 파일 저장 경로 = uploadPath
					Path path = Paths.get(uploadPath, filePath); 
					
					//파일이 존재하면 삭제
					if(Files.exists(path)) {
						try {
							Files.delete(path);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			//인증 댓글 삭제
			//challConfirmMapper.deleteReply(confirmNo);
			
			//인증 글 삭제
			int result = challConfirmMapper.deleteConfirmInfo(confirmNo);
			return result;
		}else {
			return 0;
		}
	}

	//챌린지 인증글 남겼는지 아닌지(여부)
	@Override
	public boolean isConfirmed(int userNo, int challNo) {
		return challConfirmMapper.confirmStatus(userNo, challNo) > 0;
	}

	//댓글 목록
	@Override
	public List<ChallConfirmVO> confirmReplyList(ChallConfirmVO challConfirmVO) {
		return challConfirmMapper.selectReplyList(challConfirmVO);
	}

	//댓글 등록
	@Override
	public int replyInsert(int userNo, ReplyVO replyVO) {
		replyVO.setUserNo(userNo);
	    //댓글 등록
	    challConfirmMapper.insertReply(replyVO);
	    //댓글 전체 개수 반환
	    int totalCnt = challConfirmMapper.replyTotalCnt(replyVO.getConfirmNo());
	    System.out.println(totalCnt + "개수몇개???");
	    return totalCnt;
	}
	
	//댓글 삭제(현재 접속한 userno(session), 다른 userno, replyno)
	@Override
	public int replyDelete(int userNo, int confirmReplyNo) {
		int otherUserNo = challConfirmMapper.findUserNoByReplyNo(confirmReplyNo);
		if(userNo == otherUserNo) {
			int result = challConfirmMapper.deleteReply(confirmReplyNo);
			return result;
		}else {
			return 0;
		}
	}
	
	//인증 좋아요 등록
	@Transactional //하나의 작업 단위로 보려고
	@Override
	public LikeDTO confirmLikeInsert(ChallConfirmVO challConfirmVO) {
		int result = 0;
		//서비스 호출
		int status = confirmLikeStatus(challConfirmVO.getUserNo(), challConfirmVO.getConfirmNo());
		LikeDTO likeDto = new LikeDTO();
		if(status == 0) { 
			result = challConfirmMapper.insertConfirmLike(challConfirmVO);
		}else {
			result = challConfirmMapper.deleteConfirmLike(challConfirmVO.getUserNo(), challConfirmVO.getConfirmNo());
		}
		likeDto.setLikeStatus(result);
		//좋아요 전체 개수
		int totalCnt = challConfirmMapper.likeTotalCnt(challConfirmVO.getConfirmNo());
		likeDto.setTotalCnt(totalCnt);
		return likeDto;
	}
	
	//인증 좋아요 상태
	@Override
	public int confirmLikeStatus(int userNo, int confirmNo) {
		return challConfirmMapper.confirmLikeStatus(userNo, confirmNo);
	}

	//인증 신고 사유 목록
	@Override
	public List<ChallConfirmVO> declareList() {
		return challConfirmMapper.selectConfirmDeclare();
	}
	
	//인증 신고 등록
	@Override
	public int declareInsert(ChallConfirmVO challConfirmVO) {
		//challConfirmVO.setConfirmDeclareAt(new Date()); 
		int result = challConfirmMapper.insertConfirmDeclare(challConfirmVO);
		return result == 1 ? challConfirmVO.getConfirmDeclareNo() : -1;
	}

}
