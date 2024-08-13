package com.ecobank.app.chat.web;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecobank.app.chat.service.ChatFollowVO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatRoomDTO;
import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatService;

@Controller
public class ChatRoomController {
	private ChatService chatService;
	
	@Autowired
	ChatRoomController(ChatService chatService){
		this.chatService = chatService;
	}
	
	// 채팅방 목록
	@GetMapping("/chatRoom")
	public String ChatRooms(HttpSession httpSession, Model model){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		String nickname = (String) httpSession.getAttribute("nickname");
		
		List<ChatRoomVO> chatList = chatService.chatRoomList(userNo);
		model.addAttribute("userNo", userNo);
		model.addAttribute("nickname", nickname);
		model.addAttribute("chatRooms", chatList);
		return "chat/chatRoom";
	}
	
	// 특정 채팅방 채팅로그 조회
	@GetMapping("/chatRoom/{roomId}")
	@ResponseBody
	public List<ChatMessageVO> ChatRoom(@PathVariable Integer roomId) {
		List<ChatMessageVO> msgList = chatService.chatMessageList(roomId);
		for(ChatMessageVO msg : msgList) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd a hh:mm");
			String formatDate = dateFormat.format(msg.getMsgSendTime());
			msg.setForMatTime(formatDate);
		}
		return msgList;
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
		List<ChatRoomVO> roomlist = chatService.chatRoomList(userNo);
		return roomlist;
	}
	
	// 채팅방 생성 - 1대1 채팅 & 그룹채팅
	@PostMapping("/chatRoom/CreateChat")
	@ResponseBody
	public Integer chatGroup(HttpSession httpSession, @RequestBody ChatRoomDTO chatRoom){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		Integer chatNo = chatService.ChatRoomInsert(chatRoom, userNo);
		
		List<Integer> userNos = chatRoom.getUserNo();
		for(Integer user : userNos) {
			chatService.ChatUserInsert(chatNo, user);
		};
		return chatNo;
	}
}
