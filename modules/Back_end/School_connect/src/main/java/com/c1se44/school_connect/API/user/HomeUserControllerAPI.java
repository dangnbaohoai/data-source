package com.c1se44.school_connect.API.user;

import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.ForumResponse;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.PostResponse;
import com.c1se44.school_connect.DTO.Response.homeUserResponse;
import com.c1se44.school_connect.entity.*;
import com.c1se44.school_connect.entity.relationship.postForum;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.*;
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
@RequestMapping("/api/user/hone")
@RestController()
@Api(value = "view homepage of user")
public class HomeUserControllerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IUserService userService;
	@Autowired
	IMemberForumService memberForumService;
	@Autowired
	IPostForumService postForumService;
	@Autowired
	IForumService forumService;
	@Autowired
	IPostService postService;
	@Autowired
	IImagePostService imagePostService;
	@Autowired
	ICommentService commentService;
	@Autowired
	IMemberEventService memberEventService;
	@GetMapping("/viewHone")
	public ResponseEntity<?> viewHone(HttpServletRequest request,
	                                      @RequestParam(value = "page", required = true) int page,
	                                      @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		List<Long> forumId = memberForumService.findByUserId(user.getUserid(), StatusName.censored);
		if (forumId == null) {
			return new ResponseEntity<>(new MessageResponse("user not join anny forum"), HttpStatus.OK);
		} else {
			homeUserResponse homeUserResponse = new homeUserResponse();
			List<forumsEntity> forumsEntityList = forumService.findByStatusAndForumIdIn(StatusName.is_active, forumId);
			List<ForumResponse> forumResponseList = new ArrayList<>();
			forumsEntityList.forEach(item -> {
				ForumResponse forumResponse = new ForumResponse(item);
				forumResponseList.add(forumResponse);
			});
			homeUserResponse.setForumResponseList(forumResponseList);
			ViewRequest viewRequest = new ViewRequest();
			viewRequest.setLimitUser(2);
			Pageable pageable = PageRequest.of(page - 1, viewRequest.getLimitUser(), Sort.by(sortBy).descending());
			List<PostResponse> postResponseList = new ArrayList<>();
			forumsEntityList.forEach(iteam -> {
				List<postForum> postList = postForumService.findByForumId(iteam, StatusName.censored, pageable);
				//List<postsEntity> post = postService.findByPostIdIn(postList);
				if (postList != null) {
					postList.forEach(item -> {
						//List<imagePostEntity> imagePostEntityList = imagePostService.findByPostImageId(item);
						PostResponse postResponse = new PostResponse(item.getPostForumsId(), item.getPostForumsId().getUserPostId(), item.getPostForumsId().getLinkImage());
						postResponse.setForumId(iteam.getForumId());
						postResponse.setForumName(iteam.getName());
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
			});
			homeUserResponse.setPostResponseList(postResponseList);
			return new ResponseEntity<>(homeUserResponse, HttpStatus.OK);
		}
	}
	@GetMapping("/viewHonePost")
	public ResponseEntity<?> viewHonePost(HttpServletRequest request,
	                                      @RequestParam(value = "page", required = true) int page,
	                                      @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		List<Long> forumId = memberForumService.findByUserId(user.getUserid(), StatusName.censored);
		if (forumId == null) {
			return new ResponseEntity<>(new MessageResponse("user not join anny forum"), HttpStatus.OK);
		} else {
			homeUserResponse homeUserResponse = new homeUserResponse();
			List<forumsEntity> forumsEntityList = forumService.findByStatusAndForumIdIn(StatusName.is_active, forumId);
			ViewRequest viewRequest = new ViewRequest();
			viewRequest.setLimitUser(2);
			Pageable pageable = PageRequest.of(page - 1, viewRequest.getLimitUser(), Sort.by(sortBy).descending());
			List<PostResponse> postResponseList = new ArrayList<>();
			forumsEntityList.forEach(iteam -> {
				List<postForum> postList = postForumService.findByForumId(iteam, StatusName.censored, pageable);
				//List<postsEntity> post = postService.findByPostIdIn(postList);
				if (postList != null) {
					postList.forEach(item -> {
						//List<imagePostEntity> imagePostEntityList = imagePostService.findByPostImageId(item);
						PostResponse postResponse = new PostResponse(item.getPostForumsId(), item.getPostForumsId().getUserPostId(), item.getPostForumsId().getLinkImage());
						postResponse.setForumId(iteam.getForumId());
						postResponse.setForumName(iteam.getName());
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
			});
			homeUserResponse.setPostResponseList(postResponseList);
			return new ResponseEntity<>(homeUserResponse, HttpStatus.OK);
		}
	}
}
