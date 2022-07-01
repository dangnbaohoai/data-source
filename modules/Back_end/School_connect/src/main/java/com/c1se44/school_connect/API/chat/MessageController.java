package com.c1se44.school_connect.API.chat;

import com.c1se44.school_connect.DTO.Request.MessageChatRequest;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.*;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.chatMessageEntity;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.IChatService;
import com.c1se44.school_connect.service.IReportRoomService;
import com.c1se44.school_connect.service.IRoomChatService;
import com.c1se44.school_connect.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/user/chatMessage")
@RestController()
@Api(value = "chat by user")
public class MessageController {
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
	@Autowired
	IReportRoomService reportRoomService;
	
	
	@GetMapping("/viewMessagesInRoom/{id}")
	public ResponseEntity<?> viewMessagesInRoom(HttpServletRequest request, @PathVariable("id") Long id,
	                                            @RequestParam(value = "page", required = true) int page,
	                                            @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		roomChatEntity roomChat = roomChatService.findByRoomIdAndStatus(id, StatusName.is_active);
		if (roomChat == null) {
			return new ResponseEntity<>(new MessageResponse(" room chat not found"), HttpStatus.OK);
		}
		ViewRequest ViewRequest = new ViewRequest();
		Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).descending());
		roomChatService.updateStatusMessageByStatusAndUserId(StatusName.read, StatusName.unread, username);
		List<chatMessageEntity> chatMessageList = chatService.findByRoomChatMessageId(roomChat, pageable);
		List<ChatResponse> chatResponseList = new ArrayList<>();
		chatMessageList.forEach(item -> {
			userResponse user = new userResponse(item.getUserSendMessageId());
			ChatResponse chatResponse = new ChatResponse(user, item);
			chatResponseList.add(chatResponse);
		});
		return new ResponseEntity<>(chatResponseList, HttpStatus.OK);
	}
	
	@GetMapping("/autoLoadMessage/{id}")
	public ResponseEntity<?> autoLoadMessage(HttpServletRequest request, @PathVariable("id") Long id,
	                                         @RequestParam(value = "lastMessage", required = true) Long lastMessage) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		roomChatEntity roomChat = roomChatService.findByRoomIdAndStatus(id, StatusName.is_active);
		if (roomChat == null) {
			return new ResponseEntity<>(new MessageResponse(" room chat not found"), HttpStatus.OK);
		}
		if(!roomChatService.existsByMessageRecipientAndStatusMessage(username,StatusName.unseen)){
			return new ResponseEntity<>(new MessageResponse("no new message"), HttpStatus.OK);
		}
		List<chatMessageEntity> chatMessageList = chatService.findByRoomChatMessageIdAndIdChatBigThan(roomChat.getRoomId(),lastMessage);
		List<ChatResponse> chatResponseList = new ArrayList<>();
		chatMessageList.forEach(item -> {
			userResponse user = new userResponse(item.getUserSendMessageId());
			ChatResponse chatResponse = new ChatResponse(user, item);
			chatResponseList.add(chatResponse);
		});
		if(!chatMessageList.isEmpty()){
			roomChatService.updateStatusMessageByStatusAndUserId(StatusName.read, StatusName.unread, username);
		}
		List<roomChatEntity> roomChatEntityList=roomChatService.findByStatusMessageAndMessageRecipient(StatusName.unseen,username);
		List<RoomChatResponse> roomChatResponseList= new ArrayList<>();
		roomChatEntityList.forEach(item->{
			RoomChatResponse roomChatResponse = new RoomChatResponse();
			if(item.getUserChatId1().getUsername().equals(username)){
				roomChatResponse=new RoomChatResponse(item,item.getUserChatId2());
			}else{
				roomChatResponse=new RoomChatResponse(item,item.getUserChatId1());
			}
			roomChatResponseList.add(roomChatResponse);
		});
		if(!roomChatEntityList.isEmpty()){
			roomChatService.updateStatusMessageByStatusAndUserId(StatusName.unread,StatusName.unseen,username);
		}
		UserListResponse<RoomChatResponse,ChatResponse> result = new UserListResponse<>();
		result.setListResult1(roomChatResponseList);
		result.setListResult2(chatResponseList);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/sendMessageNoWebsocket")
	public ResponseEntity<?> sendMessageNoWebsocket(HttpServletRequest request, @RequestBody MessageChatRequest messageRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		return saveChat(username, messageRequest);
	}
	@DeleteMapping("/deleteMessage/{id}")
	public ResponseEntity<?> deleteMessage(HttpServletRequest request, @PathVariable("id") Long MessageId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user=userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		chatMessageEntity chat = chatService.findByMessageId(MessageId);
		if(chat!=null){
			if(chat.getUserSendMessageId().getUsername().equals(user.getUsername())){
				if(!reportRoomService.existsByRoomChatReportIdAndMessageReport(chat)){
					chatService.deleteByChatId(MessageId,user);
					return new ResponseEntity<>(new MessageResponse("delete successfully"), HttpStatus.OK);
				}else{
					return new ResponseEntity<>(new MessageResponse("message was reported can't delete"), HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<>(new MessageResponse("message not was user send"), HttpStatus.OK);
			}
		}else{
			return new ResponseEntity<>(new MessageResponse("no message found"), HttpStatus.OK);
		}
	}
	
	public ResponseEntity<?> saveChat(String userSend, MessageChatRequest messageChatRequest) {
		roomChatEntity roomChat = roomChatService.findByRoomIdAndStatus(messageChatRequest.getRoomChatId(), StatusName.is_active);
		userEntity userSendChat = userService.findByUsername(userSend).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + userSend));
		if (roomChat == null || userSendChat == null) {
			return new ResponseEntity<>(new MessageResponse(" room chat was block by admin"), HttpStatus.OK);
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