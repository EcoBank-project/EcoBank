package com.ecobank.app.chat.web;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecobank.app.chat.service.ChatFollowVO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatPartVO;
import com.ecobank.app.chat.service.ChatRoomDTO;
import com.ecobank.app.chat.service.ChatRoomUserVO;
import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatService;

@Controller
public class ChatRoomController {
	private ChatService chatService;
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	ChatRoomController(ChatService chatService, SimpMessagingTemplate messagingTemplate){
		this.chatService = chatService;
		this.messagingTemplate = messagingTemplate;
	}
	
	// 채팅방 목록
	@GetMapping("/chatRoom")
	public String ChatRooms(HttpSession httpSession, Model model){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		String nickName = (String) httpSession.getAttribute("nickname");
		String userId = (String) httpSession.getAttribute("useId");
		List<ChatRoomVO> chatList = chatService.chatRoomList(userNo, nickName);
		String lagCode = chatService.laguageCodeSelect(userId);
		
		//번역
//		for(ChatRoomVO chat : chatList) {
//			// 채팅방
//			String translatedText = chatService.translateMessage(chat.getChatName(), lagCode);
//			String decodedText = decodeHtmlEntities(translatedText);
//			chat.setChatName(decodedText);
//		}
		
		ChatRoomUserVO chatRoomUser = chatService.ChatProfileInfo(userNo);
		model.addAttribute("userNo", userNo);
		model.addAttribute("nickname", nickName);
		model.addAttribute("chatRooms", chatList);
		model.addAttribute("lagCode", lagCode);
		model.addAttribute("profiles", chatRoomUser);
		return "chat/chatRoom";
	}
	
	// 채팅방 조회
	@GetMapping("/chatRoom/{chatNo}")
	public String ChatRoom(@PathVariable Integer chatNo , HttpSession httpSession, Model model){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		String nickName = (String) httpSession.getAttribute("nickname");
		String userId = (String) httpSession.getAttribute("useId");
		ChatRoomVO chatRoom = chatService.chatRoomInfo(chatNo, userNo, nickName);
		if(chatRoom == null) {
			return "redirect:/chatRoom";
		}
		
		
		
		List<ChatRoomVO> chatList = chatService.chatRoomList(userNo, nickName);
		String lagCode = chatService.laguageCodeSelect(userId);
		String chatType = chatRoom.getChatType();
		String chatName = chatRoom.getChatName();
		
		ChatRoomUserVO chatRoomUser = chatService.ChatProfileInfo(userNo);
		
		//번역
//		for(ChatRoomVO chat : chatList) {
//			// 채팅방
//			String translatedText = chatService.translateMessage(chat.getChatName(), lagCode);
//			String decodedText = decodeHtmlEntities(translatedText);
//			chat.setChatName(decodedText);
//		}
		
		model.addAttribute("userNo", userNo);
		model.addAttribute("chatType", chatType);
		model.addAttribute("chatName", chatName);
		model.addAttribute("nickname", nickName);
		model.addAttribute("chatRooms", chatList);
		model.addAttribute("lagCode", lagCode);
		model.addAttribute("profiles", chatRoomUser);
		
		return "chat/chatRoom";
	}
	
	// 특정 채팅방 채팅로그 조회
	@PostMapping("/chatRoom/logs")
	@ResponseBody
	public List<ChatMessageVO> ChatRoom(HttpSession httpSession, @RequestParam Integer roomId) {
		List<ChatMessageVO> msgList = chatService.chatMessageList(roomId);
		//메시지 번역
		String userId = (String) httpSession.getAttribute("useId");
		String lagCode = chatService.laguageCodeSelect(userId);
//		for(ChatMessageVO msg : msgList) {
//			//메시지
//			String translatedText = chatService.translateMessage(msg.getMsgContent(), lagCode);
//			String decodedText = decodeHtmlEntities(translatedText);
//			msg.setMsgContent(decodedText);
//			//닉네임
//			translatedText = chatService.translateMessage(msg.getNickName(), lagCode);
//			decodedText = decodeHtmlEntities(translatedText);
//			msg.setNickName(decodedText);
//		}
		
		return msgList;
	}
	
	// 문자 디코딩
	public static String decodeHtmlEntities(String text) {
        return text.replaceAll("&#39;", "'")
                   .replaceAll("&quot;", "\"")
                   .replaceAll("&amp;", "&")
                   .replaceAll("&lt;", "<")
                   .replaceAll("&gt;", ">");
    }	
	// 팔로우 목록 조회
	@GetMapping("/chatRoom/follow")
	@ResponseBody
	public List<ChatFollowVO> chatFollowList(HttpSession httpSession){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		List<ChatFollowVO> followList = chatService.chatFollowList(userNo);
		return followList;
	}
	// 채팅방 목록
	@GetMapping("/chatRoom/chatList")
	@ResponseBody
	public List<ChatRoomVO> chatRoomList(HttpSession httpSession){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		String nickName = (String) httpSession.getAttribute("nickname");
		List<ChatRoomVO> roomlist = chatService.chatRoomList(userNo, nickName);
		
		String userId = (String) httpSession.getAttribute("useId");
		String lagCode = chatService.laguageCodeSelect(userId);
		//번역
//		for (ChatRoomVO chat : roomlist) {
//			// 채팅방
//			String translatedText = chatService.translateMessage(chat.getChatName(), lagCode);
//			String decodedText = decodeHtmlEntities(translatedText);
//			chat.setChatName(decodedText);
//		}
		
		return roomlist;
	}
	
	// 채팅방 생성 - 1대1 채팅 & 그룹채팅
	@PostMapping("/chatRoom/createChat")
	@ResponseBody
	public Integer chatPrivateGroup(HttpSession httpSession, @RequestBody ChatRoomDTO chatRoom){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		
		Integer result = chatService.getChatName(chatRoom.getChatName());
		if(result != null) {
			if("O1".equals(chatRoom.getChatType())){
				chatService.chatStateUpdate(userNo, result);
				return result;
			}else if("O2".equals(chatRoom.getChatType())) {
				return 0;
			}
		}

		Integer chatNo = chatService.ChatRoomInsert(chatRoom, userNo);
		
		List<Integer> userNos = chatRoom.getUserNo();
		for(Integer user : userNos) {
			chatService.ChatUserInsert(chatNo, user);
		};
		return chatNo;
	}
	
	// 채팅 파일 저장
	@PostMapping("/chatRoom/chatMessageFile")
	@ResponseBody
	public String chatMessageFileSave(@RequestPart(value = "messageFile") MultipartFile[] images) {
		String fileURL = chatService.getfileURL(images);
		return fileURL;
	}
	
	// 채팅방 생성 - 오픈 채팅
	@PostMapping("/chatRoom/createOpenChat")
	@ResponseBody
	public Integer chatOpenCreate(HttpSession httpSession,
			                @ModelAttribute ChatRoomVO chatRoom, 
			                @RequestPart(value = "image", required = false) MultipartFile[] images) {
		
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		Integer result = chatService.getChatName(chatRoom.getChatName());
		if(result != null) {
			if("O3".equals(chatRoom.getChatType())){
				return 0;
			}
		}
		
		Integer chatNo = chatService.ChatOpenInsert(chatRoom, userNo, images);
		return chatNo;
	}
	
	// 채팅방 업데이트 - 오픈 채팅
	@PostMapping("/chatRoom/updateOpenChat")
	@ResponseBody
	public String chatOpenUpdate(HttpSession httpSession,
			                @ModelAttribute ChatRoomVO chatRoom, 
			                @RequestPart(value = "image", required = false) MultipartFile[] images) {
		Integer result = chatService.getChatName(chatRoom.getChatName());
		if(result != null) {
			return chatRoom.getChatName();
		}
		chatService.OpenChatChangeUpdate(chatRoom, images);
		
		List<String> receiverIds = chatService.ChatUserList(chatRoom.getChatNo());
		for(String receiverId : receiverIds) {
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatList", chatRoom.getChatNo());
			messagingTemplate.convertAndSendToUser(receiverId, "/queue/chatUpdate/" + chatRoom.getChatNo(), chatRoom.getChatName());
		}
		
		return null;
	}
	
	// 오픈 채팅방 팔로우 초대목록 조회
	@PostMapping("/chatRoom/inviteFollowList")
	@ResponseBody
	public List<ChatFollowVO> chatInviteList(HttpSession httpSession, @RequestParam Integer chatNo){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		List<ChatFollowVO> list = chatService.chatFollowListInfo(userNo, chatNo);
		return list;
	}
	
	// 오픈 채팅방 초대
	@PostMapping("/chatRoom/inviteOpenChat")
	@ResponseBody
	public Integer openChatInvite(@RequestBody ChatRoomDTO chatRoom) {
		List<Integer> userNos = chatRoom.getUserNo();
		for(Integer user : userNos) {
			chatService.ChatUserInsert(chatRoom.getChatNo(), user);
		};
		return chatRoom.getChatNo();
	}
	
	// 프로필 언어 설정
	@GetMapping("/chatRoom/languageChange/{languageCode}")
	@ResponseBody
	public void profilChange(HttpSession httpSession, @PathVariable String languageCode) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		chatService.laguageCodeUpdate(languageCode, userNo);
	}
	
	// 채팅방 만든이 조회
	@PostMapping("/chatRoom/getChatCreateUser")
	@ResponseBody
	public Integer getChatManager(HttpSession httpSession, @RequestParam Integer chatNo) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		int result = chatService.getRoomManager(userNo, chatNo);
		return result;
	}
	// 채팅방 오픈 정보
	@PostMapping("/chatRoom/getChatOpenInfo")
	@ResponseBody
	public ChatRoomVO ChatOpenInfo(HttpSession httpSession, @RequestParam Integer chatNo) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		String nickName = (String) httpSession.getAttribute("nickname");
		ChatRoomVO chatRoom = chatService.chatRoomInfo(chatNo, userNo, nickName);
		return chatRoom;
	}
	// 채팅방 닉네임 정보
	@PostMapping("/chatRoom/getChatUserInfo")
	@ResponseBody
	public List<ChatPartVO> getChatUserInfo(@RequestParam Integer roomId){
		List<ChatPartVO> list = chatService.ChatRoomUsersList(roomId);
		return list;
	}
}
