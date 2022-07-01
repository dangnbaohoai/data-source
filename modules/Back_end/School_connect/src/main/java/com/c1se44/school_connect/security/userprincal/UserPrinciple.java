package com.c1se44.school_connect.security.userprincal;

import com.c1se44.school_connect.entity.userEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
	private Long id;
	private String name;
	private String username;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> role;
	// constructor
	public UserPrinciple(Long id, String name, String username, String password, Collection<? extends GrantedAuthority> role) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public UserPrinciple() {
	}
	// hàm build UserPrinciple để lưu thành 1 vùng nhớ trên hệ thống
	public static UserPrinciple build(userEntity user){
		// chuyển list sang SimpleGrantedAuthority
		List<GrantedAuthority> authorities =user.getRoles().stream().map(role->
					new SimpleGrantedAuthority(role.getCode().name())).collect(Collectors.toList());
		return new UserPrinciple(
					user.getUserid(),
					user.getFullName(),
					user.getUsername(),
					user.getPassword(),
					authorities
		);
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
