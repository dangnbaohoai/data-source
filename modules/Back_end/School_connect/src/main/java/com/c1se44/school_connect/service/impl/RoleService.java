package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.RoleName;
import com.c1se44.school_connect.entity.roleEntity;
import com.c1se44.school_connect.repository.IRoleRepository;
import com.c1se44.school_connect.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoleService implements IRoleService {
	@Autowired
	IRoleRepository roleRepository;
	@Override
	public Optional<roleEntity> findByCode(RoleName name) {
		return roleRepository.findByCode(name);
	}
	
	@Override
	public List<roleEntity> findAll() {
		return roleRepository.findAll();
	}
	
}
