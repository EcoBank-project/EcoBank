package com.ecobank.app.chat.service;

import java.util.List;

import lombok.Data;

@Data
public class ChatRoomDTO {
	private String chatName;		//채팅방 이름
	private List<Integer> userNo;	//사용자 번호
	private List<String> userName;  //사용자 아이디
	private String chatType;		//채팅방 타입
}
