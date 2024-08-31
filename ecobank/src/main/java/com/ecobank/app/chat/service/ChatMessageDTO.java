package com.ecobank.app.chat.service;

import lombok.Data;

@Data
public class ChatMessageDTO {
	private Integer msgNo;		//메시지 번호
	private Integer userNo;		//회원 번호
	private String msgContent; 	//채팅 내용
	private String msgType;		//메시지 타입
	
	private String profileImg;
	private String nickName;	//회원 닉네임
	private String forMatTime;	//채팅 시간 포맷
	
	private String msgFilePath;
	private String msgFileType;
}
