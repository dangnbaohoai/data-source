package com.c1se44.school_connect.API.user;

import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.ForumResponse;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.NotifyResponse;
import com.c1se44.school_connect.entity.*;
import com.c1se44.school_connect.entity.relationship.memberForums;
import com.c1se44.school_connect.entity.relationship.memberNotify;
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
@Api(value = "notify for user")
@RequestMapping("/api/user/notify")
@RestController
public class NotifyUserControllerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IUserService userService;
	@Autowired
	IForumService forumService;
	@Autowired
	INotifyService notifyService;
	@Autowired
	IMemberNotifyService memberNotifyService;
	@Autowired
	IMemberForumService memberForumService;
	@Autowired
	ForumUserControllerAPI forumControllerAPI;
	@Autowired
	CommentUserControllerAPI commentControllerAPI;
	@Autowired
	IRoomChatService roomChatService;
	@GetMapping("/ListNotify")
	public ResponseEntity<?> ListNotify(HttpServletRequest request,
	                                    @RequestParam(value = "page", required = true) int page,
	                                    @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		ViewRequest ViewRequest = new ViewRequest();
		Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitUser(), Sort.by(sortBy).descending());
		//memberNotifyService.UpdateStatusByUserId(StatusName.unread,user.getUserid());
		memberNotifyService.UpdateStatusByUserIdAndStatus(StatusName.unread,user.getUserid(),StatusName.unseen);
		List<memberNotify> notify = memberNotifyService.findAllByUserNotifyId(user,pageable);
		List<NotifyResponse> notifyResponseList=new ArrayList<>();
		notify.forEach(item->{
			NotifyResponse notifyResponse =new NotifyResponse(item);
			if(item.getNotifyUserId().getPostNotify()!=null){
				notifyResponse.setPostId(item.getNotifyUserId().getPostNotify().getPostId());
			}
			ForumResponse forumResponse=new ForumResponse(item.getNotifyUserId().getForumNotify());
			notifyResponse.setForumResponse(forumResponse);
			notifyResponse.setMessage(item.getNotifyUserId().getMessage());
			notifyResponse.setStatusMessage(item.getStatus().toString());
			notifyResponseList.add(notifyResponse);
		});
		return new ResponseEntity<>(notifyResponseList, HttpStatus.OK);
	}
	
	@GetMapping("/ListNotifyAuto")
	public ResponseEntity<?> ListNotifyAuto(HttpServletRequest request) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		NotifyResponse notifyResponse =new NotifyResponse();
		notifyResponse.setNumberNotifyNotSeen(memberNotifyService.countAllByUserNotifyIdAndStatus(user,StatusName.unseen));
		notifyResponse.setNumberMessageNotSeen(roomChatService.countAllByStatusAndUser(StatusName.unseen,user.getUsername()));
		return new ResponseEntity<>(notifyResponse, HttpStatus.OK);
	
	}
	
	@GetMapping("/readNotify/{id}")
	public ResponseEntity<?> readNotify(HttpServletRequest request,@PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		//userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		memberNotify notify = memberNotifyService.findByNotifyId(id);
		if(notify==null){
			return new ResponseEntity<>(new MessageResponse("notify of user not found"), HttpStatus.OK);
		}
		if(notify.getNotifyUserId().getPostNotify()==null){
			memberNotifyService.UpdateStatusByNotifyId(StatusName.read,id);
			return forumControllerAPI.viewForumJoin(request,notify.getNotifyUserId().getForumNotify().getForumId(),1,"createDate");
		}else{
			memberNotifyService.UpdateStatusByNotifyId(StatusName.read,id);
			return commentControllerAPI.viewPostDetail(request,
						notify.getNotifyUserId().getPostNotify().getPostId(),
						1,"createDate",notify.getNotifyUserId().getForumNotify().getForumId());
		}
	}
	
	//@PostMapping("/NewPost")
	public void NewPost(postsEntity postId, forumsEntity forumId,String message) {
		List<memberForums> member= memberForumService.findByForumUserIdAndStatus(forumId, StatusName.censored);
		notifyEntity notify = new notifyEntity(postId,forumId,message);
		notifyEntity notifySave= notifyService.save(notify);
		List<memberNotify> ListNotify = new ArrayList<>();
		member.forEach(item->{
			memberNotify memberNotify=new memberNotify(item.getUserForumId(),notifySave);
			ListNotify.add(memberNotify);
		});
		memberNotifyService.saveAll(ListNotify);
	}
	
	//@PostMapping("/JoinForum")
	public void JoinForum( userEntity userId,  forumsEntity forumId,String message) {
	
		notifyEntity notify = new notifyEntity(forumId,message);
		notifyEntity notifyEntity = notifyService.save(notify);
		memberNotify memberNotify= new memberNotify(userId,notifyEntity);
		memberNotifyService.save(memberNotify);
	}
	public void deletePost( userEntity userId,  forumsEntity forumId,String message) {
		
		notifyEntity notify = new notifyEntity(forumId,message);
		notifyEntity notifyEntity = notifyService.save(notify);
		memberNotify memberNotify= new memberNotify(userId,notifyEntity);
		memberNotifyService.save(memberNotify);
	}
	
}
