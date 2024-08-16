package com.ecobank.app.chat.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.ecobank.app.chat.service.ChatMessageDTO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatService;

@Controller
public class ChatMessageController {
	
	private ChatService chatService;
	
	@Autowired
	ChatMessageController(ChatService chatService){
		this.chatService = chatService;
	}
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	// 채팅방 입장
	@MessageMapping("/chat.enter")
	@SendTo("/topic/message")
	public ChatMessageDTO enterUser(@Payload ChatMessageDTO message) {
		return message;
	}
		
	//채팅방 목록
	@MessageMapping("/update.chatList")
    public void updateRoomList(Integer chatNo) { 
		List<String> receiverIds = chatService.ChatUserList(chatNo);
		for(String receiverId : receiverIds) {
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatList", chatNo);
		}
    }

	// 채팅방 대화
	@MessageMapping("/chat.message/{roomId}")
	public void sendUser(@Payload ChatMessageVO message) {
		// 메시지 저장
		ChatMessageVO chatMessage = chatService.ChatMessageInsert(message);
		String chatType = chatService.chatRoomType(message.getChatNo());
		if("O1".equals(chatType)) {
			List<String> receiverIds = chatService.ChatUserList(message.getChatNo());
			for(String receiverId : receiverIds) {
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages/" + chatMessage.getChatNo(), message);
			}
		}else if("O2".equals(chatType)){
			messagingTemplate.convertAndSend("/topic/messages/" + chatMessage.getChatNo(), message);			
		}
	}
	
	// 채팅방 퇴장
	@MessageMapping("/chat.exit/{roomId}")
	@Transactional
	public void exitUser(@DestinationVariable Integer roomId, ChatMessageVO message) {
		// 채팅방 참가자 나가기
		chatService.chatEntryUpdate(message.getUserNo(), roomId);
		Integer countUser = chatService.getUsersChatRoom(roomId);
		if(countUser < 1) {
			// 채팅방 메시지 삭제
			chatService.chatAllMessageDelete(roomId);
			// 채팅방 참여자 삭제
			chatService.ChatPartDelete(roomId);
			// 채팅방 삭제
			chatService.chatRoomDelete(roomId);
		}else {
			// 채팅방 나가는 메시지 저장
			chatService.ChatMessageInsert(message);
			// 채팅방 남은 사람에게 메시지
			List<String> receiverIds = chatService.chatLeaveUser(message.getUserNo(), roomId);
			for(String receiverId : receiverIds) {
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/leaveChatRoom", message);
			}
		}
	}
	
}
