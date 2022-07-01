package com.c1se44.school_connect.API.user;

import com.c1se44.school_connect.DTO.Request.ChangeAvatar;
import com.c1se44.school_connect.DTO.Request.ChangePasswordRequest;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Request.userRequest;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.UserListResponse;
import com.c1se44.school_connect.DTO.Response.ForumResponse;
import com.c1se44.school_connect.DTO.Response.userResponse;
import com.c1se44.school_connect.DTO.jwt.JwtResponse;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.IForumService;
import com.c1se44.school_connect.service.IRoleService;
import com.c1se44.school_connect.service.impl.UserService;
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
import java.util.List;
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
@RestController
@Api(value = "Profile of user")
public class ProfileUserControllerAPI {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserService userService;
	@Autowired
	IRoleService roleService;
	@Autowired
	IForumService forumService;
	
	@PutMapping("/change-avatar")
	public ResponseEntity<?> changeAvatar(HttpServletRequest request, @Valid @RequestBody ChangeAvatar changeAvatar) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user;
		try {
			if (changeAvatar.getAvatar() == null) {
				return new ResponseEntity<>(new JwtResponse("no"), HttpStatus.OK);
			} else {
				user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
				user.setAvatar(changeAvatar.getAvatar());
				userService.save(user);
			}
			return new ResponseEntity<>(new JwtResponse("yes"), HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/updateProfile")
	public ResponseEntity<?> updateAccount(HttpServletRequest request, @Valid @RequestBody userRequest userRequest) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity usercurrent;
		try {
			usercurrent = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
			if (!usercurrent.getUsername().equals(userRequest.getUserName())) {
				return new ResponseEntity<>(new MessageResponse("user name was change"), HttpStatus.OK);
			}
			if (!usercurrent.getEmail().equals(userRequest.getEmail())  && userService.existsByEmailAndStatus(userRequest.getEmail(), StatusName.is_active)) {
				return new ResponseEntity<>(new MessageResponse("email exists "), HttpStatus.OK);
			}
			if (!usercurrent.getCode().equals(userRequest.getCode()) ) {
				return new ResponseEntity<>(new MessageResponse("code was change"), HttpStatus.OK);
			}
			if (userRequest.getAvatar() == null || userRequest.getAvatar().trim().isEmpty()) {
				userRequest.setAvatar("https://firebasestorage.googleapis.com/v0/b/school-conection.appspot.com/o/avatar_mac_dinh.png?alt=media&token=cffd2f64-f96a-453c-848e-a7a6d042ea85");
			}
			usercurrent.setFullName(userRequest.getFullName());
			if(userRequest.getEmail()!=null) {
				usercurrent.setEmail(userRequest.getEmail());
			}
			usercurrent.setDateOfBirth(userRequest.getDateOfBirth());
			usercurrent.setAddressOfUser(userRequest.getAddressOfUser());
			usercurrent.setGender(userRequest.getGender());
			usercurrent.setNumberPhone(userRequest.getNumberPhone());
			usercurrent.setAvatar(userRequest.getAvatar());
			usercurrent.setCreateDate(usercurrent.getCreateDate());
			usercurrent.setCreatedby(usercurrent.getCreatedby());
			usercurrent.setPassword(usercurrent.getPassword());
			userService.save(usercurrent);
			return new ResponseEntity<>(new MessageResponse("update success!!!"), HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/seeProfile")
	public ResponseEntity<?> seeProfile(HttpServletRequest request) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
		try {
			userResponse userResponse = new userResponse(user);
			List<String> roleResponse =new ArrayList<>();
			user.getRoles().forEach(item ->{
				roleResponse.add(item.getCode().toString());
			});
			userResponse.setRoles(roleResponse);
			return new ResponseEntity<>(userResponse, HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/seeAnotherProfile/{id}")
	public ResponseEntity<?> seeAnotherProfile(HttpServletRequest request, @PathVariable("id") Long id) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (id == null) {
			return new ResponseEntity<>(new MessageResponse("id null"), HttpStatus.OK);
		}
		if (userService.existsByUsername(username)) {
			try {
				userEntity user = userService.findByUserId(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> user id" + id));
				if (user != null) {
					userResponse userResponse = new userResponse(user);
					return new ResponseEntity<>(userResponse, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(new MessageResponse("user need not found"), HttpStatus.OK);
				}
			} catch (UsernameNotFoundException exception) {
				return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("user not found"), HttpStatus.OK);
		}
	}
	
	@PutMapping("/change-password")
	public ResponseEntity<?> changePassword(HttpServletRequest request, @Valid @RequestBody ChangePasswordRequest changePasswordForm) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		userEntity user;
		try {
			user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
			boolean matches = passwordEncoder.matches(changePasswordForm.getCurrentPassword(), user.getPassword());
			if (changePasswordForm.getNewPassword() != null) {
				if (matches) {
					user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
					userService.save(user);
				} else {
					return new ResponseEntity<>(new MessageResponse("no"), HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(new MessageResponse("yes"), HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	// TODO: need to test this method
	@GetMapping("/search/{key}")
	public ResponseEntity<?> search(HttpServletRequest request, @PathVariable("key") String key,
											@RequestParam(value = "page", required = true) int page,
											@RequestParam(value = "sortBy", required = true) String sortBy) {
		String jwt = jwtTokenFilter.getJwt(request);
		String username = jwtProvider.getUserNameFromToken(jwt);
		if (key == null) {
			return new ResponseEntity<>(new MessageResponse("key null"), HttpStatus.OK);
		}
		if (userService.existsByUsername(username)) {
			ViewRequest ViewRequest=new ViewRequest();
			Pageable pageable = PageRequest.of(page- 1, ViewRequest.getLimitUser(), Sort.by(sortBy).descending());
			try {
				List<userEntity> user = userService.findByFullNameLike(key, pageable);
				List<userResponse> ListUserResponse = new ArrayList<>();
				UserListResponse<userResponse,ForumResponse> searchResponse =new UserListResponse<>();
				if (user != null) {
					user.forEach(item -> {
						userResponse userResponse = new userResponse(item);
						ListUserResponse.add(userResponse);
					});
					searchResponse.setListResult1(ListUserResponse);
				}
				List<forumsEntity> forumsEntities = forumService.findByStatusAndNameLike(key, pageable);
				List<ForumResponse> listForumResponse = new ArrayList<>();
				if (forumsEntities != null) {
					forumsEntities.forEach(item -> {
						ForumResponse forumResponse = new ForumResponse(item);
						listForumResponse.add(forumResponse);
					});
					searchResponse.setListResult2(listForumResponse);
				}
				return new ResponseEntity<>(searchResponse, HttpStatus.OK);
			} catch (UsernameNotFoundException exception) {
				return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(new MessageResponse("user not found"), HttpStatus.OK);
		}
	}
	
}
