package com.c1se44.school_connect.security.userprincal;

import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.repository.IUserRepository;
import com.c1se44.school_connect.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	IUserService userService;
	// lấy thông tin từ user name
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// tìm username
		userEntity user =userService.findByUsername(username).orElseThrow(()->
					new UsernameNotFoundException("username not found -> username or password"+ username));
		// trả về UserPrinciple đã build
		return UserPrinciple.build(user);
	}
}
