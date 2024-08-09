package com.ecobank.app.chat.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecobank.app.chat.service.ChatMessageDTO;
import com.ecobank.app.chat.service.ChatMessageVO;

@Controller
public class ChatMessageController {
	@Autowired
	private HttpSession httpSession;
	
	// 채팅방 입장
	@MessageMapping("/chat.enter")
	@SendTo("/topic/message")
	public ChatMessageDTO enterUser(@Payload ChatMessageDTO message) {
		return message;
	}

	// 채팅방 대화
	@MessageMapping("/chat.message/{roomId}")
	@SendTo("/topic/messages/{roomId}")
	public ChatMessageDTO sendUser(@Payload ChatMessageDTO message) {
		ChatMessageVO chatMessage = new ChatMessageVO();
		chatMessage.setMsgContent(message.getMsgContent());
		chatMessage.setMsgType(message.getMsgType());
		chatMessage.setMsgSendTime(message.getMsgSendTime());
		chatMessage.setUserNo(message.getUserNo());
		chatMessage.setChatNo(message.getChatNo());
		
		return message;
	}
	
	// 채팅방 퇴장
	@MessageMapping("/chat.exit")
	@SendTo("/topic/messages")
	public ChatMessageDTO exitUser(@Payload ChatMessageDTO message) {
		return message;
	}
}
