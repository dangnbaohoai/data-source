package com.c1se44.school_connect.API.censor;

import com.c1se44.school_connect.API.user.NotifyUserControllerAPI;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.userResponse;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.relationship.memberForums;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.IForumService;
import com.c1se44.school_connect.service.IMemberForumService;
import com.c1se44.school_connect.service.impl.UserService;
import com.c1se44.school_connect.utils.CheckRole;
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
@RequestMapping("/api/censor/member")
@RestController
@Api(value = "forum manager member by censor")
public class MemberManageAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	UserService userService;
	@Autowired
	CheckRole checkRole;
	@Autowired
	IForumService forumService;
	@Autowired
	IMemberForumService MemberForumService;
	@Autowired
	NotifyUserControllerAPI notify;
	@GetMapping("/viewMemberByCensor/{id}")
	public ResponseEntity<?> viewForumByCensor(HttpServletRequest request, @PathVariable("id") Long id,
	                                           @RequestParam(value = "page", required = true) int page,
	                                           @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("forum id null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if ( forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, id, user)) {
			try {
				ViewRequest viewRequest = new ViewRequest();
				Pageable pageable = PageRequest.of(page - 1, viewRequest.getLimitUser(), Sort.by(sortBy).descending());
				forumsEntity forum = forumService.findByForumId(id);
				List<memberForums> memberForums = MemberForumService.findByForumUserIdAndStatus(forum, StatusName.censored, pageable);
				List<userResponse> userResponses = new ArrayList<>();
				memberForums.forEach(item -> {
					userResponse userResponse = new userResponse(item.getUserForumId());
					userResponses.add(userResponse);
				});
				return new ResponseEntity<>(userResponses, HttpStatus.OK);
			} catch (UsernameNotFoundException exception) {
				return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("not censor"), HttpStatus.OK);
		}
	}
	
	@PutMapping("/censorMember")
	public ResponseEntity<?> censorMember(HttpServletRequest request,
	                                      @RequestParam(value = "userId", required = true) Long userId,
	                                      @RequestParam(value = "forumId", required = true) Long forumId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (forumId == null || userId == null) {
			return new ResponseEntity<>(new MessageResponse("user or forum null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, forumId, user)) {
			try {
				MemberForumService.UpdateStatus(StatusName.censored,userId,forumId);
				userEntity userNotify = userService.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + userId));
				forumsEntity forum = forumService.findByForumId(forumId);
				String message=user.getUsername()+" had access you join the group from now you can see post in the forum";
				notify.JoinForum(userNotify,forum,message);
				return new ResponseEntity<>(new MessageResponse("censorship successful"), HttpStatus.OK);
			} catch (UsernameNotFoundException exception) {
				return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("not censor"), HttpStatus.OK);
		}
	}
	
	@GetMapping("/viewMemberNotCensored/{id}")
	public ResponseEntity<?> viewMemberNotCensored(HttpServletRequest request,
	                                               @PathVariable("id") Long id,
	                                               @RequestParam(value = "page", required = true) int page,
	                                               @RequestParam(value = "sortBy", required = true) String sortBy)  {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("no"), HttpStatus.OK);
		}
		
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, id, user)) {
			try {
				ViewRequest ViewRequest=new ViewRequest();
				Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).ascending());
				forumsEntity forum = forumService.findByForumId(id);
				List<memberForums> memberForums = MemberForumService.findByForumUserIdAndStatus(forum, StatusName.have_not_been_censored, pageable);
				List<userResponse> userResponses = new ArrayList<>();
				memberForums.forEach(item -> {
					userResponse userRespons = new userResponse(item.getUserForumId());
					userResponses.add(userRespons);
				});
				return new ResponseEntity<>(userResponses, HttpStatus.OK);
			} catch (UsernameNotFoundException exception) {
				return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("not censor"), HttpStatus.OK);
		}
	}
	//TODO:chua fix
	@DeleteMapping("/deleteMember")
	public ResponseEntity<?> deleteMember(HttpServletRequest request,
	                                      @RequestParam(value = "userId", required = true) Long userId,
	                                      @RequestParam(value = "forumId", required = true) Long forumId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (forumId == null || userId == null) {
			return new ResponseEntity<>(new MessageResponse("user or forum null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, forumId, user)) {
			if(user.getUserid().equals(userId)){
				return new ResponseEntity<>(new MessageResponse("you are censor you can't delete you"), HttpStatus.OK);
			}
			try {
				MemberForumService.DeleteStatusByUserIdAndForumId(userId,forumId);
				userEntity userNotify = userService.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + userId));
				forumsEntity forum = forumService.findByForumId(forumId);
				String message=user.getUsername()+ " had delete you out the group from now you can't see post in the forum";
				notify.JoinForum(userNotify,forum,message);
				return new ResponseEntity<>(new MessageResponse("delete member successful"), HttpStatus.OK);
			} catch (UsernameNotFoundException exception) {
				return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("not censor"), HttpStatus.OK);
		}
	}
	
}
