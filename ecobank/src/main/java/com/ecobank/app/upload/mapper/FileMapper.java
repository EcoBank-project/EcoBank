package com.ecobank.app.upload.mapper;

import java.util.List;


import com.ecobank.app.upload.service.FileVO;

public interface FileMapper {
	
	//등록
	public int insertFileInfo(FileVO fileVO);
	
	//수정
	public int deleteFileInfo(int fileVO);
	
	//파일조회
	public List<FileVO> selectSnsFileInfo(int feedNo);
	
	//나의 인증 내역 파일(userNo가 참여한 challConfirmNo별 파일 하나씩만 가져오려고(미리보기느낌))
	public List<FileVO> selectConfirmFileInfo(FileVO fileVO);
}
