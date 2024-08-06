package com.ecobank.app.upload.service;

import lombok.Data;

@Data
public class FileVO {
	private Integer fileNo;		//파일번호
	private String fileName;	//파일이름
	private String filePath;	//파일경로
	private String filetype;	//파일형식
	private String fileCode;	//파일 분류 코드
	private Integer fileCodeNo;	//파일 분류 코드 번호
	
}
