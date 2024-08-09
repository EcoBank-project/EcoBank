package com.ecobank.app.chat.service;

import java.util.List;

// 사용자 기준
public interface ChatService {
	//로그인한 회원 전체 채팅방 조회
	public List<ChatRoomVO> chatRoomList(Integer chatId);
	//로그인한 회원 채팅방 정보 조회
	public ChatRoomVO chatRoomInfo();
	//채팅방 채팅로그 기록 조회
	public List<ChatMessageVO> chatMessageList(Integer chatNo);
	//채팅방 채팅로그 기록 저장
	public int ChatMessageInsert(ChatMessageVO chatMessageVO);
	//채팅방 만들기
	public int ChatRoomInsert(ChatRoomVO chatRoomVO);

}
