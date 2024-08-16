package com.ecobank.app.chat.web;


import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

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
	@MessageMapping("/chat.exit")
	@SendTo("/topic/messages")
	public ChatMessageDTO exitUser(@Payload ChatMessageDTO message) {
		return message;
	}
	
	// 날짜 포맷
	private String formatMessageDate(Date msgSendTime) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd a hh:mm");
	    return dateFormat.format(msgSendTime);
	}
}
