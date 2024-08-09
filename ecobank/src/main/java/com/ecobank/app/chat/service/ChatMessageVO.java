package com.ecobank.app.chat.service;

import lombok.Data;

@Data
public class ChatMessageVO {
	private Integer msgNo;		//채팅방 번호
	private String msgContent; 	//채팅 내용
	private String msgType;		//채팅 타입
	private String msgSendTime;	//채팅 시간
	private Integer userNo;		//회원 번호
	private Integer chatNo;		//채팅방 번호
}
