package com.ecobank.app.chat.service;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ChatRoomVO {
	private Integer chatNo;			//채팅방 번호
	private String chatName;		//채팅방 이름
	private Integer userNo;			//사용자 번호
	private Date chatEnterTime;		//채팅방 생성시간
	private String chatType;		//채팅방 타입
	private Integer chatCreateUser;	//채팅방 주인 
}
