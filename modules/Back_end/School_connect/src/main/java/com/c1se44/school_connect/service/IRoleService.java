package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.RoleName;
import com.c1se44.school_connect.entity.roleEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
	Optional<roleEntity>  findByCode(RoleName name);// tìm kiếm role theo tên
	List<roleEntity> findAll();
}
