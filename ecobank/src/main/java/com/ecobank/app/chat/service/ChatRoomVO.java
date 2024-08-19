package com.ecobank.app.chat.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChatRoomVO {
	private Integer chatNo;			//채팅방 번호
	private String chatName;		//채팅방 이름
	private Integer userNo;			//사용자 번호
	private Date chatEnterTime;		//채팅방 생성시간
	private String chatType;		//채팅방 타입
	private Integer chatCreateUser;	//채팅방 주인
	
	private String chatPassWord;	//채팅방 비밀번호
	private String chatSearchAllow;	//채팅방 검색허용
	private String chatImage;		//채팅방 이미지
}
