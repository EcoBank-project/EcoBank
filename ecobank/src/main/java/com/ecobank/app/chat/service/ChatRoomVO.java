package com.ecobank.app.chat.service;

import java.util.Date;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;

@Data
public class ChatRoomVO {
	private int chatNo;				//채팅방 번호
	private String chatName;		//채팅방 이름
	private Date chatCreateTime;	//채팅방 생성시간
}
