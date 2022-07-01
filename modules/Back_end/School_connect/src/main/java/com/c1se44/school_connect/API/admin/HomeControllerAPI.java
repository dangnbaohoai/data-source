package com.c1se44.school_connect.API.admin;

import com.c1se44.school_connect.DTO.Response.DataChartResponse;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.StatusResponse;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.IForumService;
import com.c1se44.school_connect.service.IPostService;
import com.c1se44.school_connect.service.IReportRoomService;
import com.c1se44.school_connect.service.IUserService;
import com.c1se44.school_connect.utils.CheckRole;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/admin/home")
@RestController()
@Api(value = "home page admin")
public class HomeControllerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	CheckRole checkRole;
	@Autowired
	IForumService forumService;
	@Autowired
	IUserService userService;
	@Autowired
	IPostService postService;
	@Autowired
	IReportRoomService reportRoomService;
	
	@GetMapping("/status")
	public ResponseEntity<?> viewStatus(HttpServletRequest request) {
		
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		StatusResponse status = new StatusResponse();
		status.setNumberForum(forumService.countAll(StatusName.is_active));
		status.setNumberUser(userService.countAllUserIdByStatus(StatusName.is_active));
		status.setNumberPosts(postService.countAll());
		status.setNumberChatReport(reportRoomService.countAll(StatusName.have_not_been_censored));
		status.setDataBlockChat(ReportCount());
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
	public List<DataChartResponse> ReportCount() {
		List<DataChartResponse> dataList = new ArrayList<>();
//		"dashboard": [
//		{"months": "January","numbers": 2,"id": 1},
//		{"months": "February","numbers": 10,"id": 2},
//		{"months": "March","numbers": 5,"id": 3},
		Calendar instance = Calendar.getInstance();
		int year = instance.get(Calendar.YEAR);
		for (int month = 1; month <= 12; month++) {
			DataChartResponse data= new DataChartResponse();
			switch (month) {
				case 1:
					data=new DataChartResponse("January",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 2:
					data=new DataChartResponse("February",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 3:
					data=new DataChartResponse("March",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 4:
					data=new DataChartResponse("April",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 5:
					data=new DataChartResponse("May",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 6:
					data=new DataChartResponse("June",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 7:
					data=new DataChartResponse("July",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 8:
					data=new DataChartResponse("August",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 9:
					data=new DataChartResponse("September",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 10:
					data=new DataChartResponse("October",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 11:
					data=new DataChartResponse("November",reportRoomService.countAll(StatusName.censored, month, year));
					break;
				case 12:
					data=new DataChartResponse("December",reportRoomService.countAll(StatusName.censored, month, year));
					break;
			}
			dataList.add(data);
		}
		return dataList;
	}
}
