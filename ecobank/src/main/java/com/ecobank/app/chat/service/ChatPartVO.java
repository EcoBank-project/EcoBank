package com.ecobank.app.chat.service;

import lombok.Data;

@Data
public class ChatPartVO {
	private int chatNo;				//채팅방 번호
	private int userNo;				//회원 번호
	private String chatEnterTime;	//채팅 참가자 시간
}
