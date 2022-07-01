package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.relationship.memberForums;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMemberForumService {
	List<Long> findByUserId(Long userId, StatusName status);
	
	List<Long> findByForumId(Long forumId, StatusName status);
	
	memberForums save(memberForums memberForums);
	
	Boolean existsByUserForumIdAndForumUserId(userEntity userId, forumsEntity forumId);
	
	Boolean existsByUserForumIdAndForumUserIdAndStatus(userEntity userId, forumsEntity forumId, StatusName status);
	
	List<memberForums> findByForumUserIdAndStatus(forumsEntity forumId, StatusName status, Pageable pageable);
	List<memberForums> findByForumUserIdAndStatus(forumsEntity forumId, StatusName status);
	void UpdateStatus(StatusName status, Long userId, Long forumId);
	
	void UpdateUserByUserIdAndForumId(Long userChange, Long userCurrent, Long forumId);
	
	int countAllByForumIdsAndStatus(Long forumId, StatusName status);
	
	void DeleteStatusByUserIdAndForumId(Long userId, Long forumId);
}
