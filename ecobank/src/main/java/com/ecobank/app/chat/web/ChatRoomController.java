package com.ecobank.app.chat.web;

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
import com.ecobank.app.chat.service.ChatMessageDTO;
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
	
	@Autowired
	private HttpSession httpSession;
	
	// 채팅방 목록
	@GetMapping("/chatRoom")
	public String ChatRooms(Model model){
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
	public List<ChatMessageDTO> ChatRoom(@PathVariable Integer roomId) {
		List<ChatMessageDTO> msgList = chatService.chatMessageList(roomId);
		return msgList;
	}
	
	// 팔로우 목록 조회
	@GetMapping("/chatRoom/follow")
	@ResponseBody
	public List<ChatFollowVO> chatFollowList(){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		List<ChatFollowVO> followList = chatService.chatFollowList(userNo);
		return followList;
	}
	// 채팅방 목록
	@GetMapping("/chatRoom/chatList")
	@ResponseBody
	public List<ChatRoomVO> chatRoomList(){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		List<ChatRoomVO> roomlist = chatService.chatRoomList(userNo);
		return roomlist;
	}
	
	// 채팅방 생성 - 1대1 채팅 & 그룹채팅
	@PostMapping("/chatRoom/CreateChat")
	@ResponseBody
	public String chatGroup(@RequestBody ChatRoomDTO chatRoom){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		chatService.ChatRoomInsert(chatRoom.getChatName(), userNo);
		List<Integer> userIds = chatRoom.getUserId();
		for(Integer userId : userIds) {
			chatService.ChatUserInsert(chatRoom.getChatName(), userId);
		};
		return "success";
	}
}
