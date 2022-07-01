package com.c1se44.school_connect.API.censor;

import com.c1se44.school_connect.API.user.NotifyUserControllerAPI;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.ForumResponse;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.PostResponse;
import com.c1se44.school_connect.DTO.jwt.JwtResponse;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.relationship.postForum;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.IForumService;
import com.c1se44.school_connect.service.IImagePostService;
import com.c1se44.school_connect.service.IPostForumService;
import com.c1se44.school_connect.service.IPostService;
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
@RequestMapping("/api/censor/post")
@RestController
@Api(value = "forum manager post by forum")
public class PostManageAPI {
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
	IPostForumService postForumService;
	@Autowired
	IPostService postService;
	@Autowired
	IImagePostService imagePostService;
	@Autowired
	NotifyUserControllerAPI notifyUserControllerApi;
	
	@GetMapping("/viewPostNeedCensor/{id}")
	public ResponseEntity<?> viewPostNeedCensor(HttpServletRequest request,
	                                            @PathVariable("id") Long id,
	                                            @RequestParam(value = "page", required = true) int page,
	                                            @RequestParam(value = "sortBy", required = true) String sortBy) {
		
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		try {
			if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, id, user)) {
				forumsEntity forum = forumService.findByForumId(id);
				ForumResponse forumResponse = new ForumResponse(forum);
				ViewRequest ViewRequest = new ViewRequest();
				Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).ascending());
				List<postForum> postList = postForumService.findByForumId(forum, StatusName.have_not_been_censored, pageable);
				//List<postsEntity> post = postService.findByPostIdIn(postList);
				List<PostResponse> postResponseList = new ArrayList<>();
				if (postList != null) {
					postList.forEach(item -> {
						//	List<imagePostEntity> imagePostEntityList = imagePostService.findByPostImageId(item);
						PostResponse postResponse = new PostResponse(item.getPostForumsId(), item.getPostForumsId().getUserPostId(), item.getPostForumsId().getLinkImage());
						postResponseList.add(postResponse);
					});
				}
				forumResponse.setListPostResponse(postResponseList);
				return new ResponseEntity<>(forumResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new MessageResponse("not is censor"), HttpStatus.OK);
			}
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/censorPost")
	public ResponseEntity<?> censorPost(HttpServletRequest request,
	                                    @RequestParam(value = "postId", required = true) Long postId,
	                                    @RequestParam(value = "forumId", required = true) Long forumId) {
		
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		try {
			if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, forumId, user)) {
				forumsEntity forum = forumService.findByForumId(forumId);
				postsEntity post = postService.findByPostId(postId);
				if (postForumService.existsByStatusAndForumPostIdAndPostForumsId(StatusName.have_not_been_censored, forum, post)) {
					postForumService.UpdateStatusByPostIdAndForumId(StatusName.censored, postId, forumId);
					String message = user.getUsername() + " had censorship post of " + post.getUserPostId().getUsername() + " in the forum you join";
					notifyUserControllerApi.NewPost(post, forum, message);
				} else {
					return new ResponseEntity<>(new MessageResponse("no post pound"), HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(new MessageResponse("ok"), HttpStatus.OK);
			
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/deletePost")
	public ResponseEntity<?> deletePost(HttpServletRequest request,
	                                    @RequestParam(value = "postId", required = true) Long postId,
	                                    @RequestParam(value = "forumId", required = true) Long forumId) {
		
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		try {
			if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, forumId, user)) {
				forumsEntity forum = forumService.findByForumId(forumId);
				postsEntity post = postService.findByPostId(postId);
				if (postForumService.existsByForumPostIdAndPostForumsId(forum, post)) {
					postForumService.DeleteStatusByPostIdAndForumId(postId, forumId);
					postService.deleteByPostIdAndUserPostId(post.getPostId(), post.getUserPostId());
					String message = user.getUsername() + " had delete post of " + post.getUserPostId().getUsername() + " in the forum you join";
					notifyUserControllerApi.deletePost(post.getUserPostId(), forum, message);
				} else {
					return new ResponseEntity<>(new MessageResponse("no post pound"), HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(new MessageResponse("ok"), HttpStatus.OK);
			
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
}
