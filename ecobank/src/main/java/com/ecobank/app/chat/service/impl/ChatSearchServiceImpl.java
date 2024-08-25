package com.ecobank.app.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ecobank.app.chat.mapper.ChatMapper;
import com.ecobank.app.chat.mapper.ChatSearchMapper;
import com.ecobank.app.chat.service.ChatCheckDTO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatSearchService;

@Service
public class ChatSearchServiceImpl implements ChatSearchService{
	
	private ChatSearchMapper chatSearchMapper; 
	private ChatMapper chatMapper;
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	ChatSearchServiceImpl(ChatSearchMapper chatSearchMapper, ChatMapper chatMapper, SimpMessagingTemplate messagingTemplate){
		this.chatSearchMapper = chatSearchMapper;
		this.chatMapper = chatMapper;
		this.messagingTemplate = messagingTemplate;
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
	//오픈 채팅방 체크
	@Override
	public ChatCheckDTO openChatCheck(Integer chatNo, Integer userNo) {
		ChatCheckDTO chatCheck = new ChatCheckDTO();
		int chatCnt = chatSearchMapper.selectOpenUserCheck(chatNo, userNo);
		String passWord = chatSearchMapper.selectOpenChatPasswordCheck(chatNo);
		if(chatCnt > 0) {
			chatCheck.setParticipating(true);
		}else {
			chatCheck.setParticipating(false);
		}
		if(passWord != null) {
			chatCheck.setRequiresPassword(true);
		}else {
			chatCheck.setRequiresPassword(false);
		}
		return chatCheck;
	}
	//오픈 채팅방 비밀번호 체크
	@Override
	public Boolean ChatPasswordComparison(Integer chatNo, Integer userNo, String passWord) {
		String dbPassword = chatSearchMapper.selectOpenChatPasswordCheck(chatNo);
		Boolean check = null;
		if(dbPassword.equals(passWord)) {
			check = true;
			
			//채팅방 참여
			chatMapper.insertChatUser(chatNo, userNo);
			
			//메시지 기록
			ChatMessageVO message = new ChatMessageVO();
			message.setChatNo(chatNo);
			message.setUserNo(userNo);
			message.setMsgType("I3");
			message.setMsgContent(chatMapper.selectChatUserName(userNo)+"님이 입장하셨습니다");
			chatMapper.insertChatMessage(message);
			
			List<String> receiverIds = chatMapper.selectAllChatUser(chatNo);
			for(String receiverId : receiverIds) {
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/enterChatRoom/"+chatNo, message);
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatList", chatNo);
			}
		}else {
			check = false;
		}
		return check;
	}
	// 비밀번호 없이 들어가기
	@Override
	public void ChatSearchOpenEnter(Integer chatNo, Integer userNo) {
		chatMapper.insertChatUser(chatNo, userNo);
		
		//메시지 기록
		ChatMessageVO message = new ChatMessageVO();
		message.setChatNo(chatNo);
		message.setUserNo(userNo);
		message.setMsgType("I3");
		message.setMsgContent(chatMapper.selectChatUserName(userNo)+"님이 입장하셨습니다");
		chatMapper.insertChatMessage(message);
		
		List<String> receiverIds = chatMapper.selectAllChatUser(chatNo);
		for(String receiverId : receiverIds) {
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/enterChatRoom/"+chatNo, message);
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatList", chatNo);
		}
	}
}
