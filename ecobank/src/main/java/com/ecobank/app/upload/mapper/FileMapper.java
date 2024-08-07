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
}
