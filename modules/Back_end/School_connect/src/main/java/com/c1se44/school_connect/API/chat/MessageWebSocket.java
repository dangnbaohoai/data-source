package com.c1se44.school_connect.API.chat;

import com.c1se44.school_connect.DTO.Request.MessageChatRequest;
import com.c1se44.school_connect.DTO.Response.ChatResponse;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.userResponse;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.chatMessageEntity;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.IChatService;
import com.c1se44.school_connect.service.IRoomChatService;
import com.c1se44.school_connect.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
//@RequestMapping("/api/user/chatMessage")
@RestController()
@Api(value = "chat use webSocket by user")
public class MessageWebSocket {
	@Autowired
	IRoomChatService roomChatService;
	@Autowired
	IUserService userService;
	@Autowired
	IChatService chatService;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
//	@Autowired
//	private SimpMessagingTemplate simpMessagingTemplate;


//	@PostMapping("/api/users/{username}/send")
//	public ResponseEntity<?> sendMessage(@RequestBody Message message, @PathVariable String username) {
//		Set<SimpUser> users = userRegistry.getUsers();
//		if (users.stream().anyMatch(simpUser -> simpUser.getName().equals(username))) {
//			template.convertAndSendToUser(username, "/messages", message);
//			return ResponseEntity.noContent().build();
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
	
	//@MessageMapping("/chat/{to}")
//	@GetMapping("chat/{roomId}")
//	public void sendMessage( HttpServletRequest request,
//	                         @DestinationVariable Long roomId,
//	                         @RequestBody MessageChatRequest messageRequest) {
//		System.out.println("handling send message: " + messageRequest.getMessage() + " to: " + messageRequest.getRoomChatId());
//		String jwt = jwtTokenFilter.getJwt(request);
//		String username = jwtProvider.getUserNameFromToken(jwt);
//		messageRequest.setRoomChatId(roomId);
//		simpMessagingTemplate.convertAndSend("/topic/messages/" + messageRequest.getRoomChatId(), saveChatWebSocket(username, messageRequest));;
//	}
	public ResponseEntity<?> saveChatWebSocket(String userSend, MessageChatRequest messageChatRequest) {
		roomChatEntity roomChat = roomChatService.findByRoomIdAndStatus(messageChatRequest.getRoomChatId(), StatusName.is_active);
		userEntity userSendChat = userService.findByUsername(userSend).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + userSend));
		if (roomChat == null || userSendChat == null) {
			return new ResponseEntity<>(new MessageResponse(" room chat not found"), HttpStatus.OK);
		}
		roomChat.setNewMessage(messageChatRequest.getMessage());
		roomChat.setStatusMessage(StatusName.unseen);
		if(roomChat.getUserChatId1().getUsername().equals(userSendChat.getUsername())){
			roomChat.setMessageRecipient(roomChat.getUserChatId2().getUsername());
		}else{
			roomChat.setMessageRecipient(roomChat.getUserChatId1().getUsername());
		}
		roomChatEntity roomChatSave = roomChatService.save(roomChat);
		chatMessageEntity chatNew = new chatMessageEntity(userSendChat,roomChatSave,messageChatRequest);
		chatMessageEntity chatWasSend = chatService.save(chatNew);
		userResponse userSendMessage = new userResponse(chatWasSend.getUserSendMessageId());
		ChatResponse chatMessageResponse = new ChatResponse(userSendMessage,chatWasSend);
		return new ResponseEntity<>(chatMessageResponse, HttpStatus.OK);
	}
}
