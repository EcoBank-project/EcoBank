package com.ecobank.app.chat.service;

import java.util.List;

public interface ChatSearchService {
	//오픈 채팅방 조회
	public List<ChatRoomVO> OpenChatRoomList (int page, String search);
	//오픈 채팅방 총 갯수
	public int getTotalPages(String search);
}
