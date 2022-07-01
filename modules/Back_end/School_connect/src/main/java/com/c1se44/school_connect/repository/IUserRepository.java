package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.roleEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<userEntity, Long> {
	//	Optional là 1 collections
	Optional<userEntity> findByUsernameAndStatus(String name, StatusName status);// tìm kiếm username có trong DB ko
	
	Boolean existsByUsernameAndStatus(String username, StatusName status);// kiểm tra username có tồn tại trong DB không
	
	Boolean existsByEmailAndStatus(String email, StatusName status);
	
	Boolean existsByCodeAndStatus(Long maSo, StatusName status);
	Boolean existsByUsername(String username);// kiểm tra username có tồn tại trong DB không
	
	Boolean existsByEmail(String email);
	
	Boolean existsByCode(Long maSo);
	
	Boolean existsByUserid(Long userid);
	
	List<userEntity> findAll();
	
	Page<userEntity> findAllByStatus(StatusName status, Pageable pageable);
	
	Page<userEntity> findAllByStatusAndCode(StatusName status, Long maso, Pageable pageable);
	
	Page<userEntity> findAllByStatusAndUsernameContaining(StatusName status, String fullName, Pageable pageable);
	int countAllByStatusAndUsernameContaining(StatusName status, String fullName);
	
	@Modifying
	@Query(value = "Delete from users_roles where role_id=? and user_id=?", nativeQuery = true)
	@Transactional
	void deleteRoleById(Long roleId, Long userId);
	
	Optional<userEntity> findByUseridAndStatus(Long userId,StatusName status);
	
	List<userEntity> findByRolesAndStatusAndUseridNotIn(roleEntity roleId,StatusName status ,List<Long> userId);
	
	List<userEntity> findByRolesAndStatus(roleEntity roleId,StatusName status);
	
	@Modifying
	@Query(value = "UPDATE users SET status = ? where id=?", nativeQuery = true)
	@Transactional
	void updateStatusById(String status, Long userId);
	
	Boolean existsByRolesAndUserid(roleEntity roleId, Long userId);
	
	Boolean existsByRolesAndUsername(roleEntity roleId, String userName);
	
	Page<userEntity> findByStatusAndFullNameContaining(StatusName status,String fullName,Pageable pageable);
	
	List<userEntity> findByStatusAndUsernameContainingAndUseridNot(StatusName status,String userName,Long userId);
	
	@Query(value = "select count(*) from users", nativeQuery = true)
	int countAllUserid();
	
	int countAllByStatus(StatusName status);
	@Query(value = "select count(*) from users where status = ?", nativeQuery = true)
	int countAllUserIdByStatus(String status);
}
