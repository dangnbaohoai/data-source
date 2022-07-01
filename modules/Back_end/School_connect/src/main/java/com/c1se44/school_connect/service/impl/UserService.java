package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.roleEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.repository.IUserRepository;
import com.c1se44.school_connect.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
	@Autowired
	IUserRepository userRepository;
	
	@Override
	public Optional<userEntity> findByUsername(String name) {
		return userRepository.findByUsernameAndStatus(name, StatusName.is_active);
	}
	
	@Override
	public Optional<userEntity> findByUserId(Long userId) {
		return userRepository.findByUseridAndStatus(userId, StatusName.is_active);
	}
	
	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsernameAndStatus(username, StatusName.is_active);
	}
	
	@Override
	public Boolean existsByUsernameAndStatus(String username, StatusName status) {
		return userRepository.existsByUsername(username);
	}
	
	@Override
	public userEntity save(userEntity save) {
		return userRepository.save(save);
	}
	
	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmailAndStatus(email, StatusName.is_active);
	}
	
	@Override
	public Boolean existsByEmailAndStatus(String email, StatusName status) {
		return userRepository.existsByEmail(email);
	}
	
	@Override
	public Boolean existsByMaso(Long maSo) {
		return userRepository.existsByCodeAndStatus(maSo, StatusName.is_active);
	}
	
	@Override
	public Boolean existsByMasoAndStatus(Long maSo, StatusName status) {
		return userRepository.existsByCode(maSo);
	}
	
	@Override
	public List<userEntity> findAll(StatusName status, Pageable pageable) {
		List<userEntity> userEntitys = userRepository.findAllByStatus(status, pageable).getContent();
		return userEntitys;
	}
	
	@Override
	public List<userEntity> findAllByStatusAndMaso(StatusName status, Long maso, Pageable pageable) {
		return userRepository.findAllByStatusAndCode(status, maso, pageable).getContent();
	}
	
	@Override
	public List<userEntity> findAllByStatusAndFullName(StatusName status, String fullName, Pageable pageable) {
		return userRepository.findAllByStatusAndUsernameContaining(status, fullName, pageable).getContent();
	}
	
	@Override
	public List<userEntity> findUsernameByRolesAndUserid(roleEntity roleId, List<Long> CensorId) {
		return userRepository.findByRolesAndStatusAndUseridNotIn(roleId, StatusName.is_active, CensorId);
	}
	
	@Override
	public List<userEntity> findUsernameByRolesAndStatus(roleEntity roleId, StatusName status) {
		return userRepository.findByRolesAndStatus(roleId, status);
	}
	
	@Override
	public void deleteRoleById(Long roleId, Long userId) {
		userRepository.deleteRoleById(roleId, userId);
	}
	
	@Override
	public Boolean existsByRolesAndUserid(roleEntity roleId, Long userId) {
		return userRepository.existsByRolesAndUserid(roleId, userId);
	}
	
	@Override
	public void updateStatusById(StatusName status, Long userId) {
		userRepository.updateStatusById(status.toString(), userId);
	}
	
	@Override
	public Boolean existsByUserid(Long userid) {
		return userRepository.existsByUserid(userid);
	}
	
	@Override
	public Boolean existsByRolesAndUsername(roleEntity roleId, String userName) {
		return userRepository.existsByRolesAndUsername(roleId, userName);
	}
	
	@Override
	public List<userEntity> findAll() {
		return userRepository.findAll();
	}
	
	@Override
	public List<userEntity> findByFullNameLike(String fullName, Pageable pageable) {
		return userRepository.findByStatusAndFullNameContaining(StatusName.is_active, fullName, pageable).getContent();
	}
	
	@Override
	public List<userEntity> findByStatusAndUsernameContaining(StatusName status, String userName,Long userId) {
		return userRepository.findByStatusAndUsernameContainingAndUseridNot(status, userName,userId);
	}
	
	@Override
	public int countAllByStatusAndUsernameContaining(StatusName status, String fullName) {
		return userRepository.countAllByStatusAndUsernameContaining(status,fullName);
	}
	
	@Override
	public int countAll() {
		return userRepository.countAllUserid();
	}
	
	@Override
	public int countAllUserIdByStatus(StatusName status) {
		return userRepository.countAllUserIdByStatus(status.toString());
	}
	
	
}
