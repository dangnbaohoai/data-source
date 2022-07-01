package com.c1se44.school_connect.API.user;

import com.c1se44.school_connect.DTO.Response.*;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.chatMessageEntity;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/autoload")
@RestController()
@Api(value = "autoload data")
public class AutoloadUserControllerAPI {
	@Autowired
	IRoomChatService roomChatService;
	@Autowired
	IUserService userService;
	@Autowired
	IChatService chatService;
	@Autowired
	IMemberNotifyService memberNotifyService;
	@Autowired
	IMemberForumService memberForumService;
	
	@GetMapping("/ListNotifyAuto/{username}")
	public ResponseEntity<?> ListNotifyAuto(@PathVariable("username") String username) {
		
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		NotifyResponse notifyResponse = new NotifyResponse();
		notifyResponse.setNumberNotifyNotSeen(memberNotifyService.countAllByUserNotifyIdAndStatus(user, StatusName.unseen));
		notifyResponse.setNumberMessageNotSeen(roomChatService.countAllByStatusAndUser(StatusName.unseen, user.getUsername()));
		return new ResponseEntity<>(notifyResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("/autoLoadMessage/{idRoom}")
	public ResponseEntity<?> autoLoadMessage(@PathVariable("idRoom") Long id,
	                                         @RequestParam(value = "lastMessage", required = true) Long lastMessage,
	                                         @RequestParam(value = "username", required = true) String username) {

//		roomChatEntity roomChat = roomChatService.findByRoomIdAndStatus(id, StatusName.is_active);
//		if (roomChat == null) {
//			return new ResponseEntity<>(new MessageResponse(" room chat not found"), HttpStatus.OK);
//		}
		if (!roomChatService.existsByMessageRecipientAndStatusMessage(username, StatusName.unseen)) {
			return new ResponseEntity<>(new MessageResponse("no new message"), HttpStatus.OK);
		}
		List<chatMessageEntity> chatMessageList = chatService.findByRoomChatMessageIdAndIdChatBigThan(id, lastMessage);
//		roomChatEntity room = roomChatService.findByRoomIdAndStatus(id,StatusName.is_active);
//		List<chatMessageEntity> chatMessageList = chatService.findByRoomChatMessageIdAndIdChatBigThan2(room,lastMessage);
		List<ChatResponse> chatResponseList = new ArrayList<>();
		chatMessageList.forEach(item -> {
			userResponse user = new userResponse(item.getUserSendMessageId());
			ChatResponse chatResponse = new ChatResponse(user, item);
			chatResponseList.add(chatResponse);
		});
		if (!chatMessageList.isEmpty()) {
			roomChatService.updateStatusMessageByStatusAndRoomId(StatusName.read.toString(), StatusName.unread.toString(), id);
		}
		List<roomChatEntity> roomChatEntityList = roomChatService.findByStatusMessageAndMessageRecipient(StatusName.unseen, username);
		List<RoomChatResponse> roomChatResponseList = new ArrayList<>();
		roomChatEntityList.forEach(item -> {
			RoomChatResponse roomChatResponse = new RoomChatResponse();
			if (item.getUserChatId1().getUsername().equals(username)) {
				roomChatResponse = new RoomChatResponse(item, item.getUserChatId2());
			} else {
				roomChatResponse = new RoomChatResponse(item, item.getUserChatId1());
			}
			roomChatResponseList.add(roomChatResponse);
		});
		if (!roomChatEntityList.isEmpty()) {
			roomChatService.updateStatusMessageByStatusAndUserIdRoomNot(StatusName.unread, StatusName.unseen, username, id);
		}
		UserListResponse<RoomChatResponse, ChatResponse> result = new UserListResponse<>();
		result.setListResult1(roomChatResponseList);
		result.setListResult2(chatResponseList);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
