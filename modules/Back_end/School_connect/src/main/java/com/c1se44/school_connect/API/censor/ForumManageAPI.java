package com.c1se44.school_connect.API.censor;

import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.ForumResponse;
import com.c1se44.school_connect.DTO.Response.PostResponse;
import com.c1se44.school_connect.DTO.jwt.JwtResponse;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.relationship.postForum;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.*;
import com.c1se44.school_connect.service.impl.UserService;
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
@RequestMapping("/api/censor/forum")
@RestController
@Api(value = "forum manager by censor")
public class ForumManageAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	UserService userService;
	@Autowired
	IPostForumService postForumService;
	@Autowired
	IPostService postService;
	@Autowired
	IImagePostService imagePostService;
	@Autowired
	IForumService forumService;
	@Autowired
	IMemberForumService memberForumService;
	@Autowired
	IReportCommentService reportCommentService;
	@Autowired
	IReportPostService reportPostService;
	@Autowired
	ICommentService commentService;
	@Autowired
	IMemberEventService memberEventService;
	
	@GetMapping("/viewForumByCensor")
	public ResponseEntity<?> viewForumByCensor(HttpServletRequest request, userEntity user, Long forumId, @RequestBody ViewRequest ViewRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		try {
			forumsEntity forum = forumService.findByForumId(forumId);
			ForumResponse forumResponse = new ForumResponse(forum);
			Pageable pageable = PageRequest.of(ViewRequest.getPage() - 1, ViewRequest.getLimitUser(), Sort.by(ViewRequest.getSortBy()).descending());
			List<postForum> postList = postForumService.findByForumId(forum, StatusName.censored, pageable);
			//List<postsEntity> post = postService.findByPostIdIn(postList);
			List<PostResponse> postResponseList = new ArrayList<>();
			if (postList != null) {
				postList.forEach(item -> {
					//List<imagePostEntity> imagePostEntityList = imagePostService.findByPostImageId(item);
					PostResponse postResponse = new PostResponse(item.getPostForumsId(), item.getPostForumsId().getUserPostId(), item.getPostForumsId().getLinkImage());
					postResponse.setForumId(forum.getForumId());
					postResponse.setForumName(forum.getName());
					postResponse.setNumberComments(commentService.countAllByPostCommentId(item.getPostForumsId()));
					if (item.getPostForumsId().getDateOfEvent() != null && item.getPostForumsId().getNameEvent() != null && item.getPostForumsId().getDateOfEvent() != null) {
						if (memberEventService.existsByUserEventIdAndEventUserId(user, item.getPostForumsId())) {
							postResponse.setStatusJoinEventOfUser(1);
						} else {
							postResponse.setStatusJoinEventOfUser(0);
						}
					}
					postResponseList.add(postResponse);
				});
			}
			forumResponse.setListPostResponse(postResponseList);
			forumResponse.setNumberMembers(memberForumService.countAllByForumIdsAndStatus(forumId, StatusName.censored));
			forumResponse.setNumberMembersNotCensor(memberForumService.countAllByForumIdsAndStatus(forumId, StatusName.have_not_been_censored));
			forumResponse.setNumberPosts(postForumService.countAllByForumIdsAndStatus(forumId, StatusName.censored));
			forumResponse.setNumberPostsNotCensor(postForumService.countAllByForumIdsAndStatus(forumId, StatusName.have_not_been_censored));
			forumResponse.setNumberReportComments(reportCommentService.countAllByForumCommentReportId(forum));
			forumResponse.setNumberReportPost(reportPostService.countAllByForumPostReportId(forum));
			forumResponse.setIsCensor(1);
			return new ResponseEntity<>(forumResponse, HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

//	public List<DataChartResponse> ReportCount() {
//		List<DataChartResponse> dataList = new ArrayList<>();
////		"dashboard": [
////		{"months": "January","numbers": 2,"id": 1},
////		{"months": "February","numbers": 10,"id": 2},
////		{"months": "March","numbers": 5,"id": 3},
//		Calendar instance = Calendar.getInstance();
//		int year = instance.get(Calendar.YEAR);
//		for (int hours = 1; hours <= 12; hours++) {
//			DataChartResponse data= new DataChartResponse();
//			switch (month) {
//				case 1:
//					data=new DataChartResponse("January",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 2:
//					data=new DataChartResponse("February",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 3:
//					data=new DataChartResponse("March",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 4:
//					data=new DataChartResponse("April",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 5:
//					data=new DataChartResponse("May",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 6:
//					data=new DataChartResponse("June",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 7:
//					data=new DataChartResponse("July",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 8:
//					data=new DataChartResponse("August",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 9:
//					data=new DataChartResponse("September",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 10:
//					data=new DataChartResponse("October",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 11:
//					data=new DataChartResponse("November",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//				case 12:
//					data=new DataChartResponse("December",reportRoomService.countAll(StatusName.censored, hours, year));
//					break;
//			}
//			dataList.add(data);
//		}
//		return dataList;
//	}
}

