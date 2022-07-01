package com.c1se44.school_connect.API.censor;

import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.CommentResponse;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.ReportResponse;
import com.c1se44.school_connect.DTO.Response.userResponse;
import com.c1se44.school_connect.entity.*;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.ICommentService;
import com.c1se44.school_connect.service.IForumService;
import com.c1se44.school_connect.service.IReportCommentService;
import com.c1se44.school_connect.service.IUserService;
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
@RequestMapping("/api/censor/reportComment")
@RestController
@Api(value = "forum manager report comment by censor")
public class ReportCommentManageAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IUserService userService;
	@Autowired
	IForumService forumService;
	@Autowired
	IReportCommentService reportCommentService;
	@Autowired
	ICommentService commentService;
	@GetMapping("/viewReport/{id}")
	public ResponseEntity<?> viewCommentReport(HttpServletRequest request,
	                                            @PathVariable("id") Long id,
	                                            @RequestParam(value = "page", required = true) int page,
	                                            @RequestParam(value = "sortBy", required = true) String sortBy ) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("forum id null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if ( forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, id, user)) {
			ViewRequest vew =new ViewRequest();
			Pageable pageable = PageRequest.of(page - 1, vew.getLimitUser(), Sort.by(sortBy).ascending());
			forumsEntity forum=forumService.findByForumId(id);
			List<reportCommentEntity> reportList =  reportCommentService.findAllByForumCommentReportId(forum,pageable);
			List<ReportResponse> reportResponseList=new ArrayList<>();
			reportList.forEach(item->{
				userResponse userReport=new userResponse(item.getUserCommentsReportId());
				CommentResponse commentReport =new CommentResponse(item.getCommentsReportId(),item.getCommentsReportId().getUserCommentId(),item.getCommentsReportId().getPostCommentId());
				ReportResponse report = new ReportResponse(item,userReport,commentReport);
				reportResponseList.add(report);
			});
			return new ResponseEntity<>(reportResponseList, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new MessageResponse("not is censor"), HttpStatus.OK);
		}
	}
	@PostMapping("/cancelCommentReport/{id}")
	public ResponseEntity<?> cancelCommentReport(HttpServletRequest request,
	                                        @PathVariable("id") Long id,
	                                             @RequestParam(value = "forumId", required = true) Long forumId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("report id null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, forumId, user)) {
			if (reportCommentService.existsByReportCommentId(id)) {
				reportCommentService.deleteByReportCommentId(id);
				return new ResponseEntity<>(new MessageResponse("cancel successfully"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new MessageResponse("report not found"), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("not is censor"), HttpStatus.OK);
		}
	}

	@PostMapping("/deleteComment/{id}")
	public ResponseEntity<?> deleteComment(HttpServletRequest request,
	                                    @PathVariable("id") Long id,
	                                       @RequestParam(value = "forumId", required = true) Long forumId) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("report id null"), HttpStatus.OK);
		}
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (forumService.existsByStatusAndForumIdAndCensorId(StatusName.is_active, forumId, user)) {
			if (reportCommentService.existsByReportCommentId(id)) {
				reportCommentEntity report = reportCommentService.findByReportCommentId(id);
				reportCommentService.deleteByCommentsReportId(report.getCommentsReportId());
				commentService.deleteByCommentId(report.getCommentsReportId().getCommentId());
				commentService.deleteByReply(report.getCommentsReportId().getCommentId());
				return new ResponseEntity<>(new MessageResponse("delete successfully"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new MessageResponse("report not found"), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("not is censor"), HttpStatus.OK);
		}
	}
}
