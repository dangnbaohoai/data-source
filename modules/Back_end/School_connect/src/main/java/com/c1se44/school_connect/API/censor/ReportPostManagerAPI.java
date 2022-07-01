package com.c1se44.school_connect.API.censor;

import com.c1se44.school_connect.API.user.NotifyUserControllerAPI;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.PostResponse;
import com.c1se44.school_connect.DTO.Response.ReportResponse;
import com.c1se44.school_connect.DTO.Response.userResponse;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.reportPostEntity;
import com.c1se44.school_connect.entity.userEntity;
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
@RequestMapping("/api/censor/reportPost")
@RestController
@Api(value = "forum manager report post by censor")
public class ReportPostManagerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IUserService userService;
	@Autowired
	IForumService forumService;
	@Autowired
	IReportPostService reportPostService;
	@Autowired
	IImagePostService imagePostService;
	@Autowired
	IPostService postService;
	@Autowired
	NotifyUserControllerAPI notifyUserControllerApi;
	
	@GetMapping("/viewPostReport/{id}")
	public ResponseEntity<?> viewPostReport(HttpServletRequest request,
	                                        @PathVariable("id") Long id,
	                                        @RequestParam(value = "page", required = true) int page,
	                                        @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("forum id null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, id, user)) {
			ViewRequest vew = new ViewRequest();
			Pageable pageable = PageRequest.of(page - 1, vew.getLimitUser(), Sort.by(sortBy).ascending());
			forumsEntity forum = forumService.findByForumId(id);
			List<reportPostEntity> listReportPost = reportPostService.findAllByForumPostReportId(forum, pageable);
			List<ReportResponse> listReport = new ArrayList<>();
			listReportPost.forEach(item -> {
				userResponse userReport = new userResponse(item.getUserPostReportId());
				PostResponse post = new PostResponse(item.getPostReportId(), item.getUserPostReportId(), item.getPostReportId().getLinkImage());
				post.setForumId(forum.getForumId());
				post.setForumName(forum.getName());
				ReportResponse report = new ReportResponse(item, userReport, post);
				listReport.add(report);
			});
			return new ResponseEntity<>(listReport, HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(new MessageResponse("not is censor"), HttpStatus.OK);
		}
	}
	
	@PostMapping("/cancelPostReport/{id}")
	public ResponseEntity<?> viewPostReport(HttpServletRequest request,
	                                        @PathVariable("id") Long id,
	                                        @RequestParam(value = "forumId", required = true) Long forumId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("report id null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, forumId, user)) {
			if (reportPostService.existsByReportPostId(id)) {
				reportPostService.deleteByReportPostId(id);
				return new ResponseEntity<>(new MessageResponse("cancel successfully"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new MessageResponse("report not found"), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("not is censor"), HttpStatus.OK);
		}
	}
	
	@PostMapping("/deletePost/{id}")
	public ResponseEntity<?> deletePost(HttpServletRequest request,
	                                    @PathVariable("id") Long id,
	                                    @RequestParam(value = "forumId", required = true) Long forumId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("report id null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, forumId, user)) {
			if (reportPostService.existsByReportPostId(id)) {
				reportPostEntity report = reportPostService.findByReportPostId(id);
				reportPostService.deleteByPostReportId(report.getPostReportId());
				postService.deleteByPostId(report.getPostReportId().getPostId());
				String message = user.getUsername() + " had delete post of " + report.getPostReportId().getUserPostId().getUsername() + " in the forum you join";
				notifyUserControllerApi.deletePost(report.getPostReportId().getUserPostId(), report.getForumPostReportId(), message);
				return new ResponseEntity<>(new MessageResponse("delete successfully"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new MessageResponse("report not found"), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("not is censor"), HttpStatus.OK);
		}
	}
}
