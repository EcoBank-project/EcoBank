package com.ecobank.app.chat.web;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecobank.app.chat.service.ChatMessageDTO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatRoomUserVO;
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
	@MessageMapping("/chat.enter/{roomId}")
	public void enterUser(@DestinationVariable Integer roomId, ChatMessageVO message) {
		List<Integer> userNos = message.getUserNos();
		List<String> receiverIds = chatService.ChatUserList(roomId);
		
		for(Integer userNo : userNos) {
			message.setUserNo(userNo);
			message.setMsgContent(chatService.chatNickName(userNo)+"님이 입장하셨습니다");
			chatService.ChatMessageInsert(message);
		}
		for(String receiverId : receiverIds) {
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/enterChatRoom/"+roomId, message);
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatList", roomId);
		}
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
		
		ChatRoomUserVO chatRoomUser = chatService.ChatProfileInfo(message.getUserNo());
		
		message.setProfileImg(chatRoomUser.getProfileImg());
		
		String chatType = chatService.chatRoomType(message.getChatNo());
		List<String> receiverIds = chatService.ChatUserList(message.getChatNo());
		
		for(String receiverId : receiverIds) {
			// 메시지 번역
			String lagCode = chatService.laguageCodeSelect(receiverId);
			String translatedText = null;
			String decodedText = null;
			if(message.getMsgContent() != null) {
				translatedText = chatService.translateMessage(message.getMsgContent(), lagCode);
				decodedText = decodeHtmlEntities(translatedText);
				message.setMsgContent(decodedText);				
			}
			//닉네임
			translatedText = chatService.translateMessage(message.getNickName(), lagCode);
			decodedText = decodeHtmlEntities(translatedText);
			message.setNickName(decodedText);
			
			ChatMessageDTO chatDetail = new ChatMessageDTO();
			BeanUtils.copyProperties(message, chatDetail);
			
			if("O1".equals(chatType)) {
				//1대1 채팅
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages/" + chatMessage.getChatNo(), chatDetail);
			}else if("O2".equals(chatType)){
				//그룹 채팅
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages/" + chatMessage.getChatNo(), chatDetail);
				//오픈 채팅	
			}else if("O3".equals(chatType)){
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages/" + chatMessage.getChatNo(), chatDetail);			
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
		String nickName = principal.getName();
		// 채팅방 참가자 나가기
		
		// 채팅방 주인 아닐경우
		if(result < 1) {
			chatService.chatEntryUpdate(message.getUserNo(), roomId);
			chatService.getUsersChatRoom(roomId, message, nickName);
		// 채팅방 주인일 경우
		}else {
			List<String> receiverIds = chatService.chatLeaveUser(message.getUserNo(), roomId);
			
			// 채팅방 메시지 삭제 & 채팅방 참여자 삭제 & 채팅방 삭제
			chatService.chatAllMessageDelete(roomId);
			
			messagingTemplate.convertAndSendToUser(nickName, "/queue/chatList", message);		
			for(String receiverId : receiverIds) {
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatList", message);
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/leaveChatRoomManager" + roomId, message);
			}
		}
	}
	//채팅방 이름 변경 - 그룹
	@PostMapping("/chat.changeName")
	@ResponseBody
	public String changeRoomName(@RequestParam String chatName, @RequestParam Integer chatNo ) {
		Integer result = chatService.getChatName(chatName);
		if(result != null) {
			return chatName;
		}
		chatService.chatNameChangeUpdate(chatName, chatNo);
		List<String> receiverIds = chatService.ChatUserList(chatNo);
		for(String receiverId : receiverIds) {
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatList", chatNo);
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatUpdate/" + chatNo, chatName);
		}
		return null;
	}
	
}
