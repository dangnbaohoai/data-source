package com.c1se44.school_connect.utils;

import com.c1se44.school_connect.entity.RoleName;
import com.c1se44.school_connect.entity.roleEntity;
import com.c1se44.school_connect.service.IRoleService;
import com.c1se44.school_connect.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class CheckRole {
	@Autowired
	IUserService userService;
	
	@Autowired
	IRoleService roleService;
	
	private String UserName;
	public CheckRole(String userName) {
		this.UserName=userName;
	}
	public boolean isAdmin(String userName) {
		roleEntity adminRole = roleService.findByCode(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role ADMIN NOT FOUND"));
		if(userService.existsByRolesAndUsername(adminRole,userName))
			return true;
		else
			return false;
	}
	
	public boolean isCensor(String userName) {
		roleEntity CenorRole = roleService.findByCode(RoleName.CENSOR).orElseThrow(() -> new RuntimeException("Role ADMIN NOT FOUND"));
		if(userService.existsByRolesAndUsername(CenorRole,userName))
			return true;
		else
			return false;
		
	}
	
	public boolean isUser(String userName) {
		roleEntity userRole = roleService.findByCode(RoleName.USER).orElseThrow(() -> new RuntimeException("Role ADMIN NOT FOUND"));
		if(userService.existsByRolesAndUsername(userRole,userName))
			return true;
		else
			return false;
	}
}
