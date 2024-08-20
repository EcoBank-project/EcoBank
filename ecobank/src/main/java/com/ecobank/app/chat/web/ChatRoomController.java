package com.ecobank.app.chat.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
		String nickName = (String) httpSession.getAttribute("nickname");
		
		List<ChatRoomVO> chatList = chatService.chatRoomList(userNo, nickName);
		
		model.addAttribute("userNo", userNo);
		model.addAttribute("nickname", nickName);
		model.addAttribute("chatRooms", chatList);
		return "chat/chatRoom";
	}
	
	@GetMapping("/chatRoom/{chatNo}")
	public String ChatRoom(@PathVariable Integer chatNo , HttpSession httpSession, Model model){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		String nickName = (String) httpSession.getAttribute("nickname");
		ChatRoomVO chatRoom = chatService.chatRoomInfo(chatNo, userNo, nickName);
		if(chatRoom == null) {
			return "redirect:/";
		}
		
		List<ChatRoomVO> chatList = chatService.chatRoomList(userNo, nickName);
		
		String chatType = chatRoom.getChatType();
		String chatName = chatRoom.getChatName();
		
		model.addAttribute("userNo", userNo);
		model.addAttribute("chatType", chatType);
		model.addAttribute("chatName", chatName);
		model.addAttribute("nickname", nickName);
		model.addAttribute("chatRooms", chatList);
		
		
		return "chat/chatRoom";
	}
	
	// 특정 채팅방 채팅로그 조회
	@PostMapping("/chatRoom/logs")
	@ResponseBody
	public List<ChatMessageVO> ChatRoom(@RequestParam Integer roomId) {
		List<ChatMessageVO> msgList = chatService.chatMessageList(roomId);
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
		String nickName = (String) httpSession.getAttribute("nickname");
		List<ChatRoomVO> roomlist = chatService.chatRoomList(userNo, nickName);
		System.out.println(roomlist);
		return roomlist;
	}
	
	// 채팅방 생성 - 1대1 채팅 & 그룹채팅
	@PostMapping("/chatRoom/createChat")
	@ResponseBody
	public Integer chatPrivateGroup(HttpSession httpSession, @RequestBody ChatRoomDTO chatRoom){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		Integer chatNo = chatService.ChatRoomInsert(chatRoom, userNo);
		
		List<Integer> userNos = chatRoom.getUserNo();
		for(Integer user : userNos) {
			chatService.ChatUserInsert(chatNo, user);
		};
		return chatNo;
	}
	// 채팅방 생성 - 오픈 채팅
	@PostMapping("/chatRoom/createOpenChat")
	@ResponseBody
	public Integer chatOpen(HttpSession httpSession, @ModelAttribute ChatRoomVO chatRoom, @RequestPart("image") MultipartFile[] images) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		Integer chatNo = chatService.ChatOpenInsert(chatRoom, userNo, images);
		return chatNo;
	}
	// 오픈 채팅방 초대
	@PostMapping("/chatRoom/invite")
	@ResponseBody
	public void chatInviteList(@RequestParam Integer chatNo){
		
	}
	
	// 프로필 언어 설정
	@GetMapping("/chatRoom/languageChange/{languageCode}")
	@ResponseBody
	public void profilChange(@PathVariable String languageCode) {
		
	}
}
