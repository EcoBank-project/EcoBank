package com.ecobank.app.chat.web;


import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.ecobank.app.chat.service.ChatMessageDTO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatRoomDTO;
import com.ecobank.app.chat.service.ChatService;

@Controller
public class ChatMessageController {
	
	private ChatService chatService;
	
	@Autowired
	ChatMessageController(ChatService chatService){
		this.chatService = chatService;
	}
	@Autowired
	private HttpSession httpSession;
	
	// 채팅방 입장
	@MessageMapping("/chat.enter")
	@SendTo("/topic/message")
	public ChatMessageDTO enterUser(@Payload ChatMessageDTO message) {
		return message;
	}
		
	//채팅방 목록
	@MessageMapping("/update.roomList")
	@SendToUser("/queue/roomListUpdate")
    public ChatRoomDTO updateRoomList(ChatRoomDTO chatRoom) {
		System.out.println(chatRoom);
        return chatRoom;
    }
	
	// 채팅방 그룹대화
	@MessageMapping("/chat.message/{roomId}")
	@SendTo("/topic/messages/{roomId}")
	public ChatMessageVO sendUser(@Payload ChatMessageVO message) {
		// 메시지 저장
		ChatMessageVO chatMessage = new ChatMessageVO();
		BeanUtils.copyProperties(message, chatMessage);
		chatService.ChatMessageInsert(chatMessage);

		// 날짜 포맷
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd a hh:mm");
		String formatDate = dateFormat.format(message.getMsgSendTime());
		message.setForMatTime(formatDate);
		
		return message;
	}
	
	// 채팅방 1대1 대화
	
	
	
	// 채팅방 퇴장
	@MessageMapping("/chat.exit")
	@SendTo("/topic/messages")
	public ChatMessageDTO exitUser(@Payload ChatMessageDTO message) {
		return message;
	}
}
