package com.ecobank.app.chat.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.chat.mapper.ChatMapper;
import com.ecobank.app.chat.service.ChatFollowVO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatRoomDTO;
import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatService;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@Service
public class ChatServiceImpl implements ChatService{
	
	private ChatMapper chatMapper;
	private final Translate translate;
	
	@Autowired
	ChatServiceImpl(ChatMapper chatMapper, Translate translate) {
		this.chatMapper = chatMapper;
		this.translate = translate;
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
	
	// 채팅방 타입 조회
	@Override
	public String chatRoomType(Integer chatNo) {
		return chatMapper.getChatRoomType(chatNo);
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
	
	
	// 채팅방 전체참여자 이름 조회
	@Override
	public List<String> ChatUserList(Integer chatNo) {
		return chatMapper.selectAllChatUser(chatNo);
	}
	
	// 채팅방 참여자 숫자 조회
	@Override
	public int getUsersChatRoom(Integer chatNo) {
		int result = chatMapper.selectUsersChatRoom(chatNo);
		return result;
	}
	// 채팅방 참여자 나가기
	@Override
	public int chatEntryUpdate(Integer userNo, Integer chatNo) {
		return chatMapper.updateChatEntry(userNo, chatNo);
	}
	// 채팅방 삭제
	@Override
	public int chatRoomDelete(Integer chatNo) {
		return chatMapper.deleteChatRoom(chatNo);
	}
	// 채팅방 참여자 삭제
	@Override
	public int ChatPartDelete(Integer chatNo) {
		return chatMapper.deleteChatPart(chatNo);
	}
	// 채팅방 메시지 전부 삭제
	@Override
	public int chatAllMessageDelete(Integer chatNo) {
		return chatMapper.deleteAllMessage(chatNo);
	}
	// 채팅방 남은 사람 조회
	@Override
	public List<String> chatLeaveUser(Integer userNo, Integer chatNo) {
		return chatMapper.selectLeaveUser(userNo, chatNo);
	}
	// 채팅방 방장 조회
	@Override
	public int getRoomManager(Integer userNo, Integer chatNo) {
		int result = chatMapper.selectRoomManager(userNo, chatNo);
		return result;
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
	
	// 채팅방 이름 변경
	@Override
	public int chatNameChangeUpdate(String chatName, Integer chatNo) {
		int result = chatMapper.updateChatChangeName(chatName, chatNo);
		return result;
	}
	
	// 채팅 번역
	@Override
	public String translateMessage(String text, String targetLanguage) {
		Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(targetLanguage));
		return translation.getTranslatedText();
	}
	
	
	
	
	
	
}
