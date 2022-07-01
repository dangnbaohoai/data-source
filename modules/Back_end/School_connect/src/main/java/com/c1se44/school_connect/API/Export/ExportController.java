package com.c1se44.school_connect.API.Export;

import com.c1se44.school_connect.DTO.Response.ForumResponse;
import com.c1se44.school_connect.Exporter.ForumExcelExporter;
import com.c1se44.school_connect.Exporter.UserExcelExporter;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.service.*;
import com.c1se44.school_connect.service.impl.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "*")
@RequestMapping("/api/export")
@RestController()
@Api(value = "export data")
public class ExportController {
	@Autowired
	UserService userService;
	@Autowired
	IForumService forumService;
	@Autowired
	IMemberForumService memberForumService;
	@Autowired
	IPostForumService postForumService;
	@Autowired
	IReportCommentService reportCommentService;
	@Autowired
	IReportPostService reportPostService;

	@GetMapping("/account")
	public void  account(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<userEntity> listUsers = userService.findAll();
		UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
		excelExporter.export(response);
		//return new ResponseEntity<>(new MessageResponse("ok"), HttpStatus.OK);
	}
	@GetMapping("/forum")
	public void  forum(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=forum_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		List<ForumResponse> listResponse =new ArrayList<>();
		List<forumsEntity> listForum = forumService.findAll();
		listForum.forEach(item->{
			ForumResponse forum =new ForumResponse(item,item.getCensorId());
			forum.setNumberMembers(memberForumService.countAllByForumIdsAndStatus(item.getForumId(), StatusName.censored));
			forum.setNumberMembersNotCensor(memberForumService.countAllByForumIdsAndStatus(item.getForumId(), StatusName.have_not_been_censored));
			forum.setNumberPosts(postForumService.countAllByForumIdsAndStatus(item.getForumId(), StatusName.censored));
			forum.setNumberPostsNotCensor(postForumService.countAllByForumIdsAndStatus(item.getForumId(), StatusName.have_not_been_censored));
			forum.setNumberReportComments(reportCommentService.countAllByForumCommentReportId(item));
			forum.setNumberReportPost(reportPostService.countAllByForumPostReportId(item));
			forum.setCenserId(item.getCensorId().getCode());
			forum.setStatus(item.getStatus().toString());
			listResponse.add(forum);
		});
		ForumExcelExporter excelExporter = new ForumExcelExporter(listResponse);
		excelExporter.export(response);
		//return new ResponseEntity<>(new MessageResponse("ok"), HttpStatus.OK);
	}
}
