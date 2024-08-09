package com.ecobank.app.chat.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChatMessageVO {
	private Integer msgNo;		//채팅방 번호
	private String msgContent; 	//채팅 내용
	private String msgType;		//채팅 타입
	private Date msgSendTime;	//채팅 시간
	private Integer userNo;		//회원 번호
	private Integer chatNo;		//채팅방 번호
}
