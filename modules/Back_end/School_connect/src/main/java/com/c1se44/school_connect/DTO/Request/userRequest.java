package com.c1se44.school_connect.DTO.Request;

import com.c1se44.school_connect.entity.userEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class userRequest  {
	private Long userId;
	private Long code;
	private String userName;
	private String password;
	private String fullName;
	private String email;
	private LocalDate dateOfBirth;
	private String gender;
	private String addressOfUser;
	private String numberPhone;
	private String position;
	private String avatar;
	private List<String> roles = new ArrayList<>();
	
}
