package com.ecobank.app.chat.web;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatService;

@Controller
public class ChatMessageController {
	
	private ChatService chatService;
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	ChatMessageController(ChatService chatService, SimpMessagingTemplate messagingTemplate){
		this.chatService = chatService;
		this.messagingTemplate = messagingTemplate;
	}
	
	// 채팅방 입장
	@MessageMapping("/chat.enter")
	@SendTo("/topic/message")
	public ChatMessageVO enterUser(@Payload ChatMessageVO message) {
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
	public void sendUser(@Payload ChatMessageVO message, Principal principal) {
		// 메시지 저장
		ChatMessageVO chatMessage = chatService.ChatMessageInsert(message);
		String chatType = chatService.chatRoomType(message.getChatNo());
		
		List<String> receiverIds = chatService.ChatUserList(message.getChatNo());
		
		for(String receiverId : receiverIds) {
			// 메시지 번역
//			String lagCode = chatService.laguageCodeSelect(receiverId);
//			String translatedText = chatService.translateMessage(message.getMsgContent(), lagCode);
//			String decodedText = decodeHtmlEntities(translatedText);
//			message.setMsgContent(decodedText);
			
			if("O1".equals(chatType)) {
				//1대1 채팅
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages/" + chatMessage.getChatNo(), message);
			}else if("O2".equals(chatType)){
				//그룹 채팅
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages/" + chatMessage.getChatNo(), message);
				//오픈 채팅	
			}else if("O3".equals(chatType)){
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages/" + chatMessage.getChatNo(), message);			
			}
		}
	}
	// 문자 디코딩
	public static String decodeHtmlEntities(String text) {
        return text.replaceAll("&#39;", "'")
                   .replaceAll("&quot;", "\"")
                   .replaceAll("&amp;", "&")
                   .replaceAll("&lt;", "<")
                   .replaceAll("&gt;", ">");
    }
	// 채팅방 퇴장
	@MessageMapping("/chat.exit/{roomId}")
	public void exitUser(@DestinationVariable Integer roomId, ChatMessageVO message, Principal principal) {
		int result = chatService.getRoomManager(message.getUserNo(), roomId);
		// 채팅방 참가자 나가기
		// 채팅방 주인 아닐경우
		if(result < 1) {
			chatService.chatEntryUpdate(message.getUserNo(), roomId);
			Integer countUser = chatService.getUsersChatRoom(roomId);
			// 채팅방에 아무도 없을 경우
			if(countUser < 1) {
				// 채팅방 메시지 삭제
				chatService.chatAllMessageDelete(roomId);
				// 채팅방 참여자 삭제
				chatService.ChatPartDelete(roomId);
				// 채팅방 삭제
				chatService.chatRoomDelete(roomId);
				
				messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/chatList", message);
			}else {
				// 채팅방 나가는 메시지 저장
				chatService.ChatMessageInsert(message);
				// 채팅방 남은 사람에게 메시지
				messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/chatList", message);
				List<String> receiverIds = chatService.chatLeaveUser(message.getUserNo(), roomId);
				for(String receiverId : receiverIds) {
					messagingTemplate.convertAndSendToUser(receiverId, "/queue/leaveChatRoom", message);
				}
			}
		// 채팅방 주인일 경우
		}else {
			List<String> receiverIds = chatService.chatLeaveUser(message.getUserNo(), roomId);
			
			// 채팅방 메시지 삭제
			chatService.chatAllMessageDelete(roomId);
			// 채팅방 참여자 삭제
			chatService.ChatPartDelete(roomId);
			// 채팅방 삭제
			chatService.chatRoomDelete(roomId);
			
			messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/chatList", message);		
			for(String receiverId : receiverIds) {
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatList", message);
			}
		}
	}
	//채팅방 이름 변경 - 그룹
	@PostMapping("/chat.changeName")
	@ResponseBody
	public void changeRoomName(@RequestParam String chatName, @RequestParam Integer chatNo ) {
		chatService.chatNameChangeUpdate(chatName, chatNo);
		List<String> receiverIds = chatService.ChatUserList(chatNo);
		for(String receiverId : receiverIds) {
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatList", chatNo);
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatUpdate/" + chatNo, chatName);
		}
	}
	
}
