package com.c1se44.school_connect.API.user;

import com.c1se44.school_connect.DTO.Request.CommentRequest;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.CommentResponse;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.PostResponse;
import com.c1se44.school_connect.entity.*;
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
@RequestMapping("/api/user/comment")
@RestController()
@Api(value = "comment in post by user")
public class CommentUserControllerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IUserService userService;
	@Autowired
	IPostService postService;
	@Autowired
	ICommentService commentService;
	@Autowired
	IImagePostService imagePostService;
	@Autowired
	IForumService forumService;
	@Autowired
	IMemberEventService memberEventService;
	
	@PostMapping("/commentInPost")
	public ResponseEntity<?> commentInPost(HttpServletRequest request, @RequestBody CommentRequest commentRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		postsEntity post = postService.findByPostId(commentRequest.getPostId());
		if (post == null || user == null) {
			return new ResponseEntity<>(new MessageResponse("post or user not found"), HttpStatus.OK);
		}
		commentEntity comment = new commentEntity(commentRequest, user, post);
		commentEntity commentSave = commentService.save(comment);
		CommentResponse commentResponse = new CommentResponse(comment, commentSave.getUserCommentId(), commentSave.getPostCommentId());
		return new ResponseEntity<>(commentResponse, HttpStatus.OK);
	}
	
	@PutMapping("/updateComment/{id}")
	public ResponseEntity<?> updateComment(HttpServletRequest request, @PathVariable("id") Long id,
	                                       @RequestBody CommentRequest commentRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		commentEntity comment = commentService.findByCommentIdAndUserCommentId(id, user);
		if (comment == null) {
			return new ResponseEntity<>(new MessageResponse("comment not found"), HttpStatus.OK);
		}
		comment.setContent(commentRequest.getContent());
		if (commentRequest.getImageComment() != null) {
			comment.setLinkImage(commentRequest.getImageComment());
		}
		commentEntity commentSave = commentService.save(comment);
		CommentResponse commentResponse = new CommentResponse(comment, commentSave.getUserCommentId(), commentSave.getPostCommentId());
		return new ResponseEntity<>(commentResponse, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteComment")
	public ResponseEntity<?> deleteComment(HttpServletRequest request,
	                                       @RequestParam(value = "commentId", required = true) Long commentId,
	                                       @RequestParam(value = "forumId", required = true) Long forumId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		if(!commentService.existsByCommentId(commentId)){
			return new ResponseEntity<>(new MessageResponse("comment not found"), HttpStatus.OK);
		}
		if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, forumId, user)) {
			commentService.deleteByCommentId(commentId);
			commentService.deleteByReply(commentId);
			return new ResponseEntity<>(new MessageResponse("delete comment successfully"), HttpStatus.OK);
		}
		commentEntity comment = commentService.findByCommentIdAndUserCommentId(commentId, user);
		if (comment == null) {
			return new ResponseEntity<>(new MessageResponse("comment not found"), HttpStatus.OK);
		}
		commentService.deleteByCommentIdAndUserCommentId(commentId, user);
		commentService.deleteByReply(commentId);
		return new ResponseEntity<>(new MessageResponse("delete comment successfully"), HttpStatus.OK);
	}
	
	@GetMapping("/viewReplyComment/{id}")
	public ResponseEntity<?> viewReplyComment(HttpServletRequest request, @PathVariable("id") Long id,
	                                          @RequestParam(value = "commentId", required = true) Long commentId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		postsEntity post = postService.findByPostId(id);
		ViewRequest ViewRequest = new ViewRequest();
		//Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).ascending());
		List<commentEntity> commentEntityList = commentService.findByPostCommentIdAndReply(post, commentId);
		List<CommentResponse> commentResponseList = new ArrayList<>();
		commentEntityList.forEach(item -> {
			CommentResponse commentResponse = new CommentResponse(item, item.getUserCommentId(), item.getPostCommentId());
			commentResponse.setNumberReplyCount(commentService.countAllByPostCommentIdAndReply(item.getPostCommentId(), item.getCommentId()));
			commentResponseList.add(commentResponse);
		});
		return new ResponseEntity<>(commentResponseList, HttpStatus.OK);
	}
	
	@GetMapping("/viewCommentInPost/{id}")
	public ResponseEntity<?> viewCommentInPost(HttpServletRequest request, @PathVariable("id") Long id,
	                                           @RequestParam(value = "page", required = true) int page,
	                                           @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		postsEntity post = postService.findByPostId(id);
		ViewRequest ViewRequest = new ViewRequest();
		Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).descending());
		List<commentEntity> commentEntityList = commentService.findByPostCommentIdAndReply(post, null, pageable);
		List<CommentResponse> commentResponseList = new ArrayList<>();
		commentEntityList.forEach(item -> {
			CommentResponse commentResponse = new CommentResponse(item, item.getUserCommentId(), item.getPostCommentId());
			commentResponse.setNumberReplyCount(commentService.countAllByPostCommentIdAndReply(item.getPostCommentId(), item.getCommentId()));
			commentResponseList.add(commentResponse);
		});
		return new ResponseEntity<>(commentResponseList, HttpStatus.OK);
	}
	
	@GetMapping("/viewPostDetail/{id}")
	public ResponseEntity<?> viewPostDetail(HttpServletRequest request, @PathVariable("id") Long id,
	                                        @RequestParam(value = "page", required = true) int page,
	                                        @RequestParam(value = "sortBy", required = true) String sortBy,
	                                        @RequestParam(value = "forum", required = true) Long forumId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		postsEntity post = postService.findByPostId(id);
		forumsEntity forum = forumService.findByForumId(forumId);
		if (post == null || forum == null) {
			return new ResponseEntity<>(new MessageResponse("post or forum not found"), HttpStatus.OK);
		}
		List<imagePostEntity> imagePostEntityList = imagePostService.findByPostImageId(post);
		PostResponse postResponse = new PostResponse(post, post.getUserPostId(), imagePostEntityList);
		postResponse.setForumId(forum.getForumId());
		if (post.getDateOfEvent() != null && post.getNameEvent() != null && post.getDateOfEvent() != null) {
			if (memberEventService.existsByUserEventIdAndEventUserId(user, post)) {
				postResponse.setStatusJoinEventOfUser(1);
			} else {
				postResponse.setStatusJoinEventOfUser(0);
			}
		}
		postResponse.setForumName(forum.getName());
		ViewRequest ViewRequest = new ViewRequest();
		Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).descending());
		List<commentEntity> commentEntityList = commentService.findByPostCommentIdAndReply(post, null, pageable);
		List<CommentResponse> commentResponseList = new ArrayList<>();
		commentEntityList.forEach(item -> {
			CommentResponse commentResponse = new CommentResponse(item, item.getUserCommentId(), item.getPostCommentId());
			commentResponse.setNumberReplyCount(commentService.countAllByPostCommentIdAndReply(item.getPostCommentId(), item.getCommentId()));
			commentResponseList.add(commentResponse);
		});
		postResponse.setCommentList(commentResponseList);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
}
