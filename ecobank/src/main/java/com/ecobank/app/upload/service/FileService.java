package com.ecobank.app.upload.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
	
	//등록
	public int insertFile(MultipartFile[] images, String fileCode, int codeNo);
	

	//삭제
	public int deleteFile(int fileNo);
}
