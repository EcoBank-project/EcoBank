package com.ecobank.app.chat.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.chat.mapper.ChatMapper;
import com.ecobank.app.chat.service.ChatFollowVO;
import com.ecobank.app.chat.service.ChatMessageDTO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatRoomDTO;
import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService{
	
	private ChatMapper chatMapper;
	
	@Autowired
	ChatServiceImpl(ChatMapper chatMapper) {
		this.chatMapper = chatMapper;
	}
	// 채팅방 목록 조회
	@Override
	public List<ChatRoomVO> chatRoomList(Integer userNo, String nickName) {
		List<ChatRoomVO> chatList = chatMapper.selectChatRoomAll(userNo);
		for(ChatRoomVO chat : chatList) {
			if(chat.getChatType().equals("O1")) {
				String[] users = chat.getChatName().split("-");
				String chatRoomName = nickName.equals(users[0]) ? users[1] : users[0];
				chat.setChatName(chatRoomName);
			}
		}
		return chatList;
	}

	@Override
	public ChatRoomVO chatRoomInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// 채팅방 채팅 로그 기록
	@Override
	public ChatMessageVO ChatMessageInsert(ChatMessageVO message) {
		int result = chatMapper.insertChatMessage(message);
		message.setForMatTime(formatMessageDate(message.getMsgSendTime()));
		return message;
	}
	// 채팅방 채팅 로그 조회
	@Override
	public List<ChatMessageVO> chatMessageList(Integer chatNo) {
		List<ChatMessageVO> msgList = chatMapper.selectChatMessage(chatNo);
		for(ChatMessageVO msg : msgList) {
			msg.setForMatTime(formatMessageDate(msg.getMsgSendTime()));
		}
		return msgList;
	}
	
	
	// 채팅방 만들기
	@Override
	public int ChatRoomInsert(ChatRoomDTO chatRoom, Integer userNo) {
		ChatRoomVO chatRoomVO = new ChatRoomVO();
		chatRoomVO.setUserNo(userNo);
		chatRoomVO.setChatName(chatRoom.getChatName());
		chatRoomVO.setChatType(chatRoom.getChatType());
		chatMapper.insertChatRoom(chatRoomVO);
		
		return chatRoomVO.getChatNo();
	}
	
	
	// 채팅방 만들기 - 참여자
	@Override
	public int ChatUserInsert(Integer chatNo, Integer userNo) {
		return chatMapper.insertChatUser(chatNo, userNo);
	}
	
	
	// 채팅방 참여자 조회
	@Override
	public List<String> ChatUserList(Integer chatNo) {
		return chatMapper.selectChatUser(chatNo);
	}
	
	
	// 채팅 팔로우 목록
	@Override
	public List<ChatFollowVO> chatFollowList(Integer userNo) {
		return chatMapper.selectChatFollowAll(userNo);
	}

	
	
	
	// 날짜 포맷
	private String formatMessageDate(Date msgSendTime) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd a hh:mm");
	    return dateFormat.format(msgSendTime);
	}
	
}
