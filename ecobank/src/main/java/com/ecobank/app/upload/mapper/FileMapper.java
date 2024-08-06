package com.ecobank.app.upload.mapper;

import com.ecobank.app.upload.service.FileVO;

public interface FileMapper {
	
	//등록
	public int insertFileInfo(FileVO fileVO);
	
	//수정
	public int updateFileInfo(FileVO fileVO);
}
