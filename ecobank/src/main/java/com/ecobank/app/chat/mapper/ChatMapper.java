package com.ecobank.app.chat.mapper;

import java.util.List;

import com.ecobank.app.chat.service.ChatRoomVO;

// 서버 기준
public interface ChatMapper {
	// 전체 조회
	public List<ChatRoomVO> selectChatRoomAll();
	// 단건 조회
	public ChatRoomVO selectChatRoomInfo(ChatRoomVO chatRoomVO);
	// 만들기
	public int insertChatRoomInfo(ChatRoomVO chatRoomVO);
}
