package com.ecobank.app.upload.service;

import java.util.List;


import org.springframework.web.multipart.MultipartFile;



public interface FileService {
	
	//등록
	public int insertFile(MultipartFile[] images, String fileCode, int fileCodeNo);
	

	//삭제
	public int deleteFile(int fileNo);
	
	//파일조회
	public List<FileVO> selectFileInfo(int feedNo);
}
