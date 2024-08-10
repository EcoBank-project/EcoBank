package com.ecobank.app.chat.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChatMessageDTO {
	private int userNo;			//회원 번호
	private int chatNo;			//채팅방 번호
	private String nickName;	//회원 닉네임
	private String msgContent;	//메시지 내용
	private String msgType;		//메시지 타입
	private String msgSendTime; //메시지 포맷 시간
	private Date formatTime;    //메시지 전송 시간
}
