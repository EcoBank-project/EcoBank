package com.ecobank.app.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.chat.mapper.ChatMapper;
import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService{
	
	private ChatMapper chatMapper;
	
	@Autowired
	ChatServiceImpl(ChatMapper chatMapper) {
		this.chatMapper = chatMapper;
	}
	
	@Override
	public List<ChatRoomVO> chatRoomList(Integer userNo) {
		return chatMapper.selectChatRoomAll(userNo);
	}

	@Override
	public ChatRoomVO chatRoomInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ChatRoomInsert(ChatRoomVO chatRoomVO) {
		// TODO Auto-generated method stub
		return 0;
	}

}
