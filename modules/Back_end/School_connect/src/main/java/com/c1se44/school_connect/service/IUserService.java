package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.roleEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
	Optional<userEntity> findByUsername(String name);// tìm kiếm username có trong DB ko
	
	Optional<userEntity> findByUserId(Long userId);
	
	Boolean existsByUsername(String username);// kiểm tra username có tồn tại trong DB không
	Boolean existsByUsernameAndStatus(String username,StatusName status);
	userEntity save(userEntity save);
	
	Boolean existsByEmail(String email);
	Boolean existsByEmailAndStatus(String email,StatusName status);
	Boolean existsByMaso(Long maSo);
	Boolean existsByMasoAndStatus(Long maSo,StatusName status);
	
	List<userEntity> findAll(StatusName status, Pageable pageable);
	
	List<userEntity> findAllByStatusAndMaso(StatusName status, Long maso, Pageable pageable);
	
	List<userEntity> findAllByStatusAndFullName(StatusName status, String fullName, Pageable pageable);
	
	List<userEntity> findUsernameByRolesAndUserid(roleEntity roleId, List<Long> CensorId);
	List<userEntity> findUsernameByRolesAndStatus(roleEntity roleId,StatusName status);
	void deleteRoleById(Long roleId, Long userId);
	
	Boolean existsByRolesAndUserid(roleEntity roleId, Long userid);
	
	void updateStatusById(StatusName status, Long userId);
	Boolean existsByUserid(Long userid);
	Boolean existsByRolesAndUsername(roleEntity roleId, String userName);
	List<userEntity> findAll();
	List<userEntity> findByFullNameLike(String fullName,Pageable pageable);
	List<userEntity> findByStatusAndUsernameContaining(StatusName status,String userName,Long userId);
	int countAllByStatusAndUsernameContaining(StatusName status, String fullName);
	int countAll();
	int countAllUserIdByStatus(StatusName status);
}
