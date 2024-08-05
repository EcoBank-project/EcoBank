package com.ecobank.app.chat.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChatMessageVO {
	private int msgNo; 			//메시지 번호
	private String msgContent;	//메시지 내용
	private String msgType;		//메시지 타입
	private Date msgSendTime;   //메시지 전송 시간
	private int userNo;			//회원 번호
	private int chatNo;			//채팅방 번호
}
