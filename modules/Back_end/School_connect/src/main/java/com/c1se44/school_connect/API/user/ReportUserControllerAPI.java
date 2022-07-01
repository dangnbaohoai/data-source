package com.c1se44.school_connect.API.user;

import com.c1se44.school_connect.DTO.Request.ReportRequest;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.entity.*;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RequestMapping("/api/user/report")
@RestController()
@Api(value = "report by user")
public class ReportUserControllerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IUserService userService;
	@Autowired
	IPostService postService;
	@Autowired
	IForumService forumService;
	@Autowired
	IRoomChatService roomChatService;
	@Autowired
	ICommentService commentService;
	@Autowired
	IReportCommentService reportCommentService;
	@Autowired
	IReportRoomService reportRoomService;
	@Autowired
	IReportPostService reportPostService;
	@Autowired
	IChatService chatService;
	@PostMapping("/reportPost")
	public ResponseEntity<?> reportPost(HttpServletRequest request, @RequestBody ReportRequest reportRequest) {
				String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		postsEntity post = postService.findByPostId(reportRequest.getPostId());
		forumsEntity forum = forumService.findByForumId(reportRequest.getForumId());
		if (post == null || user == null || forum == null) {
			return new ResponseEntity<>(new MessageResponse("post, user or forum not found"), HttpStatus.OK);
		}
		reportPostEntity report = new reportPostEntity(post, user, forum, reportRequest.getReason());
		reportPostService.save(report);
		return new ResponseEntity<>(new MessageResponse("report successfully"), HttpStatus.OK);
	}
	
	@PostMapping("/reportComment")
	public ResponseEntity<?> reportComment(HttpServletRequest request, @RequestBody ReportRequest reportRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		commentEntity comment = commentService.findByCommentId(reportRequest.getCommentId());
		forumsEntity forum = forumService.findByForumId(reportRequest.getForumId());
		if (comment == null || user == null || forum == null) {
			return new ResponseEntity<>(new MessageResponse("comment, user or forum not found"), HttpStatus.OK);
		}
		reportCommentEntity report = new reportCommentEntity(comment, user, forum, reportRequest.getReason());
		reportCommentService.save(report);
		return new ResponseEntity<>(new MessageResponse("report successfully"), HttpStatus.OK);
	}
	
	@PostMapping("/reportRoomChat")
	public ResponseEntity<?> reportRoomChat(HttpServletRequest request, @RequestBody ReportRequest reportRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		roomChatEntity room = roomChatService.findByRoomIdAndStatus(reportRequest.getRoomChatId(), StatusName.is_active);
		chatMessageEntity chat= chatService.findByMessageId(reportRequest.getMessageReport());
		if (user == null || room == null||chat==null) {
			return new ResponseEntity<>(new MessageResponse("room chat or user or message not found"), HttpStatus.OK);
		}
		if(reportRoomService.existsByRoomChatReportId(room)){
			return new ResponseEntity<>(new MessageResponse("report was exists"), HttpStatus.OK);
		}
		reportChatEntity report = new reportChatEntity(user, room,chat ,reportRequest);
		reportRoomService.save(report);
		return new ResponseEntity<>(new MessageResponse("report successfully"), HttpStatus.OK);
	}
}
