package com.ecobank.app.upload.mapper;

import java.util.List;


import com.ecobank.app.upload.service.FileVO;

public interface FileMapper {
	
	//등록
	public int insertFileInfo(FileVO fileVO);
	
	//파일 단건 삭제
	public int deleteFileInfo(int feedNo, int fileNo);
	
	//게시글 삭제 할때 전체 삭제
	public int deleteFileAll(int feedNo);
	
	//파일조회
	public List<FileVO> selectSnsFileInfo(int feedNo);
	
	//나의 인증 내역 파일(userNo가 참여한 challConfirmNo별 파일 하나씩만 가져오려고(미리보기느낌))
	public List<FileVO> selectConfirmFileInfo(FileVO fileVO);
	
	//다른 참가자 인증 내역 파일
	public List<FileVO> selectOtherConfirm(int challNo);
	
	//나의 인증 내역 상세 파일
	public List<FileVO> getMyConfirmFile(int confirmNo);
	
	//인증 파일 삭제
	public int deleteConfirmFileInfo(int confirmNo);
}
