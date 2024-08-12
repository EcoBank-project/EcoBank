package com.ecobank.app.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.chat.mapper.ChatMapper;
import com.ecobank.app.chat.service.ChatFollowVO;
import com.ecobank.app.chat.service.ChatMessageDTO;
import com.ecobank.app.chat.service.ChatMessageVO;
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
	// 채팅방 채팅 로그 기록
	@Override
	public int ChatMessageInsert(ChatMessageVO chatMessageVO) {
		int result = chatMapper.insertChatMessage(chatMessageVO); 
		return result;
	}
	// 채팅방 채팅 로그 조회
	@Override
	public List<ChatMessageDTO> chatMessageList(Integer chatNo) {
		return chatMapper.selectChatMessage(chatNo);
	}
	// 채팅방 만들기
	@Override
	public int ChatRoomInsert(String chatName, Integer userNo) {
		return chatMapper.insertChatRoom(chatName, userNo);
	}
	// 채팅방 만들기 - 참여자
	@Override
	public int ChatUserInsert(String chatName, Integer userNo) {
		return chatMapper.insertChatUser(chatName, userNo);
	}
	// 채팅 팔로우 목록
	@Override
	public List<ChatFollowVO> chatFollowList(Integer userNo) {
		return chatMapper.selectChatFollowAll(userNo);
	}

	
	
	
}
