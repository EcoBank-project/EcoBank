package com.ecobank.app.upload.service;

import java.util.Map;

public interface FileService {
	
	//등록
	public int insertFile(FileVO fileVO);
	
	//수정
	public Map<String, Object> updateFile(FileVO fileVO);
}
