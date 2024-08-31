package com.ecobank.app.chat.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecobank.app.chat.mapper.ChatMapper;
import com.ecobank.app.chat.service.ChatFileVO;
import com.ecobank.app.chat.service.ChatFollowVO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatPartVO;
import com.ecobank.app.chat.service.ChatRoomDTO;
import com.ecobank.app.chat.service.ChatRoomUserVO;
import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatService;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	private ChatMapper chatMapper;
	private final Translate translate;
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	ChatServiceImpl(ChatMapper chatMapper, Translate translate, SimpMessagingTemplate messagingTemplate) {
		this.chatMapper = chatMapper;
		this.translate = translate;
		this.messagingTemplate = messagingTemplate;
	}
	// 채팅방 목록 조회
	@Override
	public List<ChatRoomVO> chatRoomList(Integer userNo, String nickName) {
		List<ChatRoomVO> chatList = chatMapper.selectChatRoomAll(userNo);
		for(ChatRoomVO chat : chatList) {
			if(chat.getChatType().equals("O1")) {
				String[] users = chat.getChatName().split("-");
				String chatRoomName = nickName.equals(users[0]) ? users[1] : users[0];
				chat.setChatName(chatRoomName);
			}
		}
		return chatList;
	}
	// 채팅방 상세 조회
	@Override
	public ChatRoomVO chatRoomInfo(Integer chatNo, Integer userNo, String nickName) {
		ChatRoomVO chatRoom = chatMapper.selectChatRoomInfo(chatNo, userNo);
		if(chatRoom == null) {
			return null;
		}
		if(chatRoom.getChatType().equals("O1")) {
			String[] users = chatRoom.getChatName().split("-");
			String chatRoomName = nickName.equals(users[0]) ? users[1] : users[0];
			chatRoom.setChatName(chatRoomName);
		}
		return chatRoom;
	}
	
	
	// 채팅방 채팅 로그 기록
	@Override
	public ChatMessageVO ChatMessageInsert(ChatMessageVO message) {
		int result = chatMapper.insertChatMessage(message);
		
		if(message.getMsgFilePath() != null) {
			ChatFileVO chatFile = new ChatFileVO();
			chatFile.setMsgFilePath(message.getMsgFilePath());
			chatFile.setMsgFileType(message.getMsgFileType());
			chatFile.setMsgNo(message.getMsgNo());
			chatFile.setChatNo(message.getChatNo());
			chatMapper.insertChatFileMessage(chatFile);
		}
		message.setForMatTime(formatMessageDate(message.getMsgSendTime()));
		return message;
	}
	
	// 채팅방 타입 조회
	@Override
	public String chatRoomType(Integer chatNo) {
		return chatMapper.getChatRoomType(chatNo);
	}
	
	// 채팅방 채팅 로그 조회
	@Override
	public List<ChatMessageVO> chatMessageList(Integer chatNo) {
		List<ChatMessageVO> msgList = chatMapper.selectChatMessage(chatNo);
		for(ChatMessageVO msg : msgList) {
			msg.setForMatTime(formatMessageDate(msg.getMsgSendTime()));
		}
		return msgList;
	}
	
	
	// 채팅방 만들기
	@Override
	public int ChatRoomInsert(ChatRoomDTO chatRoom, Integer userNo) {
		ChatRoomVO chatRoomVO = new ChatRoomVO();
		chatRoomVO.setUserNo(userNo);
		chatRoomVO.setChatName(chatRoom.getChatName());
		chatRoomVO.setChatType(chatRoom.getChatType());
		chatMapper.insertChatRoom(chatRoomVO);
		
		return chatRoomVO.getChatNo();
	}
	// 오픈 채팅방 만들기
	@Override
	public int ChatOpenInsert(ChatRoomVO chatRoom, Integer userNo, MultipartFile[] images) {
		if (images != null) {
	        for (MultipartFile image : images) {
	        	String fileName = image.getOriginalFilename();
	        	String folderPath = makeFolder();
	        	UUID uuid = UUID.randomUUID();
				String uniqueFileName = folderPath + "/" + uuid + "_" + fileName;
				String saveName = uploadPath + "/" + uniqueFileName;
				Path savePath = Paths.get(saveName);
				chatRoom.setChatImage(uniqueFileName);
				try {
					image.transferTo(savePath);
				}catch(IOException e) {
					e.printStackTrace();
				}
	        }
	    }
		chatRoom.setUserNo(userNo);
		chatMapper.insertChatRoom(chatRoom);
		return chatRoom.getChatNo();
	}
	//오픈채팅방 업데이트 
	@Override
	public int OpenChatChangeUpdate(ChatRoomVO chatRoom, MultipartFile[] images) {
		if (images != null) {
	        for (MultipartFile image : images) {
	        	String fileName = image.getOriginalFilename();
	        	String folderPath = makeFolder();
	        	UUID uuid = UUID.randomUUID();
				String uniqueFileName = folderPath + "/" + uuid + "_" + fileName;
				String saveName = uploadPath + "/" + uniqueFileName;
				Path savePath = Paths.get(saveName);
				chatRoom.setChatImage(uniqueFileName);
				try {
					image.transferTo(savePath);
				}catch(IOException e) {
					e.printStackTrace();
				}
	        }
	    }
		int result = chatMapper.updateOpenChatChange(chatRoom);
		return result;
	}	
	
	//채팅방 파일 저장
	@Override
	public ChatFileVO getfileURL(MultipartFile[] images) {
		ChatFileVO chatFile = new ChatFileVO();
		if (images != null) {
	        for (MultipartFile image : images) {
	        	String fileName = image.getOriginalFilename();
	        	String fileType = image.getContentType();
	        	String folderPath = makeFolder();
	        	UUID uuid = UUID.randomUUID();
				String uniqueFileName = folderPath + "/" + uuid + "_" + fileName;
				String saveName = uploadPath + "/" + uniqueFileName;
				
				chatFile.setMsgFilePath(uniqueFileName); 
				chatFile.setMsgFileType(fileType);
				Path savePath = Paths.get(saveName);
				try {
					image.transferTo(savePath);
				}catch(IOException e) {
					e.printStackTrace();
				}
	        }
	    }
		return chatFile;
	}
	
	
	// 오픈 채팅방 이미지 저장
	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String folderPath = str.replace("/", File.separator);
        File uploadPathFoler = new File(uploadPath, folderPath);
        if (uploadPathFoler.exists() == false) {
			uploadPathFoler.mkdirs();
        }
		return str;
	}
	
	
	// 채팅방 - 참여자
	@Override
	public int ChatUserInsert(Integer chatNo, Integer userNo) {
		return chatMapper.insertChatUser(chatNo, userNo);
	}
	
	
	// 채팅방 전체참여자 이름 조회
	@Override
	public List<String> ChatUserList(Integer chatNo) {
		return chatMapper.selectAllChatUser(chatNo);
	}
	
	// 채팅방 참여자 숫자 조회
	@Override
	public int getUsersChatRoom(Integer chatNo, ChatMessageVO message, String nickName) {
		int result = chatMapper.selectUsersChatRoom(chatNo);
		if(result < 1) {
			// 채팅방에 아무도 없을 경우
			
			List<ChatFileVO> chatFiles = chatMapper.selectChatFilePath(chatNo);
			// 채팅방 파일 DB 삭제
			chatMapper.deleteChatFileMessage(chatNo);
			
			if(chatFiles != null && !chatFiles.isEmpty()) {
				for(ChatFileVO chatFile : chatFiles) {
					String filePath = chatFile.getMsgFilePath();
					Path path = Paths.get(uploadPath, filePath); 
					
					//파일이 존재하면 삭제
					if(Files.exists(path)) {
						try {
							Files.delete(path);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}				
			}
					
			// 채팅방 메시지 삭제
			chatMapper.deleteAllMessage(chatNo);
			// 채팅방 참여자 삭제
			chatMapper.deleteChatPart(chatNo);
			// 채팅방 삭제
			chatMapper.deleteChatRoom(chatNo);
			
			messagingTemplate.convertAndSendToUser(nickName, "/queue/chatList", message);
		}else {
			// 채팅방 나가는 메시지 저장
			chatMapper.insertChatMessage(message);
			message.setForMatTime(formatMessageDate(message.getMsgSendTime()));
			// 채팅방 남은 사람에게 메시지
			messagingTemplate.convertAndSendToUser(nickName, "/queue/chatList", message);
			List<String> receiverIds = chatMapper.selectLeaveUser(message.getUserNo(), chatNo);
			for(String receiverId : receiverIds) {
				messagingTemplate.convertAndSendToUser(receiverId, "/queue/leaveChatRoom", message);
			}
		}
		return result;
	}
	// 채팅방 참여자 나가기
	@Override
	public int chatEntryUpdate(Integer userNo, Integer chatNo) {
		return chatMapper.updateChatEntry(userNo, chatNo);
	}

	// 채팅방 메시지 전부 삭제
	@Override
	public int chatAllMessageDelete(Integer chatNo) {
		List<ChatFileVO> chatFiles = chatMapper.selectChatFilePath(chatNo);
		chatMapper.deleteChatFileMessage(chatNo);
		if(chatFiles != null && !chatFiles.isEmpty()) {
			for(ChatFileVO chatFile : chatFiles) {
				String filePath = chatFile.getMsgFilePath();
				Path path = Paths.get(uploadPath, filePath); 
				
				//파일이 존재하면 삭제
				if(Files.exists(path)) {
					try {
						Files.delete(path);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}				
		}
		
		chatMapper.deleteAllMessage(chatNo);
		chatMapper.deleteChatPart(chatNo);
		return chatMapper.deleteChatRoom(chatNo);
	}
	// 채팅방 남은 사람 조회
	@Override
	public List<String> chatLeaveUser(Integer userNo, Integer chatNo) {
		return chatMapper.selectLeaveUser(userNo, chatNo);
	}
	// 채팅방 방장 조회
	@Override
	public int getRoomManager(Integer userNo, Integer chatNo) {
		int result = chatMapper.selectRoomManager(userNo, chatNo);
		return result;
	}
	// 채팅 팔로우 목록
	@Override
	public List<ChatFollowVO> chatFollowList(Integer userNo) {
		return chatMapper.selectChatFollowAll(userNo);
	}

	// 날짜 포맷
	private String formatMessageDate(Date msgSendTime) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd a hh:mm");
	    return dateFormat.format(msgSendTime);
	}
	
	// 채팅방 이름 변경
	@Override
	public int chatNameChangeUpdate(String chatName, Integer chatNo) {
		int result = chatMapper.updateChatChangeName(chatName, chatNo);
		return result;
	}
	
	// 채팅 번역
	@Override
	public String translateMessage(String text, String targetLanguage) {
		Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(targetLanguage));
		return translation.getTranslatedText();
	}
	// 언어 조회
	@Override
	public String laguageCodeSelect(String userNo) {
		String result = chatMapper.selectLaguageCode(userNo);
		return result;
	}
	// 언어 변경
	@Override
	public int laguageCodeUpdate(String lagCode, Integer userNo) {
		Integer result = chatMapper.updateLaguageCode(lagCode, userNo);
		return result;
	}
	//채팅방에 없는 팔로우 목록
	@Override
	public List<ChatFollowVO> chatFollowListInfo(Integer userNo, Integer chatNo) {
		List<ChatFollowVO> list = chatMapper.selectChatFollowInfo(userNo, chatNo);
		return list;
	}
	// 채팅방 참여자 이름
	@Override
	public String chatNickName(Integer chatNo) {
		String nickName = chatMapper.selectChatUserName(chatNo);
		return nickName;
	}
	// 채팅방 참여자 목록
	@Override
	public List<ChatPartVO> ChatRoomUsersList(Integer chatNo) {
		List<ChatPartVO> list = chatMapper.selectChatRoomUsers(chatNo);
		return list;
	}
	
	//
	@Override
	public ChatRoomUserVO ChatProfileInfo(Integer userNo) {
		ChatRoomUserVO chatRoom = chatMapper.selectChatUserInfo(userNo);
		return chatRoom;
	}
	
	//채팅방 이름 조회
	@Override
	public Integer getChatName(String chatName) {		
		return chatMapper.selectPrivateName(chatName);
	}
	@Override
	public int chatStateUpdate(Integer userNo, Integer chatNo) {
		int result = chatMapper.updateUserState(userNo, chatNo);
		return result;
	}
	

}
