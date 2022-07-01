package com.c1se44.school_connect.API.user;

import com.c1se44.school_connect.API.censor.PostManageAPI;
import com.c1se44.school_connect.DTO.Request.PostRequest;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.entity.*;
import com.c1se44.school_connect.entity.relationship.postForum;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.*;
import com.c1se44.school_connect.service.impl.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/user/post")
@RestController()
@Api(value = "post for user")
public class PostUserControllerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	UserService userService;
	@Autowired
	IPostService postService;
	@Autowired
	IImagePostService imagePostService;
	@Autowired
	IPostForumService postForumService;
	@Autowired
	IForumService forumService;
	@Autowired
	IMemberForumService memberForumService;
	@Autowired
	PostManageAPI postManageAPI;
	
	@PostMapping("/createPost")
	public ResponseEntity<?> createPost(HttpServletRequest request,
	                                    @Valid @RequestBody PostRequest postRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		if (postRequest.getForumIdForUser() == null) {
			return new ResponseEntity<>(new MessageResponse("no forum"), HttpStatus.OK);
		}
		try {
			forumsEntity forum = forumService.findByForumId(postRequest.getForumIdForUser());
			if (forum == null) {
				return new ResponseEntity<>(new MessageResponse("no forum found"), HttpStatus.OK);
			}
			if (memberForumService.existsByUserForumIdAndForumUserIdAndStatus(user, forum, StatusName.censored)) {
				postsEntity postsEntity = new postsEntity(postRequest);
				postsEntity.setUserPostId(user);
				postsEntity postWasSave = postService.save(postsEntity);
				List<imagePostEntity> imagePostList =new ArrayList<>();
				if (postRequest.getLinkImage() != null) {
					postRequest.getLinkImage().forEach(image -> {
						imagePostEntity imagePost = new imagePostEntity();
						imagePost.setLinkImage(image);
						imagePost.setPostImageId(postWasSave);
						imagePostList.add(imagePost);
					});
				}
				imagePostService.saveAll(imagePostList);
				postForum postForum = new postForum();
				postForum.setForumPostId(forum);
				postForum.setPostForumsId(postWasSave);
				if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, postRequest.getForumIdForUser(), user)) {
					postForum.setStatus(StatusName.censored);
				} else {
					postForum.setStatus(StatusName.have_not_been_censored);
				}
				postForumService.save(postForum);
				
				return new ResponseEntity<>(new MessageResponse("created success"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new MessageResponse("user not join forum"), HttpStatus.OK);
			}
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/updatePost")
	public ResponseEntity<?> updatePost(HttpServletRequest request, @Valid @RequestBody PostRequest postRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		if (postRequest.getForumIdForUser() == null) {
			return new ResponseEntity<>(new MessageResponse("no forum"), HttpStatus.OK);
		}
		try {
			forumsEntity forum = forumService.findByForumId(postRequest.getForumIdForUser());
			if (forum == null) {
				return new ResponseEntity<>(new MessageResponse("no forum found"), HttpStatus.OK);
			}
			if (memberForumService.existsByUserForumIdAndForumUserIdAndStatus(user, forum, StatusName.censored)) {
				if (!postService.existsByPostIdAndUserPostId(postRequest.getPostId(), user)) {
					return new ResponseEntity<>(new MessageResponse("post not found"), HttpStatus.OK);
				} else {
					
					postsEntity postCurrent = postService.findByPostId(postRequest.getPostId());
					
					postsEntity postsChange = new postsEntity(postRequest);
					postsChange.setPostId(postCurrent.getPostId());
					postsChange.setCreatedby(postCurrent.getCreatedby());
					postsChange.setCreateDate(postCurrent.getCreateDate());
					postsChange.setUserPostId(user);
					postsEntity postWasSave = postService.save(postsChange);
					
					imagePostService.deleteByPostImageId(postWasSave);
					List<imagePostEntity> imagePostList =new ArrayList<>();
					if (postRequest.getLinkImage() != null) {
						postRequest.getLinkImage().forEach(image -> {
							imagePostEntity imagePost = new imagePostEntity();
							imagePost.setLinkImage(image);
							imagePost.setPostImageId(postWasSave);
							imagePostList.add(imagePost);
						});
					}
					imagePostService.saveAll(imagePostList);
					if(forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, postRequest.getForumIdForUser(), user)) {
						return new ResponseEntity<>(new MessageResponse("update success"), HttpStatus.OK);
					} else {
						postForumService.UpdateStatusByPostIdAndForumId(StatusName.have_not_been_censored, postRequest.getPostId(), postRequest.getForumIdForUser());
					}
					
					return new ResponseEntity<>(new MessageResponse("update success"), HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(new MessageResponse("user not join forum"), HttpStatus.OK);
			}
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/deletePost")
	public ResponseEntity<?> deletePost(HttpServletRequest request,
	                                    @RequestParam(value = "postId", required = true) Long postId,
	                                    @RequestParam(value = "forumId", required = true) Long forumId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		if (forumId == null || postId == null) {
			return new ResponseEntity<>(new MessageResponse("forum or postId null"), HttpStatus.OK);
		}
		try {
			forumsEntity forum = forumService.findByForumId(forumId);
			if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, forumId, user)) {
				postsEntity post = postService.findByPostId(postId);
				if (postForumService.existsByForumPostIdAndPostForumsId(forum, post)) {
					//postForumService.DeleteStatusByPostIdAndForumId(postId, forumId);
					postService.deleteByPostIdAndUserPostId(post.getPostId(), post.getUserPostId());
				} else {
					return new ResponseEntity<>(new MessageResponse("no post pound"), HttpStatus.OK);
				}
				return new ResponseEntity<>(new MessageResponse("ok"), HttpStatus.OK);
			}
			if (forum == null) {
				return new ResponseEntity<>(new MessageResponse("no forum found"), HttpStatus.OK);
			}
			if (memberForumService.existsByUserForumIdAndForumUserIdAndStatus(user, forum, StatusName.censored)) {
				if (!postService.existsByPostIdAndUserPostId(postId, user)) {
					return new ResponseEntity<>(new MessageResponse("post not found"), HttpStatus.OK);
				} else {
					//postForumService.DeleteStatusByPostIdAndForumId(postId, forumId);
					postService.deleteByPostIdAndUserPostId(postId, user);
//					List<Long> idImages= imagePostService.findByPostImageId(postId);
//					idImages.forEach(iteam->{
//						imagePostService.deleteByImageId(iteam);
//					});
					return new ResponseEntity<>(new MessageResponse("delete success"), HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(new MessageResponse("user not join forum"), HttpStatus.OK);
			}
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	
}
