package com.ecobank.app.upload.service;

import java.util.List;


import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	//등록
	public int insertFile(MultipartFile[] images, String fileCode, int fileCodeNo);

	//삭제
	public int deleteFile(int feedNo, int fileNo);
	
	//게시글 삭제 할때 전체 삭제
	public int deleteFileAll(int feedNo);
		
	//파일조회
	public List<FileVO> selectFileInfo(int feedNo);
	
	//나의 인증 내역 파일(userNo가 참여한 challConfirmNo별 파일 하나씩만 가져오려고(미리보기느낌))
	public List<FileVO> selectFileInfo(int userNo, int challNo, String fileCode);
	
	//다른 참가자 인증 내역 파일
	public List<FileVO> selectFileOtherInfo(int challNo);
	
	//나의 인증 내역 파일 상세
	public List<FileVO> selectGetMyInfo(int confirmNo);
	
	//인증 파일 삭제
	public int confirmFileDelete(int confirmNo);
}
