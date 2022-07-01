package com.c1se44.school_connect.API.Auth;


import com.c1se44.school_connect.DTO.Request.EmailRequest;
import com.c1se44.school_connect.DTO.Request.ForgetPasswordRequest;
import com.c1se44.school_connect.DTO.Request.userRequest;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.jwt.JwtResponse;
import com.c1se44.school_connect.DTO.jwt.JwtSignIn;
import com.c1se44.school_connect.entity.RoleName;
import com.c1se44.school_connect.entity.roleEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.security.userprincal.UserPrinciple;
import com.c1se44.school_connect.service.impl.RoleService;
import com.c1se44.school_connect.service.impl.UserService;

import com.c1se44.school_connect.service.impl.emailService;
import com.c1se44.school_connect.utils.CheckRole;
import com.c1se44.school_connect.utils.RandomString;


import com.sendgrid.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@CrossOrigin with no arguments means your controller will accept all requests. @CrossOrigin(origins = "http://domain2.com", maxAge = 3600) means it will only accepts requests comming from this url : "http://domain2.com"
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@Api(value = "no authentication")
public class AuthController {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	CheckRole checkRole;
//	@Autowired
//	mailGun mailgun;
	@Autowired
	emailService EmailService;
	@PostMapping("/signup")
	//ResponseEntity<?> là kiểu dữ liệu trả về ntn cũng đc
	public ResponseEntity<?> register(@Valid @RequestBody userRequest userRequest) {
		if (userService.existsByUsername(userRequest.getUserName())) {
			return new ResponseEntity<>(new MessageResponse("nouser"), HttpStatus.OK);
		}
		if (userService.existsByEmail(userRequest.getEmail())) {
			return new ResponseEntity<>(new MessageResponse("noemail"), HttpStatus.OK);
		}
		if (userService.existsByMaso(userRequest.getCode())) {
			return new ResponseEntity<>(new MessageResponse("nomaso"), HttpStatus.OK);
		}
		if (userRequest.getAvatar() == null || userRequest.getAvatar().trim().isEmpty()) {
			userRequest.setAvatar("https://firebasestorage.googleapis.com/v0/b/school-conection.appspot.com/o/avatar_mac_dinh.png?alt=media&token=cffd2f64-f96a-453c-848e-a7a6d042ea85");
		}
		userEntity user = new userEntity(userRequest, passwordEncoder.encode(userRequest.getPassword()));
		List<String> strRoles = userRequest.getRoles();
		Set<roleEntity> roles = new HashSet<>();
		strRoles.forEach(role -> {
			switch (role) {
				case "ADMIN":
					roleEntity adminRole = roleService.findByCode(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role ADMIN NOT FOUND"));
					roles.add(adminRole);
					break;
				case "CENSOR":
					roleEntity centerRole = roleService.findByCode(RoleName.CENSOR).orElseThrow(() -> new RuntimeException("Role CENSOR NOT FOUND"));
					roles.add(centerRole);
					roleEntity usercenterRole = roleService.findByCode(RoleName.USER).orElseThrow(() -> new RuntimeException("ROLE use NOT FOUND"));
					roles.add(usercenterRole);
					break;
				case "USER":
					roleEntity userRole = roleService.findByCode(RoleName.USER).orElseThrow(() -> new RuntimeException("ROLE use NOT FOUND"));
					roles.add(userRole);
					break;
			}
		});
		user.setRoles(roles);
		userService.save(user);
		return new ResponseEntity<>(new MessageResponse("create success!!!"), HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> login(@Valid @RequestBody JwtSignIn signInForm) {
		// khiểm tra username có trong hệ thống không đươc khởi tạo từ @bean của webSecurityConfig
		Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
		// đẩy vào SecurityContextHolder để có thể dùng cho JpaAuditingConfig và cac chức năng khác
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.CreateToken(authentication);
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		return ResponseEntity.ok(new JwtResponse("login success", token, userPrinciple.getId(), userPrinciple.getName(), userPrinciple.getUsername(), userPrinciple.getAuthorities()));
	}
	@PostMapping("/signinAdmin")
	public ResponseEntity<?> loginAdmin(@Valid @RequestBody JwtSignIn signInForm) {
		// khiểm tra username có trong hệ thống không đươc khởi tạo từ @bean của webSecurityConfig
		Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
		// đẩy vào SecurityContextHolder để có thể dùng cho JpaAuditingConfig và cac chức năng khác
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.CreateToken(authentication);
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		if(checkRole.isAdmin(userPrinciple.getUsername())){
			return ResponseEntity.ok(new JwtResponse("login success", token, userPrinciple.getId(), userPrinciple.getName(), userPrinciple.getUsername(), userPrinciple.getAuthorities()));
		}
		else
			return ResponseEntity.ok(new MessageResponse("login fall"));
		
	}
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<?> validMember(@RequestBody ForgetPasswordRequest forgetPassword)  {
		if (userService.existsByUsername(forgetPassword.getUsername())) {
			userEntity member = userService.findByUsername(forgetPassword.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + forgetPassword.getUsername()));
			if (member.getEmail().equals(forgetPassword.getEmail())) {
				int numberOfCharactor = 8;
				RandomString rand = new RandomString();
				String passwordNew = rand.randomAlphaNumeric(numberOfCharactor);
				member.setPassword(passwordEncoder.encode(passwordNew));
				userEntity memberUpdate = userService.save(member);
				String message = "Password of you was reset" +
							"\nYour account registration information has been changed" +
							"\nUsername:" + member.getUsername() +
							"\nPassword:" + passwordNew;
				//SendGmailUtil.sendGmail(member.getEmail(), "Rest password Account", message);
				// Sendgrid
				EmailRequest email=new EmailRequest(member.getEmail(),"Rest password Account",message);
				Response response=EmailService.sendemail(email);
				if(response.getStatusCode()==200||response.getStatusCode()==202) {
					System.out.println(passwordNew);
					return new ResponseEntity<>("send successfully", HttpStatus.OK);
				}
				return new ResponseEntity<>("failed to sent",HttpStatus.NOT_FOUND);
//				//gunMail
//              JsonNode sendEmail=mailgun.sendSimpleMessage(member.getEmail(),"Rest password Account",message);
				
				//System.out.println(sendEmail.toString());
			//	return new ResponseEntity<>(new MessageResponse("Password was send in your email"), HttpStatus.OK);
			}else
				return new ResponseEntity<>(new MessageResponse("email was not right"), HttpStatus.OK);
		}else
		return new ResponseEntity<>(new MessageResponse("username was not right"), HttpStatus.OK);
	}
}
