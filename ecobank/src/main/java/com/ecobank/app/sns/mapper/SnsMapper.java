package com.ecobank.app.sns.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.sns.service.SnsReplyVO;
import com.ecobank.app.sns.service.SnsVO;


public interface SnsMapper {
	
	//전체조회
	public List<SnsVO> selectSnsAll(SnsVO snsVO);
	
	//검색조회
	public List<SnsVO> searchSnsAll(SnsVO snsVO);
	
	//단건조회 
	public SnsVO selectSnsInfo(SnsVO snsVO);
	
	//등록
	public int insertSnsInfo(SnsVO snsVO);
	
	//수정
	public int updateSnsInfo(SnsVO snsVO);
	
	//삭제
	public int deleteSnsInfo(int snsVO);
	
	//댓글 전체 삭제
	public int deleteSnsReplyAll(int SnsNo);
	
	//로그인정보 저장
	public int getUserNo();
	
	// sns신고 사유 조회
	public List<SnsVO> selectSnsDeclare();
	
	// sns신고 등록
	public int insertSnsDeclare(SnsVO snsVO);	
	
	//마이피드 조회
	public List<SnsVO> selectMySns(SnsVO snsVO);
	
	//마이피드 세부 정보
	public SnsVO countMySns(SnsVO snsVO);
	
	//팔로우 여부 확인
	public Integer selectFollow(@Param("userNo") int userNo, @Param("otherNo") int otherNo);
	
	//팔로잉 목록
	public List<SnsVO> followingList(SnsVO snsVO);
	
	//팔로워 목록
	public List<SnsVO> followerList(SnsVO snsVO);
	
	//차단 목록
	public List<SnsVO> blockList(SnsVO snsVO);
}
