package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.relationship.memberNotify;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IMemberNotifyRepository extends JpaRepository<memberNotify,Long> {
	Page<memberNotify> findAllByUserNotifyId(userEntity user, Pageable pageable);
	memberNotify findByNotifyId(Long notifyId);
	int countAllByUserNotifyIdAndStatus(userEntity user, StatusName status);
	@Modifying
	@Query(value = "Update member_notify Set status=? where user_id=?", nativeQuery = true)
	@Transactional
	void UpdateStatusByUserId(String status, Long userId);
	@Modifying
	@Query(value = "Update member_notify Set status=? where user_id=? and status=?", nativeQuery = true)
	@Transactional
	void UpdateStatusByUserIdAndStatus(String status1, Long userId,String status2);
	@Modifying
	@Query(value = "Update member_notify Set status=? where id=?", nativeQuery = true)
	@Transactional
	void UpdateStatusByNotifyId(String status, Long Id);
}
