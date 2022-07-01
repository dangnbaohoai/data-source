package com.c1se44.school_connect.API.user;

import com.c1se44.school_connect.API.censor.ForumManageAPI;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.ForumResponse;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.PostResponse;
import com.c1se44.school_connect.DTO.Response.userResponse;
import com.c1se44.school_connect.DTO.jwt.JwtResponse;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.relationship.memberForums;
import com.c1se44.school_connect.entity.relationship.postForum;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.*;
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
@RequestMapping("/api/user/forum")
@RestController()
@Api(value = "view forum of user")
public class ForumUserControllerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IForumService forumService;
	@Autowired
	IUserService userService;
	@Autowired
	IPostForumService postForumService;
	@Autowired
	IMemberForumService memberForumService;
	@Autowired
	CheckRole checkRole;
	@Autowired
	ForumManageAPI forumManageAPI;
	@Autowired
	IPostService postService;
	@Autowired
	IImagePostService imagePostService;
	@Autowired
	ICommentService commentService;
	@Autowired
	IMemberEventService memberEventService;
	// TODO:test this class
	@GetMapping("/viewForumJoin/{id}")
		public ResponseEntity<?> viewForumJoin(HttpServletRequest request, @PathVariable("id") Long id,
		@RequestParam(value = "page", required = true) int page,
		@RequestParam(value = "sortBy", required = true) String sortBy) {
			String jwt = jwtTokenFilter.getJwt(request);
			String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("forum null"), HttpStatus.OK);
		}
		if (!forumService.existsByStatusAndForumId(StatusName.is_active, id)) {
			return new ResponseEntity<>(new MessageResponse("no forum found"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		ViewRequest ViewRequest=new ViewRequest();
		ViewRequest.setPage(page);
		ViewRequest.setSortBy(sortBy);
		if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, id, user)) {
			return forumManageAPI.viewForumByCensor(request, user,id, ViewRequest);
		}
		try {
			forumsEntity forum = forumService.findByForumId(id);
			ForumResponse forumResponse = new ForumResponse(forum);
			if (memberForumService.existsByUserForumIdAndForumUserId(user, forum)) {
				if (memberForumService.existsByUserForumIdAndForumUserIdAndStatus(user, forum, StatusName.censored)) {
					Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).descending());
					List<postForum> postList = postForumService.findByForumId(forum, StatusName.censored,pageable);
					//List<postsEntity> post = postService.findByPostIdIn(postList);
					List<PostResponse> postResponseList = new ArrayList<>();
					if (postList != null) {
						postList.forEach(item -> {
							//List<imagePostEntity> imagePostEntityList = imagePostService.findByPostImageId(item);
							PostResponse postResponse = new PostResponse(item.getPostForumsId(), item.getPostForumsId().getUserPostId(),item.getPostForumsId().getLinkImage());
							postResponse.setForumId(forum.getForumId());
							postResponse.setForumName(forum.getName());
							postResponse.setNumberComments(commentService.countAllByPostCommentId(item.getPostForumsId()));
							if(item.getPostForumsId().getDateOfEvent()!= null && item.getPostForumsId().getNameEvent()!= null && item.getPostForumsId().getDateOfEvent()!= null){
								if(memberEventService.existsByUserEventIdAndEventUserId(user,item.getPostForumsId())){
									postResponse.setStatusJoinEventOfUser(1);
								}else{
									postResponse.setStatusJoinEventOfUser(0);
								}
							}
							postResponseList.add(postResponse);
						});
					}
					forumResponse.setListPostResponse(postResponseList);
					return new ResponseEntity<>(forumResponse, HttpStatus.OK);
				} else if (memberForumService.existsByUserForumIdAndForumUserIdAndStatus(user, forum, StatusName.have_not_been_censored)) {
					forumResponse.setMessage("user have not been censored");
					return new ResponseEntity<>(forumResponse, HttpStatus.OK);
				} else {
					forumResponse.setMessage("user not join forum");
					return new ResponseEntity<>(forumResponse, HttpStatus.OK);
				}
			} else {
				forumResponse.setMessage("user not join forum");
				return new ResponseEntity<>(forumResponse, HttpStatus.OK);
			}
			
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/viewPostInForum/{id}")
	public ResponseEntity<?> viewPostInForum(HttpServletRequest request, @PathVariable("id") Long id,
	                                      @RequestParam(value = "page", required = true) int page,
	                                      @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("forum null"), HttpStatus.OK);
		}
		if (!forumService.existsByStatusAndForumId(StatusName.is_active, id)) {
			return new ResponseEntity<>(new MessageResponse("no forum found"), HttpStatus.OK);
		}
		ViewRequest ViewRequest=new ViewRequest();
		ViewRequest.setPage(page);
		ViewRequest.setSortBy(sortBy);
		try {
			forumsEntity forum = forumService.findByForumId(id);
			ForumResponse forumResponse = new ForumResponse(forum);
			Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).descending());
			List<postForum> postList = postForumService.findByForumId(forum, StatusName.censored,pageable);
			//List<postsEntity> post = postService.findByPostIdIn(postList);
			List<PostResponse> postResponseList = new ArrayList<>();
			if (postList != null) {
				postList.forEach(item -> {
					PostResponse postResponse = new PostResponse(item.getPostForumsId(), item.getPostForumsId().getUserPostId(), item.getPostForumsId().getLinkImage());
					postResponse.setForumId(forum.getForumId());
					postResponse.setForumName(forum.getName());
					postResponse.setNumberComments(commentService.countAllByPostCommentId(item.getPostForumsId()));
					if(item.getPostForumsId().getDateOfEvent()!= null && item.getPostForumsId().getNameEvent()!= null && item.getPostForumsId().getDateOfEvent()!= null){
						if(memberEventService.existsByUserEventIdAndEventUserId(user,item.getPostForumsId())){
							postResponse.setStatusJoinEventOfUser(1);
						}else{
							postResponse.setStatusJoinEventOfUser(0);
						}
					}
					postResponseList.add(postResponse);
				});
					}
			forumResponse.setListPostResponse(postResponseList);
			return new ResponseEntity<>(forumResponse, HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/joinForum/{id}")
	public ResponseEntity<?> JoinForum(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("forum null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		try {
			if (!forumService.existsByStatusAndForumId(StatusName.is_active, id)) {
				return new ResponseEntity<>(new MessageResponse("no forum found"), HttpStatus.OK);
			}
			forumsEntity forum = forumService.findByForumId(id);
			memberForums memberForums = new memberForums();
			memberForums.setForumUserId(forum);
			memberForums.setUserForumId(user);
			memberForums.setStatus(StatusName.have_not_been_censored);
			memberForumService.save(memberForums);
			return new ResponseEntity<>(new MessageResponse("join forum success"), HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/leaveForum/{id}")
	public ResponseEntity<?> leaveForum(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("forum null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		try {
			if (!forumService.existsByStatusAndForumId(StatusName.is_active, id)) {
				return new ResponseEntity<>(new MessageResponse("no forum found"), HttpStatus.OK);
			}
			
			forumsEntity forum = forumService.findByForumId(id);
			if(memberForumService.existsByUserForumIdAndForumUserIdAndStatus(user,forum,StatusName.censored)||memberForumService.existsByUserForumIdAndForumUserIdAndStatus(user,forum,StatusName.have_not_been_censored))
			{
				if(forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, id, user)) {
					return new ResponseEntity<>(new MessageResponse("user cannot leave forum"), HttpStatus.OK);
				}
				memberForumService.DeleteStatusByUserIdAndForumId(user.getUserid(), forum.getForumId());
				return new ResponseEntity<>(new MessageResponse("leave Forum success"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new MessageResponse("user not join forum"), HttpStatus.OK);
			}
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/viewMemberByMember/{id}")
	public ResponseEntity<?> viewMemberByMember(HttpServletRequest request, @PathVariable("id") Long id,
	                                           @RequestParam(value = "page", required = true) int page,
	                                           @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("forum id null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		forumsEntity forum = forumService.findByForumId(id);
		if (memberForumService.existsByUserForumIdAndForumUserIdAndStatus(user, forum, StatusName.censored)) {
			ViewRequest viewRequest = new ViewRequest();
			Pageable pageable = PageRequest.of(page - 1, viewRequest.getLimitUser(), Sort.by(sortBy).descending());
			List<memberForums> memberForums = memberForumService.findByForumUserIdAndStatus(forum, StatusName.censored, pageable);
			List<userResponse> userResponses = new ArrayList<>();
			memberForums.forEach(item -> {
				userResponse userResponse = new userResponse(item.getUserForumId());
				userResponses.add(userResponse);
			});
			return new ResponseEntity<>(userResponses, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(new MessageResponse("user have not been censored"), HttpStatus.OK);
		}
	}
	@GetMapping("/viewEventInForum/{id}")
	public ResponseEntity<?> viewEventInForum(HttpServletRequest request, @PathVariable("id") Long id,
	                                           @RequestParam(value = "page", required = true) int page) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("forum id null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		forumsEntity forum = forumService.findByForumId(id);
		if (memberForumService.existsByUserForumIdAndForumUserIdAndStatus(user, forum, StatusName.censored)) {
			ViewRequest viewRequest = new ViewRequest();
			viewRequest.setSortBy("modifiedDate");
			Pageable pageable = PageRequest.of(page - 1, viewRequest.getLimitUser(), Sort.by(viewRequest.getSortBy()).descending());
			List<postForum> postList = postForumService.findByDateEvent(forum,StatusName.censored,pageable);
			List<PostResponse> postResponseList = new ArrayList<>();
			if (postList != null) {
				postList.forEach(item -> {
					//List<imagePostEntity> imagePostEntityList = imagePostService.findByPostImageId(item);
					PostResponse postResponse = new PostResponse(item.getPostForumsId(), item.getPostForumsId().getUserPostId(), item.getPostForumsId().getLinkImage());
					postResponse.setForumId(forum.getForumId());
					postResponse.setForumName(forum.getName());
					postResponse.setNumberComments(commentService.countAllByPostCommentId(item.getPostForumsId()));
					if(item.getPostForumsId().getDateOfEvent()!= null && item.getPostForumsId().getNameEvent()!= null && item.getPostForumsId().getDateOfEvent()!= null){
						if(memberEventService.existsByUserEventIdAndEventUserId(user,item.getPostForumsId())){
							postResponse.setStatusJoinEventOfUser(1);
						}else{
							postResponse.setStatusJoinEventOfUser(0);
						}
					}
					postResponseList.add(postResponse);
				});
				
			}
			//forumResponse.setListPostResponse(postResponseList);
			return new ResponseEntity<>(postResponseList, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(new MessageResponse("user have not been censored"), HttpStatus.OK);
		}
	}
}
