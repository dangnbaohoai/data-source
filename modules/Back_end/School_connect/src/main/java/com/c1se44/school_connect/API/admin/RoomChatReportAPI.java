package com.c1se44.school_connect.API.admin;

import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.*;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.reportChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.IForumService;
import com.c1se44.school_connect.service.IReportRoomService;
import com.c1se44.school_connect.service.IRoomChatService;
import com.c1se44.school_connect.service.IUserService;
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
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/admin/reportRoom")
@RestController
@Api(value = "report room manager by admin")
public class RoomChatReportAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IUserService userService;
	@Autowired
	IForumService forumService;
	@Autowired
	IReportRoomService reportRoomService;
	@Autowired
	IRoomChatService roomChatService;
	@Autowired
	CheckRole checkRole;
	
	@GetMapping("/viewReport")
	public ResponseEntity<?> viewReport(HttpServletRequest request,
	                                           @RequestParam(value = "page", required = true) int page,
	                                           @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		//userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		ViewRequest vew = new ViewRequest();
		Pageable pageable = PageRequest.of(page - 1, vew.getLimitUser(), Sort.by(sortBy).descending());
		List<reportChatEntity> reportList = reportRoomService.findAllByStatus(StatusName.have_not_been_censored,pageable);
		List<ReportResponse> reportResponseList = new ArrayList<>();
		reportList.forEach(item -> {
			userResponse userReport = new userResponse(item.getUserChatReportId());
			userResponse user1 = new userResponse(item.getRoomChatReportId().getUserChatId1());
			userResponse user2 = new userResponse(item.getRoomChatReportId().getUserChatId2());
			RoomChatResponse roomReport = new RoomChatResponse(item.getRoomChatReportId(), user1, user2);
			ChatResponse messageIsReported = new ChatResponse(new userResponse(item.getMessageReport().getUserSendMessageId()),item.getMessageReport());
			ReportResponse report;
			report = new ReportResponse(item,messageIsReported ,userReport, roomReport);
			reportResponseList.add(report);
		});
		baseResponse results = new baseResponse();
		int totalPage = (int) Math.ceil((double) (reportRoomService.countAll(StatusName.have_not_been_censored) / vew.getLimitAdmin()));
		results.setListResult(Arrays.asList(reportResponseList.toArray()));
		results.setTotalPage(totalPage);
		return new ResponseEntity<>(results, HttpStatus.OK);
	}
	
	@PostMapping("/cancelRoomReport/{id}")
	public ResponseEntity<?> cancelRoomReport(HttpServletRequest request,
	                                             @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("report id null"), HttpStatus.OK);
		}
		//userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		if (reportRoomService.existsByReportRoomIdAndStatus(id,StatusName.have_not_been_censored)) {
			reportRoomService.UpdateStatusByReportRoomId(StatusName.censored,id);
			return new ResponseEntity<>(new MessageResponse("cancel successfully"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new MessageResponse("report not found"), HttpStatus.OK);
		}
	}
	
	@PostMapping("/blockRoom/{id}")
	public ResponseEntity<?> blockRoom(HttpServletRequest request,
	                                       @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("report id null"), HttpStatus.OK);
		}
		//userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		if (reportRoomService.existsByReportRoomIdAndStatus(id,StatusName.have_not_been_censored)) {
			reportChatEntity report = reportRoomService.findByReportRoomId(id);
			roomChatService.updateStatusById(StatusName.stop_working, report.getRoomChatReportId().getRoomId());
			reportRoomService.UpdateStatusByReportRoomId(StatusName.censored,id);
			return new ResponseEntity<>(new MessageResponse("cancel successfully"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new MessageResponse("report not found"), HttpStatus.OK);
		}
	}
}
