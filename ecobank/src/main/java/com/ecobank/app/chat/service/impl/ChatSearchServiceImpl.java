package com.ecobank.app.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.chat.mapper.ChatSearchMapper;
import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatSearchService;

@Service
public class ChatSearchServiceImpl implements ChatSearchService{
	
	private ChatSearchMapper chatSearchMapper; 
	
	@Autowired
	ChatSearchServiceImpl(ChatSearchMapper chatSearchMapper){
		this.chatSearchMapper = chatSearchMapper;
	}
	//오픈 채팅방 조회
	@Override
	public List<ChatRoomVO> OpenChatRoomList(int page, String search) {
		int itemsPerPage = 8;
		int startRow = (page - 1) * itemsPerPage + 1;
        int endRow = page * itemsPerPage;
		return chatSearchMapper.selectOpenChatAll(search, startRow, endRow);
	}
	//오픈 채팅방 갯수
	@Override
	public int getTotalPages(String search) {
		int itemsPerPage = 8;
		int totalItems = chatSearchMapper.selectOpenChatCnt(search); 
		return (int) Math.ceil((double) totalItems / itemsPerPage);
	}
}
