package com.c1se44.school_connect.DTO.Response;

import com.c1se44.school_connect.entity.userEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userResponse extends baseResponse{
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
	private String roleOfUser;
	private List<String> roles = new ArrayList<>();
	
	public userResponse(userEntity userEntity){
		this.userId=userEntity.getUserid();
		this.code =userEntity.getCode();
		this.userName=userEntity.getUsername();
		this.fullName=userEntity.getFullName();
		this.email=userEntity.getEmail();
		this.dateOfBirth=userEntity.getDateOfBirth();
		this.gender=userEntity.getGender();
		this.addressOfUser=userEntity.getAddressOfUser();
		this.position=userEntity.getPosition();
		this.avatar=userEntity.getAvatar();
		this.numberPhone=userEntity.getNumberPhone();
		this.setCreateBy(userEntity.getCreatedby());
		this.setCreateDate(userEntity.getCreateDate());
		this.setModifiedBy(userEntity.getModifiedBy());
		this.setModifiedDate(userEntity.getModifiedDate());
		
	}
	
	
	
}
