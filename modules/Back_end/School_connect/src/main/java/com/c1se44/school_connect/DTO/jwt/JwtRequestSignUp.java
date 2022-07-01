package com.c1se44.school_connect.DTO.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
public class JwtRequestSignUp {
	
	private String username;
	private String password;
	private String name;
	private String email;
	private String avatar;
	private List<String> roles;

	public JwtRequestSignUp(String username, String password, String name){
		this.username = username;
		this.password = password;
		this.name = name;
	}
	
	public JwtRequestSignUp() {
	}
	
	// need default constructor for JSON Parsing
	
	
}
