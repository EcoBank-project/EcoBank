package com.ecobank.app.chat.service;

import java.util.List;

import lombok.Data;

@Data
public class ChatRoomDTO {
	private String chatName;		//채팅방 이름
	private List<Integer> userId;	//사용자 번호
}
