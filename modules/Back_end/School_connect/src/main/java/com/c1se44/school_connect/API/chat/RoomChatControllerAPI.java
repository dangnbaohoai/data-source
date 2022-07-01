package com.c1se44.school_connect.API.chat;

import com.c1se44.school_connect.DTO.Request.CommentRequest;
import com.c1se44.school_connect.DTO.Request.RoomChatRequest;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.RoomChatResponse;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.IRoomChatService;
import com.c1se44.school_connect.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/user/roomChat")
@RestController()
@Api(value = "room chat by user")
public class RoomChatControllerAPI {
	@Autowired
	IRoomChatService roomChatService;
	@Autowired
	IUserService userService;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@PostMapping("/creteRoom")
	public ResponseEntity<?> CreteRoom(HttpServletRequest request, @RequestBody RoomChatRequest roomChatRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user1 = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		userEntity user2 = userService.findByUserId(roomChatRequest.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> userId" +roomChatRequest.getUserId()));
		if(user1 == null|| user2==null){
			return new ResponseEntity<>(new MessageResponse(" user not found"), HttpStatus.OK);
		}
		roomChatEntity roomChatCheck = roomChatService.findUserChatId1AndUserChatId2(user1.getUserid(),user2.getUserid(),user2.getUserid(),user1.getUserid());
		if(roomChatCheck!=null){
			RoomChatResponse roomChatResponse;
			if(roomChatCheck.getUserChatId1().getUsername().equals(user1.getUsername())){
				roomChatResponse=new RoomChatResponse(roomChatCheck,roomChatCheck.getUserChatId2());
			}else{
				roomChatResponse=new RoomChatResponse(roomChatCheck,roomChatCheck.getUserChatId1());
			}
			return new ResponseEntity<>(roomChatResponse, HttpStatus.OK);
		}
		roomChatEntity roomChat = new roomChatEntity();
		roomChat.setUserChatId1(user1);
		roomChat.setUserChatId2(user2);
		roomChat.setStatus(StatusName.is_active);
		roomChat.setUsernameRecipient(user2.getUsername());
		roomChatEntity roomChatSave = roomChatService.save(roomChat);
		RoomChatResponse roomChatResponse;
		if(roomChatSave.getUserChatId1().getUsername().equals(user1.getUsername())){
			roomChatResponse=new RoomChatResponse(roomChatSave,roomChatSave.getUserChatId2());
		}else{
			roomChatResponse=new RoomChatResponse(roomChatSave,roomChatSave.getUserChatId1());
		}
		return new ResponseEntity<>(roomChatResponse, HttpStatus.OK);
	}
	@GetMapping("/viewRoom")
	public ResponseEntity<?> ViewRoom(HttpServletRequest request,
	                                   @RequestParam(value = "page", required = true) int page,
	                                   @RequestParam(value = "sortBy", required = true) String sortBy) {
		// sortBy=modifiedDate
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user1 = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		if(user1 == null){
			return new ResponseEntity<>(new MessageResponse(" user not found"), HttpStatus.OK);
		}
		ViewRequest ViewRequest=new ViewRequest();
		
		Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).descending());
		
		roomChatService.updateStatusMessageByStatusAndUserId(StatusName.unread,StatusName.unseen,user1.getUsername());
		
		//List<roomChatEntity> roomChatEntityList=roomChatService.findUserChatIdAndStatus(StatusName.is_active,user1,pageable);
		//List<roomChatEntity> roomChatEntityList=roomChatService.findUserChatId1orUserChatId2(StatusName.stop_working,user1.getUserid(),pageable).getContent();
		List<roomChatEntity> roomChatEntityList=roomChatService.findUserChatId1orUserChatId22(user1,pageable);
		
		List<RoomChatResponse> roomChatResponseList= new ArrayList<>();
		if(roomChatEntityList ==null){
			return new ResponseEntity<>(new MessageResponse(" no room Chat found"), HttpStatus.OK);
		}
		roomChatEntityList.forEach(item->{
			RoomChatResponse roomChat = new RoomChatResponse();
			if(item.getUserChatId1().getUsername().equals(user1.getUsername())){
			    roomChat=new RoomChatResponse(item,item.getUserChatId2());
			}else{
				roomChat=new RoomChatResponse(item,item.getUserChatId1());
			}
			roomChatResponseList.add(roomChat);
		});
		return new ResponseEntity<>(roomChatResponseList, HttpStatus.OK);
	}
	@GetMapping("/Search/{userName}")
	public ResponseEntity<?> Search(HttpServletRequest request,
	                                @PathVariable("userName") String keyUserName,
	                                @RequestParam(value = "page", required = true) int page,
	                                @RequestParam(value = "sortBy", required = true) String sortBy) {
		// sortBy=modifiedDate
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user1 = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		if(user1 == null){
			return new ResponseEntity<>(new MessageResponse(" user not found"), HttpStatus.OK);
		}
		ViewRequest ViewRequest=new ViewRequest();
		
		Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).descending());
		List<userEntity> userFound = userService.findByStatusAndUsernameContaining(StatusName.is_active,keyUserName,user1.getUserid());
		roomChatService.updateStatusMessageByStatusAndUserId(StatusName.unread,StatusName.unseen,user1.getUsername());
		
		//List<roomChatEntity> roomChatEntityList=roomChatService.findUserStatusAndChatId1orUserChatId22Like(StatusName.is_active,userFound,user1,user1,pageable);
		List<roomChatEntity> roomChatEntityList=roomChatService.findUserChatId1orUserChatId22Like(userFound,user1,user1,pageable);
		
		List<RoomChatResponse> roomChatResponseList= new ArrayList<>();
		if(roomChatEntityList ==null){
			return new ResponseEntity<>(new MessageResponse(" no room Chat found"), HttpStatus.OK);
		}
		roomChatEntityList.forEach(item->{
			RoomChatResponse roomChat = new RoomChatResponse();
			if(item.getUserChatId1().getUsername().equals(user1.getUsername())){
				roomChat=new RoomChatResponse(item,item.getUserChatId2());
			}else{
				roomChat=new RoomChatResponse(item,item.getUserChatId1());
			}
			roomChatResponseList.add(roomChat);
		});
		return new ResponseEntity<>(roomChatResponseList, HttpStatus.OK);
	}
}
