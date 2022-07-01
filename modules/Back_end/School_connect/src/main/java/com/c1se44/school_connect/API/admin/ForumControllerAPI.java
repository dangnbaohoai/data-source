package com.c1se44.school_connect.API.admin;

import com.c1se44.school_connect.DTO.Request.ForumRequest;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Response.ForumResponse;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.baseResponse;
import com.c1se44.school_connect.DTO.Response.userResponse;
import com.c1se44.school_connect.DTO.jwt.JwtResponse;
import com.c1se44.school_connect.entity.*;
import com.c1se44.school_connect.entity.relationship.memberForums;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.*;
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
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin/forum")
@RestController()
@Api(value = "forum manager")
public class ForumControllerAPI {
	
	@Autowired
	IForumService forumService;
	@Autowired
	IUserService userService;
	@Autowired
	IRoleService roleService;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	IMemberForumService memberForumService;
	@Autowired
	CheckRole checkRole;
	@Autowired
	IPostForumService postForumService;
	@Autowired
	IReportCommentService reportCommentService;
	@Autowired
	IReportPostService reportPostService;
	@PostMapping("/createForum")
	public ResponseEntity<?> createForum(HttpServletRequest request, @Valid @RequestBody ForumRequest forumRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		if (forumService.existsByForumAndStatus(forumRequest.getNameForum(),StatusName.is_active)) {
			return new ResponseEntity<>(new MessageResponse("noNameForum"), HttpStatus.OK);
		}
		if (forumRequest.getCenserId() == null) {
			return new ResponseEntity<>(new MessageResponse("no Censor in"), HttpStatus.OK);
		}
		if (forumRequest.getCoverImage() == null || forumRequest.getCoverImage().trim().isEmpty()) {
			forumRequest.setCoverImage("https://firebasestorage.googleapis.com/v0/b/school-conection.appspot.com/o/groups-default-cover-photo-2x.png?alt=media&token=7ca3470d-8c86-43f7-9cf9-6e8ebab460d8");
		}
		roleEntity roles = roleService.findByCode(RoleName.CENSOR).orElseThrow(() -> new RuntimeException("Role CENSOR NOT FOUND"));
		if (!userService.existsByRolesAndUserid(roles, forumRequest.getCenserId())) {
			return new ResponseEntity<>(new MessageResponse("noCensor found"), HttpStatus.OK);
		}
		userEntity Censor = userService.findByUserId(forumRequest.getCenserId()).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> CenserId" + forumRequest.getCenserId()));
		
		if (Censor == null || forumService.existsByCensorId(StatusName.is_active, Censor)) {
			return new ResponseEntity<>(new MessageResponse("Censor exists"), HttpStatus.OK);
		}
		try {
			forumsEntity forumsEntity = new forumsEntity(forumRequest, Censor);
			forumsEntity forumWasSave = forumService.save(forumsEntity);
			memberForums memberForums = new memberForums();
			memberForums.setStatus(StatusName.censored);
			memberForums.setUserForumId(Censor);
			memberForums.setForumUserId(forumWasSave);
			memberForumService.save(memberForums);
			return new ResponseEntity<>(new MessageResponse("create success"), HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/view")
	public ResponseEntity<?> viewAllForum(HttpServletRequest request,
	                                      @RequestParam(value = "page", required = true) int page,
	                                      @RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		try {
			ViewRequest ViewRequest = new ViewRequest();
			Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitAdmin(), Sort.by(sortBy).descending());
			List<forumsEntity> forumList = forumService.findAll(StatusName.is_active, pageable);
			List<ForumResponse> forumRespons = new ArrayList<>();
			for (forumsEntity forumsEntity : forumList) {
				ForumResponse forumResponse = new ForumResponse(forumsEntity, forumsEntity.getCensorId());
				forumResponse.setNumberMembers(memberForumService.countAllByForumIdsAndStatus(forumsEntity.getForumId(), StatusName.censored));
				forumResponse.setNumberMembersNotCensor(memberForumService.countAllByForumIdsAndStatus(forumsEntity.getForumId(), StatusName.have_not_been_censored));
				forumResponse.setNumberPosts(postForumService.countAllByForumIdsAndStatus(forumsEntity.getForumId(), StatusName.censored));
				forumResponse.setNumberPostsNotCensor(postForumService.countAllByForumIdsAndStatus(forumsEntity.getForumId(), StatusName.have_not_been_censored));
				forumResponse.setNumberReportComments(reportCommentService.countAllByForumCommentReportId(forumsEntity));
				forumResponse.setNumberReportPost(reportPostService.countAllByForumPostReportId(forumsEntity));
				forumRespons.add(forumResponse);
			}
			int totalPage= (int) Math.ceil((double) (forumService.countAll(StatusName.is_active)) / ViewRequest.getLimitAdmin());
			baseResponse Response=new baseResponse();
			Response.setListResult(Arrays.asList(forumRespons.toArray()));
			Response.setTotalPage(totalPage);
			return new ResponseEntity<>(Response, HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/view/{id}")
	public ResponseEntity<?> viewForum(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		forumsEntity forum;
		try {
			forum = forumService.findByForumId(id);
			ForumResponse forumResponse = new ForumResponse(forum, forum.getCensorId());
			return new ResponseEntity<>(forumResponse, HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> DeleteForum(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		try {
			forumService.updateStatusById(StatusName.stop_working, id);
			return new ResponseEntity<>(new MessageResponse("yes"), HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateForum(HttpServletRequest request, @Valid @RequestBody ForumRequest forumRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		if (forumRequest.getCenserId() == null || forumRequest.getNameForum() == null || forumRequest.getForumId() == null) {
			return new ResponseEntity<>(new MessageResponse("no Censor in or forum name or id in"), HttpStatus.OK);
		}
		if (forumRequest.getCoverImage() == null || forumRequest.getCoverImage().trim().isEmpty()) {
			forumRequest.setCoverImage("https://firebasestorage.googleapis.com/v0/b/school-conection.appspot.com/o/groups-default-cover-photo-2x.png?alt=media&token=7ca3470d-8c86-43f7-9cf9-6e8ebab460d8");
		}
		roleEntity roles = roleService.findByCode(RoleName.CENSOR).orElseThrow(() -> new RuntimeException("Role CENSOR NOT FOUND"));
		
		if (!userService.existsByRolesAndUserid(roles, forumRequest.getCenserId())) {// kiem tra role co dung ko
			return new ResponseEntity<>(new MessageResponse("no Censor found"), HttpStatus.OK);
		}
		userEntity Censor = userService.findByUserId(forumRequest.getCenserId()).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> CenserId" + forumRequest.getCenserId()));
		forumsEntity forumcurrent = forumService.findByForumId(forumRequest.getForumId());
		if(forumcurrent== null){
			return new ResponseEntity<>(new MessageResponse("forum not found"), HttpStatus.OK);
		}
		if(!forumcurrent.getCensorId().getUsername().equals(Censor.getUsername())) {
			if (forumService.existsByCensorId(StatusName.is_active, Censor)) {
				return new ResponseEntity<>(new MessageResponse("Censor exists"), HttpStatus.OK);
			}
		}
		try {
			if (!forumcurrent.getName().equals(forumRequest.getNameForum()) && forumService.existsByForumAndStatus(forumRequest.getNameForum(),StatusName.is_active)) {
				return new ResponseEntity<>(new MessageResponse("no forum name found"), HttpStatus.OK);
			}
			
			forumsEntity forumChange = new forumsEntity(forumRequest, Censor);
			forumChange.setForumId(forumcurrent.getForumId());
			forumChange.setCreateDate(forumcurrent.getCreateDate());
			forumChange.setCreatedby(forumcurrent.getCreatedby());
			forumService.save(forumChange);
			if (!forumcurrent.getCensorId().getUsername().equals(Censor.getUsername())) {
				if(memberForumService.existsByUserForumIdAndForumUserIdAndStatus(Censor, forumcurrent, StatusName.censored)){
					memberForumService.DeleteStatusByUserIdAndForumId(forumcurrent.getCensorId().getUserid(),forumcurrent.getForumId());
				}else {
					memberForumService.UpdateUserByUserIdAndForumId(Censor.getUserid(), forumcurrent.getCensorId().getUserid(), forumcurrent.getForumId());
				}
			}
			return new ResponseEntity<>(new MessageResponse("update success!!!"), HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
			
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchForum(HttpServletRequest request,
	                                     @RequestParam(value = "page", required = true) int page,
	                                     @RequestParam(value = "sortBy", required = true) String sortBy,
	                                     @RequestParam(value = "nameForum", required = true) String nameForum) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		try {ViewRequest ViewRequest =new ViewRequest();
			Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitAdmin(), Sort.by(sortBy).ascending());
			List<forumsEntity> forumList;
			forumList = forumService.findAllByStatusAndName(StatusName.is_active, nameForum, pageable).getContent();
			List<ForumResponse> forumRespons = new ArrayList<>();
			for (forumsEntity forumsEntity : forumList) {
				ForumResponse forumResponse = new ForumResponse(forumsEntity, forumsEntity.getCensorId());
				forumRespons.add(forumResponse);
			}
			int totalPage = (int) Math.ceil((double) (forumService.countByStatusAndNameContaining(StatusName.is_active,nameForum) / ViewRequest.getLimitAdmin()));
			baseResponse Response = new baseResponse();
			Response.setListResult(Arrays.asList(forumRespons.toArray()));
			Response.setTotalPage(totalPage);
			return new ResponseEntity<>(Response, HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	//TODO:fis đone
	@GetMapping("/viewCensor")
	public ResponseEntity<?> viewCensor(HttpServletRequest request) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (!checkRole.isAdmin(username)) {
			return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
		}
		roleEntity roles = roleService.findByCode(RoleName.CENSOR).orElseThrow(() -> new RuntimeException("Role CENSOR NOT FOUND"));
		try {
			List<Long> forumList = forumService.findCensors(StatusName.is_active);
			List<userEntity> usersEntity = new ArrayList<>();
			if (!forumList.isEmpty()) {
				usersEntity = userService.findUsernameByRolesAndUserid(roles, forumList);
			} else {
				usersEntity = userService.findUsernameByRolesAndStatus(roles, StatusName.is_active);
			}
			
			List<userResponse> usersResponses = new ArrayList<>();
			for (userEntity userEntity : usersEntity) {
				userResponse userRequest1 = new userResponse(userEntity);
				List<String> roleResponse =new ArrayList<>();
				userEntity.getRoles().forEach(item ->{
					roleResponse.add(item.getCode().toString());
				});
				userRequest1.setRoles(roleResponse);
				usersResponses.add(userRequest1);
			}
			return new ResponseEntity<>(usersResponses, HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
}
