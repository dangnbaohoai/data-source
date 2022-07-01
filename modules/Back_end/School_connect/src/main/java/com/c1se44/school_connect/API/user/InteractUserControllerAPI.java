package com.c1se44.school_connect.API.user;

import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.entity.commentEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.ICommentService;
import com.c1se44.school_connect.service.IPostService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RequestMapping("/api/user/interact")
@RestController()
@Api(value = "interact in post and comment by user")
public class InteractUserControllerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IPostService postService;
	@Autowired
	ICommentService commentService;
	
	@PostMapping("/likePost/{id}")
	public ResponseEntity<?> likePost(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("id post null"), HttpStatus.OK);
		}
		postsEntity post = postService.findByPostId(id);
		if (post == null) {
			return new ResponseEntity<>(new MessageResponse("post not found"), HttpStatus.OK);
		}
		int numberLike = post.getNumberLike() + 1;
		postService.updateNumberLikeByPostId(numberLike, post.getPostId());
		return new ResponseEntity<>(new MessageResponse("like post successfully"), HttpStatus.OK);
	}
	
	@PostMapping("/UnlikePost/{id}")
	public ResponseEntity<?> UnlikePost(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("id post null"), HttpStatus.OK);
		}
		postsEntity post = postService.findByPostId(id);
		if (post == null) {
			return new ResponseEntity<>(new MessageResponse("post not found"), HttpStatus.OK);
		}
		int numberLike = post.getNumberLike() - 1;
		postService.updateNumberLikeByPostId(numberLike, post.getPostId());
		return new ResponseEntity<>(new MessageResponse("Unlike post successfully"), HttpStatus.OK);
	}
	
	@PostMapping("/likeComment/{id}")
	public ResponseEntity<?> likeComment(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("id comment null"), HttpStatus.OK);
		}
		commentEntity comment = commentService.findByCommentId(id);
		if (comment == null) {
			return new ResponseEntity<>(new MessageResponse("comment not found"), HttpStatus.OK);
		}
		int numberLike = comment.getLike() + 1;
		postService.updateNumberLikeByPostId(numberLike, comment.getCommentId());
		return new ResponseEntity<>(new MessageResponse("like comment successfully"), HttpStatus.OK);
	}
	
	@PostMapping("/UnlikeComment/{id}")
	public ResponseEntity<?> UnlikeComment(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("id comment null"), HttpStatus.OK);
		}
		commentEntity comment = commentService.findByCommentId(id);
		if (comment == null) {
			return new ResponseEntity<>(new MessageResponse("comment not found"), HttpStatus.OK);
		}
		int numberLike = comment.getLike() - 1;
		postService.updateNumberLikeByPostId(numberLike, comment.getCommentId());
		return new ResponseEntity<>(new MessageResponse("unlike comment successfully"), HttpStatus.OK);
	}
}