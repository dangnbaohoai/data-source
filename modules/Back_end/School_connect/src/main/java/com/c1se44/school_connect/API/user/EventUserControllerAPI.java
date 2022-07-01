package com.c1se44.school_connect.API.user;

import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.PostResponse;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.relationship.memberEvent;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.IMemberEventService;
import com.c1se44.school_connect.service.IPostService;
import com.c1se44.school_connect.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/user/Event")
@RestController()
@Api(value = "Event of user")
public class EventUserControllerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IUserService userService;
	@Autowired
	IPostService postService;
	@Autowired
	IMemberEventService memberEventService;
	
	@GetMapping("/viewEventJoin")
	public ResponseEntity<?> viewEventJoin(HttpServletRequest request,
	                                       @RequestParam(value = "month", required = true) int month,
	                                       @RequestParam(value = "year", required = true) int year) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		//List<memberEvent> EventIdList= memberEventService.findEventsByUserId(user);
		List<memberEvent> EventIdList = memberEventService.findEventsByUserIdAndDateOfEvent(user.getUserid(), month, year);
		List<PostResponse> postResponseList = new ArrayList<>();
		EventIdList.forEach(item -> {
			//if(item.getEventUserId().getDateOfEvent().getMonthValue()==month&&item.getEventUserId().getDateOfEvent().getYear()==year){
			PostResponse event = new PostResponse(item.getEventUserId(), item.getEventUserId().getUserPostId(), item.getEventUserId().getLinkImage());
			postResponseList.add(event);
			//}
		});
		//List<postsEntity> EventList= postService.findAllByYearAndMonthAndPostIdIN(year,month,EventIdList);
		return new ResponseEntity<>(postResponseList, HttpStatus.OK);
	}
	
	@PostMapping("/joinEvent/{id}")
	public ResponseEntity<?> joinEvent(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		postsEntity event = postService.findByPostId(id);
		if (memberEventService.existsByUserEventIdAndEventUserId(user, event)) {
			return new ResponseEntity<>(new MessageResponse("user has join event"), HttpStatus.OK);
		}
		memberEvent memberEvent = new memberEvent();
		memberEvent.setUserEventId(user);
		memberEvent.setEventUserId(event);
		memberEvent.setDateOfEvent(event.getDateOfEvent());
		memberEvent result = memberEventService.save(memberEvent);
		return new ResponseEntity<>(new MessageResponse("user join event success"), HttpStatus.OK);
	}
	
	@PostMapping("/leaveEvent/{id}")
	public ResponseEntity<?> leaveEvent(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		//postsEntity event = postService.findByPostId(id);
		memberEventService.deleteByUserAndEvent(user.getUserid(), id);
		return new ResponseEntity<>(new MessageResponse("user leave event success"), HttpStatus.OK);
	}
}