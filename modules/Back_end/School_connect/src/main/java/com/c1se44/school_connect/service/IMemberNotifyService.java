package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.relationship.memberNotify;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMemberNotifyService {
	memberNotify save(memberNotify memberNotify);
	List<memberNotify> saveAll(List<memberNotify> memberNotify);
	List<memberNotify> findAllByUserNotifyId(userEntity user, Pageable pageable);
	int countAllByUserNotifyIdAndStatus(userEntity user, StatusName status);
	memberNotify findByNotifyId(Long notifyId);
	void UpdateStatusByNotifyId(StatusName status, Long Id);
	void UpdateStatusByUserId(StatusName status, Long userId);
	void UpdateStatusByUserIdAndStatus(StatusName status1, Long userId,StatusName status2);
}
