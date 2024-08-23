package com.ecobank.app.chat.service;

import lombok.Data;

@Data
public class ChatRoomUserVO {
	private Integer chatNo;		//채팅방 번호
	private Integer userNo;		//참가자 번호
	private String profileImg;  //유저 이미지
}
