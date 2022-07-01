package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.RoleName;
import com.c1se44.school_connect.entity.roleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<roleEntity,Long> {
	//	Optional là 1 collections
	//@Query(value = "SELECT e.* FROM roles e where e.code_role =? ", nativeQuery = true)
	Optional<roleEntity>  findByCode(RoleName name);// tìm kiếm role theo tên
	List<roleEntity> findAll();
}
