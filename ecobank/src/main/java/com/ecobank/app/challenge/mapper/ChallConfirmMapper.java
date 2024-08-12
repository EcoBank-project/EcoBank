package com.ecobank.app.challenge.mapper;

import java.util.Date;
import java.util.List;

import com.ecobank.app.challenge.service.ChallConfirmVO;
import com.ecobank.app.challenge.service.ChallVO;
import com.ecobank.app.challenge.service.MyConfirmDTO;

public interface ChallConfirmMapper {
	//인증 전체 조회 - 챌린지 상세에서 다른 참가자 인증 내역
	//public List<ChallConfirmVO> selectConfirmAll(int challNo);
	
	//인증 단건 조회 - 챌린지 상세에서 다른 참가자 인증 상세
	public ChallConfirmVO selectConfirmInfo(ChallConfirmVO chalConfirmVO);
	
	//나의 인증 내역
	public MyConfirmDTO myConfirm(int userNo, int challNo); 
	//public MyConfirmDTO myConfirm(MyConfirmDTO dto);
	
	//나의 인증 상세
	
	//인증날짜 가져오기(남은 인증 횟수가져오려고)
	public List<Date> getConfirmDate(int userNo, int challNo);
	
	//인증 횟수 가져오려고(+1할지 말지)
	public int getConfirmCnt(int challNo);
	
	//상세이미지(+리뷰)
	public ChallVO reviewList(ChallVO challVO);
	
	//챌린지 참가 등록하기
	public int challEnterInsert(ChallVO challVO);
}
