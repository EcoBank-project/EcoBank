package com.ecobank.app.chat.service;

import lombok.Data;

@Data
public class ChatFileVO {
	private Integer chatFileNo; 		//채팅 파일 번호
	private String msgFilePath;			//메시지 파일 경로
	private String msgFileType;			//메시지 파일 형식
	private Integer msgNo;				//메시지 번호
	private Integer chatNo;				//채팅방 번호
}
