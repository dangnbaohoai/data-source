package com.c1se44.school_connect.API.admin;

import com.c1se44.school_connect.API.user.NotifyUserControllerAPI;
import com.c1se44.school_connect.DTO.Request.PostRequest;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.*;
import com.c1se44.school_connect.DTO.jwt.JwtResponse;
import com.c1se44.school_connect.entity.*;
import com.c1se44.school_connect.entity.relationship.postForum;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.*;
import com.c1se44.school_connect.service.impl.RoleService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin/post")
@RestController()
@Api(value = "post for admin" )
public class PostControllerAPI {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	CheckRole checkRole;
	@Autowired
	IPostService postService;
	@Autowired
	IMemberForumService iMemberForumService;
	@Autowired
	IImagePostService imagePostService;
	@Autowired
	IForumService forumService;
	@Autowired
	IPostForumService postForumService;
	@Autowired
	NotifyUserControllerAPI notifyUserControllerApi;
	@PostMapping("/create")
	public ResponseEntity<?> createAccount(HttpServletRequest request, @Valid @RequestBody PostRequest postRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		if (postRequest.getForumId() == null|| postRequest.getForumId().isEmpty()) {
			return new ResponseEntity<>(new MessageResponse("no forum"), HttpStatus.OK);
		}
		try {
			postsEntity postsEntity = new postsEntity(postRequest);
			postsEntity.setUserPostId(user);
			postsEntity postWasSave = postService.save(postsEntity);
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
			postForumService.DeleteStatusByPostForumsId(postWasSave);
			List<postForum> postForumsList =new ArrayList<>();
			String message =user.getUsername() +" was add new post in the forum you join";
			postRequest.getForumId().forEach(forumId -> {
				if (forumService.existsByStatusAndForumId(StatusName.is_active, forumId)) {
					forumsEntity forumsEntity = forumService.findByForumId(forumId);
					postForum postForum = new postForum();
					postForum.setForumPostId(forumsEntity);
					postForum.setPostForumsId(postWasSave);
					postForum.setStatus(StatusName.censored);
					postForumsList.add(postForum);
					notifyUserControllerApi.NewPost(postWasSave,forumsEntity,message);
				}
			});
			postForumService.saveAll(postForumsList);
			
			return new ResponseEntity<>(new MessageResponse("created success"), HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	// TODO:fix this
	@PutMapping("/update")
	public ResponseEntity<?> update(HttpServletRequest request, @Valid @RequestBody PostRequest postRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		if (postRequest.getForumId() == null|| postRequest.getForumId().isEmpty()) {
			return new ResponseEntity<>(new MessageResponse("no forum"), HttpStatus.OK);
		}
		try {
			
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
				postForumService.DeleteStatusByPostForumsId(postWasSave);
				List<postForum> postForumsList =new ArrayList<>();
				String message =user.getUsername() +" was add update post in the forum you join";
				postRequest.getForumId().forEach(forumId -> {
					if (forumService.existsByStatusAndForumId(StatusName.is_active, forumId)) {
						forumsEntity forumsEntity = forumService.findByForumId(forumId);
						postForum postForum = new postForum();
						postForum.setForumPostId(forumsEntity);
						postForum.setPostForumsId(postWasSave);
						postForum.setStatus(StatusName.censored);
						postForumsList.add(postForum);
						notifyUserControllerApi.NewPost(postWasSave,forumsEntity,message);
					}
				});
				postForumService.saveAll(postForumsList);
				return new ResponseEntity<>(new MessageResponse("update success"), HttpStatus.OK);
			}
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable("id")  Long postId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		
		try {
			if (!postService.existsByPostIdAndUserPostId(postId, user)) {
				return new ResponseEntity<>(new MessageResponse("post not found"), HttpStatus.OK);
			} else {
				postService.deleteByPostIdAndUserPostId(postId, user);
//					List<Long> idImages= imagePostService.findByPostImageId(postId);
//					idImages.forEach(iteam->{
//						imagePostService.deleteByImageId(iteam);
//					});
				//postForumService.DeleteStatusByPostIdAndForumId(postId,forumId);
				
				return new ResponseEntity<>(new MessageResponse("delete success"), HttpStatus.OK);
			}
			
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	//TODO: fix this
	@GetMapping("/view")
	public ResponseEntity<?> viewAllPost(HttpServletRequest request,
	                                      @RequestParam(value = "page", required = true) int page,
	                                      @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		try {
			userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
			ViewRequest ViewRequest = new ViewRequest();
			Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitAdmin(), Sort.by(sortBy).descending());
			List<postsEntity> post = postService.findByUserPostId(user,pageable);
			List<PostResponse> postResponseList = new ArrayList<>();
			if (post != null) {
				post.forEach(item -> {
					List<postForum> forumList= postForumService.findByPostId(item.getPostId(),StatusName.censored);
					List<ForumResponse> forumResponseList=new ArrayList<>();
					forumList.forEach(forum ->{
						ForumResponse forumResponse=new ForumResponse(forum.getForumPostId());
						forumResponseList.add(forumResponse);
					});
					//List<imagePostEntity> imagePostEntityList = imagePostService.findByPostImageId(item);
					PostResponse postResponse = new PostResponse(item, item.getUserPostId(), item.getLinkImage());
					postResponse.setForumList(forumResponseList);
					postResponseList.add(postResponse);
				});
			}
			int totalPage= (int) Math.ceil((double) (postService.countAllPostIdByUserPostId(user.getUserid())) / ViewRequest.getLimitAdmin());
			baseResponse Response=new baseResponse();
			Response.setListResult(Arrays.asList(postResponseList.toArray()));
			Response.setTotalPage(totalPage);
			return new ResponseEntity<>(Response, HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	//TODO: fix this
	@GetMapping("/viewForum")
	public ResponseEntity<?> viewAllForum(HttpServletRequest request) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		try {
			List<Object[]> forumList = forumService.findForumIdAndName(StatusName.is_active);
			List<ForumResponse> forumRespons = new ArrayList<>();
			for (Object[] forumsEntity : forumList) {
				ForumResponse forumResponse = new ForumResponse();
				forumResponse.setForumId(Long.parseLong(forumsEntity[0].toString()));
				forumResponse.setNameForum(String.valueOf(forumsEntity[1]));
				forumRespons.add(forumResponse);
			}
			return new ResponseEntity<>(forumRespons, HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
}
