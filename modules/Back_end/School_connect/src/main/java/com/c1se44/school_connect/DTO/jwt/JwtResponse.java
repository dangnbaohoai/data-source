package com.c1se44.school_connect.DTO.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private  String message;
	private String tokenType = "Bearer";
	private String token;
	private String name;
	private String username;
	private Long userId;
	private Collection<?extends GrantedAuthority>authorities;
	
  public JwtResponse(String Message) {
    this.message = Message;
  }
	
	public JwtResponse(String message,String token,Long userId, String name,String userName, Collection<? extends GrantedAuthority> authorities) {
  	    this.message = message;
  	    this.token = token;
  	    this.userId = userId;
  	    this.name = name;
  	    this.username=userName;
  	    this.authorities = authorities;
	}
}